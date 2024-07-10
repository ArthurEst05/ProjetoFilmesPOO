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

public class TelaCadastro {
    private JFrame frame;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnCadastrar;

    public TelaCadastro() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Cadastro de Usuário");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas a janela de cadastro
        frame.getContentPane().setLayout(new BorderLayout());

        // Painel para os campos de cadastro
        JPanel cadastroPanel = new JPanel();
        cadastroPanel.setLayout(new GridLayout(4, 2, 10, 10));
        cadastroPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();
        JLabel lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField();
        btnCadastrar = new JButton("Cadastrar");

        cadastroPanel.add(lblNome);
        cadastroPanel.add(txtNome);
        cadastroPanel.add(lblEmail);
        cadastroPanel.add(txtEmail);
        cadastroPanel.add(lblSenha);
        cadastroPanel.add(txtSenha);
        cadastroPanel.add(new JLabel()); // Espaço vazio para alinhamento
        cadastroPanel.add(btnCadastrar);

        frame.getContentPane().add(cadastroPanel, BorderLayout.CENTER);

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    private void cadastrarUsuario() {
    String nome = txtNome.getText();
    String email = txtEmail.getText();
    String senha = new String(txtSenha.getPassword());

    UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
    Usuario novoUsuario = new Usuario(null, nome, email, senha);

    try {
        usuarioDao.insert(novoUsuario);
        JOptionPane.showMessageDialog(frame, "Usuário cadastrado com sucesso.", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose(); // Fecha a tela de cadastro após o sucesso
        // Pode adicionar lógica para abrir a tela principal ou fazer login automaticamente
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "Erro ao cadastrar usuário.", "Cadastro", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    }
}
