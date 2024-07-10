package com.example.servico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial {
    private JFrame frame;
    private JButton btnEntrar;

    public TelaInicial() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Tela Inicial");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bem-vindo ao Aplicativo de Filmes", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.getContentPane().add(welcomeLabel, BorderLayout.CENTER);

        btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entrar();
            }
        });
        frame.getContentPane().add(btnEntrar, BorderLayout.SOUTH);
    }

    public void show() {
        frame.setVisible(true);
    }

    private void entrar() {
        frame.dispose(); // Fecha a tela inicial
        JanelaPrincipal mainApp = new JanelaPrincipal();
        mainApp.show(); // Exibe a tela principal
    }
}
