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
 * @author raymond
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
        datatable();
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
        txtakurasi.setText("");
        txtjml.setText("");
        txtint.setText("");
        txtpenangan.setText("");
        txtk1.setText("");
        txtk2.setText("");
        txtk3.setText("");
        txtk4.setText("");
        txtk5.setText("");
        txtcari.setText("");
        pl = new PlaceHolder(txtcari, "Pencarian data...");
    }

    private void tambah() {
        String sql = "INSERT INTO penilaian (id_siswa, nama_siswa, nilai_akademik, prestasi_non_akademik, kehadiran, sikap_perilaku, partisipasi_kegiatan) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtid.getText());
            stat.setString(2, txtnm.getText());
            stat.setString(3, txtk1.getText()); // map to nilai_akademik
            stat.setString(4, txtk2.getText()); // map to prestasi_non_akademik
            stat.setString(5, txtk3.getText()); // map to kehadiran
            stat.setString(6, txtk4.getText()); // map to sikap_perilaku
            stat.setString(7, txtk5.getText()); // map to partisipasi_kegiatan
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            kosong();
            txtid.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan" + e);
        }
        datatable();
    }

    public void itemTerpilih() {
        PopupAlternatif PP = new PopupAlternatif();
        PP.dp = this;
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
        txtakurasi = new javax.swing.JTextField();
        txtjml = new javax.swing.JTextField();
        txtint = new javax.swing.JTextField();
        txtpenangan = new javax.swing.JTextField();
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

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));
        jPanel2.setForeground(new java.awt.Color(32, 33, 35));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DATA PENILAIAN");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/back_arrow_14447.png"))); // NOI18N
        jLabel6.setText("Kembali");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Siswa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 13))); // NOI18N

        txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidActionPerformed(evt);
            }
        });

        bdata.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bdata, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Kriteria", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Historic", 3, 13))); // NOI18N

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Nilai Akademik");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Prestasi Non-Akademik");

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Kehadiran");

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Sikap/Perilaku");

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Partisipasi Kegiatan Sekolah");

        txttepat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttepatActionPerformed(evt);
            }
        });

        txtakurasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtakurasiActionPerformed(evt);
            }
        });

        txtjml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjmlActionPerformed(evt);
            }
        });

        txtint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtintActionPerformed(evt);
            }
        });

        txtpenangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpenanganActionPerformed(evt);
            }
        });

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
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txttepat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtakurasi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtjml, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpenangan, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(txtakurasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtjml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtpenangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

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

        bsimpan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bsimpan.setText("Simpan");
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bhapus.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bhapus.setText("Hapus");
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });

        bbersih.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bbersih.setText("Bersihkan");
        bbersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbersihActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nilai Bobot", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Historic", 3, 13))); // NOI18N

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("K1");

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("K2");

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("K3");

        jLabel18.setBackground(new java.awt.Color(0, 0, 0));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("K4");

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("K5");

        txtk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk1ActionPerformed(evt);
            }
        });

        txtk2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk2ActionPerformed(evt);
            }
        });

        txtk3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk3ActionPerformed(evt);
            }
        });

        txtk4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk4ActionPerformed(evt);
            }
        });

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
                .addGap(69, 69, 69)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtk5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtk4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtk3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtk1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtk2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26)))
                .addContainerGap(125, Short.MAX_VALUE))
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

        brefresh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        brefresh.setText("Refresh");
        brefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brefreshActionPerformed(evt);
            }
        });

        bcek.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bcek.setText("Cek Nilai Bobot");
        bcek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcekActionPerformed(evt);
            }
        });

        bcari.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
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
                        .addGap(480, 480, 480)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(bsimpan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(bcek)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bbersih, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(brefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bcari)
                            .addComponent(bbersih)
                            .addComponent(brefresh))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan)
                    .addComponent(bcek)
                    .addComponent(bhapus))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel3.getAccessibleContext().setAccessibleName("Data Siswa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtakurasiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtakurasiActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtakurasiActionPerformed

    private void txtjmlActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtjmlActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtjmlActionPerformed

    private void txtintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtintActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtintActionPerformed

    private void txtpenanganActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtpenanganActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtpenanganActionPerformed

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
        int Tepat = Integer.parseInt(txttepat.getText());
        int Akurasi = Integer.parseInt(txtakurasi.getText());
        int Jumlah = Integer.parseInt(txtjml.getText());
        int Integ = Integer.parseInt(txtint.getText());
        String penangan = txtpenangan.getText();
        int score, score1, score2, score3, score4;

        // Konversi nilai berdasarkan kriteria
        if (Tepat >= 1 && Tepat <= 5) {
            score = 90;
        } else if (Tepat >= 6 && Tepat <= 10) {
            score = 70;
        } else if (Tepat >= 11) {
            score = 60;
        } else {
            score = 100;
        }
        // Konversi nilai berdasarkan kriteria
        if (Akurasi >= 1 && Akurasi <= 5) {
            score1 = 90;
        } else if (Akurasi >= 6 && Akurasi <= 10) {
            score1 = 80;
        } else if (Akurasi >= 11) {
            score1 = 60;
        } else {
            score1 = 100;
        }
        // Jumlah Paket Terkirim
        if (Jumlah >= 1 && Jumlah <= 5) {
            score2 = 85;
        } else if (Jumlah >= 6 && Jumlah <= 10) {
            score2 = 60;
        } else if (Jumlah >= 11) {
            score2 = 50;
        } else {
            score2 = 100;
        }
        // Integritas Paket
        if (Integ >= 1 && Integ <= 5) {
            score3 = 80;
        } else if (Integ >= 6 && Integ <= 10) {
            score3 = 60;
        } else if (Integ >= 11) {
            score3 = 40;
        } else {
            score3 = 100;
        }
        // Kualitas Penanganan Paket
        switch (penangan.toLowerCase()) {
            case "sangat baik":
                score4 = 100;
                break;
            case "baik":
                score4 = 85;
                break;
            case "cukup":
                score4 = 75;
                break;
            case "buruk":
                score4 = 60;
                break;
            case "sangat buruk":
                score4 = 40;
                break;
            default:
                score4 = -1; // Nilai default jika penangan tidak sesuai
        }

        if (score4 != -1) {
            txtk5.setText(Integer.toString(score));
        } else {
            txtk5.setText("Keterangan tidak valid");
        }

        txtk1.setText(Integer.toString(score));
        txtk2.setText(Integer.toString(score1));
        txtk3.setText(Integer.toString(score2));
        txtk4.setText(Integer.toString(score3));
        txtk5.setText(Integer.toString(score4));
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
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txtpenangan;
    private javax.swing.JTextField txttepat;
    // End of variables declaration//GEN-END:variables
}
