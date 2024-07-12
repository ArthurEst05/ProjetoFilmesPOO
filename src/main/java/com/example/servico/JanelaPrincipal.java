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
    private Filmes filmeService;

    public JanelaPrincipal() {
        filmeService = new Filmes();
        initialize();
    }

    private void initialize() {
        // Configurações do tema dark
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("control", new Color(128, 128, 128));
            UIManager.put("info", new Color(128, 128, 128));
            UIManager.put("nimbusBase", new Color(18, 30, 49));
            UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
            UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
            UIManager.put("nimbusFocus", new Color(115, 164, 209));
            UIManager.put("nimbusGreen", new Color(176, 179, 50));
            UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
            UIManager.put("nimbusLightBackground", new Color(18, 30, 49));
            UIManager.put("nimbusOrange", new Color(191, 98, 4));
            UIManager.put("nimbusRed", new Color(169, 46, 34));
            UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
            UIManager.put("text", new Color(230, 230, 230));
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Informações do Filme");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza a janela ao iniciar
        frame.getContentPane().setLayout(new BorderLayout());

        // Painel para entrada de texto e botão de busca
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblNomeFilme = new JLabel("Nome do Filme:");
        lblNomeFilme.setFont(new Font("Arial", Font.BOLD, 14));
        lblNomeFilme.setForeground(Color.WHITE); // Define a cor do texto para branco
        textField = new JTextField(30);
        textField.setBackground(new Color(30, 30, 30)); // Cor de fundo escura
        textField.setForeground(Color.WHITE); // Cor do texto para branco

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarFilmes();
            }
        });
        btnBuscar.setBackground(new Color(50, 50, 50)); // Cor de fundo escura
        btnBuscar.setForeground(Color.WHITE); // Cor do texto para branco

        inputPanel.setBackground(new Color(30, 30, 30)); // Cor de fundo escura
        inputPanel.add(lblNomeFilme);
        inputPanel.add(textField);
        inputPanel.add(btnBuscar);

        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);

        // Painel principal para a lista de filmes e detalhes
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(18, 30, 49)); // Cor de fundo escura
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Lista de resultados de filmes
        listModel = new DefaultListModel<>();
        movieList = new JList<>(listModel);
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieList.setForeground(Color.WHITE); // Cor do texto para branco
        movieList.setBackground(new Color(30, 30, 30)); // Cor de fundo escura
        movieList.setSelectionBackground(new Color(104, 93, 156)); // Cor de fundo da seleção
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
        listScrollPane.setPreferredSize(new Dimension(200, 0));
        listScrollPane.setBackground(new Color(30, 30, 30)); // Cor de fundo escura
        mainPanel.add(listScrollPane, BorderLayout.WEST);

        // Painel de detalhes do filme
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(new Color(18, 30, 49)); // Cor de fundo escura
        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Painel para o poster do filme
        posterLabel = new JLabel();
        posterLabel.setHorizontalAlignment(JLabel.CENTER);
        posterLabel.setVerticalAlignment(JLabel.TOP);
        posterLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        posterLabel.setBackground(new Color(18, 30, 49)); // Cor de fundo escura
        detailsPanel.add(posterLabel, BorderLayout.WEST);

        // Área de texto para exibir informações do filme
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(new Color(18, 30, 49)); // Cor de fundo escura
        textArea.setForeground(Color.WHITE); // Cor do texto para branco
        JScrollPane textScrollPane = new JScrollPane(textArea);
        detailsPanel.add(textScrollPane, BorderLayout.CENTER);

        // Mostrar a janela
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
                posterLabel.setIcon(null);
            }
        } catch (Exception ex) {
            textArea.setText("Ocorreu um erro ao buscar os detalhes do filme.");
            posterLabel.setIcon(null);
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
                Image scaledImage = icon.getImage().getScaledInstance(300, 450, Image.SCALE_SMOOTH); // Tamanho ajustado da imagem
                posterLabel.setIcon(new ImageIcon(scaledImage));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                posterLabel.setIcon(null);
            }
        } else {
            posterLabel.setIcon(null);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JanelaPrincipal();
            }
        });
    }
}
