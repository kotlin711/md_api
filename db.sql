-- MySQL dump 10.13  Distrib 8.0.28, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: api
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `md_action`
--

DROP TABLE IF EXISTS `md_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_action` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nid` int NOT NULL,
  `text` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `action` int DEFAULT NULL,
  `data` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_action`
--

LOCK TABLES `md_action` WRITE;
/*!40000 ALTER TABLE `md_action` DISABLE KEYS */;
INSERT INTO `md_action` VALUES (1,4,'1',1,'1'),(2,4,'1',1,'1'),(3,11,'1',0,''),(4,11,'1',0,'');
/*!40000 ALTER TABLE `md_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_ad`
--

DROP TABLE IF EXISTS `md_ad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_ad` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int DEFAULT NULL,
  `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `rrule` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_ad`
--

LOCK TABLES `md_ad` WRITE;
/*!40000 ALTER TABLE `md_ad` DISABLE KEYS */;
INSERT INTO `md_ad` VALUES (3,'1','1',1,'1','/static/images/user_avatar.jpeg','2022-07-14 00:00:00>2022-07-11 00:00:00');
/*!40000 ALTER TABLE `md_ad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_admin`
--

DROP TABLE IF EXISTS `md_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_admin` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `md_admin_id_uindex` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_admin`
--

LOCK TABLES `md_admin` WRITE;
/*!40000 ALTER TABLE `md_admin` DISABLE KEYS */;
INSERT INTO `md_admin` VALUES ('1','1','2022-06-27 06:44:39');
/*!40000 ALTER TABLE `md_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_app`
--

DROP TABLE IF EXISTS `md_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_app` (
  `id` int NOT NULL AUTO_INCREMENT,
  `version` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `md5` char(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `size` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_app`
--

LOCK TABLES `md_app` WRITE;
/*!40000 ALTER TABLE `md_app` DISABLE KEYS */;
/*!40000 ALTER TABLE `md_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_file`
--

DROP TABLE IF EXISTS `md_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `save_model` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_file`
--

LOCK TABLES `md_file` WRITE;
/*!40000 ALTER TABLE `md_file` DISABLE KEYS */;
INSERT INTO `md_file` VALUES (13,1,2,'wallhaven-3z3zpy.jpg','/data/6a37d548ea4b47ffab536ebacec92056.jpg',16568763),(14,1,6,'工资管理系.docx','/data/be5ee746c85448c89c564e9e30f0a6b8.docx',90365),(15,1,6,'代码.docx','/data/304edf31738b43bebf79b55db1f2536e.docx',22813),(16,1,2,'微信图片_20220604232402.jpg','/data/494cea31df6847c78087186ae55d043a.jpg',103742),(17,1,2,'图片3.png','/data/b6efa6a1970e4cda89d036dd2ec7232e.png',19221),(18,2,2,'Picture2.png','https://reh8cnc2r.sabkt.gdipper.com/Picture2.png',42330),(19,1,6,'studentmanager-master.zip','/data/7c7e04af69074cde95fa20d96d64babd.zip',1706711),(20,2,2,'ziti.png','http://reh8cnc2r.sabkt.gdipper.com/ziti.png',7358),(21,2,2,'2022-04-13 15-41-58 的屏幕截图.png','http://reh8cnc2r.sabkt.gdipper.com/2022-04-13 15-41-58 的屏幕截图.png',52045),(22,2,2,'2022-04-13 15-41-58 的屏幕截图.png','http://reh8cnc2r.sabkt.gdipper.com/2022-04-13 15-41-58 的屏幕截图.png',52045),(23,2,2,'Picture2.png','http://reh8cnc2r.sabkt.gdipper.com/Picture2.png',42330);
/*!40000 ALTER TABLE `md_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_gift`
--

DROP TABLE IF EXISTS `md_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_gift` (
  `id` int NOT NULL AUTO_INCREMENT,
  `provide_name` int DEFAULT NULL,
  `key` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` int DEFAULT '0',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_gift`
--

LOCK TABLES `md_gift` WRITE;
/*!40000 ALTER TABLE `md_gift` DISABLE KEYS */;
INSERT INTO `md_gift` VALUES (124,1,'L6aIqa',0,NULL,'2022-06-27 05:39:33');
/*!40000 ALTER TABLE `md_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_login`
--

DROP TABLE IF EXISTS `md_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_login` (
  `uid` int NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_login`
--

LOCK TABLES `md_login` WRITE;
/*!40000 ALTER TABLE `md_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `md_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_notice`
--

DROP TABLE IF EXISTS `md_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_notice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_notice`
--

LOCK TABLES `md_notice` WRITE;
/*!40000 ALTER TABLE `md_notice` DISABLE KEYS */;
INSERT INTO `md_notice` VALUES (4,'1','1'),(5,'1','1'),(6,'1','1'),(7,'1','1'),(8,'1','1'),(9,'1','1'),(10,'1','1'),(11,'1','1'),(12,'1','1'),(13,'1','1');
/*!40000 ALTER TABLE `md_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_order`
--

DROP TABLE IF EXISTS `md_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `promo_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `pay_type` int DEFAULT NULL,
  `provide_amount` float DEFAULT NULL,
  `provide_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `results` int DEFAULT '-1',
  `create_time` datetime DEFAULT NULL,
  `uid` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_order`
--

LOCK TABLES `md_order` WRITE;
/*!40000 ALTER TABLE `md_order` DISABLE KEYS */;
INSERT INTO `md_order` VALUES (2,'只能','2312',1,2321,'23123',1,'2022-06-21 03:55:58',8),(3,'只能','2312',1,2321,'23123',1,'2022-06-21 03:55:58',2),(5,NULL,NULL,1,10000,'苹果14 pro max',NULL,NULL,8),(6,NULL,NULL,1,10000,'苹果14 pro max',NULL,NULL,8),(7,NULL,NULL,1,10000,'苹果14 pro max',NULL,NULL,8),(8,NULL,NULL,1,1000000,'苹果14 pro max',NULL,NULL,8),(9,NULL,NULL,1,1000000,'苹果14 pro max',NULL,NULL,8),(10,NULL,NULL,1,10000,'苹果14 pro max',NULL,NULL,8),(11,NULL,NULL,1,10000,'苹果14 pro max',NULL,NULL,8),(12,NULL,NULL,1,10000,'苹果14 pro max',NULL,NULL,8),(13,NULL,NULL,1,10000,'苹果14 pro max',NULL,NULL,8),(14,NULL,NULL,1,10000,'苹果14 pro max',NULL,NULL,8),(15,NULL,NULL,1,10000,'苹果14 pro max',NULL,NULL,8);
/*!40000 ALTER TABLE `md_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_promo`
--

DROP TABLE IF EXISTS `md_promo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_promo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `provide_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `promo_amount` float DEFAULT NULL,
  `provide_amount` float DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_promo`
--

LOCK TABLES `md_promo` WRITE;
/*!40000 ALTER TABLE `md_promo` DISABLE KEYS */;
INSERT INTO `md_promo` VALUES (2,1,'2131','3123','测试',321,1312,'2022-05-20 00:00:00','2022-06-24 00:00:00',NULL);
/*!40000 ALTER TABLE `md_promo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_provide`
--

DROP TABLE IF EXISTS `md_provide`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_provide` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_provide`
--

LOCK TABLES `md_provide` WRITE;
/*!40000 ALTER TABLE `md_provide` DISABLE KEYS */;
INSERT INTO `md_provide` VALUES (1,'测试',1312,'2022-06-26 04:13:59'),(2,'31231',1545,NULL);
/*!40000 ALTER TABLE `md_provide` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_user`
--

DROP TABLE IF EXISTS `md_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatar` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `vip` int DEFAULT '-1',
  `last_login_time` datetime DEFAULT NULL,
  `reg_time` datetime DEFAULT NULL,
  `state` int DEFAULT '0',
  `device_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `vie_end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_user`
--

LOCK TABLES `md_user` WRITE;
/*!40000 ALTER TABLE `md_user` DISABLE KEYS */;
INSERT INTO `md_user` VALUES (11,'11','123566','/static/images/user_avatar.jpeg','1503942847@qq.com',0,NULL,'2022-07-11 14:24:57',0,NULL,NULL);
/*!40000 ALTER TABLE `md_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-23 17:07:47
