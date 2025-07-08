/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import java.sql.*;
import java.sql.Connection;
import com.placeholder.PlaceHolder;

import database.DatabaseConnection;
import utils.AutoIDGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Locale;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author raymond
 */
public class DataSiswa extends javax.swing.JFrame {
    private Connection conn = new DatabaseConnection().connect();
    private DefaultTableModel tabmode;
    PlaceHolder pl;
    public String idsiswa, nama;

    /**
     * Creates new form DataSiswa
     */

    public DataSiswa() {
        initComponents();
        this.setLocationRelativeTo(null);

        // Make ID field non-editable since it's auto-generated
        txtid.setEditable(false);

        aktif();
        kosong();
        Locale locale = Locale.of("id", "ID");
        Locale.setDefault(locale);
        autonumber();

        // Set initial button states
        bsimpan.setEnabled(true);
        bubah.setEnabled(false);
        bhapus.setEnabled(false);
        datatable();

        testDatabaseConnection(); // Test database connection on startup
    }

    protected void autonumber() {
        String nextID = AutoIDGenerator.generateSiswaID();
        txtid.setText(nextID);
    }

    private void datatable() {
        Object[] Baris = { "ID Siswa", "Nama Siswa", "NIS", "Kelas", "Alamat" };
        tabmode = new DefaultTableModel(null, Baris);
        try {
            String sql = "SELECT * FROM siswa ORDER by id_siswa";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                tabmode.addRow(new Object[] {
                        hasil.getString("id_siswa"),
                        hasil.getString("nama_siswa"),
                        hasil.getString("nis"),
                        hasil.getString("kelas"),
                        hasil.getString("alamat")
                });
            }
            tabelkurir.setModel(tabmode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data gagal dipanggil" + e);
        }
    }

    private void cari() {
        Object[] Baris = { "ID Siswa", "Nama Siswa", "NIS", "Kelas", "Alamat" };
        tabmode = new DefaultTableModel(null, Baris);
        String cariitem = txtcari.getText();

        try {
            String sql = "SELECT * FROM siswa where id_siswa LIKE '" + cariitem + "' or nama_siswa LIKE '" + cariitem
                    + "' "
                    + "or nis LIKE '" + cariitem + "' ";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                tabmode.addRow(new Object[] {
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        hasil.getString(4),
                        hasil.getString(5),

                });
            }
            tabelkurir.setModel(tabmode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data gagal dipanggil" + e);
        }
    }

    private void kosong() {
        txtcari.setText("");
        txtnm.setText("");
        txttlp.setText("");
        txtkelas.setText("");
        txtalamat.setText("");

        // Auto-generate new ID for new record
        autonumber();

        // Enable Simpan button for new records
        bsimpan.setEnabled(true);
        bubah.setEnabled(false);
        bhapus.setEnabled(false);

        pl = new PlaceHolder(txtcari, "Pencarian data...");
    }

    private void aktif() {
        txtnm.requestFocus(); // Focus on nama siswa since ID is auto-generated
    }

    private void tambah() {
        String sql = "INSERT INTO siswa (id_siswa, nama_siswa, nis, kelas, alamat) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtid.getText());
            stat.setString(2, txtnm.getText());
            stat.setString(3, txttlp.getText()); // NIS field
            stat.setString(4, txtkelas.getText()); // kelas field
            stat.setString(5, txtalamat.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            kosong(); // This will auto-generate new ID and reset button states
            txtnm.requestFocus(); // Focus on first editable field
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan" + e);
        }
        datatable();
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
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtnm = new javax.swing.JTextField();
        txttlp = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtalamat = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        txtkelas = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelkurir = new javax.swing.JTable();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        brefresh = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        bcari = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        bcetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(51, 255, 0));
        jPanel2.setForeground(new java.awt.Color(32, 33, 35));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DATA SISWA");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setText("ID Siswa");

        jLabel3.setText("Nama Siswa");

        jLabel4.setText("NIS");

        jLabel5.setText("Alamat");

