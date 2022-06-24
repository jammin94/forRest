//:userId == session userId
module.exports.listOldChatRoom=
'SELECT rr.chatRoomNo chatRoomNo, u.userId inquireUserId, u.nickname inquireNickname, u.userAddr inquireAddr, u.userImg inquireImg, o.oldTitle oldTitle, o.oldNo oldNo, o.oldState oldState, o.oldImg oldImg, msg.recentMsg recentMsg, unread.unreadcount unreadcount, msg.recentTime recentTime '
+'FROM chatroom rr '
//+'JOIN user u ON (rr.inquireUserId=u.userId) '
+'JOIN user u ON ((CASE WHEN rr.inquireUserId = :userId THEN rr.ownerUserId ELSE rr.inquireUserId END)=u.userId) '
+'JOIN old o ON (rr.oldNo = o.oldNo) '
+'LEFT OUTER JOIN '
+'(SELECT c.chatRoomNo msgroom ,c.chatMessageNo, c.chatMessage recentMsg, c.createdAt recentTime '
+'FROM chat c '
+'JOIN chatroom r ON (r.chatRoomNo=c.chatRoomNo) '
+'JOIN ( '
+'SELECT MAX(chatMessageNo) AS maxVal '
+'FROM chat '
+'GROUP BY chatRoomNo '
+') cc ON (c.chatMessageNo=cc.maxVal) '
+'ORDER BY 1) msg ON (msg.msgroom=rr.chatRoomNo) '

+'LEFT OUTER JOIN '
+'(SELECT r.chatRoomNo countroom, COUNT(c.chatMessageNo) unreadcount '
+'FROM chatroom r, chat c '
+'WHERE ((r.ownerUserId=:userId AND r.ownerUserExit=1) OR (r.inquireUserId=:userId AND r.inquireUserExit=1)) '
+'AND r.oldNo IS NOT NULL '
+'AND r.chatRoomNo=c.chatRoomNo '
+'AND c.readOrNot=1 '
+'AND c.sendUserId <>:userId '
+'GROUP BY c.chatRoomNo) unread ON (unread.countroom=rr.chatRoomNo) '

+'WHERE '
+'rr.chatRoomNo in '
+'(SELECT r.chatRoomNo '
+'FROM chatroom r '
+'WHERE ((r.ownerUserId=:userId AND r.ownerUserExit=1) OR (r.inquireUserId=:userId AND r.inquireUserExit=1)) '
+'AND r.oldNo IS NOT NULL) '
+'ORDER BY recentTime DESC';

//:oldNo chat toolbar for old
module.exports.getOld = 'select oldNo, userId, oldImg, oldState, oldTitle, oldPrice from old where oldNo=:oldNo'

//:oldNo, :inquireUserId(sessionUserId), :ownerUserId
module.exports.insertOldChatRoom='INSERT INTO chatRoom (oldNo, inquireUserId, ownerUserId) VALUES (:oldNo, :inquireUserId, :ownerUserId)';

// :chatRoomNo
//enable to see chatRoom if anyone who belongs to some chat room chated!
module.exports.updateChatRoomToSee='UPDATE chatRoom SET inquireUserExit=1, ownerUserExit=1 WHERE chatRoomNo=:chatRoomNo';

//:chatRoomNo, :userId(session)
module.exports.updateChatRoomExit=
'UPDATE chatRoom SET ownerUserExit = CASE WHEN ownerUserId = :userId THEN 0 ELSE ownerUserExit END, '
+'inquireUserExit = CASE WHEN inquireUserId = :userId THEN 0 ELSE inquireUserExit END '
+'WHERE chatRoomNo=:chatRoomNo';

//:chatRoomNo
module.exports.listChat = 'select * from chat where chatRoomNo=:chatRoomNo';

//:chatmessageNo
module.exports.getChat = 'select * from chat where chatMessageNo=:chatMessageNo';

//:chatRoomNo, :userId(session), :chatMessage
//POST
module.exports.insertChat = 'insert into chat (chatRoomNo, sendUserId, chatMessage, createdAt, readOrNot) values (:chatRoomNo, :sendUserId, :chatMessage, CURRENT_TIMESTAMP, :readOrNot)';

//:chatRoomNo, :userId(session), :chatMessage, :map(JSON)
//POST
module.exports.insertMap = 'insert into chat (chatRoomNo, sendUserId, chatMessage, createdAt, readOrNot, map) values (:chatRoomNo, :sendUserId, :chatMessage, CURRENT_TIMESTAMP, :readOrNot, :map)';

//:chatRoomNo, :userId(session), :fileName
//POST
module.exports.insertImage = 'insert into chat (chatRoomNo, sendUserId, chatMessage, createdAt, readOrNot, fileName) values (:chatRoomNo, :sendUserId, "사진을 보냈습니다", CURRENT_TIMESTAMP, :readOrNot, :fileName)';

//:chatRoomNo, :userId(session)
module.exports.updateReadOrNot='update chat set readOrNot=NULL where chatRoomNo=:chatRoomNo and sendUserId<>:userId and createdAt < CURRENT_TIMESTAMP';

//:userId(session)
module.exports.getTotalUnreadMsg=
'SELECT COUNT(c.chatMessageNo) totalUnreadMsgCount FROM chatroom r, chat c '
+'WHERE ((r.ownerUserId=:userId AND r.ownerUserExit=1) OR (r.inquireUserId=:userId AND r.inquireUserExit=1)) '
+'AND r.chatRoomNo=c.chatRoomNo AND c.readOrNot=1 AND c.sendUserId <>:userId';

//:userId
module.exports.getUser = 'select * from user where userId=:userId';

//:oldNo, :userId
module.exports.isNewOldChat = 'select * from chatRoom where inquireUserId=:inquireUserId and oldNo=:oldNo';

//:userId. :chatRoomNo
//module.exports.getOtherUser = 'SELECT (CASE WHEN c.inquireUserId = :userId THEN c.ownerUserId ELSE c.inquireUserId END) AS userId FROM chatroom c, user u WHERE c.chatroomNo=:chatRoomNo';
module.exports.getOtherUser = 'SELECT (CASE WHEN c.inquireUserId = :userId THEN c.ownerUserId ELSE c.inquireUserId END) AS userId, u.userImg userImg FROM chatroom c, user u WHERE c.chatroomNo=:chatRoomNo and u.userId=(CASE WHEN c.inquireUserId = :userId THEN c.ownerUserId ELSE c.inquireUserId END)';

//:chatRoomNo
module.exports.deleteChatRoom= 'DELETE FROM oldChatRoom WHERE chatRoomNo=:chatRoomNo'

//:chatRoomNo
module.exports.isChatRoomEmpty= 'SELECT count(*) FROM oldChatRoom WHERE chatRoomNo=:chatRoomNo'