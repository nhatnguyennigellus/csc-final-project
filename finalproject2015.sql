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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customerId` int(11) NOT NULL AUTO_INCREMENT,
  `address1` varchar(255) NOT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `idCardNumber` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `middleName` varchar(255) DEFAULT NULL,
  `phone1` varchar(15) NOT NULL,
  `phone2` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`customerId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'15/5 Nguyen Du GV','24/232 Nguyen Du Go Vap','brightsunnigellus@gmail.com','Nhat','024643151','Nguyen','Minh','38943026','01212425218'),(2,'15/5 Nguyen Du','','brightsun_nigellus4492@yahoo.com.vn','Nhat','024643151','Nguyen','Minh','01212425218',''),(3,'36 Nguyen Du Quan Go Vap','','pianoxinhxan@yahoo.com','Ha','023186198','Nguyen','Phuong Nhat','0908011963','381564894'),(4,'36/3/6 Nguyen Du GV','','thonie@gmail.com','Thong','025684861','Le','Hoang Minh','0126586198',''),(5,'25/6/7 Nguyen Thai Son GV','','thanh.vo@gmail.com','Thanh','026898451','Vo','Tuan Xuan','0129851893','');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
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
-- Table structure for table `savingaccount`
--

DROP TABLE IF EXISTS `savingaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `savingaccount` (
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
  PRIMARY KEY (`accountNumber`),
  KEY `FK_bfb4xs182a9a7yv842jra1led` (`customerId`),
  KEY `FK_9210tepayycxew2ipvaiwvggu` (`interestRateId`),
  CONSTRAINT `FK_9210tepayycxew2ipvaiwvggu` FOREIGN KEY (`interestRateId`) REFERENCES `savinginterestrate` (`id`),
  CONSTRAINT `FK_bfb4xs182a9a7yv842jra1led` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savingaccount`
--

LOCK TABLES `savingaccount` WRITE;
/*!40000 ALTER TABLE `savingaccount` DISABLE KEYS */;
INSERT INTO `savingaccount` VALUES ('150412000000','NHAT NGUYEN',0,'2015-06-12 22:04:54',0,'\0','2015-04-12 22:04:54','active',1,3),('150412000001','HA NGUYEN',0,NULL,0,'\0','2015-03-12 16:05:44','done',3,1),('150417000000','ABC',1000000,'2015-06-17 09:50:15',6685,'\0','2015-04-17 09:50:15','active',1,3),('150423000000','THONG LE',65000000,'2015-07-23 14:50:23',648219.1780821919,'','2015-04-23 14:50:23','active',4,4),('150424000000','XUAN THANH THU PHAP',68200000,'2015-06-24 10:46:34',455912,'\0','2015-04-24 10:46:34','active',5,3),('150424000001','THANH VO',65200000,NULL,54333.333333333336,'\0','2015-04-24 11:05:25','active',5,1),('150424000002','THONIE',0,'2015-06-24 11:08:54',0,'\0','2015-04-24 11:08:54','active',4,3);
/*!40000 ALTER TABLE `savingaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savinginterestrate`
--

DROP TABLE IF EXISTS `savinginterestrate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `savinginterestrate` (
  `id` int(11) NOT NULL,
  `interestRate` double NOT NULL,
  `period` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savinginterestrate`
--

LOCK TABLES `savinginterestrate` WRITE;
/*!40000 ALTER TABLE `savinginterestrate` DISABLE KEYS */;
INSERT INTO `savinginterestrate` VALUES (1,0.01,0),(2,0.01,1),(3,0.04,2),(4,0.04,3),(5,0.04,6),(6,0.05,12),(7,0.05,24),(8,0.07,48);
/*!40000 ALTER TABLE `savinginterestrate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `date` datetime NOT NULL,
  `state` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `accountNumber` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ocwtf7htxxynnpcosjy3su1gn` (`accountNumber`),
  CONSTRAINT `FK_ocwtf7htxxynnpcosjy3su1gn` FOREIGN KEY (`accountNumber`) REFERENCES `savingaccount` (`accountNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (10,668493,'2015-05-12 20:19:26','Approved','Withdraw Interest','150412000000'),(11,100668493,'2015-04-12 20:31:09','Approved','Withdraw All','150412000000'),(12,150000000,'2015-04-12 20:38:17','Approved','Deposit','150412000000'),(13,150127397,'2015-04-12 20:39:41','Approved','Withdraw All','150412000000'),(14,60000000,'2015-04-12 21:15:58','Approved','Deposit','150412000001'),(15,466667,'2015-04-12 21:32:14','Approved','Withdraw Interest','150412000001'),(17,559366667,'2015-04-12 21:42:26','Approved','Withdraw Balance','150412000001'),(18,60000000,'2015-04-12 21:44:45','Approved','Deposit','150412000001'),(19,61150916,'2015-04-12 21:45:14','Approved','Withdraw All','150412000001'),(20,61500000,'2015-04-12 22:04:46','Rejected','Deposit','150412000000'),(21,61500000,'2015-04-13 11:48:18','Rejected','Withdraw All','150412000000'),(22,65200000,'2015-04-24 11:06:36','Approved','Deposit','150424000001');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `enable` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `FK_dl7g53f7lpmorjc24kx74apx8` (`role`),
  CONSTRAINT `FK_dl7g53f7lpmorjc24kx74apx8` FOREIGN KEY (`role`) REFERENCES `userrole` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('ha','','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','Support'),('nhat','','$2a$10$gwbTCaIR/qE1uYhvEY6GG.bNDQcZuYQX9tkVwaK/aD7ZLPptC.7QC','Admin');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userrole`
--

DROP TABLE IF EXISTS `userrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userrole` (
  `role` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
INSERT INTO `userrole` VALUES ('Admin','Administrator Account'),('Support','Support Account');
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
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
  CONSTRAINT `FK_e6nvbvj33wbif9xo5fc2muvxh` FOREIGN KEY (`username`) REFERENCES `user` (`username`),
  CONSTRAINT `FK_pigyxhmk54l9entsg608jgqxe` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertransaction`
--

LOCK TABLES `usertransaction` WRITE;
/*!40000 ALTER TABLE `usertransaction` DISABLE KEYS */;
INSERT INTO `usertransaction` VALUES (22,'ha'),(10,'nhat'),(11,'nhat'),(12,'nhat'),(13,'nhat'),(14,'nhat'),(15,'nhat'),(17,'nhat'),(18,'nhat'),(19,'nhat'),(20,'nhat'),(21,'nhat'),(22,'nhat');
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

-- Dump completed on 2015-04-24 14:58:54
