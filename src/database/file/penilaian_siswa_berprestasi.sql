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
  `nis` VARCHAR(20) NOT NULL,
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
  `ranking` INT NOT NULL,
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

-- 8. Data Siswa (Berdasarkan Data Terbaru SMA Islam Terpadu Raflesia)
INSERT INTO `siswa` (`id_siswa`, `nama_siswa`, `nis`, `kelas`, `alamat`) VALUES
('C1', 'Zahra Ainun Nadhiroh', '232410127', 'XI MIPA 1', 'Jl. Melati Raya'),
('C2', 'Dhezan Shakti Al Hajj', '232410037', 'XI MIPA 1', 'Jl. Kenanga Indah'),
('C3', 'Arfa Huriya Elfaradis', '232410015', 'XI MIPA 1', 'Jl. Mawar Putih'),
('C4', 'Zaskia Nasywaa Pamungkas', '232410129', 'XI MIPA 1', 'Jl. Cendana Hijau'),
('C5', 'Bunga Maulidya Caesar', '232410024', 'XI MIPA 2', 'Jl. Anggrek Ungu'),
('C6', 'Mayla Yunisiah', '232410065', 'XI MIPA 2', 'Jl. Teratai Biru'),
('C7', 'Zauhara Maharani', '232410130', 'XI MIPA 2', 'Jl. Kamboja Merah'),
('C8', 'Vina Rahmah', '232410125', 'XI MIPA 2', 'Jl. Flamboyan Besar'),
('C9', 'Bunga Firjatullah', '232410023', 'XI MIPA 2', 'Jl. Sakura Merona'),
('C10', 'Daffa Wicaksana', '232410032', 'XI MIPA 2', 'Jl. Bougenville Asri'),
('C11', 'Affa Khalishah', '232410002', 'XI MIPA 1', 'Jl. Kemuning Ceria'),
('C12', 'Aidan Veda Rananulma Rizal', '232410005', 'XI MIPA 1', 'Jl. Ketapang Damai'),
('C13', 'Alfatari Fatimatuzzachra', '232410136', 'XI MIPA 1', 'Jl. Cemara Lestari'),
('C14', 'Alyka Maulida Moha', '232410010', 'XI MIPA 1', 'Jl. Pinus Jaya'),
('C15', 'Annadia Shafiya Sulkha', '232410013', 'XI MIPA 1', 'Jl. Mahoni Sejahtera'),
('C16', 'Ayu Anindytia Maharani', '232410019', 'XI MIPA 1', 'Jl. Jati Agung'),
('C17', 'Bella Nearsyaikh Azizah', '232410021', 'XI MIPA 1', 'Jl. Akasia Mulia'),
('C18', 'Dafa Zulfan Hammani', '232410030', 'XI MIPA 1', 'Jl. Beringin Kencana'),
('C19', 'Daffa Attila Hendrawan', '232410031', 'XI MIPA 1', 'Jl. Mangga Madu'),
('C20', 'Dhana Nugraha Abdhi Prawiranegara', '232410035', 'XI MIPA 1', 'Jl. Durian Wangi'),
('C21', 'Dheeandra Aqila Fayyaza', '232410036', 'XI MIPA 1', 'Jl. Rambutan Manis'),
('C22', 'Fakhriyah Althafuz Zahraniyah', '232410043', 'XI MIPA 1', 'Jl. Jeruk Permai'),
('C23', 'Farrah Rahma Annisa', '232410045', 'XI MIPA 1', 'Jl. Apel Mas'),
('C24', 'Ibrahim Al Hawaary', '232410054', 'XI MIPA 1', 'Jl. Kelapa Rindang'),
('C25', 'Latisya Zalfa Aretha', '232410061', 'XI MIPA 1', 'Jl. Pisang Mas'),
('C26', 'Muhammad Fairuz Ibnu Handoyo', '232410070', 'XI MIPA 1', 'Jl. Nangka Indah'),
('C27', 'Muhammad Gavin Azarya', '232410071', 'XI MIPA 1', 'Jl. Pepaya Cerah'),
('C28', 'Muhammad Ghozi Achdani', '232410072', 'XI MIPA 1', 'Jl. Duku Elok'),
('C29', 'Muhammad Ihsan Annazhimi', '232410137', 'XI MIPA 1', 'Jl. Alpukat Segar'),
('C30', 'Muhammad Luthfi Al Ghazali', '232410074', 'XI MIPA 1', 'Jl. Belimbing Wulung'),
('C31', 'Muhammad Nabil Ibnu Am', '232410075', 'XI MIPA 1', 'Jl. Merpati Putih'),
('C32', 'Najma Afifah', '232410085', 'XI MIPA 1', 'Jl. Kenari Indah'),
('C33', 'Najwa Qurrotannashita', '232410087', 'XI MIPA 1', 'Jl. Rajawali Jaya'),
('C34', 'Nur Nismu Imaniah Arsyad', '232410091', 'XI MIPA 1', 'Jl. Elang Perkasa'),
('C35', 'Queennesya Hafiizhra Manchesta Prastika', '232410095', 'XI MIPA 1', 'Jl. Cendrawasih Emas'),
('C36', 'Rafaneo Arzarli Imron', '232410099', 'XI MIPA 1', 'Jl. Kutilang Merdu'),
('C37', 'Raihanah Rizqia Putri Adiska', '232410103', 'XI MIPA 1', 'Jl. Beo Hijau'),
('C38', 'Reza Ahmad Khoiryama', '232410110', 'XI MIPA 1', 'Jl. Jalak Bali'),
('C39', 'Rizqya Naurah Khairani', '232410114', 'XI MIPA 1', 'Jl. Camar Laut'),
('C40', 'Ahmad Nu''aina Fathan', '232410004', 'XI MIPA 2', 'Jl. Garuda Sakti'),
('C41', 'Al Mira Rahma Kayana Putri Irawan', '232410006', 'XI MIPA 2', 'Jl. Harimau Sumatra'),
('C42', 'Amanda Sukma', '232410011', 'XI MIPA 2', 'Jl. Gajah Mada'),
('C43', 'Arnesya Mutiara', '232410016', 'XI MIPA 2', 'Jl. Macan Putih'),
('C44', 'Elmira Fairuz Inaya', '232410040', 'XI MIPA 2', 'Jl. Rusa Lestari'),
('C45', 'Farres Bima Prayuga', '232410046', 'XI MIPA 2', 'Jl. Kancil Ceria'),
('C46', 'Ikhwan Ma''ruf Al Hanania', '232410055', 'XI MIPA 2', 'Jl. Kerbau Tangguh'),
('C47', 'Isna Kusuma Suryani', '232410056', 'XI MIPA 2', 'Jl. Banteng Perkasa'),
('C48', 'Leana Calya Vania Setyahadi', '232410062', 'XI MIPA 2', 'Jl. Sapi Emas'),
('C49', 'Mayesha Nayottama Astrawinata', '232410064', 'XI MIPA 2', 'Jl. Lembu Agung'),
('C50', 'Mishya Yumna Ramadhani', '232410067', 'XI MIPA 2', 'Jl. Badak Jawa'),
('C51', 'Muhammad Aqila Alfarezi', '232410069', 'XI MIPA 2', 'Jl. Kupu-Kupu Cantik'),
('C52', 'Muhammad Naufal Desvanand', '232410076', 'XI MIPA 2', 'Jl. Capung Biru'),
('C53', 'Nadira Ayu', '232410082', 'XI MIPA 2', 'Jl. Semut Rajin'),
('C54', 'Nadja Novanza', '232410083', 'XI MIPA 2', 'Jl. Lebah Madu'),
('C55', 'Najwa Asyilah Astuadi', '232410086', 'XI MIPA 2', 'Jl. Laba-Laba Hitam'),
('C56', 'Naufal Akbar Julian', '232410088', 'XI MIPA 2', 'Jl. Kumbang Hijau'),
('C57', 'Nisrina', '232410090', 'XI MIPA 2', 'Jl. Kepik Merah'),
('C58', 'Radhitya Sulaiman', '232410096', 'XI MIPA 2', 'Jl. Ikan Mas'),
('C59', 'Rafael Ezza Wiratama', '232410098', 'XI MIPA 2', 'Jl. Ikan Koi'),
('C60', 'Rifaa Ilhaam Ramadhaan', '232410111', 'XI MIPA 2', 'Jl. Hiu Putih'),
('C61', 'Rizqi Dwi Dharma', '232410113', 'XI MIPA 2', 'Jl. Paus Biru'),
('C62', 'Sharfa Safira El Fouz', '232410116', 'XI MIPA 2', 'Jl. Lumba-Lumba Ceria'),
('C63', 'Sidik Zaen Muchtor', '232410118', 'XI MIPA 2', 'Jl. Penyu Laut'),
('C64', 'Syahla Fadhila Alif Prayoga', '232410120', 'XI MIPA 2', 'Jl. Pari Manta'),
('C65', 'Syahrani Julyansah Putri', '232410121', 'XI MIPA 2', 'Jl. Gurita Laut'),
('C66', 'Utera Susmeisera', '232410124', 'XI MIPA 2', 'Jl. Kepiting Ria'),
('C67', 'Abiyyu Zuhdi Dafikhairy', '232410138', 'XI MIPA 2', 'Jl. Udang Manis');

