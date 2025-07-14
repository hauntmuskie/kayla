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
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Elza Kayla
 */
public class DataPenilaian extends javax.swing.JFrame {
    private Connection conn = new DatabaseConnection().connect();
    private DefaultTableModel tabmode;
    public DataSiswa dk = null;
    PlaceHolder pl;
    public String idsiswa, nama, akademik, prestasi, kehadiran, sikap, partisipasi;

    /**
     * Creates new form datapenilaian
     */
    public DataPenilaian() {
        initComponents();

        // Setup combo boxes first
        setupComboBoxes();

        // Then clear fields
        kosong();

        Locale locale = Locale.forLanguageTag("id-ID");
        Locale.setDefault(locale);
        bhapus.setEnabled(false);

        // non editable textfield
        txtid.setEditable(false);
        txtnm.setEditable(false);
        txttepat.setEditable(false);
        txtjml.setEditable(false);

        datatable();
    }

    private void setupComboBoxes() {
        // Setup combo box for Prestasi Non-Akademik
        cmbPrestasi.removeAllItems();
        cmbPrestasi.addItem("Tidak memiliki prestasi");
        cmbPrestasi.addItem("Partisipasi aktif");
        cmbPrestasi.addItem("Juara tingkat sekolah");
        cmbPrestasi.addItem("Juara tingkat provinsi/kota");
        cmbPrestasi.addItem("Juara tingkat nasional");

        // Setup combo box for Sikap/Perilaku
        cmbSikap.removeAllItems();
        cmbSikap.addItem("Sangat kurang");
        cmbSikap.addItem("Kurang");
        cmbSikap.addItem("Cukup");
        cmbSikap.addItem("Baik");
        cmbSikap.addItem("Sangat Baik");

        // Setup combo box for Partisipasi Kegiatan Sekolah
        cmbPartisipasi.removeAllItems();
        cmbPartisipasi.addItem("Tidak aktif");
        cmbPartisipasi.addItem("Kadang-kadang aktif");
        cmbPartisipasi.addItem("Aktif rutin");
        cmbPartisipasi.addItem("Aktif dan memiliki peran");
    }

    private void datatable() {
        Object[] Baris = { "ID Siswa", "Nama Siswa", "Nilai Akademik", "Prestasi Non-Akademik", "Kehadiran",
                "Sikap/Perilaku", "Partisipasi Kegiatan" };
        tabmode = new DefaultTableModel(null, Baris);
        try {
            String sql = "SELECT * FROM penilaian ORDER by id_siswa";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                tabmode.addRow(new Object[] {
                        hasil.getString("id_siswa"),
                        hasil.getString("nama_siswa"),
                        hasil.getString("nilai_akademik"),
                        hasil.getString("prestasi_non_akademik"),
                        hasil.getString("kehadiran"),
                        hasil.getString("sikap_perilaku"),
                        hasil.getString("partisipasi_kegiatan"),
                });
            }
            tabelpenilaian.setModel(tabmode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data gagal dipanggil" + e);
        }
    }

    private void cari() {
        Object[] Baris = { "ID Siswa", "Nama Siswa", "Nilai Akademik", "Prestasi Non-Akademik", "Kehadiran",
                "Sikap/Perilaku", "Partisipasi Kegiatan" };
        tabmode = new DefaultTableModel(null, Baris);
        String cariitem = txtcari.getText();
        try {
            String sql = "SELECT * FROM penilaian WHERE id_siswa='" + cariitem + "' OR nama_siswa='" + cariitem
                    + "' OR nilai_akademik='" + cariitem + "' OR prestasi_non_akademik='" + cariitem
                    + "' OR kehadiran='" + cariitem + "' OR sikap_perilaku='" + cariitem + "' OR partisipasi_kegiatan='"
                    + cariitem + "'";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                tabmode.addRow(new Object[] {
                        hasil.getString("id_siswa"),
                        hasil.getString("nama_siswa"),
                        hasil.getString("nilai_akademik"),
                        hasil.getString("prestasi_non_akademik"),
                        hasil.getString("kehadiran"),
                        hasil.getString("sikap_perilaku"),
                        hasil.getString("partisipasi_kegiatan"),
                });
            }
            tabelpenilaian.setModel(tabmode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data gagal dipanggil" + e);
        }
    }

