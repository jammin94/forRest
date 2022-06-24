-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.5.9-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- forrest 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `forrest` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `forrest`;

-- 테이블 forrest.board 구조 내보내기
CREATE TABLE IF NOT EXISTS `board` (
  `boardNo` int(11) NOT NULL AUTO_INCREMENT,
  `boardTitle` varchar(500) NOT NULL DEFAULT '',
  `boardDetail` varchar(5000) NOT NULL DEFAULT '',
  `boardDate` datetime NOT NULL DEFAULT current_timestamp(),
  `boardPin` int(11) DEFAULT 0,
  `boardFlag` varchar(2) NOT NULL,
  `category` varchar(10) DEFAULT NULL,
  `couponURL` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`boardNo`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.board:~7 rows (대략적) 내보내기
DELETE FROM `board`;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` (`boardNo`, `boardTitle`, `boardDetail`, `boardDate`, `boardPin`, `boardFlag`, `category`, `couponURL`) VALUES
	(5, '장비 보관은 어떻게 하나요?', '보관하기 힘든 캠핑 장비들을 보관신청 해주시면 저희가 직접 픽업부터 창고에 보관까지 서비스 합니다. 제품이 창고에 도착하면 창고 촬영한 사진이 업로드 됩니다. 장비 보관 신청은 장비보관 탭에서 시작하기를 눌러 신청해주세요.', '2022-06-02 11:37:35', 0, 'F', '이용방법', NULL),
	(6, '장비 대여는 어떻게 하나요?', '렌탈 마켓에서 현재 Forrest 에서 대여 중인 상품을 확인 하실 수 있습니다. 제품은 직접 배송해 드리고 대여가 완료 되면 요청하신 장소로 픽업가는 서비스까지 제공하고 있습니다. 최대 4박5일까지 대여 가능합니다.', '2022-06-02 11:37:36', 0, 'F', '이용방법', NULL),
	(7, '중고 거래는 어떻게 이뤄지나요?', '중고 마켓에서 마음에 드는 상품이 있다면 상세보기 페이지에서 판매자와 대화를 할 수 있습니다. 판매자와 거래장소를 정하고 직접 거래 하실 수 있습니다.', '2022-06-02 11:37:37', 0, 'F', '결제관련', NULL),
	(8, '대여 수익은 어떻게 발생하나요?', '내가 보관한 물건을 대여 가능하도록 설정해주세요. 누군가 내가 보관한 물건을 빌려서 쓰면 설정해주신 대여료의 일부를 환급해 드립니다. 대여수익은 내 정보 보기에서 확인이 가능합니다.', '2022-06-02 11:37:38', 0, 'F', '계정', NULL),
	(9, '환불 관련 정책', '장비 보관 혹은 장비 대여시 픽업 서비스가 시작되기 전까지 환불이 가능합니다. 마이페이지 내 보관 / 대여 물품 페이지에서 환불을 신청 할 수 있습니다.', '2022-06-02 11:37:38', 0, 'F', '계정', NULL),
	(10, '패밀리 캠핑! 가정의 달 맞이 이벤트~ 다양한 혜택받고 행복한 추억 만드세요~!(5/30까지)', '', '2022-06-22 18:01:20', 0, 'A', NULL, 'ㅁㄴㅇㄴㅁㅇ'),
	(11, '무조건 드리는 신규회원쿠폰! 5월 신규회원 혜택이 팡팡!', '', '2022-06-22 18:17:45', 0, 'A', NULL, 'ㅁㄴㄹㄴㅁㅇㄹㅇㄴㅁㄹ');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;

-- 테이블 forrest.chat 구조 내보내기
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
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.chat:~5 rows (대략적) 내보내기
DELETE FROM `chat`;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` (`chatMessageNo`, `chatRoomNo`, `sendUserId`, `chatMessage`, `createdAt`, `readOrNot`, `fileName`, `map`) VALUES
	(62, 8, 'kedee2001@naver.com', '123', '2022-06-23 10:53:59', NULL, NULL, NULL),
	(63, 8, 'kedee2001@naver.com', ',l,l', '2022-06-23 10:54:02', NULL, NULL, NULL),
	(64, 9, 'user01@naver.com', 'ㅇ3ㅇ??', '2022-06-24 05:23:11', 1, NULL, NULL),
	(65, 8, 'user01@naver.com', '에에', '2022-06-24 05:23:34', 1, NULL, NULL),
	(66, 9, 'user01@naver.com', '흠', '2022-06-24 05:23:59', 1, NULL, NULL);
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;

-- 테이블 forrest.chatroom 구조 내보내기
CREATE TABLE IF NOT EXISTS `chatroom` (
  `chatRoomNo` int(11) NOT NULL AUTO_INCREMENT,
  `oldNo` varchar(40) DEFAULT NULL,
  `prodNo` varchar(40) DEFAULT NULL,
  `inquireUserId` varchar(30) NOT NULL,
  `ownerUserId` varchar(30) NOT NULL,
  `inquireUserExit` tinyint(1) DEFAULT 0,
  `ownerUserExit` tinyint(1) DEFAULT 0,
  `createdAt` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`chatRoomNo`),
  KEY `oldNo` (`oldNo`),
  KEY `prodNo` (`prodNo`),
  KEY `inquireUserId` (`inquireUserId`),
  KEY `ownerUserId` (`ownerUserId`),
  CONSTRAINT `chatroom_ibfk_1` FOREIGN KEY (`oldNo`) REFERENCES `old` (`oldNo`) ON DELETE CASCADE,
  CONSTRAINT `chatroom_ibfk_2` FOREIGN KEY (`prodNo`) REFERENCES `product` (`prodNo`) ON DELETE CASCADE,
  CONSTRAINT `chatroom_ibfk_3` FOREIGN KEY (`inquireUserId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `chatroom_ibfk_4` FOREIGN KEY (`ownerUserId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.chatroom:~3 rows (대략적) 내보내기
DELETE FROM `chatroom`;
/*!40000 ALTER TABLE `chatroom` DISABLE KEYS */;
INSERT INTO `chatroom` (`chatRoomNo`, `oldNo`, `prodNo`, `inquireUserId`, `ownerUserId`, `inquireUserExit`, `ownerUserExit`, `createdAt`) VALUES
	(7, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', NULL, 'kedee2001@naver.com', 'user01@naver.com', 0, 0, '2022-06-23 10:46:14'),
	(8, 'fecb1f75-15b2-4ca6-a105-32a1ba628fce', NULL, 'kedee2001@naver.com', 'user01@naver.com', 1, 1, '2022-06-23 10:51:12'),
	(9, 'ea5668d7-ad6e-4f09-8719-474876e6cad4', NULL, 'user01@naver.com', 'admin', 1, 1, '2022-06-24 05:22:57');
/*!40000 ALTER TABLE `chatroom` ENABLE KEYS */;

-- 테이블 forrest.coupon 구조 내보내기
CREATE TABLE IF NOT EXISTS `coupon` (
  `couponNo` varchar(50) NOT NULL,
  `couponName` varchar(50) NOT NULL,
  `couponCreDate` datetime DEFAULT NULL,
  `couponDelDate` datetime DEFAULT NULL,
  `discount` double NOT NULL,
  PRIMARY KEY (`couponNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.coupon:~4 rows (대략적) 내보내기
DELETE FROM `coupon`;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` (`couponNo`, `couponName`, `couponCreDate`, `couponDelDate`, `discount`) VALUES
	('1', '[가정의달 5천원쿠폰]', '2022-06-01 00:00:00', '2022-12-31 00:00:00', 5000),
	('2', '[신규회원 1000원할인쿠폰]', NULL, NULL, 1000),
	('3', '[복귀회원 15%할인쿠폰]', NULL, NULL, 0.15),
	('4', '[개발자특전 80%할인쿠폰]', '2022-06-01 00:00:00', '2022-12-31 00:00:00', 0.8);
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;

-- 테이블 forrest.imgs 구조 내보내기
CREATE TABLE IF NOT EXISTS `imgs` (
  `imgNo` int(11) NOT NULL AUTO_INCREMENT,
  `contentsNo` varchar(80) DEFAULT NULL,
  `fileName` varchar(100) DEFAULT NULL,
  `contentsFlag` varchar(20) NOT NULL,
  PRIMARY KEY (`imgNo`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.imgs:~64 rows (대략적) 내보내기
DELETE FROM `imgs`;
/*!40000 ALTER TABLE `imgs` DISABLE KEYS */;
INSERT INTO `imgs` (`imgNo`, `contentsNo`, `fileName`, `contentsFlag`) VALUES
	(1, '52fb9c79-d83b-436a-b5be-5b27a6b13c33', '8af51708-419c-45d4-ba17-ecb5f6600e6c.jpg', 'product'),
	(2, '52fb9c79-d83b-436a-b5be-5b27a6b13c33', '0bfbbbb9-02f1-4a32-b4da-1c0a76294c23.jpg', 'product'),
	(3, '52fb9c79-d83b-436a-b5be-5b27a6b13c33', 'f92bd1df-806c-400f-9369-a45212575896.jpg', 'product'),
	(4, '52fb9c79-d83b-436a-b5be-5b27a6b13c33', '20653905-59e6-4d64-a8d2-aec69a23c7ea.jpg', 'product'),
	(5, '7374c5cb-e68b-4d65-84a9-013f0d1df57c', 'e54d899f-8a6c-4f49-bb89-8703196bf34b.jpg', 'product'),
	(6, '7374c5cb-e68b-4d65-84a9-013f0d1df57c', '3ee8ab40-c8d8-4212-8c3d-d2cfb3d8344b.jpg', 'product'),
	(7, '7374c5cb-e68b-4d65-84a9-013f0d1df57c', 'bebcc2e6-191d-4cd7-9324-8d2dcf5c4187.jpg', 'product'),
	(8, '7374c5cb-e68b-4d65-84a9-013f0d1df57c', '415c04e6-17d2-4823-b053-8d78513301d6.jpg', 'product'),
	(9, '7374c5cb-e68b-4d65-84a9-013f0d1df57c', '1d483d25-e732-44fd-bf5b-b7ec6c83a32f.jpg', 'product'),
	(10, 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', '2b35cd5b-2680-4f81-8db9-17090645c038.jpg', 'product'),
	(11, 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', '406cee59-20f5-40bc-af9d-de944f4454a1.jpg', 'product'),
	(12, 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', '9afc243e-db93-4637-955c-49bd1ff267ec.jpg', 'product'),
	(13, 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', '863e6029-7b7b-46c4-af99-afe977cb1a53.jpg', 'product'),
	(14, 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', '041724c6-1272-4e41-bc23-daec6c2d68ca.jpg', 'product'),
	(15, '10', '1b4872a8-df20-4631-ad73-696c6331a323.jpg', 'announce'),
	(16, '11', 'bf5ec4d3-5f81-49dc-a454-8ebc0010404d.jfif', 'announce'),
	(17, '6f7022e3-ec24-4b48-96de-acf11aaaf12b', '2d190517-8304-4a34-beab-23e7d3b31af2.jpg', 'old'),
	(18, '6f7022e3-ec24-4b48-96de-acf11aaaf12b', 'fde2604b-6261-4cdf-9eeb-688b3fc2e86a.jpg', 'old'),
	(19, '6f7022e3-ec24-4b48-96de-acf11aaaf12b', '9ce2c7cd-0268-42f5-8c28-36586c02fee1.jpg', 'old'),
	(20, '6f7022e3-ec24-4b48-96de-acf11aaaf12b', 'a0da642a-22d4-4f0e-af8c-8734a58b9006.jpg', 'old'),
	(21, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', '9ab1853e-4b67-4bd2-9ee2-b1ff4d7b4398.jpg', 'old'),
	(22, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', 'cd2be5c3-42d9-4de5-846e-f32e1144851e.jpg', 'old'),
	(23, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', 'c69e4784-dba2-423c-8839-b7f4841674bc.jpg', 'old'),
	(24, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', 'a0f7c24a-6236-4c48-b13a-6648966f5ba4.jpg', 'old'),
	(25, 'fecb1f75-15b2-4ca6-a105-32a1ba628fce', 'b50b7c22-c12a-4896-9aa8-4b9ac6deed42.jpg', 'old'),
	(26, 'fecb1f75-15b2-4ca6-a105-32a1ba628fce', '9106a2b1-e929-45a0-aa98-353ec4b8ff3b.jpg', 'old'),
	(27, 'fecb1f75-15b2-4ca6-a105-32a1ba628fce', 'f597e3bd-3997-4970-a91e-25cf2c430350.jpg', 'old'),
	(28, 'fecb1f75-15b2-4ca6-a105-32a1ba628fce', '83c8ce90-fb90-47ec-84be-75d818ab06ba.jpg', 'old'),
	(29, '48314086-6371-4cbf-8a16-c0495771f046', '098af6ee-f658-4873-877e-3bc8e2095a58.jpg', 'old'),
	(30, '48314086-6371-4cbf-8a16-c0495771f046', '6244835a-b366-45dd-84ab-c00a0f2214e1.jpg', 'old'),
	(31, '48314086-6371-4cbf-8a16-c0495771f046', '0617a0f7-5b7a-46c8-9838-320de2aa5737.PNG', 'old'),
	(32, '48314086-6371-4cbf-8a16-c0495771f046', 'c4505803-94a9-43dd-a24a-2a24faa3fed2.jpg', 'old'),
	(33, 'ea5668d7-ad6e-4f09-8719-474876e6cad4', '2638800c-6535-4ab8-b052-d5c7ee82b180.jpg', 'old'),
	(34, 'ea5668d7-ad6e-4f09-8719-474876e6cad4', '53a5147c-44bf-4b35-8580-eba4dfdd3b69.jpg', 'old'),
	(35, 'ea5668d7-ad6e-4f09-8719-474876e6cad4', '20b0eff3-f7b9-4ccb-b6c9-7001e1a9abbc.jpg', 'old'),
	(36, 'ea5668d7-ad6e-4f09-8719-474876e6cad4', '63eec621-9f76-4c52-b303-8c74691ba29f.jpg', 'old'),
	(37, '5643ddbf-60fd-44b1-add6-8d7548357c7b', '99b4ecd1-ebba-42db-b936-f646060a04d1.jpg', 'old'),
	(38, '5643ddbf-60fd-44b1-add6-8d7548357c7b', 'cd91d1d2-2596-4787-937c-f655124188d8.jpg', 'old'),
	(39, '5643ddbf-60fd-44b1-add6-8d7548357c7b', '98ba452a-6d85-436f-8e5a-56ee54528e51.jpg', 'old'),
	(40, '5643ddbf-60fd-44b1-add6-8d7548357c7b', 'c6394234-0d68-4d7a-858e-7530b3167ab7.jpg', 'old'),
	(41, 'l', '1.jpg', 'product'),
	(42, 'l', '2.jpg', 'product'),
	(43, 'l', '3.jpg', 'product'),
	(44, 'l', '4.jpg', 'product'),
	(47, 'df9d73df-8b33-4fd3-99b9-b2e43784ca0a', 'c42c8bc7-1e4b-4b15-a6b3-67bb0fabe77f.gif', 'old'),
	(48, 'df9d73df-8b33-4fd3-99b9-b2e43784ca0a', '893861ee-9199-4375-90b7-4232e71a2571.jpg', 'old'),
	(49, 'df9d73df-8b33-4fd3-99b9-b2e43784ca0a', '816e009e-ab46-484d-971a-682ae3e4759c.webp', 'old'),
	(50, 'df9d73df-8b33-4fd3-99b9-b2e43784ca0a', '661a5d11-9e71-4b32-9ed5-7be9e5d0191f.jpg', 'old'),
	(51, 'f215ff49-27a8-49ba-ac01-9ef27bfb02fe', 'ede4e20d-5403-4df4-a65d-fef902425730.webp', 'old'),
	(52, 'f215ff49-27a8-49ba-ac01-9ef27bfb02fe', '27fd13af-e215-4fe5-b59a-829e984c4964.webp', 'old'),
	(53, 'f215ff49-27a8-49ba-ac01-9ef27bfb02fe', 'fc979d10-e787-401a-ba08-971d1936597a.webp', 'old'),
	(54, 'f215ff49-27a8-49ba-ac01-9ef27bfb02fe', 'd16e9cce-8331-4792-9007-6fe7addcadcd.webp', 'old'),
	(55, '3d91e986-7a27-4c99-9d02-c7a7b56a2f6c', '2112c4de-b722-4761-9062-a8e0e1a458c7.webp', 'old'),
	(56, '3d91e986-7a27-4c99-9d02-c7a7b56a2f6c', 'd76c2f85-1bee-47e8-b5c0-a82b35f1a360.jpg', 'old'),
	(57, '3d91e986-7a27-4c99-9d02-c7a7b56a2f6c', '8d389324-a8fa-48d5-bb4b-b5b246f8e784.jpg', 'old'),
	(58, '3d91e986-7a27-4c99-9d02-c7a7b56a2f6c', '50029eb2-9fb5-4c4f-9dd2-27131566e992.jpg', 'old'),
	(59, 'ef4e0923-71a7-47ac-bc7e-f133da484fcf', 'de527e4a-15af-4a4b-9c32-9bab5221061e.webp', 'old'),
	(60, 'ef4e0923-71a7-47ac-bc7e-f133da484fcf', 'd363dbdb-1e6c-4f7f-9acb-b60421ce1443.webp', 'old'),
	(61, 'ef4e0923-71a7-47ac-bc7e-f133da484fcf', '72db73eb-b1ce-4dd8-a827-c6d54327aacc.jpg', 'old'),
	(62, 'ef4e0923-71a7-47ac-bc7e-f133da484fcf', '75564de8-5cca-4488-ac7e-24070394654e.jpg', 'old'),
	(63, '9113b67b-70eb-425b-ba35-36b4838b74b6', '32fef995-b239-48a2-9ad1-eb313b30bc98.webp', 'old'),
	(64, '9113b67b-70eb-425b-ba35-36b4838b74b6', '560c96bb-7455-4fad-b6d9-763ae354f8c1.webp', 'old'),
	(65, '9113b67b-70eb-425b-ba35-36b4838b74b6', '886ee2dd-d003-4046-a5a0-b563949314aa.webp', 'old'),
	(66, '9113b67b-70eb-425b-ba35-36b4838b74b6', '8797cf99-90bf-47a4-a9f4-80f332449a46.jpg', 'old');
/*!40000 ALTER TABLE `imgs` ENABLE KEYS */;

-- 테이블 forrest.old 구조 내보내기
CREATE TABLE IF NOT EXISTS `old` (
  `oldNo` varchar(40) NOT NULL,
  `userId` varchar(30) NOT NULL,
  `oldPrice` int(11) NOT NULL,
  `oldTitle` varchar(100) NOT NULL,
  `oldDetail` varchar(8000) NOT NULL,
  `oldDate` datetime NOT NULL DEFAULT current_timestamp(),
  `oldView` int(11) NOT NULL,
  `category` varchar(10) NOT NULL,
  `oldState` tinyint(1) NOT NULL DEFAULT 1,
  `oldImg` varchar(60) NOT NULL,
  `oldAddr` varchar(100) NOT NULL,
  PRIMARY KEY (`oldNo`),
  KEY `userId` (`userId`),
  CONSTRAINT `old_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.old:~10 rows (대략적) 내보내기
DELETE FROM `old`;
/*!40000 ALTER TABLE `old` DISABLE KEYS */;
INSERT INTO `old` (`oldNo`, `userId`, `oldPrice`, `oldTitle`, `oldDetail`, `oldDate`, `oldView`, `category`, `oldState`, `oldImg`, `oldAddr`) VALUES
	('3d91e986-7a27-4c99-9d02-c7a7b56a2f6c', 'kedee2001@naver.com', 50000, '스텐다드 접이식 바베큐그릴 숯불 캠핑 야외용 BBQ', '한번 쓰고 팝니다. 쿨거래만 받아요..!', '2022-06-24 14:33:11', 1, '그릴', 1, '2112c4de-b722-4761-9062-a8e0e1a458c7.webp', '죽전동'),
	('48314086-6371-4cbf-8a16-c0495771f046', 'user01@naver.com', 5000, '분위기 있는 랜턴', '싸게 가져가세요~', '2022-06-23 09:56:47', 1, '조명', 1, '098af6ee-f658-4873-877e-3bc8e2095a58.jpg', '삼성동'),
	('5643ddbf-60fd-44b1-add6-8d7548357c7b', 'user01@naver.com', 9000, '고기굽기 좋은 테이블', '사용감이 있어 저렴하게 올립니다. 기스 살짝 있어요.', '2022-06-23 10:02:08', 3, '테이블', 1, '99b4ecd1-ebba-42db-b936-f646060a04d1.jpg', '삼성동'),
	('9113b67b-70eb-425b-ba35-36b4838b74b6', 'kedee2001@naver.com', 20000, '캠핑식기 세트 22P 캠핑용품 접시세트', '20000원에 팝니다..!', '2022-06-24 14:37:33', 1, '식기', 1, '32fef995-b239-48a2-9ad1-eb313b30bc98.webp', '동천동'),
	('a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', 'user01@naver.com', 20000, '감성 조명', '싸게 가져가세요', '2022-06-23 09:50:06', 1, '조명', 1, '9ab1853e-4b67-4bd2-9ee2-b1ff4d7b4398.jpg', '청담동'),
	('df9d73df-8b33-4fd3-99b9-b2e43784ca0a', 'kedee2001@naver.com', 20000, '다니고 캠핑 전구', '30000원에 샀는데 눈물을 머금고 20000원에 올립니다ㅠㅠ\r\n이것만 있으면 밤에 안 어둡고 갬성넘치는 캠핑을 할 수 있습니다 ㅎㅎ\r\n쿨거래 시 네고 해드립니다!', '2022-06-24 14:26:02', 2, '조명', 1, 'c42c8bc7-1e4b-4b15-a6b3-67bb0fabe77f.gif', '상산동'),
	('ea5668d7-ad6e-4f09-8719-474876e6cad4', 'admin', 60000, '원목 테이블', '튼튼해요 퀄리티 정말 좋아요.', '2022-06-23 09:59:53', 3, '테이블', 1, '2638800c-6535-4ab8-b052-d5c7ee82b180.jpg', '역삼동'),
	('ef4e0923-71a7-47ac-bc7e-f133da484fcf', 'kedee2001@naver.com', 50000, '포레스트우드 구이바다 BBQ그릴 풀세트M', '기름도 별로 안튀었고, 한번 청소했어서 완전 깨끗해요!\r\n더 좋은걸 사서 그냥 팝니다 ㅎㅎㅎ!', '2022-06-24 14:35:13', 1, '버너', 1, 'de527e4a-15af-4a4b-9c32-9bab5221061e.webp', '죽전동'),
	('f215ff49-27a8-49ba-ac01-9ef27bfb02fe', 'kedee2001@naver.com', 30000, '경량체어 감성 로우 커밋체어 등산의자', '경량 체어라서 엄청 가벼워요. 앉아서 불멍때리면 행복이 여기에!\r\n사용감 거의 없어서 거의 새거에요. 얻어가세요!~', '2022-06-24 14:29:09', 1, '의자', 1, 'ede4e20d-5403-4df4-a65d-fef902425730.webp', '상산동'),
	('fecb1f75-15b2-4ca6-a105-32a1ba628fce', 'user01@naver.com', 90000, '한번 사용한 텐트', '실물 예뻐요~', '2022-06-23 09:55:13', 0, '텐트', 1, 'b50b7c22-c12a-4896-9aa8-4b9ac6deed42.jpg', '연희동');
/*!40000 ALTER TABLE `old` ENABLE KEYS */;

-- 테이블 forrest.oldlike 구조 내보내기
CREATE TABLE IF NOT EXISTS `oldlike` (
  `oldLikeNo` int(11) NOT NULL AUTO_INCREMENT,
  `oldNo` varchar(40) NOT NULL,
  `userId` varchar(30) NOT NULL,
  PRIMARY KEY (`oldLikeNo`),
  KEY `oldNo` (`oldNo`),
  KEY `userId` (`userId`),
  CONSTRAINT `oldlike_ibfk_1` FOREIGN KEY (`oldNo`) REFERENCES `old` (`oldNo`) ON DELETE CASCADE,
  CONSTRAINT `oldlike_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.oldlike:~1 rows (대략적) 내보내기
DELETE FROM `oldlike`;
/*!40000 ALTER TABLE `oldlike` DISABLE KEYS */;
INSERT INTO `oldlike` (`oldLikeNo`, `oldNo`, `userId`) VALUES
	(4, 'a4bde7a7-c0dc-4734-9aa6-1c2cd769b6d8', 'kedee2001@naver.com');
/*!40000 ALTER TABLE `oldlike` ENABLE KEYS */;

-- 테이블 forrest.oldreview 구조 내보내기
CREATE TABLE IF NOT EXISTS `oldreview` (
  `oldReviewNo` int(11) NOT NULL AUTO_INCREMENT,
  `reviewUserId` varchar(30) NOT NULL,
  `reviewedUserId` varchar(30) NOT NULL,
  `oldNo` varchar(40) NOT NULL,
  `reviewDetail` varchar(100) DEFAULT NULL,
  `userRate` double DEFAULT NULL,
  `reviewDate` date NOT NULL DEFAULT curdate(),
  PRIMARY KEY (`oldReviewNo`),
  KEY `reviewUserId` (`reviewUserId`),
  KEY `reviewedUserId` (`reviewedUserId`),
  KEY `oldNo` (`oldNo`),
  CONSTRAINT `oldreview_ibfk_1` FOREIGN KEY (`reviewUserId`) REFERENCES `user` (`userId`),
  CONSTRAINT `oldreview_ibfk_2` FOREIGN KEY (`reviewedUserId`) REFERENCES `user` (`userId`),
  CONSTRAINT `oldreview_ibfk_3` FOREIGN KEY (`oldNo`) REFERENCES `old` (`oldNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.oldreview:~0 rows (대략적) 내보내기
DELETE FROM `oldreview`;
/*!40000 ALTER TABLE `oldreview` DISABLE KEYS */;
/*!40000 ALTER TABLE `oldreview` ENABLE KEYS */;

-- 테이블 forrest.owncoupon 구조 내보내기
CREATE TABLE IF NOT EXISTS `owncoupon` (
  `ownCouponNo` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(30) NOT NULL,
  `couponNo` varchar(50) NOT NULL,
  `ownCouponCreDate` datetime NOT NULL,
  `ownCouponDelDate` datetime NOT NULL,
  PRIMARY KEY (`ownCouponNo`),
  KEY `userId` (`userId`),
  KEY `couponNo` (`couponNo`),
  CONSTRAINT `owncoupon_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `owncoupon_ibfk_2` FOREIGN KEY (`couponNo`) REFERENCES `coupon` (`couponNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.owncoupon:~8 rows (대략적) 내보내기
DELETE FROM `owncoupon`;
/*!40000 ALTER TABLE `owncoupon` DISABLE KEYS */;
INSERT INTO `owncoupon` (`ownCouponNo`, `userId`, `couponNo`, `ownCouponCreDate`, `ownCouponDelDate`) VALUES
	(1, 'user01@naver.com', '1', '2022-06-01 00:00:00', '2022-06-30 00:00:00'),
	(2, 'user01@naver.com', '4', '2022-06-01 00:00:00', '2022-06-30 00:00:00'),
	(3, 'user01@naver.com', '2', '2022-06-01 00:00:00', '2022-06-30 00:00:00'),
	(4, 'user02@naver.com', '2', '2022-06-01 00:00:00', '2022-06-30 00:00:00'),
	(5, 'user01@naver.com', '2', '2022-06-24 12:19:21', '2022-07-24 12:19:21'),
	(6, 'qwerty5266@naver.com', '2', '2022-06-24 13:22:08', '2022-07-24 13:22:08'),
	(7, 'user03@naver.com', '2', '2022-06-24 13:23:00', '2022-07-24 13:23:00'),
	(8, 'admin', '2', '2022-06-24 14:14:15', '2022-07-24 14:14:15');
/*!40000 ALTER TABLE `owncoupon` ENABLE KEYS */;

-- 테이블 forrest.product 구조 내보내기
CREATE TABLE IF NOT EXISTS `product` (
  `prodNo` varchar(40) NOT NULL,
  `width` int(11) NOT NULL,
  `length` int(11) NOT NULL,
  `height` int(11) NOT NULL,
  `userId` varchar(30) NOT NULL,
  `prodCondition` varchar(30) NOT NULL DEFAULT '물품보관승인신청중',
  `prodName` varchar(40) NOT NULL,
  `prodQuantity` int(11) NOT NULL,
  `prodDetail` varchar(600) NOT NULL,
  `isRental` tinyint(1) NOT NULL DEFAULT 0,
  `rentalCounting` int(11) DEFAULT NULL,
  `rentalPrice` int(11) DEFAULT NULL,
  `account` varchar(30) DEFAULT NULL,
  `category` varchar(10) NOT NULL,
  `divyAddress` varchar(100) NOT NULL,
  `prodImg` varchar(100) NOT NULL,
  `recentImg` varchar(100) DEFAULT NULL,
  `regDate` datetime NOT NULL,
  PRIMARY KEY (`prodNo`),
  KEY `userId` (`userId`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.product:~3 rows (대략적) 내보내기
DELETE FROM `product`;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`prodNo`, `width`, `length`, `height`, `userId`, `prodCondition`, `prodName`, `prodQuantity`, `prodDetail`, `isRental`, `rentalCounting`, `rentalPrice`, `account`, `category`, `divyAddress`, `prodImg`, `recentImg`, `regDate`) VALUES
	('52fb9c79-d83b-436a-b5be-5b27a6b13c33', 10, 10, 2, 'captain9697@naver.com', '보관중', '바베큐,숯불 그릴/캠핑 그릴', 1, '캠핑용 가정용 그릴이고 한번사용후 보관했는데 숯자국은 남네요\r\n중형으로 간단히 구워먹기좋아요 3-4인용이고 가방이 깨끗하지않아요\r\n숯사용후 남은것도 드려요  이사짐정리중입니다', 1, 0, 5000, '국민/47810204386651', '그릴', '06035/서울 강남구 도산대로 402-2/울집/ (신사동)', '8af51708-419c-45d4-ba17-ecb5f6600e6c.jpg', NULL, '2022-06-22 14:11:24'),
	('7374c5cb-e68b-4d65-84a9-013f0d1df57c', 10, 10, 2, 'captain9697@naver.com', '보관중', '라탄 전등갓 줄전구 조명', 1, '[손품]이라는 곳에서 구매한 라탄 전등 갓 조명이예요\r\n20구 이고, 건전지형 입니다 (USB형 나오기전 구매)\r\n저는 캠핑때 사용하려고 샀었고, 1회 사용 후 보관만 해둔 상태 입니다\r\n\r\n샀던 곳 상세설명 첨부 참고해주세요\r\n더스트백에 넣어 드립니다\r\n라탄 갓은 전구에 일일히 따로 끼워야 하는데 20개 다 하려면 시간 꽤 걸려요 그래서 끼운채 있고 그대로 드려요!', 1, 0, 3000, '국민/47810204386651', '조명', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', 'e54d899f-8a6c-4f49-bb89-8703196bf34b.jpg', NULL, '2022-06-22 14:15:26'),
	('b37a6c88-8ec7-4d39-9e6b-8474e3376fce', 10, 10, 2, 'captain9697@naver.com', '보관중', '캠핑의자,캠핑체어,스노우라인,유아의자', 1, '야외에서 쓰던거라 사용감 있어요.\r\n그래도 튼튼하고 사용하기 좋아요\r\n\r\n민트색의자는 주니어의자 라고해서 당근구입하고 봤더니 어린이가 쓰는거라 안쓰고 보관만했어요. 꺼내봤더니 많이 지저분해졌네요.이것도 튼튼해요~\r\n스노우라인꺼 사시는분 필요하시면 드려요~', 1, 0, 3000, '국민/47810204386651', '의자', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '2b35cd5b-2680-4f81-8db9-17090645c038.jpg', NULL, '2022-06-22 14:19:07');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- 테이블 forrest.rentalreview 구조 내보내기
CREATE TABLE IF NOT EXISTS `rentalreview` (
  `reviewNo` int(11) NOT NULL AUTO_INCREMENT,
  `reviewImg` varchar(200) NOT NULL,
  `reviewDetail` varchar(600) NOT NULL,
  `reviewScore` int(11) NOT NULL,
  `prodNo` varchar(40) NOT NULL,
  `userId` varchar(30) NOT NULL,
  `regDate` date NOT NULL,
  PRIMARY KEY (`reviewNo`),
  KEY `userId` (`userId`),
  KEY `prodNo` (`prodNo`),
  CONSTRAINT `rentalreview_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `rentalreview_ibfk_2` FOREIGN KEY (`prodNo`) REFERENCES `product` (`prodNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.rentalreview:~2 rows (대략적) 내보내기
DELETE FROM `rentalreview`;
/*!40000 ALTER TABLE `rentalreview` DISABLE KEYS */;
INSERT INTO `rentalreview` (`reviewNo`, `reviewImg`, `reviewDetail`, `reviewScore`, `prodNo`, `userId`, `regDate`) VALUES
	(1, 'tent.jpg', '너무너무 힘들게 배송받은 오두막 텐트집근처에 왔다가 반송되고 다시 배송오다가반송되기전에 겨우 택배기사님과 통화해서어렵게 받았네요. 탠트에 발자국 2개가찍혀있었지만 그냥 씁니다.텐트는 너무 이쁘고 좋습니닺', 3, 'e3a2fad8-188d-45f8-bec1-881f185e090d#', 'user02@naver.com', '2021-05-25'),
	(2, 'tent.jpg', '가족이 구매했는데 만족하네요~가격은 사악하지만 좋습니다~', 3, 'e3a2fad8-188d-45f8-bec1-881f185e090d#', 'user03@naver.com', '2021-05-25');
/*!40000 ALTER TABLE `rentalreview` ENABLE KEYS */;

-- 테이블 forrest.report 구조 내보내기
CREATE TABLE IF NOT EXISTS `report` (
  `reportNo` int(11) NOT NULL AUTO_INCREMENT,
  `reportUser` varchar(30) NOT NULL,
  `reportedUser` varchar(30) NOT NULL,
  `reportOldNo` varchar(40) NOT NULL,
  `reportChatroomNo` int(11) DEFAULT NULL,
  `reportCategory` varchar(20) DEFAULT NULL,
  `reportDate` datetime NOT NULL DEFAULT current_timestamp(),
  `reportDetail` varchar(100) DEFAULT NULL,
  `reportChat` varchar(4000) DEFAULT NULL,
  `reportCode` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`reportNo`),
  KEY `reportUser` (`reportUser`),
  KEY `reportedUser` (`reportedUser`),
  KEY `reportOldNo` (`reportOldNo`),
  CONSTRAINT `report_ibfk_1` FOREIGN KEY (`reportUser`) REFERENCES `user` (`userId`),
  CONSTRAINT `report_ibfk_2` FOREIGN KEY (`reportedUser`) REFERENCES `user` (`userId`),
  CONSTRAINT `report_ibfk_3` FOREIGN KEY (`reportOldNo`) REFERENCES `old` (`oldNo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.report:~0 rows (대략적) 내보내기
DELETE FROM `report`;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;

-- 테이블 forrest.transaction 구조 내보내기
CREATE TABLE IF NOT EXISTS `transaction` (
  `tranNo` varchar(100) NOT NULL,
  `userId` varchar(30) NOT NULL,
  `prodNo` varchar(40) NOT NULL,
  `divyRequest` varchar(100) DEFAULT NULL,
  `divyAddress` varchar(100) NOT NULL,
  `pickupAddress` varchar(100) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `period` int(11) NOT NULL,
  `tranCode` tinyint(1) NOT NULL DEFAULT 0,
  `paymentNo` varchar(30) NOT NULL,
  `paymentDate` datetime DEFAULT NULL,
  `paymentWay` varchar(50) DEFAULT NULL,
  `receiverPhone` varchar(50) DEFAULT NULL,
  `receiverName` varchar(50) DEFAULT NULL,
  `prodName` varchar(40) NOT NULL,
  `prodImg` varchar(100) NOT NULL,
  `originPrice` int(11) NOT NULL,
  `discountPrice` int(11) DEFAULT NULL,
  `resultPrice` int(11) DEFAULT NULL,
  `reviewDone` tinyint(1) NOT NULL DEFAULT 0,
  `complete` tinyint(1) NOT NULL DEFAULT 0,
  `cancelComplete` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`tranNo`),
  KEY `userId` (`userId`),
  KEY `prodNo` (`prodNo`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`prodNo`) REFERENCES `product` (`prodNo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.transaction:~3 rows (대략적) 내보내기
DELETE FROM `transaction`;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` (`tranNo`, `userId`, `prodNo`, `divyRequest`, `divyAddress`, `pickupAddress`, `startDate`, `endDate`, `period`, `tranCode`, `paymentNo`, `paymentDate`, `paymentWay`, `receiverPhone`, `receiverName`, `prodName`, `prodImg`, `originPrice`, `discountPrice`, `resultPrice`, `reviewDone`, `complete`, `cancelComplete`) VALUES
	('193f067d-3eda-4a07-a68b-e90824f642cf', 'captain9697@naver.com', 'b37a6c88-8ec7-4d39-9e6b-8474e3376fce', NULL, '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '2022-06-23', '2022-10-21', 120, 0, 'imp_930121942719', '2022-06-22 14:19:07', NULL, '010/4114/9697', '박범수', '캠핑의자,캠핑체어,스노우라인,유아의자,어린이캠핑', '2b35cd5b-2680-4f81-8db9-17090645c038.jpg', 960, 0, 960, 0, 0, 0),
	('22679442-ce59-41f8-8191-c5a26ee25ca3', 'captain9697@naver.com', '7374c5cb-e68b-4d65-84a9-013f0d1df57c', NULL, '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '2022-06-23', '2022-07-23', 30, 0, 'imp_380900755748', '2022-06-22 14:15:26', NULL, '010/4114/9697', '박범수', '라탄 전등갓 줄전구 조명', 'e54d899f-8a6c-4f49-bb89-8703196bf34b.jpg', 240, 0, 240, 0, 0, 0),
	('7338e575-9b26-46fe-920f-9004098a3ef4', 'captain9697@naver.com', '52fb9c79-d83b-436a-b5be-5b27a6b13c33', NULL, '06035/서울 강남구 도산대로 402-2/울집/ (신사동)', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', '2022-06-23', '2022-08-22', 60, 0, 'imp_254658714863', '2022-06-22 14:11:24', NULL, '010/4114/9697', '박범수', '바베큐,숯불 그릴/캠핑 그릴', '8af51708-419c-45d4-ba17-ecb5f6600e6c.jpg', 480, 0, 480, 0, 0, 0);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;

-- 테이블 forrest.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `userId` varchar(30) NOT NULL,
  `nickname` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `userName` varchar(20) NOT NULL,
  `userAddr` varchar(100) NOT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'user',
  `joinDate` datetime NOT NULL,
  `joinPath` varchar(10) NOT NULL,
  `userImg` varchar(100) DEFAULT NULL,
  `recentDate` datetime NOT NULL DEFAULT current_timestamp(),
  `pushToken` varchar(100) DEFAULT NULL,
  `leaveApplyDate` datetime DEFAULT NULL,
  `leaveDate` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `nickname` (`nickname`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.user:~10 rows (대략적) 내보내기
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`userId`, `nickname`, `phone`, `password`, `userName`, `userAddr`, `role`, `joinDate`, `joinPath`, `userImg`, `recentDate`, `pushToken`, `leaveApplyDate`, `leaveDate`) VALUES
	('admin', '어드민', '01011111111', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG', '어드민', 'adminAddr', 'admin', '2022-06-24 00:00:00', 'own', 'adminImg.jpg', '2022-06-24 14:14:15', NULL, NULL, NULL),
	('captain9697@naver.com', '구스범수', '010/4114/9697', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG', '박범수', '13271/경기 성남시 수정구 산성대로 341/5동 502호/ (신흥동, 한신아파트)', 'user', '2022-06-24 00:00:00', 'own', 'goosebeomImg.jpg', '2022-06-24 00:00:00', NULL, NULL, NULL),
	('jj3033@naver.com', '금붕어회', '01093512557', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG', '정태영', '02582/서울 동대문구 신설동 98-49/201/ (신설동, 신설동주상복합)', 'user', '2022-06-23 00:09:41', 'own', '37b5c554-62db-4fbb-bbfb-0d0ff63d415b.jpg', '2022-06-23 00:09:53', NULL, NULL, NULL),
	('kedee2001@naver.com', '쥄쥄', '01093257941', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG', '김재민', '16872/경기 용인시 수지구 대지로 49/ (죽전동, 죽전퍼스트하임)', 'user', '2022-06-24 13:27:18', 'own', '가지.jpg', '2022-06-24 14:22:35', NULL, NULL, NULL),
	('qwerty5266@naver.com', '띵띵', '01093995266', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG', '김명선', '14999/경기 시흥시 군자동 810/204-1402/ (군자동, 한여울 초등학교)', 'user', '2022-06-23 01:19:41', 'own', 'chacha.JPG', '2022-06-24 13:22:08', NULL, NULL, NULL),
	('sanstory12rt@naver.com', '과자조아', '010/8783/6065', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG', '형산', '18112/경기 오산시 세교동 587/303-4201/ (세교동, 오산세교주상1블록)', 'user', '2022-06-24 00:00:00', 'own', 'iu.jpg', '2022-06-24 00:00:00', NULL, NULL, NULL),
	('tkdals4534@naver.com', '이어폰폴펜', '01033294534', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG', '이상민', '13121/경기 성남시 수정구 창곡동 501/3603동 1112호/ (창곡동, 성남위례 LH36단지)', 'user', '2022-06-22 09:45:34', 'own', NULL, '2022-06-22 09:45:34', NULL, NULL, NULL),
	('user01@naver.com', '김유정', '01012345678', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG', '김유정', '13494/경기 성남시 분당구 삼평동 681/26-805/ (삼평동, 에이치스퀘어 엔동)', 'user', '2022-06-24 00:00:00', 'own', 'user01Img.jpg', '2022-06-24 12:19:21', NULL, NULL, NULL),
	('user02@naver.com', '고영희좋아', '01023456781', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG', '고영희', '04981/서울 광진구 구의동 164-5/101-150/ (구의동, 구의야구공원)', 'user', '2022-06-24 00:00:00', 'own', '아이유.jpg', '2022-06-24 00:00:00', NULL, NULL, NULL),
	('user03@naver.com', '나도좋아', '01034567891', '$2a$10$jX/xb.arDJYDOqokfAJNkeg3ISGU1vTHG1t7lA2TxR7xbgTYVb6RG', '나좋아', '06134/서울 강남구 강남대로94길 20 삼오빌딩 9층/ (역삼동, 비트캠프 강남센터)', 'user', '2022-06-24 00:00:00', 'own', '고영희.jpg', '2022-06-24 13:24:15', NULL, NULL, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 테이블 forrest.wishlist 구조 내보내기
CREATE TABLE IF NOT EXISTS `wishlist` (
  `wishlistNo` int(11) NOT NULL AUTO_INCREMENT,
  `prodNo` varchar(40) NOT NULL,
  `wishedUserId` varchar(30) NOT NULL,
  `period` int(11) DEFAULT NULL,
  PRIMARY KEY (`wishlistNo`),
  KEY `wishedUserId` (`wishedUserId`),
  KEY `prodNo` (`prodNo`),
  CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`wishedUserId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`prodNo`) REFERENCES `product` (`prodNo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 forrest.wishlist:~0 rows (대략적) 내보내기
DELETE FROM `wishlist`;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
