SET foreign_key_checks=0;
DROP TABLE board, chat, chatimg, chatroom, coupon, imgs, `old`, oldlike, oldreview, owncoupon, product, rentalreview, report, `transaction`, user, wishlist;



CREATE TABLE user (
   userId      VARCHAR(30)   NOT NULL,
   nickname      VARCHAR(100)    NOT NULL UNIQUE,
   phone VARCHAR(100) NOT NULL UNIQUE, 
   password VARCHAR(100) NOT NULL,
   userName VARCHAR(20) NOT NULL,
   userAddr VARCHAR(100) NOT NULL,
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

ALTER TABLE product ADD FOREIGN KEY(userId) REFERENCES user(userId);

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
   PRIMARY KEY (tranNo)
);

ALTER TABLE transaction ADD FOREIGN KEY(userId) REFERENCES user(userId);
ALTER TABLE transaction ADD FOREIGN KEY(prodNo) REFERENCES product(prodNo);


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
   oldImg VARCHAR(30) NOT NULL,
   oldAddr VARCHAR(100) NOT NULL,
   PRIMARY KEY (oldNo),
   FOREIGN KEY (userId) REFERENCES user(userId)
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

ALTER TABLE wishList ADD FOREIGN KEY(wishedUserId) REFERENCES user(userId);
ALTER TABLE wishList ADD FOREIGN KEY(prodNo) REFERENCES product(prodNo);

CREATE TABLE rentalReview
(
   reviewNo INTEGER NOT NULL AUTO_INCREMENT,
   reviewImg VARCHAR(200) NOT NULL,
   reviewDetail VARCHAR(600) NOT NULL,
   reviewScore INTEGER NOT NULL,
   prodNo VARCHAR(40) NOT NULL,
   userId VARCHAR(30) NOT NULL,
   regDate DATE NOT NULL,
   PRIMARY KEY (reviewNo)
);

ALTER TABLE rentalReview ADD FOREIGN KEY(userId) REFERENCES user(userId);
ALTER TABLE rentalReview ADD FOREIGN KEY(prodNo) REFERENCES product(prodNo);

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

