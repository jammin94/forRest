const SocketIO = require('socket.io');
const axios = require('axios');

module.exports = (server, app, sessionMiddleware) => {
  const io = SocketIO(server, { path: '/socket.io'});
  app.set('io', io); //app.get('io')로 SocketIO를 불러올 수 있다.
  const room = io.of('/oldChatRoom'); // '/room'으로 namespace를 지정
  const chat = io.of('/oldChat'); // '/chat'으로 namespace를 지정

  //미들웨어 장착. 모든 웹 소켓 연결 시 마다 실행된다.
  //socket.request : 요청객체
  //socket.request.res : 응답객체
  //이제 socket.request 객체 안에 socket.request.session이 형성된다!
  io.use((socket, next) => {
    sessionMiddleware(socket.request, socket.request.res, next);
  });

  room.on('connection', (socket) => {
    //unreadcount 때문에 room도 room을 따로 만들어보자.
   // socket.join(socket.request.connection._peername.address);
   //console.log(socket.request.connection);
 
	const userId1 = new URL(socket.request.headers.referer);
	console.log('asdasd123213' +userId1);
	const userId=userId1.pathname.split('/')[3];
	console.log('asdasd '+userId);
	
	console.log('oldChatRoom 네임스페이스에 접속 : room='+userId);
	socket.join(userId);

    socket.on('disconnect', () => {
       console.log('oldChatRoom 네임스페이스에 해제 : room='+userId);
      socket.leave(userId);
    });
  });
  
  chat.on('connection', (socket) => {
    console.log('oldChat 네임스페이스에 접속');
    const req = socket.request;
    const url = new URL(req.headers.referer);
    const roomId = url.searchParams.get('chatRoomNo'); 
    //socket.request.headers.referer하면 현재 요청된 url나옴
    //여기에서 queryString으로 chatRoomNo parsing해서 잡아다 쓰자!
    
    socket.join(roomId,()=>{
		const currentRoom = socket.adapter.rooms[roomId];
		console.log('현재 접속자 수: '+currentRoom.length);
		if(currentRoom.length==2){
			chat.to(roomId).emit('fulljoin', '상대방이 접속중입니다', 'true');
		}
	}); // chat 네임스페이스 접속
    
    
    //const currentRoom = socket.adapter.rooms[roomId];
	//	console.log('현재 접속자 수: '+currentRoom.length);
	//	if(currentRoom.length==2){
	//		chat.to(roomId).emit('fulljoin', '상대방이 접속중입니다', true);

    //socket.to(방 아이디)로 특정 방에 데이터 보낼 수 잇다. 시스템 메세지 전용.
    //emit(이벤트 이름, 데이터) : 클라이언트에게 '이벤트 이름'으로 '데이터'를 보낸다
    //클라이언트가 이 데이터를 받으려면 '이벤트 이름'으로 된 이벤트 리스너를 달아야 한다.
     
    console.log("connection info :",
	//socket.request.connection);
	socket.adapter);
	
	
    socket.on('disconnect', () => {
      console.log('chat 네임스페이스 접속 해제');
      socket.leave(roomId); //chat 네임스페이스 접속해제
      
      socket.to(roomId).emit('exit', '상대방이 부재중입니다', 'false');

      //접속 해제 시 현재 방의 사람수를 구해서 참여자 수가 0명이면 방을 제거하는 http 요청 보낸다.
      //socket.adapter.rooms[roomId];에 참여중인 소켓정보가 담겨있다.
      //const currentRoom = socket.adapter.rooms[roomId];
    });
    
   // socket.on('chat', (data) => {
    //  socket.to(data.room).emit(data);
    //});
  });
};
