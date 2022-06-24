SET foreign_key_checks=0;
DROP TABLE board, chat, chatroom, coupon, imgs, `old`, oldlike, oldreview, owncoupon, product, rentalreview, report, `transaction`, user, wishlist;

CREATE TABLE user (
   userId      VARCHAR(30)   NOT NULL,
   nickname      VARCHAR(100)    NOT NULL UNIQUE,
   phone VARCHAR(100) NOT NULL UNIQUE, 
   password VARCHAR(100) NOT NULL,
   userName VARCHAR(20) NOT NULL,
   userAddr VARCHAR(100),
   role VARCHAR(10) NOT NULL DEFAULT 'user',
   joinDate DATETIME NOT NULL,
   joinPath VARCHAR(10) NOT NULL,
   userImg VARCHAR(100),
   recentDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   pushToken VARCHAR(100),
   leaveApplyDate DATETIME,
   leaveDate DATETIME, 
   PRIMARY KEY (userId)   
);

CREATE TABLE product 
(
   prodNo VARCHAR(40) NOT NULL,
   width INTEGER NOT NULL,
   length INTEGER NOT NULL,
   height INTEGER NOT NULL,
   userId VARCHAR(30) NOT NULL,
   prodCondition VARCHAR(30) NOT NULL DEFAULT '물품보관승인신청중',
   prodName VARCHAR(40) NOT NULL,
   prodQuantity INTEGER NOT NULL,
   prodDetail VARCHAR(600) NOT NULL,
   isRental TINYINT(1) NOT NULL DEFAULT 0,
   rentalCounting INTEGER,
   rentalPrice INTEGER,
   account VARCHAR(30),
   category VARCHAR(10) NOT NULL,
   divyAddress VARCHAR(100) NOT NULL,
   prodImg VARCHAR(100) NOT NULL,
   recentImg VARCHAR(100),
   regDate DATETIME NOT NULL,
   PRIMARY KEY (prodNo)
);

ALTER TABLE product ADD FOREIGN KEY(userId) REFERENCES user(userId) ON DELETE CASCADE;

CREATE TABLE transaction
(
   tranNo VARCHAR(100) NOT NULL,
   userId VARCHAR(30) NOT NULL,
   prodNo VARCHAR(40) NOT NULL,
   divyRequest VARCHAR(100),
   divyAddress VARCHAR(100) NOT NULL,
   pickupAddress VARCHAR(100) NOT NULL,
   startDate DATE NOT NULL,
   endDate DATE NOT NULL,
   period INTEGER NOT NULL,
   tranCode TINYINT(1) NOT NULL DEFAULT 0,
   paymentNo VARCHAR(30) NOT NULL,
   paymentDate DATETIME,
   paymentWay VARCHAR(50),
   receiverPhone VARCHAR(50),
   receiverName VARCHAR(50),
   prodName VARCHAR(40) NOT NULL,
   prodImg VARCHAR(100) NOT NULL,
   originPrice INTEGER NOT NULL,
   discountPrice INTEGER,
   resultPrice INTEGER,
   reviewDone TINYINT(1) NOT NULL DEFAULT 0,
   complete TINYINT(1) NOT NULL DEFAULT 0,
  cancelComplete TINYINT(1) NOT NULL DEFAULT 0,
   PRIMARY KEY (tranNo)
);

ALTER TABLE transaction ADD FOREIGN KEY(userId) REFERENCES user(userId) ON DELETE CASCADE;
ALTER TABLE transaction ADD FOREIGN KEY(prodNo) REFERENCES product(prodNo) ON DELETE CASCADE;


CREATE TABLE old (
   oldNo VARCHAR(40) NOT NULL,
   userId VARCHAR(30) NOT NULL,
   oldPrice INTEGER NOT NULL,
   oldTitle VARCHAR(100) NOT NULL,
   oldDetail VARCHAR(8000) NOT NULL,
   oldDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   oldView INTEGER NOT NULL,
   category VARCHAR(10) NOT NULL,
   oldState TINYINT(1)   NOT NULL DEFAULT 1,
   oldImg VARCHAR(60) NOT NULL,
   oldAddr VARCHAR(100) NOT NULL,
   PRIMARY KEY (oldNo),
   FOREIGN KEY (userId) REFERENCES user(userId) ON DELETE CASCADE
);



CREATE TABLE coupon(
   couponNo   VARCHAR(50) NOT NULL ,
   couponName   VARCHAR(50)   NOT NULL,
   couponCreDate   DATETIME      ,
   couponDelDate   DATETIME      ,   
   discount      DOUBLE   NOT NULL,
   PRIMARY KEY (couponNo)
);


CREATE TABLE ownCoupon(
   ownCouponNo         INTEGER      NOT NULL AUTO_INCREMENT,
   userId            VARCHAR(30)      NOT NULL,
   couponNo            VARCHAR(50)	NOT NULL,
   ownCouponCreDate   DATETIME         NOT NULL,
   ownCouponDelDate   DATETIME         NOT NULL,
   PRIMARY KEY(ownCouponNo),
   FOREIGN KEY(userId) REFERENCES user(userId) ON DELETE CASCADE,
   FOREIGN KEY(couponNo) REFERENCES coupon(couponNo) ON DELETE CASCADE
);


