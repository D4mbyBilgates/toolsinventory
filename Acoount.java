/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package toolsinventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

/**
 *
 * @author obana
 */
public class Acoount extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Acoount.class.getName());

    /**
     * Creates new form Acoount
     */
    public Acoount() {
        initComponents();
        UserSession.installExitConfirmation(this);
        styleButtons();
        
        setLocationRelativeTo(null);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][] {},
        new String[] {"USERNAME", "POSITION", "STATUS"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    });

    loadAccounts();
    }
    
    private void loadAccounts() {
    try {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable2.getModel();

        model.setRowCount(0);

        Object[][] accounts = AccountCSV.getAccounts();

        for (Object[] account : accounts) {
            model.addRow(account);
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error loading accounts: " + e.getMessage());
    }
}
    
    private void styleButtons() {
    Color blue = new Color(37, 99, 235);
    Color blueHover = new Color(29, 78, 216);

    Color red = new Color(220, 53, 69);
    Color redHover = new Color(185, 35, 50);

    Color white = Color.WHITE;

    styleRoundedButton(jButton1, blue, blueHover, white);
    styleRoundedButton(jButton2, blue, blueHover, white);
    styleRoundedButton(jButton3, blue, blueHover, white);
    styleRoundedButton(jButton7, red, redHover, white);
    styleRoundedButton(jButton4, blue, blueHover, white);
     styleRoundedButton(jButton5, blue, blueHover, white);
      styleRoundedButton(jButton6, blue, blueHover, white);
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

private void showStyledMessage(String message, String title, int type) {
    final JDialog dialog = new JDialog(this, title, true);
    dialog.setUndecorated(true);
    dialog.setSize(390, 225);
    dialog.setLocationRelativeTo(this);

    Color blue = new Color(37, 99, 235);
    Color darkBlue = new Color(15, 45, 90);

    JPanel mainPanel = new JPanel(new BorderLayout(12, 12)) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setPaint(new GradientPaint(
                    0, 0, darkBlue,
                    getWidth(), getHeight(), blue
            ));

            g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(), 28, 28
            );
            g2.dispose();
        }
    };

    mainPanel.setOpaque(false);
    mainPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 22, 18, 22)
    );

    String symbol;
    if (type == JOptionPane.ERROR_MESSAGE) {
        symbol = "✖";
    } else if (type == JOptionPane.WARNING_MESSAGE) {
        symbol = "!";
    } else if (type == JOptionPane.QUESTION_MESSAGE) {
        symbol = "?";
    } else {
        symbol = "✓";
    }

    JLabel iconLabel = new JLabel(symbol, SwingConstants.CENTER);
    iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
    iconLabel.setForeground(Color.WHITE);

    JLabel titleLabel = new JLabel(
            title.toUpperCase(),
            SwingConstants.CENTER
    );
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
    titleLabel.setForeground(Color.WHITE);

    JTextArea messageLabel = new JTextArea(message);
    messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    messageLabel.setForeground(Color.WHITE);
    messageLabel.setOpaque(false);
    messageLabel.setEditable(false);
    messageLabel.setFocusable(false);
    messageLabel.setLineWrap(true);
    messageLabel.setWrapStyleWord(true);
    messageLabel.setHighlighter(null);
    messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JPanel centerPanel = new JPanel();
    centerPanel.setOpaque(false);
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    centerPanel.add(iconLabel);
    centerPanel.add(Box.createVerticalStrut(3));
    centerPanel.add(titleLabel);
    centerPanel.add(Box.createVerticalStrut(12));
    centerPanel.add(messageLabel);

    JButton okButton = new JButton("OK");
    okButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
    okButton.setForeground(blue);
    okButton.setBackground(Color.WHITE);
    okButton.setFocusPainted(false);
    okButton.setBorderPainted(false);
    okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    okButton.setPreferredSize(new Dimension(105, 36));

    okButton.addActionListener(e -> dialog.dispose());

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.setOpaque(false);
    bottomPanel.add(okButton);

    mainPanel.add(centerPanel, BorderLayout.CENTER);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    dialog.setContentPane(mainPanel);
    dialog.getRootPane().setDefaultButton(okButton);
    dialog.getRootPane().registerKeyboardAction(
            e -> dialog.dispose(),
            KeyStroke.getKeyStroke("ESCAPE"),
            JComponent.WHEN_IN_FOCUSED_WINDOW
    );

    dialog.setVisible(true);
}

