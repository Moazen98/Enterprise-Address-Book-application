-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 02, 2019 at 05:45 PM
-- Server version: 5.7.26
-- PHP Version: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `datasystemcenter`
--

-- --------------------------------------------------------

--
-- Table structure for table `userstable`
--

DROP TABLE IF EXISTS `userstable`;
CREATE TABLE IF NOT EXISTS `userstable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` int(11) NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  `enabled` bit(64) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `accepted` int(1) NOT NULL DEFAULT '0',
  `username` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `user_roles_id` bigint(20) DEFAULT NULL,
  `user_role_id` bigint(50) DEFAULT NULL,
  `phone` varchar(26) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrge3mlmpf3mubnyb9an8mb73d` (`user_roles_id`)
) ENGINE=MyISAM AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userstable`
--

INSERT INTO `userstable` (`id`, `age`, `department`, `enabled`, `firstname`, `lastname`, `password`, `email`, `accepted`, `username`, `version`, `user_roles_id`, `user_role_id`, `phone`) VALUES
(38, 20, 'Networking', b'0000000000000000000000000000000000000000000000000000000000000001', 'reem', 'jomaa', '$2a$10$8ENy/rxFYczJKFm/vgFoz.LOWqJYR1Zz10VRNWCF53yfb2QecJoJa', 'mohammad.m.moazen@gmail.com', 1, 'reemy', 0, NULL, 0, '0997017659'),
(50, 20, 'programmer', b'0000000000000000000000000000000000000000000000000000000000000001', 'ahmad', 'mla', '123', 'mohammad.m.moazen@gmail.com', 1, 'ahmad98', 1, NULL, 50, '0992875193'),
(39, 21, 'Programmer', b'0000000000000000000000000000000001110100011100100111010101100101', 'adnan', 'ktan', '$2a$10$lJHHUmHRIEh.yEiyaBOpr.ec6nqNFb31Vgmu2Tn6nLJ2xhDWE.8vy', 'mohammad.m.moazen@gmail.com', 1, 'adnan98', 5, NULL, 0, '0992875194'),
(40, 21, 'IT', b'0000000000000000000000000000000000000000000000000000000000000001', 'ahmad', 'mla', '$2a$10$b5dBsKTRkrBk7Q8szY7F5OPMybwW1hBlNaC0gO7QsAh1fUrBI123y', 'mohammad.m.moazen@gmail.com', 1, 'ahmad98', 1, NULL, 0, '0992875193'),
(43, 20, 'Programmer', b'0000000000000000000000000000000000000000000000000000000000000001', 'zaher', 'saadaldeen', '$2a$10$/lid6EYIjRRB0xuRyAlXBOjb7a9fe8Lo4/27vXxbFPXblIYmzzDXW', 'mohammad.m.moazen@gmail.com', 1, 'zaher99', 0, NULL, 0, '0992875193'),
(44, 22, 'Networking', b'0000000000000000000000000000000000000000000000000000000000000001', 'baraa', 'al zain', '$2a$10$.Oj5Ak.5uGmh0OaA0rOZdOTXlDe.x320vDfrzULNmnnwbqBzNRG/q', 'mohammad.m.moazen@gmail.com', 1, 'baraa98', 1, NULL, 0, '0992875193'),
(48, 21, 'Programmer', b'0000000000000000000000000000000000000000000000000000000000000001', 'reda', 'haj', '$2a$10$MSZv6WeDoqEJoBoBrQc/AuD/WV5uhItT74ugLLIyoX9TBaUEjhqZi', 'mohammad.m.moazen@gmail.com', 1, 'reda98', 1, NULL, 0, '0992875193'),
(47, 21, 'IT', b'0000000000000000000000000000000001110100011100100111010101100101', 'osama', 'al halabe', '$2a$10$VvW0SZ23x8A2sXEmF2yZ2.dAL6MTL3ZiElWxf/q8KHR4nmLQjL/2i', 'mohammad.m.moazen@gmail.com', 1, 'osama99', 1, NULL, 0, '0992875193'),
(57, 21, 'Networking', b'0000000000000000000000000000000000000000000000000000000000000001', 'mohamad', 'al moazen', '$2a$10$n4Rm5ZTc..zkawTyp.sazeIZfGCE/6c/NDVIQhHJZqJ4sxl8DRZt.', 'mohammad.m.moazen@gmail.com', 1, 'mezo98', 0, NULL, 0, '0992875193');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
