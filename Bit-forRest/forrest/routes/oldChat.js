const express = require('express');
const {QueryTypes} = require('sequelize');
const Query = require('../queries/query'); 
const db = require('../models/index');


const router = express.Router();

//중고거래 채팅방 나가기
router.get('/out', async (req, res, next) => {
  try {
    
    res.render('oldChatRoom',{lists});

  }catch (err) {
    console.error(err)
    next(err)
  }
});

//중고거래 채팅하기 버튼 눌렀을 시
router.get('/init', async (req, res, next) => {
  try {
    
    res.render('oldChatRoom',{lists});

  }catch (err) {
    console.error(err)
    next(err)
  }
});

//기본화면. 중고거래 채팅방 목록을 보여준다.
router.get('/', async (req, res, next) => {
  try {
    //listOldChatRoom
    const sessionId= req.session.user
    console.log(sessionId);
    let query=Query.listOldChatRoom;
    const lists = await db.sequelize.query(query, {
      replacements: {userId : sessionId}, //sessionId 끌어오는 법 알아내서 수정하자
      type: QueryTypes.SELECT,
      raw: true
    });

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
        sendUserId : req.session.user, //일단 얘로 하자..., //sessionId 끌어오는 법 알아내서 수정하자
        chatMessage : chatMessage,
        }, 
      type: QueryTypes.INSERT,
      raw: true
    });
    //console.log("insertChat : "+insertChat); // sequelize insert는 primekey, foreignkey만 return해준다.

    //보낸 채팅 얻기
    query=Query.getChat
    const getChat = await db.sequelize.query(query, {
      replacements: 
      { chatMessageNo : insertChat[0]}, 
      type: QueryTypes.SELECT,
      raw: true
    });
    console.log(getChat);

    const data={chat: getChat};
    
    req.app.get('io').of('/oldChat').to(roomNo).emit('oldChat',data);
    res.send('ok');

  }catch (err) {
    console.error(err)
    next(err)
  }
});

module.exports = router;
