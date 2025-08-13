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
-- Table structure for table `quan_ly`
--

DROP TABLE IF EXISTS `quan_ly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quan_ly` (
  `id_quan_ly` int NOT NULL AUTO_INCREMENT,
  `ma_quan_ly` varchar(255) NOT NULL,
  `ten_quan_ly` varchar(255) NOT NULL,
  `tai_khoan` varchar(255) NOT NULL,
  `mat_khau` varchar(255) NOT NULL,
  PRIMARY KEY (`id_quan_ly`),
  UNIQUE KEY `id_quan_ly_UNIQUE` (`id_quan_ly`),
  UNIQUE KEY `ma_quan_ly_UNIQUE` (`ma_quan_ly`),
  UNIQUE KEY `tai_khoan_UNIQUE` (`tai_khoan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `khach_hang`
--

DROP TABLE IF EXISTS `khach_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khach_hang` (
  `id_khach_hang` int NOT NULL AUTO_INCREMENT,
  `ho_va_ten` varchar(255) NOT NULL,
  `so_dien_thoai` varchar(255) NOT NULL,
  `dia_chi` varchar(1000) NOT NULL,
  `ma_khach_hang` varchar(255) NOT NULL,
  `gioi_tinh` varchar(255) NOT NULL,
  `trang_thai` varchar(255) NOT NULL,
  PRIMARY KEY (`id_khach_hang`),
  UNIQUE KEY `id_khach_hang_UNIQUE` (`id_khach_hang`),
  UNIQUE KEY `ma_khach_hang_UNIQUE` (`ma_khach_hang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loai_ao`
--

DROP TABLE IF EXISTS `loai_ao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai_ao` (
  `id_loai_ao` int NOT NULL AUTO_INCREMENT,
  `ma_loai` varchar(255) NOT NULL,
  `ten_loai` varchar(255) NOT NULL,
  PRIMARY KEY (`id_loai_ao`),
  UNIQUE KEY `id_UNIQUE` (`id_loai_ao`),
  UNIQUE KEY `ma_loai_UNIQUE` (`ma_loai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mau_sac`
--

DROP TABLE IF EXISTS `mau_sac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mau_sac` (
  `id_mau_sac` int NOT NULL AUTO_INCREMENT,
  `ma_mau_sac` varchar(255) NOT NULL,
  `ten_mau_sac` varchar(255) NOT NULL,
  PRIMARY KEY (`id_mau_sac`),
  UNIQUE KEY `idmau_sac_UNIQUE` (`id_mau_sac`),
  UNIQUE KEY `ma_mau_sac_UNIQUE` (`ma_mau_sac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `id_size` int NOT NULL AUTO_INCREMENT,
  `ma_size` varchar(255) NOT NULL,
  `ten_size` varchar(255) NOT NULL,
  PRIMARY KEY (`id_size`),
  UNIQUE KEY `idsize_UNIQUE` (`id_size`),
  UNIQUE KEY `ma_size_UNIQUE` (`ma_size`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nhan_vien`
--

DROP TABLE IF EXISTS `nhan_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhan_vien` (
  `id_nhan_vien` int NOT NULL AUTO_INCREMENT,
  `ma_nhan_vien` varchar(255) NOT NULL,
  `ten_nhan_vien` varchar(255) NOT NULL,
  `tai_khoan` varchar(255) NOT NULL,
  `mat_khau` varchar(255) NOT NULL,
  `so_dien_thoai` varchar(255) NOT NULL,
  `id_quan_ly` int NOT NULL,
  PRIMARY KEY (`id_nhan_vien`),
  UNIQUE KEY `id_quan_ly_UNIQUE` (`id_quan_ly`),
  UNIQUE KEY `ma_nhan_vien_UNIQUE` (`ma_nhan_vien`),
  UNIQUE KEY `id_nhan_vien_UNIQUE` (`id_nhan_vien`),
  CONSTRAINT `id_quan_ly_nhan_vien` FOREIGN KEY (`id_quan_ly`) REFERENCES `quan_ly` (`id_quan_ly`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher` (
  `id_voucher` int NOT NULL AUTO_INCREMENT,
  `ma_voucher` varchar(255) NOT NULL,
  `ten_voucher` varchar(255) NOT NULL,
  `gia_tri_giam` int NOT NULL,
  `ngay_bat_dau` datetime NOT NULL,
  `ngay_ket_thuc` datetime NOT NULL,
  `so_luong` int NOT NULL,
  `trang_thai` varchar(255) NOT NULL,
  `id_quan_ly` int NOT NULL,
  PRIMARY KEY (`id_voucher`),
  UNIQUE KEY `id_voucher_UNIQUE` (`id_voucher`),
  UNIQUE KEY `id_quan_ly_UNIQUE` (`id_quan_ly`),
  UNIQUE KEY `ma_voucher_UNIQUE` (`ma_voucher`),
  CONSTRAINT `id_quan_ly_voucher` FOREIGN KEY (`id_quan_ly`) REFERENCES `quan_ly` (`id_quan_ly`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `gia` varchar(255) NOT NULL,
  `so_luong` varchar(255) NOT NULL,
  `mo_ta` varchar(255) DEFAULT NULL,
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
  `tong_tien` varchar(255) NOT NULL,
  `id_nhan_vien` int NOT NULL,
  `id_khach_hang` int DEFAULT NULL,
  `id_voucher` int DEFAULT NULL,
  `ho_va_ten_khach` varchar(255) DEFAULT NULL,
  `so_dien_thoai_khach` varchar(255) DEFAULT NULL,
  `dia_chi_khach` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_hoa_don`),
  UNIQUE KEY `id_hoa_don_UNIQUE` (`id_hoa_don`),
  UNIQUE KEY `ma_hoa_don_UNIQUE` (`ma_hoa_don`),
  KEY `hoa_don_id_khach_hang_idx` (`id_khach_hang`),
  KEY `hoa_don_id_nhan_vien_idx` (`id_nhan_vien`),
  KEY `hoa_don_id_voucher_idx` (`id_voucher`),
  CONSTRAINT `hoa_don_id_khach_hang` FOREIGN KEY (`id_khach_hang`) REFERENCES `khach_hang` (`id_khach_hang`),
  CONSTRAINT `hoa_don_id_nhan_vien` FOREIGN KEY (`id_nhan_vien`) REFERENCES `nhan_vien` (`id_nhan_vien`),
  CONSTRAINT `hoa_don_id_voucher` FOREIGN KEY (`id_voucher`) REFERENCES `voucher` (`id_voucher`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chi_tiet_hoa_don`
--

DROP TABLE IF EXISTS `chi_tiet_hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_tiet_hoa_don` (
  `id_chi_tiet_hoa_don` int NOT NULL,
  `id_hoa_don` int NOT NULL,
  `id_ao` int NOT NULL,
  `so_luong` int NOT NULL,
  `don_gia` int NOT NULL,
  PRIMARY KEY (`id_chi_tiet_hoa_don`),
  UNIQUE KEY `id_chi_tiet_hoa_don_UNIQUE` (`id_chi_tiet_hoa_don`),
  KEY `chi_tiet_hoa_don_id_hoa_don_idx` (`id_hoa_don`),
  KEY `chi_tiet_hoa_don_id_ao_idx` (`id_ao`),
  CONSTRAINT `chi_tiet_hoa_don_id_ao` FOREIGN KEY (`id_ao`) REFERENCES `ao` (`id_ao`),
  CONSTRAINT `chi_tiet_hoa_don_id_hoa_don` FOREIGN KEY (`id_hoa_don`) REFERENCES `hoa_don` (`id_hoa_don`)
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

-- Dump completed on 2025-07-25 12:14:43