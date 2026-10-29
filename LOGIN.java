/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package toolsinventory;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author obana
 */
public class LOGIN extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LOGIN.class.getName());
      private boolean showPassword = false;
    /**
     * Creates new form LOGIN
     */
    public LOGIN() {
        initComponents();
        setLocationRelativeTo(null);
        styleButtons();
       jPanel1.setOpaque(true);
       jPanel1.setBackground(new java.awt.Color(52, 152, 219, 200));
         jPasswordField1.setEchoChar('•');
         jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
         
         jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            showPassword = !showPassword;

            if (showPassword) {
                jPasswordField1.setEchoChar((char) 0);
            } else {
                jPasswordField1.setEchoChar('•');
            }
        }
    });
         
    }
    
     private void styleButtons() {
    Color blue = new Color(37, 99, 235);
    Color blueHover = new Color(29, 78, 216);

  
    Color white = Color.WHITE;

    styleRoundedButton(jButton1, blue, blueHover, white);
    styleRoundedButton(jButton2, blue, blueHover, white);
  
}

private void styleRoundedButton(
        JButton button,
        Color normalColor,
        Color hoverColor,
        Color textColor) {

    button.setFont(new Font("Segoe UI", Font.BOLD, 13));
    button.setForeground(textColor);
    button.setBackground(normalColor);

    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    button.setUI(new javax.swing.plaf.basic.BasicButtonUI());

    button.setBorderPainted(false);

    button.addMouseListener(new java.awt.event.MouseAdapter() {

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            button.setBackground(hoverColor);
            button.repaint();
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            button.setBackground(normalColor);
            button.repaint();
        }
    });

    button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {

        @Override
        public void update(Graphics g, javax.swing.JComponent c) {
            paint(g, c);
        }

        @Override
        public void paint(Graphics g, javax.swing.JComponent c) {
            JButton b = (JButton) c;

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(b.getBackground());

            g2.fillRoundRect(
                0, 0,
                b.getWidth(),
                b.getHeight(),
                20, 20
            );

            g2.dispose();

            super.paint(g, c);
        }
    });
}


private void showWelcomeDialog(
        java.awt.Frame parent,
        String username,
        String role) {

    JDialog dialog = new JDialog(parent, false);
    dialog.setUndecorated(true);
    dialog.setSize(380, 230);
    dialog.setLocationRelativeTo(parent);
    dialog.setLayout(new java.awt.BorderLayout());

    JPanel mainPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            GradientPaint gradient = new GradientPaint(
                    0, 0,
                    new Color(15, 76, 129),
                    getWidth(), getHeight(),
                    new Color(52, 152, 219)
            );

            g2.setPaint(gradient);
            g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(), 28, 28
            );

            g2.dispose();
        }
    };

    mainPanel.setOpaque(false);
    mainPanel.setLayout(new BorderLayout(10, 10));
    mainPanel.setBorder(
            BorderFactory.createEmptyBorder(22, 20, 20, 20)
    );

    JLabel iconLabel = new JLabel("✓", SwingConstants.CENTER);
    iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
    iconLabel.setForeground(Color.WHITE);
    iconLabel.setPreferredSize(new Dimension(60, 55));

    JLabel titleLabel = new JLabel(
            "LOGIN SUCCESSFUL!",
            SwingConstants.CENTER
    );
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 21));
    titleLabel.setForeground(Color.WHITE);

    JLabel welcomeLabel = new JLabel(
            "Welcome, " + username + "!",
            SwingConstants.CENTER
    );
    welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
    welcomeLabel.setForeground(Color.WHITE);

    JLabel roleLabel = new JLabel(
            "Position: " + role,
            SwingConstants.CENTER
    );
    roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    roleLabel.setForeground(new Color(230, 242, 255));

    JLabel systemLabel = new JLabel(
            "TOOLS INVENTORY SYSTEM",
            SwingConstants.CENTER
    );
    systemLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
    systemLabel.setForeground(new Color(220, 235, 255));

    JPanel messagePanel = new JPanel();
    messagePanel.setOpaque(false);
    messagePanel.setLayout(
            new BoxLayout(messagePanel, BoxLayout.Y_AXIS)
    );

    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    systemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    messagePanel.add(iconLabel);
    messagePanel.add(Box.createVerticalStrut(2));
    messagePanel.add(titleLabel);
    messagePanel.add(Box.createVerticalStrut(8));
    messagePanel.add(welcomeLabel);
    messagePanel.add(Box.createVerticalStrut(4));
    messagePanel.add(roleLabel);
    messagePanel.add(Box.createVerticalStrut(12));
    messagePanel.add(systemLabel);

    mainPanel.add(messagePanel, BorderLayout.CENTER);

    dialog.add(mainPanel, BorderLayout.CENTER);

    dialog.setShape(
            new java.awt.geom.RoundRectangle2D.Double(
                    0, 0, 380, 230, 28, 28
            )
    );

    dialog.setVisible(true);

    javax.swing.Timer timer = new javax.swing.Timer(3000, e -> {
        dialog.dispose();
    });

    timer.setRepeats(false);
    timer.start();
}


