-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 04, 2021 at 02:58 PM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `citysquare_payroll`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_tb`
--

CREATE TABLE `admin_tb` (
  `adminID` int(10) NOT NULL,
  `Username` varchar(10) NOT NULL,
  `Password` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin_tb`
--

INSERT INTO `admin_tb` (`adminID`, `Username`, `Password`) VALUES
(1, 'admin123', 'pass123');

-- --------------------------------------------------------

--
-- Table structure for table `attendancelog_perday_tb`
--

CREATE TABLE `attendancelog_perday_tb` (
  `ID` int(255) NOT NULL,
  `EmployeeID` varchar(50) NOT NULL,
  `Employee_Name` varchar(50) NOT NULL,
  `Employee_Position` varchar(50) NOT NULL,
  `Log_In` varchar(20) NOT NULL,
  `Log_Out` varchar(20) NOT NULL,
  `Month` varchar(20) NOT NULL,
  `Day` varchar(10) NOT NULL,
  `Year` varchar(20) NOT NULL,
  `Number_of_Hours` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `attendancelog_perday_tb`
--

INSERT INTO `attendancelog_perday_tb` (`ID`, `EmployeeID`, `Employee_Name`, `Employee_Position`, `Log_In`, `Log_Out`, `Month`, `Day`, `Year`, `Number_of_Hours`) VALUES
(67, '201901', 'Villaflores, Jovan Marpa', 'Manager', '20:12:02', '21:26:00', '05', '24', '2019', ''),
(109, '201901', 'Villaflores, Jovan Marpa', 'Manager', '00:28:04', '00:30:04', 'July', '14', '2019', '0'),
(110, '201902', 'Cabitac, Vriohn Trebor Nazareth', 'Marketing Officer', '00:33:01', '00:33:05', 'July', '14', '2019', '1'),
(112, '201903', 'Martecio, Kharl Benzon Mirasol', 'Admin Staff', '00:35:36', '00:35:40', 'July', '14', '2019', '1'),
(113, '201901', 'Villaflores, Jovan Marpa', 'Manager', '09:17:36', '09:17:47', 'July', '15', '2019', '0'),
(114, '201903', 'Martecio, Kharl Benzon Mirasol', 'Male', '15:05:31', '15:05:44', 'July', '15', '2019', '0'),
(125, '201902', 'Cabitac, Vriohn Trebor Nazareth', 'Male', '09:37:52', '09:38:04', 'July', '17', '2019', '1'),
(138, '201901', 'Villaflores, Jovan Marpa', 'MALE', '10:21:07', '10:21:13', 'July', '17', '2019', '0'),
(155, '201903', 'Martecio, Kharl Benzon Mirasol', 'Male', '11:07:11', '11:07:15', 'July', '17', '2019', '0'),
(156, '201901', 'Villaflores, Jovan Marpa', 'MALE', '22:51:52', '22:52:00', 'July', '19', '2019', '1');

-- --------------------------------------------------------

--
-- Table structure for table `attendance_summary`
--

CREATE TABLE `attendance_summary` (
  `ID` int(255) NOT NULL,
  `EmployeeID` varchar(50) NOT NULL,
  `DaysRendered` varchar(50) NOT NULL,
  `SpecialHoliday` varchar(50) NOT NULL,
  `RegularHoliday` varchar(50) NOT NULL,
  `Month` varchar(50) NOT NULL,
  `Year` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `attendance_summary`
--

INSERT INTO `attendance_summary` (`ID`, `EmployeeID`, `DaysRendered`, `SpecialHoliday`, `RegularHoliday`, `Month`, `Year`) VALUES
(1, '201902', '25', '2', '1', 'July', '2019'),
(2, '201901', '25', '1', '1', 'July', '2019'),
(27, '201903', '0', '0', '1', 'July', '2019'),
(28, '201903', '1', '0', '0', 'July', '2019'),
(29, '201901', '1', '0', '0', 'July', '2019');

-- --------------------------------------------------------

--
-- Table structure for table `employeeinfo_tb`
--

CREATE TABLE `employeeinfo_tb` (
  `ID` int(255) NOT NULL,
  `EmployeeID` varchar(20) NOT NULL,
  `First_Name` varchar(20) NOT NULL,
  `Middle_Name` varchar(20) NOT NULL,
  `Last_Name` varchar(20) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Birth_Date` varchar(20) NOT NULL,
  `Address` varchar(50) NOT NULL,
  `Job_Title` varchar(20) NOT NULL,
  `RatePerDay` varchar(20) NOT NULL,
  `Contact_No` varchar(15) NOT NULL,
  `Tin_No` varchar(20) NOT NULL,
  `SSS_No` varchar(20) NOT NULL,
  `PhilHealth_No` varchar(20) NOT NULL,
  `PAGIBIG_No` varchar(20) NOT NULL,
  `Date_Start` varchar(20) NOT NULL,
  `Age` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employeeinfo_tb`
--

INSERT INTO `employeeinfo_tb` (`ID`, `EmployeeID`, `First_Name`, `Middle_Name`, `Last_Name`, `Gender`, `Birth_Date`, `Address`, `Job_Title`, `RatePerDay`, `Contact_No`, `Tin_No`, `SSS_No`, `PhilHealth_No`, `PAGIBIG_No`, `Date_Start`, `Age`) VALUES
(1, '201901', 'Jovan', 'Marpa', 'Villaflores', 'MALE', '09-16-2000', 'Kalaklan, Olongapo City', 'Manager', '500', '091111111111', '1111111111', '2222222222', '3333333333333', '444444444444', '10-23-2018', '19'),
(2, '201902', 'Vriohn Trebor', 'Nazareth', 'Cabitac', 'Male', 'January 1, 1999', 'Morong, Bataan', 'Marketing Officer', '400', '0909101011', '1111111111', '22222222222', '333333333333', '444444444', 'November 19, 2019', '20'),
(3, '201903', 'Kharl Benzon', 'Mirasol', 'Martecio', 'Male', '02-16-1997', 'Morong, Bataan', 'Admin Staff', '400', '09102020300', '11111111111111', '2222222222222', '333333333333', '4444444444444', '06-07-2019', '22');

-- --------------------------------------------------------

--
-- Table structure for table `holiday`
--

CREATE TABLE `holiday` (
  `ID` int(255) NOT NULL,
  `Holiday` varchar(50) NOT NULL,
  `HolidayType` varchar(50) NOT NULL,
  `HolidayDate` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `holiday`
--

INSERT INTO `holiday` (`ID`, `Holiday`, `HolidayType`, `HolidayDate`) VALUES
(1, 'Labor Day', 'RegularHoliday', '05/01/2019');

-- --------------------------------------------------------

--
-- Table structure for table `salary`
--

CREATE TABLE `salary` (
  `salaryID` int(255) NOT NULL,
  `EmployeeID` varchar(50) NOT NULL,
  `EmployeeName` varchar(50) NOT NULL,
  `EmployeePosition` varchar(50) NOT NULL,
  `BasicSalary` varchar(50) NOT NULL,
  `DaysRendered` varchar(50) NOT NULL,
  `RegularHoliday` varchar(50) NOT NULL,
  `SpecialHoliday` varchar(50) NOT NULL,
  `SubTotal` varchar(50) NOT NULL,
  `SSSdeduction` varchar(50) NOT NULL,
  `PHdeduction` varchar(50) NOT NULL,
  `PAGIBIG` varchar(50) NOT NULL,
  `TotalDeduction` varchar(50) NOT NULL,
  `NetPay` varchar(50) NOT NULL,
  `Month` varchar(50) NOT NULL,
  `Year` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `salary`
--

INSERT INTO `salary` (`salaryID`, `EmployeeID`, `EmployeeName`, `EmployeePosition`, `BasicSalary`, `DaysRendered`, `RegularHoliday`, `SpecialHoliday`, `SubTotal`, `SSSdeduction`, `PHdeduction`, `PAGIBIG`, `TotalDeduction`, `NetPay`, `Month`, `Year`) VALUES
(61, '201901', 'Villaflores, Jovan Marpa', 'Manager', '13000', '26', '1000', '1500', '15500', '1890', '100', '130', '2120', '13380', 'July', '2019'),
(62, '201902', 'Cabitac, Vriohn Trebor Nazareth', 'Marketing Officer', '10000', '25', '800', '2400', '13200', '1570', '100', '100', '1770', '11430', 'July', '2019'),
(63, '201903', 'Martecio, Kharl Benzon Mirasol', 'Admin Staff', '9600', '24', '800', '1200', '11600', '1390', '100', '96', '1586', '10014', 'July', '2019'),
(64, '201903', 'Martecio, Kharl Benzon Mirasol', 'Admin Staff', '9600', '24', '800', '1200', '11600', '1390', '100', '96', '1586', '10014', 'July', '2019'),
(65, '201903', 'Martecio, Kharl Benzon Mirasol', 'Admin Staff', '10400', '26', '800', '1200', '12400', '1510', '100', '104', '1714', '10686', 'July', '2019'),
(66, '201901', 'Villaflores, Jovan Marpa', 'Manager', '13000', '26', '1000', '1500', '15500', '1890', '100', '130', '2120', '13380', 'July', '2019'),
(67, '201901', 'Villaflores, Jovan Marpa', 'Manager', '12500', '25', '1000', '1500', '15000', '1830', '100', '125', '2055', '12945', 'July', '2019'),
(68, '201901', 'Villaflores, Jovan Marpa', 'Manager', '500', '1', '0', '0', '500', '250', '100', '5', '355', '145', 'July', '2019'),
(69, '201902', 'Cabitac, Vriohn Trebor Nazareth', 'Marketing Officer', '10000', '25', '800', '2400', '13200', '1570', '100', '100', '1770', '11430', 'July', '2019'),
(70, '201903', 'Martecio, Kharl Benzon Mirasol', 'Admin Staff', '0', '0', '800', '0', '800', '250', '100', '0', '350', '450', 'July', '2019'),
(71, '201903', 'Martecio, Kharl Benzon Mirasol', 'Admin Staff', '400', '1', '0', '0', '400', '250', '100', '4', '354', '46', 'July', '2019'),
(72, '201901', 'Villaflores, Jovan Marpa', 'Manager', '12500', '25', '1000', '1500', '15000', '1830', '100', '125', '2055', '12945', 'July', '2019'),
(73, '201901', 'Villaflores, Jovan Marpa', 'Manager', '500', '1', '0', '0', '500', '250', '100', '5', '355', '145', 'July', '2019');

-- --------------------------------------------------------

--
-- Table structure for table `salary_deduction`
--

CREATE TABLE `salary_deduction` (
  `deducID` int(255) NOT NULL,
  `DeducName` varchar(50) NOT NULL,
  `salaryRangeFrom` varchar(50) NOT NULL,
  `salaryRangeTo` varchar(50) NOT NULL,
  `DeducValue` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `salary_deduction`
--

INSERT INTO `salary_deduction` (`deducID`, `DeducName`, `salaryRangeFrom`, `salaryRangeTo`, `DeducValue`) VALUES
(1, 'PhilHealth', '1', '25000', '100'),
(2, 'PhilHealth', '25001', '100000', '300'),
(3, 'PAGIBIG', '1', '1500', '1'),
(4, 'PAGIBIG', '1501', '100000', '2'),
(5, 'SSS', '1', '2249', '250'),
(6, 'SSS', '2250', '2749', '310'),
(7, 'SSS', '2750', '3249', '370'),
(8, 'SSS', '3250', '3749', '430'),
(9, 'SSS', '3750', '4249', '490'),
(10, 'SSS', '4250', '4749', '550'),
(11, 'SSS', '4750', '5249', '610'),
(12, 'SSS', '5250', '5749', '670'),
(13, 'SSS', '5750', '6249', '730'),
(14, 'SSS', '6250', '6749', '790'),
(15, 'SSS', '6750', '7249', '850'),
(16, 'SSS', '7250', '7749', '910'),
(17, 'SSS', '7750', '8249', '970'),
(18, 'SSS', '8250', '8749', '1030'),
(19, 'SSS', '8750', '9249', '1090'),
(20, 'SSS', '9250', '9749', '1150'),
(21, 'SSS', '9750', '10249', '1210'),
(22, 'SSS', '10250', '10749', '1270'),
(23, 'SSS', '10750', '11249', '1330'),
(24, 'SSS', '11250', '11749', '1390'),
(25, 'SSS', '11750', '12249', '1450'),
(26, 'SSS', '12250', '12749', '1510'),
(27, 'SSS', '12750', '13249', '1570'),
(28, 'SSS', '13250', '13749', '1630'),
(29, 'SSS', '13750', '14249', '1690'),
(30, 'SSS', '14250', '14749', '1750'),
(31, 'SSS', '14750', '15249', '1830'),
(32, 'SSS', '15250', '15749', '1890'),
(33, 'SSS', '15750', '16249', '1950'),
(34, 'SSS', '16250', '16749', '2010'),
(35, 'SSS', '16750', '17249', '2070'),
(36, 'SSS', '17250', '17749', '2130'),
(37, 'SSS', '17750', '18249', '2190'),
(38, 'SSS', '18250', '18749', '2250'),
(39, 'SSS', '18750', '19249', '2130'),
(40, 'SSS', '19250', '19749', '2370'),
(41, 'SSS', '19250', '50000', '2430');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_tb`
--
ALTER TABLE `admin_tb`
  ADD PRIMARY KEY (`adminID`);

--
-- Indexes for table `attendancelog_perday_tb`
--
ALTER TABLE `attendancelog_perday_tb`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `attendance_summary`
--
ALTER TABLE `attendance_summary`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `employeeinfo_tb`
--
ALTER TABLE `employeeinfo_tb`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `holiday`
--
ALTER TABLE `holiday`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `salary`
--
ALTER TABLE `salary`
  ADD PRIMARY KEY (`salaryID`);

--
-- Indexes for table `salary_deduction`
--
ALTER TABLE `salary_deduction`
  ADD PRIMARY KEY (`deducID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_tb`
--
ALTER TABLE `admin_tb`
  MODIFY `adminID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `attendancelog_perday_tb`
--
ALTER TABLE `attendancelog_perday_tb`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=157;

--
-- AUTO_INCREMENT for table `attendance_summary`
--
ALTER TABLE `attendance_summary`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `employeeinfo_tb`
--
ALTER TABLE `employeeinfo_tb`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `holiday`
--
ALTER TABLE `holiday`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `salary`
--
ALTER TABLE `salary`
  MODIFY `salaryID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT for table `salary_deduction`
--
ALTER TABLE `salary_deduction`
  MODIFY `deducID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
