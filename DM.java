/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package toolsinventory;
import java.awt.BorderLayout;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author obana
 */
public class DM extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DM.class.getName());

    /**
     * Creates new form DM
     */
    public DM() {
        initComponents();
         setLocationRelativeTo(null);
         styleButtons();
         UserSession.installExitConfirmation(this);

         /*  boolean staff = UserSession.isStaff();
         
         jButton3.setVisible(!staff);*/

        
         UserSession.installExitConfirmation(this);
         
        createCSV();
    loadTools();
     colorLowStock();
jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
    new String[] { "AVAILABLE", "NOT AVAILABLE", "INUSE" }
        
        
));

   jTextField3.getDocument().addDocumentListener(
        new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                searchTools();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchTools();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchTools();
            }
        }
    );
    }
    
      private final String CSV_FILE = "DM.csv";

private void createCSV() {
    File file = new File(CSV_FILE);

    if (!file.exists()) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println("Tool Name,Quantity,Status");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error creating CSV: " + e.getMessage());
        }
    }
}

private void loadTools() {
    DefaultTableModel model =
            (DefaultTableModel) jTable1.getModel();

    model.setRowCount(0);

    createCSV();

    try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {

        String line;

        // Skip header
        br.readLine();

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",", -1);

            if (data.length >= 3) {
                model.addRow(new Object[]{
                    data[0],
                    data[1],
                    data[2]
                });
            }
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this,
                "Error loading tools: " + e.getMessage());
    }
}
private void searchTools() {

    String search = jTextField3.getText()
            .trim()
            .toLowerCase();

    DefaultTableModel model =
            (DefaultTableModel) jTable1.getModel();

    model.setRowCount(0);

    createCSV();

    try (BufferedReader br =
            new BufferedReader(new FileReader(CSV_FILE))) {

        String line;

        // Skip header
        br.readLine();

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",", -1);

            if (data.length >= 3) {

                String toolName = data[0].toLowerCase();
                String quantity = data[1].toLowerCase();
                String status = data[2].toLowerCase();

                if (toolName.contains(search)
                        || quantity.contains(search)
                        || status.contains(search)) {

                    model.addRow(new Object[]{
                        data[0],
                        data[1],
                        data[2]
                    });
                }
            }
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this,
                "Error searching tools: " + e.getMessage());
    }
}



