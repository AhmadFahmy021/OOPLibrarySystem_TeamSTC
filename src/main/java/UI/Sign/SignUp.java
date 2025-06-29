/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.java.UI.Sign;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author pakan
 */
public class SignUp extends javax.swing.JPanel {
    private final Perpus parentFrame;
        private JTextField[] inputFields;
        
        public SignUp(Perpus parent) {
            this.parentFrame = parent;
            setLayout(null);
            setOpaque(false);
            initComponents();
        }
        
        private void initComponents() {
            // Panel utama
            JPanel panel = new RoundedPanel(30);
            panel.setLayout(null);
            panel.setBounds(40, 40, 320, 450);
            panel.setBackground(Color.WHITE);
            add(panel);

            // Gradasi header dengan rounded corners
            GradientPanel header = new GradientPanel(30);
            header.setLayout(null);
            header.setBounds(0, 0, 320, 60);
            panel.add(header);

            JLabel title = new JLabel("Sign Up to Library", SwingConstants.CENTER);
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));
            title.setForeground(Color.WHITE);
            title.setBounds(0, 0, 320, 60);
            header.add(title);

            // Tabs
            JButton tabSignIn = new JButton("Sign In");
            JButton tabSignUp = new JButton("Sign Up");
            tabSignIn.setBounds(0, 60, 160, 30);
            tabSignUp.setBounds(160, 60, 160, 30);
            tabSignIn.setBackground(new Color(245, 245, 245));
            tabSignUp.setBackground(new Color(230, 230, 255));
            tabSignIn.setBorder(null);
            tabSignUp.setBorder(null);
            tabSignIn.setFocusPainted(false);
            tabSignUp.setFocusPainted(false);
            panel.add(tabSignIn);
            panel.add(tabSignUp);
            
            // Tab actions
            tabSignIn.addActionListener(e -> parentFrame.showSignIn());

            // Input fields
            String[] labels = {"ID", "Full Name", "Major", "Email"};
            inputFields = new JTextField[labels.length];

            int y = 100;
            for (int i = 0; i < labels.length; i++) {
                JLabel lbl = new JLabel(labels[i]);
                lbl.setBounds(30, y, 260, 20);
                lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                lbl.setForeground(new Color(80, 80, 80));
                panel.add(lbl);

                JTextField tf = new RoundedTextField(15);
                tf.setBounds(30, y + 20, 260, 35);
                inputFields[i] = tf;
                panel.add(tf);

                y += 60;
            }
            
            // Add Enter key navigation
            for (int i = 0; i < inputFields.length - 1; i++) {
                final int nextIndex = i + 1;
                inputFields[i].addActionListener(e -> inputFields[nextIndex].requestFocus());
            }
            
            // Last field triggers sign up
            inputFields[inputFields.length - 1].addActionListener(e -> signUp());

            // Button
            RoundedButton signUpBtn = new RoundedButton("Sign Up", 25);
            signUpBtn.setBounds(80, 380, 160, 40);
            panel.add(signUpBtn);
            
            // Sign up action
            signUpBtn.addActionListener(e -> signUp());
        }
        
        private void signUp() {
            String[] labels = {"ID", "Full Name", "Major", "Email"};
            StringBuilder message = new StringBuilder("Registration successful!\n\nYour data:\n");
            
            // Validate all fields
            for (int i = 0; i < inputFields.length; i++) {
                String value = inputFields[i].getText().trim();
                if (value.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Please fill the " + labels[i] + " field!", 
                        "Registration Failed", 
                        JOptionPane.WARNING_MESSAGE);
                    inputFields[i].requestFocus();
                    return;
                }
            }
            
            // Additional validation for email
            String email = inputFields[3].getText().trim();
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid email address!", 
                    "Registration Failed", 
                    JOptionPane.WARNING_MESSAGE);
                inputFields[3].requestFocus();
                return;
            }
            
            // Build success message
            for (int i = 0; i < inputFields.length; i++) {
                message.append("• ").append(labels[i]).append(": ").append(inputFields[i].getText().trim()).append("\n");
            }
            
            // Show success message
            int result = JOptionPane.showConfirmDialog(this, 
                message.toString() + "\nProceed to Sign In?", 
                "Registration Successful", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields
            for (JTextField field : inputFields) {
                field.setText("");
            }
            
            // Switch to sign in if user confirms
            if (result == JOptionPane.YES_OPTION) {
                parentFrame.showSignIn();
            }
        }
        
        private boolean isValidEmail(String email) {
            return email.contains("@") && email.contains(".") && email.length() > 5;
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

        // Gradient panel for header
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