CREATE TABLE `chat` (
  `chatMessageNo` int NOT NULL AUTO_INCREMENT,
  `chatRoomNo` int NOT NULL,
  `sendUserId` varchar(30) NOT NULL,
  `chatMessage` varchar(4000) NOT NULL,
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `readOrNot` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`chatMessageNo`),
  KEY `chatRoomNo` (`chatRoomNo`),
  KEY `sendUserId` (`sendUserId`),
  CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`chatRoomNo`) REFERENCES `chatRoom` (`chatRoomNo`) ON DELETE CASCADE,
  CONSTRAINT `chat_ibfk_2` FOREIGN KEY (`sendUserId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
);

CREATE TABLE `chatImg` (
  `chatImgNo` int NOT NULL AUTO_INCREMENT,
  `chatMessageNo` int NOT NULL,
  `fileName` varchar(1000) NOT NULL,
  PRIMARY KEY (`chatImgNo`),
  KEY `chatMessageNo` (`chatMessageNo`),
  CONSTRAINT `chatimg_ibfk_1` FOREIGN KEY (`chatMessageNo`) REFERENCES `chat` (`chatMessageNo`)
);

CREATE TABLE `board` (
  `boardNo` int NOT NULL AUTO_INCREMENT,
  `boardTitle` varchar(50) NOT NULL DEFAULT '',
  `boardDetail` varchar(400) NOT NULL DEFAULT '',
  `boardDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `boardPin` int DEFAULT 0, 
  `boardFlag` varchar(2) NOT NULL, 
  `category` varchar(10), 
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
	oldLikeNo			INTEGER 	NOT NULL	AUTO_INCREMENT,
	oldNo 			VARCHAR(40)		NOT NULL 	REFERENCES     old(oldNo),
	userId			VARCHAR(30)	NOT NULL 	REFERENCES     user(userId),
	PRIMARY KEY(oldLikeNo)
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
VALUES ('user02@naver.com','user02','user02Phone','2222','user02Name','user02Addr','user',CURDATE(),'own','user02Img',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('user03@naver.com','user03','user03Phone','3333','user03Name','user03Addr','user',CURDATE(),'own','user03Img',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('user04@naver.com','user04','user04Phone','4444','user04Name','user04Addr','user',CURDATE(),'own','user04Img',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('user05@naver.com','user05','user05Phone','5555','user05Name','user05Addr','user',CURDATE(),'own','user05Img',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('user06@naver.com','user06','user06Phone','6666','user06Name','user06Addr','user',CURDATE(),'own','user06Img',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('user10@naver.com','user10','user10Phone','1010','user10Name','user10Addr','leave',CURDATE(),'own','user10Img',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('user20@naver.com','user20','user20Phone','2020','user20Name','user20Addr','restrict',CURDATE(),'own','user20Img',CURDATE(),NULL,NULL,NULL);

INSERT INTO user
VALUES ('captain9697@naver.com','구스범수','010/4114/9697','$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG','박범수','13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)','user', CURDATE(),'own','goosebeomImg.jpg',CURDATE(),NULL,NULL,NULL);

INSERT INTO old
VALUES ('a','user01@naver.com',40000,'야전침대','접이식',CURRENT_TIMESTAMP(),0,'침대',TRUE,'aaa.jpg','삼성동');

INSERT INTO old
VALUES ('b','user02@naver.com',90000,'1인용 텐트','베이지색',CURRENT_TIMESTAMP(),0,'텐트',TRUE,'bbb.jpg','서교동');

INSERT INTO old
VALUES ('c','user03@naver.com',40000,'접이식 의자','대형',CURRENT_TIMESTAMP(),0,'의자',TRUE,'ccc.jpg','서교동');

INSERT INTO old
VALUES ('d','user04@naver.com',30000,'바베큐 그릴','2번 사용',CURRENT_TIMESTAMP(),0,'그릴',TRUE,'ddd.jpg','대홍동');

INSERT INTO old
VALUES ('e','user01@naver.com',5000,'휴대용 버너','가성비',CURRENT_TIMESTAMP(),0,'버너',TRUE,'eee.jpg','염리동');

INSERT INTO old
VALUES ('f','user01@naver.com',20000,'랜턴조명','LED',CURRENT_TIMESTAMP(),0,'조명',TRUE,'fff.jpg','망원동');

INSERT INTO old
VALUES ('g','user01@naver.com',70000,'접이식 테이블','거의새것',CURRENT_TIMESTAMP(),0,'테이블',TRUE,'ggg.jpg','망원동');

INSERT INTO old
VALUES ('h','user01@naver.com',20000,'컵 수저 식기 세트','스탠304',CURRENT_TIMESTAMP(),0,'식기',FALSE,'hhh.jpg','삼성동');

INSERT INTO old
VALUES ('i','user01@naver.com',120000,'석유난로','작년에 샀어요', CURRENT_TIMESTAMP(),0,'난로',FALSE,'iii.jpg','서초동');

INSERT INTO old
VALUES ('j','admin',10000,'아이스박스','10L',CURRENT_TIMESTAMP(),0,'조명',FALSE,'jjj.jpg','서초동');


INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, isRental, rentalCounting, rentalPrice, account, category, divyAddress, prodImg, regDate) VALUES ('a', 100, 30, 12, 'user01@naver.com', '대여중', '특대형 누빔텐트1', 1, '특대형 누빔 텐트 판매합니다. 올해초 신품 구매후 3회사용했습니다. 사진상 마지막 캠핑 장소가 저래서 스커트 부분에 흙먼지 있을수 있습니다. 상태 좋습니다.', 1, 0, 10000, '국민 478102-04-386651', '텐트', '13271 성남시 수정구 신흥2동 한신아파트 5동 502호', '1.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, isRental, rentalCounting, rentalPrice, account, category, divyAddress, prodImg, regDate) VALUES ('b', 100, 30, 12, 'user01@naver.com', '보관중', '특대형 누빔텐트2', 1, '특대형 누빔 텐트 판매합니다. 올해초 신품 구매후 3회사용했습니다. 사진상 마지막 캠핑 장소가 저래서 스커트 부분에 흙먼지 있을수 있습니다. 상태 좋습니다.', 1, 0, 10000, '국민 478102-04-386651', '텐트', '13271 성남시 수정구 신흥2동 한신아파트 5동 502호', '2.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, isRental, rentalCounting, rentalPrice, account, category, divyAddress, prodImg, regDate) VALUES ('c', 100, 30, 12, 'user01@naver.com', '보관중', '특대형 누빔텐트3', 1, '특대형 누빔 텐트 판매합니다. 올해초 신품 구매후 3회사용했습니다. 사진상 마지막 캠핑 장소가 저래서 스커트 부분에 흙먼지 있을수 있습니다. 상태 좋습니다.', 1, 0, 10000, '국민 478102-04-386651', '텐트', '13271 성남시 수정구 신흥2동 한신아파트 5동 502호', '3.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, isRental, rentalCounting, rentalPrice, account, category, divyAddress, prodImg, regDate) VALUES ('d', 40, 30, 15, 'user01@naver.com', '보관중', '캠핑 강염버너 버너', 1, '캠핑하는 동안 잘 썼는데 다른 제품을 구매하게 돼서 공유합니다. 사용감 많지만 고장이나 하자 없어서 사용하는데 전혀 문제 없습니다~', 1, 0, 8000, '국민 478102-04-386651', '버너', '13271 성남시 수정구 신흥2동 한신아파트 5동 502호', '4.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, isRental, rentalCounting, rentalPrice, account, category, divyAddress, prodImg, regDate) VALUES ('e', 30, 30, 60, 'user01@naver.com', '보관중', '알파카TS-77A콤팩트캠핑난로', 1, '알파카TS-77S콤팩트 캠핑난로 심지난로 석유난로 등유난로 기름난로 난로 석유통 자바라 난로전용가방 난로 4가지세트판매 2021년8월제조', 1, 0, 10000, '국민 478102-04-386651', '텐트', '13271 성남시 수정구 신흥2동 한신아파트 5동 502호', '1.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail,isRental, rentalCounting, rentalPrice, account, category, divyAddress, prodImg, regDate) VALUES ('f', 60, 30, 20, 'user02@naver.com', '보관중', '퀸나 휴대용전기그릴', 1, '테스트로1회사용함 깨끗하게 닦아뒀으니바로사용가능 비싼거 새거 사지마시고 좋은거 저렴하게 이용하세요. ', 1, 0, 5000, '국민 478102-04-386651', '그릴', '18125 경기 오산시 가수1로 13 (가수주공아파트) 102동 1201호 ', '2.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, category, divyAddress, prodImg, regDate) VALUES ('g', 20, 60, 10, 'user03@naver.com', '보관중', '마추픽추 릴렉스 체어', 1, '네이버 캠핑의자 랭킹 상위의 마추픽추 릴렉스체어입니다. 캠핑이나 낚시 등에 활용 가능할 것 같네요 색상은 버건디입니다. 3회밖에 사용안해서 상태는 제거와 크게 다름 없습니다.', '의자', '01849 서울 노원구 공릉로 111 (애지빌라) A동 103호', '3.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, category, divyAddress, prodImg, regDate) VALUES ('h', 73, 45, 10, 'user04@naver.com', '입고중', '캠핑테이블 캠핑박스 확장형', 1, '확장형우드테이블+운반상자6호', '테이블', '성남시 중원구 은행2동 주공아파트 120동 1001호', '4.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, category, divyAddress, prodImg, regDate) VALUES ('i', 73, 45, 10, 'user01@naver.com', '출고완료', '캠핑테이블 캠핑박스 확장형1', 1, '확장형우드테이블+운반상자6호', '테이블', '성남시 중원구 은행2동 주공아파트 120동 1001호', '1.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, category, divyAddress, prodImg, regDate) VALUES ('j', 73, 45, 10, 'user01@naver.com', '출고완료', '캠핑테이블 캠핑박스 확장형2', 1, '확장형우드테이블+운반상자6호', '테이블', '성남시 중원구 은행2동 주공아파트 120동 1001호', '2.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, category, divyAddress, prodImg, regDate) VALUES ('1', 73, 45, 10, 'user01@naver.com', '대여중', '캠핑테이블 캠핑박스 확장형2', 1, '확장형우드테이블+운반상자6호', '테이블', '성남시 중원구 은행2동 주공아파트 120동 1001호', '3.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, category, divyAddress, prodImg, regDate) VALUES ('2', 73, 45, 10, 'user01@naver.com', '배송중', '캠핑테이블 캠핑박스 확장형2', 1, '확장형우드테이블+운반상자6호', '테이블', '성남시 중원구 은행2동 주공아파트 120동 1001호', '1.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, category, divyAddress, prodImg, regDate) VALUES ('3', 73, 45, 10, 'user01@naver.com', '물품대여승인신청중', '캠핑테이블 캠핑박스 확장형2', 1, '확장형우드테이블+운반상자6호', '테이블', '성남시 중원구 은행2동 주공아파트 120동 1001호', '2.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, category, divyAddress, prodImg, regDate) VALUES ('4', 73, 45, 10, 'user01@naver.com', '대여중', '캠핑테이블 캠핑박스 확장형2', 1, '확장형우드테이블+운반상자6호', '테이블', '성남시 중원구 은행2동 주공아파트 120동 1001호', '3.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, category, divyAddress, prodImg, regDate) VALUES ('5', 73, 45, 10, 'user01@naver.com', '배송중', '캠핑테이블 캠핑박스 확장형2', 1, '확장형우드테이블+운반상자6호', '테이블', '성남시 중원구 은행2동 주공아파트 120동 1001호', '4.jpg', CURRENT_TIMESTAMP());

INSERT INTO product  (prodNo, width, length, height, userId, prodCondition, prodName, prodQuantity, prodDetail, category, divyAddress, prodImg, regDate) VALUES ('6', 73, 45, 10, 'user01@naver.com', '물품대여승인신청중', '캠핑테이블 캠핑박스 확장형2', 1, '확장형우드테이블+운반상자6호', '테이블', '성남시 중원구 은행2동 주공아파트 120동 1001호', '1.jpg', CURRENT_TIMESTAMP());

INSERT INTO transaction(tranNo, userId, prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice)
values ('aa', 'user01@naver.com', 'a', '신흥동1', '신흥동2', '빨리줘', date_add(curdate(), interval 1 day), date_add(curdate(), interval 1+30 day), 30, 'imp-1002', current_timestamp(), '이니시스', '010-4114-9697', '박범수', '특대형 누빔텐트1', '1.jpg', 20000, 2000, 18000);

INSERT INTO transaction(tranNo, userId, prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice)
values ('bb', 'user01@naver.com', 'b', '신흥동1', '신흥동2', '빨리줘', date_add(curdate(), interval 1 day), date_add(curdate(), interval 1+30 day), 30, 'imp-1002', current_timestamp(), '이니시스', '010-4114-9697', '박범수', '특대형 누빔텐트2', '1.jpg', 20000, 2000, 18000);

INSERT INTO transaction(tranNo, userId, prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice)
values ('cc', 'user01@naver.com', 'c', '신흥동1', '신흥동2', '빨리줘', date_add(curdate(), interval 1 day), date_add(curdate(), interval 1+30 day), 30, 'imp-1002', current_timestamp(), '이니시스', '010-4114-9697', '박범수', '특대형 누빔텐트3', '1.jpg', 20000, 2000, 18000);

INSERT INTO transaction(tranNo, userId, prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice)
values ('dd', 'user01@naver.com', 'd', '신흥동1', '신흥동2', '빨리줘', date_add(curdate(), interval 1 day), date_add(curdate(), interval 1+30 day), 30, 'imp-1002', current_timestamp(), '이니시스', '010-4114-9697', '박범수', '캠핑 강염버너 버너', '1.jpg', 20000, 2000, 18000);

INSERT INTO transaction(tranNo, userId, prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice)
values ('ee', 'user01@naver.com', 'e', '신흥동1', '신흥동2', '빨리줘', date_add(curdate(), interval 1 day), date_add(curdate(), interval 1+30 day), 30, 'imp-1002', current_timestamp(), '이니시스', '010-4114-9697', '박범수', '알파카TS-77A콤팩트캠핑난로', '1.jpg', 20000, 2000, 18000);

INSERT INTO transaction(tranNo, userId, prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice)
values ('ff', 'user02@naver.com', 'f', '강남구1', '강남구2', '빨리줘', date_add(curdate(), interval 1 day), date_add(curdate(), interval 1+60 day), 60, 'imp-1003', current_timestamp(), '이니시스', '010-8283-5621', '김범수', '퀸나 휴대용전기그릴', '2.jpg', 25000, 1000, 24000);

INSERT INTO transaction(tranNo, userId, prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice)
values ('gg', 'user03@naver.com', 'g', '비트캠프1', '비트캠프2', '빨리줘', date_add(curdate(), interval 1 day), date_add(curdate(), interval 1+90 day), 90, 'imp-1004', current_timestamp(), '이니시스', '010-2091-9728', '이상민', '마추픽추 릴렉스 체어', '3.jpg', 30000, 0, 30000);

INSERT INTO transaction(tranNo, userId, prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice)
values ('hh', 'user04@naver.com', 'h', '복정동1', '복정동2', '빨리줘', date_add(curdate(), interval 1 day), date_add(curdate(), interval 1+120 day), 120, 'imp-1005', current_timestamp(), '이니시스', '010-8294-1923', '김명선', '캠핑테이블 캠핑박스 확장형', '4.jpg', 40000, 0, 40000);

INSERT INTO transaction(tranNo, userId, prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice)
values ('ii', 'user01@naver.com', 'i', '복정동1', '복정동2', '빨리줘', date_add(curdate(), interval 1 day), date_add(curdate(), interval 1+120 day), 120, 'imp-1005', current_timestamp(), '이니시스', '010-8294-1923', '김명선', '캠핑테이블 캠핑박스 확장형1', '4.jpg', 40000, 0, 40000);

INSERT INTO transaction(tranNo, userId, prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice)
values ('jj', 'user01@naver.com', 'j', '복정동1', '복정동2', '빨리줘', date_add(curdate(), interval 1 day), date_add(curdate(), interval 1+120 day), 120, 'imp-1005', current_timestamp(), '이니시스', '010-8294-1923', '김명선', '캠핑테이블 캠핑박스 확장형2', '4.jpg', 40000, 0, 40000);

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('kk', 'user02@naver.com', 'k', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '1.jpg', '10000', '1000', '9000' );

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('kkg', 'user01@naver.com', 'b', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '2.jpg', '10000', '1000', '9000' );

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('kkgg', 'user01@naver.com', 'c', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '3.jpg', '10000', '1000', '9000' );

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('kkag', 'user01@naver.com', 'd', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '4.jpg', '10000', '1000', '9000' );

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('kkasg', 'user01@naver.com', 'd', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '1.jpg', '10000', '1000', '9000' );

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('2kk4asg', 'user05@naver.com', '1', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '2.jpg', '10000', '1000', '9000' );

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('kk3a6sg', 'user04@naver.com', '2', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '3.jpg', '10000', '1000', '9000' );

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('kk4a7sg', 'user03@naver.com', '3', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '4.jpg', '10000', '1000', '9000' );

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('12kkasg', 'user02@naver.com', '4', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '1.jpg', '10000', '1000', '9000' );

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('k4k3asg', 'user07@naver.com', '5', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '2.jpg', '10000', '1000', '9000' );

INSERT INTO transaction  (tranNo , userId , prodNo, divyRequest, divyAddress, pickupAddress, startDate, endDate, period, tranCode, paymentNo, paymentDate, paymentWay, receiverPhone, receiverName, prodName, prodImg, originPrice, discountPrice, resultPrice ) VALUES ('kk54asg', 'user06@naver.com', '6', '서울특별시 비트캠프', '부산광역시 해운대구', '빨리줘', '2022-06-15', '2022-06-18', '4', TRUE, '20030', '2022-05-30 20:19:15', '계좌이체', '01087836060', '홍길동', '특대형 누빔텐트' , '1.jpg', '10000', '1000', '9000' );

INSERT INTO wishlist (wishlistNo, prodNo, wishedUserId) VALUES (NULL, '1', 'user05@naver.com');

INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, '1.jpg', '상품상세설명입니다', '3', '1', 'user03@naver.com', '20210525');

INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'a', 'user01@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '4', 'b', 'user02@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '1', 'c', 'user03@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '가족이 구매했는데 만족하네요~가격은 사악하지만 좋습니다~', '2', 'd', 'user04@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '5', 'e', 'user05@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '5', 'f', 'user01@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'a', 'user03@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'b', 'user02@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '가족이 구매했는데 만족하네요~가격은 사악하지만 좋습니다~', '3', 'c', 'user03@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'd', 'user04@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '아직 사용전인데 이미 많은 리뷰만 보더라도 기대가 됩니다', '3', 'e', 'user05@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'f', 'user03@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기', '3', 'g', 'user03@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'a', 'user01@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'b', 'user02@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'c', 'user03@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'd', 'user04@naver.com', '20210525');

INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'e3a2fad8-188d-45f8-bec1-881f185e090d#', 'user02@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '가족이 구매했는데 만족하네요~가격은 사악하지만 좋습니다~', '3', 'e3a2fad8-188d-45f8-bec1-881f185e090d#', 'user03@naver.com', '20210525');
INSERT INTO rentalReview (reviewNo, reviewImg, reviewDetail,reviewScore, prodNo, userId, regDate ) VALUES (NULL, 'tent.jpg', '아직 사용전인데 이미 많은 리뷰만 보더라도 기대가 됩니다 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', '3', 'e3a2fad8-188d-45f8-bec1-881f185e090d#', 'user04@naver.com', '20210525');


INSERT INTO `chatroom` (`chatRoomNo`, `oldNo`, `prodNo`, `inquireUserId`, `ownerUserId`, `inquireUserExit`, `ownerUserExit`, `createdAt`) VALUES
	(1, 'a', NULL, 'user02@naver.com', 'user01@naver.com', 1, 1, '2022-05-30 18:21:00'),
	(2, 'a', NULL, 'user03@naver.com', 'user01@naver.com', 1, 1, '2022-05-30 18:21:42'),
	(3, 'a', NULL, 'user04@naver.com', 'user01@naver.com', 1, 1, '2022-05-30 18:22:29'),
	(4, NULL, 'a', 'user02@naver.com', 'user01@naver.com', 1, 1, '2022-05-30 18:31:32');

INSERT INTO `chat` (`chatMessageNo`, `chatRoomNo`, `sendUserId`, `chatMessage`, `createdAt`, `readOrNot`) VALUES
	(1, 1, 'user02@naver.com', '하이욤', '2022-05-30 18:23:17', NULL),
	(2, 1, 'user02@naver.com', '팔렸나요?', '2022-05-30 18:23:17', NULL),
	(3, 1, 'user01@naver.com', '아니요', '2022-05-30 18:23:17', 1),
	(4, 2, 'user03@naver.com', '안녕하세요', '2022-05-30 18:28:47', 1),
	(5, 2, 'user03@naver.com', '잘 지내세요?', '2022-05-30 18:28:47', 1),
	(6, 3, 'user04@naver.com', ' HI', '2022-05-30 18:32:54', 1),
	(7, 4, 'user02@naver.com', 'dkssudgktpdy', '2022-05-30 18:36:47', 1);

INSERT INTO board( boardTitle, boardDetail, boardDate, boardFlag) VALUES('공지사항1', '어쩌고저쩌고1', '2022-06-02 11:37:35', 'A');
INSERT INTO board( boardTitle, boardDetail, boardDate, boardFlag) VALUES('공지사항2', '어쩌고저쩌고2', '2022-06-02 11:37:36', 'A');
INSERT INTO board( boardTitle, boardDetail, boardDate, boardFlag) VALUES('공지사항3', '어쩌고저쩌고3', '2022-06-02 11:37:37', 'A');
INSERT INTO board( boardTitle, boardDetail, boardDate, boardFlag) VALUES('공지사항4', '어쩌고저쩌고4', '2022-06-02 11:37:38', 'A');
INSERT INTO board( boardTitle, boardDetail, boardDate, boardFlag, category) VALUES('장비 보관은 어떻게 하나요?', '보관하기 힘든 캠핑 장비들을 보관신청 해주시면 저희가 직접 픽업부터 창고에 보관까지 서비스 합니다. 제품이 창고에 도착하면 창고 촬영한 사진이 업로드 됩니다. 장비 보관 신청은 장비보관 탭에서 시작하기를 눌러 신청해주세요.', '2022-06-02 11:37:35', 'F', '이용방법');
INSERT INTO board( boardTitle, boardDetail, boardDate, boardFlag, category) VALUES('장비 대여는 어떻게 하나요?', '렌탈 마켓에서 현재 Forrest 에서 대여 중인 상품을 확인 하실 수 있습니다. 제품은 직접 배송해 드리고 대여가 완료 되면 요청하신 장소로 픽업가는 서비스까지 제공하고 있습니다. 최대 4박5일까지 대여 가능합니다.', '2022-06-02 11:37:36', 'F', '이용방법');
INSERT INTO board( boardTitle, boardDetail, boardDate, boardFlag, category) VALUES('중고 거래는 어떻게 이뤄지나요?', '중고 마켓에서 마음에 드는 상품이 있다면 상세보기 페이지에서 판매자와 대화를 할 수 있습니다. 판매자와 거래장소를 정하고 직접 거래 하실 수 있습니다.', '2022-06-02 11:37:37', 'F', '결제관련');
INSERT INTO board( boardTitle, boardDetail, boardDate, boardFlag, category) VALUES('대여 수익은 어떻게 발생하나요?', '내가 보관한 물건을 대여 가능하도록 설정해주세요. 누군가 내가 보관한 물건을 빌려서 쓰면 설정해주신 대여료의 일부를 환급해 드립니다. 대여수익은 내 정보 보기에서 확인이 가능합니다.', '2022-06-02 11:37:38', 'F', '계정');
INSERT INTO board( boardTitle, boardDetail, boardDate, boardFlag, category) VALUES('환불 관련 정책', '장비 보관 혹은 장비 대여시 픽업 서비스가 시작되기 전까지 환불이 가능합니다. 마이페이지 내 보관 / 대여 물품 페이지에서 환불을 신청 할 수 있습니다.', '2022-06-02 11:37:38', 'F', '계정');

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
VALUES ('user01@naver.com',2,'2022-06-01','2022-06-30');
INSERT INTO owncoupon(userid, couponno, ownCouponCreDate, ownCoupondelDate)
VALUES ('user02@naver.com',2,'2022-06-01','2022-06-30');

INSERT INTO report(reportNo, reportUser, reportedUser, reportOldNo, reportChatroomNo, reportCategory, reportDetail, reportChat, reportCode)
VALUES(NULL, 'admin' , 'user02@naver.com', 2, null, '선정성', '싸가지가 없음', null, 0);

INSERT INTO report(reportNo, reportUser, reportedUser, reportOldNo, reportChatroomNo, reportCategory, reportDetail, reportChat, reportCode)
VALUES(NULL, 'admin' , 'user03@naver.com', 3, null, '선정성',  '가격 안깎아줌', null, 0);

INSERT INTO report(reportNo, reportUser, reportedUser, reportOldNo, reportChatroomNo, reportCategory, reportDetail, reportChat, reportCode)
VALUES(NULL, 'admin' , 'user04@naver.com', 4, null, '광고성',  '욕함', null, 0);

INSERT INTO report(reportNo, reportUser, reportedUser, reportOldNo, reportChatroomNo, reportCategory,reportDetail, reportChat, reportCode)
VALUES(NULL, 'admin' , 'user05@naver.com', 5, null, '선정성',  '너무 못생김', null, 0);

INSERT INTO report(reportNo, reportUser, reportedUser, reportOldNo, reportChatroomNo, reportCategory, reportDetail, reportChat, reportCode)
VALUES(NULL, 'admin' , 'user06@naver.com', 6, null, '선정성',  '그냥', null, 0);


INSERT INTO oldReview(oldReviewNo, reviewUserId, reviewedUserId, oldNo, reviewDetail, userRate)
VALUES(NULL, 'admin', 'user01@naver.com', 'a', '굿매너', 5);

INSERT INTO oldReview(oldReviewNo, reviewUserId, reviewedUserId, oldNo, reviewDetail, userRate)
VALUES(NULL, 'admin', 'user02@naver.com', 'b', '개매너', 1);

INSERT INTO oldReview(oldReviewNo, reviewUserId, reviewedUserId, oldNo, reviewDetail, userRate)
VALUES(NULL, 'admin', 'user03@naver.com', 'c', '살짝 불친절', 3);

INSERT INTO oldReview(oldReviewNo, reviewUserId, reviewedUserId, oldNo, reviewDetail, userRate)
VALUES(NULL, 'admin', 'user04@naver.com', 'd', '물건값 깎아줌', 4);

INSERT INTO oldReview(oldReviewNo, reviewUserId, reviewedUserId, oldNo, reviewDetail, userRate)
VALUES(NULL, 'admin', 'user01@naver.com', 'e', '굿매너', 5);

INSERT INTO oldReview(oldReviewNo, reviewUserId, reviewedUserId, oldNo, reviewDetail, userRate)
VALUES(NULL, 'admin', 'user01@naver.com', 'f', '굿매너', 5);

INSERT INTO oldReview(oldReviewNo, reviewUserId, reviewedUserId, oldNo, reviewDetail, userRate)
VALUES(NULL, 'admin', 'user01@naver.com', 'g', '굿매너', 5);

INSERT INTO oldReview(oldReviewNo, reviewUserId, reviewedUserId, oldNo, reviewDetail, userRate)
VALUES(NULL, 'admin', 'user01@naver.com', 'h', '굿매너', 5);

INSERT INTO oldReview(oldReviewNo, reviewUserId, reviewedUserId, oldNo, reviewDetail, userRate)
VALUES(NULL, 'admin', 'user01@naver.com', 'i', '굿매너', 5);

INSERT INTO oldReview(oldReviewNo, reviewUserId, reviewedUserId, oldNo, reviewDetail, userRate)
VALUES(NULL, 'admin', 'admin', 'j', '개매너', 1);