private int showStyledConfirm(String message, String title) {
    UIManager.put("OptionPane.background", new Color(240, 247, 255));
    UIManager.put("Panel.background", new Color(240, 247, 255));
    UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
    UIManager.put("OptionPane.messageForeground", new Color(31, 41, 55));
    UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 13));
    UIManager.put("Button.background", new Color(37, 99, 235));
    UIManager.put("Button.foreground", Color.WHITE);
    UIManager.put("Button.focus", new Color(37, 99, 235));

    JButton yesButton = new JButton("YES");
    JButton noButton = new JButton("CANCEL");

    styleRoundedButton(
        yesButton,
        new Color(37, 99, 235),
        new Color(29, 78, 216),
        Color.WHITE
    );

    styleRoundedButton(
        noButton,
        new Color(107, 114, 128),
        new Color(75, 85, 99),
        Color.WHITE
    );

    Object[] options = {yesButton, noButton};

    JOptionPane pane = new JOptionPane(
        message,
        JOptionPane.QUESTION_MESSAGE,
        JOptionPane.DEFAULT_OPTION,
        null,
        options,
        yesButton
    );

    JDialog dialog = pane.createDialog(this, title);
    dialog.setModal(true);
    dialog.setVisible(true);

    Object selected = pane.getValue();
    dialog.dispose();

    return selected == yesButton
        ? JOptionPane.YES_OPTION
        : JOptionPane.NO_OPTION;
}

