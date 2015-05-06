CREATE DATABASE  IF NOT EXISTS `finalproject2015` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `finalproject2015`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: finalproject2015
-- ------------------------------------------------------
-- Server version	5.6.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address1` varchar(255) NOT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `idCardNumber` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `middleName` varchar(255) DEFAULT NULL,
  `phone1` varchar(15) NOT NULL,
  `phone2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'15/5 Nguyen Du GV','24/232 Nguyen Du Go Vap','brightsunnigellus@gmail.com','Nhat','024643151','Nguyen','Minh','38943026','01212425218'),(2,'15/5 Nguyen Du','','brightsun_nigellus4492@yahoo.com.vn','Nhat','024643151','Nguyen','Minh','01212425218',''),(3,'36 Nguyen Du Quan Go Vap','','pianoxinhxan@yahoo.com','Ha','023186198','Nguyen','Phuong Nhat','0908011963','381564894'),(4,'36/3/6 Nguyen Du GV','','thonie@gmail.com','Thong','025684861','Le','Hoang Minh','0126586198',''),(5,'25/6/7 Nguyen Thai Son GV','','tvo@gmail.com','Thanh','026898451','Vo','Tuan Xuan','0129851893',''),(6,'56/5 Nguyen Du GV','36 Nguyen Du Go Vap','thinh.tran@gmail.com','Thinh','025123189','Tran','Quang','0123489489','38943018'),(7,'16/5 Nguyen Du GV','','chinhnc@gmail.com','Chinh','025031898','Nguyen','Cong','0124236816','35882849');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savingaccounts`
--

DROP TABLE IF EXISTS `savingaccounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `savingaccounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountNumber` varchar(12) NOT NULL,
  `accountOwner` varchar(255) NOT NULL,
  `balanceAmount` double NOT NULL,
  `dueDate` datetime DEFAULT NULL,
  `interest` double NOT NULL,
  `repeatable` bit(1) NOT NULL,
  `startDate` datetime NOT NULL,
  `state` varchar(255) NOT NULL,
  `customerId` int(11) DEFAULT NULL,
  `interestRateId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bfb4xs182a9a7yv842jra1led` (`customerId`),
  KEY `FK_9210tepayycxew2ipvaiwvggu` (`interestRateId`),
  CONSTRAINT `FK_9210tepayycxew2ipvaiwvggu` FOREIGN KEY (`interestRateId`) REFERENCES `savinginterestrates` (`id`),
  CONSTRAINT `FK_bfb4xs182a9a7yv842jra1led` FOREIGN KEY (`customerId`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savingaccounts`
--

LOCK TABLES `savingaccounts` WRITE;
/*!40000 ALTER TABLE `savingaccounts` DISABLE KEYS */;
INSERT INTO `savingaccounts` VALUES (1,'150424000000','NHAT NGUYEN',50000000,'2015-07-24 17:38:48',498630,'\0','2015-04-24 17:38:48','active',1,4),(2,'150504000000','THONG LE',69000000,'2017-05-04 13:23:56',6909452,'\0','2015-05-04 13:23:56','hold',4,7),(3,'150504000001','THANH VO',0,NULL,0,'\0','2015-05-04 14:34:28','active',5,1),(4,'141207000000','HA NGUYEN',890000000,'2015-05-07 10:00:24',17946301.369863015,'','2014-12-07 10:00:24','active',3,5),(5,'150506000000','QUANG THINH TRAN',0,'2021-05-06 10:24:10',0,'\0','2015-05-06 10:24:10','active',6,27),(6,'150406000001','CONG CHINH',0,'2015-05-06 10:56:32',0,'\0','2015-04-06 10:56:32','done',7,31);
/*!40000 ALTER TABLE `savingaccounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savinginterestrates`
--

DROP TABLE IF EXISTS `savinginterestrates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `savinginterestrates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interestRate` double NOT NULL,
  `period` int(11) NOT NULL,
  `state` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savinginterestrates`
--

LOCK TABLES `savinginterestrates` WRITE;
/*!40000 ALTER TABLE `savinginterestrates` DISABLE KEYS */;
INSERT INTO `savinginterestrates` VALUES (1,0.01,0,'Current'),(2,0.01,1,'Old'),(3,0.04,2,'Old'),(4,0.04,3,'Old'),(5,0.04,6,'Current'),(6,0.05,12,'Old'),(7,0.05,24,'Old'),(8,0.07,48,'Old'),(9,0.03,1,'Old'),(10,0.03,2,'Old'),(11,0.08,72,'Old'),(12,0.02,2,'Old'),(13,0.09,96,'Old'),(18,0.1,96,'Current'),(20,0.06,24,'Old'),(21,0.02,2,'Old'),(22,0.03,3,'Current'),(23,0.06,12,'Current'),(24,0.07,24,'Current'),(25,0.02,1,'Old'),(26,0.08,48,'Current'),(27,0.09,72,'Current'),(28,0.03,2,'Old'),(29,0.02,2,'Current'),(30,0.01,1,'Old'),(31,0.02,1,'Current');
/*!40000 ALTER TABLE `savinginterestrates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `date` datetime NOT NULL,
  `state` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `accountId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tj3mdv5lddxeh41qqwtyy8ny0` (`accountId`),
  CONSTRAINT `FK_tj3mdv5lddxeh41qqwtyy8ny0` FOREIGN KEY (`accountId`) REFERENCES `savingaccounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (8,50000000,'2015-04-24 16:58:53','Approved','Deposit',1),(9,69000000,'2015-05-04 11:55:04','Approved','Deposit',2),(10,890000000,'2015-05-06 09:59:56','Approved','Deposit',4),(11,51200000,'2015-05-06 10:27:52','Rejected','Deposit',5),(12,54200000,'2015-05-06 10:56:18','Approved','Deposit',6),(13,54292066,'2015-05-06 10:58:53','Approved','Withdraw All',6);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userroles`
--

DROP TABLE IF EXISTS `userroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userroles` (
  `role` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userroles`
--

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;
INSERT INTO `userroles` VALUES ('Admin','Administrator Account'),('Support','Support Account');
/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(255) NOT NULL DEFAULT '',
  `enable` bit(1) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `FK_dl7g53f7lpmorjc24kx74apx8` (`role`),
  CONSTRAINT `FK_dl7g53f7lpmorjc24kx74apx8` FOREIGN KEY (`role`) REFERENCES `userroles` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('ha','','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','Support'),('nhat','','$2a$10$gwbTCaIR/qE1uYhvEY6GG.bNDQcZuYQX9tkVwaK/aD7ZLPptC.7QC','Admin'),('sp','','$2a$10$gwbTCaIR/qE1uYhvEY6GG.bNDQcZuYQX9tkVwaK/aD7ZLPptC.7QC','Support'),('tai','','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','Admin'),('tai1','','$2a$10$gwbTCaIR/qE1uYhvEY6GG.bNDQcZuYQX9tkVwaK/aD7ZLPptC.7QC','Support'),('vinh','','$2a$10$gwbTCaIR/qE1uYhvEY6GG.bNDQcZuYQX9tkVwaK/aD7ZLPptC.7QC','Admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertransaction`
--

DROP TABLE IF EXISTS `usertransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertransaction` (
  `transaction_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`transaction_id`,`username`),
  KEY `FK_e6nvbvj33wbif9xo5fc2muvxh` (`username`),
  CONSTRAINT `FK_e6nvbvj33wbif9xo5fc2muvxh` FOREIGN KEY (`username`) REFERENCES `users` (`username`),
  CONSTRAINT `FK_pigyxhmk54l9entsg608jgqxe` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertransaction`
--

LOCK TABLES `usertransaction` WRITE;
/*!40000 ALTER TABLE `usertransaction` DISABLE KEYS */;
INSERT INTO `usertransaction` VALUES (8,'ha'),(9,'ha'),(10,'ha'),(12,'ha'),(13,'ha'),(8,'nhat'),(9,'nhat'),(11,'nhat'),(12,'nhat'),(10,'tai'),(11,'tai1'),(13,'vinh');
/*!40000 ALTER TABLE `usertransaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-06 14:18:09
