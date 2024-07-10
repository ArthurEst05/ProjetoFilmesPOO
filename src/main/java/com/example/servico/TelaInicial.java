package com.example.servico;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entites.Usuario;
import modelDao.DaoFactory;
import modelDao.UsuarioDao;

public class TelaInicial {
    private JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnEntrar;
    private JButton btnCadastrar;

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
        btnCadastrar = new JButton("Cadastrar");

        loginPanel.add(lblUsername);
        loginPanel.add(txtUsername);
        loginPanel.add(lblPassword);
        loginPanel.add(txtPassword);
        loginPanel.add(btnCadastrar);
        loginPanel.add(btnEntrar);

        frame.getContentPane().add(loginPanel, BorderLayout.CENTER);

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entrar();
            }
        });

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastro();
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

    private void abrirTelaCadastro() {
        frame.dispose(); // Fecha a tela inicial
        TelaCadastro telaCadastro = new TelaCadastro();
        telaCadastro.show(); // Exibe a tela de cadastro
    }

    private boolean autenticar(String username, String password) {
    UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
    Usuario usuario = usuarioDao.findByUsernameAndPassword(username, password);

    return usuario != null;
}

}
