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
    console.log('oldChatRoom 네임스페이스에 접속');
    socket.on('disconnect', () => {
      console.log('oldChatRoom 네임스페이스 접속 해제');
    });
  });
  
  chat.on('connection', (socket) => {
    console.log('oldChat 네임스페이스에 접속');
    const req = socket.request;
    //console.log(req);
    const url = new URL(req.headers.referer);
    const roomId = url.searchParams.get('chatRoomNo'); //socket.request.headers.referer하면 현재 요청된 url나옴
    //여기에서 queryString으로 chatRoomNo parsing해서 잡아다 쓰자!
    
    socket.join(roomId); // chat 네임스페이스 접속
    console.log("roomId : "+roomId);

    //socket.to(방 아이디)로 특정 방에 데이터 보낼 수 잇다. 시스템 메세지 전용.
    //emit(이벤트 이름, 데이터) : 클라이언트에게 '이벤트 이름'으로 '데이터'를 보낸다
    //클라이언트가 이 데이터를 받으려면 '이벤트 이름'으로 된 이벤트 리스너를 달아야 한다.
    socket.to(roomId).emit('join', {
      user: 'system',
      //chat: `${req.session}님이 입장하셨습니다.`,
      chat: `누군가 입장하셨습니다.`,
    });

    socket.on('disconnect', () => {
      console.log('chat 네임스페이스 접속 해제');
      socket.leave(roomId); //chat 네임스페이스 접속해제

      //접속 해제 시 현재 방의 사람수를 구해서 참여자 수가 0명이면 방을 제거하는 http 요청 보낸다.
      //socket.adapter.rooms[roomId];에 참여중인 소켓정보가 담겨있다.
      const currentRoom = socket.adapter.rooms[roomId];
      /*
      const userCount = currentRoom ? currentRoom.length : 0;
      if (userCount === 0) { // 유저가 0명이면 방 삭제
        axios.delete(`http://localhost:8005/room/${roomId}`)
          .then(() => {
            console.log('방 제거 요청 성공');
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
      */
        //socket.to(방 아이디)로 특정 방에 데이터 보낼 수 잇다. 시스템 메세지 전용.
        socket.to(roomId).emit('exit', {
          user: 'system',
          chat: `${req.session}님이 퇴장하셨습니다.`,
        });
      //}
    });
    
    socket.on('chat', (data) => {
      socket.to(data.room).emit(data);
    });
  });
};
