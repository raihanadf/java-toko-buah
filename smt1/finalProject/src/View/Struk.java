/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author raihan
 */
public final class Struk extends javax.swing.JFrame {
	Connection conn;
	ResultSet rs = null;
	PreparedStatement pst = null;

	/**
	 * Creates new form Struk
	 * @throws java.sql.SQLException
	 * @throws java.lang.ClassNotFoundException
	 */
	public Struk() throws SQLException, ClassNotFoundException {
		this.conn = Koneksi.getKoneksi();
		initComponents();
		initStruk();
	}

	/**
	 *
	 * @param id
	 * @param idKasir
	 * @param bayar
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Struk(String id, String idKasir, int bayar) throws SQLException, ClassNotFoundException {
		this.conn = Koneksi.getKoneksi();
		initComponents();
		initStruk(id, idKasir, bayar);
	}

	public void initStruk(){
		System.out.println("bro");
	}
	public void initStruk(String id, String idKasir, int bayar){
		String sql = "select * from tb_transaksi where no_faktur = " + id;
		try{
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
		while(rs.next()){
			jTextArea1.setText("ID Transaksi : " + rs.getString(1) + "\t\t\t\t\tTanggal: " +rs.getString(3) + "\n");
		}
		sql = "select nama from tb_user where id = " + idKasir;
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
		while(rs.next()){
			jTextArea1.setText(jTextArea1.getText() + "\t\t\t\t\t\t\tKasir : " + rs.getString(1));
		}
			jTextArea1.setText(jTextArea1.getText() + "\n\n");
			jTextArea1.setText(jTextArea1.getText() + "---------------------------------------------------------------------------------\n");
			jTextArea1.setText(jTextArea1.getText() + "ID Item\t\t");
			jTextArea1.setText(jTextArea1.getText() + "Nama\t\t");
			jTextArea1.setText(jTextArea1.getText() + "Jumlah\t\t\t");
			jTextArea1.setText(jTextArea1.getText() + "Sub Total");
			jTextArea1.setText(jTextArea1.getText() + "\n");
			jTextArea1.setText(jTextArea1.getText() + "-------\t\t");
			jTextArea1.setText(jTextArea1.getText() + "----\t\t");
			jTextArea1.setText(jTextArea1.getText() + "------\t\t\t");
			jTextArea1.setText(jTextArea1.getText() + "---------");
			jTextArea1.setText(jTextArea1.getText() + "\n");
		sql = "select b.id, substring(b.nama,1,7), dt.qty, sum(dt.qty * b.harga), u.nama from tb_detail_transaksi as dt join tb_transaksi as t on dt.id_transaksi = t.no_faktur join tb_buah as b on dt.id_buah = b.id join tb_pelanggan as p on t.id_pelanggan = p.id join tb_user as u on dt.id_user = u.id where t.no_faktur = " + id + " group by dt.id order by t.tanggal desc";
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
		while(rs.next()){
			jTextArea1.setText(jTextArea1.getText() + rs.getString(1) + "\t\t");
			jTextArea1.setText(jTextArea1.getText() + rs.getString(2) + "\t\t");
			jTextArea1.setText(jTextArea1.getText() + rs.getString(3) + "\t\t\t");
			jTextArea1.setText(jTextArea1.getText() + rs.getString(4));
			jTextArea1.setText(jTextArea1.getText() + "\n");
		}
		sql = "select sum(dt.qty * b.harga) from tb_detail_transaksi as dt join tb_transaksi as t on dt.id_transaksi = t.no_faktur join tb_buah as b on dt.id_buah = b.id join tb_pelanggan as p on t.id_pelanggan = p.id join tb_user as u on dt.id_user = u.id where t.no_faktur = " + id;
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
		jTextArea1.setText(jTextArea1.getText() + "\n");
		jTextArea1.setText(jTextArea1.getText() + "---------------------------------------------------------------------------------");
		while(rs.next()){
			jTextArea1.setText(jTextArea1.getText() + "\n");
			jTextArea1.setText(jTextArea1.getText() + "\n");
			Locale locale = new Locale("id", "ID");
			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
			jTextArea1.setText(jTextArea1.getText() + "\t\t\t\t\t\t\tTotal : " + currencyFormatter.format(Integer.parseInt(rs.getString(1))) + "\n");
			jTextArea1.setText(jTextArea1.getText() + "\t\t\t\t\t\t\tBayar : " + currencyFormatter.format(bayar) + "\n");
			jTextArea1.setText(jTextArea1.getText() + "\t\t\t\t\t\t\tKembalian : " + currencyFormatter.format(bayar - Integer.parseInt(rs.getString(1))) + "\n");
		}
		}catch(SQLException ex){
			System.out.println("hmm gagal ya gan");
		}
	}
	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                jTextArea1 = new javax.swing.JTextArea();
                jLabel9 = new javax.swing.JLabel();
                jLabel1 = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jPanel1.setBackground(new java.awt.Color(216, 233, 168));
                jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(25, 26, 25), 2, true));

                jScrollPane1.setBorder(null);

                jTextArea1.setEditable(false);
                jTextArea1.setBackground(new java.awt.Color(216, 233, 168));
                jTextArea1.setColumns(20);
                jTextArea1.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
                jTextArea1.setForeground(new java.awt.Color(25, 26, 25));
                jTextArea1.setRows(5);
                jTextArea1.setBorder(null);
                jTextArea1.setFocusable(false);
                jTextArea1.setSelectionColor(new java.awt.Color(216, 233, 168));
                jScrollPane1.setViewportView(jTextArea1);

                jLabel9.setBackground(new java.awt.Color(255, 255, 255));
                jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
                jLabel9.setForeground(new java.awt.Color(25, 26, 25));
                jLabel9.setText("x");
                jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel9MouseClicked(evt);
                        }
                });

                jLabel1.setBackground(new java.awt.Color(25, 26, 25));
                jLabel1.setFont(new java.awt.Font("Cascadia Mono", 1, 24)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(25, 26, 25));
                jLabel1.setText("Struk Towkoh Vuahh");

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(181, 181, 181)
                                                .addComponent(jLabel1))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(26, Short.MAX_VALUE))
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(68, 68, 68)
                                .addComponent(jLabel1)
                                .addGap(40, 40, 40)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(47, Short.MAX_VALUE))
                );

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
                int dialogbtn = JOptionPane.YES_NO_OPTION;
                int dialogresult = JOptionPane.showConfirmDialog(this, "you sure bout that mayneeee?", "Warning", dialogbtn);
                if (dialogresult == 0){
			this.setVisible(false);
		}
        }//GEN-LAST:event_jLabel9MouseClicked

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Struk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Struk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Struk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Struk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(() -> {
			try {
				new Struk().setVisible(true);
			} catch (SQLException | ClassNotFoundException ex) {
				Logger.getLogger(Struk.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTextArea jTextArea1;
        // End of variables declaration//GEN-END:variables
}