    private void kosong() {
        txtid.setText("");
        txtnm.setText("");
        txttepat.setText("");

        // Safely reset combo boxes
        if (cmbPrestasi.getItemCount() > 0) {
            cmbPrestasi.setSelectedIndex(0);
        }

        txtjml.setText("");

        if (cmbSikap.getItemCount() > 0) {
            cmbSikap.setSelectedIndex(0);
        }

        if (cmbPartisipasi.getItemCount() > 0) {
            cmbPartisipasi.setSelectedIndex(0);
        }

        txtk1.setText("");
        txtk2.setText("");
        txtk3.setText("");
        txtk4.setText("");
        txtk5.setText("");
        txtcari.setText("");
        pl = new PlaceHolder(txtcari, "Pencarian data...");
    }

    private void tambah() {
        try {
            // Validasi input
            if (txtk1.getText().trim().isEmpty() || txtk2.getText().trim().isEmpty() ||
                    txtk3.getText().trim().isEmpty() || txtk4.getText().trim().isEmpty() ||
                    txtk5.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Harap klik 'Cek Nilai Bobot' terlebih dahulu untuk menghitung skor!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String idSiswa = txtid.getText();
            String namaSiswa = txtnm.getText();

            // Cek apakah data sudah ada di tabel penilaian
            String checkSql = "SELECT COUNT(*) FROM penilaian WHERE id_siswa = ?";
            PreparedStatement checkStat = conn.prepareStatement(checkSql);
            checkStat.setString(1, idSiswa);
            ResultSet checkResult = checkStat.executeQuery();
            checkResult.next();
            boolean exists = checkResult.getInt(1) > 0;

            if (exists) {
                // Update data yang sudah ada
                String updateSql = "UPDATE penilaian SET nama_siswa = ?, nilai_akademik = ?, prestasi_non_akademik = ?, "
                        +
                        "kehadiran = ?, sikap_perilaku = ?, partisipasi_kegiatan = ? WHERE id_siswa = ?";
                PreparedStatement updateStat = conn.prepareStatement(updateSql);
                updateStat.setString(1, namaSiswa);
                updateStat.setInt(2, Integer.parseInt(txtk1.getText())); // nilai_akademik
                updateStat.setInt(3, Integer.parseInt(txtk2.getText())); // prestasi_non_akademik
                updateStat.setInt(4, Integer.parseInt(txtk3.getText())); // kehadiran
                updateStat.setInt(5, Integer.parseInt(txtk4.getText())); // sikap_perilaku
                updateStat.setInt(6, Integer.parseInt(txtk5.getText())); // partisipasi_kegiatan
                updateStat.setString(7, idSiswa);
                updateStat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil diperbarui", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Insert data baru
                String insertSql = "INSERT INTO penilaian (id_siswa, nama_siswa, nilai_akademik, prestasi_non_akademik, kehadiran, sikap_perilaku, partisipasi_kegiatan) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement insertStat = conn.prepareStatement(insertSql);
                insertStat.setString(1, idSiswa);
                insertStat.setString(2, namaSiswa);
                insertStat.setInt(3, Integer.parseInt(txtk1.getText())); // nilai_akademik
                insertStat.setInt(4, Integer.parseInt(txtk2.getText())); // prestasi_non_akademik
                insertStat.setInt(5, Integer.parseInt(txtk3.getText())); // kehadiran
                insertStat.setInt(6, Integer.parseInt(txtk4.getText())); // sikap_perilaku
                insertStat.setInt(7, Integer.parseInt(txtk5.getText())); // partisipasi_kegiatan
                insertStat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            kosong();
            txtid.requestFocus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Nilai skor harus berupa angka! Harap klik 'Cek Nilai Bobot' terlebih dahulu.", "Error Format",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan: " + e.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        datatable();
    }

    public void itemTerpilih() {
        PopupAlternatif PP = new PopupAlternatif();
        PP.dp = this;
        txtid.setText(idsiswa);
        txtnm.setText(nama);
        txttepat.setText(akademik);

        // Set combo box selection based on prestasi value
        if (prestasi != null && !prestasi.isEmpty()) {
            for (int i = 0; i < cmbPrestasi.getItemCount(); i++) {
                String item = (String) cmbPrestasi.getItemAt(i);
                if (item.toLowerCase().contains(prestasi.toLowerCase())) {
                    cmbPrestasi.setSelectedIndex(i);
                    break;
                }
            }
        }

        txtjml.setText(kehadiran);

        // Set combo box selection based on sikap value
        if (sikap != null && !sikap.isEmpty()) {
            for (int i = 0; i < cmbSikap.getItemCount(); i++) {
                String item = (String) cmbSikap.getItemAt(i);
                if (item.toLowerCase().contains(sikap.toLowerCase())) {
                    cmbSikap.setSelectedIndex(i);
                    break;
                }
            }
        }

        // Set combo box selection based on partisipasi value
        if (partisipasi != null && !partisipasi.isEmpty()) {
            for (int i = 0; i < cmbPartisipasi.getItemCount(); i++) {
                String item = (String) cmbPartisipasi.getItemAt(i);
                if (item.toLowerCase().contains(partisipasi.toLowerCase())) {
                    cmbPartisipasi.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtid = new javax.swing.JTextField();
        txtnm = new javax.swing.JTextField();
        bdata = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txttepat = new javax.swing.JTextField();
        cmbPrestasi = new javax.swing.JComboBox();
        txtjml = new javax.swing.JTextField();
        cmbSikap = new javax.swing.JComboBox();
        cmbPartisipasi = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelpenilaian = new javax.swing.JTable();
        bsimpan = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbersih = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtk1 = new javax.swing.JTextField();
        txtk2 = new javax.swing.JTextField();
        txtk3 = new javax.swing.JTextField();
        txtk4 = new javax.swing.JTextField();
        txtk5 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        brefresh = new javax.swing.JButton();
        bcek = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        bcari = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(231, 229, 179));
        jPanel2.setForeground(new java.awt.Color(32, 33, 35));

        jLabel1.setBackground(new java.awt.Color(234, 228, 213));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(138, 120, 78));
        jLabel1.setText("DATA PENILAIAN");

        jLabel6.setBackground(new java.awt.Color(234, 228, 213));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(138, 120, 78));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/back_arrow_14447.png"))); // NOI18N
        jLabel6.setText("Kembali");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(177, 171, 134));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Siswa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 13))); // NOI18N

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
        bdata.setText("Pilih Data Kriteria");
        bdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdataActionPerformed(evt);
            }
        });

        jLabel24.setBackground(new java.awt.Color(0, 0, 0));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("Nama Siswa");

        jLabel25.setBackground(new java.awt.Color(0, 0, 0));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("ID Siswa");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bdata, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(bdata))
        );

        jPanel4.setBackground(new java.awt.Color(177, 171, 134));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Kriteria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Historic", 3, 13))); // NOI18N

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Nilai Akademik");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Prestasi Non-Akademik");

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Kehadiran");

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Sikap/Perilaku");

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Partisipasi Kegiatan Sekolah");

        txttepat.setBackground(new java.awt.Color(234, 228, 213));
        txttepat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttepatActionPerformed(evt);
            }
        });

        cmbPrestasi.setBackground(new java.awt.Color(234, 228, 213));
        cmbPrestasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak memiliki prestasi", "Partisipasi aktif", "Juara tingkat sekolah", "Juara tingkat provinsi/kota", "Juara tingkat nasional" }));

        txtjml.setBackground(new java.awt.Color(234, 228, 213));
        txtjml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjmlActionPerformed(evt);
            }
        });

        cmbSikap.setBackground(new java.awt.Color(234, 228, 213));
        cmbSikap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sangat kurang", "Kurang", "Cukup", "Baik", "Sangat Baik" }));

        cmbPartisipasi.setBackground(new java.awt.Color(234, 228, 213));
        cmbPartisipasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak aktif", "Kadang-kadang aktif", "Aktif rutin", "Aktif dan memiliki peran" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbPrestasi, javax.swing.GroupLayout.Alignment.TRAILING, 0, 150, Short.MAX_VALUE)
                            .addComponent(cmbSikap, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txttepat, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtjml, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPartisipasi, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txttepat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbPrestasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtjml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbSikap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmbPartisipasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tabelpenilaian.setBackground(new java.awt.Color(234, 228, 213));
        tabelpenilaian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabelpenilaian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpenilaianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelpenilaian);

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

        bbersih.setBackground(new java.awt.Color(138, 120, 78));
        bbersih.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bbersih.setForeground(new java.awt.Color(255, 255, 255));
        bbersih.setText("Bersihkan");
        bbersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbersihActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(177, 171, 134));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nilai Bobot", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Historic", 3, 13))); // NOI18N

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("K1");

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("K2");

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("K3");

        jLabel18.setBackground(new java.awt.Color(0, 0, 0));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("K4");

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("K5");

        txtk1.setBackground(new java.awt.Color(234, 228, 213));
        txtk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk1ActionPerformed(evt);
            }
        });

        txtk2.setBackground(new java.awt.Color(234, 228, 213));
        txtk2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk2ActionPerformed(evt);
            }
        });

        txtk3.setBackground(new java.awt.Color(234, 228, 213));
        txtk3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk3ActionPerformed(evt);
            }
        });

        txtk4.setBackground(new java.awt.Color(234, 228, 213));
        txtk4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk4ActionPerformed(evt);
            }
        });

        txtk5.setBackground(new java.awt.Color(234, 228, 213));
        txtk5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk5ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(jLabel26)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtk1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(txtk2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtk3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtk4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtk5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txtk1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtk2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtk3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtk4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtk5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        brefresh.setBackground(new java.awt.Color(138, 120, 78));
        brefresh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        brefresh.setForeground(new java.awt.Color(255, 255, 255));
        brefresh.setText("Refresh");
        brefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brefreshActionPerformed(evt);
            }
        });

        bcek.setBackground(new java.awt.Color(138, 120, 78));
        bcek.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bcek.setForeground(new java.awt.Color(255, 255, 255));
        bcek.setText("Cek Nilai Bobot");
        bcek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcekActionPerformed(evt);
            }
        });

        txtcari.setBackground(new java.awt.Color(234, 228, 213));

        bcari.setBackground(new java.awt.Color(138, 120, 78));
        bcari.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bcari.setForeground(new java.awt.Color(255, 255, 255));
        bcari.setText("Cari");
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(529, 529, 529)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(bsimpan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(bcek)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bbersih, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(brefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(72, 72, 72))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bsimpan)
                            .addComponent(bcek)
                            .addComponent(bhapus)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bcari)
                            .addComponent(bbersih)
                            .addComponent(brefresh))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtidActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtidActionPerformed

    private void bdataActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bdataActionPerformed
        PopupAlternatif PP = new PopupAlternatif();
        PP.dp = this;
        PP.setVisible(true);
        PP.setResizable(false);
        kosong();
    }// GEN-LAST:event_bdataActionPerformed

    private void txttepatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txttepatActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txttepatActionPerformed

    private void txtjmlActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtjmlActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtjmlActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bhapusActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data penilaian ini?", "Konfirmasi dialog!",
                JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            int row = tabelpenilaian.getSelectedRow();
            String cell = tabelpenilaian.getModel().getValueAt(row, 0).toString();
            String sql = "delete from penilaian where id_siswa = '" + cell + "'";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
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

    private void bbersihActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bbersihActionPerformed
        kosong();
    }// GEN-LAST:event_bbersihActionPerformed

    private void txtk1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtk1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtk1ActionPerformed

    private void txtk2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtk2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtk2ActionPerformed

    private void txtk3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtk3ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtk3ActionPerformed

    private void txtk4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtk4ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtk4ActionPerformed

    private void txtk5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtk5ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtk5ActionPerformed

    private void brefreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_brefreshActionPerformed
        datatable();
        txtcari.setText("");
        pl = new PlaceHolder(txtcari, "Pencarian data...");
        bsimpan.setEnabled(true);

        bhapus.setEnabled(false);
    }// GEN-LAST:event_brefreshActionPerformed

    private void bcekActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bcekActionPerformed
        try {
            // Validasi input tidak kosong
            if (txtid.getText().trim().isEmpty() || txtnm.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID Siswa dan Nama Siswa harus diisi terlebih dahulu!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Ambil input dari field
            String akademikStr = txttepat.getText().trim();
            String prestasiStr = (String) cmbPrestasi.getSelectedItem();
            String kehadiranStr = txtjml.getText().trim();
            String sikapStr = (String) cmbSikap.getSelectedItem();
            String partisipasiStr = (String) cmbPartisipasi.getSelectedItem();

            // Validasi field tidak kosong
            if (akademikStr.isEmpty() || prestasiStr == null || kehadiranStr.isEmpty() ||
                    sikapStr == null || partisipasiStr == null) {
                JOptionPane.showMessageDialog(null, "Semua field kriteria harus diisi!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int skorK1 = 0, skorK2 = 0, skorK3 = 0, skorK4 = 0, skorK5 = 0;

            // 2. Konversi ke skor sesuai tabel paper (Tabel 4.2 - 4.6)

            // K1: Nilai Akademik (Tabel 4.2)
            try {
                double akademik = Double.parseDouble(akademikStr);
                if (akademik >= 90 && akademik <= 100)
                    skorK1 = 100;
                else if (akademik >= 80 && akademik < 90)
                    skorK1 = 90;
                else if (akademik >= 70 && akademik < 80)
                    skorK1 = 80;
                else if (akademik >= 60 && akademik < 70)
                    skorK1 = 70;
                else if (akademik < 60)
                    skorK1 = 60;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Nilai Akademik harus berupa angka!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // K2: Prestasi Non-Akademik (Tabel 4.3) - Enhanced categorical matching
            String prestasi = prestasiStr.toLowerCase().trim();
            if (prestasi.contains("juara tingkat nasional") || prestasi.contains("nasional")
                    || prestasi.contains("juara nasional"))
                skorK2 = 100;
            else if (prestasi.contains("juara tingkat provinsi") || prestasi.contains("provinsi")
                    || prestasi.contains("juara provinsi") ||
                    prestasi.contains("juara tingkat kota") || prestasi.contains("kota")
                    || prestasi.contains("juara kota"))
                skorK2 = 90;
            else if (prestasi.contains("juara tingkat sekolah") || prestasi.contains("sekolah"))
                skorK2 = 80;
            else if (prestasi.contains("partisipasi aktif") || prestasi.contains("partisipasi"))
                skorK2 = 70;
            else // default untuk input tidak dikenali
                skorK2 = 60;

            // K3: Kehadiran (Tabel 4.4)
            try {
                double kehadiran = Double.parseDouble(kehadiranStr.replace("%", ""));
                if (kehadiran == 100)
                    skorK3 = 100;
                else if (kehadiran >= 90)
                    skorK3 = 90;
                else if (kehadiran >= 80)
                    skorK3 = 80;
                else if (kehadiran >= 60)
                    skorK3 = 70;
                else
                    skorK3 = 60;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Kehadiran harus berupa angka (dalam persen)!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // K4: Sikap/Perilaku (Tabel 4.5) - Enhanced categorical matching
            String sikap = sikapStr.toLowerCase().trim();
            if (sikap.contains("sangat baik") || sikap.equals("sangat baik"))
                skorK4 = 100;
            else if (sikap.contains("baik") && !sikap.contains("sangat") && !sikap.contains("kurang"))
                skorK4 = 90;
            else if (sikap.contains("cukup"))
                skorK4 = 85;
            else if (sikap.contains("kurang") && !sikap.contains("sangat"))
                skorK4 = 75;
            else if (sikap.contains("sangat kurang"))
                skorK4 = 60;
            else // default untuk input tidak dikenali (anggap baik)
                skorK4 = 85;

            // K5: Partisipasi Kegiatan Sekolah (Tabel 4.6)
            String partisipasi = partisipasiStr.toLowerCase();
            if (partisipasi.contains("aktif dan memiliki peran") || partisipasi.contains("memiliki peran"))
                skorK5 = 100;
            else if (partisipasi.contains("aktif rutin"))
                skorK5 = 90;
            else if (partisipasi.contains("kadang-kadang aktif"))
                skorK5 = 75;
            else if (partisipasi.contains("tidak aktif"))
                skorK5 = 60;
            else // default untuk input tidak dikenali
                skorK5 = 75;

            // Tampilkan skor pada field K1-K5
            txtk1.setText(Integer.toString(skorK1));
            txtk2.setText(Integer.toString(skorK2));
            txtk3.setText(Integer.toString(skorK3));
            txtk4.setText(Integer.toString(skorK4));
            txtk5.setText(Integer.toString(skorK5));

            // Semua penyimpanan ke database dihapus dari sini, hanya hitung dan tampilkan
            // skor saja.

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_bcekActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel6MouseClicked
        new MenuUtama().setVisible(true);
        this.dispose(); // TODO add your handling code here:
    }// GEN-LAST:event_jLabel6MouseClicked

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bsimpanActionPerformed
        String textId, textNama, textTepat, textAkurasi, textJml, textInt, textPenangan;

        textId = txtid.getText();
        textNama = txtnm.getText();
        textTepat = txtk1.getText();
        textAkurasi = txtk2.getText();
        textJml = txtk3.getText();
        textInt = txtk4.getText();
        textPenangan = txtk5.getText();
        if ((textId.equals("") | (textNama.equals("") | textTepat.equals("") | textAkurasi.equals("")
                | textJml.equals("") | textInt.equals("") | textPenangan.equals("")))) {
            JOptionPane.showMessageDialog(null, "Pengisian Data Tidak Boleh Kosong");
            txtid.requestFocus();
        } else {
            tambah();
        }
    }// GEN-LAST:event_bsimpanActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bcariActionPerformed
        cari();
    }// GEN-LAST:event_bcariActionPerformed

    private void tabelpenilaianMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tabelpenilaianMouseClicked
        bsimpan.setEnabled(false);
        bhapus.setEnabled(true);
        int bar = tabelpenilaian.getSelectedRow();
        String a = tabmode.getValueAt(bar, 0).toString();
        String b = tabmode.getValueAt(bar, 1).toString();
        String c = tabmode.getValueAt(bar, 2).toString();
        String d = tabmode.getValueAt(bar, 3).toString();
        String e = tabmode.getValueAt(bar, 4).toString();
        String f = tabmode.getValueAt(bar, 5).toString();
        String g = tabmode.getValueAt(bar, 6).toString();
        txtid.setText(a);
        txtnm.setText(b);
        txtk1.setText(c);
        txtk2.setText(d);
        txtk3.setText(e);
        txtk4.setText(f);
        txtk5.setText(g);
    }// GEN-LAST:event_tabelpenilaianMouseClicked

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DataPenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataPenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataPenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataPenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataPenilaian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbersih;
    private javax.swing.JButton bcari;
    private javax.swing.JButton bcek;
    private javax.swing.JButton bdata;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton brefresh;
    private javax.swing.JButton bsimpan;
    private javax.swing.JComboBox cmbPartisipasi;
    private javax.swing.JComboBox cmbPrestasi;
    private javax.swing.JComboBox cmbSikap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelpenilaian;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtjml;
    private javax.swing.JTextField txtk1;
    private javax.swing.JTextField txtk2;
    private javax.swing.JTextField txtk3;
    private javax.swing.JTextField txtk4;
    private javax.swing.JTextField txtk5;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txttepat;
    // End of variables declaration//GEN-END:variables
}