-- 9. Data Alternatif (Raw Input berdasarkan Data Terbaru)
INSERT INTO `alternatif` (`id_siswa`, `nama_siswa`, `nilai_akademik`, `prestasi_non_akademik`, `kehadiran`, `sikap_perilaku`, `partisipasi_kegiatan`) VALUES
('C1', 'Zahra Ainun Nadhiroh', 86.00, 'Tidak Memiliki Prestasi', 93.00, 'Baik', 'Aktif Rutin'),
('C2', 'Dhezan Shakti Al Hajj', 89.00, 'Partisipasi Aktif', 84.00, 'Baik', 'Aktif dan Memiliki Peran'),
('C3', 'Arfa Huriya Elfaradis', 88.00, 'Tidak Memiliki Prestasi', 93.00, 'Baik', 'Kadang-Kadang Aktif'),
('C4', 'Zaskia Nasywaa Pamungkas', 91.00, 'Partisipasi Aktif', 100.00, 'Baik', 'Aktif dan Memiliki Peran'),
('C5', 'Bunga Maulidya Caesar', 88.00, 'Juara Tingkat Provinsi/Kota', 88.00, 'Baik', 'Aktif Rutin'),
('C6', 'Mayla Yunisiah', 87.00, 'Partisipasi Aktif', 93.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C7', 'Zauhara Maharani', 89.00, 'Juara Tingkat Sekolah', 84.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C8', 'Vina Rahmah', 89.00, 'Partisipasi Aktif', 84.00, 'Baik', 'Aktif dan Memiliki Peran'),
('C9', 'Bunga Firjatullah', 89.00, 'Partisipasi Aktif', 93.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C10', 'Daffa Wicaksana', 86.00, 'Partisipasi Aktif', 99.00, 'Baik', 'Aktif dan Memiliki Peran'),
('C11', 'Affa Khalishah', 88.00, 'Juara Tingkat Nasional', 86.00, 'Baik', 'Aktif Rutin'),
('C12', 'Aidan Veda Rananulma Rizal', 90.00, 'Tidak Memiliki Prestasi', 93.00, 'Sangat Baik', 'Aktif Rutin'),
('C13', 'Alfatari Fatimatuzzachra', 88.00, 'Juara Tingkat Nasional', 86.00, 'Baik', 'Aktif Rutin'),
('C14', 'Alyka Maulida Moha', 89.00, 'Tidak Memiliki Prestasi', 99.00, 'Baik', 'Kadang-Kadang Aktif'),
('C15', 'Annadia Shafiya Sulkha', 90.00, 'Partisipasi Aktif', 99.00, 'Baik', 'Aktif Rutin'),
('C16', 'Ayu Anindytia Maharani', 87.00, 'Tidak Memiliki Prestasi', 93.00, 'Baik', 'Kadang-Kadang Aktif'),
('C17', 'Bella Nearsyaikh Azizah', 87.00, 'Partisipasi Aktif', 84.00, 'Baik', 'Aktif Rutin'),
('C18', 'Dafa Zulfan Hammani', 88.00, 'Partisipasi Aktif', 99.00, 'Baik', 'Aktif Rutin'),
('C19', 'Daffa Attila Hendrawan', 91.00, 'Partisipasi Aktif', 93.00, 'Sangat Baik', 'Kadang-Kadang Aktif'),
('C20', 'Dhana Nugraha Abdhi Prawiranegara', 89.00, 'Partisipasi Aktif', 84.00, 'Baik', 'Aktif Rutin'),
('C21', 'Dheeandra Aqila Fayyaza', 88.00, 'Partisipasi Aktif', 84.00, 'Baik', 'Aktif dan Memiliki Peran'),
('C22', 'Fakhriyah Althafuz Zahraniyah', 88.00, 'Partisipasi Aktif', 95.00, 'Baik', 'Kadang-Kadang Aktif'),
('C23', 'Farrah Rahma Annisa', 89.00, 'Partisipasi Aktif', 95.00, 'Baik', 'Kadang-Kadang Aktif'),
('C24', 'Ibrahim Al Hawaary', 88.00, 'Partisipasi Aktif', 84.00, 'Baik', 'Aktif dan Memiliki Peran'),
('C25', 'Latisya Zalfa Aretha', 88.00, 'Partisipasi Aktif', 84.00, 'Baik', 'Aktif Rutin'),
('C26', 'Muhammad Fairuz Ibnu Handoyo', 88.00, 'Partisipasi Aktif', 84.00, 'Baik', 'Aktif dan Memiliki Peran'),
('C27', 'Muhammad Gavin Azarya', 89.00, 'Partisipasi Aktif', 95.00, 'Sangat Baik', 'Aktif Rutin'),
('C28', 'Muhammad Ghozi Achdani', 89.00, 'Partisipasi Aktif', 93.00, 'Baik', 'Aktif dan Memiliki Peran'),
('C29', 'Muhammad Ihsan Annazhimi', 88.00, 'Partisipasi Aktif', 93.00, 'Baik', 'Kadang-Kadang Aktif'),
('C30', 'Muhammad Luthfi Al Ghazali', 87.00, 'Partisipasi Aktif', 93.00, 'Baik', 'Kadang-Kadang Aktif'),
('C31', 'Muhammad Nabil Ibnu Am', 86.00, 'Juara Tingkat Nasional', 86.00, 'Baik', 'Aktif dan Memiliki Peran'),
('C32', 'Najma Afifah', 91.00, 'Partisipasi Aktif', 86.00, 'Sangat Baik', 'Kadang-Kadang Aktif'),
('C33', 'Najwa Qurrotannashita', 89.00, 'Tidak Memiliki Prestasi', 88.00, 'Baik', 'Kadang-Kadang Aktif'),
('C34', 'Nur Nismu Imaniah Arsyad', 89.00, 'Juara Tingkat Sekolah', 93.00, 'Baik', 'Kadang-Kadang Aktif'),
('C35', 'Queennesya Hafiizhra Manchesta Prastika', 86.00, 'Partisipasi Aktif', 86.00, 'Baik', 'Aktif Rutin'),
('C36', 'Rafaneo Arzarli Imron', 87.00, 'Partisipasi Aktif', 93.00, 'Baik', 'Aktif Rutin'),
('C37', 'Raihanah Rizqia Putri Adiska', 89.00, 'Partisipasi Aktif', 93.00, 'Baik', 'Kadang-Kadang Aktif'),
('C38', 'Reza Ahmad Khoiryama', 86.00, 'Juara Tingkat Nasional', 80.00, 'Baik', 'Aktif Rutin'),
('C39', 'Rizqya Naurah Khairani', 90.00, 'Partisipasi Aktif', 93.00, 'Sangat Baik', 'Aktif Rutin'),
('C40', 'Ahmad Nu''aina Fathan', 88.00, 'Partisipasi Aktif', 95.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C41', 'Al Mira Rahma Kayana Putri Irawan', 91.00, 'Partisipasi Aktif', 86.00, 'Sangat Baik', 'Aktif Rutin'),
('C42', 'Amanda Sukma', 90.00, 'Partisipasi Aktif', 85.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C43', 'Arnesya Mutiara', 89.00, 'Partisipasi Aktif', 84.00, 'Sangat Baik', 'Aktif Rutin'),
('C44', 'Elmira Fairuz Inaya', 88.00, 'Partisipasi Aktif', 83.00, 'Sangat Baik', 'Aktif Rutin'),
('C45', 'Farres Bima Prayuga', 88.00, 'Partisipasi Aktif', 99.00, 'Sangat Baik', 'Aktif Rutin'),
('C46', 'Ikhwan Ma''ruf Al Hanania', 88.00, 'Tidak Memiliki Prestasi', 85.00, 'Baik', 'Aktif Rutin'),
('C47', 'Isna Kusuma Suryani', 90.00, 'Juara Tingkat Provinsi/kota', 93.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C48', 'Leana Calya Vania Setyahadi', 88.00, 'Tidak Memiliki Prestasi', 92.00, 'Baik', 'Kadang-Kadang Aktif'),
('C49', 'Mayesha Nayottama Astrawinata', 88.00, 'Partisipasi Aktif', 84.00, 'Sangat Baik', 'Aktif Rutin'),
('C50', 'Mishya Yumna Ramadhani', 88.00, 'Partisipasi Aktif', 81.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C51', 'Muhammad Aqila Alfarezi', 86.00, 'Tidak Memiliki Prestasi', 84.00, 'Baik', 'Kadang-Kadang Aktif'),
('C52', 'Muhammad Naufal Desvanand', 87.00, 'Partisipasi Aktif', 92.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C53', 'Nadira Ayu', 91.00, 'Juara Tingkat Nasional', 93.00, 'Sangat Baik', 'Aktif Rutin'),
('C54', 'Nadja Novanza', 85.00, 'Tidak Memiliki Prestasi', 80.00, 'Baik', 'Kadang-Kadang Aktif'),
('C55', 'Najwa Asyilah Astuadi', 89.00, 'Partisipasi Aktif', 97.00, 'Sangat Baik', 'Aktif Rutin'),
('C56', 'Naufal Akbar Julian', 86.00, 'Partisipasi Aktif', 90.00, 'Sangat Baik', 'Kadang-Kadang Aktif'),
('C57', 'Nisrina', 88.00, 'Tidak Memiliki Prestasi', 86.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C58', 'Radhitya Sulaiman', 89.00, 'Tidak Memiliki Prestasi', 99.00, 'Sangat Baik', 'Kadang-Kadang Aktif'),
('C59', 'Rafael Ezza Wiratama', 88.00, 'Tidak Memiliki Prestasi', 100.00, 'Baik', 'Aktif Rutin'),
('C60', 'Rifaa Ilhaam Ramadhaan', 86.00, 'Tidak Memiliki Prestasi', 95.00, 'Baik', 'Aktif Rutin'),
('C61', 'Rizqi Dwi Dharma', 86.00, 'Partisipasi Aktif', 82.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C62', 'Sharfa Safira El Fouz', 90.00, 'Partisipasi Aktif', 95.00, 'Sangat Baik', 'Aktif dan Memiliki Peran'),
('C63', 'Sidik Zaen Muchtor', 87.00, 'Tidak Memiliki Prestasi', 90.00, 'Baik', 'Aktif Rutin'),
('C64', 'Syahla Fadhila Alif Prayoga', 88.00, 'Juara Tingkat Nasional', 95.00, 'Sangat Baik', 'Aktif Rutin'),
('C65', 'Syahrani Julyansah Putri', 89.00, 'Juara Tingkat Provinsi/kota', 81.00, 'Sangat Baik', 'Aktif Rutin'),
('C66', 'Utera Susmeisera', 89.00, 'Partisipasi Aktif', 96.00, 'Sangat Baik', 'Kadang-Kadang Aktif'),
('C67', 'Abiyyu Zuhdi Dafikhairy', 87.00, 'Partisipasi Aktif', 84.00, 'Baik', 'Aktif Rutin');

-- 10. Data Penilaian (Nilai Konversi Skala 0-100 berdasarkan Data Terbaru)
INSERT INTO `penilaian` (`id_siswa`, `nama_siswa`, `nilai_akademik`, `prestasi_non_akademik`, `kehadiran`, `sikap_perilaku`, `partisipasi_kegiatan`) VALUES
('C1', 'Zahra Ainun Nadhiroh', 86, 60, 93, 85, 85),
('C2', 'Dhezan Shakti Al Hajj', 89, 70, 84, 85, 100),
('C3', 'Arfa Huriya Elfaradis', 88, 60, 93, 85, 75),
('C4', 'Zaskia Nasywaa Pamungkas', 91, 70, 100, 85, 100),
('C5', 'Bunga Maulidya Caesar', 88, 90, 88, 85, 85),
('C6', 'Mayla Yunisiah', 87, 70, 93, 100, 100),
('C7', 'Zauhara Maharani', 89, 80, 84, 100, 100),
('C8', 'Vina Rahmah', 89, 70, 84, 85, 100),
('C9', 'Bunga Firjatullah', 89, 70, 93, 100, 100),
('C10', 'Daffa Wicaksana', 86, 70, 99, 85, 100),
('C11', 'Affa Khalishah', 88, 100, 86, 85, 85),
('C12', 'Aidan Veda Rananulma Rizal', 90, 60, 93, 100, 85),
('C13', 'Alfatari Fatimatuzzachra', 88, 100, 86, 85, 85),
('C14', 'Alyka Maulida Moha', 89, 60, 99, 85, 75),
('C15', 'Annadia Shafiya Sulkha', 90, 70, 99, 85, 85),
('C16', 'Ayu Anindytia Maharani', 87, 60, 93, 85, 75),
('C17', 'Bella Nearsyaikh Azizah', 87, 70, 84, 85, 85),
('C18', 'Dafa Zulfan Hammani', 88, 70, 99, 85, 85),
('C19', 'Daffa Attila Hendrawan', 91, 70, 93, 100, 75),
('C20', 'Dhana Nugraha Abdhi Prawiranegara', 89, 70, 84, 85, 85),
('C21', 'Dheeandra Aqila Fayyaza', 88, 70, 84, 85, 100),
('C22', 'Fakhriyah Althafuz Zahraniyah', 88, 70, 95, 85, 75),
('C23', 'Farrah Rahma Annisa', 89, 70, 95, 85, 75),
('C24', 'Ibrahim Al Hawaary', 88, 70, 84, 85, 100),
('C25', 'Latisya Zalfa Aretha', 88, 70, 84, 85, 85),
('C26', 'Muhammad Fairuz Ibnu Handoyo', 88, 70, 84, 85, 100),
('C27', 'Muhammad Gavin Azarya', 89, 70, 95, 100, 85),
('C28', 'Muhammad Ghozi Achdani', 89, 70, 93, 85, 100),
('C29', 'Muhammad Ihsan Annazhimi', 88, 70, 93, 85, 75),
('C30', 'Muhammad Luthfi Al Ghazali', 87, 70, 93, 85, 75),
('C31', 'Muhammad Nabil Ibnu Am', 86, 100, 86, 85, 100),
('C32', 'Najma Afifah', 91, 70, 86, 100, 75),
('C33', 'Najwa Qurrotannashita', 89, 60, 88, 85, 75),
('C34', 'Nur Nismu Imaniah Arsyad', 89, 80, 93, 85, 75),
('C35', 'Queennesya Hafiizhra Manchesta Prastika', 86, 70, 86, 85, 85),
('C36', 'Rafaneo Arzarli Imron', 87, 70, 93, 85, 85),
('C37', 'Raihanah Rizqia Putri Adiska', 89, 70, 93, 85, 75),
('C38', 'Reza Ahmad Khoiryama', 86, 100, 80, 85, 85),
('C39', 'Rizqya Naurah Khairani', 90, 70, 93, 100, 85),
('C40', 'Ahmad Nu''aina Fathan', 88, 70, 95, 100, 100),
('C41', 'Al Mira Rahma Kayana Putri Irawan', 91, 70, 86, 100, 85),
('C42', 'Amanda Sukma', 90, 70, 85, 100, 100),
('C43', 'Arnesya Mutiara', 89, 70, 84, 100, 85),
('C44', 'Elmira Fairuz Inaya', 88, 70, 83, 100, 85),
('C45', 'Farres Bima Prayuga', 88, 70, 99, 100, 85),
('C46', 'Ikhwan Ma''ruf Al Hanania', 88, 60, 85, 85, 85),
('C47', 'Isna Kusuma Suryani', 90, 90, 93, 100, 100),
('C48', 'Leana Calya Vania Setyahadi', 88, 60, 92, 85, 75),
('C49', 'Mayesha Nayottama Astrawinata', 88, 70, 84, 100, 85),
('C50', 'Mishya Yumna Ramadhani', 88, 70, 81, 100, 100),
('C51', 'Muhammad Aqila Alfarezi', 86, 60, 84, 85, 75),
('C52', 'Muhammad Naufal Desvanand', 87, 70, 92, 100, 100),
('C53', 'Nadira Ayu', 91, 100, 93, 100, 85),
('C54', 'Nadja Novanza', 85, 60, 80, 85, 75),
('C55', 'Najwa Asyilah Astuadi', 89, 70, 97, 100, 85),
('C56', 'Naufal Akbar Julian', 86, 70, 90, 100, 75),
('C57', 'Nisrina', 88, 60, 86, 100, 100),
('C58', 'Radhitya Sulaiman', 89, 60, 99, 100, 75),
('C59', 'Rafael Ezza Wiratama', 88, 60, 100, 85, 85),
('C60', 'Rifaa Ilhaam Ramadhaan', 86, 60, 95, 85, 85),
('C61', 'Rizqi Dwi Dharma', 86, 70, 82, 100, 100),
('C62', 'Sharfa Safira El Fouz', 90, 70, 95, 100, 100),
('C63', 'Sidik Zaen Muchtor', 87, 60, 90, 85, 85),
('C64', 'Syahla Fadhila Alif Prayoga', 88, 100, 95, 100, 85),
('C65', 'Syahrani Julyansah Putri', 89, 90, 81, 100, 85),
('C66', 'Utera Susmeisera', 89, 70, 96, 100, 75),
('C67', 'Abiyyu Zuhdi Dafikhairy', 87, 70, 84, 85, 85);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
