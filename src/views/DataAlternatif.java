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
public class DataAlternatif extends javax.swing.JFrame {

    private Connection conn = new DatabaseConnection().connect();
    private DefaultTableModel tabmode;
    PlaceHolder pl;
    public String idsiswa, nama;

    /**
     * Creates new form dataalternatif
     */
    public DataAlternatif() {
        initComponents();

        // Setup combo boxes first
        setupComboBoxes();

        aktif();
        kosong();
        Locale locale = Locale.forLanguageTag("id-ID");
        Locale.setDefault(locale);
        bubah.setEnabled(false);
        bhapus.setEnabled(false);

        // non editable textfield
        txtid.setEditable(false);
        txtnm.setEditable(false);
        datatable();
    }

    private void datatable() {
        Object[] Baris = {"ID Siswa", "Nama Siswa", "Nilai Akademik", "Prestasi Non-Akademik", "Kehadiran",
            "Sikap/Perilaku", "Partisipasi Kegiatan"};
        tabmode = new DefaultTableModel(null, Baris);

        try {
            String sql = "SELECT * FROM alternatif ORDER by id_siswa";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                tabmode.addRow(new Object[]{
                    hasil.getString("id_siswa"),
                    hasil.getString("nama_siswa"),
                    hasil.getString("nilai_akademik"),
                    hasil.getString("prestasi_non_akademik"),
                    hasil.getString("kehadiran"),
                    hasil.getString("sikap_perilaku"),
                    hasil.getString("partisipasi_kegiatan"),});
            }
            tabelalternatif.setModel(tabmode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data gagal dipanggil" + e);
        }
    }

    private void cari() {
        Object[] Baris = {"ID Siswa", "Nama Siswa", "Nilai Akademik", "Prestasi Non-Akademik", "Kehadiran",
            "Sikap/Perilaku", "Partisipasi Kegiatan"};
        tabmode = new DefaultTableModel(null, Baris);
        String cariitem = txtcari.getText();

        try {
            String sql = "SELECT * FROM alternatif WHERE id_siswa='" + cariitem + "' OR nama_siswa='" + cariitem
                    + "' OR nilai_akademik='" + cariitem + "' OR prestasi_non_akademik='" + cariitem
                    + "' OR kehadiran='" + cariitem + "' OR sikap_perilaku='" + cariitem + "' OR partisipasi_kegiatan='"
                    + cariitem + "'";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                tabmode.addRow(new Object[]{
                    hasil.getString("id_siswa"),
                    hasil.getString("nama_siswa"),
                    hasil.getString("nilai_akademik"),
                    hasil.getString("prestasi_non_akademik"),
                    hasil.getString("kehadiran"),
                    hasil.getString("sikap_perilaku"),
                    hasil.getString("partisipasi_kegiatan"),});
            }
            tabelalternatif.setModel(tabmode);
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

        txtcari.setText("");
        pl = new PlaceHolder(txtcari, "Pencarian data...");
    }

    private void aktif() {
        txttepat.requestFocus();
    }

    private void tambah() {
        String sql = "INSERT INTO alternatif (id_siswa, nama_siswa, nilai_akademik, prestasi_non_akademik, kehadiran, sikap_perilaku, partisipasi_kegiatan) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtid.getText());
            stat.setString(2, txtnm.getText());
            stat.setString(3, txttepat.getText()); // map to nilai_akademik
            stat.setString(4, (String) cmbPrestasi.getSelectedItem()); // map to prestasi_non_akademik
            stat.setString(5, txtjml.getText()); // map to kehadiran
            stat.setString(6, (String) cmbSikap.getSelectedItem()); // map to sikap_perilaku
            stat.setString(7, (String) cmbPartisipasi.getSelectedItem()); // map to partisipasi_kegiatan
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            kosong();
            txtid.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan" + e);
        }
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

