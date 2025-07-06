-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: July 02, 2025
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- --------------------------------------------------------
-- Database: penilaian_siswa_berprestasi
-- --------------------------------------------------------

-- Drop database if exists and recreate
DROP DATABASE IF EXISTS `penilaian_siswa_berprestasi`;
CREATE DATABASE `penilaian_siswa_berprestasi` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `penilaian_siswa_berprestasi`;

-- 1. Tabel Data Siswa
CREATE TABLE `siswa` (
  `id_siswa` VARCHAR(20) PRIMARY KEY,
  `nama_siswa` VARCHAR(100) NOT NULL,
  `nisn` VARCHAR(20) NOT NULL,
  `kelas` VARCHAR(30) NOT NULL,
  `alamat` VARCHAR(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 2. Tabel Data Kriteria
CREATE TABLE `kriteria` (
  `kode_kriteria` VARCHAR(5) PRIMARY KEY,
  `nama_kriteria` VARCHAR(100) NOT NULL,
  `bobot_kriteria` DECIMAL(4,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 3. Tabel Data Alternatif (Nilai Mentah/Raw Input Siswa per Kriteria)
CREATE TABLE `alternatif` (
  `id_siswa` VARCHAR(20) NOT NULL,
  `nama_siswa` VARCHAR(100) NOT NULL,
  `nilai_akademik` DECIMAL(5,2) NOT NULL,
  `prestasi_non_akademik` VARCHAR(100) NOT NULL,
  `kehadiran` DECIMAL(5,2) NOT NULL,
  `sikap_perilaku` VARCHAR(50) NOT NULL,
  `partisipasi_kegiatan` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_siswa`),
  FOREIGN KEY (`id_siswa`) REFERENCES `siswa`(`id_siswa`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 4. Tabel Data Penilaian (Nilai Konversi Skala 0-100 per Kriteria)
CREATE TABLE `penilaian` (
  `id_siswa` VARCHAR(20) NOT NULL,
  `nama_siswa` VARCHAR(100) NOT NULL,
  `nilai_akademik` INT NOT NULL,
  `prestasi_non_akademik` INT NOT NULL,
  `kehadiran` INT NOT NULL,
  `sikap_perilaku` INT NOT NULL,
  `partisipasi_kegiatan` INT NOT NULL,
  PRIMARY KEY (`id_siswa`),
  FOREIGN KEY (`id_siswa`) REFERENCES `siswa`(`id_siswa`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- 5. Tabel Utility (Nilai Utility per Kriteria)
CREATE TABLE `utility` (
  `id_siswa` VARCHAR(20) NOT NULL,
  `nama_siswa` VARCHAR(100) NOT NULL,
  `utility_akademik` DECIMAL(5,3) NOT NULL,
  `utility_prestasi` DECIMAL(5,3) NOT NULL,
  `utility_kehadiran` DECIMAL(5,3) NOT NULL,
  `utility_sikap` DECIMAL(5,3) NOT NULL,
  `utility_partisipasi` DECIMAL(5,3) NOT NULL,
  PRIMARY KEY (`id_siswa`),
  FOREIGN KEY (`id_siswa`) REFERENCES `siswa`(`id_siswa`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 6. Tabel Nilai Akhir (Hasil Perhitungan Metode SMART)
CREATE TABLE `nilai_akhir` (
  `id_siswa` VARCHAR(20) NOT NULL,
  `nama_siswa` VARCHAR(100) NOT NULL,
  `nilai_akhir_akademik` DECIMAL(8,5) NOT NULL,
  `nilai_akhir_prestasi` DECIMAL(8,5) NOT NULL,
  `nilai_akhir_kehadiran` DECIMAL(8,5) NOT NULL,
  `nilai_akhir_sikap` DECIMAL(8,5) NOT NULL,
  `nilai_akhir_partisipasi` DECIMAL(8,5) NOT NULL,
  `jumlah_nilai_akhir` DECIMAL(8,5) NOT NULL,
  `keputusan` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_siswa`),
  FOREIGN KEY (`id_siswa`) REFERENCES `siswa`(`id_siswa`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 7. Data Kriteria (Sesuai Bobot SMA Islam Terpadu Raflesia)
INSERT INTO `kriteria` (`kode_kriteria`, `nama_kriteria`, `bobot_kriteria`) VALUES
('K1', 'Nilai Akademik', 0.35),
('K2', 'Prestasi Non-Akademik', 0.20),
('K3', 'Kehadiran', 0.15),
('K4', 'Sikap/Perilaku', 0.20),
('K5', 'Partisipasi Kegiatan Sekolah', 0.10);

-- 8. Data Siswa Contoh (Berdasarkan Paper SMA Islam Terpadu Raflesia)
INSERT INTO `siswa` (`id_siswa`, `nama_siswa`, `nisn`, `kelas`, `alamat`) VALUES
('C1', 'Zahra Ainun Nadhiroh', '1234567801', 'XI IPA 1', 'Jl. Melati No. 15, Bogor'),
('C2', 'Dhezan Shakti Al Hajj', '1234567802', 'XI IPA 1', 'Jl. Kenanga No. 23, Bogor'),
('C3', 'Arfa Huriya Elfaradis', '1234567803', 'XI IPA 1', 'Jl. Mawar No. 8, Bogor'),
('C4', 'Zaskia Nasywaa Pamungkas', '1234567804', 'XI IPA 1', 'Jl. Dahlia No. 12, Bogor'),
('C5', 'Bunga Maulidya Caesar', '1234567805', 'XI IPA 1', 'Jl. Anggrek No. 7, Bogor'),
('C6', 'Mayla Yunisiah', '1234567806', 'XI IPA 1', 'Jl. Tulip No. 19, Bogor'),
('C7', 'Zauhara Maharani', '1234567807', 'XI IPA 1', 'Jl. Seroja No. 10, Bogor'),
('C8', 'Vina Rahmah', '1234567808', 'XI IPA 1', 'Jl. Cempaka No. 5, Bogor'),
('C9', 'Bunga Firjatullah', '1234567809', 'XI IPA 1', 'Jl. Flamboyan No. 20, Bogor'),
('C10', 'Daffa Wicaksana', '1234567810', 'XI IPA 1', 'Jl. Kamboja No. 14, Bogor');

-- 9. Data Alternatif (Raw Input berdasarkan Paper Tabel 4.8)
INSERT INTO `alternatif` (`id_siswa`, `nama_siswa`, `nilai_akademik`, `prestasi_non_akademik`, `kehadiran`, `sikap_perilaku`, `partisipasi_kegiatan`) VALUES
('C1', 'Zahra Ainun Nadhiroh', 86.00, 'Tidak memiliki prestasi', 93.00, 'Baik', 'Aktif rutin'),
('C2', 'Dhezan Shakti Al Hajj', 89.00, 'Partisipasi aktif', 84.00, 'Baik', 'Aktif dan memiliki peran'),
('C3', 'Arfa Huriya Elfaradis', 88.00, 'Tidak memiliki prestasi', 93.00, 'Baik', 'Kadang-kadang aktif'),
('C4', 'Zaskia Nasywaa Pamungkas', 91.00, 'Partisipasi aktif', 100.00, 'Baik', 'Aktif dan memiliki peran'),
('C5', 'Bunga Maulidya Caesar', 88.00, 'Juara tingkat provinsi/kota', 88.00, 'Baik', 'Aktif rutin'),
('C6', 'Mayla Yunisiah', 87.00, 'Partisipasi aktif', 93.00, 'Sangat Baik', 'Aktif dan memiliki peran'),
('C7', 'Zauhara Maharani', 89.00, 'Juara tingkat sekolah', 84.00, 'Sangat Baik', 'Aktif dan memiliki peran'),
('C8', 'Vina Rahmah', 89.00, 'Partisipasi aktif', 84.00, 'Baik', 'Aktif dan memiliki peran'),
('C9', 'Bunga Firjatullah', 89.00, 'Partisipasi aktif', 93.00, 'Sangat Baik', 'Aktif dan memiliki peran'),
('C10', 'Daffa Wicaksana', 86.00, 'Partisipasi aktif', 99.00, 'Baik', 'Aktif dan memiliki peran');

-- 10. Data Penilaian (Nilai Konversi Skala 0-100 berdasarkan Paper Tabel 4.9)
INSERT INTO `penilaian` (`id_siswa`, `nama_siswa`, `nilai_akademik`, `prestasi_non_akademik`, `kehadiran`, `sikap_perilaku`, `partisipasi_kegiatan`) VALUES
('C1', 'Zahra Ainun Nadhiroh', 90, 60, 80, 85, 85),
('C2', 'Dhezan Shakti Al Hajj', 90, 70, 60, 85, 100),
('C3', 'Arfa Huriya Elfaradis', 90, 60, 80, 85, 75),
('C4', 'Zaskia Nasywaa Pamungkas', 100, 70, 100, 85, 100),
('C5', 'Bunga Maulidya Caesar', 90, 90, 70, 85, 85),
('C6', 'Mayla Yunisiah', 90, 70, 80, 100, 100),
('C7', 'Zauhara Maharani', 90, 80, 60, 100, 100),
('C8', 'Vina Rahmah', 90, 70, 60, 85, 100),
('C9', 'Bunga Firjatullah', 90, 70, 80, 100, 100),
('C10', 'Daffa Wicaksana', 90, 70, 90, 85, 100);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
