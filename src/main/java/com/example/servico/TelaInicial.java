package com.example.servico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import entites.Usuario;
import modelDao.DaoFactory;
import modelDao.UsuarioDao;

public class TelaInicial {
    private JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnEntrar;
    private JButton btnCadastrar;
    private Image backgroundImage;

    public TelaInicial() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        backgroundImage = new ImageIcon("C:\\Users\\arthur\\Documents\\ProjetoFilmesPOO\\ProjetoFilmesPOO\\image\\telainicial.png").getImage();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Flick Review");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Maximiza a janela ao iniciar
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.getContentPane().setLayout(new BorderLayout());

        // Painel para os campos de login
        JPanel loginPanel = new BackgroundPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);

        JLabel lblUsername = new JLabel("Nome de usuário:");
        lblUsername.setFont(new Font("Melted Monster", Font.PLAIN, 30));
        lblUsername.setForeground(Color.red); // Define a cor da fonte para branco
        txtUsername = new JTextField(25); // Limita o tamanho da caixa de texto

        JLabel lblPassword = new JLabel("Senha:");
        lblPassword.setFont(new Font("Melted Monster", Font.PLAIN, 30));
        lblPassword.setForeground(Color.red); // Define a cor da fonte para branco
        txtPassword = new JPasswordField(25); // Limita o tamanho da caixa de texto

        btnCadastrar = new JButton("Cadastrar");
        btnEntrar = new JButton("Entrar");

        btnCadastrar.setIcon(new ImageIcon("path/to/register_icon.png"));
        btnEntrar.setIcon(new ImageIcon("path/to/login_icon.png"));

        btnCadastrar.setBackground(new Color(0, 0, 0));
        btnCadastrar.setForeground(new Color(255,63,63));
        btnCadastrar.setFont(new Font("Melted Monster", Font.BOLD, 30));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        btnEntrar.setBackground(new Color(255, 63, 63));
        btnEntrar.setForeground(new Color(0,0,0));
        btnEntrar.setFont(new Font("Melted Monster", Font.BOLD, 30));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(lblUsername, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(btnEntrar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(btnCadastrar, gbc);

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
            new JanelaPrincipal(); // Inicia e exibe a tela principal
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

    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaInicial().show();
            }
        });
    }
}
