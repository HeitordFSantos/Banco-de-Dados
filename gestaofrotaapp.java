import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestaoFrotaApp extends JFrame {
    private static final String DB_URL = "jdbc:sqlite:gestao_frota.db";
    private JTextField nomeField, telefoneField, enderecoField;
    private JTextField placaField, modeloField, marcaField, anoField;
    private JButton inserirMotoristaBtn, inserirVeiculoBtn;
    
    public GestaoFrotaApp() {
        setTitle("Gestão de Frota");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Painel para motoristas
        JPanel motoristaPanel = new JPanel();
        motoristaPanel.setLayout(new GridLayout(4, 2));
        motoristaPanel.setBorder(BorderFactory.createTitledBorder("Inserir Motorista"));

        motoristaPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        motoristaPanel.add(nomeField);

        motoristaPanel.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        motoristaPanel.add(telefoneField);

        motoristaPanel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        motoristaPanel.add(enderecoField);

        inserirMotoristaBtn = new JButton("Inserir Motorista");
        motoristaPanel.add(inserirMotoristaBtn);
        add(motoristaPanel);

        // Painel para veículos
        JPanel veiculoPanel = new JPanel();
        veiculoPanel.setLayout(new GridLayout(5, 2));
        veiculoPanel.setBorder(BorderFactory.createTitledBorder("Inserir Veículo"));

        veiculoPanel.add(new JLabel("Placa:"));
        placaField = new JTextField();
        veiculoPanel.add(placaField);

        veiculoPanel.add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        veiculoPanel.add(modeloField);

        veiculoPanel.add(new JLabel("Marca:"));
        marcaField = new JTextField();
        veiculoPanel.add(marcaField);

        veiculoPanel.add(new JLabel("Ano:"));
        anoField = new JTextField();
        veiculoPanel.add(anoField);

        inserirVeiculoBtn = new JButton("Inserir Veículo");
        veiculoPanel.add(inserirVeiculoBtn);
        add(veiculoPanel);

        // Adiciona listeners
        inserirMotoristaBtn.addActionListener(new InserirMotoristaListener());
        inserirVeiculoBtn.addActionListener(new InserirVeiculoListener());
    }

    private class InserirMotoristaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nome = nomeField.getText();
            String telefone = telefoneField.getText();
            String endereco = enderecoField.getText();

            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO Motoristas (Nome, Telefone, Endereco) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nome);
                    pstmt.setString(2, telefone);
                    pstmt.setString(3, endereco);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(GestaoFrotaApp.this, "Motorista inserido com sucesso!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(GestaoFrotaApp.this, "Erro ao inserir motorista: " + ex.getMessage());
            }
        }
    }

    private class InserirVeiculoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String placa = placaField.getText();
            String modelo = modeloField.getText();
            String marca = marcaField.getText();
            int ano;

            try {
                ano = Integer.parseInt(anoField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GestaoFrotaApp.this, "Ano inválido.");
                return;
            }

            // Aqui assumimos que o código do motorista é 1 para simplicidade
            int codigoMotorista = 1;

            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO Veiculos (PlacaCarro, Modelo, Marca, Ano, CodigoMotorista) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, placa);
                    pstmt.setString(2, modelo);
                    pstmt.setString(3, marca);
                    pstmt.setInt(4, ano);
                    pstmt.setInt(5, codigoMotorista);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(GestaoFrotaApp.this, "Veículo inserido com sucesso!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(GestaoFrotaApp.this, "Erro ao inserir veículo: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GestaoFrotaApp().setVisible(true);
        });
    }
}
