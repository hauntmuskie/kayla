/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.placeholder.PlaceHolder;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Elza Kayla
 */
public class DataRank extends javax.swing.JFrame {
        private Connection conn = new DatabaseConnection().connect();
        public String nilaikriteria;
        private DefaultTableModel tabmode;
        PlaceHolder pl;
        public String idsiswa, nama, akademik, prestasi, kehadiran, sikap, partisipasi, kri1, kri2, kri3;

        /**
         * Creates new form datarank
         */
        public DataRank() {
                initComponents();
                kosong();
                Locale locale = new Locale("id", "ID");
                Locale.setDefault(locale);
                bhapus.setEnabled(false);

                // non editable textfield
                txtid.setEditable(false);
                txtnm.setEditable(false);
                txttepat.setEditable(false);
                txtakurasi.setEditable(false);
                txtjml.setEditable(false);
                txtint.setEditable(false);
                txtpenangan.setEditable(false);
                txtkd1.setEditable(false);
                txtkd2.setEditable(false);
                txtkd3.setEditable(false);
                txtkd4.setEditable(false);
                txtkd5.setEditable(false);
                txtnilaiakhir.setEditable(false);

                // Load bobot kriteria from database
                try {
                        String sql = "SELECT bobot_kriteria FROM kriteria ORDER BY kode_kriteria ASC";
                        Statement stat = conn.createStatement();
                        ResultSet rs = stat.executeQuery(sql);
                        int idx = 1;
                        while (rs.next() && idx <= 5) {
                                String bobot = String.format(Locale.US, "%.2f", rs.getDouble("bobot_kriteria"));
                                switch (idx) {
                                        case 1:
                                                txtk1.setText(bobot);
                                                break;
                                        case 2:
                                                txtk2.setText(bobot);
                                                break;
                                        case 3:
                                                txtk3.setText(bobot);
                                                break;
                                        case 4:
                                                txtk4.setText(bobot);
                                                break;
                                        case 5:
                                                txtk5.setText(bobot);
                                                break;
                                }
                                idx++;
                        }
                } catch (SQLException e) {
                        // fallback default if db error
                        txtk1.setText("0.35");
                        txtk2.setText("0.20");
                        txtk3.setText("0.15");
                        txtk4.setText("0.20");
                        txtk5.setText("0.10");
                }

                datatable();
        }

