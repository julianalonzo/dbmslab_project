-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: vetclinic
-- ------------------------------------------------------
-- Server version	5.6.35

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
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment` (
  `apptid` int(11) NOT NULL,
  `date` varchar(45) NOT NULL,
  `time` varchar(45) NOT NULL,
  `room_no` varchar(45) NOT NULL,
  `vetid` int(11) NOT NULL,
  `petid` int(11) NOT NULL,
  PRIMARY KEY (`apptid`),
  KEY `vetid_idx` (`vetid`),
  KEY `petid_idx` (`petid`),
  CONSTRAINT `petid` FOREIGN KEY (`petid`) REFERENCES `pet` (`petid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `vetid` FOREIGN KEY (`vetid`) REFERENCES `veterinarian` (`vetid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,'12-07-2017','9:00 ','101',1,2),(2,'12-07-2017','13:00','101',1,3),(3,'12-07-2017','15:00','102',2,4),(4,'12-09-2017','10:00','103',3,2),(5,'12-09-2017','14:00','102',3,1);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `owner` (
  `ownerid` int(11) NOT NULL,
  `owner_last_name` varchar(45) NOT NULL,
  `owner_first_name` varchar(45) NOT NULL,
  `owner_gender` varchar(45) NOT NULL,
  `owner_contact_no` varchar(45) NOT NULL,
  PRIMARY KEY (`ownerid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` VALUES (1,'Kent','Clark','M','09859485768'),(2,'Prince','Diana','F','09758674756'),(3,'Wayne','Bruce','M','09758475847');
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet`
--

DROP TABLE IF EXISTS `pet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pet` (
  `petid` int(11) NOT NULL,
  `pet_name` varchar(45) NOT NULL,
  `species` varchar(45) NOT NULL,
  `gender` varchar(45) NOT NULL,
  `birthdate` varchar(45) NOT NULL,
  `ownerid` int(11) NOT NULL,
  PRIMARY KEY (`petid`),
  KEY `ownerid_idx` (`ownerid`),
  CONSTRAINT `ownerid` FOREIGN KEY (`ownerid`) REFERENCES `owner` (`ownerid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet`
--

LOCK TABLES `pet` WRITE;
/*!40000 ALTER TABLE `pet` DISABLE KEYS */;
INSERT INTO `pet` VALUES (1,'Toby','Dog','M','05-16-2016',2),(2,'Percy','Hamster','M','06-17-2017',1),(3,'Hermione','Cat','F','01-20-2015',3),(4,'Taylor','Snake','F','03-19-2017',2);
/*!40000 ALTER TABLE `pet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veterinarian`
--

DROP TABLE IF EXISTS `veterinarian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `veterinarian` (
  `vetid` int(11) NOT NULL,
  `vet_first_name` varchar(45) NOT NULL,
  `vet_last_name` varchar(45) NOT NULL,
  `vet_gender` varchar(45) NOT NULL,
  `vet_type` varchar(45) NOT NULL,
  `vet_contact_no` varchar(45) NOT NULL,
  PRIMARY KEY (`vetid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veterinarian`
--

LOCK TABLES `veterinarian` WRITE;
/*!40000 ALTER TABLE `veterinarian` DISABLE KEYS */;
INSERT INTO `veterinarian` VALUES (1,'Kara','Zorel','F','permanent','0984756475'),(2,'Barry','Allen','M','visiting','0978574754'),(3,'Oliver','Queen','M','permanent','0938437473');
/*!40000 ALTER TABLE `veterinarian` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'vetclinic'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-06 21:24:46