private void colorLowStock() {
    jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            int modelRow = table.convertRowIndexToModel(row);
            int stock = 0;

            try {
                stock = Integer.parseInt(
                        table.getModel().getValueAt(modelRow, 1).toString()
                );
            } catch (Exception e) {
                stock = 0;
            }

            if (stock < 5) {
                c.setBackground(Color.RED);
                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }

            if (isSelected) {
                c.setBackground(new Color(0, 0, 102));
                c.setForeground(Color.WHITE);
            }

            return c;
        }
    });
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
    styleRoundedButton(jButton9, red, redHover, white);
    styleRoundedButton(jButton4, blue, blueHover, white);
    styleRoundedButton(jButton8, blue, blueHover, white);
    styleRoundedButton(jButton5, blue, blueHover, white);
    styleRoundedButton(jButton6, blue, blueHover, white);
    styleRoundedButton(jButton7, blue, blueHover, white);
    styleRoundedButton(jButton10, blue, blueHover, white);
      
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

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

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
        jButton3.setText("ACCOUNTS");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(this::jButton3ActionPerformed);
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 150, 50));

        jButton9.setBackground(new java.awt.Color(102, 102, 102));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("LOGOUT");
        jButton9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton9.addActionListener(this::jButton9ActionPerformed);
        getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 150, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/sec.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 630));

        jLabel3.setFont(new java.awt.Font("Segoe UI Historic", 3, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("DRESSMAKING NC ll");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 760, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("TOOL NAME:");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("STATUS:");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AVAILABLE", "NOT AVAILABLE", "INUSE" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("QUANTITY:");

        jButton4.setBackground(new java.awt.Color(51, 51, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("SAVE TOOL");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton7.setBackground(new java.awt.Color(51, 51, 255));
        jButton7.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("CLEAR");
        jButton7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton7.addActionListener(this::jButton7ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(10, 10, 10)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(10, 10, 10)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 260, 370));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/ICON.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 80, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("ADD NEW TOOL");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, -1, -1));

        jButton10.setBackground(new java.awt.Color(51, 51, 255));
        jButton10.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("BACK");
        jButton10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton10.addActionListener(this::jButton10ActionPerformed);
        getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 560, 130, 50));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "TOOL NAME", "QUANTITY", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, 480, 440));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/ADD.png"))); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, -1, -1));

        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 260, -1));

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("UPDATE TOOL");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.addActionListener(this::jButton5ActionPerformed);
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 560, 130, 50));

        jButton8.setBackground(new java.awt.Color(51, 51, 255));
        jButton8.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("PRINT");
        jButton8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton8.addActionListener(this::jButton8ActionPerformed);
        getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 560, 130, 50));

        jButton6.setBackground(new java.awt.Color(51, 51, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("DELETE TOOL");
        jButton6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.addActionListener(this::jButton6ActionPerformed);
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 560, 130, 50));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/bgg.png"))); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 790, 620));

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
   if (UserSession.isStaff()) {
        JOptionPane.showMessageDialog(
            this,
            "Access denied! Admin only.",
            "Restricted Access",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    Acoount a = new Acoount();
    a.setLocationRelativeTo(null);
    a.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
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
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    String toolName = jTextField1.getText().trim();
    String quantityText = jTextField2.getText().trim();
    String status = jComboBox1.getSelectedItem().toString();

    if (toolName.isEmpty() || quantityText.isEmpty()) {
        showStyledMessage(
                "Please enter Tool Name and Quantity.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int quantity;

    try {
        quantity = Integer.parseInt(quantityText);

        if (quantity <= 0) {
            showStyledMessage(
                    "Quantity must be greater than zero.",
                    "Invalid Quantity",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
    } catch (NumberFormatException e) {
        showStyledMessage(
                "Quantity must be a valid number.",
                "Invalid Quantity",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    DefaultTableModel model =
            (DefaultTableModel) jTable1.getModel();

    int existingRow = -1;

    for (int i = 0; i < model.getRowCount(); i++) {
        String existingName =
                model.getValueAt(i, 0).toString().trim();

        if (existingName.equalsIgnoreCase(toolName)) {
            existingRow = i;
            break;
        }
    }

    if (existingRow != -1) {
        int oldQuantity;

        try {
            oldQuantity = Integer.parseInt(
                    model.getValueAt(existingRow, 1).toString()
            );
        } catch (NumberFormatException e) {
            showStyledMessage(
                    "The existing tool quantity is invalid.",
                    "Invalid Data",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int newQuantity = oldQuantity + quantity;

        model.setValueAt(newQuantity, existingRow, 1);

        String existingStatus =
                model.getValueAt(existingRow, 2).toString();

        if (existingStatus.equalsIgnoreCase("NOT AVAIL")
                || existingStatus.equalsIgnoreCase("NOT AVAILABLE")) {
            model.setValueAt("AVAILABLE", existingRow, 2);
        }
    } else {
        model.addRow(new Object[]{
            toolName,
            quantity,
            status
        });
    }

    try (PrintWriter pw = new PrintWriter(
            new FileWriter(CSV_FILE))) {

        pw.println("Tool Name,Quantity,Status");

        for (int i = 0; i < model.getRowCount(); i++) {
            pw.println(
                    model.getValueAt(i, 0) + ","
                    + model.getValueAt(i, 1) + ","
                    + model.getValueAt(i, 2)
            );
        }

    } catch (IOException e) {
        showStyledMessage(
                "Error saving BK.csv:\n" + e.getMessage(),
                "Save Error",
                JOptionPane.ERROR_MESSAGE
        );

        loadTools();
        return;
    }

    jTextField1.setText("");
    jTextField2.setText("");
    jComboBox1.setSelectedIndex(0);

    colorLowStock();

    showStyledMessage(
            "Tool saved successfully!",
            "Save Success",
            JOptionPane.INFORMATION_MESSAGE
    );
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jTextField1.setText("");
        jTextField2.setText("");
        jComboBox1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();

        if (row != -1) {
            jTextField1.setText(jTable1.getValueAt(row, 0).toString());
            jTextField2.setText(jTable1.getValueAt(row, 1).toString());
            jComboBox1.setSelectedItem(jTable1.getValueAt(row, 2).toString());
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
             int row = jTable1.getSelectedRow();

    if (row == -1) {
        showStyledMessage(
                "Please select a tool from the table first.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String oldToolName =
            jTable1.getValueAt(row, 0).toString();

    String oldQuantity =
            jTable1.getValueAt(row, 1).toString();

    String oldStatus =
            jTable1.getValueAt(row, 2).toString();

    String newToolName = jTextField1.getText().trim();
    String newQuantity = jTextField2.getText().trim();
    String newStatus = jComboBox1.getSelectedItem().toString();

    if (newToolName.isEmpty() || newQuantity.isEmpty()) {
        showStyledMessage(
                "Please complete the Tool Name and Quantity fields.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int quantity;

    try {
        quantity = Integer.parseInt(newQuantity);

        if (quantity <= 0) {
            showStyledMessage(
                    "Quantity must be greater than zero.",
                    "Invalid Quantity",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
    } catch (NumberFormatException e) {
        showStyledMessage(
                "Quantity must be a valid number.",
                "Invalid Quantity",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    int confirm = showStyledConfirm(
            "Update the selected tool?\n\n"
            + "Old Name: " + oldToolName + "\n"
            + "Old Quantity: " + oldQuantity + "\n"
            + "Old Status: " + oldStatus + "\n\n"
            + "New Name: " + newToolName + "\n"
            + "New Quantity: " + quantity + "\n"
            + "New Status: " + newStatus,
            "Confirm Update"
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    File inputFile = new File(CSV_FILE);
    File tempFile = new File("BK_temp.csv");
    boolean updated = false;

    try (
            BufferedReader br = new BufferedReader(
                    new FileReader(inputFile));
            PrintWriter pw = new PrintWriter(
                    new FileWriter(tempFile))
    ) {
        pw.println("Tool Name,Quantity,Status");

        br.readLine();

        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",", -1);

            if (!updated
                    && data.length >= 3
                    && data[0].equals(oldToolName)
                    && data[1].equals(oldQuantity)
                    && data[2].equals(oldStatus)) {

                pw.println(
                        newToolName + ","
                        + quantity + ","
                        + newStatus
                );

                updated = true;
            } else {
                pw.println(line);
            }
        }

    } catch (IOException e) {
        tempFile.delete();

        showStyledMessage(
                "Error updating tool:\n" + e.getMessage(),
                "Update Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (!updated) {
        tempFile.delete();

        showStyledMessage(
                "The selected tool could not be found in BK.csv.",
                "Update Failed",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    try {
        java.nio.file.Files.move(
                tempFile.toPath(),
                inputFile.toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
        );
    } catch (IOException e) {
        showStyledMessage(
                "Unable to replace BK.csv:\n" + e.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    loadTools();
    colorLowStock();

    jTextField1.setText("");
    jTextField2.setText("");
    jComboBox1.setSelectedIndex(0);
    jTable1.clearSelection();

    showStyledMessage(
            "Tool updated successfully!",
            "Update Success",
            JOptionPane.INFORMATION_MESSAGE
    );

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
          if (jTable1.getRowCount() == 0) {
        showStyledMessage(
                "There are no tools to print.",
                "Nothing to Print",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int confirm = showStyledConfirm(
            "Do you want to print the DRESSMAKING NC ll inventory?",
            "Confirm Print"
    );

    if (confirm != JOptionPane.YES_OPTION) {
        showStyledMessage(
                "Printing was cancelled.",
                "Print Cancelled",
                JOptionPane.INFORMATION_MESSAGE
        );
        return;
    }

    try {
        boolean complete = jTable1.print(
                javax.swing.JTable.PrintMode.FIT_WIDTH,
                new java.text.MessageFormat("DRESSMAKING NC ll - TOOLS INVENTORY"),
                new java.text.MessageFormat("Page {0}")
        );

        if (complete) {
            showStyledMessage(
                    "The inventory was sent to the printer successfully.",
                    "Print Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            showStyledMessage(
                    "The printing process was cancelled.",
                    "Print Cancelled",
                    JOptionPane.WARNING_MESSAGE
            );
        }

    } catch (java.awt.print.PrinterException e) {
        showStyledMessage(
                "Error printing inventory:\n" + e.getMessage(),
                "Print Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
         int row = jTable1.getSelectedRow();

    if (row == -1) {
        showStyledMessage(
                "Please select a tool from the table first.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String toolName = jTable1.getValueAt(row, 0).toString();
    String quantity = jTable1.getValueAt(row, 1).toString();
    String status = jTable1.getValueAt(row, 2).toString();

    int confirm = showStyledConfirm(
            "Are you sure you want to delete this tool?\n\n"
            + "Tool Name: " + toolName + "\n"
            + "Quantity: " + quantity + "\n"
            + "Status: " + status,
            "Confirm Delete"
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    File inputFile = new File(CSV_FILE);
    File tempFile = new File("BK_delete_temp.csv");

    boolean deleted = false;

    try (
            BufferedReader br = new BufferedReader(
                    new FileReader(inputFile));
            PrintWriter pw = new PrintWriter(
                    new FileWriter(tempFile))
    ) {
        String header = br.readLine();

        if (header != null) {
            pw.println(header);
        }

        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",", -1);

            if (!deleted
                    && data.length >= 3
                    && data[0].trim().equals(toolName)
                    && data[1].trim().equals(quantity)
                    && data[2].trim().equals(status)) {

                deleted = true;
                continue;
            }

            pw.println(line);
        }

    } catch (IOException e) {
        tempFile.delete();

        showStyledMessage(
                "Error deleting tool:\n" + e.getMessage(),
                "Delete Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (!deleted) {
        tempFile.delete();

        showStyledMessage(
                "The selected tool was not found in BK.csv.",
                "Delete Failed",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    try {
        java.nio.file.Files.move(
                tempFile.toPath(),
                inputFile.toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
        );
    } catch (IOException e) {
        showStyledMessage(
                "Unable to update BK.csv:\n" + e.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    loadTools();
    colorLowStock();

    jTextField1.setText("");
    jTextField2.setText("");
    jComboBox1.setSelectedIndex(0);
    jTable1.clearSelection();

    showStyledMessage(
            "The selected tool was deleted successfully.",
            "Delete Success",
            JOptionPane.INFORMATION_MESSAGE
    );
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
       ALL A = new ALL();
       A.show();
       this.dispose();
    }//GEN-LAST:event_jButton10ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new DM().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
