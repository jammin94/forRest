const express = require('express');
const {QueryTypes} = require('sequelize');
const Query = require('../queries/query'); 
const db = require('../models/index');
const moment = require('moment');

moment.locale('ko'); //한글로 시간 표시  

const router = express.Router();

//중고거래 채팅방 나가기
router.get('/exit', async (req, res, next) => {
  try {
	let query=Query.updateChatRoomExit;
	const updateChatRoomExit = await db.sequelize.query(query,{
		replacements: {
			chatRoomNo: req.query.chatRoomNo,
			userId : req.session.user,
		},
		type: QueryTypes.UPDATE,
	  	raw: true
	});
	res.send('1');
	
	}catch (err) {
    console.error(err)
    next(err)
 	}
});


//중고물품 상세조회에서 중고거래 채팅하기 버튼 눌렀을 시
router.get('/init/:userId/:oldNo', async (req, res, next) => {
  try {
	const sessionId = req.params.userId;
	const oldNo = req.params.oldNo;
 	//overwrite
	req.session.user = sessionId;
	req.session.save();
	
	//getOld
	let query=Query.getOld;
	const oldArr = await db.sequelize.query(query,{
		replacements: {
			oldNo: oldNo,
		},
		type: QueryTypes.SELECT,
      	raw: true
	});
	//물건주인 추적
	const ownerUserId = oldArr[0].userId;
	
	//원래 있던 방인가? 아니면 처음인가?
	query=Query.isNewOldChat;
	const isNewOldChat = await db.sequelize.query(query,{
		replacements: {
			oldNo: oldNo,
			inquireUserId: sessionId,
		},
		type: QueryTypes.SELECT,
      	raw: true
	});
	//console.log(isNewOldChat);

	let chatRoomNo;
	
	//처음이면 채팅방 생성. 아직 둘다 exit가 0,0상태임.
	if(isNewOldChat[0]===undefined){
		query=Query.insertOldChatRoom;
		var makeRoom = await db.sequelize.query(query,{
			replacements: {
				oldNo: oldNo,
				inquireUserId: sessionId,
				ownerUserId: ownerUserId,
			},
			type: QueryTypes.INSERT,
	      	raw: true
		});
		chatRoomNo=makeRoom[0]
		console.log('새로 생성됨. chatRoomNo : '+chatRoomNo);
	}else{
		chatRoomNo=isNewOldChat[0].chatRoomNo
		console.log('원래 있었음. chatRoomNo : '+chatRoomNo);
	}

	query=Query.listOldChatRoom;
    const lists = await db.sequelize.query(query, {
      replacements: {userId : sessionId}, 
      type: QueryTypes.SELECT,
      raw: true
    });
    
    for(let list of lists){
		list.recentTime = moment(list.recentTime).fromNow();
	}
    
    //response에 담아서 'oldChatRoom.html'로 보내기
    //console.log(lists);
    //console.log('chatRoomNo: '+chatRoomNo);
    const immediate = {chatRoomNo: chatRoomNo, oldNo: oldNo};
    res.render('oldChatRoom',{lists, immediate});

  }catch (err) {
    console.error(err)
    next(err)
  }
});

//기본화면. 중고거래 채팅방 목록을 보여준다.
router.get('/list/:userId', async (req, res, next) => {
  try {
	//nodejs session
	const sessionId = req.params.userId
	
	//overwrite
	req.session.user = sessionId;
	req.session.save()
	
	let query=Query.listOldChatRoom;
    const lists = await db.sequelize.query(query, {
      replacements: {userId : sessionId}, 
      type: QueryTypes.SELECT,
      raw: true
    });
    
    //~~분전 ~~시간 전
    for(let list of lists){
		list.recentTime = moment(list.recentTime).fromNow();
	}

	//response에 담아서 'oldChatRoom.html'로 보내기
    console.log(lists);
    res.render('oldChatRoom',{lists});

  }catch (err) {
    console.error(err)
    next(err)
  }
});




//해당 채팅방 더블클릭시 채팅창 이동
// '/oldChat/:oldNo?chatRoomNo=something'
router.get('/:oldNo', async (req, res, next) => {
  try {
    //listChat
    let query=Query.listChat;
    const chatLists = await db.sequelize.query(query, {
      replacements: {chatRoomNo : req.query.chatRoomNo}, //sessionId 끌어오는 법 알아내서 수정하자
      type: QueryTypes.SELECT,
      raw: true
    });
    
    for(let chatList of chatLists){
		chatList.createdAt = moment(chatList.createdAt).format('LT');
	}

    //getOld
    query=Query.getOld;
    const oldArr = await db.sequelize.query(query, {
      replacements: {oldNo : req.params.oldNo}, 
      type: QueryTypes.SELECT,
      raw: true
    });

    const old = oldArr[0];
    const chatRoomNo = req.query.chatRoomNo
    const user = req.session.user
    
    //접속하면 읽음표시 ㄱㄱ
    query=Query.updateReadOrNot;
    const updateReadOrNot = await db.sequelize.query(query, {
      replacements: {
		chatRoomNo : chatRoomNo,
		userId : user}, 
      type: QueryTypes.UPDATE,
      raw: true
    });
    
    //response에 담아서 'oldChatRoom.html'로 보내기
    res.render('oldChat',{chatLists, old, user, chatRoomNo});

  }catch (err) {
    console.error(err)
    next(err)
  }
});






//채팅창 채팅치기
// '/oldChat/:oldNo/chat?chatRoomNo=something'
router.post('/chat/:oldNo', async (req, res, next) => {
  try {
    //insertChat
    let query=Query.insertChat;
    const roomNo = req.query.chatRoomNo;
    const chatMessage = req.body.chat
    //console.log("req.body.chat : "+chatMessage);
    const insertChat = await db.sequelize.query(query, {
      replacements: 
      { chatRoomNo : roomNo,
        sendUserId : req.session.user, //sessionId 
        chatMessage : chatMessage,
        }, 
      type: QueryTypes.INSERT,
      raw: true
    });
    //console.log("insertChat : "+insertChat); // sequelize insert는 primekey, foreignkey만 return해준다.
    
    //칠때마다 채팅방 나가기를 취소하고 채팅방이 보이게 한다.
    query=Query.updateChatRoomToSee
    const updateChatRoomToSee = await db.sequelize.query(query, {
      replacements: 
      { chatRoomNo : req.query.chatRoomNo}, 
      type: QueryTypes.UPDATE,
      raw: true
    });

    //보낸 채팅 얻어서 실시간으로 채팅창에 띄우기
    query=Query.getChat
    const getChat = await db.sequelize.query(query, {
      replacements: 
      { chatMessageNo : insertChat[0]}, 
      type: QueryTypes.SELECT,
      raw: true
    });

	const data={chat: getChat};
    req.app.get('io').of('/oldChat').to(roomNo).emit('oldChat',data);

  }catch (err) {
    console.error(err)
    next(err)
  }
});

module.exports = router;