        txtid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtidMouseReleased(evt);
            }
        });
        txtid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtidKeyTyped(evt);
            }
        });

        txtalamat.setColumns(20);
        txtalamat.setRows(5);
        jScrollPane1.setViewportView(txtalamat);

        jLabel7.setText("Kelas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel5)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1)
                                        .addComponent(txttlp)
                                        .addComponent(txtnm)
                                        .addComponent(txtid)
                                        .addComponent(txtkelas))
                                .addContainerGap(79, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txttlp, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7)
                                        .addComponent(txtkelas, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(27, Short.MAX_VALUE)));

        tabelkurir.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
                }));
        tabelkurir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelkurirMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelkurir);

        bsimpan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bsimpan.setForeground(new java.awt.Color(0, 51, 51));
        bsimpan.setText("Simpan");
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bubah.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bubah.setForeground(new java.awt.Color(0, 51, 51));
        bubah.setText("Ubah");
        bubah.setPreferredSize(new java.awt.Dimension(79, 27));
        bubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubahActionPerformed(evt);
            }
        });

        bhapus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bhapus.setForeground(new java.awt.Color(0, 51, 51));
        bhapus.setText("Hapus");
        bhapus.setPreferredSize(new java.awt.Dimension(79, 27));
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });

        bbatal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bbatal.setForeground(new java.awt.Color(0, 51, 51));
        bbatal.setText("Bersihkan");
        bbatal.setPreferredSize(new java.awt.Dimension(79, 27));
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        brefresh.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        brefresh.setForeground(new java.awt.Color(0, 51, 51));
        brefresh.setText("Refresh");
        brefresh.setBorder(null);
        brefresh.setPreferredSize(new java.awt.Dimension(79, 27));
        brefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brefreshActionPerformed(evt);
            }
        });

        bcari.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        bcari.setForeground(new java.awt.Color(0, 51, 51));
        bcari.setText("Cari");
        bcari.setPreferredSize(new java.awt.Dimension(79, 27));
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });

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

        bcetak.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bcetak.setForeground(new java.awt.Color(0, 51, 51));
        bcetak.setText("Cetak Laporan Siswa");
        bcetak.setPreferredSize(new java.awt.Dimension(79, 27));
        bcetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcetakActionPerformed(evt);
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
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                false)
                                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(bsimpan,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                                .addComponent(bubah,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        74,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(bhapus,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(bcetak,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                172, Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(bbatal,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 98,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(38, 38, 38)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(brefresh,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 74,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtcari,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 334,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(bcari,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 76,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jScrollPane2))
                                                .addContainerGap(15, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 159,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)))));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel2Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(brefresh,
                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel2Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(txtcari,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(bcari,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                        Short.MAX_VALUE))
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(bsimpan)
                                        .addGroup(jPanel2Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bcariActionPerformed
        cari(); // TODO add your handling code here:
    }// GEN-LAST:event_bcariActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel6MouseClicked
        new MenuUtama().setVisible(true);
        this.dispose(); // TODO add your handling code here:
    }// GEN-LAST:event_jLabel6MouseClicked

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bsimpanActionPerformed
        String textId, textNama, textTlp, textKelas, textAlamat;

        textId = txtid.getText();
        textNama = txtnm.getText();
        textTlp = txttlp.getText();
        textKelas = txtkelas.getText();
        textAlamat = txtalamat.getText();
        if ((textId.equals("")
                | (textNama.equals("") | textTlp.equals("") | textKelas.equals("") | textAlamat.equals("")))) {
            JOptionPane.showMessageDialog(null, "Pengisian Data Tidak Boleh Kosong");
            txtnm.requestFocus(); // Focus on first editable field
        } else {
            tambah(); // tambah() already handles ID generation and cleanup
        }
    }// GEN-LAST:event_bsimpanActionPerformed

    private void brefreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_brefreshActionPerformed
        // TODO add your handling code here:
        datatable();
        kosong(); // This will auto-generate ID and reset button states
        txtcari.setText("");
        pl = new PlaceHolder(txtcari, "Pencarian data...");
    }// GEN-LAST:event_brefreshActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bhapusActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data siswa ini?", "Konfirmasi dialog!",
                JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            int row = tabelkurir.getSelectedRow();
            String cell = tabelkurir.getModel().getValueAt(row, 0).toString();
            String sql = "delete from siswa where id_siswa = '" + cell + "'";
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
        } // GEN-LAST:event_bhapusActionPerformed
    }

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bubahActionPerformed
        try {
            String sql = "UPDATE siswa SET nama_siswa = ?, nis = ?, kelas = ?, alamat = ?"
                    + "WHERE id_siswa = '" + txtid.getText() + "' ";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtnm.getText());
            stat.setString(2, txttlp.getText());
            stat.setString(3, txtkelas.getText());
            stat.setString(4, txtalamat.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "data berhasil diubah");
            kosong(); // This will auto-generate new ID and reset button states
            txtnm.requestFocus(); // Focus on first editable field
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "data gagal diubah " + e);
        }
        datatable();
    }// GEN-LAST:event_bubahActionPerformed

    private void tabelkurirMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tabelkurirMouseClicked
        bsimpan.setEnabled(false);
        bubah.setEnabled(true);
        bhapus.setEnabled(true);
        int bar = tabelkurir.getSelectedRow();
        String a = tabmode.getValueAt(bar, 0).toString();
        String b = tabmode.getValueAt(bar, 1).toString();
        String c = tabmode.getValueAt(bar, 2).toString();
        String d = tabmode.getValueAt(bar, 3).toString();
        String e = tabmode.getValueAt(bar, 4).toString();
        txtid.setText(a);
        txtnm.setText(b);
        txttlp.setText(c);
        txtkelas.setText(d);
        txtalamat.setText(e);
    }// GEN-LAST:event_tabelkurirMouseClicked

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bbatalActionPerformed
        kosong(); // TODO add your handling code here:
    }// GEN-LAST:event_bbatalActionPerformed

    private void txtidKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtidKeyTyped
        // ID field is auto-generated and not editable, so prevent any input
        evt.consume();
    }// GEN-LAST:event_txtidKeyTyped

    private void txtidMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_txtidMouseReleased
        // ID field is auto-generated, redirect focus to first editable field
        txtnm.requestFocus();
    }// GEN-LAST:event_txtidMouseReleased

    private void bcetakActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bcetakActionPerformed
        try {
            // Always refresh database connection to ensure fresh data
            conn = new DatabaseConnection().connect();
            if (conn == null) {
                JOptionPane.showMessageDialog(rootPane, "Gagal terhubung ke database!");
                return;
            }

            // Use the new student report
            String reportJRXML = "./src/reports/laporansiswa_new.jrxml";
            String reportJasper = "./src/reports/laporansiswa_new.jasper";

            // Always compile fresh from JRXML to ensure latest report template
            java.io.File jrxmlFile = new java.io.File(reportJRXML);
            if (jrxmlFile.exists()) {
                try {
                    // Always compile JRXML to JASPER for fresh compilation
                    net.sf.jasperreports.engine.JasperCompileManager.compileReportToFile(reportJRXML, reportJasper);
                    System.out.println("Report compiled fresh from JRXML");
                } catch (Exception compileEx) {
                    JOptionPane.showMessageDialog(rootPane, 
                        "Error kompilasi laporan: " + compileEx.getMessage() + 
                        "\nPastikan file JRXML tidak memiliki error UUID atau format XML.", 
                        "Compilation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    compileEx.printStackTrace();
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "File laporan tidak ditemukan: " + reportJRXML);
                return;
            }

            // Refresh data table to ensure we're working with latest data
            datatable();

            HashMap<String, Object> param = new HashMap<>();
            // You can add parameters here if needed
            // param.put("parameter1", someValue);

            // Fill report with fresh data from database
            JasperPrint print = JasperFillManager.fillReport(reportJasper, param, conn);
            JasperViewer.viewReport(print, false);
            
            System.out.println("Report generated successfully with fresh data");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Error membuat laporan: " + ex.getMessage());
            ex.printStackTrace(); // This will help debug the exact error
        }
    }// GEN-LAST:event_bcetakActionPerformed

    private void testDatabaseConnection() {
        try {
            Connection testConn = new DatabaseConnection().connect();
            if (testConn != null) {
                // Test if siswa table exists
                String testQuery = "SELECT COUNT(*) FROM siswa";
                Statement stmt = testConn.createStatement();
                ResultSet rs = stmt.executeQuery(testQuery);
                if (rs.next()) {
                    System.out.println(
                            "Database connection successful. Found " + rs.getInt(1) + " students in database.");
                }
                rs.close();
                stmt.close();
                testConn.close();
            } else {
                System.err.println("Database connection failed!");
                JOptionPane.showMessageDialog(this,
                        "Peringatan: Tidak dapat terhubung ke database!\nPastikan MySQL berjalan dan database 'penilaian_siswa_berprestasi' tersedia.",
                        "Database Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Database test failed: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Peringatan: Error database: " + e.getMessage(), "Database Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

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
            java.util.logging.Logger.getLogger(DataSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataSiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bcari;
    private javax.swing.JButton bcetak;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton brefresh;
    private javax.swing.JButton bsimpan;
    private javax.swing.JButton bubah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelkurir;
    private javax.swing.JTextArea txtalamat;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtkelas;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txttlp;
    // End of variables declaration//GEN-END:variables
}
