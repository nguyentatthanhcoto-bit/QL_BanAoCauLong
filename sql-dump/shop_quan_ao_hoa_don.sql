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
-- Table structure for table `hoa_don`
--

DROP TABLE IF EXISTS `hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don` (
  `id_hoa_don` int NOT NULL AUTO_INCREMENT,
  `ma_hoa_don` varchar(255) NOT NULL,
  `ngay_tao` datetime NOT NULL,
  `tong_tien` int NOT NULL,
  `id_nhan_vien` int NOT NULL,
  `id_khach_hang` int DEFAULT NULL,
  `id_voucher` int DEFAULT NULL,
  `ho_va_ten_khach` varchar(255) DEFAULT NULL,
  `so_dien_thoai_khach` varchar(255) DEFAULT NULL,
  `dia_chi_khach` varchar(255) DEFAULT NULL,
  `trang_thai` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_hoa_don`),
  UNIQUE KEY `id_hoa_don_UNIQUE` (`id_hoa_don`),
  UNIQUE KEY `ma_-hoa_don_UNIQUE` (`ma_hoa_don`),
  KEY `hoa_don_id_khach_hang_idx` (`id_khach_hang`),
  KEY `hoa_don_id_nhan_vien_idx` (`id_nhan_vien`),
  KEY `hoa_don_id_voucher_idx` (`id_voucher`),
  CONSTRAINT `hoa_don_id_khach_hang` FOREIGN KEY (`id_khach_hang`) REFERENCES `khach_hang` (`id_khach_hang`),
  CONSTRAINT `hoa_don_id_nhan_vien` FOREIGN KEY (`id_nhan_vien`) REFERENCES `nhan_vien` (`id_nhan_vien`),
  CONSTRAINT `hoa_don_id_voucher` FOREIGN KEY (`id_voucher`) REFERENCES `voucher` (`id_voucher`)
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