        private void datatable() {
                // Load criteria names from database for dynamic column headers
                String[] criteriaNames = new String[5];
                try {
                        String sqlCriteria = "SELECT nama_kriteria FROM kriteria ORDER BY kode_kriteria ASC";
                        Statement statCriteria = conn.createStatement();
                        ResultSet rsCriteria = statCriteria.executeQuery(sqlCriteria);
                        int idx = 0;
                        while (rsCriteria.next() && idx < 5) {
                                criteriaNames[idx] = rsCriteria.getString("nama_kriteria");
                                idx++;
                        }
                } catch (SQLException e) {
                        // Fallback criteria names if database error
                        criteriaNames[0] = "Nilai Akademik";
                        criteriaNames[1] = "Prestasi Non-Akademik";
                        criteriaNames[2] = "Kehadiran";
                        criteriaNames[3] = "Sikap/Perilaku";
                        criteriaNames[4] = "Partisipasi Kegiatan Sekolah";
                }

                // Create dynamic column headers
                Object[] Baris = { "ID Siswa", "Nama Siswa", criteriaNames[0], criteriaNames[1], criteriaNames[2],
                                criteriaNames[3], criteriaNames[4], "Jumlah Nilai Akhir", "Ranking" };
                tabmode = new DefaultTableModel(null, Baris);

                try {
                        String sql = "SELECT * FROM nilai_akhir ORDER BY jumlah_nilai_akhir DESC";
                        Statement stat = conn.createStatement();
                        ResultSet hasil = stat.executeQuery(sql);
                        while (hasil.next()) {
                                tabmode.addRow(new Object[] {
                                                hasil.getString("id_siswa"),
                                                hasil.getString("nama_siswa"),
                                                hasil.getString("nilai_akhir_akademik"),
                                                hasil.getString("nilai_akhir_prestasi"),
                                                hasil.getString("nilai_akhir_kehadiran"),
                                                hasil.getString("nilai_akhir_sikap"),
                                                hasil.getString("nilai_akhir_partisipasi"),
                                                hasil.getString("jumlah_nilai_akhir"),
                                                hasil.getInt("ranking")
                                });
                        }
                        tabelrank.setModel(tabmode);
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "data gagal dipanggil: " + e);
                }
        }

        private void cari() {
                // Load criteria names from database for dynamic column headers
                String[] criteriaNames = new String[5];
                try {
                        String sqlCriteria = "SELECT nama_kriteria FROM kriteria ORDER BY kode_kriteria ASC";
                        Statement statCriteria = conn.createStatement();
                        ResultSet rsCriteria = statCriteria.executeQuery(sqlCriteria);
                        int idx = 0;
                        while (rsCriteria.next() && idx < 5) {
                                criteriaNames[idx] = rsCriteria.getString("nama_kriteria");
                                idx++;
                        }
                } catch (SQLException e) {
                        // Fallback criteria names if database error
                        criteriaNames[0] = "Nilai Akademik";
                        criteriaNames[1] = "Prestasi Non-Akademik";
                        criteriaNames[2] = "Kehadiran";
                        criteriaNames[3] = "Sikap/Perilaku";
                        criteriaNames[4] = "Partisipasi Kegiatan Sekolah";
                }

                // Create dynamic column headers
                Object[] Baris = { "ID Siswa", "Nama Siswa", criteriaNames[0], criteriaNames[1], criteriaNames[2],
                                criteriaNames[3], criteriaNames[4], "Jumlah Nilai Akhir", "Ranking" };
                tabmode = new DefaultTableModel(null, Baris);

                String cariitem = txtcari.getText();
                try {
                        String sql = "SELECT * FROM nilai_akhir WHERE id_siswa = ? OR nama_siswa = ? OR nilai_akhir_akademik = ? OR nilai_akhir_prestasi = ? OR nilai_akhir_kehadiran = ? OR nilai_akhir_sikap = ? OR nilai_akhir_partisipasi = ? OR jumlah_nilai_akhir = ? OR ranking = ? ORDER BY jumlah_nilai_akhir DESC";
                        PreparedStatement stat = conn.prepareStatement(sql);
                        for (int i = 1; i <= 9; i++) {
                                stat.setString(i, cariitem);
                        }
                        ResultSet hasil = stat.executeQuery();
                        while (hasil.next()) {
                                tabmode.addRow(new Object[] {
                                                hasil.getString("id_siswa"),
                                                hasil.getString("nama_siswa"),
                                                hasil.getString("nilai_akhir_akademik"),
                                                hasil.getString("nilai_akhir_prestasi"),
                                                hasil.getString("nilai_akhir_kehadiran"),
                                                hasil.getString("nilai_akhir_sikap"),
                                                hasil.getString("nilai_akhir_partisipasi"),
                                                hasil.getString("jumlah_nilai_akhir"),
                                                hasil.getInt("ranking")
                                });
                        }
                        tabelrank.setModel(tabmode);
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "data gagal dipanggil: " + e);
                }
        }

        private void kosong() {
                txtid.setText("");
                txtnm.setText("");
                txttepat.setText("");
                txtakurasi.setText("");
                txtjml.setText("");
                txtint.setText("");
                txtpenangan.setText("");
                txtkd1.setText("");
                txtkd2.setText("");
                txtkd3.setText("");
                txtkd4.setText("");
                txtkd5.setText("");
                txtnilaiakhir.setText("");
                txtrank.setText("");
                txtcari.setText("");
                pl = new PlaceHolder(txtcari, "Pencarian data...");
        }

        private void tambah() {
                String sql = "INSERT INTO nilai_akhir (id_siswa, nama_siswa, nilai_akhir_akademik, nilai_akhir_prestasi, nilai_akhir_kehadiran, nilai_akhir_sikap, nilai_akhir_partisipasi, jumlah_nilai_akhir, ranking) VALUES (?,?,?,?,?,?,?,?,?)";
                try {
                        PreparedStatement stat = conn.prepareStatement(sql);
                        stat.setString(1, txtid.getText());
                        stat.setString(2, txtnm.getText());

                        // Parse decimal values with proper locale handling (replace comma with dot)
                        double nilaiAkhirAkademik = Double.parseDouble(txtkd1.getText().replace(",", "."));
                        double nilaiAkhirPrestasi = Double.parseDouble(txtkd2.getText().replace(",", "."));
                        double nilaiAkhirKehadiran = Double.parseDouble(txtkd3.getText().replace(",", "."));
                        double nilaiAkhirSikap = Double.parseDouble(txtkd4.getText().replace(",", "."));
                        double nilaiAkhirPartisipasi = Double.parseDouble(txtkd5.getText().replace(",", "."));
                        double jumlahNilaiAkhir = Double.parseDouble(txtnilaiakhir.getText().replace(",", "."));

                        // Use setDouble for decimal columns
                        stat.setDouble(3, nilaiAkhirAkademik);
                        stat.setDouble(4, nilaiAkhirPrestasi);
                        stat.setDouble(5, nilaiAkhirKehadiran);
                        stat.setDouble(6, nilaiAkhirSikap);
                        stat.setDouble(7, nilaiAkhirPartisipasi);
                        stat.setDouble(8, jumlahNilaiAkhir);
                        stat.setInt(9, Integer.parseInt(txtrank.getText())); // ranking as integer

                        stat.executeUpdate();

                        // Update all rankings after inserting new data
                        updateAllRankings();

                        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                        kosong();
                        txtid.requestFocus();
                } catch (SQLException e) {
                        System.err.println("Error: " + e.getMessage());
                        JOptionPane.showMessageDialog(null, "Data gagal disimpan: " + e.getMessage());
                } catch (NumberFormatException e) {
                        System.err.println("Number format error: " + e.getMessage());
                        JOptionPane.showMessageDialog(null,
                                        "Format angka tidak valid. Pastikan semua nilai telah dihitung dengan benar.");
                }
                datatable();
        }

        public void itemTerpilih() {
                PopupUtility PNA = new PopupUtility();
                PNA.dr = this;
                txtid.setText(idsiswa);
                txtnm.setText(nama);
                txttepat.setText(akademik);
                txtakurasi.setText(prestasi);
                txtjml.setText(kehadiran);
                txtint.setText(sikap);
                txtpenangan.setText(partisipasi);
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jScrollPane2 = new javax.swing.JScrollPane();
                jPanel1 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jPanel2 = new javax.swing.JPanel();
                jPanel3 = new javax.swing.JPanel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                txtid = new javax.swing.JTextField();
                txtnm = new javax.swing.JTextField();
                bdata = new javax.swing.JButton();
                jPanel4 = new javax.swing.JPanel();
                jLabel8 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                jLabel11 = new javax.swing.JLabel();
                jLabel12 = new javax.swing.JLabel();
                txttepat = new javax.swing.JTextField();
                txtakurasi = new javax.swing.JTextField();
                txtjml = new javax.swing.JTextField();
                txtint = new javax.swing.JTextField();
                txtpenangan = new javax.swing.JTextField();
                jPanel6 = new javax.swing.JPanel();
                jLabel13 = new javax.swing.JLabel();
                jLabel14 = new javax.swing.JLabel();
                jLabel15 = new javax.swing.JLabel();
                jLabel16 = new javax.swing.JLabel();
                jLabel17 = new javax.swing.JLabel();
                txtk1 = new javax.swing.JTextField();
                txtk2 = new javax.swing.JTextField();
                txtk3 = new javax.swing.JTextField();
                txtk4 = new javax.swing.JTextField();
                txtk5 = new javax.swing.JTextField();
                jPanel7 = new javax.swing.JPanel();
                jLabel18 = new javax.swing.JLabel();
                jLabel19 = new javax.swing.JLabel();
                jLabel20 = new javax.swing.JLabel();
                jLabel21 = new javax.swing.JLabel();
                jLabel22 = new javax.swing.JLabel();
                txtkd1 = new javax.swing.JTextField();
                txtkd2 = new javax.swing.JTextField();
                txtkd3 = new javax.swing.JTextField();
                txtkd4 = new javax.swing.JTextField();
                txtkd5 = new javax.swing.JTextField();
                jLabel23 = new javax.swing.JLabel();
                txtnilaiakhir = new javax.swing.JTextField();
                txtrank = new javax.swing.JTextField();
                jLabel24 = new javax.swing.JLabel();
                bhitung = new javax.swing.JButton();
                bsimpan = new javax.swing.JButton();
                bhapus = new javax.swing.JButton();
                bbersihkan = new javax.swing.JButton();
                brefresh = new javax.swing.JButton();
                txtcari = new javax.swing.JTextField();
                bcari = new javax.swing.JButton();
                jScrollPane1 = new javax.swing.JScrollPane();
                tabelrank = new javax.swing.JTable();
                bcetak = new javax.swing.JButton();
                jLabel6 = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setUndecorated(true);

                jPanel1.setBackground(new java.awt.Color(231, 229, 179));

                jLabel1.setBackground(new java.awt.Color(102, 102, 102));
                jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(138, 120, 78));
                jLabel1.setText("Form Perhitungan");

                jPanel2.setBackground(new java.awt.Color(129, 144, 103));
                jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hasil Perhitungan Metode SMART",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N
                jPanel2.setForeground(new java.awt.Color(255, 255, 255));

                jPanel3.setBackground(new java.awt.Color(177, 171, 134));

                jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                jLabel3.setText("ID Siswa");

                jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                jLabel4.setText("Nama Siswa");

                txtid.setBackground(new java.awt.Color(234, 228, 213));
                txtid.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtidActionPerformed(evt);
                        }
                });

                txtnm.setBackground(new java.awt.Color(234, 228, 213));

                bdata.setBackground(new java.awt.Color(138, 120, 78));
                bdata.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
                bdata.setForeground(new java.awt.Color(255, 255, 255));
                bdata.setText("Pilih Data Utility");
                bdata.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                bdataActionPerformed(evt);
                        }
                });

                jPanel4.setBackground(new java.awt.Color(177, 171, 134));
                jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                                "Nilai Utility",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI Historic", 3, 18))); // NOI18N

                jLabel8.setBackground(new java.awt.Color(0, 0, 0));
                jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel8.setText("Nilai Akademik (K1)");

                jLabel9.setBackground(new java.awt.Color(0, 0, 0));
                jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel9.setText("Prestasi Non-Akademik (K2)");

                jLabel10.setBackground(new java.awt.Color(0, 0, 0));
                jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel10.setText("Kehadiran (K3)");

                jLabel11.setBackground(new java.awt.Color(0, 0, 0));
                jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel11.setText("Sikap/Perilaku (K4)");

                jLabel12.setBackground(new java.awt.Color(0, 0, 0));
                jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel12.setText("Partisipasi Kegiatan Sekolah (K5)");

                txttepat.setEditable(false);
                txttepat.setBackground(new java.awt.Color(234, 228, 213));

                txtakurasi.setBackground(new java.awt.Color(234, 228, 213));

                txtjml.setBackground(new java.awt.Color(234, 228, 213));

                txtint.setBackground(new java.awt.Color(234, 228, 213));

                txtpenangan.setBackground(new java.awt.Color(234, 228, 213));

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel8)
                                                                                .addComponent(jLabel9)
                                                                                .addComponent(jLabel10)
                                                                                .addComponent(jLabel11)
                                                                                .addComponent(jLabel12))
                                                                .addGap(31, 31, 31)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(txtakurasi,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                83,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txttepat,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                83,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txtjml,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                83,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txtint,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                83,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txtpenangan,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                83,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel4Layout.setVerticalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel8)
                                                                                .addComponent(txttepat,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel9)
                                                                                .addComponent(txtakurasi,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel10)
                                                                                .addComponent(txtjml,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel11)
                                                                                .addComponent(txtint,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel12)
                                                                                .addComponent(txtpenangan,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 0, Short.MAX_VALUE)));

                jPanel6.setBackground(new java.awt.Color(177, 171, 134));
                jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                                "Nilai Bobot Kriteria",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI Historic", 3, 18))); // NOI18N

                jLabel13.setBackground(new java.awt.Color(0, 0, 0));
                jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel13.setText("Nilai Akademik (K1)");

                jLabel14.setBackground(new java.awt.Color(0, 0, 0));
                jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel14.setText("Prestasi Non-Akademik (K2)");

                jLabel15.setBackground(new java.awt.Color(0, 0, 0));
                jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel15.setText("Kehadiran (K3)");

                jLabel16.setBackground(new java.awt.Color(0, 0, 0));
                jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel16.setText("Sikap/Perilaku (K4)");

                jLabel17.setBackground(new java.awt.Color(0, 0, 0));
                jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel17.setText("Partisipasi Kegiatan Sekolah (K5)");

                txtk1.setBackground(new java.awt.Color(234, 228, 213));

                txtk2.setBackground(new java.awt.Color(234, 228, 213));

                txtk3.setBackground(new java.awt.Color(234, 228, 213));

                txtk4.setBackground(new java.awt.Color(234, 228, 213));

                txtk5.setBackground(new java.awt.Color(234, 228, 213));

                javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                jPanel6.setLayout(jPanel6Layout);
                jPanel6Layout.setHorizontalGroup(
                                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel6Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel6Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel17)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(txtk5,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                83,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(jPanel6Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel16)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(txtk4,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                83,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(jPanel6Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel15)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(txtk3,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                83,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(jPanel6Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel14)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(txtk2,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                83,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(jPanel6Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel13)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(txtk1,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                83,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap()));
                jPanel6Layout.setVerticalGroup(
                                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addGroup(jPanel6Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel13)
                                                                                .addComponent(txtk1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel6Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel14)
                                                                                .addComponent(txtk2,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel6Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel15)
                                                                                .addComponent(txtk3,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel6Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(txtk4,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel16,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel6Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel17)
                                                                                .addComponent(txtk5,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 18, Short.MAX_VALUE)));

                jPanel7.setBackground(new java.awt.Color(177, 171, 134));
                jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nilai Akhir",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI Historic", 3, 18))); // NOI18N

                jLabel18.setBackground(new java.awt.Color(0, 0, 0));
                jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel18.setText("Nilai Akademik (K1)");

                jLabel19.setBackground(new java.awt.Color(0, 0, 0));
                jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel19.setText("Prestasi Non-Akademik (K2)");

                jLabel20.setBackground(new java.awt.Color(0, 0, 0));
                jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel20.setText("Kehadiran (K3)");

                jLabel21.setBackground(new java.awt.Color(0, 0, 0));
                jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel21.setText("Sikap/Perilaku (K4)");

                jLabel22.setBackground(new java.awt.Color(0, 0, 0));
                jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel22.setText("Partisipasi Kegiatan Sekolah (K5)");

                txtkd1.setBackground(new java.awt.Color(234, 228, 213));
                txtkd1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtkd1ActionPerformed(evt);
                        }
                });

                txtkd2.setBackground(new java.awt.Color(234, 228, 213));

                txtkd3.setBackground(new java.awt.Color(234, 228, 213));
                txtkd3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtkd3ActionPerformed(evt);
                        }
                });

                txtkd4.setBackground(new java.awt.Color(234, 228, 213));

                txtkd5.setBackground(new java.awt.Color(234, 228, 213));

                jLabel23.setBackground(new java.awt.Color(0, 0, 0));
                jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel23.setText("Jumlah Nilai Akhir");

                txtnilaiakhir.setBackground(new java.awt.Color(234, 228, 213));
                txtnilaiakhir.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtnilaiakhirActionPerformed(evt);
                        }
                });

                txtrank.setBackground(new java.awt.Color(234, 228, 213));

                jLabel24.setBackground(new java.awt.Color(0, 0, 0));
                jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
                jLabel24.setText("Ranking");

                javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
                jPanel7.setLayout(jPanel7Layout);
                jPanel7Layout.setHorizontalGroup(
                                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel7Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel7Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel18)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(txtkd1,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                83,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel7Layout
                                                                                                                .createSequentialGroup()
                                                                                                                .addComponent(jLabel24)
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(txtrank,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                83,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(jPanel7Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel7Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(jLabel23)
                                                                                                                .addComponent(jLabel22)
                                                                                                                .addComponent(jLabel21)
                                                                                                                .addComponent(jLabel20)
                                                                                                                .addComponent(jLabel19))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(jPanel7Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(jPanel7Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(txtkd5,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                83,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                jPanel7Layout.createSequentialGroup()
                                                                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                                                                .addGroup(jPanel7Layout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addComponent(txtnilaiakhir,
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                83,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(txtkd4,
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                83,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(txtkd3,
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                83,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(txtkd2,
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                83,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                                                .addContainerGap()));
                jPanel7Layout.setVerticalGroup(
                                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addGap(12, 12, 12)
                                                                .addComponent(jLabel18)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel19)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(9, Short.MAX_VALUE)
                                                                .addComponent(txtkd1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txtkd2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel7Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(txtkd3,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel20))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel7Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(txtkd4,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel21))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel7Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(txtkd5,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel22))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel7Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(txtnilaiakhir,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel23))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel7Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(txtrank,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel24))
                                                                .addContainerGap()));

                bhitung.setBackground(new java.awt.Color(138, 120, 78));
                bhitung.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
                bhitung.setForeground(new java.awt.Color(255, 255, 255));
                bhitung.setText("Proses Perhitungan Nilai Akhir");
                bhitung.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                bhitungActionPerformed(evt);
                        }
                });

                bsimpan.setBackground(new java.awt.Color(138, 120, 78));
                bsimpan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
                bsimpan.setForeground(new java.awt.Color(255, 255, 255));
                bsimpan.setText("Simpan");
                bsimpan.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                bsimpanActionPerformed(evt);
                        }
                });

                bhapus.setBackground(new java.awt.Color(255, 0, 0));
                bhapus.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
                bhapus.setForeground(new java.awt.Color(255, 255, 255));
                bhapus.setText("Hapus");
                bhapus.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                bhapusActionPerformed(evt);
                        }
                });

                bbersihkan.setBackground(new java.awt.Color(138, 120, 78));
                bbersihkan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
                bbersihkan.setForeground(new java.awt.Color(255, 255, 255));
                bbersihkan.setText("Bersihkan");
                bbersihkan.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                bbersihkanActionPerformed(evt);
                        }
                });

                brefresh.setBackground(new java.awt.Color(138, 120, 78));
                brefresh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
                brefresh.setForeground(new java.awt.Color(255, 255, 255));
                brefresh.setText("Refresh");
                brefresh.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                brefreshActionPerformed(evt);
                        }
                });

                txtcari.setBackground(new java.awt.Color(234, 228, 213));
                txtcari.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtcariActionPerformed(evt);
                        }
                });

                bcari.setBackground(new java.awt.Color(138, 120, 78));
                bcari.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
                bcari.setForeground(new java.awt.Color(255, 255, 255));
                bcari.setText("Cari");
                bcari.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                bcariActionPerformed(evt);
                        }
                });

                tabelrank.setBackground(new java.awt.Color(234, 228, 213));
                tabelrank.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                {},
                                                {},
                                                {},
                                                {}
                                },
                                new String[] {

                                }));
                tabelrank.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tabelrankMouseClicked(evt);
                        }
                });
                jScrollPane1.setViewportView(tabelrank);

                bcetak.setBackground(new java.awt.Color(138, 120, 78));
                bcetak.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
                bcetak.setForeground(new java.awt.Color(255, 255, 255));
                bcetak.setText("Cetak");
                bcetak.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                bcetakActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel3Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                false)
                                                                                .addComponent(jScrollPane1,
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                jPanel3Layout
                                                                                                                .createSequentialGroup()
                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                .addComponent(jLabel4)
                                                                                                                                .addComponent(jLabel3))
                                                                                                                .addGap(18, 18, 18)
                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                                .createSequentialGroup()
                                                                                                                                                .addComponent(txtid,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                167,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addGap(28, 28, 28)
                                                                                                                                                .addComponent(bdata,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                192,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addComponent(txtnm,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                167,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(bhitung,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                240,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                jPanel3Layout
                                                                                                                .createSequentialGroup()
                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                                .createSequentialGroup()
                                                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                false)
                                                                                                                                                                .addGroup(jPanel3Layout
                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                .addComponent(bsimpan,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                91,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                                                                .addComponent(bbersihkan,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                                                                .addComponent(bhapus,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                122,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                                .addComponent(jPanel4,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                                .addComponent(jPanel6,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                jPanel3Layout.createSequentialGroup()
                                                                                                                                                                .addComponent(brefresh,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                130,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                                                .addComponent(txtcari,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                340,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                                                .addComponent(bcari,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                122,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                                                .addComponent(bcetak,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                125,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                .addComponent(jPanel7,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)))
                                                                .addContainerGap(14, Short.MAX_VALUE)));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel3Layout
                                                                                                .createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addGroup(jPanel3Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(jLabel3)
                                                                                                                .addComponent(txtid,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(bdata))
                                                                                                .addGap(18, 18, 18)
                                                                                                .addGroup(jPanel3Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(jLabel4)
                                                                                                                .addComponent(txtnm,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(jPanel3Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(22, 22, 22)
                                                                                                .addComponent(bhitung,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                42,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                33,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel7,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(jPanel3Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel3Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(jPanel6,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(jPanel4,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addGap(29, 29, 29)
                                                                                                .addGroup(jPanel3Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(bsimpan)
                                                                                                                .addComponent(bbersihkan)
                                                                                                                .addComponent(bhapus))
                                                                                                .addGap(31, 31, 31)
                                                                                                .addGroup(jPanel3Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(brefresh)
                                                                                                                .addComponent(txtcari,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(bcari)
                                                                                                                .addComponent(bcetak))))
                                                                .addGap(0, 22, Short.MAX_VALUE)
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                227,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap()));

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jPanel3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 29, Short.MAX_VALUE)));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE));

                jLabel6.setBackground(new java.awt.Color(102, 102, 102));
                jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel6.setForeground(new java.awt.Color(102, 102, 102));
                jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/back_arrow_14447.png"))); // NOI18N
                jLabel6.setText("Kembali");
                jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel6MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel2,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel6)
                                                                                                .addGap(424, 424, 424)
                                                                                                .addComponent(jLabel1)))
                                                                .addContainerGap(53, Short.MAX_VALUE)));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel6)
                                                                                                .addGap(18, 18, 18))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                .addComponent(jLabel1)
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                                                .addComponent(jPanel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(84, Short.MAX_VALUE)));

                jScrollPane2.setViewportView(jPanel1);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jScrollPane2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                1188,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, 0)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane2,
                                                                javax.swing.GroupLayout.Alignment.TRAILING));

                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents

        private void txtnilaiakhirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtnilaiakhirActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtnilaiakhirActionPerformed

        private void txtkd3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtkd3ActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtkd3ActionPerformed

        private void txtkd1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtkd1ActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtkd1ActionPerformed

        private void txtidActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtidActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtidActionPerformed

        private void bdataActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bdataActionPerformed
                PopupUtility PNA = new PopupUtility();
                PNA.dr = this;
                PNA.setVisible(true);
                PNA.setResizable(false);
                kosong();
        }// GEN-LAST:event_bdataActionPerformed

        private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel6MouseClicked
                new MetodeSmart().setVisible(true);
                this.dispose(); // TODO add your handling code here:
        }// GEN-LAST:event_jLabel6MouseClicked

        private void brefreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_brefreshActionPerformed
                datatable();
                txtcari.setText("");
                pl = new PlaceHolder(txtcari, "Pencarian data...");
                bsimpan.setEnabled(true);
                bhapus.setEnabled(false);
        }// GEN-LAST:event_brefreshActionPerformed

        private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bsimpanActionPerformed
                String textId, textNama, textTepat, textAkurasi, textJml, textInt, textPenangan, textNilaiAkhir,
                                ranking;

                textId = txtid.getText();
                textNama = txtnm.getText();
                textTepat = txtkd1.getText();
                textAkurasi = txtkd2.getText();
                textJml = txtkd3.getText();
                textInt = txtkd4.getText();
                textPenangan = txtkd5.getText();
                textNilaiAkhir = txtnilaiakhir.getText();
                ranking = txtrank.getText();
                if ((textId.equals("") | (textNama.equals("") | textTepat.equals("") | textAkurasi.equals("")
                                | textJml.equals("") | textInt.equals("") | textPenangan.equals("")
                                | textNilaiAkhir.equals("")
                                | ranking.equals("")))) {
                        JOptionPane.showMessageDialog(null, "Pengisian Data Tidak Boleh Kosong");
                        txtid.requestFocus();
                } else {
                        tambah();
                }
        }// GEN-LAST:event_bsimpanActionPerformed

        private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtcariActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtcariActionPerformed

        private void bdata1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bdata1ActionPerformed
                PopupKriteria PK = new PopupKriteria();
                PK.dr = this; // Set properti dr dengan instance DataRank saat membuat PopupKriteria
                PK.setVisible(true);
        }// GEN-LAST:event_bdata1ActionPerformed

        private void bhitungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bhitungActionPerformed
                try {
                        // Get utility values (should be 0.000 to 1.000)
                        double tepat = Double.parseDouble(txttepat.getText().replace(",", "."));
                        double akurasi = Double.parseDouble(txtakurasi.getText().replace(",", "."));
                        double jml = Double.parseDouble(txtjml.getText().replace(",", "."));
                        double intg = Double.parseDouble(txtint.getText().replace(",", "."));
                        double penangan = Double.parseDouble(txtpenangan.getText().replace(",", "."));

                        // Get weight values (should be 0.35, 0.20, 0.15, 0.20, 0.10)
                        double k1 = Double.parseDouble(txtk1.getText().replace(",", "."));
                        double k2 = Double.parseDouble(txtk2.getText().replace(",", "."));
                        double k3 = Double.parseDouble(txtk3.getText().replace(",", "."));
                        double k4 = Double.parseDouble(txtk4.getText().replace(",", "."));
                        double k5 = Double.parseDouble(txtk5.getText().replace(",", "."));

                        // Debug: Print input values
                        System.out.printf(
                                        "DEBUG DataRank - Utility values: U1=%.3f, U2=%.3f, U3=%.3f, U4=%.3f, U5=%.3f%n",
                                        tepat, akurasi, jml, intg, penangan);
                        System.out.printf(
                                        "DEBUG DataRank - Weight values: W1=%.3f, W2=%.3f, W3=%.3f, W4=%.3f, W5=%.3f%n",
                                        k1, k2, k3, k4, k5);

                        // Calculate final values using SMART formula: Ui * Wi
                        double hasilK1 = tepat * k1;
                        double hasilK2 = akurasi * k2;
                        double hasilK3 = jml * k3;
                        double hasilK4 = intg * k4;
                        double hasilK5 = penangan * k5;

                        // Debug: Print calculated values
                        System.out.printf(
                                        "DEBUG DataRank - Final values: F1=%.5f, F2=%.5f, F3=%.5f, F4=%.5f, F5=%.5f%n",
                                        hasilK1, hasilK2, hasilK3, hasilK4, hasilK5);

                        // Display with 5 decimal places to match SMART precision, using US locale for
                        // dot separator
                        txtkd1.setText(String.format(Locale.US, "%.5f", hasilK1));
                        txtkd2.setText(String.format(Locale.US, "%.5f", hasilK2));
                        txtkd3.setText(String.format(Locale.US, "%.5f", hasilK3));
                        txtkd4.setText(String.format(Locale.US, "%.5f", hasilK4));
                        txtkd5.setText(String.format(Locale.US, "%.5f", hasilK5));

                        double nilaiAkhir = hasilK1 + hasilK2 + hasilK3 + hasilK4 + hasilK5;
                        txtnilaiakhir.setText(String.format(Locale.US, "%.5f", nilaiAkhir));

                        // Debug: Print final total
                        System.out.printf("DEBUG DataRank - Total final score: %.5f%n", nilaiAkhir);

                        // Calculate ranking based on current score compared to all scores in database
                        int ranking = calculateRanking(nilaiAkhir);

                        // Display ranking in txtrank
                        txtrank.setText(String.valueOf(ranking));
                } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Input tidak valid");
                }
        }// GEN-LAST:event_bhitungActionPerformed

        // Method to calculate ranking based on current score
        private int calculateRanking(double currentScore) {
                int ranking = 1;
                try {
                        String sql = "SELECT jumlah_nilai_akhir FROM nilai_akhir WHERE jumlah_nilai_akhir > ?";
                        PreparedStatement stat = conn.prepareStatement(sql);
                        stat.setDouble(1, currentScore);
                        ResultSet rs = stat.executeQuery();

                        while (rs.next()) {
                                ranking++;
                        }
                } catch (SQLException e) {
                        System.out.println("Error calculating ranking: " + e.getMessage());
                }
                return ranking;
        }

        // Method to update all rankings in the database
        private void updateAllRankings() {
                try {
                        // Get all records ordered by score descending
                        String selectSql = "SELECT id_siswa, jumlah_nilai_akhir FROM nilai_akhir ORDER BY jumlah_nilai_akhir DESC";
                        Statement selectStat = conn.createStatement();
                        ResultSet rs = selectStat.executeQuery(selectSql);

                        // Update each record with its new ranking
                        String updateSql = "UPDATE nilai_akhir SET ranking = ? WHERE id_siswa = ?";
                        PreparedStatement updateStat = conn.prepareStatement(updateSql);

                        int rank = 1;
                        while (rs.next()) {
                                updateStat.setInt(1, rank);
                                updateStat.setString(2, rs.getString("id_siswa"));
                                updateStat.executeUpdate();
                                rank++;
                        }

                        System.out.println("All rankings updated successfully");
                } catch (SQLException e) {
                        System.out.println("Error updating rankings: " + e.getMessage());
                }
        }

        private void bcariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bcariActionPerformed
                cari();
        }// GEN-LAST:event_bcariActionPerformed

        private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bhapusActionPerformed
                int ok = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus nilai akhir ini?",
                                "Konfirmasi dialog!",
                                JOptionPane.YES_NO_OPTION);
                if (ok == 0) {
                        int row = tabelrank.getSelectedRow();
                        String cell = tabelrank.getModel().getValueAt(row, 0).toString();
                        String sql = "delete from nilai_akhir where id_siswa = '" + cell + "'";
                        try {
                                PreparedStatement stat = conn.prepareStatement(sql);
                                stat.executeUpdate();

                                // Update all rankings after deleting data
                                updateAllRankings();

                                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                        } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, "Data gagal dihapus" + e);
                        }
                        datatable();
                        kosong();
                        bsimpan.setEnabled(true);
                        bhapus.setEnabled(false);
                }
        }// GEN-LAST:event_bhapusActionPerformed

        private void bbersihkanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bbersihkanActionPerformed
                kosong();
        }// GEN-LAST:event_bbersihkanActionPerformed

        private void tabelrankMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tabelrankMouseClicked
                bsimpan.setEnabled(false);
                bhapus.setEnabled(true);
                int bar = tabelrank.getSelectedRow();
                String a = tabmode.getValueAt(bar, 0).toString();
                String b = tabmode.getValueAt(bar, 1).toString();
                String c = tabmode.getValueAt(bar, 2).toString();
                String d = tabmode.getValueAt(bar, 3).toString();
                String e = tabmode.getValueAt(bar, 4).toString();
                String f = tabmode.getValueAt(bar, 5).toString();
                String g = tabmode.getValueAt(bar, 6).toString();
                String h = tabmode.getValueAt(bar, 7).toString();
                String i = tabmode.getValueAt(bar, 8).toString();
                txtid.setText(a);
                txtnm.setText(b);
                txtkd1.setText(c);
                txtkd2.setText(d);
                txtkd3.setText(e);
                txtkd4.setText(f);
                txtkd5.setText(g);
                txtnilaiakhir.setText(h);
                txtrank.setText(i);
        }// GEN-LAST:event_tabelrankMouseClicked

        private void bcetakActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bcetakActionPerformed
                try {
                        String report = "./src/reports/laporan_nilai_akhir_siswa.jasper";
                        HashMap<String, Object> param = new HashMap<>();
                        // param.put("parameter1", cari.getText());
                        JasperPrint print = JasperFillManager.fillReport(report, param, conn);
                        JasperViewer.viewReport(print, false);
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(rootPane, ex);
                }
        }// GEN-LAST:event_bcetakActionPerformed

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
                // (optional) ">
                /*
                 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
                 * look and feel.
                 * For details see
                 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                                        .getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(DataRank.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(DataRank.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(DataRank.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(DataRank.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                }
                // </editor-fold>
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new DataRank().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton bbersihkan;
        private javax.swing.JButton bcari;
        private javax.swing.JButton bcetak;
        private javax.swing.JButton bdata;
        private javax.swing.JButton bhapus;
        private javax.swing.JButton bhitung;
        private javax.swing.JButton brefresh;
        private javax.swing.JButton bsimpan;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel16;
        private javax.swing.JLabel jLabel17;
        private javax.swing.JLabel jLabel18;
        private javax.swing.JLabel jLabel19;
        private javax.swing.JLabel jLabel20;
        private javax.swing.JLabel jLabel21;
        private javax.swing.JLabel jLabel22;
        private javax.swing.JLabel jLabel23;
        private javax.swing.JLabel jLabel24;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JPanel jPanel7;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JTable tabelrank;
        private javax.swing.JTextField txtakurasi;
        private javax.swing.JTextField txtcari;
        private javax.swing.JTextField txtid;
        private javax.swing.JTextField txtint;
        private javax.swing.JTextField txtjml;
        private javax.swing.JTextField txtk1;
        private javax.swing.JTextField txtk2;
        private javax.swing.JTextField txtk3;
        private javax.swing.JTextField txtk4;
        private javax.swing.JTextField txtk5;
        private javax.swing.JTextField txtkd1;
        private javax.swing.JTextField txtkd2;
        private javax.swing.JTextField txtkd3;
        private javax.swing.JTextField txtkd4;
        private javax.swing.JTextField txtkd5;
        private javax.swing.JTextField txtnilaiakhir;
        private javax.swing.JTextField txtnm;
        private javax.swing.JTextField txtpenangan;
        private javax.swing.JTextField txtrank;
        private javax.swing.JTextField txttepat;
        // End of variables declaration//GEN-END:variables
}
