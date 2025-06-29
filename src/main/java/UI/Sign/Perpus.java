/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main.java.UI.Sign;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 *
 * @author pakan
 */
public class Perpus extends javax.swing.JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final SignIn signInPanel;
    private final SignUp signUpPanel;
    
    // Constants for card names
    private static final String SIGNIN_CARD = "SignIn";
    private static final String SIGNUP_CARD = "SignUp";
    
    public Perpus() {
        setTitle("Library Management System");
        setSize(400, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        // Set background color
        getContentPane().setBackground(new Color(90, 110, 255));
        
        // Initialize CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        
        // Create panels
        signInPanel = new SignIn(this);
        signUpPanel = new SignUp(this);
        
        // Add panels to CardLayout
        cardPanel.add(signInPanel, SIGNIN_CARD);
        cardPanel.add(signUpPanel, SIGNUP_CARD);
        
        add(cardPanel);
        
        setVisible(true);
    }
    
    public void showSignIn() {
        cardLayout.show(cardPanel, SIGNIN_CARD);
    }
    
    public void showSignUp() {
        cardLayout.show(cardPanel, SIGNUP_CARD);
    }
    
    public static void main(String[] args) {
        // Set system look and feel
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
        // Create and run the application
        SwingUtilities.invokeLater(() -> {
            Perpus app = new Perpus();
            app.showSignIn();
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
