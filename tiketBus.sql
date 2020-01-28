/*
SQLyog Ultimate v12.5.1 (64 bit)
MySQL - 10.4.10-MariaDB : Database - tiketbus
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tiketbus` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `tiketbus`;

/*Table structure for table `t_bus` */

DROP TABLE IF EXISTS `t_bus`;

CREATE TABLE `t_bus` (
  `kode_bus` varchar(40) NOT NULL,
  `jmlh_seat` int(3) DEFAULT NULL,
  `jam_berangkat` time DEFAULT NULL,
  `aktif` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`kode_bus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_perjalanan` */

DROP TABLE IF EXISTS `t_perjalanan`;

CREATE TABLE `t_perjalanan` (
  `id_perjalanan` varchar(40) NOT NULL,
  `tujuan` varchar(50) DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `makan` int(2) DEFAULT NULL,
  `kode_bus` varchar(5) DEFAULT NULL,
  `aktif` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`id_perjalanan`),
  KEY `FK` (`kode_bus`),
  CONSTRAINT `t_perjalanan_ibfk_1` FOREIGN KEY (`kode_bus`) REFERENCES `t_bus` (`kode_bus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_tiket` */

DROP TABLE IF EXISTS `t_tiket`;

CREATE TABLE `t_tiket` (
  `id_tiket` varchar(50) NOT NULL,
  `ktp` varchar(16) DEFAULT NULL,
  `nama_pemesan` varchar(20) DEFAULT NULL,
  `tujuan` varchar(20) DEFAULT NULL,
  `tanggal_pemesanan` date DEFAULT NULL,
  `tanggal_berangkat` date DEFAULT NULL,
  `kode_bus` varchar(5) DEFAULT NULL,
  `aktif` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`id_tiket`),
  KEY `t_tiket_ibfk_1` (`kode_bus`),
  CONSTRAINT `t_tiket_ibfk_1` FOREIGN KEY (`kode_bus`) REFERENCES `t_bus` (`kode_bus`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
