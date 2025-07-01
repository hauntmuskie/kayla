# Database Setup Guide
## SMA Islam Terpadu Raflesia - Student Excellence Evaluation System

### Database Structure

This system uses the **penilaian_siswa_berprestasi** database with the following tables:

1. **siswa** - Student master data
   - id_siswa (Primary Key)
   - nama_siswa
   - nisn (National Student ID Number)
   - kelas (Class)
   - alamat (Address)

2. **kriteria** - Evaluation criteria
   - kode_kriteria (K1-K5)
   - nama_kriteria (Criteria name)
   - bobot_kriteria (Weight/percentage)

3. **alternatif** - Raw student scores per criteria
   - Contains actual values before normalization

4. **penilaian** - Normalized scores (0-100 scale)
   - Contains converted values ready for SMART method

5. **utility** - Utility values calculated using SMART method
   - Contains normalized utility values (0-1 scale)

6. **nilai_akhir** - Final results
   - Contains weighted final scores and decisions

### How to Setup

1. **Full Database Setup:**
   ```sql
   source penilaian_siswa_berprestasi.sql
   ```

2. **Quick Setup (existing database):**
   ```sql
   source quick_setup.sql
   ```

### Evaluation Criteria

The system evaluates students based on 5 criteria:

- **K1: Nilai Akademik (35%)** - Academic performance
- **K2: Prestasi Non-Akademik (20%)** - Non-academic achievements
- **K3: Kehadiran (15%)** - Attendance
- **K4: Sikap/Perilaku (20%)** - Attitude and behavior
- **K5: Partisipasi Kegiatan Sekolah (10%)** - School activity participation

### Decision Categories

Based on final scores:
- **Sangat Baik** - Excellent (Top performers)
- **Baik** - Good
- **Cukup** - Average
- **Kurang** - Below average

### Usage Workflow

1. **Data Siswa** - Add student information
2. **Data Kriteria** - Define evaluation criteria (pre-configured)
3. **Data Alternatif** - Input raw assessment data
4. **Data Penilaian** - Convert to 0-100 scale
5. **Proses Metode SMART** - Calculate utility and final values
6. **Generate Reports** - View and print results

### Notes

- All foreign key relationships are set up with CASCADE DELETE
- The system maintains data integrity across all related tables
- Sample data is provided for testing and demonstration purposes
