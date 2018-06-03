/*
SQLyog Ultimate v8.32 
MySQL - 5.0.22-community-nt : Database - devicemonitoringsystem
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`devicemonitoringsystem` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `devicemonitoringsystem`;

/*Table structure for table `t_device` */

DROP TABLE IF EXISTS `t_device`;

CREATE TABLE `t_device` (
  `id` int(11) NOT NULL auto_increment,
  `d_title` varchar(255) default NULL,
  `d_time` varchar(255) default NULL,
  `d_bz` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_device` */

insert  into `t_device`(`id`,`d_title`,`d_time`,`d_bz`) values (1,'1','04/22/2016','asdf'),(2,'2','04/22/2016','asdf'),(3,'3','04/21/2016','asdf'),(4,'4','04/22/2016','asdf');

/*Table structure for table `t_devicelog` */

DROP TABLE IF EXISTS `t_devicelog`;

CREATE TABLE `t_devicelog` (
  `id` int(11) NOT NULL auto_increment,
  `user` int(11) default NULL,
  `d_title` varchar(255) default NULL,
  `d_time` varchar(255) default NULL,
  `d_bz` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK97D47EE349912131` (`user`),
  CONSTRAINT `FK97D47EE349912131` FOREIGN KEY (`user`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_devicelog` */

insert  into `t_devicelog`(`id`,`user`,`d_title`,`d_time`,`d_bz`) values (1,1,'sdfd ','04/22/2016','asdfsdf');

/*Table structure for table `t_devicestatus` */

DROP TABLE IF EXISTS `t_devicestatus`;

CREATE TABLE `t_devicestatus` (
  `id` int(11) NOT NULL auto_increment,
  `device` int(11) default NULL,
  `d_title` varchar(255) default NULL,
  `d_time` varchar(255) default NULL,
  `d_asx` varchar(255) default NULL,
  `d_bsx` varchar(255) default NULL,
  `d_csx` varchar(255) default NULL,
  `d_dsx` varchar(255) default NULL,
  `d_esx` varchar(255) default NULL,
  `d_fsx` varchar(255) default NULL,
  `d_gsx` varchar(255) default NULL,
  `d_bz` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKAD915BF3EE4D8B87` (`device`),
  CONSTRAINT `FKAD915BF3EE4D8B87` FOREIGN KEY (`device`) REFERENCES `t_device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_devicestatus` */

insert  into `t_devicestatus`(`id`,`device`,`d_title`,`d_time`,`d_asx`,`d_bsx`,`d_csx`,`d_dsx`,`d_esx`,`d_fsx`,`d_gsx`,`d_bz`) values (25,1,'20:14','2016-04-22','0','6','15','27','23','24','12',NULL);

/*Table structure for table `t_devicestatushis` */

DROP TABLE IF EXISTS `t_devicestatushis`;

CREATE TABLE `t_devicestatushis` (
  `id` int(11) NOT NULL auto_increment,
  `device` int(11) default NULL,
  `d_title` varchar(255) default NULL,
  `d_time` varchar(255) default NULL,
  `d_asx` varchar(255) default NULL,
  `d_bsx` varchar(255) default NULL,
  `d_csx` varchar(255) default NULL,
  `d_dsx` varchar(255) default NULL,
  `d_esx` varchar(255) default NULL,
  `d_fsx` varchar(255) default NULL,
  `d_gsx` varchar(255) default NULL,
  `d_bz` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK469C569FEE4D8B87` (`device`),
  CONSTRAINT `FK469C569FEE4D8B87` FOREIGN KEY (`device`) REFERENCES `t_device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_devicestatushis` */

insert  into `t_devicestatushis`(`id`,`device`,`d_title`,`d_time`,`d_asx`,`d_bsx`,`d_csx`,`d_dsx`,`d_esx`,`d_fsx`,`d_gsx`,`d_bz`) values (14,1,'20:10','2016-10-22 20:10:09','25','21','14','1','20','28','28',NULL),(15,1,'20:12','2016-04-22','22','12','1','25','24','18','10',NULL),(16,2,'20:12','2016-04-22','19','14','14','21','1','1','13',NULL),(17,3,'20:12','2016-04-22','17','17','25','12','23','2','24',NULL),(18,4,'20:12','2016-04-22','13','22','26','12','26','0','30',NULL),(19,1,'20:13','2016-04-22','6','1','23','5','19','23','41',NULL),(20,1,'20:13','2016-04-22','11','23','19','19','3','22','18',NULL),(21,2,'20:13','2016-04-22','0','4','7','29','24','9','6',NULL),(22,3,'20:13','2016-04-22','29','22','28','24','14','10','4',NULL),(23,1,'20:14','2016-04-22','6','5','11','0','11','28','36',NULL),(24,2,'20:14','2016-04-22','3','6','1','18','13','10','43',NULL);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `birthday` varchar(255) default NULL,
  `sex` varchar(255) default NULL,
  `tel` varchar(255) default NULL,
  `jjlxr` varchar(255) default NULL,
  `jjtel` varchar(255) default NULL,
  `jg` varchar(255) default NULL,
  `address` varchar(255) default NULL,
  `bm` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  `by_1` varchar(255) default NULL,
  `by_2` varchar(255) default NULL,
  `by_3` varchar(255) default NULL,
  `bz` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`username`,`password`,`name`,`birthday`,`sex`,`tel`,`jjlxr`,`jjtel`,`jg`,`address`,`bm`,`type`,`by_1`,`by_2`,`by_3`,`bz`) values (1,'admin','admin','admin','04/22/2016','男','','admin','','','','','admin',NULL,NULL,NULL,'asdf'),(2,'111','111','111','04/22/2016','男','','111sdf','','','','','teacher',NULL,NULL,NULL,'asdfsadf'),(3,'222','222','222','04/22/2016','男','','2222','','','','','student',NULL,NULL,NULL,'sadfsdf');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
