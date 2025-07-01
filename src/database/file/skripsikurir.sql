-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 23, 2023 at 08:23 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `skripsikurir`
--

-- --------------------------------------------------------

--
-- Table structure for table `dataalternatif`
--

CREATE TABLE `dataalternatif` (
  `id_kurir` varchar(100) NOT NULL,
  `nmakurir` varchar(100) NOT NULL,
  `K1` varchar(100) NOT NULL,
  `K2` varchar(100) NOT NULL,
  `K3` varchar(100) NOT NULL,
  `K4` varchar(100) NOT NULL,
  `K5` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dataalternatif`
--

INSERT INTO `dataalternatif` (`id_kurir`, `nmakurir`, `K1`, `K2`, `K3`, `K4`, `K5`) VALUES
('IDK0003', 'Ferry', '0', '6', '10', '1', 'Baik'),
('IDK0001', 'Raymond', '0', '1', '5', '12', 'Sangat Baik'),
('IDK0002', 'Bimo', '5', '5', '5', '8', 'Buruk'),
('IDK0005', 'Budi', '2', '0', '0', '2', 'Baik'),
('IDK0006', 'Jono', '1', '12', '5', '0', 'Cukup'),
('IDK0004', 'Rio', '0', '0', '0', '0', 'Sangat Baik');

-- --------------------------------------------------------

--
-- Table structure for table `datakriteria`
--

CREATE TABLE `datakriteria` (
  `kdkriteria` varchar(100) NOT NULL,
  `nmakriteria` varchar(100) NOT NULL,
  `nilaikriteria` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `datakriteria`
--

INSERT INTO `datakriteria` (`kdkriteria`, `nmakriteria`, `nilaikriteria`) VALUES
('K1', 'Pengiriman Tepat Waktu', '0,25'),
('K2', 'Akurasi Pengiriman', '0,20'),
('K3', 'Jumlah Paket Terkirim', '0,15'),
('K4', 'Integritas Paket', '0,20'),
('K5', 'Penanangan Paket', '0,20');

-- --------------------------------------------------------

--
-- Table structure for table `datakurir`
--

CREATE TABLE `datakurir` (
  `id_kurir` varchar(100) NOT NULL,
  `nmakurir` varchar(100) NOT NULL,
  `telp` varchar(100) NOT NULL,
  `tglbergabung` varchar(100) NOT NULL,
  `alamat` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `datakurir`
--

INSERT INTO `datakurir` (`id_kurir`, `nmakurir`, `telp`, `tglbergabung`, `alamat`) VALUES
('IDK0001', 'Raymond', '02558415', '22-12-2019', 'Kebagusan'),
('IDK0002', 'Bimo', '084575555', '22-12-1945', 'Pancoran'),
('IDK0003', 'Ferry', '0251522', '22-10-1998', 'Bantar Gembang'),
('IDK0004', 'Rio', '0821455', '12-12-2012', 'Pancoran'),
('IDK0005', 'Budi', '08554', '11-12-2012', 'Bogor'),
('IDK0006', 'Jono', '0882145', '05-11-2016', 'dsad'),
('IDK0007', 'Joko', '085455', '10-05-2016', 'asd');

-- --------------------------------------------------------

--
-- Table structure for table `datapenilaian`
--

CREATE TABLE `datapenilaian` (
  `id_kurir` varchar(100) NOT NULL,
  `nmakurir` varchar(100) NOT NULL,
  `k1` varchar(100) NOT NULL,
  `k2` varchar(100) NOT NULL,
  `k3` varchar(100) NOT NULL,
  `k4` varchar(100) NOT NULL,
  `k5` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `datapenilaian`
--

INSERT INTO `datapenilaian` (`id_kurir`, `nmakurir`, `k1`, `k2`, `k3`, `k4`, `k5`) VALUES
('IDK0001', 'Raymond', '100', '90', '85', '40', '100'),
('IDK0002', 'Bimo', '90', '90', '85', '60', '60'),
('IDK0005', 'Budi', '90', '100', '100', '80', '85'),
('IDK0006', 'Jono', '90', '60', '85', '100', '75'),
('IDK0004', 'Rio', '100', '100', '100', '100', '100');

-- --------------------------------------------------------

--
-- Table structure for table `nilaiakhir`
--

CREATE TABLE `nilaiakhir` (
  `id_kurir` varchar(100) NOT NULL,
  `nmakurir` varchar(100) NOT NULL,
  `kd_1` varchar(100) NOT NULL,
  `kd_2` varchar(100) NOT NULL,
  `kd_3` varchar(100) NOT NULL,
  `kd_4` varchar(100) NOT NULL,
  `kd_5` varchar(100) NOT NULL,
  `jmlakhir` varchar(100) NOT NULL,
  `peringkat` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nilaiakhir`
--

INSERT INTO `nilaiakhir` (`id_kurir`, `nmakurir`, `kd_1`, `kd_2`, `kd_3`, `kd_4`, `kd_5`, `jmlakhir`, `peringkat`) VALUES
('IDK0001', 'Raymond', '25,00', '18,00', '12,75', '8,00', '20,00', '83,75', 'Baik'),
('IDK0002', 'Bimo', '22,50', '18,00', '12,75', '12,00', '12,00', '77,25', 'Cukup');

-- --------------------------------------------------------

--
-- Table structure for table `utility`
--

CREATE TABLE `utility` (
  `id_kurir` varchar(100) NOT NULL,
  `nmakurir` varchar(100) NOT NULL,
  `kd1` varchar(100) NOT NULL,
  `kd2` varchar(100) NOT NULL,
  `kd3` varchar(100) NOT NULL,
  `kd4` varchar(100) NOT NULL,
  `kd5` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utility`
--

INSERT INTO `utility` (`id_kurir`, `nmakurir`, `kd1`, `kd2`, `kd3`, `kd4`, `kd5`) VALUES
('IDK0001', 'Raymond', '100.0', '90.0', '85.0', '40.0', '100.0'),
('IDK0002', 'Bimo', '90.0', '90.0', '85.0', '60.0', '60.0'),
('IDK0004', 'Rio', '100.0', '100.0', '100.0', '100.0', '100.0'),
('IDK0005', 'Budi', '90.0', '100.0', '100.0', '80.0', '85.0'),
('IDK0006', 'Jono', '90.0', '60.0', '85.0', '100.0', '75.0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `datakriteria`
--
ALTER TABLE `datakriteria`
  ADD PRIMARY KEY (`kdkriteria`);

--
-- Indexes for table `datakurir`
--
ALTER TABLE `datakurir`
  ADD PRIMARY KEY (`id_kurir`);

--
-- Indexes for table `nilaiakhir`
--
ALTER TABLE `nilaiakhir`
  ADD PRIMARY KEY (`id_kurir`);

--
-- Indexes for table `utility`
--
ALTER TABLE `utility`
  ADD PRIMARY KEY (`id_kurir`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
