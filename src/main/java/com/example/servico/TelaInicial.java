package com.example.servico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial {
    private JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnEntrar;

    public TelaInicial() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Tela Inicial");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Painel para os campos de login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblUsername = new JLabel("Nome de usuário:");
        txtUsername = new JTextField();
        JLabel lblPassword = new JLabel("Senha:");
        txtPassword = new JPasswordField();
        btnEntrar = new JButton("Entrar");

        loginPanel.add(lblUsername);
        loginPanel.add(txtUsername);
        loginPanel.add(lblPassword);
        loginPanel.add(txtPassword);
        loginPanel.add(new JLabel()); // Espaço vazio para alinhamento
        loginPanel.add(btnEntrar);

        frame.getContentPane().add(loginPanel, BorderLayout.CENTER);

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entrar();
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    private void entrar() {
        String username = txtUsername.getText();
        char[] password = txtPassword.getPassword();

        if (autenticar(username, new String(password))) {
            frame.dispose(); // Fecha a tela inicial
            JanelaPrincipal mainApp = new JanelaPrincipal();
            mainApp.show(); // Exibe a tela principal
        } else {
            JOptionPane.showMessageDialog(frame, "Nome de usuário ou senha incorretos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean autenticar(String username, String password) {
        // Aqui você pode adicionar a lógica de autenticação real, como verificar em um banco de dados.
        // Por enquanto, vamos usar um exemplo simples:
        return "admin".equals(username) && "password".equals(password);
    }
}
