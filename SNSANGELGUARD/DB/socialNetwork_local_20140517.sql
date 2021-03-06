CREATE DATABASE  IF NOT EXISTS `socialNetwork` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `socialNetwork`;
-- MySQL dump 10.13  Distrib 5.6.12, for osx10.7 (x86_64)
--
-- Host: localhost    Database: socialNetwork
-- ------------------------------------------------------
-- Server version	5.6.12

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
-- Table structure for table `Address_openSocial`
--

DROP TABLE IF EXISTS `Address_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Address_openSocial` (
  `id_Address_openSocial` varchar(100) NOT NULL DEFAULT '',
  `COUNTRY` varchar(100) DEFAULT NULL,
  `EXTENDED_ADDRESS` varchar(150) DEFAULT NULL,
  `LATITUDE` int(11) DEFAULT NULL,
  `LOCALITY` varchar(100) DEFAULT NULL,
  `LONGITUDE` int(11) DEFAULT NULL,
  `PO_BOX` varchar(10) DEFAULT NULL,
  `POSTAL_CODE` varchar(10) DEFAULT NULL,
  `REGION` varchar(100) DEFAULT NULL,
  `STREET_ADDRESS` varchar(150) DEFAULT NULL,
  `TYPE` varchar(45) DEFAULT NULL,
  `UNSTRUCTURED` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_Address_openSocial`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Address_openSocial`
--

LOCK TABLES `Address_openSocial` WRITE;
/*!40000 ALTER TABLE `Address_openSocial` DISABLE KEYS */;
INSERT INTO `Address_openSocial` VALUES ('0000000000','','',0,'',0,'','','','','',NULL);
/*!40000 ALTER TABLE `Address_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Drinker_openSocial`
--

DROP TABLE IF EXISTS `Drinker_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Drinker_openSocial` (
  `id_Drinker_openSocial` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_Drinker_openSocial`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Drinker_openSocial`
--

LOCK TABLES `Drinker_openSocial` WRITE;
/*!40000 ALTER TABLE `Drinker_openSocial` DISABLE KEYS */;
INSERT INTO `Drinker_openSocial` VALUES (1,''),(2,'NO'),(3,'OCCASIONALLY'),(4,'QUIT'),(5,'QUITTING'),(6,'REGULARLY'),(7,'SOCIALLY'),(8,'YES'),(9,'HEAVILY');
/*!40000 ALTER TABLE `Drinker_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Email_openSocial`
--

DROP TABLE IF EXISTS `Email_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Email_openSocial` (
  `id_Email_openSocial` int(11) NOT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `TYPE` varchar(45) DEFAULT NULL,
  `user_openSocial_id_user_openSocial` varchar(100) NOT NULL,
  PRIMARY KEY (`id_Email_openSocial`),
  KEY `fk_Email_openSocial_user_openSocial1` (`user_openSocial_id_user_openSocial`),
  CONSTRAINT `fk_Email_openSocial_user_openSocial1` FOREIGN KEY (`user_openSocial_id_user_openSocial`) REFERENCES `user_openSocial` (`id_user_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Email_openSocial`
--

LOCK TABLES `Email_openSocial` WRITE;
/*!40000 ALTER TABLE `Email_openSocial` DISABLE KEYS */;
/*!40000 ALTER TABLE `Email_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Friends_openSocial`
--

DROP TABLE IF EXISTS `Friends_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Friends_openSocial` (
  `USER_ID` varchar(45) NOT NULL,
  `GROUP_ID` varchar(45) DEFAULT NULL,
  `NETWORK_DISTANCE` int(11) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Friends_openSocial`
--

LOCK TABLES `Friends_openSocial` WRITE;
/*!40000 ALTER TABLE `Friends_openSocial` DISABLE KEYS */;
/*!40000 ALTER TABLE `Friends_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Gender_openSocial`
--

DROP TABLE IF EXISTS `Gender_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Gender_openSocial` (
  `id_Gender_openSocial` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_Gender_openSocial`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Gender_openSocial`
--

LOCK TABLES `Gender_openSocial` WRITE;
/*!40000 ALTER TABLE `Gender_openSocial` DISABLE KEYS */;
INSERT INTO `Gender_openSocial` VALUES (1,''),(2,'MALE'),(3,'FEMALE');
/*!40000 ALTER TABLE `Gender_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LookingFor_openSocial`
--

DROP TABLE IF EXISTS `LookingFor_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LookingFor_openSocial` (
  `id_LookingFor_openSocial` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_LookingFor_openSocial`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LookingFor_openSocial`
--

LOCK TABLES `LookingFor_openSocial` WRITE;
/*!40000 ALTER TABLE `LookingFor_openSocial` DISABLE KEYS */;
INSERT INTO `LookingFor_openSocial` VALUES (1,''),(2,'ACTIVITY_PARTNERS'),(3,'DATING'),(4,'FRIENDS'),(5,'NETWORKING'),(6,'RANDOM'),(7,'RELATIONSHIP');
/*!40000 ALTER TABLE `LookingFor_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Message_Type_openSocial`
--

DROP TABLE IF EXISTS `Message_Type_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Message_Type_openSocial` (
  `id_Message_Type_openSocial` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_Message_Type_openSocial`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Message_Type_openSocial`
--

LOCK TABLES `Message_Type_openSocial` WRITE;
/*!40000 ALTER TABLE `Message_Type_openSocial` DISABLE KEYS */;
INSERT INTO `Message_Type_openSocial` VALUES (1,NULL),(2,'EMAIL'),(3,'NOTIFICATION'),(4,'PRIVATE_MESSAGE'),(5,'PUBLIC_MESSAGE');
/*!40000 ALTER TABLE `Message_Type_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Message_openSocial`
--

DROP TABLE IF EXISTS `Message_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Message_openSocial` (
  `id_Message_openSocial` int(11) NOT NULL,
  `BODY` varchar(1000) DEFAULT NULL,
  `BODY_ID` varchar(1000) DEFAULT NULL,
  `TITLE` varchar(200) DEFAULT NULL,
  `TITLE_ID` varchar(200) DEFAULT NULL,
  `Message_Type_openSocial_id_Message_Type_openSocial` int(11) NOT NULL,
  `user_openSocial_id_user_openSocial` varchar(100) NOT NULL,
  PRIMARY KEY (`id_Message_openSocial`),
  KEY `fk_Message_openSocial_Message_Type_openSocial1` (`Message_Type_openSocial_id_Message_Type_openSocial`),
  KEY `fk_Message_openSocial_user_openSocial1` (`user_openSocial_id_user_openSocial`),
  CONSTRAINT `fk_Message_openSocial_Message_Type_openSocial1` FOREIGN KEY (`Message_Type_openSocial_id_Message_Type_openSocial`) REFERENCES `Message_Type_openSocial` (`id_Message_Type_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Message_openSocial_user_openSocial1` FOREIGN KEY (`user_openSocial_id_user_openSocial`) REFERENCES `user_openSocial` (`id_user_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Message_openSocial`
--

LOCK TABLES `Message_openSocial` WRITE;
/*!40000 ALTER TABLE `Message_openSocial` DISABLE KEYS */;
/*!40000 ALTER TABLE `Message_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Name_openSocial`
--

DROP TABLE IF EXISTS `Name_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Name_openSocial` (
  `id_Name_openSocial` int(11) NOT NULL,
  `ADDITIONAL_NAME` varchar(45) DEFAULT NULL,
  `FAMILY_NAME` varchar(45) DEFAULT NULL,
  `GIVEN_NAME` varchar(45) DEFAULT NULL,
  `HONORIFIC_PREFIX` varchar(45) DEFAULT NULL,
  `HONORIFIC_SUFFIX` varchar(45) DEFAULT NULL,
  `UNSTRUCTURED` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_Name_openSocial`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Name_openSocial`
--

LOCK TABLES `Name_openSocial` WRITE;
/*!40000 ALTER TABLE `Name_openSocial` DISABLE KEYS */;
/*!40000 ALTER TABLE `Name_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Organization_openSocial`
--

DROP TABLE IF EXISTS `Organization_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Organization_openSocial` (
  `id_Organization_openSocial` int(11) NOT NULL,
  `Address_openSocial_id_Address_openSocial` varchar(10) NOT NULL,
  `DESCRIPTION` varchar(100) DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  `FIELD` varchar(100) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `SALARY` varchar(45) DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  `SUB_FIELD` varchar(45) DEFAULT NULL,
  `TITLE` varchar(45) DEFAULT NULL,
  `WEB_PAGE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_Organization_openSocial`),
  KEY `fk_Organization_openSocial_Address_openSocial` (`Address_openSocial_id_Address_openSocial`),
  CONSTRAINT `fk_Organization_openSocial_Address_openSocial` FOREIGN KEY (`Address_openSocial_id_Address_openSocial`) REFERENCES `Address_openSocial` (`id_Address_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Organization_openSocial`
--

LOCK TABLES `Organization_openSocial` WRITE;
/*!40000 ALTER TABLE `Organization_openSocial` DISABLE KEYS */;
/*!40000 ALTER TABLE `Organization_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Phones_openSocial`
--

DROP TABLE IF EXISTS `Phones_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Phones_openSocial` (
  `id_Phones_openSocial` int(11) NOT NULL,
  `NUMBER` varchar(45) DEFAULT NULL,
  `TYPE` varchar(45) DEFAULT NULL,
  `user_openSocial_id_user_openSocial` varchar(100) NOT NULL,
  PRIMARY KEY (`id_Phones_openSocial`),
  KEY `fk_Phones_openSocial_user_openSocial1` (`user_openSocial_id_user_openSocial`),
  CONSTRAINT `fk_Phones_openSocial_user_openSocial1` FOREIGN KEY (`user_openSocial_id_user_openSocial`) REFERENCES `user_openSocial` (`id_user_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Phones_openSocial`
--

LOCK TABLES `Phones_openSocial` WRITE;
/*!40000 ALTER TABLE `Phones_openSocial` DISABLE KEYS */;
/*!40000 ALTER TABLE `Phones_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Presence_openSocial`
--

DROP TABLE IF EXISTS `Presence_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Presence_openSocial` (
  `id_Presence_openSocial` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_Presence_openSocial`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Presence_openSocial`
--

LOCK TABLES `Presence_openSocial` WRITE;
/*!40000 ALTER TABLE `Presence_openSocial` DISABLE KEYS */;
INSERT INTO `Presence_openSocial` VALUES (1,''),(2,'AWAY'),(3,'CHAT'),(4,'DND'),(5,'OFFLINE'),(6,'ONLINE'),(7,'XA');
/*!40000 ALTER TABLE `Presence_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Smoker_openSocial`
--

DROP TABLE IF EXISTS `Smoker_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Smoker_openSocial` (
  `id_Smoker_openSocial` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_Smoker_openSocial`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Smoker_openSocial`
--

LOCK TABLES `Smoker_openSocial` WRITE;
/*!40000 ALTER TABLE `Smoker_openSocial` DISABLE KEYS */;
INSERT INTO `Smoker_openSocial` VALUES (1,''),(2,'HEAVILY'),(3,'NO'),(4,'OCCASIONALLY'),(5,'QUIT'),(6,'QUITTING'),(7,'REGULARLY'),(8,'SOCIALLY'),(9,'YES');
/*!40000 ALTER TABLE `Smoker_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `action_links_facebook`
--

DROP TABLE IF EXISTS `action_links_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action_links_facebook` (
  `id_action_links_facebook` int(11) NOT NULL,
  `TEXT` varchar(200) DEFAULT NULL,
  `URL` varchar(100) DEFAULT NULL,
  `stream_facebook_post_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id_action_links_facebook`),
  KEY `fk_action_links_facebook_stream_facebook1` (`stream_facebook_post_id`),
  CONSTRAINT `fk_action_links_facebook_stream_facebook1` FOREIGN KEY (`stream_facebook_post_id`) REFERENCES `stream_facebook` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_links_facebook`
--

LOCK TABLES `action_links_facebook` WRITE;
/*!40000 ALTER TABLE `action_links_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `action_links_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `affiliations_facebook`
--

DROP TABLE IF EXISTS `affiliations_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `affiliations_facebook` (
  `id_affiliations_facebook` int(11) NOT NULL,
  `YEAR` varchar(10) DEFAULT NULL,
  `NAME` varchar(200) DEFAULT NULL,
  `NID` int(11) DEFAULT NULL,
  `STATUS` varchar(100) DEFAULT NULL,
  `type_affiliations_facebook_id_type_affiliations_facebook` int(11) NOT NULL,
  `user_facebook_id_user_facebook` varchar(100) NOT NULL,
  PRIMARY KEY (`id_affiliations_facebook`),
  KEY `fk_affiliations_facebook_type_affiliations_facebook1` (`type_affiliations_facebook_id_type_affiliations_facebook`),
  KEY `fk_affiliations_facebook_user_facebook1` (`user_facebook_id_user_facebook`),
  CONSTRAINT `fk_affiliations_facebook_type_affiliations_facebook1` FOREIGN KEY (`type_affiliations_facebook_id_type_affiliations_facebook`) REFERENCES `type_affiliations_facebook` (`id_type_affiliations_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_affiliations_facebook_user_facebook1` FOREIGN KEY (`user_facebook_id_user_facebook`) REFERENCES `user_facebook` (`id_user_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `affiliations_facebook`
--

LOCK TABLES `affiliations_facebook` WRITE;
/*!40000 ALTER TABLE `affiliations_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `affiliations_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_facebook`
--

DROP TABLE IF EXISTS `comment_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_facebook` (
  `XID` varchar(100) NOT NULL,
  `OBJECT_ID` varchar(100) DEFAULT NULL,
  `POST_ID` varchar(100) NOT NULL,
  `FROMID` varchar(100) DEFAULT NULL,
  `TIME_COMMENT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TEXT` mediumtext CHARACTER SET utf8,
  `ID` varchar(100) NOT NULL,
  `USERNAME` varchar(100) DEFAULT NULL,
  `REPLY_XID` varchar(100) DEFAULT NULL,
  `comments_facebook_id_comments_facebook` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_comment_facebook_comments_facebook1` (`comments_facebook_id_comments_facebook`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_facebook`
--

LOCK TABLES `comment_facebook` WRITE;
/*!40000 ALTER TABLE `comment_facebook` DISABLE KEYS */;
INSERT INTO `comment_facebook` VALUES ('','522327411159452','100001105347886_533364776710362','1149018120','2013-05-19 11:38:19','Bleck Uribarri','100001105347886:522506654474861:291:533364776710362_81088119','','',0),('','172891229424387','100001105347886_172891229424387','100001105347886','2011-02-24 09:20:11','Suele ser el que vale...','172891229424387_2113090','','',0),('','172891229424387','100001105347886_172891229424387','100001105347886','2011-02-24 10:26:12','Siempre funciona','172891229424387_2113194','','',0),('','172891229424387','100001105347886_172891229424387','100001105347886','2011-02-25 09:06:51','Parece que va bien....','172891229424387_2117452','','',0),('','173115946068582','100001105347886_173115946068582','100001105347886','2011-02-25 09:43:09','A ver si funcionan','173115946068582_2117512','','',0),('','173115946068582','100001105347886_173115946068582','100001105347886','2011-02-25 11:42:50','DANDO CAÑA','173115946068582_2117830','','',0),('','175182775861899','100001105347886_175182775861899','100001105347886','2011-03-06 16:05:07','hija de puta tu madre','175182775861899_2157677','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 09:22:47','Que hija de puta','176056522441191_2174775','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 09:33:07','Vamos a ver ahora...','176056522441191_2174803','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 09:42:04','Seguimos...','176056522441191_2174820','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 09:51:11','A ver si hay nuevos comentarios....','176056522441191_2174840','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 10:00:36','Probando vector...','176056522441191_2174857','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 10:27:26','Probando segundo vector....','176056522441191_2174888','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 10:31:22','Brindo por aquel ayer...','176056522441191_2174894','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 10:36:27','El perfume de las chicas en flor...','176056522441191_2174897','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 10:44:49','El aroma de su cara y su amor...','176056522441191_2174914','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 10:46:59','Es un regalo... es así','176056522441191_2174924','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 10:49:32','Con qué motivo le dejé tocar mi corazón con su amor...','176056522441191_2174929','','',0),('','176056522441191','100001105347886_176056522441191','100001105347886','2011-03-10 10:51:47','Les Miserables...','176056522441191_2174935','','',0),('','176067402440103','100001105347886_176067402440103','100001105347886','2011-03-10 10:51:26','Hoy ha muerto Jan Valjean','176067402440103_2174932','','',0),('','176067402440103','100001105347886_176067402440103','100001105347886','2011-03-10 10:51:35','Aqui termina su canción....','176067402440103_2174933','','',0),('','176067402440103','100001105347886_176067402440103','100001105347886','2011-03-10 10:55:59','Comemé la polla','176067402440103_2174943','','',0),('','176067402440103','100001105347886_176067402440103','100001105347886','2011-03-10 10:58:07','Comeme la polla otra vez','176067402440103_2174947','','',0),('','176067402440103','100001105347886_176067402440103','100001105347886','2011-03-10 10:59:54','Polla','176067402440103_2174950','','',0),('','176067402440103','100001105347886_176067402440103','100001105347886','2011-03-10 11:00:41','polla','176067402440103_2174951','','',0),('','460440287336145','100001105347886_460440287336145','100001105347886','2012-12-02 19:57:49','Si macho, payaso','460440287336145_90176777','','',0),('','460440287336145','100001105347886_460440287336145','100001105347886','2012-12-02 20:21:20','Vamos ya','460440287336145_90176825','','',0),('','460440287336145','100001105347886_460440287336145','100001105347886','2012-12-02 20:27:04','Prueba con un nuevo angel','460440287336145_90176852','','',0),('','506238176089689','100001105347886_506238176089689','100001105347886','2013-03-10 21:32:15','Que gilipollas... tonto del culo','506238176089689_90497701','','',0),('','506238176089689','100001105347886_506238176089689','100001105347886','2013-03-30 19:47:23','pallaso','506238176089689_90558648','','',0),('','506238176089689','100001105347886_506238176089689','100001105347886','2013-03-30 19:57:19','SUBNORMAL','506238176089689_90558691','','',0),('','513800032000170','100001105347886_513800032000170','100001105347886','2013-03-30 20:05:43','Hola caracola!!!','513800032000170_90558715','','',0),('','513800032000170','100001105347886_513800032000170','1050629888','2013-03-30 20:09:56','Si es que eres tonto, tontaco...','513800032000170_90558721','','',0),('','525308034182703','100001105347886_525308034182703','1050629888','2013-04-28 14:02:11','Esto funciona!!!! Si es que eres tonto, eres gilipollas...','525308034182703_90638721','','',0),('','538645579515615','100001105347886_538645579515615','100001105347886','2013-06-01 11:43:53','Gracias!!! Tu eres un poco gilipollicas!!','538645579515615_90729701','','',0),('','551232484923591','100001105347886_551232484923591','100001105347886','2013-06-30 14:42:57','1','551232484923591_90807488','','',0),('','551232484923591','100001105347886_551232484923591','100001105347886','2013-06-30 14:45:01','Parece que funciona....','551232484923591_90807490','','',0),('','551232484923591','100001105347886_551232484923591','100001105347886','2013-06-30 14:45:08','Gilipollas!!','551232484923591_90807491','','',0),('','551232484923591','100001105347886_551232484923591','100001105347886','2013-06-30 14:56:04','alemanita','551232484923591_90807508','','',0),('','551232484923591','100001105347886_551232484923591','100001105347886','2013-06-30 15:24:55','sos muy gilipollas','551232484923591_90807544','','',0),('','551232484923591','100001105347886_551232484923591','100001105347886','2013-06-30 15:29:35','Mas tonto y no naces...','551232484923591_90807550','','',0),('','551232484923591','100001105347886_551232484923591','100001105347886','2013-06-30 17:21:19','Todo funciona','551232484923591_90807787','','',0),('','623669041013268','100001105347886_623669041013268','100001105347886','2013-12-04 22:08:09','Será gilipollas el tio...','623669041013268_91187736','','',0),('','625201154193390','100001105347886_625201154193390','1050629888','2013-12-08 12:37:24','jajajajajajaja','625201154193390_91195960','','',0),('','625201154193390','100001105347886_625201154193390','1050629888','2013-12-08 12:48:40','jajajja tontaco!','625201154193390_91195985','','',0),('','625247660855406','100001105347886_625247660855406','100001105347886','2013-12-08 14:51:22','Gilipollas!','625247660855406_91196223','','',0),('','625247660855406','100001105347886_625247660855406','100001105347886','2013-12-08 15:07:48','Cabronazo!','625247660855406_91196253','','',0),('','663933960320109','100001105347886_663933960320109','1050629888','2014-03-15 14:28:00','Gilipollas, capullo y payaso','663933960320109_91432541','','',0),('','663933960320109','100001105347886_663933960320109','1458231992','2014-03-15 16:40:45','Y eso?','663933960320109_91432755','','',0);
/*!40000 ALTER TABLE `comment_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments_facebook`
--

DROP TABLE IF EXISTS `comments_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments_facebook` (
  `id_comments_facebook` int(11) NOT NULL,
  `CAN_REMOVE` tinyint(1) DEFAULT NULL,
  `CAN_POST` tinyint(1) DEFAULT NULL,
  `COUNT` int(11) DEFAULT NULL,
  `stream_facebook_post_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id_comments_facebook`),
  KEY `fk_comments_facebook_stream_facebook1` (`stream_facebook_post_id`),
  CONSTRAINT `fk_comments_facebook_stream_facebook1` FOREIGN KEY (`stream_facebook_post_id`) REFERENCES `stream_facebook` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments_facebook`
--

LOCK TABLES `comments_facebook` WRITE;
/*!40000 ALTER TABLE `comments_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education_history_facebook`
--

DROP TABLE IF EXISTS `education_history_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `education_history_facebook` (
  `id_education_history_facebook` int(11) NOT NULL,
  `YEAR` varchar(5) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `DEGREE` varchar(100) DEFAULT NULL,
  `CONCENTRATIONS_0` varchar(200) DEFAULT NULL,
  `CONCENTRATIONS_1` varchar(200) DEFAULT NULL,
  `CONCENTRATIONS_2` varchar(200) DEFAULT NULL COMMENT '	',
  `user_facebook_id_user_facebook` varchar(100) NOT NULL,
  PRIMARY KEY (`id_education_history_facebook`),
  KEY `fk_education_history_facebook_user_facebook1` (`user_facebook_id_user_facebook`),
  CONSTRAINT `fk_education_history_facebook_user_facebook1` FOREIGN KEY (`user_facebook_id_user_facebook`) REFERENCES `user_facebook` (`id_user_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education_history_facebook`
--

LOCK TABLES `education_history_facebook` WRITE;
/*!40000 ALTER TABLE `education_history_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `education_history_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `family_facebook`
--

DROP TABLE IF EXISTS `family_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `family_facebook` (
  `id_family_facebook` int(11) NOT NULL,
  `relationship_facebook_id_relationship_facebook` int(11) NOT NULL,
  `UID` varchar(45) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `BIRTHDAY` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_family_facebook`),
  KEY `fk_family_facebook_relationship_facebook1` (`relationship_facebook_id_relationship_facebook`),
  CONSTRAINT `fk_family_facebook_relationship_facebook1` FOREIGN KEY (`relationship_facebook_id_relationship_facebook`) REFERENCES `relationship_facebook` (`id_relationship_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family_facebook`
--

LOCK TABLES `family_facebook` WRITE;
/*!40000 ALTER TABLE `family_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `family_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends_facebook`
--

DROP TABLE IF EXISTS `friends_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends_facebook` (
  `USER_UID` varchar(45) NOT NULL,
  `USER_NAME` varchar(45) DEFAULT NULL,
  `USER_BIRTHDAY` varchar(45) DEFAULT NULL,
  `USER_PIC` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`USER_UID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends_facebook`
--

LOCK TABLES `friends_facebook` WRITE;
/*!40000 ALTER TABLE `friends_facebook` DISABLE KEYS */;
INSERT INTO `friends_facebook` VALUES ('100002478641009','Miguel Refusta','18-01-0000','https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc1/t1.0-1/c25.0.81.81/s50x50/252231_1002029915278_1941483569_s.jpg'),('1050629888','José Javier Blecua','16-03-1984','https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c9.0.50.50/p50x50/10334365_10202792508907724_161800622022956079_s.jpg'),('1149018120','Pablo Gonzalo Salmerón','14-06-1984','https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c9.9.112.112/s50x50/295032_10200349239950807_1622508073_s.jpg'),('1458231992','Sandra Camaño Barrueco','29-07-1993','https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c7.7.88.88/s50x50/292454_3968191210706_1795970141_s.jpg'),('588944763','Miguel-Angel Sicilia','26-02-1973','https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c0.12.50.50/p50x50/1920031_10152208349299764_917939916_t.jpg'),('796834259','Recio Martell','21-04-1984','https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/t1.0-1/c7.7.89.89/s50x50/312156_10150367898399260_541927562_s.jpg');
/*!40000 ALTER TABLE `friends_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends_likes_facebook`
--

DROP TABLE IF EXISTS `friends_likes_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends_likes_facebook` (
  `id_friends_likes_facebook` int(11) NOT NULL,
  `UID` varchar(45) DEFAULT NULL,
  `likes_facebook_id_likes_facebook` int(11) NOT NULL,
  PRIMARY KEY (`id_friends_likes_facebook`),
  KEY `fk_friends_likes_facebook_likes_facebook1` (`likes_facebook_id_likes_facebook`),
  CONSTRAINT `fk_friends_likes_facebook_likes_facebook1` FOREIGN KEY (`likes_facebook_id_likes_facebook`) REFERENCES `likes_facebook` (`id_likes_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends_likes_facebook`
--

LOCK TABLES `friends_likes_facebook` WRITE;
/*!40000 ALTER TABLE `friends_likes_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `friends_likes_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guardian_settings`
--

DROP TABLE IF EXISTS `guardian_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guardian_settings` (
  `uid_guardian` int(11) NOT NULL AUTO_INCREMENT,
  `guardian_name` varchar(200) NOT NULL,
  `guardian_email` varchar(200) NOT NULL,
  PRIMARY KEY (`uid_guardian`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guardian_settings`
--

LOCK TABLES `guardian_settings` WRITE;
/*!40000 ALTER TABLE `guardian_settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `guardian_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes_facebook`
--

DROP TABLE IF EXISTS `likes_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `likes_facebook` (
  `id_likes_facebook` int(11) NOT NULL,
  `HREF` varchar(100) DEFAULT NULL,
  `COUNT` int(11) DEFAULT NULL,
  `USER_LIKES` tinyint(1) DEFAULT NULL,
  `CAN_LIKE` tinyint(1) DEFAULT NULL,
  `stream_facebook_post_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id_likes_facebook`),
  KEY `fk_likes_facebook_stream_facebook1` (`stream_facebook_post_id`),
  CONSTRAINT `fk_likes_facebook_stream_facebook1` FOREIGN KEY (`stream_facebook_post_id`) REFERENCES `stream_facebook` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes_facebook`
--

LOCK TABLES `likes_facebook` WRITE;
/*!40000 ALTER TABLE `likes_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `likes_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locale_settings`
--

DROP TABLE IF EXISTS `locale_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `locale_settings` (
  `id_locale` varchar(100) NOT NULL DEFAULT '',
  `acceptingTerms` varchar(1000) DEFAULT NULL,
  `legalAccepted` mediumtext,
  `btnAgreeAT` varchar(100) DEFAULT NULL,
  `btnCancelAT` varchar(100) DEFAULT NULL,
  `titleAT` varchar(100) DEFAULT NULL,
  `titleSettings` varchar(100) DEFAULT NULL,
  `titleMenSettings` varchar(100) DEFAULT NULL,
  `btnSaveCheckSettings` varchar(100) DEFAULT NULL,
  `titleSettAng` varchar(100) DEFAULT NULL,
  `titleFriendsContentSettAng` varchar(100) DEFAULT NULL,
  `titleFriendsImportSettAng` varchar(100) DEFAULT NULL,
  `txtNameTutorSettAng` varchar(100) DEFAULT NULL,
  `txtEmailTutorSettAng` varchar(100) DEFAULT NULL,
  `btnImportSettAng` varchar(100) DEFAULT NULL,
  `titleFbListSettAng` varchar(100) DEFAULT NULL,
  `subTitleAngelSettAng` varchar(100) DEFAULT NULL,
  `titleSettVig` varchar(100) DEFAULT NULL,
  `titleVigilantSettVig` varchar(100) DEFAULT NULL,
  `titleVigSettVig` varchar(100) DEFAULT NULL,
  `titleVigDescriptionSettVig` varchar(1000) DEFAULT NULL,
  `titleVigFrecSettVig` varchar(100) DEFAULT NULL,
  `titleVigFrecSelectSettVig` varchar(100) DEFAULT NULL,
  `titleVigAngSettVig` varchar(100) DEFAULT NULL,
  `titleAngConfirm` varchar(100) DEFAULT NULL,
  `desInfoAngConfirm` varchar(1000) DEFAULT NULL,
  `aceptConfAngConfirm` varchar(100) DEFAULT NULL,
  `cancelConfAngConfirm` varchar(100) DEFAULT NULL,
  `infoAngGuard` varchar(100) DEFAULT NULL,
  `titleAngUser` varchar(100) DEFAULT NULL,
  `nameUserAngUser` varchar(100) DEFAULT NULL,
  `btnCloseAngUser` varchar(100) DEFAULT NULL,
  `titleGoogleCont` varchar(100) DEFAULT NULL,
  `titleContGoogleCont` varchar(100) DEFAULT NULL,
  `btnLogGoogleCont` varchar(100) DEFAULT NULL,
  `titleNameContactGoogleCont` varchar(100) DEFAULT NULL,
  `titleEmailContactGoogleCont` varchar(100) DEFAULT NULL,
  `btnAceptGoogleCont` varchar(100) DEFAULT NULL,
  `btnCancelGoogleCont` varchar(100) DEFAULT NULL,
  `helpMe` mediumtext NOT NULL,
  `warnings` mediumtext NOT NULL,
  `titleInformationMessage` varchar(100) NOT NULL,
  `informationMessage` mediumtext NOT NULL,
  `mailDelete` mediumtext NOT NULL,
  `mailNotification` mediumtext NOT NULL,
  `altContactsAngelsEd` mediumtext NOT NULL,
  `titleVisitsFilterOptions` mediumtext,
  `post_friend_facebook` mediumtext,
  PRIMARY KEY (`id_locale`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locale_settings`
--

LOCK TABLES `locale_settings` WRITE;
/*!40000 ALTER TABLE `locale_settings` DISABLE KEYS */;
INSERT INTO `locale_settings` VALUES ('00000001','You are about to install SNSAngelGuard. Your profile data has NOT been accessed at this point. SNSAngelGuard is devised to give you support on living in social networks safely by declaring contact data of some people that you trust (e.g. your parents or relatives) that we call angels and selecting a number of guards, wich are software modules that read your profile information and inform the angels of any potential risk. Personal information on your contatcts is never forwarded to your angels, but they know that something that deserve inspection is needed and can warm and contact you.','<b>SNSAngelGuardFB Terms of Service</b>\n<br> \n<br>\n<ol> \n<li><b>You are welcome to SNSAngelGuardFB</b> </li>\n<p>Thanks for using our parental control plug-in SNSAngelGuardFB (“Application”) located at Escuela Técnica Superior de Ingeniería Informática, Campus Universitario, Carretera de Barcelona km 33.6, Alcalá de Henares, Madrid, España.</p>\n<p>Using our Application, you are agreeing to these terms. Please read them carefully. </p>\n<br>\n\n\n<li><b>Using our Application</b></li>\n<p>The Application is defined inside of Facebook’s App Center. Because this reason, we might to apply the same restrictions that Facebook does in your information.</p>\n\n<p>Using our Application does not give you ownership of any intellectual property rights in our Application or the content you access. You may use our Application if you accept the exclusive permissions of this as permitted by law. These terms do not grant you the right to use any branding or logos used in our Application. Don’t remove, obscure or alter any legal notices displayed in or along with our Application.</p>\n\n<p>Our Application displays some content that is no Facebook. This content is the sole responsibility of the entity that makes it available. We can review this content if this is not legal or broke our politics, and delete it or negate to publish this if we have any reason to considerate that the content broke our politics or the laws. However, this possibility not implies necessary that we review the content, so please don’t assume that we do.</p>\n\n<p>In connection with your use of the Application, we can sending services notifications, administrative emails and other kind of information. All the same, the Application send service notifications to others contacts, of your Facebook account or others Social Networks, like Google or others emails address that you define, sending someone information about an anomaly in your information inside your Facebook profile. These notifications and the information to third parties that the Application can be used, is the sole responsibility of the person who is titular of the Facebook account from which access to the Application. These notifications can be disabled in the control module of the Application in any time. Also, the contact that the Application send information can be disabled manually in the moment that the application send the accept terms for receiving notifications of your information.</p>\n\n<p>In this moment, our Application are not available for access from mobile devices but, in the case of in a next future would be available, shall apply all of this conditions to they which use our application in this devices. Don’t use the Application if you can distract and break traffic laws or safety.</p>\n<br>\n\n<li><b>Your Facebook account</b></li>\n<p>For using our Application is necessary that you have a Facebook account. For this, it will be necessary that you create, if you don’t have, your own account and to accept the access terms of the Application.</p>\n\n<p>To protect your Facebook account, keep your password confidential. You are responsible for the activity that happens on or through your Facebook account, like our Application. Try not to reuse your Facebook account password on third party applications or other Social Network. If you learn of any unauthorised use of your password or Facebook account, follow the instructions that Facebook put at your disposal for solve that issue. </p>\n<br>\n\n<li><b>Privacy protect and copyright</b></li>\n<p>Our Application will apply the privacy politic and data processing inside of the privacy politic of Facebook. If you use our Application, you accept that Facebook and our Application doing to use your information according of the Facebook politics. For more information of this, you must to previously read the politics about privacy and security of Facebook.</p>\n\n<p>We respond to notices of alleged copyright infringement and we’ll cancel the accounts of the users that, inside of our application, could be infring the terms of the  the spanish law of copyright, Real Decreto Legislativo 1/1996, of april 12, published in the BOE number 97, of the april 22 of 1996, and all of earlier modifications.</p>\n<br>\n\n<li><b>Your content in our Application</b></li>\n<p>Our Application allow to send notifications about the content of your personal information in Facebook (wall comments, dates of birthday of your contacts with their personal image, your relationships, familiars and personals, data about your profile, your configuration). If this information is send, you will do responsible of this information. In short, what belongs to you stays yours.</p>\n\n<p>All the information that our Application stores about you belongs you, we don´t share it with no more, and we don’t use it for fraudulent purposes, only we store your information for processing and sending to your selected contacts detected anomalies that the Application could detect in your information. So, your contacts can be informed of the attacks that you can received by Facebook’s profiles that act in this Social Network like stalkers.</p>\n\n<p>Never our Application will have any information that not be your own information in Facebook, that it\'s, our Application don’t receive data of other type that haven\'t been information that Facebook store about you and have given your acceptation for Facebook access of to these.</p>\n\n<p>For accessing of our Application to your data, you must to consent this legal terms and the permissions that Facebook give us for our Application. These permissions could be reviewed in any time in your app config on Facebook. If you don’t like some of these or you don’t want that our Application access to some information about you, you can to revoke any permission that you want and our Application will not access to this information.</p>\n\n<p>We suggest you reviews the privacy terms of Facebook for having a deep knowledge of the information that Facebook store about you and the uses that Facebook does of this.</p>\n<br>\n\n<li><b>About Software in our Application</b></li>\n<p>Our application runs inside in Facebook web environment. A part of the security context of Facebook, we have you a service, on which our application runs, that your security and the security of your information is guaranteed with a digital certificated on your web browser and you can test it in this. </p>\n\n<p>You can use our Application thanks to the license that Facebook grants you by access to their Social Network. The content of this can be found in your profile page. Our application does the next actions:\n\n<ul>\n<li>When you accept the content of this document, that you must to read it, the application downloads all information about your profile that you had given the permissions for storing this information.</li>\n\n<li>The application permits you to access a control module. You can access to this from Facebook. In this, you can do the next actions:\n<ul>\n<li>You can chose your Facebook’s friends or your Gmail contacts or contacts of others different emails, that you want they are in contact with your information inside Facebook.</li>\n\n<li>One selected, you can chose what kind of control you want to realise about your content. For each control, you can to select the contacts that you want to verify your different content and the temporal window that you want the control runs. In every moment you can to access to the control module and modify the properties of all controls.</li>\n</ul>\n</li>\n<li>Our application has a offline module that it runs regularity and it downloads all your new information in Facebook, according with your permissions, and sends notifications to your chosen contacts if the temporal window is completed. This module permits to our Application updating in every moment the information about you and your activity in Facebook for realising a more powerful control of these.</li>\n\n<li>At not point our Application will access to their personal information, that it\'s, if you give permission to our Application, we can to access to some information about your contacts, like birthday date and like profile image, always with the Facebook permission, so Facebook restrict the access of the applications to third person’s data. Never we can download wall’s information or personal information about your Facebook contacts, also it not belongs to our Application politic.</li>\n</ul>\n</p>\n<br>\n\n<li><b>Modifying and Terminating our Application</b></li>\n<p>Being a application that it runs inside Facebook, we don’t have any tool for deleting our Application in your Facebook’s profile. You must be who delete our application from your app’s configuration page inside Facebook, in your profile’s page. </p>\n\n<p>The fact that you delete our Application not involve that your data has been deleted in our store system. We can to determinate if a user has revoked permissions to access of their information but  we can’t to determinate if you have deleted our application. For your information could be delete, you must to send an email to our application’s administrators and they will delete all information about you. </p>\n\n<p>We believe that you own your data and preserving your access to such data is important. If we discontinue our Application, where reasonably possible, we will give you reasonable advance notice and a chance to get information out of our Application. </p>\n<br>\n\n<li><b>Our Warranties and Disclaimers</b></li>\n<p>Our application grants you that your information are always protect and never more can access they. Only we access to those datas which you will give us your consent to consult they. </p>\n\n<p>Your security is more important for us, so we’ll do anything for your information is completely safe in our store system. Our Application is responsible of our content and the use that we do they into Facebook. This include the responsibility that our Application achieve the terms of use of Facebook and their publicity terms. </p>\n\n<p>Facebook’s politics are achieving in this terms:\n<ul>\n\n<li>Our application only can to apply some information that it needs for it can be functional correctly, never information that doesn’t apply for their activity.</li>\n\n<li>We have a privacy politic, contents in this document, for granting and showing to our users the information that we’re going to use and what is the form that use this. </li>\n\n<li>Never, inside any concept, we’ll share your information with users or application of third-persons. </li>\n\n<li>We delete your information if you tell us, by an email or any other mechanism. We’ll contact with you and delete your information. We’ll do all for you do fine using our application. </li>\n\n<li>Never we’ll have any information about you in media. We’ll never sell to anyone your information. </li>\n\n<li>Facebook could limit our activity if detects that we aren’t doing a good use of your information. </li>\n\n<li>The user of our application will accept that your information will are processing and storing in the installations that the application will have. In this moment, this information will be processing in Spain, but, depending of the telecommunications market, this could change, being responsibility of our application. </li>\n\n<li>Is not responsibility of our application your content inside Facebook. Any data that we receive will be your responsibility. We can restrict the activity for a user in our application if we detect a not good use of our application but, for any moment is our responsibility the use that you could do of our application. </li> \n</ul>\n</p>\n<br>\n\n<li><b>Others conditions</b></li>\n<p>We can change this privacy terms if we detect any problem or Facebook changes your privacy. We can change this privacy terms if we detect any problem or Facebook changes their privacy terms. If we are any change in these terms, you will know by the mechanism that we have and you will must to accept this terms again. </p>\n</ol>','Agree','Cancel','Accepting Terms and Conditions','Settings','Settings;Angels;Vigilants;','Save','Select your Angels','Select your Angels from your Facebook Contacts','Import a contact of your Gmail account;Select others contacts;','Name','Email','Import','View All;Selected;Unselected;','Select as your Angel','Configure your Vigilants','Vigilants','Wall Control;Friends Control;Privacy Control;Visits Control;','Vigilant Description;When this control is activated, it checks the age for each facebook friend. If friend is very older for you, sends an email to your angels indicating that you have any friend that is very older for you.;When this control is activated, it checks if there are offensive language in your Facebook Wall, sending an email to your angels if the control verifies that any post is not appropiate for you.;When this control is activated, it checks if the level of privacy of your Facebook profile is too permissive. If many of your displayed information in Facebook is public and it hasn\'t some restrictions, the control sends to your angels an email with this issue.;When this control is activated, it checks the social activity of your friends in your profile. It sends an email with some stadistics about friends that share with others contacts, the contacts number of your friends and the post number that your friends have written in your wall.;','Notifications Frecuency','Once a day;Once a week;Once two weeks;Once a month;Once two months;Once six months;Once a year;','Angels','SNSAngelGuardFB: Authorisation for send notificacions',' wishes that you control his social activity in Facebook.\n\n                                    If you accept above mentioned authorization, a series of notifications will be sent in order that it could realize a personalized follow-up of his social activity.\n\n                                    If on the contrary, you cancel the authorization, it will not return to send any notification','Accept','Cancel',' is software developed in the Alcalá de Henares University, Spain.','SNSAngelGuardFB: User Identification','Name','Close','Import Contact from your Google account','Select a contact from your Google account','Login Google','Name','Email','Acept','Cancel','Need help?;Help; What is an Angel?; An Angel is a person who receives notifications of the anormal activities that it produced in your Facebook profile. For each anomalies, your Angels will receive a notification in her/his email.; What is a Vigilant?; A Vigilant is a software tool that is executing inside Facebook. These tools detect threats in your profile information, privacity, friends, wall, ..., etc and send notifications for your Angels.<br><br>Actually, there are four Vigilants: <br><br>- Wall Control: Check your post wall and send a notification to your Angels with the offensive language that could exist. <br><br>- Friends Control: Check your friends, detecting which of them have indicated his age, and if this one exceeds a certain limit.<br><br>- Privacity Control: Check your privacy configuration, detecting if your profile is too permissive to access to your personal information.<br><br>- Visits Control: Check the last visits and to whom they belong on your facebook profile.; Vigilant configuration; To configure a Vigilant it\'s necesary two actions:<br><br>1. To select an Angel or Angels to which you want them to control your activity. The Angels they are selected in the eyelash \"Angels\" and later, in the eyelash \"Vigilants\", they will be able to associate in an individual way to every filter.<br><br>2. The frequency the SNSAngelGuard will send reports to your Angels, informing them about your activity in Facebook.<br>;','You must select at least an angel;Information has stored successfully;Storing information...;Loading...;Waiting for operation to complete...;Angel correctly deleted!;Angel correctly saved!;Error;There is an error in the application… ;Details; You must introduce any email; Not valid email; There was a mistake while sharing a post. Angel did not save it;Your browser is not compatible with the application. Use one of the below list:&lt;P&gt;&lt;OL&gt;&lt;LI&gt;Firefox 5&lt;LI&gt;Google Chrome&lt;/OL&gt;&lt;/P&gt;','Information','Dates saved successfully.;User already confirmated.;Error: Non-existent Angel;','SNSAngelGuard Alert: Deleted angel; deleted you of the Angel List;','SNSAngelGuard: Activity Notification; SNSAngelGuad Notification: User ;In the comment ; of the post wall ; there is offensive language. ;There isn\'t any anomalies in the user\'s Wall.; doen\'t say his/her age ; is too old for ;There isn\'t any anomalies in the user\'s friends.;There isn\'t any anomalies in the user\'s privacity configuration.;There isn\'t any anomalies in the user\'s profile visits.;','Create new Angel;Edit;Cancel;Delete;Save;','Best ranking\'s friends with mutual contacts in your Facebook; Worst ranking\'s friends with mutual contacts in your Facebook; Post wall of your friends in your Facebook wall; ','Authorisation to send notifications;Agree with terms and conditions;The user wants you to control his or her social activity on Facebook. For more information, click on the link below.;'),('00000002','SNSAngelGuard analiza la actividad social de una persona. Si detecta alguna anomal&iacute;a, env&iacute;a un informe a la persona o tutor responsable. Este tutor habr&aacute; sido previamente designado por la persona que decide instalar, por voluntad propia, SNSAngelGuard. La aplicaci&oacute;n utiliza datos personales del perfil en cuesti&oacute;n para ser posteriormente analizados. Para proceder a su instalaci&oacute;n, rogamos lea detenidamente el Acuerdo Legal que se expone a continuaci&oacute;n. Si, por el contrario, no desea que SNSAngelGuard se ejecute, pulse el bot&oacute;n \"Cancelar\" y se proceder&aacute; a su desinstalaci&oacute;n.','<b>Condiciones de servicio de SNSAngelGuardFB</b>\n<br>\n<br>\n<ol>\n<li><b>Te damos la bienvenida a SNSAngelGuardFB</b></li>\n<p>Te agradecemos que uses la herramienta de control parental SNSAngelGuardFB (en adelante, la <b>Aplicación</b>), cuyo dominio social está ubicado en la Escuela Técnica Superior de Ingeniería Informática, Campus Universitario, Carretera de Barcelona km 33.6, Alcalá de Hernares, Madrid, España.</p>\n<p>El uso de nuestra Aplicación implica la aceptación de éstas condiciones. Te recomendamos que las leas detenidamente.</p>\n<br>\n<li><b>Uso de nuestra Aplicación</b></li>\n<p>La aplicación se encuentra dentro del dominio de ejecución de aplicaciones de Facebook, por lo que las restricciones que dicha Red Social ejecute sobre tus datos son los mismos que nuestra Aplicación ejecutará a éstos.</p>\n<p>El uso de nuestra Aplicación no te convierte en el titular de ninguno de los derechos de propiedad intelectual de los mismos ni del contenido al que accedes. Solo podrás acceder a la Aplicación si consientes la aceptación de sus permisos o si está permitido por la ley. Estas condiciones no te otorgan el derecho a usar las marcas ni los logotipos utilizados en nuestra Aplicación. Presta mucha atención a los avisos legales que se muestran en nuestra Aplicación.</p>\n<p>Nuestra Aplicación muestra contenidos que no pertenecen a Facebook. Este contenido es responsabilidad exclusiva de la entidad que lo haya puesto a disposición del usuario. Podremos revisar el contenido para determinar si es ilegal o infringe nuestras políticas, y eliminarlo o negarnos a publicarlo si tenemos razones suficientes para considerar que infringe nuestras políticas o la ley. Sin embargo, esta posibilidad no implica necesariamente que revisemos el contenido, por lo que no debes dar por sentado que vayamos a hacerlo.</p>\n<p>En relación al uso de la Aplicación, podremos enviarte notificaciones de servicio, mensajes administrativos y otro tipo de información. A parte de los mensajes que tu puedes recibir, la aplicación envía notificaciones de servicio a aquellos contactos, de Facebook o de otras Redes Sociales, como Google u otras direcciones de correo, a los cuales tu hayas dado acceso a examinar los contenidos de tu perfil en Facebook, enviándoles cualquier tipo de notificación si se detecta algún tipo de anomalía en los controles que ejecuta la aplicación. Estas notificaciones y los datos a terceros de los que puede hacer uso la Aplicación son responsabilidad propia del titular de la cuenta de Facebook a través de la cual se accede a nuestra Aplicación. Estas notificaciones pueden ser desactivadas en el módulo de control de la Aplicación en cualquier instante. También el propio contacto puede desactivarlas manualmente en el momento en el que se le envía la aceptación de términos para recibir las notificaciones de tu información.</p>\n<p>De momento, nuestra Aplicación no está disponible para acceso desde dispositivos móviles, pero, en el caso de que en un futuro próximo, a corto plazo, estuviera disponible, se aplicarán todas las condiciones de éste documento a su uso en dichos dispositivos. No utilices la Aplicación de un modo que pueda distraerte y que te impida cumplir las leyes de tráfico o de seguridad.</p>\n<br>\n<li><b>Tu cuenta de Facebook</b></li>\n<p>Para utilizar nuestra Aplicación es necesario que tengas una cuenta en Facebook. Para ello, será necesario que crees tu mismo, en caso de no estar en disposición de una, tu propia cuenta y aceptar los permisos de acceso a la Aplicación.</p>\n<p>Para proteger tu cuenta de Facebook, mantén la confidencialidad de tu contraseña. Eres responsable de la actividad que se desarrolle en tu cuenta de Facebook o través de ella, tal es el caso de nuestra Aplicación. Intenta no reutilizar la contraseña de tu cuenta de Facebook en otro tipo de aplicaciones a terceros o en alguna otra Red Social. Si detectas cualquier tipo de uso no autorizado de tu cuenta de Facebook, sigue las instrucciones que pone Facebook a tu disposición para solucionarlo lo antes posible.</p>\n<br>\n<li><b>Protección de la privacidad y de los derechos de autor</b></li>\n<p>Nuestra Aplicación aplicará la política de privacidad y de tratamiento de datos personales enmarcada en las políticas de privacidad de Facebook. Si usas nuestra aplicación, aceptas que Facebook y nuestra Aplicación use tus datos totalmente conforme con a sus políticas de privacidad. Para más información, consulta previamente las políticas de privacidad y de seguridad de Facebook.</p>\n<p>Respondemos a las notificaciones de presuntas infracciones de los derechos de autor y cancelamos las cuentas de los usuarios que, dentro de nuestra Aplicación, comentan infracciones reiteradas de acuerdo con el proceso establecido en la ley española de propiedad intelectual, Real Decreto Legislativo 1/1996, de 12 de abril, publicado en el BOE num. 97, del 22 de abril de 1996, y todas sus modificaciones anteriores.</p>\n<br>\n<li><b>Tu contenido en nuestra Aplicación</b></li>\n<p>Nuestra Aplicación te permite enviar notificaciones sobre el contenido de tu información personal de Facebook (información publicada en tu muro, fechas de nacimiento de tus contactos junto con su imagen de perfil, información registrada en tu perfil acerca de tus relaciones, tanto familiares como personales, datos de tu perfil, configuración de tu cuenta). Si esta información es enviada, seguirás siendo el titular de éstos datos, es decir, lo que te pertenece es tuyo. </p>\n<p>Toda la información que nuestra Aplicación almacena sobre ti es tuya, no vamos a compartirla con nadie más, ni vamos a hacer un uso fraudulento de la misma. Únicamente la almacenamos para poder procesarla y enviar a los contactos que tu selecciones las anomalías que la Aplicación detecte en tus datos. Así, tus contactos podrán estar convenientemente informados de los posibles ataques que puedas recibir por parte de perfiles de Facebook que actúan en ésta Red Social como acosadores. </p>\n<p>En ningún momento nuestra Aplicación contendrá información que no sea la propia de Facebook, es decir, la aplicación no recibe datos de otro tipo que no sean los propios que Facebook almacena sobre ti y, sobre los cuales, tu has dado consentimiento para que ésta acceda a ellos. </p>\n<p>Para que nuestra Aplicación pueda acceder a tus datos, deberás consentir este acuerdo legal y los permisos que Facebook pone a nuestra disposición, los cuales pueden ser revisados en tu página de configuración de aplicaciones de Facebook. Si alguno de éstos permisos no son de tu agrado o no deseas que accedamos a cierta información que tu consideres que no es necesario que revisemos, bastará con que desactives los permisos en dicha página que no desees que la Aplicación tenga sobre tu información.</p>\n<p>Te recomendamos que revises la política de privacidad de Facebook para que puedas tener un conocimiento más profundo de los datos que se almacenan en tu perfil y de los usos que Facebook puede hacer con ellos.</p>\n<br>\n<li><b>Acerca del software de nuestra Aplicación</b></li>\n<p>Nuestra aplicación se ejecuta en el entorno Web de Facebook. A parte de los controles de Seguridad de Facebook, nosotros ponemos a tu disposición un servicio, sobre el cual se ejecuta la aplicación, en el que tu seguridad y la seguridad de tus datos está garantizada mediante certificados digitales que así lo atestiguan y que tú mismo podrás comprobar en la barra de direcciones de tu navegador.</p>\n<p>Podrás utilizar nuestra Aplicación gracias a la licencia que te concede Facebook para utilizar su Red Social. El contenido de la misma puede ser consultada en tu página de perfil.</p>\n<p>Nuestra aplicación realiza una serie de acciones:\n<ul> \n<li>Cuando aceptas el contenido de éste documento, el cual recomendamos profundamente sea leido en su totalidad, la Aplicación descarga todos los datos de tu perfil, a los cuales tu hayas dado los permisos necesarios para que los podamos almacenar, a sus sistemas de almacenamiento.</li>\n<li>La Aplicación pone a tu disposición un módulo de control, al cual sólo se podrá acceder desde Facebook, en el cual podrás realizar las siguientes acciones:\n<ul><li>Puedes seleccionar de tus contactos de Facebook, de tu cuenta de Gmail, o de otras cuentas de correo electrónico, aquellos que desees que controlen tu información dentro de Facebook.</li>\n<li>Una vez seleccionados, podrás determinar qué tipo de control quieres que se realice sobre tu contenido. Por cada control podrás seleccionar los contactos que verifiquen tu información y el intervalo regular de tiempo sobre el cual se ejecutarán estos controles y enviarán notificaciones a tus contactos en caso de que se detecten anomalías.</li>\n<li>En cualquier momento que tu desees podrás acceder al módulo de control y modificar las propiedades de los controles de la forma que desees, controlando en todo momento el contenido que se quieres que se verifique y que tus contactos puedan observar.</li></ul></li>\n<li>La Aplicación cuenta con un módulo offline que se ejecutará diariamente y que descargará toda tu nueva información registrada en Facebook, de acuerdo con tus permisos, y enviará notificaciones a tus contactos seleccionados si se cumple el intervalo de tiempo bajo el cual has definido la ejecución de tus controles en la Aplicación. Este módulo permitirá a la Aplicación tener actualizada en todo momento tu información y hacer análisis más precisos sobre tu actividad.</li></ul></p>\n<p>En ningún momento nuestra Aplicación accederá a datos de terceras personas, es decir, si tu das consentimiento a la Aplicación, nosotros podemos acceder a los datos de tus contactos que Facebook pone a disposición de las aplicaciones, por lo que los datos a terceros están restringidos a las políticas que Facebook administra sobre ellos. En ningún momento podremos descargar contenido de sus muros o su información personal, ya que no forma parte de la política de la Aplicación.</p>\n<br>\n<li><b>Como modificar y cancelar nuestra Aplicación</b></li>\n<p>Al ser una aplicación que se ejecuta dentro del marco de aplicaciones de Facebook, no disponemos de mecanismos para eliminar la Aplicación en tu perfil. Deberás ser tu el que elimine nuestra aplicación desde la configuración de aplicaciones que Facebook pone a tu disposición en tu página de perfil.</p>\n<p>El hecho de que elimines de tu perfil de tu actividad no implica que tus datos sean borrados de nuestro sistema de almacenamiento. Podemos determinar si un usuario nos ha denegado permisos para acceder a su información pero no podemos determinar con exactitud si ha eliminado la aplicación. Para que tus datos sean borrados de nuestra aplicación, deberás ponerte en contacto con los administradores de nuestra Aplicación y ellos se ocuparán de eliminar toda tu información.</p>\n<p>Consideramos que eres el propietario de todos tus datos en nuestra Aplicación, por lo que, si interrumpimos alguna vez nuestra Aplicación, serás avisado convenientemente y tus datos serán eliminados de nuestra Aplicación para que nadie pueda hacer un uso indebido de ellos.</p>\n<br>\n<li><b>Nuestras garantías y renuncias de responsabilidad</b></li>\n<p>La Aplicación te garantiza que tus datos estén siempre protegidos y que nadie más acceda a ellos. Sólo se accederá a aquellos datos a los cuales tu hayas dado consentimiento formal para que sean consultados. </p>\n<p>Tu seguridad es muy importante para nosotros, por lo que haremos todo lo que esté en nuestra mano para que tus datos estén totalmente seguros en nuestros sistemas de almacenamiento.</p>\n<p>Nuestra Aplicación es responsable de nuestro contenido, de sus datos y del uso que hagamos de ellos dentro de Facebook. Esto incluye la obligación de asegurar que nuestra aplicación o su uso cumpla las normas de Facebook y sus normas de publicidad. </p>\n<p>La política de Facebook para sus aplicaciones se cumplirá de la siguiente forma:\n<ul>\n<li>Nuestra Aplicación sólo podrá solicitar los datos que necesite para que sea correctamente funcional, nunca datos que no apliquen para el desarrollo de su actividad.</li>\n<li>Disponemos de una politica de privacidad, contenida en éste documento, para garantizar e indicar a nuestros usuarios los datos que vamos a utilizar y de qué forma los utilizaremos.</li>\n<li>Nunca, bajo ningún concepto, compartiremos tus datos con usuarios ni aplicaciones a terceros.</li>\n<li>Eliminaremos tus datos si nos lo pides, mediante contacto con nuestros administradores de la Aplicación o con cualquier otro mecanismo, poniéndonos previamente en contacto contigo y facilitándote, en la medida de lo posible, éste trámite. Haremos todo lo posible para que estés a gusto utilizando nuestra Aplicación.</li>\n<li>No pondremos ninguna información tuya públicamente ni en ningún medio de comunicación.\n<li>No venderemos tus datos a nadie, sería nuestro fin!</li>\n<li>Facebook podrá limitar nuestra actividad de forma unilateral si comprueba que no estamos haciendo un buen uso de tus datos.\n</li></ul></p>\n<p>El usuario de la aplicación aceptará que sus datos serán procesados y almacenados en las instalaciones que la Aplicación ponga a su disposición. Actualmente, estos datos serán procesados en España, aunque dependiendo de las características del mercado de las telecomunicaciones a nivel mundial, esto podría cambiar, siendo responsabilidad de la Aplicación su total mantenimiento. Si en cualquier momento pudiera producirse un cambio de éstas características, serías convenientemente informado de toda la información referida a éste respecto. </p>\n<p>La aplicación no se hace responsable de tu contenido de Facebook. Cualquier dato que recibamos de Facebook será responsabilidad de éste y, por consiguiente, del usuario del cual lo recibamos. Podemos restringir la actividad de un usuario en nuestra Aplicación si detectamos un uso indebido de la misma, pero en ningún momento será nuestra responsabilidad el uso que se haga de la misma. </p>\n<br>\n<li><b>Otras condiciones</b></li>\n<p>Podemos cambiar esta Política de Privacidad si detectamos cualquier tipo de agujero legal o Facebook cambia su política con respecto a las aplicaciones contenidas. Si hacemos cualquier cambio en éste acuerdo, serás convenientemente informado por los mecanismos que disponemos y deberás de nuevo aceptar dichos cambios. </p></ol>','Aceptar','Cancelar','Condiciones y T&eacute;rminos de Aceptaci&oacute;n','Configuraci&oacute;n','Configuracion;Angeles;Vigilantes;','Guardar','Selecciona tus Angeles','Elige tus Angeles de entre tus contactos en Facebook','Importa un contacto de tu cuenta Gmail;Añade otros contactos;','Nombre','Email','Importar ','Ver Todos;Seleccionados;No seleccionados','Eligele como tu Angel','Configura tus Vigilantes','Vigilantes','Control de Muro;Control de Amigos;Control de Privacidad;Control de Visitas;','Descripcion del Vigilante;Cuando est&aacute activado, realiza un control de las edades de cada amigo, enviando cualquier incidencia al Tutor de la aplicaci&oacuten;Cuando est&aacute activado, comprueba que no exista lenguaje ofensivo en los mensajes del muro, enviando cualquier incidencia al Tutor de la aplicaci&oacuten;Cuando est&aacute activado, comprueba que el nivel de privacidad del usuario no sea demasiado permisivo, enviando cualquier incidencia al Tutor de la aplicaci&oacuten;Cuando est&aacute activado, comprueba el nivel de visitas que ha tenido el perfil del usuario durante el periodo de tiempo transcurrido desde la &uacuteltima comprobaci&oacuten del perfil, enviando cualquier incidencia al Tutor de la aplicaci&oacuten;','Frecuencia de Notificaciones','Cada día;Cada semana;Cada dos semanas;Cada mes;Cada dos meses;Cada seis meses;Cada año;','Angeles','SNSAngelGuardFB: Autorización para el envío de notificaciones','desea que ústed controle su actividad social en Facebook.\n\n                                    Si acepta dicha autorización, se le enviará una serie de notificaciones para que pueda realizar un seguimiento personalizado de su actividad social.\n\n                                    Si por el contrario, cancela la autorización, no se le volverá a enviar ninguna notificación.','Aceptar','Cancelar',' es una herramienta software desarrollada por la Universidad de Alcalá de Henares, España.','SNSAngelGuardFB: Identificación de Usuario','Nombre','Cerrar','Importar un contacto de tu cuenta Google','Selecciona un contacto de tu cuenta Google','Iniciar Sesion Google','Nombre','Email','Aceptar','Cancelar','Necesitas ayuda?;Ayuda; ¿Qué es un Angel?;Un Ángel es una persona que recibe informes de la actividad que usted genera en Facebook. Cada vez que se produzca una anomalía en la información de su Perfil Social, su Ángel recibirá detalladamente los datos de ésta.; ¿Qué es un Vigilante?;Un Vigilante en una herramienta software que se ejecuta dentro de Facebook. Detecta las posibles amenazas dentro de su perfil y elabora informes que enviará a sus Angeles para que éstos tengan constancia de su actividad social.<br><br>Actualmente, existen cuatro Vigilantes: <br><br>- Filtro de Control de Muro: Realiza un control sobre los mensajes del muro del usuario en cuestión, detectando el vocabulario ofensivo que pudiera existir. <br><br>- Filtro de Control de Amigos: Realiza un control sobre los amigos del usuario en cuestión, detectando cuales de ellos han indicado su edad, y si ésta sobrepasa un límite determinado.<br><br>- Filtro de Control de Privacidad: Realiza un control sobre la configuración de la privacidad del usuario, detectando si su perfil es demasiado permisivo para acceder a su información personal. <br><br>- Filtro de Control de Visitas: Realiza un control sobre las últimas visitas y a quién pertenecen, sobre el perfil del usuario.; Configuración de un Vigilante;Para configurar un Vigilante son necesarias dos acciones<br><br>1. Seleccionar un Ángel o Ángeles a los que quieres que controlen tu actividad. Los Ángeles son seleccionados en la pestaña \"Ángeles\" y posteriormente, en la pestaña \"Vigilantes\", se podrán asociar de manera individual a cada filtro.<br><br>2. Configurar la frecuencia a la que SNSAngelGuard enviará informes a tus Ángeles, informándoles de tu actividad en Facebook.<br>;','Debes elegir al menos un angel;Datos guardados correctamente;Guardando datos...;Cargando datos...;Espere, por favor...;Angel correctamente eliminado!;Angel correctamente guardado!;Error;Se ha producido un error en la aplicacion... ;Detalles; Debe introducir un email; Email no valido; No se ha podido compartir el post con tu amigo. El angel no ha sido guardado;Su navegador web no es compatible con la aplicacion. Para que todas las funciones se ejecuten correctamente, por favor, utilice alguno de los siguientes navegadores:<P><OL><LI>Firefox 5<LI>Google Chrome</OL></P>','Información','Datos guardados correctamente;Usuario ya confirmado anteriormente;Error: Angel no existente;','SNSAngelGuard Aviso: Angel Eliminado; le ha borrado de su lista de Angeles;','SNSAngelGuard: Notificación de Actividad; SNSAngelGuad Notificación: Usuario ;En el comentario ; del post ; existe lenguaje ofensivo. ;No se han encontrado anomalías en el muro del usuario.; no ha especificado su fecha de nacimiento. ; es demasiado mayor para ;No se han encontrado anomalías en las amistades del usuario.;No se han encontrado anomalías en la configuración de Privacidad del usuario.;No se han encontrado anomalías en las últimas visitas al perfil del usuario.;','Añadir nuevo Angel;Editar;Cancelar;Eliminar;Guardar;','Ranking de tus amigos de Facebook con los que compartes más contactos;Ranking de tus amigos de Facebook con los que compartes menos contactos;Numero de mensajes de tus amigos en tu muro de Facebook;','Autorización para el envio de notificaciones;Términos de uso;El usuario quiere que usted controle su actividad social en Facebook. Para más información, haga click en el título de éste post.;');
/*!40000 ALTER TABLE `locale_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_facebook`
--

DROP TABLE IF EXISTS `location_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_facebook` (
  `id_location_facebook` int(11) NOT NULL,
  `CITY` varchar(45) DEFAULT NULL,
  `STATE` varchar(45) DEFAULT NULL,
  `COUNTRY` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_location_facebook`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_facebook`
--

LOCK TABLES `location_facebook` WRITE;
/*!40000 ALTER TABLE `location_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `location_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_facebook_has_user_facebook`
--

DROP TABLE IF EXISTS `location_facebook_has_user_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_facebook_has_user_facebook` (
  `location_facebook_id_location_facebook` int(11) NOT NULL,
  `user_facebook_id_user_facebook` varchar(100) NOT NULL,
  PRIMARY KEY (`location_facebook_id_location_facebook`,`user_facebook_id_user_facebook`),
  KEY `fk_location_facebook_has_user_facebook_user_facebook1` (`user_facebook_id_user_facebook`),
  CONSTRAINT `fk_location_facebook_has_user_facebook_location_facebook1` FOREIGN KEY (`location_facebook_id_location_facebook`) REFERENCES `location_facebook` (`id_location_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_location_facebook_has_user_facebook_user_facebook1` FOREIGN KEY (`user_facebook_id_user_facebook`) REFERENCES `user_facebook` (`id_user_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_facebook_has_user_facebook`
--

LOCK TABLES `location_facebook_has_user_facebook` WRITE;
/*!40000 ALTER TABLE `location_facebook_has_user_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `location_facebook_has_user_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_facebook_has_user_facebook_CURRENT_LOCATION`
--

DROP TABLE IF EXISTS `location_facebook_has_user_facebook_CURRENT_LOCATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_facebook_has_user_facebook_CURRENT_LOCATION` (
  `location_facebook_id_location_facebook` int(11) NOT NULL,
  `user_facebook_id_user_facebook` varchar(100) NOT NULL,
  `ZIP` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`location_facebook_id_location_facebook`,`user_facebook_id_user_facebook`),
  KEY `fk_location_facebook_has_user_facebook_user_facebook2` (`user_facebook_id_user_facebook`),
  CONSTRAINT `fk_location_facebook_has_user_facebook_location_facebook2` FOREIGN KEY (`location_facebook_id_location_facebook`) REFERENCES `location_facebook` (`id_location_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_location_facebook_has_user_facebook_user_facebook2` FOREIGN KEY (`user_facebook_id_user_facebook`) REFERENCES `user_facebook` (`id_user_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_facebook_has_user_facebook_CURRENT_LOCATION`
--

LOCK TABLES `location_facebook_has_user_facebook_CURRENT_LOCATION` WRITE;
/*!40000 ALTER TABLE `location_facebook_has_user_facebook_CURRENT_LOCATION` DISABLE KEYS */;
/*!40000 ALTER TABLE `location_facebook_has_user_facebook_CURRENT_LOCATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privacy_facebook`
--

DROP TABLE IF EXISTS `privacy_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `privacy_facebook` (
  `id_privacy_facebook` int(11) NOT NULL,
  `UID` varchar(45) DEFAULT NULL,
  `stream_facebook_post_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id_privacy_facebook`),
  KEY `fk_privacy_facebook_stream_facebook1` (`stream_facebook_post_id`),
  CONSTRAINT `fk_privacy_facebook_stream_facebook1` FOREIGN KEY (`stream_facebook_post_id`) REFERENCES `stream_facebook` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privacy_facebook`
--

LOCK TABLES `privacy_facebook` WRITE;
/*!40000 ALTER TABLE `privacy_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `privacy_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relationship_facebook`
--

DROP TABLE IF EXISTS `relationship_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relationship_facebook` (
  `id_relationship_facebook` int(11) NOT NULL,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_relationship_facebook`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relationship_facebook`
--

LOCK TABLES `relationship_facebook` WRITE;
/*!40000 ALTER TABLE `relationship_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `relationship_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sample_likes_facebook`
--

DROP TABLE IF EXISTS `sample_likes_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sample_likes_facebook` (
  `id_sample_likes_facebook` int(11) NOT NULL,
  `UID` varchar(45) DEFAULT NULL,
  `likes_facebook_id_likes_facebook` int(11) NOT NULL,
  PRIMARY KEY (`id_sample_likes_facebook`),
  KEY `fk_sample_likes_facebook_likes_facebook1` (`likes_facebook_id_likes_facebook`),
  CONSTRAINT `fk_sample_likes_facebook_likes_facebook1` FOREIGN KEY (`likes_facebook_id_likes_facebook`) REFERENCES `likes_facebook` (`id_likes_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sample_likes_facebook`
--

LOCK TABLES `sample_likes_facebook` WRITE;
/*!40000 ALTER TABLE `sample_likes_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `sample_likes_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings_angels`
--

DROP TABLE IF EXISTS `settings_angels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings_angels` (
  `uid_angel` int(11) NOT NULL AUTO_INCREMENT,
  `id_angel` varchar(100) NOT NULL,
  `name_angel` varchar(200) NOT NULL,
  `img_angel` varchar(200) NOT NULL,
  `type_angel` varchar(10) NOT NULL,
  `accept_angel` varchar(1) NOT NULL,
  `user_prop_angel` varchar(100) NOT NULL,
  `confirm_angel` varchar(1) NOT NULL,
  `id_facebook` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`uid_angel`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings_angels`
--

LOCK TABLES `settings_angels` WRITE;
/*!40000 ALTER TABLE `settings_angels` DISABLE KEYS */;
INSERT INTO `settings_angels` VALUES (7,'josejavier.blecua@gmail.com','José de Pedro','../SNSAngelGuardFB/resources/perfilStandar.gif','G','1','100001105347886','1','');
/*!40000 ALTER TABLE `settings_angels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings_fltFriends`
--

DROP TABLE IF EXISTS `settings_fltFriends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings_fltFriends` (
  `user_settings_uid` varchar(100) NOT NULL,
  `frec_fltFriends` varchar(10) NOT NULL,
  `active_fltFriends` varchar(1) NOT NULL,
  `last_check` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_settings_uid`) USING BTREE,
  CONSTRAINT `fk_settings_fltFriends_user_settings_uid` FOREIGN KEY (`user_settings_uid`) REFERENCES `user_settings` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings_fltFriends`
--

LOCK TABLES `settings_fltFriends` WRITE;
/*!40000 ALTER TABLE `settings_fltFriends` DISABLE KEYS */;
INSERT INTO `settings_fltFriends` VALUES ('100001105347886','0','0','2014-05-04 19:24:08');
/*!40000 ALTER TABLE `settings_fltFriends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings_fltFriends_has_settings_angels`
--

DROP TABLE IF EXISTS `settings_fltFriends_has_settings_angels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings_fltFriends_has_settings_angels` (
  `settings_fltFriends_user_settings_uid` varchar(100) NOT NULL,
  `settings_angels_uid_angel` int(11) NOT NULL,
  PRIMARY KEY (`settings_fltFriends_user_settings_uid`,`settings_angels_uid_angel`) USING BTREE,
  KEY `fk_settings_fltFriends_has_settings_angels_uid_angel` (`settings_angels_uid_angel`),
  CONSTRAINT `fk_settings_fltFriends_has_settings_angels_uid_angel` FOREIGN KEY (`settings_angels_uid_angel`) REFERENCES `settings_angels` (`uid_angel`),
  CONSTRAINT `fk_settings_fltFriends_has_settings_angels_user_settings_uid` FOREIGN KEY (`settings_fltFriends_user_settings_uid`) REFERENCES `settings_fltFriends` (`user_settings_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings_fltFriends_has_settings_angels`
--

LOCK TABLES `settings_fltFriends_has_settings_angels` WRITE;
/*!40000 ALTER TABLE `settings_fltFriends_has_settings_angels` DISABLE KEYS */;
/*!40000 ALTER TABLE `settings_fltFriends_has_settings_angels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings_fltPriv`
--

DROP TABLE IF EXISTS `settings_fltPriv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings_fltPriv` (
  `user_settings_uid` varchar(100) NOT NULL,
  `frec_fltPriv` varchar(10) NOT NULL,
  `active_fltPriv` varchar(1) NOT NULL,
  `last_check` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_settings_uid`) USING BTREE,
  CONSTRAINT `fk_settings_fltPriv_user_settings_uid` FOREIGN KEY (`user_settings_uid`) REFERENCES `user_settings` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings_fltPriv`
--

LOCK TABLES `settings_fltPriv` WRITE;
/*!40000 ALTER TABLE `settings_fltPriv` DISABLE KEYS */;
INSERT INTO `settings_fltPriv` VALUES ('100001105347886','3','0','2014-05-04 19:24:08');
/*!40000 ALTER TABLE `settings_fltPriv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings_fltPriv_has_settings_angels`
--

DROP TABLE IF EXISTS `settings_fltPriv_has_settings_angels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings_fltPriv_has_settings_angels` (
  `settings_fltPriv_user_settings_uid` varchar(100) NOT NULL,
  `settings_angels_uid_angel` int(11) NOT NULL,
  PRIMARY KEY (`settings_fltPriv_user_settings_uid`,`settings_angels_uid_angel`) USING BTREE,
  KEY `fk_settings_fltPriv_has_settings_angels_uid_angel` (`settings_angels_uid_angel`),
  CONSTRAINT `fk_settings_fltPriv_has_settings_angels_uid_angel` FOREIGN KEY (`settings_angels_uid_angel`) REFERENCES `settings_angels` (`uid_angel`),
  CONSTRAINT `fk_settings_fltPriv_has_settings_angels_user_settings_uid` FOREIGN KEY (`settings_fltPriv_user_settings_uid`) REFERENCES `settings_fltPriv` (`user_settings_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings_fltPriv_has_settings_angels`
--

LOCK TABLES `settings_fltPriv_has_settings_angels` WRITE;
/*!40000 ALTER TABLE `settings_fltPriv_has_settings_angels` DISABLE KEYS */;
/*!40000 ALTER TABLE `settings_fltPriv_has_settings_angels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings_fltVist`
--

DROP TABLE IF EXISTS `settings_fltVist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings_fltVist` (
  `user_settings_uid` varchar(100) NOT NULL,
  `frec_fltVist` varchar(10) NOT NULL,
  `active_fltVist` varchar(1) NOT NULL,
  `last_check` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_settings_uid`) USING BTREE,
  CONSTRAINT `fk_settings_fltVist_user_settings_uid` FOREIGN KEY (`user_settings_uid`) REFERENCES `user_settings` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings_fltVist`
--

LOCK TABLES `settings_fltVist` WRITE;
/*!40000 ALTER TABLE `settings_fltVist` DISABLE KEYS */;
INSERT INTO `settings_fltVist` VALUES ('100001105347886','3','0','2014-05-04 19:24:08');
/*!40000 ALTER TABLE `settings_fltVist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings_fltVist_has_settings_angels`
--

DROP TABLE IF EXISTS `settings_fltVist_has_settings_angels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings_fltVist_has_settings_angels` (
  `settings_fltVist_user_settings_uid` varchar(100) NOT NULL,
  `settings_angels_uid_angel` int(11) NOT NULL,
  PRIMARY KEY (`settings_fltVist_user_settings_uid`,`settings_angels_uid_angel`) USING BTREE,
  KEY `fk_settings_fltVist_has_settings_angels_uid_angel` (`settings_angels_uid_angel`),
  CONSTRAINT `fk_settings_fltVist_has_settings_angels_uid_angel` FOREIGN KEY (`settings_angels_uid_angel`) REFERENCES `settings_angels` (`uid_angel`),
  CONSTRAINT `fk_settings_fltVist_has_settings_angels_user_settings_uid` FOREIGN KEY (`settings_fltVist_user_settings_uid`) REFERENCES `settings_fltVist` (`user_settings_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings_fltVist_has_settings_angels`
--

LOCK TABLES `settings_fltVist_has_settings_angels` WRITE;
/*!40000 ALTER TABLE `settings_fltVist_has_settings_angels` DISABLE KEYS */;
/*!40000 ALTER TABLE `settings_fltVist_has_settings_angels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings_fltWall`
--

DROP TABLE IF EXISTS `settings_fltWall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings_fltWall` (
  `user_settings_uid` varchar(100) NOT NULL,
  `frec_fltWall` varchar(100) NOT NULL,
  `active_fltWall` varchar(1) NOT NULL,
  `last_check` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_settings_uid`) USING BTREE,
  CONSTRAINT `fk_settings_fltWall_user_settings_uid` FOREIGN KEY (`user_settings_uid`) REFERENCES `user_settings` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings_fltWall`
--

LOCK TABLES `settings_fltWall` WRITE;
/*!40000 ALTER TABLE `settings_fltWall` DISABLE KEYS */;
INSERT INTO `settings_fltWall` VALUES ('100001105347886','0','0','2014-05-04 19:24:08');
/*!40000 ALTER TABLE `settings_fltWall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings_fltWall_has_settings_angels`
--

DROP TABLE IF EXISTS `settings_fltWall_has_settings_angels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings_fltWall_has_settings_angels` (
  `settings_fltWall_user_settings_uid` varchar(100) NOT NULL,
  `settings_angels_uid_angel` int(11) NOT NULL,
  PRIMARY KEY (`settings_fltWall_user_settings_uid`,`settings_angels_uid_angel`) USING BTREE,
  KEY `fk_settings_fltWall_has_settings_angels_uid_angel` (`settings_angels_uid_angel`),
  CONSTRAINT `fk_settings_fltWall_has_settings_angels_uid_angel` FOREIGN KEY (`settings_angels_uid_angel`) REFERENCES `settings_angels` (`uid_angel`),
  CONSTRAINT `fk_settings_fltWall_has_settings_angels_user_settings_uid` FOREIGN KEY (`settings_fltWall_user_settings_uid`) REFERENCES `settings_fltWall` (`user_settings_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings_fltWall_has_settings_angels`
--

LOCK TABLES `settings_fltWall_has_settings_angels` WRITE;
/*!40000 ALTER TABLE `settings_fltWall_has_settings_angels` DISABLE KEYS */;
/*!40000 ALTER TABLE `settings_fltWall_has_settings_angels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stream_facebook`
--

DROP TABLE IF EXISTS `stream_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stream_facebook` (
  `post_id` varchar(100) NOT NULL,
  `viewer_id` varchar(100) DEFAULT NULL,
  `app_id` varchar(100) DEFAULT NULL,
  `source_id` varchar(100) DEFAULT NULL,
  `updated_time` timestamp NULL DEFAULT NULL,
  `created_time` timestamp NULL DEFAULT NULL,
  `filter_key` varchar(100) DEFAULT NULL,
  `attribution` varchar(100) DEFAULT NULL,
  `actor_id` varchar(100) DEFAULT NULL,
  `target_id` varchar(100) DEFAULT NULL,
  `message` mediumtext CHARACTER SET utf8,
  `app_data` text,
  `attachment` text,
  `type` varchar(100) DEFAULT NULL,
  `permalink` varchar(300) DEFAULT NULL,
  `xid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stream_facebook`
--

LOCK TABLES `stream_facebook` WRITE;
/*!40000 ALTER TABLE `stream_facebook` DISABLE KEYS */;
INSERT INTO `stream_facebook` VALUES ('100001105347886_156557411057769','100001105347886',NULL,'100001105347886','2010-12-21 10:23:35','2010-12-21 10:23:35','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/156557411057769',''),('100001105347886_156557524391091','100001105347886',NULL,'100001105347886','2010-12-21 10:24:34','2010-12-21 10:24:34','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/156557524391091',''),('100001105347886_169497653097078','100001105347886',NULL,'100001105347886','2011-02-09 19:07:16','2011-02-09 19:07:16','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}','8','https://www.facebook.com/jose.blecuadepedro/posts/169497653097078:0',''),('100001105347886_172891229424387','100001105347886',NULL,'100001105347886','2011-02-25 09:06:51','2011-02-24 09:19:59','',NULL,'100001105347886',NULL,'Primer pensamiento...','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/172891229424387',''),('100001105347886_172891276091049','100001105347886',NULL,'100001105347886','2011-02-24 09:20:11','2011-02-24 09:20:11','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_173115946068582','100001105347886',NULL,'100001105347886','2011-02-25 11:42:50','2011-02-25 09:42:59','',NULL,'100001105347886',NULL,'Segundo día de trabajo con las trazas...','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/173115946068582',''),('100001105347886_173115976068579','100001105347886',NULL,'100001105347886','2011-02-25 09:43:09','2011-02-25 09:43:09','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_175155639197946','100001105347886',NULL,'100001105347886','2011-03-06 13:06:32','2011-03-06 13:06:32','',NULL,'100001105347886',NULL,'alemanita hija de puta','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/175155639197946',''),('100001105347886_175182775861899','100001105347886',NULL,'100001105347886','2011-03-06 16:05:07','2011-03-06 15:46:27','',NULL,'100001105347886',NULL,'hija de la grandisima puta','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/175182775861899',''),('100001105347886_175186225861554','100001105347886',NULL,'100001105347886','2011-03-06 16:05:07','2011-03-06 16:05:07','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_176056522441191','100001105347886',NULL,'100001105347886','2011-03-10 10:51:47','2011-03-10 09:17:54','',NULL,'100001105347886',NULL,'Vaya mierda, qué gilipollez...','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/176056522441191',''),('100001105347886_176057149107795','100001105347886',NULL,'100001105347886','2011-03-10 09:22:47','2011-03-10 09:22:47','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_176067402440103','100001105347886',NULL,'100001105347886','2011-03-10 11:00:41','2011-03-10 10:51:13','',NULL,'100001105347886',NULL,'Este mundo no es lugar para el viejo Jan Valjean','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/176067402440103',''),('100001105347886_176298589083651','100001105347886',NULL,'100001105347886','2011-03-11 10:30:08','2011-03-11 10:30:08','',NULL,'100001105347886',NULL,'Vamos a follar algún día?','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/176298589083651',''),('100001105347886_179718162075027','100001105347886',NULL,'100001105347886','2011-03-26 09:42:47','2011-03-26 09:42:47','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_179718375408339','100001105347886',NULL,'100001105347886','2011-03-26 09:44:21','2011-03-26 09:44:21','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_179718922074951','100001105347886',NULL,'100001105347886','2011-03-26 09:49:17','2011-03-26 09:49:17','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_191463700900473','100001105347886',NULL,'100001105347886','2011-06-10 12:04:42','2011-06-10 12:04:42','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}','8','',''),('100001105347886_191463784233798','100001105347886',NULL,'100001105347886','2011-05-17 07:49:30','2011-05-17 07:49:30','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}','56','',''),('100001105347886_201841179862725','100001105347886',NULL,'100001105347886','2011-06-27 09:13:42','2011-06-27 09:13:42','',NULL,'1050629888','100001105347886','Que pasa gilipollas!','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/201841179862725',''),('100001105347886_237998572913652','100001105347886','179105958774916','100001105347886','2011-09-14 05:08:40','2011-09-14 05:08:40','','SNSAngelGuard','100001105347886',NULL,'','','{\"description\":\"\",\"name\":\"\",\"caption\":\"SNSAngelGuard, un control parental para Facebook!\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbcdn-photos-e-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851579_592529734099201_98520436_n.gif\",\"type\":\"link\",\"href\":\"https://apps.facebook.com/snsangelguard/?fb_source=feed\"}]}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/237998572913652',''),('100001105347886_452331934813647','100001105347886',NULL,'100001105347886','2012-11-10 12:39:47','2012-11-10 12:39:47','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/452331934813647',''),('100001105347886_458155507564623','100001105347886',NULL,'100001105347886','2012-11-26 18:43:32','2012-11-26 18:43:32','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_460440287336145','100001105347886',NULL,'100001105347886','2012-12-02 20:27:04','2012-12-02 19:19:00','',NULL,'100001105347886',NULL,'Soy un capullo toca pelotas... de la misma familia pelotez... \"prueba proyecto, no os asusteis\"','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/460440287336145',''),('100001105347886_460455477334626','100001105347886',NULL,'100001105347886','2012-12-02 19:57:49','2012-12-02 19:57:49','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_462916140421893','100001105347886',NULL,'100001105347886','2012-12-08 18:17:05','2012-12-08 18:17:05','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}','8','https://www.facebook.com/jose.blecuadepedro/posts/462916140421893:0',''),('100001105347886_503623379684502','100001105347886',NULL,'100001105347886','2011-02-24 09:20:03','2011-02-24 09:20:03','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_506238176089689','100001105347886',NULL,'100001105347886','2013-03-30 19:57:19','2013-03-10 20:34:12','',NULL,'100001105347886',NULL,'gilipollas','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/506238176089689',''),('100001105347886_506255676087939','100001105347886',NULL,'100001105347886','2013-03-10 21:32:15','2013-03-10 21:32:15','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_513795395333967','100001105347886',NULL,'100001105347886','2013-03-30 19:47:23','2013-03-30 19:47:23','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_513800032000170','100001105347886','6628568379','100001105347886','2013-03-30 20:09:56','2013-03-30 20:02:13','',NULL,'1050629888','100001105347886','Hola hola!!!','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/513800032000170',''),('100001105347886_520019424711564','100001105347886',NULL,'100001105347886','2013-04-14 16:15:52','2013-04-14 16:15:52','',NULL,'1050629888','100001105347886','Mas pruebas capullin????','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/520019424711564',''),('100001105347886_522100397836800','100001105347886',NULL,'100001105347886','2013-04-19 15:17:48','2013-04-19 15:17:48','',NULL,'1050629888','100001105347886','Tengo nuevo ordenador capullo!!!','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/522100397836800',''),('100001105347886_525308034182703','100001105347886',NULL,'100001105347886','2013-04-28 14:02:11','2013-04-28 14:00:44','',NULL,'1050629888','100001105347886','Siempre estamos de pruebas, me gustaría que ahora si que lo cogiera...','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/525308034182703',''),('100001105347886_531994746847365','100001105347886',NULL,'100001105347886','2013-05-15 11:21:52','2013-05-15 11:21:52','',NULL,'100001105347886',NULL,'','','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/531994746847365',''),('100001105347886_531994766847363','100001105347886',NULL,'100001105347886','2013-05-15 11:21:55','2013-05-15 11:21:55','',NULL,'100001105347886',NULL,'','','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/531994766847363',''),('100001105347886_531994780180695','100001105347886',NULL,'100001105347886','2013-05-15 11:22:00','2013-05-15 11:22:00','',NULL,'100001105347886',NULL,'','','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/531994780180695',''),('100001105347886_531994803514026','100001105347886',NULL,'100001105347886','2013-05-15 11:22:09','2013-05-15 11:22:09','',NULL,'100001105347886',NULL,'','','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/531994803514026',''),('100001105347886_531994863514020','100001105347886','6628568379','100001105347886','2013-05-15 11:22:28','2013-05-15 11:22:28','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_533364776710362','100001105347886',NULL,'100001105347886','2013-05-19 11:38:19','2013-05-19 11:33:16','',NULL,'100001105347886',NULL,'Era de esperar... Nos echamos las manos a la cabeza cuando España decide dar más dinero para el festival de Eurovisión pero luego todos los españoles estamos deseando que gane... La diferencia está en que los primeros cinco clasificados han estado sonando por toda Europa desde Navidad, cosa que no se ha hecho en España( no miento cuando digo que pocos de nosotros nos sabíamos nuestra propia canción). Eurovisión no se gana en la gala, aunque a mi personalmente la canción de éste año ni fu ni fa, se gana seis meses antes con la labor de promoción, para la cual hace falta un mayor presupuesto que el que actualmente estamos destinando. Hasta que este aspecto no cambie, no haremos un papel digno.','','{\"icon\":\"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yn/r/slh9ZgwfrSA.gif\",\"description\":\"El representante de España tan solo ha logrado ocho puntos, terminando de esa manera en la penúltima posición, sólo por delante de Irlanda.\",\"name\":\"\'El sueño de Morfeo\', representante de España, termina penúltimo con sólo ocho puntos en el Festival\",\"caption\":\"www.libertaddigital.com\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/safe_image.php?d=AQCAxIwW9Yseurdd&w=154&h=154&url=http%3A%2F%2Fs.libertaddigital.com%2Ffotos%2Fnoticias%2Fmorfeo.jpg\",\"type\":\"link\",\"href\":\"http://www.libertaddigital.com/chic/arte-cultura/2013-05-19/el-sueno-de-morfeo-representante-de-espana-termina-penultima-con-solo-ocho-puntos-en-el-festival-de-eurovision-2013-1276490534/?fb_comment_id=fbc_522327411159452_81088114_522506654474861\"}],\"href\":\"http://www.libertaddigital.com/chic/arte-cultura/2013-05-19/el-sueno-de-morfeo-representante-de-espana-termina-penultima-con-solo-ocho-puntos-en-el-festival-de-eurovision-2013-1276490534/?fb_comment_id=fbc_522327411159452_81088114_522506654474861\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/533364776710362',''),('100001105347886_538645579515615','100001105347886',NULL,'100001105347886','2013-06-01 11:43:53','2013-06-01 11:43:27','',NULL,'1050629888','100001105347886','Payaso!','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/538645579515615',''),('100001105347886_538645922848914','100001105347886',NULL,'100001105347886','2013-06-01 11:43:53','2013-06-01 11:43:53','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_547217328658440','100001105347886',NULL,'100001105347886','2013-06-20 21:57:51','2013-06-20 21:57:51','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_551232484923591','100001105347886',NULL,'100001105347886','2013-06-30 17:21:19','2013-06-30 14:39:16','',NULL,'100001105347886',NULL,'Ultimos días de pruebas...','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/551232484923591',''),('100001105347886_551233258256847','100001105347886',NULL,'100001105347886','2013-06-30 14:42:57','2013-06-30 14:42:57','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_581035031943336','100001105347886',NULL,'100001105347886','2013-09-07 11:57:21','2013-09-07 11:57:21','',NULL,'100001105347886',NULL,'Tomando los parámetros para EclipseLink desde un fichero de configuración externa acabada... Esto va viento en popa','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/581035031943336',''),('100001105347886_594991097214396','100001105347886','179105958774916','100001105347886','2013-10-08 19:24:40','2013-10-08 19:24:40','','','100001105347886',NULL,'','','{\"icon\":\"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yD/r/aS8ecmYRys0.gif\",\"description\":\"SNSAngelGuard es una aplicación que permite monotorizar toda la información de un perfil en Facebook y, a través de su información, realizar informes de actividad, detectando posibles ataques o amenazas de terceras personas.\\r\\n\\r\\nLa aplicación define como Ángeles a aquellas personas o perfiles de Face...\",\"name\":\"SNSAngelGuard\",\"caption\":\"Published by University of Alcalá, Spain\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbcdn-photos-f-a.akamaihd.net/hphotos-ak-prn1/t39.2081-0/851584_667619649923542_694410872_n.png\",\"type\":\"link\",\"href\":\"https://www.facebook.com/appcenter/snsangelguard\"}],\"href\":\"https://www.facebook.com/appcenter/snsangelguard\"}','80','https://www.facebook.com/jose.blecuadepedro/posts/594991097214396',''),('100001105347886_595843210462518','100001105347886','578871802160869','100001105347886','2013-10-10 18:16:21','2013-10-10 20:16:22','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_596952333684939','100001105347886','578871802160869','100001105347886','2013-10-12 18:40:47','2013-10-13 11:51:20','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_597520056961500','100001105347886','179105958774916','100001105347886','2013-10-13 18:11:30','2013-10-13 18:11:30','','SNSAngelGuard','100001105347886',NULL,'Hola Hola','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"http:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/angelConfirmation.jsp?par1=RCHAZmjREUEvkpU0-9mLcQ..&par2=57&par3=1\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"http:\\\\/\\\\/fbrell.com\\\\/f8.jpg\\\",\\\"width\\\":390,\\\"height\\\":366}],\\\"name\\\":\\\"Aceptaci?n de usuarios en SNSAngelGuardFB\\\",\\\"href\\\":\\\"http:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/angelConfirmation.jsp?par1=RCHAZmjREUEvkpU0-9mLcQ..&par2=57&par3=1\\\",\\\"caption\\\":\\\"Reference Documentation\\\",\\\"description\\\":\\\"Dialogs provide a simple, consistent interface for applications to interface with users.\\\"}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"http:\\\\/\\\\/fbrell.com\\\\/f8.jpg\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"http:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/angelConfirmation.jsp?par1=RCHAZmjREUEvkpU0-9mLcQ..&par2=57&par3=1\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"Dialogs provide a simple, consistent interface for applications to interface with users.\",\"name\":\"Aceptaci?n de usuarios en SNSAngelGuardFB\",\"caption\":\"Reference Documentation\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=6d26e0cc053c9b99fb1dd3f62ae1774a&src=http%3A%2F%2Ffbrell.com%2Ff8.jpg\",\"type\":\"link\",\"href\":\"http://localhost/SNSAngelGuardFB/angelConfirmation.jsp?par1=RCHAZmjREUEvkpU0-9mLcQ..&par2=57&par3=1\"}],\"href\":\"http://localhost/SNSAngelGuardFB/angelConfirmation.jsp?par1=RCHAZmjREUEvkpU0-9mLcQ..&par2=57&par3=1\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/597520056961500',''),('100001105347886_601589023221270','100001105347886',NULL,'100001105347886','2013-10-20 11:19:22','2013-10-20 11:19:22','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_601740276539478','100001105347886','179105958774916','100001105347886','2013-10-20 16:26:03','2013-10-20 16:26:03','','SNSAngelGuard','100001105347886',NULL,'prueba','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"http:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=68&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"http:\\\\/\\\\/fbrell.com\\\\/f8.jpg\\\",\\\"width\\\":390,\\\"height\\\":366}],\\\"name\\\":\\\"Aceptaci?n de usuarios en SNSAngelGuardFB\\\",\\\"href\\\":\\\"http:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=68&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\\\",\\\"caption\\\":\\\"Reference Documentation\\\",\\\"description\\\":\\\"Dialogs provide a simple, consistent interface for applications to interface with users.\\\"}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"http:\\\\/\\\\/fbrell.com\\\\/f8.jpg\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"http:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=68&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"Dialogs provide a simple, consistent interface for applications to interface with users.\",\"name\":\"Aceptaci?n de usuarios en SNSAngelGuardFB\",\"caption\":\"Reference Documentation\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=6d26e0cc053c9b99fb1dd3f62ae1774a&src=http%3A%2F%2Ffbrell.com%2Ff8.jpg\",\"type\":\"link\",\"href\":\"http://localhost/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=68&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\"}],\"href\":\"http://localhost/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=68&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/601740276539478',''),('100001105347886_601746206538885','100001105347886','179105958774916','100001105347886','2013-10-20 16:38:44','2013-10-20 16:38:44','','SNSAngelGuard','100001105347886',NULL,'Mas pruebas','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"http:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=70&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"http:\\\\/\\\\/fbrell.com\\\\/f8.jpg\\\",\\\"width\\\":390,\\\"height\\\":366}],\\\"name\\\":\\\"Aceptaci?n de usuarios en SNSAngelGuardFB\\\",\\\"href\\\":\\\"http:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=70&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\\\",\\\"caption\\\":\\\"Reference Documentation\\\",\\\"description\\\":\\\"Dialogs provide a simple, consistent interface for applications to interface with users.\\\"}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"http:\\\\/\\\\/fbrell.com\\\\/f8.jpg\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"http:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=70&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"Dialogs provide a simple, consistent interface for applications to interface with users.\",\"name\":\"Aceptaci?n de usuarios en SNSAngelGuardFB\",\"caption\":\"Reference Documentation\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=6d26e0cc053c9b99fb1dd3f62ae1774a&src=http%3A%2F%2Ffbrell.com%2Ff8.jpg\",\"type\":\"link\",\"href\":\"http://localhost/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=70&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\"}],\"href\":\"http://localhost/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=70&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/601746206538885',''),('100001105347886_601785956534910','100001105347886','179105958774916','100001105347886','2013-10-20 18:24:29','2013-10-20 18:24:29','','SNSAngelGuard','100001105347886',NULL,'Parece que va funcionando','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=71&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo75.gif\\\"}],\\\"name\\\":\\\"Autorizaci\\\\u00f3n para el envio de notificaciones\\\",\\\"href\\\":\\\"https:\\\\/\\\\/localhost\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=71&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\\\",\\\"caption\\\":\\\"T\\\\u00e9rminos de uso\\\",\\\"description\\\":\\\"El usuario quiere que usted controle su actividad social en Facebook. Para m\\\\u00e1s informaci\\\\u00f3n, haga click en el t\\\\u00edtulo de \\\\u00e9ste post.\\\"}\",\"photo_ids\":[],\"images\":\"[]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"fb_object_type\":\"\",\"description\":\"El usuario quiere que usted controle su actividad social en Facebook. Para más información, haga click en el título de éste post.\",\"name\":\"Autorización para el envio de notificaciones\",\"caption\":\"Términos de uso\",\"properties\":[],\"media\":[],\"href\":\"https://localhost/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=71&uidPublicUser=RCHAZmjREUEvkpU0-9mLcQ..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/601785956534910',''),('100001105347886_604772489569590','100001105347886','179105958774916','100001105347886','2013-10-27 16:57:04','2013-10-27 16:57:04','','SNSAngelGuard','1050629888','100001105347886','Prueba mi aplicación!!!','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=18&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\",\\\"width\\\":200,\\\"height\\\":200}],\\\"name\\\":\\\"Autoritation to send notifications\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=18&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"caption\\\":\\\"Agree with terms and conditions\\\",\\\"description\\\":\\\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\\\",\\\"target\\\":\\\"<fb:name uid=\\\\\\\"100001105347886\\\\\\\" \\\\/>\\\",\\\"target_ids\\\":100001105347886}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=18&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\",\"name\":\"Autoritation to send notifications\",\"caption\":\"Agree with terms and conditions\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=847c25606e083bf64e668fc05079c830&src=https%3A%2F%2Fsnsangelguard.com%2FSNSAngelGuardFB%2Fresources%2Flogo200.png\",\"type\":\"link\",\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=18&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}],\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=18&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/604772489569590',''),('100001105347886_605382662841906','100001105347886','179105958774916','100001105347886','2013-10-28 20:52:33','2013-10-28 20:52:33','','SNSAngelGuard','1050629888','100001105347886','Te selecciono!!','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=24&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\",\\\"width\\\":200,\\\"height\\\":200}],\\\"name\\\":\\\"Autoritation to send notifications\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=24&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"caption\\\":\\\"Agree with terms and conditions\\\",\\\"description\\\":\\\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\\\",\\\"target\\\":\\\"<fb:name uid=\\\\\\\"100001105347886\\\\\\\" \\\\/>\\\",\\\"target_ids\\\":100001105347886}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=24&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\",\"name\":\"Autoritation to send notifications\",\"caption\":\"Agree with terms and conditions\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=847c25606e083bf64e668fc05079c830&src=https%3A%2F%2Fsnsangelguard.com%2FSNSAngelGuardFB%2Fresources%2Flogo200.png\",\"type\":\"link\",\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=24&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}],\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=24&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/605382662841906',''),('100001105347886_605888352791337','100001105347886',NULL,'100001105347886','2013-10-29 19:35:33','2013-10-29 19:35:33','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_611693345544171','100001105347886','179105958774916','100001105347886','2013-11-10 15:42:52','2013-11-10 15:42:52','','SNSAngelGuard','1050629888','100001105347886','Te elijo para que me tuteles','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=37&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\",\\\"width\\\":200,\\\"height\\\":200}],\\\"name\\\":\\\"Autoritation to send notifications\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=37&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"caption\\\":\\\"Agree with terms and conditions\\\",\\\"description\\\":\\\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\\\",\\\"target\\\":\\\"<fb:name uid=\\\\\\\"100001105347886\\\\\\\" \\\\/>\\\",\\\"target_ids\\\":100001105347886}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=37&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\",\"name\":\"Autoritation to send notifications\",\"caption\":\"Agree with terms and conditions\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=847c25606e083bf64e668fc05079c830&src=https%3A%2F%2Fsnsangelguard.com%2FSNSAngelGuardFB%2Fresources%2Flogo200.png\",\"type\":\"link\",\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=37&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}],\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=37&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/611693345544171',''),('100001105347886_616165061763666','100001105347886','179105958774916','100001105347886','2013-11-19 08:00:56','2013-11-19 08:00:56','','SNSAngelGuard','1050629888','100001105347886','Molaaaa','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=46&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\",\\\"width\\\":200,\\\"height\\\":200}],\\\"name\\\":\\\"Autoritation to send notifications\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=46&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"caption\\\":\\\"Agree with terms and conditions\\\",\\\"description\\\":\\\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\\\",\\\"target\\\":\\\"<fb:name uid=\\\\\\\"100001105347886\\\\\\\" \\\\/>\\\",\\\"target_ids\\\":100001105347886}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=46&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\",\"name\":\"Autoritation to send notifications\",\"caption\":\"Agree with terms and conditions\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=847c25606e083bf64e668fc05079c830&src=https%3A%2F%2Fsnsangelguard.com%2FSNSAngelGuardFB%2Fresources%2Flogo200.png\",\"type\":\"link\",\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=46&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}],\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=46&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/616165061763666',''),('100001105347886_618461898200649','100001105347886','578871802160869','100001105347886','2013-11-24 09:30:30','2013-11-24 22:25:35','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_619700108076828','100001105347886','578871802160869','100001105347886','2013-11-26 20:49:09','2013-11-26 22:25:45','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_620634221316750','100001105347886','578871802160869','100001105347886','2013-11-28 22:42:30','2013-11-28 22:42:30','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_621554747891364','100001105347886','578871802160869','100001105347886','2013-11-30 10:53:23','2013-12-01 08:31:18','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_622043947842444','100001105347886','578871802160869','100001105347886','2013-12-01 11:38:58','2013-12-01 15:39:53','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_622195367827302','100001105347886','179105958774916','100001105347886','2013-12-01 18:15:57','2013-12-01 18:15:57','','','100001105347886',NULL,'Esta es mi aplicación!! Es la primera así que os ruego que no me lo tengáis muy en cuenta!!','','{\"icon\":\"https://fbstatic-a.akamaihd.net/rsrc.php/v2/yD/r/aS8ecmYRys0.gif\",\"description\":\"SNSAngelGuard es una aplicación que permite monotorizar toda la información de un perfil en Facebook y, a través de su información, realizar informes de actividad, detectando posibles ataques o amenazas de terceras personas.\\r\\n\\r\\nLa aplicación define como Ángeles a aquellas personas o perfiles de Face...\",\"name\":\"SNSAngelGuard\",\"caption\":\"Published by University of Alcalá, Spain\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbcdn-photos-f-a.akamaihd.net/hphotos-ak-prn1/t39.2081-0/851584_667619649923542_694410872_n.png\",\"type\":\"link\",\"href\":\"https://www.facebook.com/appcenter/snsangelguard\"}],\"href\":\"https://www.facebook.com/appcenter/snsangelguard\"}','80','https://www.facebook.com/jose.blecuadepedro/posts/622195367827302',''),('100001105347886_622200491160123','100001105347886','179105958774916','100001105347886','2013-12-01 18:30:40','2013-12-01 18:30:40','','SNSAngelGuard','1050629888','100001105347886','Quieres ser mi angel??','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=56&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\",\\\"width\\\":200,\\\"height\\\":200}],\\\"name\\\":\\\"Autoritation to send notifications\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=56&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"caption\\\":\\\"Agree with terms and conditions\\\",\\\"description\\\":\\\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\\\",\\\"target\\\":\\\"<fb:name uid=\\\\\\\"100001105347886\\\\\\\" \\\\/>\\\",\\\"target_ids\\\":100001105347886}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=56&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\",\"name\":\"Autoritation to send notifications\",\"caption\":\"Agree with terms and conditions\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=847c25606e083bf64e668fc05079c830&src=https%3A%2F%2Fsnsangelguard.com%2FSNSAngelGuardFB%2Fresources%2Flogo200.png\",\"type\":\"link\",\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=56&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}],\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=56&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/622200491160123',''),('100001105347886_623486057698233','100001105347886','578871802160869','100001105347886','2013-12-04 12:28:19','2013-12-04 12:28:19','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_623669041013268','100001105347886',NULL,'100001105347886','2013-12-04 22:08:09','2013-12-04 21:10:00','',NULL,'1050629888','100001105347886','Entrega el proyecto ya!!','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/623669041013268',''),('100001105347886_623687604344745','100001105347886',NULL,'100001105347886','2013-12-04 22:08:09','2013-12-04 22:08:09','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_625175607529278','100001105347886','578871802160869','100001105347886','2013-12-08 10:53:54','2013-12-08 10:53:54','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_625177204195785','100001105347886',NULL,'100001105347886','2013-12-08 11:00:55','2013-12-08 11:00:55','',NULL,'100001105347886',NULL,'Buenos días!!!','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/625177204195785',''),('100001105347886_625179027528936','100001105347886',NULL,'100001105347886','2013-12-08 11:05:50','2013-12-08 11:05:50','',NULL,'100001105347886',NULL,'Penosos días!!!','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/625179027528936',''),('100001105347886_625188527527986','100001105347886',NULL,'100001105347886','2013-12-08 11:44:32','2013-12-08 11:44:32','',NULL,'100001105347886',NULL,'Seguimos con los penosos días!','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/625188527527986',''),('100001105347886_625201154193390','100001105347886',NULL,'100001105347886','2013-12-08 12:48:40','2013-12-08 12:32:03','',NULL,'1050629888','100001105347886','Hay que joderse!!','','{\"description\":\"\"}',NULL,'https://www.facebook.com/jose.blecuadepedro/posts/625201154193390',''),('100001105347886_625214554192050','100001105347886','179105958774916','100001105347886','2013-12-08 13:14:34','2013-12-08 13:14:34','','SNSAngelGuard','1050629888','100001105347886','Pruebas!','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=63&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\",\\\"width\\\":200,\\\"height\\\":200}],\\\"name\\\":\\\"Autoritation to send notifications\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=63&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"caption\\\":\\\"Agree with terms and conditions\\\",\\\"description\\\":\\\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\\\",\\\"target\\\":\\\"<fb:name uid=\\\\\\\"100001105347886\\\\\\\" \\\\/>\\\",\\\"target_ids\\\":100001105347886}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=63&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\",\"name\":\"Autoritation to send notifications\",\"caption\":\"Agree with terms and conditions\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=847c25606e083bf64e668fc05079c830&src=https%3A%2F%2Fsnsangelguard.com%2FSNSAngelGuardFB%2Fresources%2Flogo200.png\",\"type\":\"link\",\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=63&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}],\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=63&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/625214554192050',''),('100001105347886_625247660855406','100001105347886',NULL,'100001105347886','2013-12-08 15:07:48','2013-12-08 14:51:09','',NULL,'100001105347886',NULL,'Más pruebas!!','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/625247660855406',''),('100001105347886_625247727522066','100001105347886',NULL,'100001105347886','2013-12-08 14:51:22','2013-12-08 14:51:22','',NULL,'100001105347886',NULL,'','','{\"description\":\"\"}',NULL,'',''),('100001105347886_625253660854806','100001105347886',NULL,'100001105347886','2013-12-08 15:07:39','2013-12-08 15:07:39','',NULL,'100001105347886',NULL,'Gordo cabrón!!','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/625253660854806',''),('100001105347886_625762204137285','100001105347886','578871802160869','100001105347886','2013-12-09 17:12:33','2013-12-09 20:18:14','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_626419127404926','100001105347886','578871802160869','100001105347886','2013-12-10 17:36:37','2013-12-10 17:36:37','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_626919630688209','100001105347886','578871802160869','100001105347886','2013-12-11 18:29:46','2013-12-12 18:24:36','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_627484227298416','100001105347886','578871802160869','100001105347886','2013-12-12 21:51:49','2013-12-12 21:51:49','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_628416880538484','100001105347886','578871802160869','100001105347886','2013-12-14 17:53:45','2013-12-15 12:13:24','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_628968520483320','100001105347886','578871802160869','100001105347886','2013-12-15 19:54:33','2013-12-15 22:53:36','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_629589720421200','100001105347886','578871802160869','100001105347886','2013-12-17 08:52:11','2013-12-17 18:54:23','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_629832740396898','100001105347886','179105958774916','100001105347886','2013-12-17 21:53:27','2013-12-17 21:53:27','','SNSAngelGuard','1050629888','100001105347886','Quieres ser mi angel?','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=96&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\",\\\"width\\\":200,\\\"height\\\":200}],\\\"name\\\":\\\"Autoritation to send notifications\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=96&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"caption\\\":\\\"Agree with terms and conditions\\\",\\\"description\\\":\\\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\\\",\\\"target\\\":\\\"<fb:name uid=\\\\\\\"100001105347886\\\\\\\" \\\\/>\\\",\\\"target_ids\\\":100001105347886}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=96&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\",\"name\":\"Autoritation to send notifications\",\"caption\":\"Agree with terms and conditions\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=847c25606e083bf64e668fc05079c830&src=https%3A%2F%2Fsnsangelguard.com%2FSNSAngelGuardFB%2Fresources%2Flogo200.png\",\"type\":\"link\",\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=96&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}],\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=96&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/629832740396898',''),('100001105347886_653482874698551','100001105347886','578871802160869','100001105347886','2014-02-09 10:32:47','2014-02-09 17:31:35','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_663147063732132','100001105347886','578871802160869','100001105347886','2014-03-02 12:57:09','2014-03-02 12:57:09','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_663150043731834','100001105347886',NULL,'100001105347886','2014-03-02 13:08:19','2014-03-02 13:08:19','',NULL,'100001105347886',NULL,'Putas pruebas!!','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/663150043731834',''),('100001105347886_663933960320109','100001105347886','179105958774916','100001105347886','2014-03-15 16:40:45','2014-03-04 07:08:45','','SNSAngelGuard','1050629888','100001105347886','Elígeme pringao!!','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=4&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\",\\\"width\\\":200,\\\"height\\\":200}],\\\"call_to_action\\\":null,\\\"name\\\":\\\"Autoritation to send notifications\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=4&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"caption\\\":\\\"Agree with terms and conditions\\\",\\\"description\\\":\\\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\\\",\\\"target\\\":\\\"<fb:name uid=\\\\\\\"100001105347886\\\\\\\" \\\\/>\\\",\\\"target_ids\\\":100001105347886}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=4&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\",\"name\":\"Autoritation to send notifications\",\"caption\":\"Agree with terms and conditions\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=847c25606e083bf64e668fc05079c830&src=https%3A%2F%2Fsnsangelguard.com%2FSNSAngelGuardFB%2Fresources%2Flogo200.png\",\"type\":\"link\",\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=4&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}],\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=4&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/663933960320109',''),('100001105347886_669319846448187','100001105347886',NULL,'100001105347886','2014-03-16 13:05:24','2014-03-16 13:05:24','',NULL,'100001105347886',NULL,'Gilipollas...','','{\"description\":\"\"}','46','https://www.facebook.com/jose.blecuadepedro/posts/669319846448187',''),('100001105347886_674942349219270','100001105347886','578871802160869','100001105347886','2014-03-29 10:38:09','2014-03-30 09:37:22','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_692462440800594','100001105347886','578871802160869','100001105347886','2014-05-04 09:31:33','2014-05-04 10:58:00','',NULL,'100001105347886',NULL,'','{\"attachment_data\":\"[]\",\"photo_ids\":[],\"images\":\"[]\"}','{\"description\":\"\",\"name\":\"\",\"caption\":\"\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"\",\"type\":\"link\"}]}',NULL,'',''),('100001105347886_692488670797971','100001105347886','179105958774916','100001105347886','2014-05-04 10:49:17','2014-05-04 10:49:17','','SNSAngelGuard','1050629888','100001105347886','Pringao','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=10&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\",\\\"width\\\":200,\\\"height\\\":200}],\\\"call_to_action\\\":null,\\\"name\\\":\\\"Autoritation to send notifications\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=10&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"caption\\\":\\\"Agree with terms and conditions\\\",\\\"description\\\":\\\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\\\",\\\"target\\\":\\\"<fb:name uid=\\\\\\\"100001105347886\\\\\\\" \\\\/>\\\",\\\"target_ids\\\":100001105347886}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=10&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\",\"name\":\"Autoritation to send notifications\",\"caption\":\"Agree with terms and conditions\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=847c25606e083bf64e668fc05079c830&src=https%3A%2F%2Fsnsangelguard.com%2FSNSAngelGuardFB%2Fresources%2Flogo200.png\",\"type\":\"link\",\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=10&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}],\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=10&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/692488670797971',''),('100001105347886_692492764130895','100001105347886','179105958774916','100001105347886','2014-05-04 11:04:20','2014-05-04 11:04:20','','SNSAngelGuard','1050629888','100001105347886','Venga, ahora si','{\"attachment_data\":\"{\\\"media\\\":[{\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=11&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"type\\\":\\\"image\\\",\\\"src\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\",\\\"width\\\":200,\\\"height\\\":200}],\\\"call_to_action\\\":null,\\\"name\\\":\\\"Autoritation to send notifications\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=11&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\",\\\"caption\\\":\\\"Agree with terms and conditions\\\",\\\"description\\\":\\\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\\\",\\\"target\\\":\\\"<fb:name uid=\\\\\\\"100001105347886\\\\\\\" \\\\/>\\\",\\\"target_ids\\\":100001105347886}\",\"photo_ids\":[],\"images\":\"[{\\\"fbml\\\":\\\"<img src=\\\\\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/resources\\\\/logo200.png\\\\\\\" \\\\/>\\\",\\\"href\\\":\\\"https:\\\\/\\\\/snsangelguard.com\\\\/SNSAngelGuardFB\\\\/saveEmailFacebookContact.jsp?uidAngel=11&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\\\"}]\"}','{\"icon\":\"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-prn1/t39.2080-0/851563_592529730765868_1452724326_n.gif\",\"description\":\"The user wants you to control his or her social activity on Facebook. For more information, click on the link above.\",\"name\":\"Autoritation to send notifications\",\"caption\":\"Agree with terms and conditions\",\"properties\":[],\"media\":[{\"alt\":\"\",\"src\":\"https://fbexternal-a.akamaihd.net/app_full_proxy.php?app=179105958774916&v=1&size=z&cksum=847c25606e083bf64e668fc05079c830&src=https%3A%2F%2Fsnsangelguard.com%2FSNSAngelGuardFB%2Fresources%2Flogo200.png\",\"type\":\"link\",\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=11&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}],\"href\":\"https://snsangelguard.com/SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=11&uidPublicUser=T7o27hLoogAMZ_-gpHXGlg..\"}','237','https://www.facebook.com/jose.blecuadepedro/posts/692492764130895','');
/*!40000 ALTER TABLE `stream_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_affiliations_facebook`
--

DROP TABLE IF EXISTS `type_affiliations_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `type_affiliations_facebook` (
  `id_type_affiliations_facebook` int(11) NOT NULL,
  `TYPE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_type_affiliations_facebook`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_affiliations_facebook`
--

LOCK TABLES `type_affiliations_facebook` WRITE;
/*!40000 ALTER TABLE `type_affiliations_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `type_affiliations_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `url_openSocial`
--

DROP TABLE IF EXISTS `url_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `url_openSocial` (
  `id_url_openSocial` int(11) NOT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `LINK_TEST` varchar(100) DEFAULT NULL,
  `TYPE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_url_openSocial`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `url_openSocial`
--

LOCK TABLES `url_openSocial` WRITE;
/*!40000 ALTER TABLE `url_openSocial` DISABLE KEYS */;
/*!40000 ALTER TABLE `url_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `url_openSocial_has_user_openSocial_PROFILE_VIDEO`
--

DROP TABLE IF EXISTS `url_openSocial_has_user_openSocial_PROFILE_VIDEO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `url_openSocial_has_user_openSocial_PROFILE_VIDEO` (
  `url_openSocial_id_url_openSocial` int(11) NOT NULL,
  `user_openSocial_id_user_openSocial` varchar(100) NOT NULL,
  PRIMARY KEY (`url_openSocial_id_url_openSocial`,`user_openSocial_id_user_openSocial`),
  KEY `fk_url_openSocial_has_user_openSocial_user_openSocial1` (`user_openSocial_id_user_openSocial`),
  CONSTRAINT `fk_url_openSocial_has_user_openSocial_url_openSocial1` FOREIGN KEY (`url_openSocial_id_url_openSocial`) REFERENCES `url_openSocial` (`id_url_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_url_openSocial_has_user_openSocial_user_openSocial1` FOREIGN KEY (`user_openSocial_id_user_openSocial`) REFERENCES `user_openSocial` (`id_user_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `url_openSocial_has_user_openSocial_PROFILE_VIDEO`
--

LOCK TABLES `url_openSocial_has_user_openSocial_PROFILE_VIDEO` WRITE;
/*!40000 ALTER TABLE `url_openSocial_has_user_openSocial_PROFILE_VIDEO` DISABLE KEYS */;
/*!40000 ALTER TABLE `url_openSocial_has_user_openSocial_PROFILE_VIDEO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_settings_uid` varchar(100) NOT NULL,
  `SEX` varchar(45) DEFAULT NULL,
  `RELIGION` text,
  `RELATIONSHIP_STATUS` text,
  `POLITICAL` text,
  `ACTIVITIES` text,
  `INTERESTS` text,
  `IS_APP_USER` tinyint(1) DEFAULT NULL,
  `MUSIC` text,
  `TV` text,
  `MOVIES` text,
  `BOOKS` text,
  `ABOUT_ME` text,
  `STATUS` text,
  `QUOTES` text,
  `user_facebook_id_user_facebook` varchar(100) NOT NULL,
  `user_openSocial_id_user_openSocial` varchar(100) NOT NULL,
  PRIMARY KEY (`user_settings_uid`),
  KEY `fk_user_user_settings1` (`user_settings_uid`),
  KEY `fk_user_user_facebook1` (`user_facebook_id_user_facebook`),
  KEY `fk_user_user_openSocial1` (`user_openSocial_id_user_openSocial`),
  CONSTRAINT `fk_user_user_facebook1` FOREIGN KEY (`user_facebook_id_user_facebook`) REFERENCES `user_facebook` (`id_user_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_user_openSocial1` FOREIGN KEY (`user_openSocial_id_user_openSocial`) REFERENCES `user_openSocial` (`id_user_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_user_settings1` FOREIGN KEY (`user_settings_uid`) REFERENCES `user_settings` (`uid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('100001105347886','male','','In a relationship','','','',1,'','','','','','','','100001105347886','0000000000');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_facebook`
--

DROP TABLE IF EXISTS `user_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_facebook` (
  `id_user_facebook` varchar(100) NOT NULL,
  `FIRST_NAME` varchar(100) DEFAULT NULL,
  `MIDDLE_NAME` varchar(100) DEFAULT NULL,
  `LAST_NAME` varchar(100) DEFAULT NULL,
  `NAME` varchar(200) DEFAULT NULL,
  `PIC_SMALL` varchar(450) DEFAULT NULL,
  `PIC_BIG` varchar(450) DEFAULT NULL,
  `PIC_SQUARE` varchar(450) DEFAULT NULL,
  `PIC` varchar(450) DEFAULT NULL,
  `PROFILE_UPDATE_TIME` time DEFAULT NULL,
  `BIRTHDAY` varchar(450) DEFAULT NULL,
  `BIRTHDAY_DATE` varchar(450) DEFAULT NULL,
  `SIGNIFICANT_OTHER_ID` varchar(450) DEFAULT NULL,
  `HS1_NAME` varchar(450) DEFAULT NULL,
  `HS2_NAME` varchar(450) DEFAULT NULL,
  `GRAD_YEAR` int(11) DEFAULT NULL,
  `HS1_ID` varchar(450) DEFAULT NULL,
  `HS2_ID` varchar(450) DEFAULT NULL,
  `NOTES_COUNT` int(11) DEFAULT NULL,
  `WALL_COUNT` int(11) DEFAULT NULL,
  `ONLINE_PRESENCE` varchar(450) DEFAULT NULL,
  `LOCALE` varchar(450) DEFAULT NULL,
  `PROXIED_EMAIL` varchar(450) DEFAULT NULL,
  `PROFILE_URL` varchar(450) DEFAULT NULL,
  `EMAIL_HASHES` varchar(450) DEFAULT NULL,
  `PIC_SMALL_WITH_LOGO` varchar(450) DEFAULT NULL,
  `PIC_BIG_WITH_LOGO` varchar(450) DEFAULT NULL,
  `PIC_SQUARE_WITH_LOGO` varchar(450) DEFAULT NULL,
  `PIC_WITH_LOGO` varchar(450) DEFAULT NULL,
  `ALLOWED_RESTRICTIONS` varchar(450) DEFAULT NULL,
  `VERIFIED` tinyint(1) DEFAULT NULL,
  `PROFILE_BLURB` varchar(450) DEFAULT NULL,
  `USERNAME` varchar(450) DEFAULT NULL,
  `WEBSITE` varchar(450) DEFAULT NULL,
  `IS_BLOCKED` tinyint(1) DEFAULT NULL,
  `CONTACT_EMAIL` varchar(450) DEFAULT NULL,
  `EMAIL` varchar(450) DEFAULT NULL,
  `MEETING_FOR` varchar(45) DEFAULT NULL,
  `MEETING_SEX` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_user_facebook`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_facebook`
--

LOCK TABLES `user_facebook` WRITE;
/*!40000 ALTER TABLE `user_facebook` DISABLE KEYS */;
INSERT INTO `user_facebook` VALUES ('100001105347886','Jose','','Blecua de Pedro','Jose Blecua de Pedro','https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc1/t1.0-1/s50x50/252231_1002029915278_1941483569_t.jpg','https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc1/t1.0-1/s200x200/252231_1002029915278_1941483569_n.jpg','https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc1/t1.0-1/c25.0.81.81/s50x50/252231_1002029915278_1941483569_s.jpg','https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc1/t1.0-1/s100x100/252231_1002029915278_1941483569_s.jpg','01:43:43','March 16, 1984','03/16/1984','','Colegio Diocesano Cardenal Cisneros','',NULL,'','',NULL,8,'offline','es_ES','app+gefcc26pn5.2qud95p19e.7791a19b27f793cc612b4839283e4825@proxymail.facebook.com','https://www.facebook.com/jose.blecuadepedro','','https://fbcdn-profile-a.akamaihd.net/static-ak/rsrc.php/v1/y6/r/ksUwmHUElCY.jpg','https://fbcdn-profile-a.akamaihd.net/static-ak/rsrc.php/v2/y5/r/SRDCaeCL7hM.gif','https://fbcdn-profile-a.akamaihd.net/static-ak/rsrc.php/v1/y6/r/ksUwmHUElCY.jpg','https://fbcdn-profile-a.akamaihd.net/static-ak/rsrc.php/v1/yf/r/8AvusoZ2_W4.jpg','alcohol',1,'','jose.blecuadepedro','',0,'josejavier.blecua@gmail.com','josejavier.blecua@gmail.com','','');
/*!40000 ALTER TABLE `user_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_facebook_has_family_facebook`
--

DROP TABLE IF EXISTS `user_facebook_has_family_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_facebook_has_family_facebook` (
  `user_facebook_id_user_facebook` varchar(100) NOT NULL,
  `family_facebook_id_family_facebook` int(11) NOT NULL,
  PRIMARY KEY (`user_facebook_id_user_facebook`,`family_facebook_id_family_facebook`),
  KEY `fk_user_facebook_has_family_facebook_family_facebook1` (`family_facebook_id_family_facebook`),
  CONSTRAINT `fk_user_facebook_has_family_facebook_family_facebook1` FOREIGN KEY (`family_facebook_id_family_facebook`) REFERENCES `family_facebook` (`id_family_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_facebook_has_family_facebook_user_facebook1` FOREIGN KEY (`user_facebook_id_user_facebook`) REFERENCES `user_facebook` (`id_user_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_facebook_has_family_facebook`
--

LOCK TABLES `user_facebook_has_family_facebook` WRITE;
/*!40000 ALTER TABLE `user_facebook_has_family_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_facebook_has_family_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_facebook_has_friends_facebook`
--

DROP TABLE IF EXISTS `user_facebook_has_friends_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_facebook_has_friends_facebook` (
  `user_facebook_id_user_facebook` varchar(100) NOT NULL,
  `friends_facebook_USER_UID` varchar(45) NOT NULL,
  PRIMARY KEY (`user_facebook_id_user_facebook`,`friends_facebook_USER_UID`),
  KEY `fk_user_facebook_has_friends_facebook_friends_facebook1` (`friends_facebook_USER_UID`),
  CONSTRAINT `fk_user_facebook_has_friends_facebook_friends_facebook1` FOREIGN KEY (`friends_facebook_USER_UID`) REFERENCES `friends_facebook` (`USER_UID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_facebook_has_friends_facebook_user_facebook1` FOREIGN KEY (`user_facebook_id_user_facebook`) REFERENCES `user_facebook` (`id_user_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_facebook_has_friends_facebook`
--

LOCK TABLES `user_facebook_has_friends_facebook` WRITE;
/*!40000 ALTER TABLE `user_facebook_has_friends_facebook` DISABLE KEYS */;
INSERT INTO `user_facebook_has_friends_facebook` VALUES ('100001105347886','100002478641009'),('100001105347886','1050629888'),('100001105347886','1149018120'),('100001105347886','1458231992'),('100001105347886','588944763'),('100001105347886','796834259');
/*!40000 ALTER TABLE `user_facebook_has_friends_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_facebook_has_stream_facebook`
--

DROP TABLE IF EXISTS `user_facebook_has_stream_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_facebook_has_stream_facebook` (
  `user_facebook_id_user_facebook` varchar(100) NOT NULL,
  `stream_facebook_post_id` varchar(100) NOT NULL,
  PRIMARY KEY (`user_facebook_id_user_facebook`,`stream_facebook_post_id`),
  KEY `fk_user_facebook_has_stream_facebook_stream_facebook1` (`stream_facebook_post_id`),
  CONSTRAINT `fk_user_facebook_has_stream_facebook_stream_facebook1` FOREIGN KEY (`stream_facebook_post_id`) REFERENCES `stream_facebook` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_facebook_has_stream_facebook_user_facebook1` FOREIGN KEY (`user_facebook_id_user_facebook`) REFERENCES `user_facebook` (`id_user_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_facebook_has_stream_facebook`
--

LOCK TABLES `user_facebook_has_stream_facebook` WRITE;
/*!40000 ALTER TABLE `user_facebook_has_stream_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_facebook_has_stream_facebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_openSocial`
--

DROP TABLE IF EXISTS `user_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_openSocial` (
  `id_user_openSocial` varchar(100) NOT NULL,
  `AGE` int(11) DEFAULT NULL,
  `BODYTYPE_BUILD` varchar(100) DEFAULT NULL,
  `BODYTYPE_EYE_COLOR` varchar(45) DEFAULT NULL,
  `BODYTYPE_HAIR_COLOR` varchar(45) DEFAULT NULL,
  `BODYTYPE_HEIGHT` varchar(45) DEFAULT NULL,
  `BODYTYPE_WEIGHT` varchar(45) DEFAULT NULL,
  `CARS` varchar(450) DEFAULT NULL,
  `CHILDREN` varchar(450) DEFAULT NULL,
  `Address_openSocial_CURRENT_LOCATION` varchar(10) NOT NULL,
  `DATE_OF_BIRTH` date DEFAULT NULL,
  `Drinker_openSocial_id_Drinker_openSocial` int(11) NOT NULL,
  `ETHNICITY` varchar(450) DEFAULT NULL,
  `FASHION` varchar(450) DEFAULT NULL,
  `FOOD` varchar(450) DEFAULT NULL,
  `Gender_openSocial_id_Gender_openSocial` int(11) NOT NULL,
  `HAPPIEST_WHEN` varchar(450) DEFAULT NULL,
  `HEROES` varchar(450) DEFAULT NULL,
  `HUMOR` varchar(450) DEFAULT NULL,
  `JOB_INTERESTS` varchar(450) DEFAULT NULL,
  `LANGUAGES_SPOKEN` varchar(450) DEFAULT NULL,
  `LIVING_ARRANGEMENT` varchar(450) DEFAULT NULL,
  `LookingFor_openSocial_id_LookingFor_openSocial` int(11) NOT NULL,
  `Presence_openSocial_id_Presence_openSocial` int(11) NOT NULL,
  `NICKNAME` varchar(100) DEFAULT NULL,
  `PETS` varchar(450) DEFAULT NULL,
  `PROFILE_URL` varchar(100) DEFAULT NULL,
  `ROMANCE` varchar(100) DEFAULT NULL,
  `SCARED_OF` varchar(450) DEFAULT NULL,
  `Smoker_openSocial_id_Smoker_openSocial` int(11) NOT NULL,
  `SPORTS` varchar(450) DEFAULT NULL,
  `TAGS` varchar(450) DEFAULT NULL,
  `THUMBNAIL_URL` varchar(100) DEFAULT NULL,
  `TIME_ZONE` date DEFAULT NULL,
  `TURNS_OFFS` varchar(450) DEFAULT NULL,
  `TURNS_ONS` varchar(450) DEFAULT NULL,
  `URLS` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`id_user_openSocial`),
  KEY `fk_user_openSocial_Address_openSocial1` (`Address_openSocial_CURRENT_LOCATION`),
  KEY `fk_user_openSocial_Drinker_openSocial1` (`Drinker_openSocial_id_Drinker_openSocial`),
  KEY `fk_user_openSocial_Gender_openSocial1` (`Gender_openSocial_id_Gender_openSocial`),
  KEY `fk_user_openSocial_LookingFor_openSocial1` (`LookingFor_openSocial_id_LookingFor_openSocial`),
  KEY `fk_user_openSocial_Presence_openSocial1` (`Presence_openSocial_id_Presence_openSocial`),
  KEY `fk_user_openSocial_Smoker_openSocial1` (`Smoker_openSocial_id_Smoker_openSocial`),
  CONSTRAINT `fk_user_openSocial_Address_openSocial1` FOREIGN KEY (`Address_openSocial_CURRENT_LOCATION`) REFERENCES `Address_openSocial` (`id_Address_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_openSocial_Drinker_openSocial1` FOREIGN KEY (`Drinker_openSocial_id_Drinker_openSocial`) REFERENCES `Drinker_openSocial` (`id_Drinker_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_openSocial_Gender_openSocial1` FOREIGN KEY (`Gender_openSocial_id_Gender_openSocial`) REFERENCES `Gender_openSocial` (`id_Gender_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_openSocial_LookingFor_openSocial1` FOREIGN KEY (`LookingFor_openSocial_id_LookingFor_openSocial`) REFERENCES `LookingFor_openSocial` (`id_LookingFor_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_openSocial_Presence_openSocial1` FOREIGN KEY (`Presence_openSocial_id_Presence_openSocial`) REFERENCES `Presence_openSocial` (`id_Presence_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_openSocial_Smoker_openSocial1` FOREIGN KEY (`Smoker_openSocial_id_Smoker_openSocial`) REFERENCES `Smoker_openSocial` (`id_Smoker_openSocial`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_openSocial`
--

LOCK TABLES `user_openSocial` WRITE;
/*!40000 ALTER TABLE `user_openSocial` DISABLE KEYS */;
INSERT INTO `user_openSocial` VALUES ('0000000000',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0000000000',NULL,1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_openSocial_has_Friends_openSocial`
--

DROP TABLE IF EXISTS `user_openSocial_has_Friends_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_openSocial_has_Friends_openSocial` (
  `user_openSocial_id_user_openSocial` int(11) NOT NULL,
  `Friends_openSocial_USER_ID` varchar(45) NOT NULL,
  PRIMARY KEY (`user_openSocial_id_user_openSocial`,`Friends_openSocial_USER_ID`),
  KEY `fk_user_openSocial_has_Friends_openSocial_Friends_openSocial1` (`Friends_openSocial_USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_openSocial_has_Friends_openSocial`
--

LOCK TABLES `user_openSocial_has_Friends_openSocial` WRITE;
/*!40000 ALTER TABLE `user_openSocial_has_Friends_openSocial` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_openSocial_has_Friends_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_openSocial_has_Name_openSocial`
--

DROP TABLE IF EXISTS `user_openSocial_has_Name_openSocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_openSocial_has_Name_openSocial` (
  `user_openSocial_id_user_openSocial` int(11) NOT NULL,
  `Name_openSocial_id_Name_openSocial` int(11) NOT NULL,
  PRIMARY KEY (`user_openSocial_id_user_openSocial`,`Name_openSocial_id_Name_openSocial`),
  KEY `fk_user_openSocial_has_Name_openSocial_Name_openSocial1` (`Name_openSocial_id_Name_openSocial`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_openSocial_has_Name_openSocial`
--

LOCK TABLES `user_openSocial_has_Name_openSocial` WRITE;
/*!40000 ALTER TABLE `user_openSocial_has_Name_openSocial` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_openSocial_has_Name_openSocial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_openSocial_has_Organization_openSocial_JOBS`
--

DROP TABLE IF EXISTS `user_openSocial_has_Organization_openSocial_JOBS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_openSocial_has_Organization_openSocial_JOBS` (
  `user_openSocial_id_user_openSocial` int(11) NOT NULL,
  `Organization_openSocial_id_Organization_openSocial` int(11) NOT NULL,
  PRIMARY KEY (`user_openSocial_id_user_openSocial`,`Organization_openSocial_id_Organization_openSocial`),
  KEY `fk_user_openSocial_has_Organization_openSocial_Organization_o1` (`Organization_openSocial_id_Organization_openSocial`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_openSocial_has_Organization_openSocial_JOBS`
--

LOCK TABLES `user_openSocial_has_Organization_openSocial_JOBS` WRITE;
/*!40000 ALTER TABLE `user_openSocial_has_Organization_openSocial_JOBS` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_openSocial_has_Organization_openSocial_JOBS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_openSocial_has_Organization_openSocial_SCHOOLS`
--

DROP TABLE IF EXISTS `user_openSocial_has_Organization_openSocial_SCHOOLS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_openSocial_has_Organization_openSocial_SCHOOLS` (
  `user_openSocial_id_user_openSocial` int(11) NOT NULL,
  `Organization_openSocial_id_Organization_openSocial` int(11) NOT NULL,
  PRIMARY KEY (`user_openSocial_id_user_openSocial`,`Organization_openSocial_id_Organization_openSocial`),
  KEY `fk_user_openSocial_has_Organization_openSocial_Organization_o2` (`Organization_openSocial_id_Organization_openSocial`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_openSocial_has_Organization_openSocial_SCHOOLS`
--

LOCK TABLES `user_openSocial_has_Organization_openSocial_SCHOOLS` WRITE;
/*!40000 ALTER TABLE `user_openSocial_has_Organization_openSocial_SCHOOLS` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_openSocial_has_Organization_openSocial_SCHOOLS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_openSocial_has_url_openSocial_PROFILE_SONGS`
--

DROP TABLE IF EXISTS `user_openSocial_has_url_openSocial_PROFILE_SONGS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_openSocial_has_url_openSocial_PROFILE_SONGS` (
  `user_openSocial_id_user_openSocial` int(11) NOT NULL,
  `url_openSocial_id_url_openSocial` int(11) NOT NULL,
  PRIMARY KEY (`user_openSocial_id_user_openSocial`,`url_openSocial_id_url_openSocial`),
  KEY `fk_user_openSocial_has_url_openSocial_url_openSocial1` (`url_openSocial_id_url_openSocial`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_openSocial_has_url_openSocial_PROFILE_SONGS`
--

LOCK TABLES `user_openSocial_has_url_openSocial_PROFILE_SONGS` WRITE;
/*!40000 ALTER TABLE `user_openSocial_has_url_openSocial_PROFILE_SONGS` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_openSocial_has_url_openSocial_PROFILE_SONGS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_settings`
--

DROP TABLE IF EXISTS `user_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_settings` (
  `uid` varchar(100) NOT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `user_email` varchar(200) DEFAULT NULL,
  `legal_accepted` varchar(1) DEFAULT NULL,
  `last_check` timestamp NULL DEFAULT NULL,
  `uid_public` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `app_activated` varchar(1) DEFAULT NULL,
  `user_session` varchar(400) DEFAULT NULL,
  `locale_settings_id_locale` varchar(100) NOT NULL,
  `backup_check` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`uid`),
  KEY `fk_user_settings_locale_settings1` (`locale_settings_id_locale`),
  CONSTRAINT `fk_user_settings_locale_settings` FOREIGN KEY (`locale_settings_id_locale`) REFERENCES `locale_settings` (`id_locale`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_settings`
--

LOCK TABLES `user_settings` WRITE;
/*!40000 ALTER TABLE `user_settings` DISABLE KEYS */;
INSERT INTO `user_settings` VALUES ('100001105347886','Jose Blecua de Pedro','josejavier.blecua@gmail.com','1','2014-05-04 19:24:08','RCHAZmjREUEvkpU0-9mLcQ..','1','CAAIOexgjZAuUBAJtACAA7HnA0Wxe8RKhXSnobShNAMiBoFYZAvDFlm1yiH43R8yoWJfzNt5KrlxZBvUDiYmGe9J0BjGWFQaqPN8aVcUuUcasUfEtQ2JZCRU21dxqDSSiK3X7tZCHpBj0dBTTZCIGQd1USTXACYjEZAiCwRcRXQi8WK89Xzs8IZABdZAplQsjtA5QZD','00000002','2014-05-04 18:27:45');
/*!40000 ALTER TABLE `user_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_settings_has_guardian_settings`
--

DROP TABLE IF EXISTS `user_settings_has_guardian_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_settings_has_guardian_settings` (
  `user_settings_uid` varchar(100) NOT NULL,
  `guardian_settings_uid_guardian` int(11) NOT NULL,
  PRIMARY KEY (`user_settings_uid`,`guardian_settings_uid_guardian`),
  KEY `fk_user_settings_has_guardian_settings_guardian_settings` (`guardian_settings_uid_guardian`),
  CONSTRAINT `fk_user_settings_has_guardian_settings_guardian_settings` FOREIGN KEY (`guardian_settings_uid_guardian`) REFERENCES `guardian_settings` (`uid_guardian`),
  CONSTRAINT `fk_user_settings_has_guardian_settings_user_settings1` FOREIGN KEY (`user_settings_uid`) REFERENCES `user_settings` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_settings_has_guardian_settings`
--

LOCK TABLES `user_settings_has_guardian_settings` WRITE;
/*!40000 ALTER TABLE `user_settings_has_guardian_settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_settings_has_guardian_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_settings_has_settings_angels`
--

DROP TABLE IF EXISTS `user_settings_has_settings_angels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_settings_has_settings_angels` (
  `user_settings_uid` varchar(100) NOT NULL,
  `settings_angels_uid_angel` int(11) NOT NULL,
  PRIMARY KEY (`user_settings_uid`,`settings_angels_uid_angel`),
  KEY `fk_user_settings_has_settings_angels_uid_angel` (`settings_angels_uid_angel`),
  CONSTRAINT `fk_user_settings_has_settings_angels_uid` FOREIGN KEY (`user_settings_uid`) REFERENCES `user_settings` (`uid`),
  CONSTRAINT `fk_user_settings_has_settings_angels_uid_angel` FOREIGN KEY (`settings_angels_uid_angel`) REFERENCES `settings_angels` (`uid_angel`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_settings_has_settings_angels`
--

LOCK TABLES `user_settings_has_settings_angels` WRITE;
/*!40000 ALTER TABLE `user_settings_has_settings_angels` DISABLE KEYS */;
INSERT INTO `user_settings_has_settings_angels` VALUES ('100001105347886',7);
/*!40000 ALTER TABLE `user_settings_has_settings_angels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_history_facebook`
--

DROP TABLE IF EXISTS `work_history_facebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work_history_facebook` (
  `id_work_history_facebook` int(11) NOT NULL,
  `LOCATION` varchar(100) DEFAULT NULL,
  `COMPANY_NAME` varchar(100) DEFAULT NULL,
  `DESCRIPTION` varchar(100) DEFAULT NULL,
  `POSITION` varchar(100) DEFAULT NULL,
  `START_DATE` varchar(7) DEFAULT NULL,
  `END_DATE` varchar(7) DEFAULT NULL,
  `user_facebook_id_user_facebook` varchar(100) NOT NULL,
  PRIMARY KEY (`id_work_history_facebook`),
  KEY `fk_work_history_facebook_user_facebook1` (`user_facebook_id_user_facebook`),
  CONSTRAINT `fk_work_history_facebook_user_facebook1` FOREIGN KEY (`user_facebook_id_user_facebook`) REFERENCES `user_facebook` (`id_user_facebook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_history_facebook`
--

LOCK TABLES `work_history_facebook` WRITE;
/*!40000 ALTER TABLE `work_history_facebook` DISABLE KEYS */;
/*!40000 ALTER TABLE `work_history_facebook` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-17 16:15:00
