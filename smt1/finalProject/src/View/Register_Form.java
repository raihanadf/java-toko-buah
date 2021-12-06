/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.sql.*;
import javax.swing.JOptionPane;
import Controller.Koneksi;
import java.awt.HeadlessException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raihan
 */
public class Register_Form extends javax.swing.JFrame {
	Connection conn;
	ResultSet rs = null;
	PreparedStatement pst = null;

    /**
     * Creates new form Register_Form
	 * @throws java.sql.SQLException
	 * @throws java.lang.ClassNotFoundException
     */
    public Register_Form() throws SQLException, ClassNotFoundException {
	this.conn=Koneksi.getKoneksi();
        initComponents();
        hide_pass.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                txt_username = new javax.swing.JTextField();
                txt_email = new javax.swing.JTextField();
                txt_password = new javax.swing.JPasswordField();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jSeparator1 = new javax.swing.JSeparator();
                jSeparator2 = new javax.swing.JSeparator();
                jSeparator3 = new javax.swing.JSeparator();
                jPanel2 = new javax.swing.JPanel();
                signup = new javax.swing.JLabel();
                jPanel4 = new javax.swing.JPanel();
                signin = new javax.swing.JLabel();
                hide_pass = new javax.swing.JLabel();
                show_pass = new javax.swing.JLabel();
                jSeparator4 = new javax.swing.JSeparator();
                txt_name = new javax.swing.JTextField();
                jLabel5 = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jPanel1.setBackground(new java.awt.Color(30, 81, 40));
                jPanel1.setToolTipText("");
                jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(255, 255, 255));
                jLabel1.setText("REGISTER");
                jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 130, 40));

                txt_username.setBackground(new java.awt.Color(30, 81, 40));
                txt_username.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                txt_username.setForeground(new java.awt.Color(255, 255, 255));
                txt_username.setToolTipText("");
                txt_username.setBorder(null);
                txt_username.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusGained(java.awt.event.FocusEvent evt) {
                                txt_usernameFocusGained(evt);
                        }
                });
                txt_username.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txt_usernameActionPerformed(evt);
                        }
                });
                jPanel1.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 220, 30));

                txt_email.setBackground(new java.awt.Color(30, 81, 40));
                txt_email.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                txt_email.setForeground(new java.awt.Color(255, 255, 255));
                txt_email.setToolTipText("");
                txt_email.setBorder(null);
                txt_email.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusGained(java.awt.event.FocusEvent evt) {
                                txt_emailFocusGained(evt);
                        }
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                txt_emailFocusLost(evt);
                        }
                });
                txt_email.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txt_emailActionPerformed(evt);
                        }
                });
                jPanel1.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 220, 30));

                txt_password.setBackground(new java.awt.Color(30, 81, 40));
                txt_password.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                txt_password.setForeground(new java.awt.Color(255, 255, 255));
                txt_password.setToolTipText("");
                txt_password.setBorder(null);
                txt_password.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusGained(java.awt.event.FocusEvent evt) {
                                txt_passwordFocusGained(evt);
                        }
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                txt_passwordFocusLost(evt);
                        }
                });
                txt_password.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txt_passwordActionPerformed(evt);
                        }
                });
                jPanel1.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 220, 30));

                jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-email-open-20.png"))); // NOI18N
                jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 30, 30));

                jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-name-tag-20.png"))); // NOI18N
                jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 20, 30));

                jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-lock-20.png"))); // NOI18N
                jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, 30));

                jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
                jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
                jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 220, 20));

                jSeparator2.setBackground(new java.awt.Color(255, 255, 255));
                jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
                jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 220, 20));

                jSeparator3.setBackground(new java.awt.Color(255, 255, 255));
                jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
                jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 220, 10));

                jPanel2.setBackground(new java.awt.Color(30, 81, 40));
                jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

                signup.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                signup.setForeground(new java.awt.Color(255, 255, 255));
                signup.setText("    Sign Up");
                signup.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                signupMouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(signup, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                );
                jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(signup, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                );

                jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 90, 30));

                jPanel4.setBackground(new java.awt.Color(30, 81, 40));
                jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

                signin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                signin.setForeground(new java.awt.Color(255, 255, 255));
                signin.setText("       Back");
                signin.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                signinMouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(signin, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                );
                jPanel4Layout.setVerticalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(signin, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                );

                jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, -1, -1));

                hide_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-uchiha-eyes-20.png"))); // NOI18N
                hide_pass.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                hide_passMouseClicked(evt);
                        }
                });
                jPanel1.add(hide_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 260, 20, 20));

                show_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-uchiha-eyes-20(1).png"))); // NOI18N
                show_pass.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                show_passMouseClicked(evt);
                        }
                });
                jPanel1.add(show_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 260, 20, 20));

                jSeparator4.setBackground(new java.awt.Color(255, 255, 255));
                jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
                jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 220, 20));

                txt_name.setBackground(new java.awt.Color(30, 81, 40));
                txt_name.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                txt_name.setForeground(new java.awt.Color(255, 255, 255));
                txt_name.setToolTipText("");
                txt_name.setBorder(null);
                txt_name.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusGained(java.awt.event.FocusEvent evt) {
                                txt_nameFocusGained(evt);
                        }
                });
                txt_name.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txt_nameActionPerformed(evt);
                        }
                });
                jPanel1.add(txt_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 220, 30));

                jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-user-20.png"))); // NOI18N
                jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 20, 30));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                );

                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void txt_usernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusGained
        txt_username.setText("");
    }//GEN-LAST:event_txt_usernameFocusGained

    private void txt_emailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusLost

    }//GEN-LAST:event_txt_emailFocusLost

    private void txt_passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusLost

    }//GEN-LAST:event_txt_passwordFocusLost

    private void txt_emailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusGained
       txt_email.setText("");
    }//GEN-LAST:event_txt_emailFocusGained

    private void txt_passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passwordFocusGained
       txt_password.setText("");
    }//GEN-LAST:event_txt_passwordFocusGained

    private void signinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signinMouseClicked
		 this.setVisible(false);
	    try {
		    new Login_Form().setVisible(true);
	    } catch (SQLException | ClassNotFoundException ex) {
		    Logger.getLogger(Register_Form.class.getName()).log(Level.SEVERE, null, ex);
	    }
    }//GEN-LAST:event_signinMouseClicked

    private void signupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupMouseClicked
        try{
        String sql = "INSERT INTO tb_user(username,password,nama,email) VALUES ('" + txt_username.getText()+ "','" + txt_password.getText()+ "',' " + txt_name.getText() + "','"  + txt_email.getText()+"')";
          
	System.out.println(sql);
	pst=conn.prepareStatement(sql);
	pst.executeUpdate();
	JOptionPane.showMessageDialog(null,"Penyimpanan Data Berhasil");
	this.setVisible(false);
	new Login_Form().setVisible(true);
      }catch(HeadlessException | ClassNotFoundException | SQLException e){
          JOptionPane.showMessageDialog(null, "ada yg error" + e.getMessage());
      }
    }//GEN-LAST:event_signupMouseClicked

    private void show_passMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_passMouseClicked
        show_pass.setVisible(false);
        hide_pass.setVisible(true);
        txt_password.setEchoChar((char)0);
    }//GEN-LAST:event_show_passMouseClicked

    private void hide_passMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hide_passMouseClicked
        hide_pass.setVisible(false);
        show_pass.setVisible(true);
        txt_password.setEchoChar('*');
    }//GEN-LAST:event_hide_passMouseClicked

        private void txt_nameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nameFocusGained
                // TODO add your handling code here:
        }//GEN-LAST:event_txt_nameFocusGained

        private void txt_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameActionPerformed
                // TODO add your handling code here:
        }//GEN-LAST:event_txt_nameActionPerformed

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
            java.util.logging.Logger.getLogger(Register_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Register_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Register_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
		    try {
			    new Register_Form().setVisible(true);
		    } catch (SQLException | ClassNotFoundException ex) {
			    Logger.getLogger(Register_Form.class.getName()).log(Level.SEVERE, null, ex);
		    }
            }
        });
    }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel hide_pass;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JSeparator jSeparator1;
        private javax.swing.JSeparator jSeparator2;
        private javax.swing.JSeparator jSeparator3;
        private javax.swing.JSeparator jSeparator4;
        private javax.swing.JLabel show_pass;
        private javax.swing.JLabel signin;
        private javax.swing.JLabel signup;
        private javax.swing.JTextField txt_email;
        private javax.swing.JTextField txt_name;
        private javax.swing.JPasswordField txt_password;
        private javax.swing.JTextField txt_username;
        // End of variables declaration//GEN-END:variables
}
