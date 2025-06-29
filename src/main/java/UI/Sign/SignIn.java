/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.java.UI.Sign;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author pakan
 */
public class SignIn extends javax.swing.JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private final Perpus parentFrame;
    
    public SignIn(Perpus parent) {
        this.parentFrame = parent;
        setLayout(null);
        setOpaque(false);
        initComponents();
    }
    
    private void initComponents() {
        // Panel container
        JPanel panel = new RoundedPanel(30);
        panel.setLayout(null);
        panel.setBounds(40, 50, 320, 330);
        panel.setBackground(Color.WHITE);
        add(panel);

        GradientPanel header = new GradientPanel(30);
        header.setLayout(null);
        header.setBounds(0, 0, 320, 60);
        panel.add(header);

        JLabel title = new JLabel("Sign In to Library", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 10, 320, 30);
        header.add(title);

        // Tabs
        JButton tabSignIn = new JButton("Sign In");
        JButton tabSignUp = new JButton("Sign Up");
        tabSignIn.setBounds(0, 50, 160, 30);
        tabSignUp.setBounds(160, 50, 160, 30);
        tabSignIn.setBackground(new Color(230, 230, 255));
        tabSignUp.setBackground(new Color(245, 245, 245));
        tabSignIn.setBorder(null);
        tabSignUp.setBorder(null);
        tabSignIn.setFocusPainted(false);
        tabSignUp.setFocusPainted(false);
        panel.add(tabSignIn);
        panel.add(tabSignUp);
        
        // Tab actions
        tabSignUp.addActionListener(e -> parentFrame.showSignUp());

        JLabel emailLabel = new JLabel("Email address:");
        emailLabel.setBounds(30, 90, 260, 20);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        emailLabel.setForeground(new Color(80, 80, 80));
        panel.add(emailLabel);

        emailField = new RoundedTextField(20);
        emailField.setBounds(30, 110, 260, 35);
        panel.add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 155, 260, 20);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passLabel.setForeground(new Color(80, 80, 80));
        panel.add(passLabel);

        passwordField = new RoundedPasswordField(20);
        passwordField.setBounds(30, 175, 260, 35);
        panel.add(passwordField);

        RoundedButton loginButton = new RoundedButton("Sign In", 25);
        loginButton.setBounds(80, 230, 160, 40);
        panel.add(loginButton);

        JLabel forgotLabel = new JLabel("Forgot Password ?", SwingConstants.CENTER);
        forgotLabel.setBounds(0, 280, 320, 20);
        forgotLabel.setForeground(new Color(80, 80, 255));
        forgotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(forgotLabel);

        // Action
        loginButton.addActionListener(e -> login());
        
        // Add Enter key support
        emailField.addActionListener(e -> passwordField.requestFocus());
        passwordField.addActionListener(e -> login());
    }
    
    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Login Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (email.equals("admin@example.com") && password.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Clear fields
            emailField.setText("");
            passwordField.setText("");
            // TODO: Open dashboard
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText(""); // Clear password field
        }
    }
    
    // Rounded panel
    class RoundedPanel extends JPanel {
        protected int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
        }
    }

    class GradientPanel extends JPanel {
        private final int radius;
        
        public GradientPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Membuat gradient
            GradientPaint gp = new GradientPaint(
                0, 0, new Color(40, 80, 255),
                getWidth(), getHeight(), new Color(100, 140, 255)
            );
            g2.setPaint(gp);
            
            // Menggambar rounded rectangle dengan gradient
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.fillRect(0, radius, getWidth(), getHeight() - radius); // menutup sudut bawah agar tajam
            g2.dispose();
        }
    }
    
    // Rounded text field
    class RoundedTextField extends JTextField {
        private final int radius;

        public RoundedTextField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Background putih
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            // Border abu-abu
            g2.setColor(new Color(200, 200, 200));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            
            super.paintComponent(g);
            g2.dispose();
        }
    }

    // Rounded password field
    class RoundedPasswordField extends JPasswordField {
        private final int radius;

        public RoundedPasswordField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setColor(new Color(200, 200, 200));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Rounded button
    class RoundedButton extends JButton {
        private final int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setBackground(new Color(0, 50, 255));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            super.paintComponent(g);
            g2.dispose();
        }
    }
    /*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
