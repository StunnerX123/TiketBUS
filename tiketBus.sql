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

/*Data for the table `t_bus` */

insert  into `t_bus`(`kode_bus`,`jmlh_seat`,`jam_berangkat`,`aktif`) values 
('Batal',0,'00:00:00',0),
('BJ-01',34,'15:00:00',1),
('BJ-02',34,'15:00:00',1),
('DS-01',34,'08:00:00',1),
('DS-02',34,'08:00:00',1),
('DU-01',34,'14:00:00',1),
('DU-02',34,'14:00:00',1),
('JG-01',34,'18:00:00',1),
('JG-02',34,'18:00:00',1),
('LM-01',34,'17:00:00',1),
('LM-02',34,'17:00:00',1),
('LP-01',34,'19:00:00',1),
('LP-02',34,'19:00:00',1),
('MG-01',34,'13:00:00',1),
('MG-02',34,'13:00:00',1),
('SO-01',34,'17:30:00',1),
('SO-02',34,'17:30:00',1);

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

/*Data for the table `t_perjalanan` */

insert  into `t_perjalanan`(`id_perjalanan`,`tujuan`,`harga`,`makan`,`kode_bus`,`aktif`) values 
('T-001','Bojonegoro',220000,1,'BJ-01',1),
('T-002','Bojonegoro',220000,1,'BJ-02',1),
('T-003','Denpasar L SLTN',480000,2,'DS-01',1),
('T-004','Denpasar L SLTN',480000,2,'DS-02',1),
('T-005','Denpasar L UTR',500000,2,'DU-01',1),
('T-006','Denpasar L UTR',500000,2,'DU-02',1),
('T-007','Malang',300000,2,'MG-01',1),
('T-008','Malang',300000,2,'MG-02',1),
('T-009','Lasem',180000,1,'LM-01',1),
('T-010','Lasem',180000,1,'LM-02',1),
('T-011','Solo',180000,1,'SO-01',1),
('T-012','Solo',180000,1,'SO-02',1),
('T-013','Jogjakarta',160000,1,'JG-01',1),
('T-014','Jogjakarta',160000,1,'JG-02',1),
('T-015','Lampung',250000,2,'LP-01',1),
('T-016','Lampung',250000,2,'LP-02',1);

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
  CONSTRAINT `t_tiket_ibfk_1` FOREIGN KEY (`kode_bus`) REFERENCES `t_bus` (`kode_bus`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_tiket` */

insert  into `t_tiket`(`id_tiket`,`ktp`,`nama_pemesan`,`tujuan`,`tanggal_pemesanan`,`tanggal_berangkat`,`kode_bus`,`aktif`) values 
('B-6013615','1672637871676253','SUSAN Y','Lampung','2020-01-30','2020-01-30','LP-01',1),
('B-7672477','1717262182811881','YAYAT SUPRIYATNA','Bojonegoro','2020-01-30','2020-01-31','BJ-02',1),
('B-8142180','1234445456667687','YURA','Lasem','2020-01-30','2020-01-30','LM-02',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
