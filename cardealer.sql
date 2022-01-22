-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 22, 2022 at 01:04 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cardealer`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone_number` varchar(13) NOT NULL,
  `gender` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `name`, `address`, `phone_number`, `gender`) VALUES
(20, 'Kaya', 'Di suatu Tempat', '1515152321', 'Male'),
(21, 'Orang Kaya', 'Bumi', '09875514145', 'Female'),
(22, 'Customer', 'Sebuah jalan', '09786514151', 'Male');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(5) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `position` varchar(20) NOT NULL,
  `birth_date` date NOT NULL,
  `gender` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `username`, `password`, `name`, `position`, `birth_date`, `gender`) VALUES
(2, 'manager', 'aaaa', 'Seseorang', 'Manager', '2021-01-02', 'Male'),
(4, 'ical', 'aaaa', 'Ahmad Nur Rizal', 'Technician', '2014-01-01', 'Male'),
(8, 'syifa', 'aaaa', 'Syifa Fauziah', 'Sales', '2000-02-28', 'Female');

-- --------------------------------------------------------

--
-- Table structure for table `engine`
--

CREATE TABLE `engine` (
  `FK_partNumber` varchar(13) NOT NULL,
  `capacity` int(11) NOT NULL,
  `number_cylinder` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `engine`
--

INSERT INTO `engine` (`FK_partNumber`, `capacity`, `number_cylinder`) VALUES
('525252', 14141, 4);

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `payment_type` varchar(12) NOT NULL,
  `description` varchar(200) NOT NULL,
  `created_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`id`, `customer_id`, `employee_id`, `payment_type`, `description`, `created_at`) VALUES
(29, 20, 8, 'CreditCards', 'Sebuah Desc', '2022-01-21'),
(30, 21, 4, 'DebitCards', 'VAT : 0001', '2022-01-21'),
(31, 22, 4, 'Check', 'Deskripsi', '2022-01-21');

-- --------------------------------------------------------

--
-- Table structure for table `invoice_sales`
--

CREATE TABLE `invoice_sales` (
  `FK_invoice_id` int(11) NOT NULL,
  `vehicle_register_number` varchar(10) CHARACTER SET utf8mb4 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invoice_sales`
--

INSERT INTO `invoice_sales` (`FK_invoice_id`, `vehicle_register_number`) VALUES
(29, '1234567890');

-- --------------------------------------------------------

--
-- Table structure for table `invoice_service`
--

CREATE TABLE `invoice_service` (
  `FK_invoice_id` int(11) NOT NULL,
  `part_id` varchar(13) CHARACTER SET utf8mb4 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invoice_service`
--

INSERT INTO `invoice_service` (`FK_invoice_id`, `part_id`) VALUES
(30, '525252'),
(30, '13131'),
(31, '1566161');

-- --------------------------------------------------------

--
-- Table structure for table `rearview_mirror`
--

CREATE TABLE `rearview_mirror` (
  `FK_partNumber` varchar(13) NOT NULL,
  `mirror_type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rearview_mirror`
--

INSERT INTO `rearview_mirror` (`FK_partNumber`, `mirror_type`) VALUES
('51511', 'Anti-Glare');

-- --------------------------------------------------------

--
-- Table structure for table `rims`
--

CREATE TABLE `rims` (
  `FK_partNumber` varchar(13) NOT NULL,
  `rims_diameter` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rims`
--

INSERT INTO `rims` (`FK_partNumber`, `rims_diameter`) VALUES
('13131', 7),
('1234567890', 7);

-- --------------------------------------------------------

--
-- Table structure for table `spare_part`
--

CREATE TABLE `spare_part` (
  `part_number` varchar(13) CHARACTER SET utf8mb4 NOT NULL,
  `name` varchar(16) NOT NULL,
  `brand` varchar(16) NOT NULL,
  `price` int(11) NOT NULL,
  `status` varchar(7) NOT NULL DEFAULT 'Ready'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `spare_part`
--

INSERT INTO `spare_part` (`part_number`, `name`, `brand`, `price`, `status`) VALUES
('1234567890', 'Rims YTIM X1', 'YTIM Series', 700000, 'Ready'),
('13131', 'RimX', 'X Series', 100000000, 'Sold'),
('1566161', 'TireX', 'X series', 2000000, 'Sold'),
('51511', 'MirrorX', 'X Series', 690000000, 'Ready'),
('525252', 'EngineX', 'X series', 1000000, 'Sold');

-- --------------------------------------------------------

--
-- Table structure for table `tire`
--

CREATE TABLE `tire` (
  `FK_partNumber` varchar(13) NOT NULL,
  `tire_diameter` int(11) NOT NULL,
  `width` int(11) NOT NULL,
  `tire_type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tire`
--

INSERT INTO `tire` (`FK_partNumber`, `tire_diameter`, `width`, `tire_type`) VALUES
('1566161', 7, 7, 'Sport');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `register_number` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `color` varchar(10) NOT NULL,
  `number_wheel` int(5) NOT NULL,
  `weight` double NOT NULL,
  `number_doors` int(5) NOT NULL,
  `transmission` varchar(10) NOT NULL,
  `price` int(20) NOT NULL,
  `fuel_type` varchar(20) NOT NULL,
  `horse_power` int(20) NOT NULL,
  `model` varchar(20) DEFAULT NULL,
  `load_capacity` int(10) DEFAULT NULL,
  `status` varchar(7) NOT NULL DEFAULT 'Ready'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`register_number`, `name`, `brand`, `color`, `number_wheel`, `weight`, `number_doors`, `transmission`, `price`, `fuel_type`, `horse_power`, `model`, `load_capacity`, `status`) VALUES
('1234567890', 'Evo Lancer X', 'Mitsubisi', 'Black', 4, 14567, 4, 'Auto', 1000000000, 'Gasoline', 123456789, 'Van', NULL, 'Sold'),
('23456365', 'Isekai-kun', 'Suzuki', 'White', 4, 1000, 2, 'Auto', 69000000, 'Bio-diesel', 690, NULL, 100, 'Ready'),
('987654321', 'Aventador', 'Ferrarri', 'Yellow', 4, 100, 2, 'Manual', 150000000, 'Gasoline', 769, 'Sport', NULL, 'Ready');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `phone_number` (`phone_number`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `engine`
--
ALTER TABLE `engine`
  ADD KEY `FK_partNumber` (`FK_partNumber`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer fk` (`customer_id`),
  ADD KEY `employee fk` (`employee_id`);

--
-- Indexes for table `invoice_sales`
--
ALTER TABLE `invoice_sales`
  ADD KEY `FK_invoice_id` (`FK_invoice_id`),
  ADD KEY `vehicle_register_number` (`vehicle_register_number`);

--
-- Indexes for table `invoice_service`
--
ALTER TABLE `invoice_service`
  ADD KEY `FK_invoice_id` (`FK_invoice_id`),
  ADD KEY `part_id` (`part_id`);

--
-- Indexes for table `rearview_mirror`
--
ALTER TABLE `rearview_mirror`
  ADD KEY `FK_partNumber` (`FK_partNumber`);

--
-- Indexes for table `rims`
--
ALTER TABLE `rims`
  ADD KEY `FK_partNumber` (`FK_partNumber`);

--
-- Indexes for table `spare_part`
--
ALTER TABLE `spare_part`
  ADD PRIMARY KEY (`part_number`);

--
-- Indexes for table `tire`
--
ALTER TABLE `tire`
  ADD KEY `FK_partNumber` (`FK_partNumber`) USING BTREE;

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`register_number`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `engine`
--
ALTER TABLE `engine`
  ADD CONSTRAINT `engine_ibfk_1` FOREIGN KEY (`FK_partNumber`) REFERENCES `spare_part` (`part_number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `invoice`
--
ALTER TABLE `invoice`
  ADD CONSTRAINT `customer fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `employee fk` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Constraints for table `invoice_sales`
--
ALTER TABLE `invoice_sales`
  ADD CONSTRAINT `invoice_sales_ibfk_1` FOREIGN KEY (`FK_invoice_id`) REFERENCES `invoice` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `invoice_sales_ibfk_2` FOREIGN KEY (`vehicle_register_number`) REFERENCES `vehicle` (`register_number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `invoice_service`
--
ALTER TABLE `invoice_service`
  ADD CONSTRAINT `invoice_service_ibfk_1` FOREIGN KEY (`FK_invoice_id`) REFERENCES `invoice` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `invoice_service_ibfk_2` FOREIGN KEY (`part_id`) REFERENCES `spare_part` (`part_number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rearview_mirror`
--
ALTER TABLE `rearview_mirror`
  ADD CONSTRAINT `rearview_mirror_ibfk_1` FOREIGN KEY (`FK_partNumber`) REFERENCES `spare_part` (`part_number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rims`
--
ALTER TABLE `rims`
  ADD CONSTRAINT `rims_ibfk_1` FOREIGN KEY (`FK_partNumber`) REFERENCES `spare_part` (`part_number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tire`
--
ALTER TABLE `tire`
  ADD CONSTRAINT `tire_ibfk_1` FOREIGN KEY (`FK_partNumber`) REFERENCES `spare_part` (`part_number`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
