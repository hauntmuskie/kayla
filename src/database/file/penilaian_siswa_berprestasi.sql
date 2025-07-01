-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2025-07-02
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

-- 1. Tabel Data Siswa
CREATE TABLE siswa (
  id_siswa VARCHAR(20) PRIMARY KEY,
  nama_siswa VARCHAR(100) NOT NULL,
  nisn VARCHAR(20) NOT NULL,
  kelas VARCHAR(30) NOT NULL,
  alamat VARCHAR(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 2. Tabel Data Kriteria
CREATE TABLE kriteria (
  kode_kriteria VARCHAR(5) PRIMARY KEY,
  nama_kriteria VARCHAR(100) NOT NULL,
  bobot_kriteria DECIMAL(4,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 3. Tabel Data Alternatif (Nilai Mentah Siswa per Kriteria)
CREATE TABLE alternatif (
  id_siswa VARCHAR(20) NOT NULL,
  nama_siswa VARCHAR(100) NOT NULL,
  nilai_akademik DECIMAL(5,2) NOT NULL,
  prestasi_non_akademik VARCHAR(100) NOT NULL,
  kehadiran DECIMAL(5,2) NOT NULL,
  sikap_perilaku VARCHAR(50) NOT NULL,
  partisipasi_kegiatan VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_siswa),
  FOREIGN KEY (id_siswa) REFERENCES siswa(id_siswa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 4. Tabel Data Penilaian (Nilai Skala 0-100 per Kriteria)
CREATE TABLE penilaian (
  id_siswa VARCHAR(20) NOT NULL,
  nama_siswa VARCHAR(100) NOT NULL,
  nilai_akademik INT NOT NULL,
  prestasi_non_akademik INT NOT NULL,
  kehadiran INT NOT NULL,
  sikap_perilaku INT NOT NULL,
  partisipasi_kegiatan INT NOT NULL,
  PRIMARY KEY (id_siswa),
  FOREIGN KEY (id_siswa) REFERENCES siswa(id_siswa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 5. Tabel Utility (Nilai Utility per Kriteria)
CREATE TABLE utility (
  id_siswa VARCHAR(20) NOT NULL,
  nama_siswa VARCHAR(100) NOT NULL,
  utility_akademik DECIMAL(5,3) NOT NULL,
  utility_prestasi DECIMAL(5,3) NOT NULL,
  utility_kehadiran DECIMAL(5,3) NOT NULL,
  utility_sikap DECIMAL(5,3) NOT NULL,
  utility_partisipasi DECIMAL(5,3) NOT NULL,
  PRIMARY KEY (id_siswa),
  FOREIGN KEY (id_siswa) REFERENCES siswa(id_siswa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 6. Tabel Nilai Akhir (Hasil Perhitungan Metode SMART)
CREATE TABLE nilai_akhir (
  id_siswa VARCHAR(20) NOT NULL,
  nama_siswa VARCHAR(100) NOT NULL,
  nilai_akhir_akademik DECIMAL(5,3) NOT NULL,
  nilai_akhir_prestasi DECIMAL(5,3) NOT NULL,
  nilai_akhir_kehadiran DECIMAL(5,3) NOT NULL,
  nilai_akhir_sikap DECIMAL(5,3) NOT NULL,
  nilai_akhir_partisipasi DECIMAL(5,3) NOT NULL,
  jumlah_nilai_akhir DECIMAL(5,3) NOT NULL,
  keputusan VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_siswa),
  FOREIGN KEY (id_siswa) REFERENCES siswa(id_siswa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 7. Data Kriteria (Sesuai Bobot SMA IT Raflesia)
INSERT INTO kriteria (kode_kriteria, nama_kriteria, bobot_kriteria) VALUES
('K1', 'Nilai Akademik', 0.35),
('K2', 'Prestasi Non-Akademik', 0.20),
('K3', 'Kehadiran', 0.15),
('K4', 'Sikap/Perilaku', 0.20),
('K5', 'Partisipasi Kegiatan Sekolah', 0.10);

-- Contoh Data Siswa
INSERT INTO siswa (id_siswa, nama_siswa, nisn, kelas, alamat) VALUES
('SIS001', 'Ahmad Fauzi', '1234567890', 'X IPA 1', 'Jl. Melati No. 1'),
('SIS002', 'Siti Aminah', '1234567891', 'XI IPS 2', 'Jl. Kenanga No. 2');

-- Contoh Data Alternatif (Nilai Mentah)
INSERT INTO alternatif (id_siswa, nama_siswa, nilai_akademik, prestasi_non_akademik, kehadiran, sikap_perilaku, partisipasi_kegiatan) VALUES
('SIS001', 'Ahmad Fauzi', 88.5, 'Juara Provinsi', 98.0, 'Sangat Baik', 'Aktif dan memiliki peran'),
('SIS002', 'Siti Aminah', 91.0, 'Partisipasi Aktif', 100.0, 'Baik', 'Aktif rutin');

-- Contoh Data Penilaian (Nilai Skala 0-100)
INSERT INTO penilaian (id_siswa, nama_siswa, nilai_akademik, prestasi_non_akademik, kehadiran, sikap_perilaku, partisipasi_kegiatan) VALUES
('SIS001', 'Ahmad Fauzi', 90, 100, 98, 100, 90),
('SIS002', 'Siti Aminah', 95, 80, 100, 90, 85);

-- Contoh Data Utility
INSERT INTO utility (id_siswa, nama_siswa, utility_akademik, utility_prestasi, utility_kehadiran, utility_sikap, utility_partisipasi) VALUES
('SIS001', 'Ahmad Fauzi', 0.75, 1.00, 0.90, 1.00, 0.80),
('SIS002', 'Siti Aminah', 0.90, 0.80, 1.00, 0.90, 0.75);

-- Contoh Data Nilai Akhir
INSERT INTO nilai_akhir (id_siswa, nama_siswa, nilai_akhir_akademik, nilai_akhir_prestasi, nilai_akhir_kehadiran, nilai_akhir_sikap, nilai_akhir_partisipasi, jumlah_nilai_akhir, keputusan) VALUES
('SIS001', 'Ahmad Fauzi', 0.263, 0.200, 0.135, 0.200, 0.080, 0.878, 'Sangat Baik'),
('SIS002', 'Siti Aminah', 0.315, 0.160, 0.150, 0.180, 0.075, 0.880, 'Sangat Baik');

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
