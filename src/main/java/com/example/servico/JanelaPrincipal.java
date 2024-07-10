package com.example.servico;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class JanelaPrincipal {
    private JFrame frame;
    private JTextField textField;
    private JTextArea textArea;
    private JLabel posterLabel;
    private JList<String> movieList;
    private DefaultListModel<String> listModel;
    private Filmes filmeService; // Importante: Certifique-se de que o nome da classe é `Filmes` e não `FilmeService`

    public JanelaPrincipal() {
        filmeService = new Filmes(); // Inicializa o serviço de filmes
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Informações do Filme");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        posterLabel = new JLabel();
        frame.getContentPane().add(posterLabel, BorderLayout.WEST);

        // Painel para entrada de texto e botão de busca
        JPanel inputPanel = new JPanel();
        JLabel lblNomeFilme = new JLabel("Nome do Filme:");
        textField = new JTextField(20);
        inputPanel.add(lblNomeFilme);
        inputPanel.add(textField);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarFilmes();
            }
        });
        inputPanel.add(btnBuscar);

        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);

        // Área de exibição de informações e lista de resultados
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Lista de resultados
        listModel = new DefaultListModel<>();
        movieList = new JList<>(listModel);
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedMovie = movieList.getSelectedValue();
                    if (selectedMovie != null) {
                        exibirFilmeSelecionado(selectedMovie);
                    }
                }
            }
        });
        JScrollPane listScrollPane = new JScrollPane(movieList);
        contentPanel.add(listScrollPane, BorderLayout.WEST);

        // Área de exibição de informações
        textArea = new JTextArea(15, 40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane textScrollPane = new JScrollPane(textArea);
        contentPanel.add(textScrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
    }

    public void show() {
        frame.setVisible(true);
    }

    private void buscarFilmes() {
        String nomeFilme = textField.getText().trim().replaceAll("\\s+", " ");
        try {
            List<EnderecoDto> filmes = filmeService.buscarFilmes(nomeFilme);
            if (filmes != null && !filmes.isEmpty()) {
                listModel.clear();
                for (EnderecoDto filme : filmes) {
                    listModel.addElement(filme.getTitle());
                }
            } else {
                textArea.setText("Nenhum resultado encontrado.");
                listModel.clear();
            }
        } catch (Exception ex) {
            textArea.setText("Ocorreu um erro ao buscar os filmes.");
            listModel.clear();
        }
    }

    private void exibirFilmeSelecionado(String selectedMovie) {
        try {
            EnderecoDto filmeSelecionado = filmeService.buscarDetalhesFilme(selectedMovie);
            if (filmeSelecionado != null) {
                textArea.setText(filmeSelecionado.toString());
                exibirPoster(filmeSelecionado.getPoster());
            } else {
                textArea.setText("Detalhes do filme não encontrados.");
                posterLabel.setIcon(null); // Limpa o poster se nenhum resultado for encontrado
            }
        } catch (Exception ex) {
            textArea.setText("Ocorreu um erro ao buscar os detalhes do filme.");
            posterLabel.setIcon(null); // Limpa o poster em caso de erro
        }
    }

    private void exibirPoster(String posterUrl) {
        if (posterUrl != null && !posterUrl.isEmpty()) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(posterUrl)).build();
                HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

                InputStream posterStream = response.body();
                byte[] posterBytes = posterStream.readAllBytes();
                ImageIcon icon = new ImageIcon(posterBytes);
                Image scaledImage = icon.getImage().getScaledInstance(150, 225, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(scaledImage));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                posterLabel.setIcon(null); // Limpa o poster em caso de erro
            }
        } else {
            posterLabel.setIcon(null); // Limpa o poster se a URL estiver vazia
        }
    }
}
