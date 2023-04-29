-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 27, 2023 at 04:53 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `supermarket`
--
CREATE DATABASE IF NOT EXISTS `supermarket` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `supermarket`;
-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `dateTime` datetime NOT NULL DEFAULT current_timestamp(),
  `custID` int(11) NOT NULL,
  `arrivalTime` double NOT NULL,
  `serviceTime` double NOT NULL,
  `finishTime` double NOT NULL,
  `serveTime` double NOT NULL,
  `wait` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE `results` (
  `dateTime` datetime NOT NULL DEFAULT current_timestamp(),
  `fullAverageWait` double NOT NULL,
  `selfAverageWait` double NOT NULL,
  `fullNoUse` double NOT NULL,
  `selfNoUse` double NOT NULL,
  `satisfied` int(11) NOT NULL,
  `dissatisfied` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `testdata`
--

CREATE TABLE `testdata` (
  `dateTime` datetime NOT NULL DEFAULT current_timestamp(),
  `minArrival` int(11) NOT NULL,
  `maxArrival` int(11) NOT NULL,
  `minService` int(11) NOT NULL,
  `maxService` int(11) NOT NULL,
  `numCust` int(11) NOT NULL,
  `numFullLanes` int(11) NOT NULL,
  `numSelfLanes` int(11) NOT NULL,
  `percentSlow` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