CREATE TABLE wishlist
(
   wishlistNo INTEGER NOT NULL AUTO_INCREMENT,
   prodNo VARCHAR(40) NOT NULL,
   wishedUserId VARCHAR(30) NOT NULL,
   period INTEGER,
   PRIMARY KEY (wishlistNo)
);

ALTER TABLE wishList ADD FOREIGN KEY(wishedUserId) REFERENCES user(userId) ON DELETE CASCADE;
ALTER TABLE wishList ADD FOREIGN KEY(prodNo) REFERENCES product(prodNo) ON DELETE CASCADE;

CREATE TABLE rentalReview
(
   reviewNo INTEGER NOT NULL AUTO_INCREMENT,
   reviewImg VARCHAR(200) NOT NULL,
   reviewDetail VARCHAR(600) NOT NULL,
   reviewScore INTEGER NOT NULL,
   prodNo VARCHAR(40) NOT NULL,
   userId VARCHAR(30) NOT NULL,
   regDate DATE NOT NULL,
   PRIMARY KEY (`reviewNo`)
);

ALTER TABLE rentalReview ADD FOREIGN KEY(userId) REFERENCES user(userId) ON DELETE CASCADE;
ALTER TABLE rentalReview ADD FOREIGN KEY(prodNo) REFERENCES product(prodNo) ON DELETE CASCADE;

