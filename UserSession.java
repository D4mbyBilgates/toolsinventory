/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toolsinventory;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author obana
 */

public class UserSession {

   public static String username;
    public static String role;
    public static boolean welcomeShown = false;

    private static final Color DARK_BLUE = new Color(15, 45, 90);
    private static final Color BLUE = new Color(37, 99, 235);
    private static final Color HOVER_BLUE = new Color(29, 78, 216);
    private static final Color WHITE = Color.WHITE;

    public static void startSession(String user, String userRole) {
        username = user;
        role = userRole;
        welcomeShown = false;
    }

    public static String username() {
        return username;
    }

    public static String role() {
        return role;
    }

    public static boolean isAdmin() {
        return role != null && role.equalsIgnoreCase("ADMINISTRATOR");
    }

    public static boolean isStaff() {
        return role != null && role.equalsIgnoreCase("STAFF");
    }

    public static void logout() {
        username = null;
        role = null;
        welcomeShown = false;
    }

    public static void installExitConfirmation(JFrame frame) {
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = showStyledConfirm(
                    frame,
                    "Are you sure you want to exit the Tool Inventory System?\nYou will be logged out if you exit.",
                    "Confirm Exit"
                );

                if (choice == JOptionPane.YES_OPTION) {
                    UserSession.logout();
                    System.exit(0);
                }
            }
        });
    }

    public static void confirmLogout(JFrame currentFrame) {
        int choice = showStyledConfirm(
            currentFrame,
            "Are you sure you want to log out?",
            "Confirm Logout"
        );

        if (choice == JOptionPane.YES_OPTION) {
            logout();

            LOGIN login = new LOGIN();
            login.setLocationRelativeTo(null);
            login.setVisible(true);

            currentFrame.dispose();
        }
    }

    private static int showStyledConfirm(
            JFrame parent,
            String message,
            String title) {

        JDialog dialog = new JDialog(parent, title, true);
        dialog.setUndecorated(true);
        dialog.setSize(440, 250);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(
            new Color(190, 210, 245), 1
        ));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(new EmptyBorder(18, 22, 18, 22));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));

        JLabel iconLabel = new JLabel("?");
        iconLabel.setForeground(WHITE);
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 23));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(iconLabel, BorderLayout.EAST);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(WHITE);
        contentPanel.setBorder(new EmptyBorder(22, 24, 18, 24));

        JLabel messageLabel = new JLabel(
            "<html><div style='width:350px;'>" +
            message.replace("\n", "<br>") +
            "</div></html>"
        );
        messageLabel.setForeground(new Color(45, 55, 72));
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        contentPanel.add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(WHITE);

        JButton yesButton = createButton("YES", BLUE, WHITE);
        JButton noButton = createButton("NO", new Color(229, 231, 235),
                new Color(55, 65, 81));

        final int[] choice = {JOptionPane.NO_OPTION};

        yesButton.addActionListener(e -> {
            choice[0] = JOptionPane.YES_OPTION;
            dialog.dispose();
        });

        noButton.addActionListener(e -> {
            choice[0] = JOptionPane.NO_OPTION;
            dialog.dispose();
        });

        dialog.getRootPane().setDefaultButton(noButton);
        dialog.getRootPane().registerKeyboardAction(
            e -> dialog.dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        dialog.add(mainPanel);
        dialog.setVisible(true);

        return choice[0];
    }

    private static JButton createButton(
            String text,
            Color background,
            Color foreground) {

        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                Color color = getModel().isRollover()
                        ? (background.equals(BLUE) ? HOVER_BLUE
                        : new Color(209, 213, 219))
                        : background;

                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        button.setText(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 36));

        return button;
    }
}
