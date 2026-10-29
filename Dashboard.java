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
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author obana
 */
public class Dashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dashboard.class.getName());

    /**
     * Creates new form Dashboard
     */
  public Dashboard() {
    initComponents();

    styleButtons();

    BorrowCSV.loadBorrow(jTable1);
    loadToolSummary();

    setLocationRelativeTo(null);

    UserSession.installExitConfirmation(this);

    boolean staff = UserSession.isStaff();
    //jButton3.setVisible(!staff);
    
     
    }

    

    
    private void loadToolSummary() {

String[] csvFiles = {
    "tools.csv",
    "TM.csv",
    "SMAWNC2.csv",
    "SMAWNC1.csv",
    "fe.csv",
    "FD.csv",
    "EM.csv",
    "ECCADSNC3.csv",
    "DRIVING.csv",
    "DM.csv",
    "CP.csv",
    "BK.csv"
};

int totalTools = 0;
int availableTools = 0;
int inUseTools = 0;

for (String fileName : csvFiles) {

    File file = new File(fileName);

    if (!file.exists()) {
        continue;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {

            if (firstLine) {
                firstLine = false;
                continue;
            }

            String[] data = line.split(",", -1);

            if (data.length < 3) {
                continue;
            }

            int quantity;

            try {
                quantity = Integer.parseInt(data[1].trim());
            } catch (NumberFormatException e) {
                continue;
            }

            String status = data[2].trim()
                    .toUpperCase()
                    .replaceAll("\\s+", "");

            totalTools += quantity;

            if (status.equals("AVAIL")
                    || status.equals("AVAILABLE")
                    || status.equals("ACTIVE")) {

                availableTools += quantity;

            } else if (status.equals("INUSE")) {

                inUseTools += quantity;
            }

        }

    } catch (IOException e) {

        JOptionPane.showMessageDialog(this,
                "Error loading " + fileName + ": " + e.getMessage());
    }
}

jLabel10.setText(String.valueOf(totalTools));
jLabel11.setText(String.valueOf(availableTools));
jLabel12.setText(String.valueOf(inUseTools));
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
    styleRoundedButton(jButton4, red, redHover, white);
    styleRoundedButton(jButton8, blue, blueHover, white);
      styleRoundedButton(jButton9, blue, blueHover, white);
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

private void showWelcomeMessage(String username) {

    JDialog dialog = new JDialog(this);
    dialog.setUndecorated(true);
    dialog.setModal(false);
    dialog.setSize(380, 165);
    dialog.setLocationRelativeTo(this);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(Color.WHITE);
    mainPanel.setBorder(BorderFactory.createLineBorder(
            new Color(37, 99, 235), 2
    ));

    JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
    header.setBackground(new Color(37, 99, 235));

    JLabel title = new JLabel("TOOLS INVENTORY SYSTEM");
    title.setFont(new Font("Segoe UI", Font.BOLD, 15));
    title.setForeground(Color.WHITE);

    header.add(title);

    JPanel content = new JPanel();
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    content.setBackground(Color.WHITE);
    content.setBorder(BorderFactory.createEmptyBorder(12, 15, 10, 15));

    JLabel success = new JLabel(
            "\u2714  LOGIN SUCCESSFUL!"
    );
    success.setFont(new Font("Segoe UI", Font.BOLD, 19));
    success.setForeground(new Color(22, 163, 74));
    success.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel welcome = new JLabel(
            "Welcome, " + (username == null ? "User" : username) + "!"
    );
    welcome.setFont(new Font("Segoe UI", Font.BOLD, 17));
    welcome.setForeground(new Color(30, 41, 59));
    welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel subtitle = new JLabel("You have successfully logged in.");
    subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    subtitle.setForeground(new Color(100, 116, 139));
    subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    content.add(success);
    content.add(Box.createVerticalStrut(7));
    content.add(welcome);
    content.add(Box.createVerticalStrut(4));
    content.add(subtitle);

    mainPanel.add(header, BorderLayout.NORTH);
    mainPanel.add(content, BorderLayout.CENTER);

    dialog.setContentPane(mainPanel);
    dialog.setAlwaysOnTop(true);

    Timer timer = new Timer(3000, e -> dialog.dispose());
    timer.setRepeats(false);

    dialog.setVisible(true);
    timer.start();
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
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/logo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 20, 190, 170));

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("TOOLS INVENTORY");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 150, 50));

        jButton4.setBackground(new java.awt.Color(102, 102, 102));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("LOGOUT");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(this::jButton4ActionPerformed);
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 150, 50));

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("BARROW TOOLS");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(this::jButton2ActionPerformed);
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 150, 50));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 153));
        jLabel13.setText("TOTAL BARROWER");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, -1, -1));

        jButton3.setBackground(new java.awt.Color(102, 102, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("ACCOUNT");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(this::jButton3ActionPerformed);
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 150, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/sec.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 630));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/total tools.jpg"))); // NOI18N
        jLabel4.setText("jLabel4");

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("TOTAL TOOLS");

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 3, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10))
                    .addComponent(jLabel4))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 200, 100));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/Avail.jpg"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("AVAILABLE");

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 3, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addComponent(jLabel5))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 210, 100));

        jButton9.setBackground(new java.awt.Color(51, 51, 255));
        jButton9.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("DELETE");
        jButton9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton9.addActionListener(this::jButton9ActionPerformed);
        getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 560, 130, 50));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NAME", "ITEM", "BARROW DATE", "RETURN DATE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 640, 380));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/inuse.jpg"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 3, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("IN USE");

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 3, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 102));
        jLabel12.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12))
                    .addComponent(jLabel6))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 40, 210, 100));

        jButton8.setBackground(new java.awt.Color(51, 51, 255));
        jButton8.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("PRINT");
        jButton8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton8.addActionListener(this::jButton8ActionPerformed);
        getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 560, 130, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/bgg.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 690, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     ALL t = new  ALL();
     t.show();
     this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       barrow b = new barrow();
       b.show();
       this.dispose();
       
       
    }//GEN-LAST:event_jButton2ActionPerformed

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
            "Do you want to print the Total Borrower?",
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
                new java.text.MessageFormat("TOTAL BORROWER"),
                new java.text.MessageFormat("Page {0}")
        );

        if (complete) {
            showStyledMessage(
                    "The TOTAL BORROWER was sent to the printer successfully.",
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        UserSession.confirmLogout(this);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
         int selectedRow = jTable1.getSelectedRow();

    if (selectedRow == -1) {
        showStyledMessage(
                "Please select a record from the table first.",
                "No Record Selected",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int modelRow = jTable1.convertRowIndexToModel(selectedRow);

    String borrower = String.valueOf(
            jTable1.getModel().getValueAt(modelRow, 0)
    );
    String tool = String.valueOf(
            jTable1.getModel().getValueAt(modelRow, 1)
    );
    String borrowDate = String.valueOf(
            jTable1.getModel().getValueAt(modelRow, 2)
    );
    String returnDate = String.valueOf(
            jTable1.getModel().getValueAt(modelRow, 3)
    );

    int confirm = showStyledConfirm(
            "Delete this selected record?\n\n"
            + "Borrower: " + borrower + "\n"
            + "Tool: " + tool + "\n"
            + "Borrow Date: " + borrowDate + "\n"
            + "Return Date: " + returnDate,
            "Confirm Delete"
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    File file = new File("borrow.csv");
    File tempFile = new File("borrow_temp.csv");

    int matchingRecord = 0;
    boolean deleted = false;

    try (
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
    ) {
        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {
            if (firstLine) {
                bw.write(line);
                bw.newLine();
                firstLine = false;
                continue;
            }

            String[] data = line.split(",", -1);

            if (data.length >= 4
                    && data[0].trim().equals(borrower.trim())
                    && data[1].trim().equals(tool.trim())
                    && data[2].trim().equals(borrowDate.trim())
                    && data[3].trim().equals(returnDate.trim())) {

                matchingRecord++;

                if (matchingRecord == 1) {
                    deleted = true;
                    continue;
                }
            }

            bw.write(line);
            bw.newLine();
        }

    } catch (IOException e) {
        tempFile.delete();

        showStyledMessage(
                "Error deleting record: " + e.getMessage(),
                "Delete Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (!deleted) {
        tempFile.delete();

        showStyledMessage(
                "The selected record was not found in borrow.csv.",
                "Record Not Found",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (file.delete() && tempFile.renameTo(file)) {
        BorrowCSV.loadBorrow(jTable1);

        showStyledMessage(
                "The selected record has been deleted successfully.",
                "Delete Successful",
                JOptionPane.INFORMATION_MESSAGE
        );
    } else {
        if (!file.exists()) {
            tempFile.renameTo(file);
        }

        showStyledMessage(
                "Unable to update borrow.csv. Please close any program "
                + "using the file and try again.",
                "Delete Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_jButton9ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Dashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
