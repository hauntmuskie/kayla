-- Quick Database Setup Script for SMA Islam Terpadu Raflesia
-- Student Excellence Evaluation System
-- Run this script to quickly set up the database with sample data

-- Use the database
USE penilaian_siswa_berprestasi;

-- Clear existing data (if any)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE nilai_akhir;
TRUNCATE TABLE utility;
TRUNCATE TABLE penilaian;
TRUNCATE TABLE alternatif;
TRUNCATE TABLE siswa;
TRUNCATE TABLE kriteria;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert criteria data
INSERT INTO kriteria (kode_kriteria, nama_kriteria, bobot_kriteria) VALUES
('K1', 'Nilai Akademik', 0.35),
('K2', 'Prestasi Non-Akademik', 0.20),
('K3', 'Kehadiran', 0.15),
('K4', 'Sikap/Perilaku', 0.20),
('K5', 'Partisipasi Kegiatan Sekolah', 0.10);

-- Insert sample student data
INSERT INTO siswa (id_siswa, nama_siswa, nisn, kelas, alamat) VALUES
('SIS001', 'Ahmad Fauzi Rahman', '1234567890', 'X IPA 1', 'Jl. Melati No. 15, Bogor'),
('SIS002', 'Siti Aminah Putri', '1234567891', 'XI IPS 2', 'Jl. Kenanga No. 23, Bogor'),
('SIS003', 'Muhammad Rizki Hakim', '1234567892', 'X IPA 2', 'Jl. Mawar No. 8, Bogor'),
('SIS004', 'Fatimah Zahra', '1234567893', 'XI IPA 1', 'Jl. Dahlia No. 12, Bogor'),
('SIS005', 'Abdullah Al-Farisi', '1234567894', 'X IPS 1', 'Jl. Anggrek No. 7, Bogor'),
('SIS006', 'Khadijah Salsabila', '1234567895', 'XI IPA 2', 'Jl. Tulip No. 19, Bogor');

COMMIT;

SELECT 'Database setup completed successfully!' AS Status;
