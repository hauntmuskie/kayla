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

-- 3. Tabel Data Alternatif (Nilai Mentah Siswa per Kriteria)
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

-- 4. Tabel Data Penilaian (Nilai Skala 0-100 per Kriteria)
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
  `nilai_akhir_akademik` DECIMAL(5,3) NOT NULL,
  `nilai_akhir_prestasi` DECIMAL(5,3) NOT NULL,
  `nilai_akhir_kehadiran` DECIMAL(5,3) NOT NULL,
  `nilai_akhir_sikap` DECIMAL(5,3) NOT NULL,
  `nilai_akhir_partisipasi` DECIMAL(5,3) NOT NULL,
  `jumlah_nilai_akhir` DECIMAL(5,3) NOT NULL,
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

-- 8. Data Siswa Contoh
INSERT INTO `siswa` (`id_siswa`, `nama_siswa`, `nisn`, `kelas`, `alamat`) VALUES
('SIS001', 'Ahmad Fauzi Rahman', '1234567890', 'X IPA 1', 'Jl. Melati No. 15, Bogor'),
('SIS002', 'Siti Aminah Putri', '1234567891', 'XI IPS 2', 'Jl. Kenanga No. 23, Bogor'),
('SIS003', 'Muhammad Rizki Hakim', '1234567892', 'X IPA 2', 'Jl. Mawar No. 8, Bogor'),
('SIS004', 'Fatimah Zahra', '1234567893', 'XI IPA 1', 'Jl. Dahlia No. 12, Bogor'),
('SIS005', 'Abdullah Al-Farisi', '1234567894', 'X IPS 1', 'Jl. Anggrek No. 7, Bogor'),
('SIS006', 'Khadijah Salsabila', '1234567895', 'XI IPA 2', 'Jl. Tulip No. 19, Bogor');

-- 9. Data Alternatif (Nilai Mentah)
INSERT INTO `alternatif` (`id_siswa`, `nama_siswa`, `nilai_akademik`, `prestasi_non_akademik`, `kehadiran`, `sikap_perilaku`, `partisipasi_kegiatan`) VALUES
('SIS001', 'Ahmad Fauzi Rahman', 88.50, 'Juara 2 Olimpiade Matematika Tingkat Provinsi', 98.00, 'Sangat Baik', 'Aktif dan memiliki peran penting'),
('SIS002', 'Siti Aminah Putri', 91.00, 'Juara 1 Lomba Debat Bahasa Inggris Tingkat Kota', 100.00, 'Sangat Baik', 'Aktif rutin dalam kegiatan'),
('SIS003', 'Muhammad Rizki Hakim', 86.75, 'Partisipasi aktif dalam berbagai lomba', 93.50, 'Baik', 'Kadang-kadang aktif'),
('SIS004', 'Fatimah Zahra', 92.25, 'Juara 3 Lomba Qiroah Tingkat Provinsi', 97.00, 'Sangat Baik', 'Aktif dan memiliki peran penting'),
('SIS005', 'Abdullah Al-Farisi', 84.00, 'Tidak memiliki prestasi khusus', 89.00, 'Baik', 'Aktif rutin dalam kegiatan'),
('SIS006', 'Khadijah Salsabila', 89.50, 'Juara 1 Lomba Karya Tulis Ilmiah Tingkat Kota', 95.50, 'Sangat Baik', 'Aktif dan memiliki peran penting');

-- 10. Data Penilaian (Nilai Skala 0-100)
INSERT INTO `penilaian` (`id_siswa`, `nama_siswa`, `nilai_akademik`, `prestasi_non_akademik`, `kehadiran`, `sikap_perilaku`, `partisipasi_kegiatan`) VALUES
('SIS001', 'Ahmad Fauzi Rahman', 90, 100, 98, 100, 90),
('SIS002', 'Siti Aminah Putri', 95, 100, 100, 100, 85),
('SIS003', 'Muhammad Rizki Hakim', 85, 70, 85, 85, 70),
('SIS004', 'Fatimah Zahra', 97, 90, 95, 100, 90),
('SIS005', 'Abdullah Al-Farisi', 80, 60, 80, 85, 85),
('SIS006', 'Khadijah Salsabila', 92, 100, 93, 100, 90);

-- 11. Data Utility (Contoh hasil perhitungan)
INSERT INTO `utility` (`id_siswa`, `nama_siswa`, `utility_akademik`, `utility_prestasi`, `utility_kehadiran`, `utility_sikap`, `utility_partisipasi`) VALUES
('SIS001', 'Ahmad Fauzi Rahman', 0.588, 1.000, 0.900, 1.000, 0.750),
('SIS002', 'Siti Aminah Putri', 0.882, 1.000, 1.000, 1.000, 0.625),
('SIS003', 'Muhammad Rizki Hakim', 0.294, 0.250, 0.250, 0.333, 0.250),
('SIS004', 'Fatimah Zahra', 1.000, 0.750, 0.750, 1.000, 0.750),
('SIS005', 'Abdullah Al-Farisi', 0.000, 0.000, 0.000, 0.333, 0.625),
('SIS006', 'Khadijah Salsabila', 0.706, 1.000, 0.650, 1.000, 0.750);

-- 12. Data Nilai Akhir (Hasil Perhitungan Metode SMART)
INSERT INTO `nilai_akhir` (`id_siswa`, `nama_siswa`, `nilai_akhir_akademik`, `nilai_akhir_prestasi`, `nilai_akhir_kehadiran`, `nilai_akhir_sikap`, `nilai_akhir_partisipasi`, `jumlah_nilai_akhir`, `keputusan`) VALUES
('SIS001', 'Ahmad Fauzi Rahman', 0.206, 0.200, 0.135, 0.200, 0.075, 0.816, 'Sangat Baik'),
('SIS002', 'Siti Aminah Putri', 0.309, 0.200, 0.150, 0.200, 0.063, 0.922, 'Sangat Baik'),
('SIS003', 'Muhammad Rizki Hakim', 0.103, 0.050, 0.038, 0.067, 0.025, 0.283, 'Kurang'),
('SIS004', 'Fatimah Zahra', 0.350, 0.150, 0.113, 0.200, 0.075, 0.888, 'Sangat Baik'),
('SIS005', 'Abdullah Al-Farisi', 0.000, 0.000, 0.000, 0.067, 0.063, 0.130, 'Kurang'),
('SIS006', 'Khadijah Salsabila', 0.247, 0.200, 0.098, 0.200, 0.075, 0.820, 'Sangat Baik');

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
