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
import java.io.*;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import javax.swing.RowFilter;
/**
 *
 * @author obana
 */
public class barrow extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(barrow.class.getName());

    /**
     * Creates new form barrow
     */
    public barrow() {
        initComponents();
        styleButtons();
         setupLiveSearch();

    jComboBox1.addActionListener(e -> loadSelectedCourseCSV());

    jComboBox1.setSelectedIndex(0);

    setLocationRelativeTo(null);

    UserSession.installExitConfirmation(this);
    }
    
    private String getSelectedCSV() {
    String course = jComboBox1.getSelectedItem().toString();

    switch (course) {
        case "BHS NC ll":
            return "tools.csv";

        case "BOOKEEPING NC ll":
            return "BK.csv";

        case "CONSTRUCTION PAINTING NCll":
            return "CP.csv";

        case "DRESSMAKING NC ll":
            return "DM.csv";

        case "FASHION DESIGN NC ll":
            return "FD.csv";

        case "FACILITATE s-LEARNING SESSIONS":
            return "fe.csv";

        case "SMAW NC l":
            return "SMAWNC1.csv";

        case "SMAW NC ll":
            return "SMAWNC2.csv";

        case "TM LEVEL 1":
            return "TM.csv";

        case "ECCDS NC lll":
            return "ECCADSNC3.csv";

        case "EVENT MANAGEMENT SERVICES NC lll":
            return "EM.csv";

        case "DRIVING":
            return "DRIVING.csv";

        default:
            return null;
    }
}