CREATE TABLE `chatRoom` (
  `chatRoomNo` int NOT NULL AUTO_INCREMENT,
  `oldNo` varchar(40) DEFAULT NULL,
  `prodNo` varchar(40) DEFAULT NULL,
  `inquireUserId` varchar(30) NOT NULL,
  `ownerUserId` varchar(30) NOT NULL,
  `inquireUserExit` tinyint(1) DEFAULT '0',
  `ownerUserExit` tinyint(1) DEFAULT '0',
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`chatRoomNo`),
  KEY `oldNo` (`oldNo`),
  KEY `prodNo` (`prodNo`),
  KEY `inquireUserId` (`inquireUserId`),
  KEY `ownerUserId` (`ownerUserId`),
  CONSTRAINT `chatroom_ibfk_1` FOREIGN KEY (`oldNo`) REFERENCES `old` (`oldNo`) ON DELETE CASCADE,
  CONSTRAINT `chatroom_ibfk_2` FOREIGN KEY (`prodNo`) REFERENCES `product` (`prodNo`) ON DELETE CASCADE,
  CONSTRAINT `chatroom_ibfk_3` FOREIGN KEY (`inquireUserId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `chatroom_ibfk_4` FOREIGN KEY (`ownerUserId`) REFERENCES `user` (`userId`) ON DELETE CASCADE 
);

CREATE TABLE IF NOT EXISTS `chat` (
  `chatMessageNo` int(11) NOT NULL AUTO_INCREMENT,
  `chatRoomNo` int(11) NOT NULL,
  `sendUserId` varchar(30) NOT NULL,
  `chatMessage` varchar(4000) NOT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `readOrNot` tinyint(1) DEFAULT 1,
  `fileName` varchar(4000) DEFAULT NULL,
  `map` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`map`)),
  PRIMARY KEY (`chatMessageNo`),
  KEY `chatRoomNo` (`chatRoomNo`),
  KEY `sendUserId` (`sendUserId`),
  CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`chatRoomNo`) REFERENCES `chatroom` (`chatRoomNo`) ON DELETE CASCADE,
  CONSTRAINT `chat_ibfk_2` FOREIGN KEY (`sendUserId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE `board` (
  `boardNo` int NOT NULL AUTO_INCREMENT,
  `boardTitle` varchar(500) NOT NULL DEFAULT '',
  `boardDetail` varchar(5000) NOT NULL DEFAULT '',
  `boardDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `boardPin` int DEFAULT 0, 
  `boardFlag` varchar(2) NOT NULL, 
  `category` varchar(10),
  `couponURL` varchar(1000),
  PRIMARY KEY (`boardNo`)
);

CREATE TABLE `imgs` (
  `imgNo` int NOT NULL AUTO_INCREMENT,
  `contentsNo` varchar(80) DEFAULT NULL,
  `fileName` varchar(100) DEFAULT NULL,
  `contentsFlag` varchar(20) NOT NULL,
  PRIMARY KEY (`imgNo`)
);


CREATE TABLE oldlike(
	oldLikeNo	 INTEGER	 NOT NULL	 AUTO_INCREMENT,
	oldNo 		 VARCHAR(40) NOT NULL ,
	userId		 VARCHAR(30)	NOT NULL ,
	PRIMARY KEY(oldLikeNo),
	KEY oldNo (oldNo),
	KEY userId (userId),
	CONSTRAINT `oldlike_ibfk_1` FOREIGN KEY (`oldNo`) REFERENCES `old` (`oldNo`) ON DELETE CASCADE,
	CONSTRAINT `oldlike_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
);

CREATE TABLE oldReview(
	oldReviewNo	INTEGER		NOT NULL	AUTO_INCREMENT,
	reviewUserId	VARCHAR(30)	NOT NULL	REFERENCES	user(userId),
	reviewedUserId	VARCHAR(30)	NOT NULL	REFERENCES	user(userId),
	oldNo		VARCHAR(40)		NOT NULL	REFERENCES	old(oldNo),
	reviewDetail	VARCHAR(100),
	userRate		DOUBLE,
	reviewDate	DATE 		NOT NULL	DEFAULT (CURRENT_DATE),
	PRIMARY KEY(oldReviewNo)
);

CREATE TABLE report(
	reportNo		INTEGER		NOT NULL	AUTO_INCREMENT,
	reportUser		VARCHAR(30)	NOT NULL 	REFERENCES	user(userId),
	reportedUser	VARCHAR(30)	NOT NULL 	REFERENCES	user(userId),
	reportOldNo	VARCHAR(40)		NOT NULL	REFERENCES	old(oldNo),
	reportChatroomNo	INTEGER,
	reportCategory	VARCHAR(20),
	reportDate	DATETIME	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	reportDetail	VARCHAR(100),
	reportChat	VARCHAR(4000),
	reportCode	TINYINT(1),
	PRIMARY KEY(reportNo)
);	


INSERT INTO user
VALUES ('admin','adminNickname','adminphone','$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG','adminName','adminAddr','admin',CURDATE(),'own','adminImg.jpg',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('user01@naver.com','user01','user01Phone','$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG','user01Name','user01Addr','user',CURDATE(),'own','user01Img.jpg',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('user02@naver.com','user02','user02Phone','$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG','user02Name','user02Addr','user',CURDATE(),'own','user02Img',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('user03@naver.com','user03','user03Phone','$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG','user03Name','user03Addr','user',CURDATE(),'own','user03Img',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('captain9697@naver.com','구스범수','010/4114/9697','$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG','박범수','13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)','user', CURDATE(),'own','goosebeomImg.jpg',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('sanstory12rt@naver.com','과자조아','010/8783/6065','$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG','형산','13271/경기오산시~~','user', CURDATE(),'own','iu.jpg',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('tkdals4534@naver.com', '이어폰폴펜', '01033294534', '$2a$10$3lnMim/bwALsgFZfPyVvrek2yhYr8dT9riXND/gsHycClzaWt7TDK', '이상민', '13121/경기 성남시 수정구 창곡동 501/3603동 1112호/ (창곡동, 성남위례 LH36단지)', 'user', '2022-06-22 09:45:34', 'own', NULL, '2022-06-22 09:45:34', NULL, NULL, NULL);

INSERT INTO user
VALUES ('jj3033@naver.com', '금붕어회', '01093512557', '$2a$10$7KryLXSZc1CuQLNJF6TZve7UZmRQieY4iqh3dLAkC9Ae4cHVSax0q', '정태영', '02582/서울 동대문구 신설동 98-49/201/ (신설동, 신설동주상복합)', 'user', '2022-06-23 00:09:41', 'own', '37b5c554-62db-4fbb-bbfb-0d0ff63d415b.jpg', '2022-06-23 00:09:53', NULL, NULL, NULL);

INSERT INTO user
VALUES ('qwerty5266@naver.com', '띵띵', '01093995266', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG','김명선','14999/경기 시흥시','user', '2022-06-23 01:19:41','own','chacha.JPG','2022-06-23 01:19:41', NULL, NULL, NULL);

INSERT INTO old
VALUES('48314086-6371-4cbf-8a16-c0495771f046', 'user01@naver.com', 5000, '분위기 있는 랜턴', '싸게 가져가세요~', '2022-06-23 09:56:47', 1, '조명', 1, '098af6ee-f658-4873-877e-3bc8e2095a58.jpg', '삼성동');

INSERT INTO old
VALUES	('5643ddbf-60fd-44b1-add6-8d7548357c7b', 'user01@naver.com', 9000, '고기굽기 좋은 테이블', '사용감이 있어 저렴하게 올립니다. 기스 살짝 있어요.', '2022-06-23 10:02:08', 0, '테이블', 1, '99b4ecd1-ebba-42db-b936-f646060a04d1.jpg', '삼성동');

INSERT INTO old
VALUES('a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', 'user01@naver.com', 566, '감성 조명', '싸게 가져가세요', '2022-06-23 09:50:06', 1, '조명', 1, '9ab1853e-4b67-4bd2-9ee2-b1ff4d7b4398.jpg', '청담동');

INSERT INTO old
VALUES('ea5668d7-ad6e-4f09-8719-474876e6cad4', 'admin', 60000, '원목 테이블', '튼튼해요 퀄리티 정말 좋아요.', '2022-06-23 09:59:53', 1, '테이블', 1, '2638800c-6535-4ab8-b052-d5c7ee82b180.jpg', '역삼동');

INSERT INTO old
VALUES('fecb1f75-15b2-4ca6-a105-32a1ba628fce', 'user01@naver.com', 90000, '한번 사용한 텐트', '실물 예뻐요~', '2022-06-23 09:55:13', 0, '텐트', 1, 'b50b7c22-c12a-4896-9aa8-4b9ac6deed42.jpg', '연희동');

INSERT INTO `product` (`prodNo`, `width`, `length`, `height`, `userId`, `prodCondition`, `prodName`, `prodQuantity`, `prodDetail`, `isRental`, `rentalCounting`, `rentalPrice`, `account`, `category`, `divyAddress`, `prodImg`, `recentImg`, `regDate`) VALUES
	('52fb9c79-d83b-436a-b5be-5b27a6b13c33', 10, 10, 2, 'captain9697@naver.com', '보관중', '바베큐,숯불 그릴/캠핑 그릴', 1, '캠핑용 가정용 그릴이고 한번사용후 보관했는데 숯자국은 남네요\r\n중형으로 간단히 구워먹기좋아요 3-4인용이고 가방이 깨끗하지않아요\r\n숯사용후 남은것도 드려요  이사짐정리중입니다', 1, 0, 5000, '국민/47810204386651', '그릴', '06035/서울 강남구 도산대로 402-2/울집/ (신사동)', '8af51708-419c-45d4-ba17-ecb5f6600e6c.jpg', NULL, '2022-06-22 14:11:24');

INSERT INTO `product` (`prodNo`, `width`, `length`, `height`, `userId`, `prodCondition`, `prodName`, `prodQuantity`, `prodDetail`, `isRental`, `rentalCounting`, `rentalPrice`, `account`, `category`, `divyAddress`, `prodImg`, `recentImg`, `regDate`) VALUES
('7374c5cb-e68b-4d65-84a9-013f0d1df57c', 10, 10, 2, 'captain9697@naver.com', '보관중', '라탄 전등갓 줄전구 조명', 1, '[손품]이라는 곳에서 구매한 라탄 전등 갓 조명이예요\r\n20구 이고, 건전지형 입니다 (USB형 나오기전 구매)\r\n저는 캠핑때 사용하려고 샀었고, 1회 사용 후 보관만 해둔 상태 입니다\r\n\r\n샀던 곳 상세설명 첨부 참고해주세요\r\n더스트백에 넣어 드립니다\r\n라탄 갓은 전구에 일일히 따로 끼워야 하는데 20개 다 하려면 시간 꽤 걸려요 그래서 끼운채 있고 그대로 드려요!', 1, 0, 3000, '국민/47810204386651', '조명', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', 'e54d899f-8a6c-4f49-bb89-8703196bf34b.jpg', NULL, '2022-06-22 14:15:26');

INSERT INTO `product` (`prodNo`, `width`, `length`, `height`, `userId`, `prodCondition`, `prodName`, `prodQuantity`, `prodDetail`, `isRental`, `rentalCounting`, `rentalPrice`, `account`, `category`, `divyAddress`, `prodImg`, `recentImg`, `regDate`) VALUES
('b37a6c88-8ec7-4d39-9e6b-8474e3376fce', 10, 10, 2, 'captain9697@naver.com', '보관중', '캠핑의자,캠핑체어,스노우라인,유아의자', 1, '야외에서 쓰던거라 사용감 있어요.\r\n그래도 튼튼하고 사용하기 좋아요\r\n\r\n민트색의자는 주니어의자 라고해서 당근구입하고 봤더니 어린이가 쓰는거라 안쓰고 보관만했어요. 꺼내봤더니 많이 지저분해졌네요.이것도 튼튼해요~\r\n스노우라인꺼 사시는분 필요하시면 드려요~', 1, 0, 3000, '국민/47810204386651', '의자', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '2b35cd5b-2680-4f81-8db9-17090645c038.jpg', NULL, '2022-06-22 14:19:07');

INSERT INTO `transaction` (`tranNo`, `userId`, `prodNo`, `divyRequest`, `divyAddress`, `pickupAddress`, `startDate`, `endDate`, `period`, `tranCode`, `paymentNo`, `paymentDate`, `paymentWay`, `receiverPhone`, `receiverName`, `prodName`, `prodImg`, `originPrice`, `discountPrice`, `resultPrice`, `reviewDone`, `complete`) VALUES
 ('193f067d-3eda-4a07-a68b-e90824f642cf', 'captain9697@naver.com', 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', NULL, '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '2022-06-23', '2022-10-21', 120, 0, 'imp_930121942719', '2022-06-22 14:19:07', NULL, '010/4114/9697', '박범수', '캠핑의자,캠핑체어,스노우라인,유아의자,어린이캠핑', '2b35cd5b-2680-4f81-8db9-17090645c038.jpg', 960, 0, 960, 0, 0);

INSERT INTO `transaction` (`tranNo`, `userId`, `prodNo`, `divyRequest`, `divyAddress`, `pickupAddress`, `startDate`, `endDate`, `period`, `tranCode`, `paymentNo`, `paymentDate`, `paymentWay`, `receiverPhone`, `receiverName`, `prodName`, `prodImg`, `originPrice`, `discountPrice`, `resultPrice`, `reviewDone`, `complete`) VALUES
('22679442-ce59-41f8-8191-c5a26ee25ca3', 'captain9697@naver.com', '7374c5cb-e68b-4d65-84a9-013f0d1df57c', NULL, '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '2022-06-23', '2022-07-23', 30, 0, 'imp_380900755748', '2022-06-22 14:15:26', NULL, '010/4114/9697', '박범수', '라탄 전등갓 줄전구 조명', 'e54d899f-8a6c-4f49-bb89-8703196bf34b.jpg', 240, 0, 240, 0, 0);

INSERT INTO `transaction` (`tranNo`, `userId`, `prodNo`, `divyRequest`, `divyAddress`, `pickupAddress`, `startDate`, `endDate`, `period`, `tranCode`, `paymentNo`, `paymentDate`, `paymentWay`, `receiverPhone`, `receiverName`, `prodName`, `prodImg`, `originPrice`, `discountPrice`, `resultPrice`, `reviewDone`, `complete`) VALUES
('7338e575-9b26-46fe-920f-9004098a3ef4', 'captain9697@naver.com', '52fb9c79-d83b-436a-b5be-5b27a6b13c33', NULL, '06035/서울 강남구 도산대로 402-2/울집/ (신사동)', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '2022-06-23', '2022-08-22', 60, 0, 'imp_254658714863', '2022-06-22 14:11:24', NULL, '010/4114/9697', '박범수', '바베큐,숯불 그릴/캠핑 그릴', '8af51708-419c-45d4-ba17-ecb5f6600e6c.jpg', 480, 0, 480, 0, 0);

INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'e3a2fad8-188d-45f8-bec1-881f185e090d#', 'user02@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '가족이 구매했는데 만족하네요~가격은 사악하지만 좋습니다~', '3', 'e3a2fad8-188d-45f8-bec1-881f185e090d#', 'user03@naver.com', '20210525');

INSERT INTO `chatroom` (`chatRoomNo`, `oldNo`, `prodNo`, `inquireUserId`, `ownerUserId`, `inquireUserExit`, `ownerUserExit`, `createdAt`) VALUES
	(1, 'a', NULL, 'user02@naver.com', 'user01@naver.com', 1, 1, '2022-05-30 18:21:00'),
	(2, 'a', NULL, 'user03@naver.com', 'user01@naver.com', 1, 1, '2022-05-30 18:21:42'),
	(4, NULL, 'a', 'user02@naver.com', 'user01@naver.com', 1, 1, '2022-05-30 18:31:32'),
	(5, 'e', NULL, 'admin', 'user01@naver.com', 1, 1, '2022-06-23 06:28:50'),
	(6, 'f', NULL, 'admin', 'user01@naver.com', 0, 0, '2022-06-23 06:34:33'),
	(7, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', NULL, 'admin', 'user01@naver.com', 0, 0, '2022-06-23 10:46:14'),
	(8, 'fecb1f75-15b2-4ca6-a105-32a1ba628fce', NULL, 'admin', 'user01@naver.com', 1, 1, '2022-06-23 10:51:12');

INSERT INTO `chat` (`chatMessageNo`, `chatRoomNo`, `sendUserId`, `chatMessage`, `createdAt`, `readOrNot`, `fileName`, `map`) VALUES
	(1, 1, 'user02@naver.com', '하이욤', '2022-05-30 18:23:17', NULL, NULL, NULL),
	(2, 1, 'user02@naver.com', '팔렸나요?', '2022-05-30 18:23:17', NULL, NULL, NULL),
	(3, 1, 'user01@naver.com', '아니요', '2022-05-30 18:23:17', 1, NULL, NULL),
	(4, 2, 'user03@naver.com', '안녕하세요', '2022-05-30 18:28:47', NULL, NULL, NULL),
	(5, 2, 'user03@naver.com', '잘 지내세요?', '2022-05-30 18:28:47', NULL, NULL, NULL),	
	(7, 4, 'user02@naver.com', 'dkssudgktpdy', '2022-05-30 18:36:47', 1, NULL, NULL),
	(10, 5, 'admin', '123', '2022-06-23 06:28:52', NULL, NULL, NULL),
	(11, 5, 'user01@naver.com', '457', '2022-06-23 06:29:01', NULL, NULL, NULL),
	(12, 5, 'admin', '123123', '2022-06-23 06:39:41', NULL, NULL, NULL),
	(13, 5, 'user01@naver.com', '456456', '2022-06-23 06:39:51', NULL, NULL, NULL),
	(14, 5, 'user01@naver.com', '65765', '2022-06-23 06:40:56', NULL, NULL, NULL),
	(15, 5, 'admin', '12312', '2022-06-23 06:40:58', NULL, NULL, NULL),
	(16, 5, 'user01@naver.com', '345', '2022-06-23 06:44:32', NULL, NULL, NULL),
	(17, 5, 'user01@naver.com', '3456546', '2022-06-23 06:44:34', NULL, NULL, NULL),
	(18, 5, 'user01@naver.com', '4657567', '2022-06-23 06:45:14', NULL, NULL, NULL),
	(19, 5, 'admin', '123123', '2022-06-23 06:45:17', NULL, NULL, NULL),
	(20, 5, 'user01@naver.com', '234', '2022-06-23 06:51:42', NULL, NULL, NULL),
	(21, 5, 'user01@naver.com', '456', '2022-06-23 06:53:05', NULL, NULL, NULL),
	(22, 5, 'user01@naver.com', '456', '2022-06-23 06:53:25', NULL, NULL, NULL),
	(23, 5, 'user01@naver.com', '5674567', '2022-06-23 06:54:42', NULL, NULL, NULL),
	(24, 5, 'admin', '124', '2022-06-23 06:54:46', NULL, NULL, NULL),
	(25, 5, 'user01@naver.com', '346', '2022-06-23 06:57:41', NULL, NULL, NULL),
	(26, 5, 'admin', '124124', '2022-06-23 06:57:50', NULL, NULL, NULL),
	(27, 5, 'admin', '3trwetw', '2022-06-23 06:57:54', NULL, NULL, NULL),
	(28, 5, 'user01@naver.com', '57567', '2022-06-23 06:57:57', NULL, NULL, NULL),
	(29, 5, 'admin', '24124', '2022-06-23 06:58:33', NULL, NULL, NULL),
	(30, 5, 'user01@naver.com', '4567567', '2022-06-23 06:58:42', NULL, NULL, NULL),
	(31, 5, 'user01@naver.com', '345654', '2022-06-23 06:58:46', NULL, NULL, NULL),
	(32, 5, 'user01@naver.com', '456', '2022-06-23 06:58:47', NULL, NULL, NULL),
	(33, 5, 'user01@naver.com', '456546', '2022-06-23 06:58:49', NULL, NULL, NULL),
	(34, 5, 'user01@naver.com', '456546', '2022-06-23 06:59:13', NULL, NULL, NULL),
	(35, 5, 'user01@naver.com', '456546', '2022-06-23 06:59:15', NULL, NULL, NULL),
	(36, 5, 'admin', '123123', '2022-06-23 06:59:30', NULL, NULL, NULL),
	(37, 5, 'user01@naver.com', '456', '2022-06-23 07:00:21', NULL, NULL, NULL),
	(38, 5, 'user01@naver.com', '567657', '2022-06-23 07:00:33', NULL, NULL, NULL),
	(39, 5, 'user01@naver.com', '45674767', '2022-06-23 07:03:06', NULL, NULL, NULL),
	(40, 5, 'admin', '123123', '2022-06-23 07:03:36', NULL, NULL, NULL),
	(41, 5, 'user01@naver.com', '7657567', '2022-06-23 07:03:40', NULL, NULL, NULL),
	(42, 5, 'admin', '123123', '2022-06-23 07:03:54', NULL, NULL, NULL),
	(43, 5, 'admin', '123123', '2022-06-23 07:03:55', NULL, NULL, NULL),
	(44, 5, 'user01@naver.com', '4567657', '2022-06-23 07:04:18', NULL, NULL, NULL),
	(45, 5, 'user01@naver.com', '7685678', '2022-06-23 07:04:21', NULL, NULL, NULL),
	(46, 5, 'user01@naver.com', '569769', '2022-06-23 07:04:23', NULL, NULL, NULL),
	(47, 5, 'user01@naver.com', '47567657', '2022-06-23 07:04:34', NULL, NULL, NULL),
	(48, 5, 'user01@naver.com', '74567', '2022-06-23 07:04:39', NULL, NULL, NULL),
	(49, 5, 'user01@naver.com', '654567', '2022-06-23 07:04:44', NULL, NULL, NULL),
	(50, 5, 'user01@naver.com', '4567', '2022-06-23 07:04:46', NULL, NULL, NULL),
	(51, 5, 'admin', '124124', '2022-06-23 07:04:55', NULL, NULL, NULL),
	(52, 5, 'admin', '12412', '2022-06-23 07:04:56', NULL, NULL, NULL),
	(59, 5, 'user01@naver.com', '지도 : 비트로드', '2022-06-23 09:03:41', NULL, NULL, '{"address_name":"서울 마포구 서교동 478-12","category_group_code":"","category_group_name":"","category_name":"문화,예술 > 음악","distance":"","id":"848170709","phone":"070-4205-9040","place_name":"비트로드","place_url":"http://place.map.kakao.com/848170709","road_address_name":"서울 마포구 동교로 107","x":"126.913812427216","y":"37.5548204286513"}'),
	(60, 5, 'user01@naver.com', 'hjkl', '2022-06-23 10:50:00', NULL, NULL, NULL),
	(61, 5, 'admin', 'kkn', '2022-06-23 10:50:54', NULL, NULL, NULL),
	(62, 8, 'admin', '123', '2022-06-23 10:53:59', 1, NULL, NULL),
	(63, 8, 'admin', ',l,l', '2022-06-23 10:54:02', 1, NULL, NULL);

INSERT INTO `board` (`boardNo`, `boardTitle`, `boardDetail`, `boardDate`, `boardPin`, `boardFlag`, `category`, `couponURL`) VALUES
	(5, '장비 보관은 어떻게 하나요?', '보관하기 힘든 캠핑 장비들을 보관신청 해주시면 저희가 직접 픽업부터 창고에 보관까지 서비스 합니다. 제품이 창고에 도착하면 창고 촬영한 사진이 업로드 됩니다. 장비 보관 신청은 장비보관 탭에서 시작하기를 눌러 신청해주세요.', '2022-06-02 11:37:35', 0, 'F', '이용방법', NULL),
	(6, '장비 대여는 어떻게 하나요?', '렌탈 마켓에서 현재 Forrest 에서 대여 중인 상품을 확인 하실 수 있습니다. 제품은 직접 배송해 드리고 대여가 완료 되면 요청하신 장소로 픽업가는 서비스까지 제공하고 있습니다. 최대 4박5일까지 대여 가능합니다.', '2022-06-02 11:37:36', 0, 'F', '이용방법', NULL),
	(7, '중고 거래는 어떻게 이뤄지나요?', '중고 마켓에서 마음에 드는 상품이 있다면 상세보기 페이지에서 판매자와 대화를 할 수 있습니다. 판매자와 거래장소를 정하고 직접 거래 하실 수 있습니다.', '2022-06-02 11:37:37', 0, 'F', '결제관련', NULL),
	(8, '대여 수익은 어떻게 발생하나요?', '내가 보관한 물건을 대여 가능하도록 설정해주세요. 누군가 내가 보관한 물건을 빌려서 쓰면 설정해주신 대여료의 일부를 환급해 드립니다. 대여수익은 내 정보 보기에서 확인이 가능합니다.', '2022-06-02 11:37:38', 0, 'F', '계정', NULL),
	(9, '환불 관련 정책', '장비 보관 혹은 장비 대여시 픽업 서비스가 시작되기 전까지 환불이 가능합니다. 마이페이지 내 보관 / 대여 물품 페이지에서 환불을 신청 할 수 있습니다.', '2022-06-02 11:37:38', 0, 'F', '계정', NULL),
	(10, '패밀리 캠핑! 가정의 달 맞이 이벤트~ 다양한 혜택받고 행복한 추억 만드세요~!(5/30까지)', '', '2022-06-22 18:01:20', 0, 'A', NULL, 'ㅁㄴㅇㄴㅁㅇ'),
	(11, '무조건 드리는 신규회원쿠폰! 5월 신규회원 혜택이 팡팡!', '', '2022-06-22 18:17:45', 0, 'A', NULL, 'ㅁㄴㄹㄴㅁㅇㄹㅇㄴㅁㄹ');

INSERT INTO coupon (couponno, couponname, couponcredate, coupondeldate, discount)
VALUES('1', '[가정의달 5천원쿠폰]', '2022-06-01','2022-12-31', 5000);
INSERT INTO coupon (couponno, couponname, discount)
VALUES('2', '[신규회원 1000원할인쿠폰]',1000 );
INSERT INTO coupon (couponno, couponname, discount)
VALUES('3', '[복귀회원 15%할인쿠폰]', 0.15);
INSERT INTO coupon (couponno, couponname, couponcredate, coupondeldate, discount)
VALUES('4', '[개발자특전 80%할인쿠폰]','2022-06-01','2022-12-31', 0.8);

INSERT INTO owncoupon(userid, couponno, ownCouponCreDate, ownCoupondelDate)
VALUES ('user01@naver.com',1,'2022-06-01','2022-06-30');
INSERT INTO owncoupon(userid, couponno, ownCouponCreDate, ownCoupondelDate)
VALUES ('user01@naver.com',4,'2022-06-01','2022-06-30');
INSERT INTO owncoupon(userid, couponno, ownCouponCreDate, ownCoupondelDate)
VALUES ('user01@naver.com',2,'2022-06-01','2022-06-30');
INSERT INTO owncoupon(userid, couponno, ownCouponCreDate, ownCoupondelDate)
VALUES ('user02@naver.com',2,'2022-06-01','2022-06-30');

INSERT INTO report(reportNo, reportUser, reportedUser, reportOldNo, reportChatroomNo, reportCategory, reportDetail, reportChat, reportCode)
VALUES(NULL, 'admin' , 'user02@naver.com', 2, null, '선정성', '싸가지가 없음', null, 0);
INSERT INTO report(reportNo, reportUser, reportedUser, reportOldNo, reportChatroomNo, reportCategory, reportDetail, reportChat, reportCode)
VALUES(NULL, 'user01@naver.com' , 'user02@naver.com', 2, null, '선정성', '싸가지가 없음', null, 0);
INSERT INTO report(reportNo, reportUser, reportedUser, reportOldNo, reportChatroomNo, reportCategory, reportDetail, reportChat, reportCode)
VALUES(NULL, 'user03@naver.com' , 'user02@naver.com', 2, null, '선정성', '싸가지가 없음', null, 0);

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(1, '52fb9c79-d83b-436a-b5be-5b27a6b13c33', '8af51708-419c-45d4-ba17-ecb5f6600e6c.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(2, '52fb9c79-d83b-436a-b5be-5b27a6b13c33', '0bfbbbb9-02f1-4a32-b4da-1c0a76294c23.jpg', 'product');
		
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(3, '52fb9c79-d83b-436a-b5be-5b27a6b13c33', 'f92bd1df-806c-400f-9369-a45212575896.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(4, '52fb9c79-d83b-436a-b5be-5b27a6b13c33', '20653905-59e6-4d64-a8d2-aec69a23c7ea.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(5, '7374c5cb-e68b-4d65-84a9-013f0d1df57c', 'e54d899f-8a6c-4f49-bb89-8703196bf34b.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(6, '7374c5cb-e68b-4d65-84a9-013f0d1df57c', '3ee8ab40-c8d8-4212-8c3d-d2cfb3d8344b.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(7, '7374c5cb-e68b-4d65-84a9-013f0d1df57c', 'bebcc2e6-191d-4cd7-9324-8d2dcf5c4187.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(8, '7374c5cb-e68b-4d65-84a9-013f0d1df57c', '415c04e6-17d2-4823-b053-8d78513301d6.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(9, '7374c5cb-e68b-4d65-84a9-013f0d1df57c', '1d483d25-e732-44fd-bf5b-b7ec6c83a32f.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(10, 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', '2b35cd5b-2680-4f81-8db9-17090645c038.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(11, 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', '406cee59-20f5-40bc-af9d-de944f4454a1.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(12, 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', '9afc243e-db93-4637-955c-49bd1ff267ec.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(13, 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', '863e6029-7b7b-46c4-af99-afe977cb1a53.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(14, 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', '041724c6-1272-4e41-bc23-daec6c2d68ca.jpg', 'product');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(15, '10', '1b4872a8-df20-4631-ad73-696c6331a323.jpg', 'announce');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(16, '11', 'bf5ec4d3-5f81-49dc-a454-8ebc0010404d.jfif', 'announce');

INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
(17, '6f7022e3-ec24-4b48-96de-acf11aaaf12b', '2d190517-8304-4a34-beab-23e7d3b31af2.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(18, '6f7022e3-ec24-4b48-96de-acf11aaaf12b', 'fde2604b-6261-4cdf-9eeb-688b3fc2e86a.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(19, '6f7022e3-ec24-4b48-96de-acf11aaaf12b', '9ce2c7cd-0268-42f5-8c28-36586c02fee1.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(20, '6f7022e3-ec24-4b48-96de-acf11aaaf12b', 'a0da642a-22d4-4f0e-af8c-8734a58b9006.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(21, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', '9ab1853e-4b67-4bd2-9ee2-b1ff4d7b4398.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(22, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', 'cd2be5c3-42d9-4de5-846e-f32e1144851e.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(23, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', 'c69e4784-dba2-423c-8839-b7f4841674bc.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(24, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', 'a0f7c24a-6236-4c48-b13a-6648966f5ba4.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(25, 'fecb1f75-15b2-4ca6-a105-32a1ba628fce', 'b50b7c22-c12a-4896-9aa8-4b9ac6deed42.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(26, 'fecb1f75-15b2-4ca6-a105-32a1ba628fce', '9106a2b1-e929-45a0-aa98-353ec4b8ff3b.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(27, 'fecb1f75-15b2-4ca6-a105-32a1ba628fce', 'f597e3bd-3997-4970-a91e-25cf2c430350.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(28, 'fecb1f75-15b2-4ca6-a105-32a1ba628fce', '83c8ce90-fb90-47ec-84be-75d818ab06ba.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(29, '48314086-6371-4cbf-8a16-c0495771f046', '098af6ee-f658-4873-877e-3bc8e2095a58.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(30, '48314086-6371-4cbf-8a16-c0495771f046', '6244835a-b366-45dd-84ab-c00a0f2214e1.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(31, '48314086-6371-4cbf-8a16-c0495771f046', '0617a0f7-5b7a-46c8-9838-320de2aa5737.PNG', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(32, '48314086-6371-4cbf-8a16-c0495771f046', 'c4505803-94a9-43dd-a24a-2a24faa3fed2.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(33, 'ea5668d7-ad6e-4f09-8719-474876e6cad4', '2638800c-6535-4ab8-b052-d5c7ee82b180.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(34, 'ea5668d7-ad6e-4f09-8719-474876e6cad4', '53a5147c-44bf-4b35-8580-eba4dfdd3b69.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(35, 'ea5668d7-ad6e-4f09-8719-474876e6cad4', '20b0eff3-f7b9-4ccb-b6c9-7001e1a9abbc.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(36, 'ea5668d7-ad6e-4f09-8719-474876e6cad4', '63eec621-9f76-4c52-b303-8c74691ba29f.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(37, '5643ddbf-60fd-44b1-add6-8d7548357c7b', '99b4ecd1-ebba-42db-b936-f646060a04d1.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(38, '5643ddbf-60fd-44b1-add6-8d7548357c7b', 'cd91d1d2-2596-4787-937c-f655124188d8.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(39, '5643ddbf-60fd-44b1-add6-8d7548357c7b', '98ba452a-6d85-436f-8e5a-56ee54528e51.jpg', 'old');
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES	
(40, '5643ddbf-60fd-44b1-add6-8d7548357c7b', 'c6394234-0d68-4d7a-858e-7530b3167ab7.jpg', 'old');

INSERT INTO imgs (contentsNo,fileName,contentsFlag) VALUES ('l','1.jpg','product');
INSERT INTO imgs (contentsNo,fileName,contentsFlag) VALUES ('l','2.jpg','product');
INSERT INTO imgs (contentsNo,fileName,contentsFlag) VALUES ('l','3.jpg','product');
INSERT INTO imgs (contentsNo,fileName,contentsFlag) VALUES ('l','4.jpg','product');