private void showStyledMessage(
        String message,
        String title,
        int messageType) {

    Color background;
    Color accent;
    String symbol;

    if (messageType == JOptionPane.ERROR_MESSAGE) {
        background = new Color(255, 241, 242);
        accent = new Color(220, 38, 38);
        symbol = "!";
    } else if (messageType == JOptionPane.WARNING_MESSAGE) {
        background = new Color(255, 247, 237);
        accent = new Color(217, 119, 6);
        symbol = "!";
    } else if (messageType == JOptionPane.INFORMATION_MESSAGE) {
        background = new Color(239, 246, 255);
        accent = new Color(37, 99, 235);
        symbol = "✓";
    } else {
        background = new Color(240, 247, 255);
        accent = new Color(37, 99, 235);
        symbol = "?";
    }

    JDialog dialog = new JDialog(this, title, true);
    dialog.setUndecorated(true);
    dialog.setSize(400, 220);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(new BorderLayout());

    JPanel mainPanel = new JPanel(new BorderLayout(12, 12)) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            GradientPaint gradient = new GradientPaint(
                0, 0,
                new Color(15, 76, 129),
                getWidth(), getHeight(),
                new Color(52, 152, 219)
            );

            g2.setPaint(gradient);
            g2.fillRoundRect(
                0, 0,
                getWidth(),
                getHeight(),
                28, 28
            );

            g2.dispose();
        }
    };

    mainPanel.setOpaque(false);
    mainPanel.setBorder(
        BorderFactory.createEmptyBorder(20, 24, 20, 24)
    );

    JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
    titleLabel.setForeground(Color.WHITE);

    JLabel iconLabel = new JLabel(symbol, SwingConstants.CENTER);
    iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
    iconLabel.setForeground(Color.WHITE);

    JTextArea messageArea = new JTextArea(message);
    messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    messageArea.setForeground(new Color(31, 41, 55));
    messageArea.setBackground(background);
    messageArea.setEditable(false);
    messageArea.setFocusable(false);
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    messageArea.setOpaque(false);
    messageArea.setBorder(
        BorderFactory.createEmptyBorder(8, 10, 8, 10)
    );

    JPanel messagePanel = new JPanel(new BorderLayout(8, 8));
    messagePanel.setBackground(background);
    messagePanel.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                new Color(255, 255, 255, 160), 1
            ),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        )
    );

    messagePanel.add(iconLabel, BorderLayout.WEST);
    messagePanel.add(messageArea, BorderLayout.CENTER);

    JButton okButton = new JButton("OK");
    styleRoundedButton(
        okButton,
        accent,
        accent.darker(),
        Color.WHITE
    );

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setOpaque(false);
    buttonPanel.add(okButton);

    JPanel contentPanel = new JPanel(new BorderLayout(8, 12));
    contentPanel.setOpaque(false);
    contentPanel.add(titleLabel, BorderLayout.NORTH);
    contentPanel.add(messagePanel, BorderLayout.CENTER);
    contentPanel.add(buttonPanel, BorderLayout.SOUTH);

    mainPanel.add(contentPanel, BorderLayout.CENTER);
    dialog.add(mainPanel, BorderLayout.CENTER);

    dialog.setShape(
        new java.awt.geom.RoundRectangle2D.Double(
            0, 0, 400, 220, 28, 28
        )
    );

    okButton.addActionListener(e -> dialog.dispose());

    dialog.getRootPane().setDefaultButton(okButton);
    dialog.setVisible(true);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 3, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 30, 100, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/sec logo.png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, -1, -1));

        jButton2.setBackground(new java.awt.Color(51, 102, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("CREATE ACCOUNT");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 240, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("-----------------------OR-----------------------");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        jButton1.setBackground(new java.awt.Color(51, 102, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("LOGIN");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 240, 40));

        jPasswordField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 240, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("PASSWORD:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 240, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("USERNAME:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("TOOLS INVENTORY SYSTEM");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/ICON_1.png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 47, 38));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 350, 450));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/1bbd8403-960a-4dc2-b6d3-4cfe434845ff.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      CreateAcc ca = new CreateAcc();
      ca.show();
      this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         String username = jTextField1.getText().trim();
    String password = new String(jPasswordField1.getPassword());

    if (username.isEmpty() || password.isEmpty()) {
        showStyledMessage(
            "Please enter your username and password.",
            "Missing Information",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    try {
        String role = AccountCSV.login(username, password);

        if (role == null || role.trim().isEmpty()) {
            showStyledMessage(
                "Invalid username or password.\nPlease try again.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE
            );
            jPasswordField1.setText("");
            jTextField1.requestFocus();
            return;
        }

        UserSession.startSession(username, role);

        showWelcomeDialog(this, username, role);

        if (role.equalsIgnoreCase("ADMINISTRATOR")
                || role.equalsIgnoreCase("ADMIN")) {

            Dashboard dashboard = new Dashboard();
            dashboard.setLocationRelativeTo(null);
            dashboard.setVisible(true);
            this.dispose();

        } else if (role.equalsIgnoreCase("STAFF")) {

            Dashboard dashboard = new Dashboard();
            dashboard.setLocationRelativeTo(null);
            dashboard.setVisible(true);
            this.dispose();

        } else {
            showStyledMessage(
                "Your account role is not recognized.",
                "Access Denied",
                JOptionPane.WARNING_MESSAGE
            );
            UserSession.logout();
        }

    } catch (Exception e) {
        showStyledMessage(
            "Unable to log in.\nPlease check your account file or database connection.\n\n"
                + e.getMessage(),
            "Login Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    }//GEN-LAST:event_jButton1ActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new LOGIN().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