private void loadSelectedCourseCSV() {
    String fileName = getSelectedCSV();

    javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();

    model.setRowCount(0);

    if (fileName == null) {
        return;
    }

    File file = new File(fileName);

    if (!file.exists()) {
        JOptionPane.showMessageDialog(
                this,
                "CSV file not found: " + fileName,
                "File Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    try (BufferedReader br =
            new BufferedReader(new FileReader(file))) {

        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {

            if (firstLine) {
                firstLine = false;

                if (line.toLowerCase().contains("tool name")
                        || line.toLowerCase().contains("quantity")) {
                    continue;
                }
            }

            String[] data = line.split(",", -1);

            if (data.length >= 3) {
                model.addRow(new Object[]{
                    data[0].trim(),
                    data[1].trim(),
                    data[2].trim()
                });
            }
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(
                this,
                "Error loading CSV: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
    
   private void updateQuantity(String toolName, int newQuantity) {
    String fileName = getSelectedCSV();

    if (fileName == null) {
        JOptionPane.showMessageDialog(this,
                "Please select a course first.");
        return;
    }

    File file = new File(fileName);
    File tempFile = new File(fileName + ".tmp");

    try (
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
    ) {
        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);

            if (firstLine) {
                firstLine = false;
                bw.write(line);
                bw.newLine();
                continue;
            }

            if (data.length >= 3
                    && data[0].trim().equalsIgnoreCase(toolName)) {
                data[1] = String.valueOf(newQuantity);
            }

            bw.write(String.join(",", data));
            bw.newLine();
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this,
                "Error updating quantity: " + e.getMessage());
        tempFile.delete();
        return;
    }

    if (file.delete()) {
        if (!tempFile.renameTo(file)) {
            JOptionPane.showMessageDialog(this,
                    "Unable to replace CSV file.");
        }
    } else {
        tempFile.delete();
        JOptionPane.showMessageDialog(this,
                "Unable to update CSV file.");
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
    styleRoundedButton(jButton5, red, redHover, white);
    styleRoundedButton(jButton6, blue, blueHover, white);
    styleRoundedButton(jButton4, blue, blueHover, white);
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

private void setupLiveSearch() {
    TableRowSorter<TableModel> sorter =
            new TableRowSorter<>(jTable1.getModel());

    jTable1.setRowSorter(sorter);

    jTextField1.getDocument().addDocumentListener(new DocumentListener() {

        private void search() {
            String text = jTextField1.getText().trim();

            if (text.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(
                    RowFilter.regexFilter(
                        "(?i)" + java.util.regex.Pattern.quote(text),
                        0, 1, 2
                    )
                );
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            search();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            search();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            search();
        }
    });
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
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
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
        jButton2.setText("TOOLS INVENTORY");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(this::jButton2ActionPerformed);
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 150, 50));

        jButton5.setBackground(new java.awt.Color(102, 102, 102));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("LOGOUT");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.addActionListener(this::jButton5ActionPerformed);
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 150, 50));

        jButton3.setBackground(new java.awt.Color(102, 102, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("ACOOUNTS");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(this::jButton3ActionPerformed);
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 150, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/sec.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 590));

        jLabel3.setFont(new java.awt.Font("Segoe UI Historic", 3, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("BARROW TOOLS");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 350, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/ICON.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT AN COURSE", "BHS NC ll", "BOOKEEPING NC ll", "CONSTRUCTION PAINTING NCll", "DRESSMAKING NC ll", "FASHION DESIGN NC ll", "FACILITATE s-LEARNING SESSIONS", "SMAW NC l", "SMAW NC ll", "TM LEVEL 1", "ECCDS NC lll", "EVENT MANAGEMENT SERVICES NC lll" }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 60, 300, 40));

        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 260, -1));

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
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 500, 440));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("QUANTITY:");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("RETURN DATE:");

        jLabel6.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("BARROW DATE:");

        jButton4.setBackground(new java.awt.Color(51, 51, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("CONFRIM BARROW");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jLabel9.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("BARROWER NAME:");

        jButton6.setBackground(new java.awt.Color(51, 51, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("RETURN");
        jButton6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 160, 260, 410));

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("<---- BARROW TOOL");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 130, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolsinventory/bgg.png"))); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 820, 590));

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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      int row = jTable1.getSelectedRow();

    if (row == -1) {
        showStyledMessage(
            "Please select a tool first.",
            "No Tool Selected",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String toolName = jTable1.getValueAt(row, 0).toString();
    String status = jTable1.getValueAt(row, 2).toString();

    String borrowerName = jTextField2.getText().trim();
    String quantityText = jTextField3.getText().trim();
    String borrowDate = jTextField4.getText().trim();
    String returnDate = jTextField5.getText().trim();

    if (borrowerName.isEmpty()) {
        showStyledMessage(
            "Please enter borrower name.",
            "Missing Borrower Name",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    if (quantityText.isEmpty()) {
        showStyledMessage(
            "Please enter quantity.",
            "Missing Quantity",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    if (borrowDate.isEmpty() || returnDate.isEmpty()) {
        showStyledMessage(
            "Please enter borrow date and return date.",
            "Missing Dates",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    java.text.SimpleDateFormat sdf =
        new java.text.SimpleDateFormat("MM/dd/yyyy");

    sdf.setLenient(false);

    java.util.Date parsedBorrowDate;
    java.util.Date parsedReturnDate;

    try {
        parsedBorrowDate = sdf.parse(borrowDate);
        parsedReturnDate = sdf.parse(returnDate);

        if (!sdf.format(parsedBorrowDate).equals(borrowDate)
                || !sdf.format(parsedReturnDate).equals(returnDate)) {
            showStyledMessage(
                "Invalid date format.\nUse MM/dd/yyyy (example: 10/11/2026).",
                "Invalid Date Format",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
    } catch (java.text.ParseException e) {
        showStyledMessage(
            "Invalid date.\nUse MM/dd/yyyy format (example: 10/11/2026).",
            "Invalid Date",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (parsedReturnDate.before(parsedBorrowDate)) {
        showStyledMessage(
            "Return date cannot be earlier than borrow date.",
            "Invalid Return Date",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int borrowQuantity;

    try {
        borrowQuantity = Integer.parseInt(quantityText);
    } catch (NumberFormatException e) {
        showStyledMessage(
            "Quantity must be a whole number.",
            "Invalid Quantity",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    if (borrowQuantity <= 0) {
        showStyledMessage(
            "Quantity must be greater than zero.",
            "Invalid Quantity",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int availableQuantity;

    try {
        availableQuantity = Integer.parseInt(
            jTable1.getValueAt(row, 1).toString().trim()
        );
    } catch (NumberFormatException e) {
        showStyledMessage(
            "Invalid quantity in table.",
            "Invalid Stock",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (borrowQuantity > availableQuantity) {
        showStyledMessage(
            "Not enough tools available!\n\n"
            + "Available: " + availableQuantity
            + "\nRequested: " + borrowQuantity,
            "Insufficient Quantity",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    if (status.equalsIgnoreCase("IN USE")) {
        showStyledMessage(
            "This tool is already marked IN USE.",
            "Tool Unavailable",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int confirm = showStyledConfirm(
        "Tool: " + toolName
        + "\nBorrower: " + borrowerName
        + "\nQuantity: " + borrowQuantity
        + "\nBorrow Date: " + borrowDate
        + "\nReturn Date: " + returnDate
        + "\n\nConfirm borrowing?",
        "Confirm Borrow"
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    File borrowFile = new File("borrow.csv");
    long oldFileLength = borrowFile.exists()
        ? borrowFile.length() : 0;

    BorrowCSV.addBorrow(
        borrowerName,
        toolName,
        borrowDate,
        returnDate
    );

    if (!borrowFile.exists()
            || borrowFile.length() <= oldFileLength) {
        showStyledMessage(
            "Unable to save borrow record.\nPlease check borrow.csv.",
            "Save Error",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    int newQuantity = availableQuantity - borrowQuantity;

    updateQuantity(toolName, newQuantity);

    String newStatus = newQuantity == 0 ? "IN USE" : "ACTIVE";

    ToolCSV.updateStatus(toolName, newStatus);

    jTable1.setValueAt(newQuantity, row, 1);
    jTable1.setValueAt(newStatus, row, 2);

    showStyledMessage(
        "Tool borrowed successfully!",
        "Borrow Successful",
        JOptionPane.INFORMATION_MESSAGE
    );

    jTextField2.setText("");
    jTextField3.setText("");
    jTextField4.setText("");
    jTextField5.setText("");

    jTable1.clearSelection();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      
    int row = jTable1.getSelectedRow();

    if (row == -1) {
        showStyledMessage(
            "Please select a tool first.",
            "No Tool Selected",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String toolName = jTable1.getValueAt(row, 0).toString();
    String status = jTable1.getValueAt(row, 2).toString();

    if (status.equalsIgnoreCase("ACTIVE")) {
        showStyledMessage(
            "This tool is not currently borrowed.",
            "Tool Not Borrowed",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String quantityText = jTextField3.getText().trim();

    if (quantityText.isEmpty()) {
        showStyledMessage(
            "Please enter return quantity.",
            "Missing Quantity",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int returnQuantity;

    try {
        returnQuantity = Integer.parseInt(quantityText);
    } catch (NumberFormatException e) {
        showStyledMessage(
            "Quantity must be a number.",
            "Invalid Quantity",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    if (returnQuantity <= 0) {
        showStyledMessage(
            "Quantity must be greater than 0.",
            "Invalid Quantity",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int currentQuantity;

    try {
        currentQuantity = Integer.parseInt(
            jTable1.getValueAt(row, 1).toString()
        );
    } catch (NumberFormatException e) {
        showStyledMessage(
            "Invalid quantity in table.",
            "Invalid Stock",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    int confirm = showStyledConfirm(
        "Tool: " + toolName
        + "\nReturn Quantity: " + returnQuantity
        + "\n\nConfirm return?",
        "Confirm Return"
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    int newQuantity = currentQuantity + returnQuantity;

    updateQuantity(toolName, newQuantity);

    ToolCSV.updateStatus(toolName, "ACTIVE");

    jTable1.setValueAt(newQuantity, row, 1);
    jTable1.setValueAt("ACTIVE", row, 2);

    showStyledMessage(
        "Tool returned successfully!",
        "Return Successful",
        JOptionPane.INFORMATION_MESSAGE
    );

    jTextField3.setText("");
    jTable1.clearSelection();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       Dashboard db = new Dashboard();
       db.show();
       this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       ALL t = new  ALL();
     t.show();
     this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
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
    }//GEN-LAST:event_jButton5ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new barrow().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
