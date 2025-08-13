-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: shop_quan_ao
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ao`
--

DROP TABLE IF EXISTS `ao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ao` (
  `id_ao` int NOT NULL AUTO_INCREMENT,
  `ma_ao` varchar(255) NOT NULL,
  `ten_ao` varchar(255) NOT NULL,
  `gia` int NOT NULL,
  `so_luong` int NOT NULL,
  `mo_ta` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `id_loai` int NOT NULL,
  `id_mau_sac` int NOT NULL,
  `id_size` int NOT NULL,
  PRIMARY KEY (`id_ao`),
  UNIQUE KEY `id_ao_UNIQUE` (`id_ao`),
  UNIQUE KEY `ma_ao_UNIQUE` (`ma_ao`),
  KEY `id_loai_idx` (`id_loai`),
  KEY `id_mau_sac_idx` (`id_mau_sac`),
  KEY `id_size_idx` (`id_size`),
  CONSTRAINT `id_loai` FOREIGN KEY (`id_loai`) REFERENCES `loai_ao` (`id_loai_ao`),
  CONSTRAINT `id_mau_sac` FOREIGN KEY (`id_mau_sac`) REFERENCES `mau_sac` (`id_mau_sac`),
  CONSTRAINT `id_size` FOREIGN KEY (`id_size`) REFERENCES `size` (`id_size`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-25 21:02:57
