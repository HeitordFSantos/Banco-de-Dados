"""

                            Online Python Compiler.
                Code, Compile, Run and Debug python program online.
Write your code in this editor and press "Run" button to execute it.

"""
import sqlite3

# Conectar ao banco de dados (ou criar um novo)
conn = sqlite3.connect('gestao_frota.db')
cursor = conn.cursor()

# Função para criar tabelas
def criar_tabelas():
    cursor.execute('''CREATE TABLE IF NOT EXISTS Motoristas (
                        CodigoMotorista INTEGER PRIMARY KEY,
                        Nome TEXT NOT NULL,
                        Telefone TEXT,
                        Endereco TEXT)''')

    cursor.execute('''CREATE TABLE IF NOT EXISTS Veiculos (
                        PlacaCarro TEXT PRIMARY KEY,
                        Modelo TEXT NOT NULL,
                        Marca TEXT NOT NULL,
                        Ano INTEGER NOT NULL,
                        CodigoMotorista INTEGER,
                        FOREIGN KEY (CodigoMotorista) REFERENCES Motoristas(CodigoMotorista))''')

# Função para inserir dados na tabela Motoristas
def inserir_motorista(codigo_motorista, nome, telefone, endereco):
    cursor.execute('''INSERT INTO Motoristas (CodigoMotorista, Nome, Telefone, Endereco)
                      VALUES (?, ?, ?, ?)''', (codigo_motorista, nome, telefone, endereco))
    conn.commit()

# Função para inserir dados na tabela Veiculos
def inserir_veiculo(placa_carro, modelo, marca, ano, codigo_motorista):
    cursor.execute('''INSERT INTO Veiculos (PlacaCarro, Modelo, Marca, Ano, CodigoMotorista)
                      VALUES (?, ?, ?, ?, ?)''', (placa_carro, modelo, marca, ano, codigo_motorista))
    conn.commit()

# Função principal para executar a inserção
def main():
    criar_tabelas()

    # Inserir dados
    inserir_motorista(1, 'João Silva', '123456789', 'Rua A, 123')
    inserir_veiculo('ABC1234', 'Fusca', 'Volkswagen', 1980, 1)

    print('Dados inseridos com sucesso!')

# Fechar conexão
def fechar_conexao():
    cursor.close()
    conn.close()

if __name__ == "__main__":
    main()
    fechar_conexao()