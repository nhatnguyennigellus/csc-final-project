CREATE DATABASE  IF NOT EXISTS `finalproject2015` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `finalproject2015`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: finalproject2015
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
  `middleName` varchar(255) NOT NULL,
  `phone1` varchar(15) NOT NULL,
  `phone2` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`customerId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'lac long quan hcm','nha trang','tpvinh@gmail','vinh','225499940','truong','phuc','01226703343','0122666878');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
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
  `dueDate` datetime NOT NULL,
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
INSERT INTO `savingaccount` VALUES ('201504080001','vinh',12500000,'2015-07-09 00:00:00',0,'','2015-04-09 00:00:00','active',1,2);
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
  `period` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savinginterestrate`
--

LOCK TABLES `savinginterestrate` WRITE;
/*!40000 ALTER TABLE `savinginterestrate` DISABLE KEYS */;
INSERT INTO `savinginterestrate` VALUES (1,0.005,0),(2,0.03,3);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
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
INSERT INTO `user` VALUES ('sp','','123','support'),('vinh','','123','admin');
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
INSERT INTO `userrole` VALUES ('admin','admin'),('support','support');
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

-- Dump completed on 2015-04-09 15:38:38