private int showStyledConfirm(String message, String title) {
    final int[] result = {-1};

    JDialog dialog = new JDialog(this, title, true);
    dialog.setUndecorated(true);
    dialog.setSize(390, 235);
    dialog.setLocationRelativeTo(this);

    Color blue = new Color(37, 99, 235);
    Color darkBlue = new Color(15, 45, 90);

    JPanel panel = new JPanel(new BorderLayout(12, 12)) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );
            g2.setPaint(new GradientPaint(
                    0, 0, darkBlue,
                    getWidth(), getHeight(), blue
            ));
            g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(), 28, 28
            );
            g2.dispose();
        }
    };

    panel.setOpaque(false);
    panel.setBorder(BorderFactory.createEmptyBorder(20, 22, 18, 22));

    JLabel iconLabel = new JLabel("?", SwingConstants.CENTER);
    iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
    iconLabel.setForeground(Color.WHITE);

    JLabel titleLabel = new JLabel(
            title.toUpperCase(), SwingConstants.CENTER
    );
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    titleLabel.setForeground(Color.WHITE);

    JTextArea text = new JTextArea(message);
    text.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    text.setForeground(Color.WHITE);
    text.setOpaque(false);
    text.setEditable(false);
    text.setFocusable(false);
    text.setLineWrap(true);
    text.setWrapStyleWord(true);

    JPanel center = new JPanel();
    center.setOpaque(false);
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    center.add(iconLabel);
    center.add(titleLabel);
    center.add(Box.createVerticalStrut(10));
    center.add(text);

    JButton yes = new JButton("YES, CONTINUE");
    JButton no = new JButton("CANCEL");

    for (JButton button : new JButton[]{yes, no}) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(135, 36));
    }

    yes.setBackground(Color.WHITE);
    yes.setForeground(blue);
    no.setBackground(new Color(220, 53, 69));
    no.setForeground(Color.WHITE);

    yes.addActionListener(e -> {
        result[0] = JOptionPane.YES_OPTION;
        dialog.dispose();
    });

    no.addActionListener(e -> {
        result[0] = JOptionPane.NO_OPTION;
        dialog.dispose();
    });

    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
    buttons.setOpaque(false);
    buttons.add(yes);
    buttons.add(no);

    panel.add(center, BorderLayout.CENTER);
    panel.add(buttons, BorderLayout.SOUTH);

    dialog.setContentPane(panel);
    dialog.getRootPane().setDefaultButton(no);
    dialog.getRootPane().registerKeyboardAction(
            e -> dialog.dispose(),
            KeyStroke.getKeyStroke("ESCAPE"),
            JComponent.WHEN_IN_FOCUSED_WINDOW
    );

    dialog.setVisible(true);
    return result[0];
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Username", "Position"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/logo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 190, 170));

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("DASHBOARD");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 150, 50));

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("BARROW TOOLS");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(this::jButton2ActionPerformed);
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 150, 50));

        jButton3.setBackground(new java.awt.Color(102, 102, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("TOOLS INVENTORY");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(this::jButton3ActionPerformed);
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 150, 50));

        jButton7.setBackground(new java.awt.Color(102, 102, 102));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("LOGOUT");
        jButton7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton7.addActionListener(this::jButton7ActionPerformed);
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 150, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/sec.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 630));

        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 90, 73));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/ICON.png"))); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, -1, -1));

        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 260, -1));

        jButton4.setBackground(new java.awt.Color(51, 102, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("DEACTIVATE");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(this::jButton4ActionPerformed);
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 570, 140, 50));

        jButton6.setBackground(new java.awt.Color(51, 102, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("DELETE");
        jButton6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.addActionListener(this::jButton6ActionPerformed);
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 570, 140, 50));

        jButton5.setBackground(new java.awt.Color(51, 102, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("ACTIVATE");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.addActionListener(this::jButton5ActionPerformed);
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 570, 140, 50));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "USERNAME", "POSITION", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
        }

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 750, 410));

        jLabel7.setFont(new java.awt.Font("Segoe UI Historic", 3, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("ACCOUNT MANAGEMENT");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 500, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, 110, 20));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 60, 120, 20));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/bgg.png"))); // NOI18N
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 800, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Dashboard db = new Dashboard();
        db.show();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        barrow b = new barrow();
        b.show();
        this.dispose();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
   ALL t = new  ALL();
     t.show();
     this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      int row = jTable2.getSelectedRow();

    if (row == -1) {
        showStyledMessage(
                "Please select an account first.",
                "No Account Selected",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String username = jTable2.getValueAt(row, 0).toString();
    String status = jTable2.getValueAt(row, 2).toString();

    if (status.equalsIgnoreCase("INACTIVE")) {
        showStyledMessage(
                "This account is already inactive.",
                "Account Already Inactive",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int confirm = showStyledConfirm(
            "Deactivate this account?\n\nUsername: " + username,
            "Confirm Deactivation"
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        AccountCSV.deactivateAccount(username);
        loadAccounts();

        showStyledMessage(
                "Account deactivated successfully!",
                "Deactivation Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    } catch (Exception e) {
        showStyledMessage(
                "Error deactivating account:\n" + e.getMessage(),
                "Deactivation Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
     int row = jTable2.getSelectedRow();

    if (row == -1) {
        showStyledMessage(
                "Please select an account first.",
                "No Account Selected",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String username = jTable2.getValueAt(row, 0).toString();
    String status = jTable2.getValueAt(row, 2).toString();

    if (status.equalsIgnoreCase("ACTIVE")) {
        showStyledMessage(
                "This account is already active.",
                "Account Already Active",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int confirm = showStyledConfirm(
            "Activate this account?\n\nUsername: " + username,
            "Confirm Activation"
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        AccountCSV.activateAccount(username);
        loadAccounts();

        showStyledMessage(
                "Account activated successfully!",
                "Activation Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    } catch (Exception e) {
        showStyledMessage(
                "Error activating account:\n" + e.getMessage(),
                "Activation Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      int row = jTable2.getSelectedRow();

    if (row == -1) {
        showStyledMessage(
                "Please select an account first.",
                "No Account Selected",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String username = jTable2.getValueAt(row, 0).toString();

    int confirm = showStyledConfirm(
            "This action cannot be undone.\n\n"
            + "Are you sure you want to permanently delete this account?\n"
            + "Username: " + username,
            "Confirm Delete"
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        AccountCSV.deleteAccount(username);
        loadAccounts();

        showStyledMessage(
                "Account deleted successfully!",
                "Delete Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    } catch (Exception e) {
        showStyledMessage(
                "Error deleting account:\n" + e.getMessage(),
                "Delete Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
      int confirm = showStyledConfirm(
            "Are you sure you want to log out?",
            "Confirm Logout"
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    LOGIN login = new LOGIN();
    login.setLocationRelativeTo(null);
    login.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Acoount().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
