-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 21, 2024 at 06:34 PM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `heath-care`
--
CREATE DATABASE IF NOT EXISTS `heath-care` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `heath-care`;

-- --------------------------------------------------------

--
-- Table structure for table `appointment_details`
--

CREATE TABLE `appointment_details` (
  `Appointment_Id` int(11) NOT NULL,
  `Hospital_Id` int(11) NOT NULL,
  `Doctor_Id` int(11) NOT NULL,
  `Patient_Id` int(11) NOT NULL,
  `Status` tinyint(1) NOT NULL,
  `Appointment_Date` datetime NOT NULL,
  `Online_URL` varchar(250) DEFAULT NULL,
  `Recorded_Date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `appointment_details`
--

INSERT INTO `appointment_details` (`Appointment_Id`, `Hospital_Id`, `Doctor_Id`, `Patient_Id`, `Status`, `Appointment_Date`, `Online_URL`, `Recorded_Date`) VALUES
(16, 300000, 200000, 100000, 0, '2024-05-21 23:59:59', 'https://meet.google.com/PWg-n30-9O8', '2024-05-21 21:33:21');

-- --------------------------------------------------------

--
-- Table structure for table `doctor_details`
--

CREATE TABLE `doctor_details` (
  `Doctor_Id` int(11) NOT NULL,
  `Name` varchar(250) NOT NULL,
  `Phoneno` bigint(15) NOT NULL,
  `Email_Id` varchar(250) NOT NULL,
  `Degree` varchar(250) NOT NULL,
  `Desigination` varchar(250) NOT NULL,
  `Address` text NOT NULL,
  `Username` varchar(250) NOT NULL,
  `Password` varchar(250) NOT NULL,
  `Recorded_Date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `doctor_details`
--

INSERT INTO `doctor_details` (`Doctor_Id`, `Name`, `Phoneno`, `Email_Id`, `Degree`, `Desigination`, `Address`, `Username`, `Password`, `Recorded_Date`) VALUES
(200000, 'Hari', 9043963074, 'saran19924@gmail.com', 'M.B.B.S', 'Ortho Surgeon', 'A1-405, Akshaya Adena apartments, Annai Theressa street, Kazhipattur', 'hari', 'hari', '2024-03-21 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `hospital_details`
--

CREATE TABLE `hospital_details` (
  `Hospital_Id` int(11) NOT NULL,
  `Name` varchar(250) NOT NULL,
  `Phoneno` bigint(15) NOT NULL,
  `Email_Id` varchar(250) NOT NULL,
  `Address` text NOT NULL,
  `Username` varchar(250) NOT NULL,
  `Password` varchar(250) NOT NULL,
  `Recorded_Date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hospital_details`
--

INSERT INTO `hospital_details` (`Hospital_Id`, `Name`, `Phoneno`, `Email_Id`, `Address`, `Username`, `Password`, `Recorded_Date`) VALUES
(300000, 'SRM Hospital', 9952068427, 'saran19924@gmail.com', 'A1-405, Akshaya Adena apartments, Annai Theressa street, Kazhipattur', 'srm', 'srm', '2024-03-21 00:00:00'),
(300001, 'Vijaya Hospital', 7708625027, 'jsaranyaqmc@gmail.com', 'no.8, 2nd cross st, aravindar nagar, reddiyarpalayam post', 'vijaya', 'vijaya', '2024-03-21 00:00:00'),
(300002, 'Supreme Hospital', 9043963074, 'jsaranyaqmc@gmail.com', 'no.8, 2nd cross st, aravindar nagar, reddiyarpalayam post', 'supreme', 'supreme', '2024-05-06 23:15:13');

-- --------------------------------------------------------

--
-- Table structure for table `hospital_doctor_details`
--

CREATE TABLE `hospital_doctor_details` (
  `HD_Id` int(11) NOT NULL,
  `Hospital_Id` int(11) NOT NULL,
  `Doctor_Id` int(11) NOT NULL,
  `Visiting_Hours` varchar(250) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  `Recorded_Date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hospital_doctor_details`
--

INSERT INTO `hospital_doctor_details` (`HD_Id`, `Hospital_Id`, `Doctor_Id`, `Visiting_Hours`, `Status`, `Recorded_Date`) VALUES
(1, 300000, 200000, '10:00 AM to 12:00 PM', 1, '2024-03-22 00:00:00'),
(2, 300001, 200000, '01:00 PM to 02:00 PM', 1, '2024-05-06 00:00:00'),
(3, 300002, 200000, '04:00 PM to 05:00 PM', 1, '2024-05-06 23:15:41');

-- --------------------------------------------------------

--
-- Table structure for table `labreport_details`
--

CREATE TABLE `labreport_details` (
  `Lab_Id` int(11) NOT NULL,
  `Appointment_Id` int(11) NOT NULL,
  `File_Name` varchar(250) NOT NULL,
  `File_Extension` varchar(250) NOT NULL,
  `Remarks` text,
  `Status` tinyint(1) NOT NULL,
  `Recorded_Date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `labreport_details`
--

INSERT INTO `labreport_details` (`Lab_Id`, `Appointment_Id`, `File_Name`, `File_Extension`, `Remarks`, `Status`, `Recorded_Date`) VALUES
(1, 12, 'doc', 'pdf', 'cxddsaa', 1, '2024-05-07 02:55:07'),
(2, 12, 'Physics_SrSec_2022-23', 'pdf', '', 1, '2024-05-07 02:58:08');

-- --------------------------------------------------------

--
-- Table structure for table `patient_details`
--

CREATE TABLE `patient_details` (
  `Patient_Id` int(11) NOT NULL,
  `Name` varchar(250) NOT NULL,
  `Phoneno` bigint(15) NOT NULL,
  `Email_Id` varchar(250) NOT NULL,
  `Address` text NOT NULL,
  `Username` varchar(250) NOT NULL,
  `Password` varchar(250) NOT NULL,
  `Recorded_Date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `patient_details`
--

INSERT INTO `patient_details` (`Patient_Id`, `Name`, `Phoneno`, `Email_Id`, `Address`, `Username`, `Password`, `Recorded_Date`) VALUES
(100000, 'kiruba', 9043963074, 'kirubakarans2009@gmail.com', 'chennai', 'kiruba', 'kiruba', '2024-03-21 00:00:00'),
(100001, 'Saranya', 9952068427, 'saran19924@gmail.com', 'A1-405, Akshaya Adena apartments, Annai Theressa street, Kazhipattur', 'saranya', 'saranya', '2024-03-21 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `prescription_details`
--

CREATE TABLE `prescription_details` (
  `Prescription_Id` int(11) NOT NULL,
  `Appointment_Id` int(11) NOT NULL,
  `Prescription` text NOT NULL,
  `Lab_Test` text NOT NULL,
  `Status` tinyint(1) NOT NULL,
  `Recorded_Date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `prescription_details`
--

INSERT INTO `prescription_details` (`Prescription_Id`, `Appointment_Id`, `Prescription`, `Lab_Test`, `Status`, `Recorded_Date`) VALUES
(5, 12, 'Test', 'Test', 1, '2024-05-07 13:13:08');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment_details`
--
ALTER TABLE `appointment_details`
  ADD PRIMARY KEY (`Appointment_Id`),
  ADD KEY `Hospital_Id` (`Hospital_Id`),
  ADD KEY `Doctor_Id` (`Doctor_Id`),
  ADD KEY `Patient_Id` (`Patient_Id`);

--
-- Indexes for table `doctor_details`
--
ALTER TABLE `doctor_details`
  ADD PRIMARY KEY (`Doctor_Id`);

--
-- Indexes for table `hospital_details`
--
ALTER TABLE `hospital_details`
  ADD PRIMARY KEY (`Hospital_Id`);

--
-- Indexes for table `hospital_doctor_details`
--
ALTER TABLE `hospital_doctor_details`
  ADD PRIMARY KEY (`HD_Id`),
  ADD KEY `Hospital_Id` (`Hospital_Id`),
  ADD KEY `Doctor_Id` (`Doctor_Id`);

--
-- Indexes for table `labreport_details`
--
ALTER TABLE `labreport_details`
  ADD PRIMARY KEY (`Lab_Id`),
  ADD KEY `Appointment_Id` (`Appointment_Id`);

--
-- Indexes for table `patient_details`
--
ALTER TABLE `patient_details`
  ADD PRIMARY KEY (`Patient_Id`);

--
-- Indexes for table `prescription_details`
--
ALTER TABLE `prescription_details`
  ADD PRIMARY KEY (`Prescription_Id`),
  ADD KEY `Appointment_Id` (`Appointment_Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment_details`
--
ALTER TABLE `appointment_details`
  MODIFY `Appointment_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `doctor_details`
--
ALTER TABLE `doctor_details`
  MODIFY `Doctor_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=200001;

--
-- AUTO_INCREMENT for table `hospital_details`
--
ALTER TABLE `hospital_details`
  MODIFY `Hospital_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=300003;

--
-- AUTO_INCREMENT for table `hospital_doctor_details`
--
ALTER TABLE `hospital_doctor_details`
  MODIFY `HD_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `labreport_details`
--
ALTER TABLE `labreport_details`
  MODIFY `Lab_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `patient_details`
--
ALTER TABLE `patient_details`
  MODIFY `Patient_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100002;

--
-- AUTO_INCREMENT for table `prescription_details`
--
ALTER TABLE `prescription_details`
  MODIFY `Prescription_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointment_details`
--
ALTER TABLE `appointment_details`
  ADD CONSTRAINT `appointment_details_ibfk_1` FOREIGN KEY (`Hospital_Id`) REFERENCES `hospital_details` (`Hospital_Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `appointment_details_ibfk_2` FOREIGN KEY (`Doctor_Id`) REFERENCES `doctor_details` (`Doctor_Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `appointment_details_ibfk_3` FOREIGN KEY (`Patient_Id`) REFERENCES `patient_details` (`Patient_Id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