    public void itemTerpilih() {
        PopupSiswa PA = new PopupSiswa();
        PA.da = this;
        txtid.setText(idsiswa);
        txtnm.setText(nama);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtnm = new javax.swing.JTextField();
        bdata = new javax.swing.JButton();
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
        tabelalternatif = new javax.swing.JTable();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbersih = new javax.swing.JButton();
        brefresh = new javax.swing.JButton();
        bcari = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        cetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(231, 229, 179));
        jPanel2.setForeground(new java.awt.Color(32, 33, 35));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(138, 120, 78));
        jLabel1.setText("DATA ALTERNATIF");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("ID Siswa");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Nama Siswa");

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
        bdata.setText("Pilih Data Siswa");
        bdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bdata, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(bdata)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(177, 171, 134));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Kriteria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Historic", 3, 13))); // NOI18N

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Nilai Akademik (K1)");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Prestasi Non-Akademik (K2)");

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Kehadiran (K3)");

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Sikap/Perilaku (K4)");

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Partisipasi Kegiatan Sekolah (K5)");

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
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(118, 118, 118)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbSikap, 0, 150, Short.MAX_VALUE)
                            .addComponent(cmbPrestasi, 0, 150, Short.MAX_VALUE)
                            .addComponent(cmbPartisipasi, 0, 150, Short.MAX_VALUE)
                            .addComponent(txttepat)
                            .addComponent(txtjml)))
                    .addComponent(jLabel4))
                .addContainerGap(32, Short.MAX_VALUE))
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

        tabelalternatif.setBackground(new java.awt.Color(234, 228, 213));
        tabelalternatif.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelalternatif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelalternatifMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelalternatif);

        bsimpan.setBackground(new java.awt.Color(138, 120, 78));
        bsimpan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bsimpan.setForeground(new java.awt.Color(255, 255, 255));
        bsimpan.setText("Simpan");
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bubah.setBackground(new java.awt.Color(138, 120, 78));
        bubah.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bubah.setForeground(new java.awt.Color(255, 255, 255));
        bubah.setText("Ubah");
        bubah.setMaximumSize(new java.awt.Dimension(79, 27));
        bubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubahActionPerformed(evt);
            }
        });

        bhapus.setBackground(new java.awt.Color(255, 0, 0));
        bhapus.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bhapus.setForeground(new java.awt.Color(255, 255, 255));
        bhapus.setText("Hapus");
        bhapus.setMaximumSize(new java.awt.Dimension(79, 27));
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

        brefresh.setBackground(new java.awt.Color(138, 120, 78));
        brefresh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        brefresh.setForeground(new java.awt.Color(255, 255, 255));
        brefresh.setText("Refresh");
        brefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brefreshActionPerformed(evt);
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

        txtcari.setBackground(new java.awt.Color(234, 228, 213));

        cetak.setBackground(new java.awt.Color(138, 120, 78));
        cetak.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        cetak.setForeground(new java.awt.Color(255, 255, 255));
        cetak.setText("Cetak");
        cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(brefresh, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                    .addComponent(bsimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(bubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(bbersih, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cetak, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(411, 411, 411)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(73, 73, 73))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bsimpan)
                            .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bbersih)
                            .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(brefresh)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bcari)
                            .addComponent(cetak))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakActionPerformed
        // TODO add your handling code here:
        try {
            String report = "./src/reports/laporan_data_alternatif.jasper";
            HashMap<String, Object> param = new HashMap<>();
            // param.put("parameter1", cari.getText());
            JasperPrint print = JasperFillManager.fillReport(report, param, conn);
            JasperViewer.viewReport(print, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_cetakActionPerformed

    private void txtjmlActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtjmlActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtjmlActionPerformed

    private void txttepatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txttepatActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txttepatActionPerformed

    private void bdataActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bdataActionPerformed
        PopupSiswa PA = new PopupSiswa();
        PA.da = this;
        PA.setVisible(true);
        PA.setResizable(false);
    }// GEN-LAST:event_bdataActionPerformed

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtidActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtidActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bsimpanActionPerformed
        String textId, textNama, textTepat, textJml;

        textId = txtid.getText();
        textNama = txtnm.getText();
        textTepat = txttepat.getText();
        textJml = txtjml.getText();

        if ((textId.equals("") | (textNama.equals("") | textTepat.equals("") | textJml.equals("")
                | cmbPrestasi.getSelectedItem() == null | cmbSikap.getSelectedItem() == null
                | cmbPartisipasi.getSelectedItem() == null))) {
            JOptionPane.showMessageDialog(null, "Pengisian Data Tidak Boleh Kosong");
            txtid.requestFocus();
        } else {
            tambah();
        }
    }// GEN-LAST:event_bsimpanActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bubahActionPerformed
        try {
            String sql = "UPDATE alternatif SET nama_siswa = ?, nilai_akademik = ?, prestasi_non_akademik = ?, kehadiran = ?, sikap_perilaku = ?, partisipasi_kegiatan = ?"
                    + "WHERE id_siswa = '" + txtid.getText() + "' ";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtnm.getText());
            stat.setString(2, txttepat.getText());
            stat.setString(3, (String) cmbPrestasi.getSelectedItem());
            stat.setString(4, txtjml.getText());
            stat.setString(5, (String) cmbSikap.getSelectedItem());
            stat.setString(6, (String) cmbPartisipasi.getSelectedItem());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "data berhasil diubah");
            kosong();
            txtid.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "data gagal diubah " + e);
        }
        datatable();
    }// GEN-LAST:event_bubahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bhapusActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data alternatif ini?", "Konfirmasi dialog!",
                JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            int row = tabelalternatif.getSelectedRow();
            String cell = tabelalternatif.getModel().getValueAt(row, 0).toString();
            String sql = "delete from alternatif where id_siswa = '" + cell + "'";
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
            bubah.setEnabled(false);
            bhapus.setEnabled(false);
        }
    }// GEN-LAST:event_bhapusActionPerformed

    private void bbersihActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bbersihActionPerformed
        kosong();
    }// GEN-LAST:event_bbersihActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel6MouseClicked
        new MenuUtama().setVisible(true);
        this.dispose(); // TODO add your handling code here:
    }// GEN-LAST:event_jLabel6MouseClicked

    private void brefreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_brefreshActionPerformed
        datatable();
        txtcari.setText("");
        pl = new PlaceHolder(txtcari, "Pencarian data...");
        bsimpan.setEnabled(true);
        bubah.setEnabled(false);
        bhapus.setEnabled(false);
    }// GEN-LAST:event_brefreshActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bcariActionPerformed
        cari();
    }// GEN-LAST:event_bcariActionPerformed

    private void tabelalternatifMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tabelalternatifMouseClicked
        bsimpan.setEnabled(false);
        bubah.setEnabled(true);
        bhapus.setEnabled(true);
        int bar = tabelalternatif.getSelectedRow();
        String a = tabmode.getValueAt(bar, 0).toString();
        String b = tabmode.getValueAt(bar, 1).toString();
        String c = tabmode.getValueAt(bar, 2).toString();
        String d = tabmode.getValueAt(bar, 3).toString();
        String e = tabmode.getValueAt(bar, 4).toString();
        String f = tabmode.getValueAt(bar, 5).toString();
        String g = tabmode.getValueAt(bar, 6).toString();
        txtid.setText(a);
        txtnm.setText(b);
        txttepat.setText(c);

        // Set combo box selection based on prestasi value
        if (d != null && !d.isEmpty()) {
            for (int i = 0; i < cmbPrestasi.getItemCount(); i++) {
                String item = (String) cmbPrestasi.getItemAt(i);
                if (item.toLowerCase().contains(d.toLowerCase())) {
                    cmbPrestasi.setSelectedIndex(i);
                    break;
                }
            }
        }

        txtjml.setText(e);

        // Set combo box selection based on sikap value
        if (f != null && !f.isEmpty()) {
            for (int i = 0; i < cmbSikap.getItemCount(); i++) {
                String item = (String) cmbSikap.getItemAt(i);
                if (item.toLowerCase().contains(f.toLowerCase())) {
                    cmbSikap.setSelectedIndex(i);
                    break;
                }
            }
        }

        // Set combo box selection based on partisipasi value
        if (g != null && !g.isEmpty()) {
            for (int i = 0; i < cmbPartisipasi.getItemCount(); i++) {
                String item = (String) cmbPartisipasi.getItemAt(i);
                if (item.toLowerCase().contains(g.toLowerCase())) {
                    cmbPartisipasi.setSelectedIndex(i);
                    break;
                }
            }
        }
    }// GEN-LAST:event_tabelalternatifMouseClicked

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
            java.util.logging.Logger.getLogger(DataAlternatif.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataAlternatif.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataAlternatif.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataAlternatif.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataAlternatif().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbersih;
    private javax.swing.JButton bcari;
    private javax.swing.JButton bdata;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton brefresh;
    private javax.swing.JButton bsimpan;
    private javax.swing.JButton bubah;
    private javax.swing.JButton cetak;
    private javax.swing.JComboBox cmbPartisipasi;
    private javax.swing.JComboBox cmbPrestasi;
    private javax.swing.JComboBox cmbSikap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelalternatif;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtjml;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txttepat;
    // End of variables declaration//GEN-END:variables
}
