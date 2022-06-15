//:userId == session userId
module.exports.listOldChatRoom=
'SELECT rr.chatRoomNo chatRoomNo, u.userId ownerUserId, u.nickname ownerNickname, u.userAddr ownerAddr, u.userImg ownerImg, o.oldTitle oldTitle, o.oldNo oldNo, o.oldState oldState, o.oldImg oldImg, msg.recentMsg recentMsg, unread.unreadcount unreadcount, msg.recentTime recentTime '
+'FROM chatroom rr '
+'JOIN user u ON (rr.ownerUserId=u.userId) '
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

//:userId == session userId
module.exports.listProductChatRoom=
'SELECT rr.chatRoomNo chatRoomNo, u.userId ownerUserId, u.nickname ownerNickname, u.userAddr ownerAddr, u.userImg ownerImg, p.prodNo prodNo, p.prodName prodName, p.prodImg prodImg, p.isRental isRental, msg.recentMsg recentMsg, unread.unreadcount unreadcount, msg.recentTime recentTime '
+'FROM chatroom rr '
+'JOIN user u ON (rr.ownerUserId=u.userId) '
+'JOIN product p ON (rr.prodNo = p.prodNo) '
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
+'AND r.prodNo IS NOT NULL '
+'AND r.chatRoomNo=c.chatRoomNo '
+'AND c.readOrNot=1 '
+'AND c.sendUserId <>:userId '
+'GROUP BY c.chatRoomNo) unread ON (unread.countroom=rr.chatRoomNo) '

+'WHERE '
+'rr.chatRoomNo in '
+'(SELECT r.chatRoomNo '
+'FROM chatroom r '
+'WHERE ((r.ownerUserId=:userId AND r.ownerUserExit=1) OR (r.inquireUserId=:userId AND r.inquireUserExit=1)) '
+'AND r.prodNo IS NOT NULL) '
+'ORDER BY recentTime DESC';

//:prodNo
//chat toolbar for product
module.exports.getProduct='select prodNo, userId, prodName, prodImg, isRental, prodQuantity, deposit, rentalPrice from product where prodNo=:prodNo';

//:oldNo, :inquireUserId(sessionUserId), :ownerUserId
module.exports.insertOldChatRoom='INSERT INTO chatRoom (oldNo, inquireUserId, ownerUserId) VALUES (:oldNo, :inquireUserId, :ownerUserId)';

//:prodNo, :inquireUserId(sessionUserId), :ownerUserId
module.exports.insertProductChatRoom='INSERT INTO chatRoom (prodNo, inquireUserId, ownerUserId) VALUES (:prodNo, :inquireUserId, :ownerUserId)';

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
module.exports.insertChat = 'insert into chat (chatRoomNo, sendUserId, chatMessage, createdAt, readOrNot) values (:chatRoomNo, :sendUserId, :chatMessage, CURRENT_TIMESTAMP, 1)';

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

//:prodNo, :userId
module.exports.isNewProductChat = 'select * from chatRoom where inquireUserId=:inquireUserId and prodNo=:prodNo';