package br.com.aurasm.dao;

import br.com.aurasm.config.DatabaseConfig;
import br.com.aurasm.model.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    public List<Pessoa> listarTodos() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_PESSOA ORDER BY CD_PESSOA";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setCdPessoa(rs.getInt("CD_PESSOA"));
                pessoa.setTpPessoa(rs.getString("TP_PESSOA"));
                pessoa.setNmPessoa(rs.getString("NM_PESSOA"));
                pessoa.setDtNascimento(rs.getDate("DT_NASCIMENTO"));
                pessoa.setNrCpf(rs.getString("NR_CPF"));
                lista.add(pessoa);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pessoas: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public Pessoa buscarPorId(Integer id) {
        String sql = "SELECT * FROM T_ASM_PESSOA WHERE CD_PESSOA = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setCdPessoa(rs.getInt("CD_PESSOA"));
                pessoa.setTpPessoa(rs.getString("TP_PESSOA"));
                pessoa.setNmPessoa(rs.getString("NM_PESSOA"));
                pessoa.setDtNascimento(rs.getDate("DT_NASCIMENTO"));
                pessoa.setNrCpf(rs.getString("NR_CPF"));
                return pessoa;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoa: " + e.getMessage(), e);
        }
        
        return null;
    }

    public Pessoa buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM T_ASM_PESSOA WHERE NR_CPF = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setCdPessoa(rs.getInt("CD_PESSOA"));
                pessoa.setTpPessoa(rs.getString("TP_PESSOA"));
                pessoa.setNmPessoa(rs.getString("NM_PESSOA"));
                pessoa.setDtNascimento(rs.getDate("DT_NASCIMENTO"));
                pessoa.setNrCpf(rs.getString("NR_CPF"));
                return pessoa;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoa por CPF: " + e.getMessage(), e);
        }
        
        return null;
    }

    public Integer criar(Pessoa pessoa) {
        String sql = "INSERT INTO T_ASM_PESSOA (CD_PESSOA, TP_PESSOA, NM_PESSOA, DT_NASCIMENTO, NR_CPF) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Gerar próximo ID manualmente (sem sequence)
            Integer proximoId = obterProximoId();
            
            stmt.setInt(1, proximoId);
            stmt.setString(2, pessoa.getTpPessoa());
            stmt.setString(3, pessoa.getNmPessoa());
            stmt.setDate(4, new java.sql.Date(pessoa.getDtNascimento().getTime()));
            stmt.setString(5, pessoa.getNrCpf());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                return proximoId;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar pessoa: " + e.getMessage(), e);
        }
        
        return -1;
    }

    public void atualizar(Pessoa pessoa) {
        String sql = "UPDATE T_ASM_PESSOA SET TP_PESSOA = ?, NM_PESSOA = ?, DT_NASCIMENTO = ?, NR_CPF = ? " +
                     "WHERE CD_PESSOA = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, pessoa.getTpPessoa());
            stmt.setString(2, pessoa.getNmPessoa());
            stmt.setDate(3, new java.sql.Date(pessoa.getDtNascimento().getTime()));
            stmt.setString(4, pessoa.getNrCpf());
            stmt.setInt(5, pessoa.getCdPessoa());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pessoa: " + e.getMessage(), e);
        }
    }

    public void deletar(Integer id) {
        String sql = "DELETE FROM T_ASM_PESSOA WHERE CD_PESSOA = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pessoa: " + e.getMessage(), e);
        }
    }

    private Integer obterProximoId() {
        String sql = "SELECT NVL(MAX(CD_PESSOA), 0) + 1 AS PROXIMO_ID FROM T_ASM_PESSOA";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("PROXIMO_ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter próximo ID: " + e.getMessage(), e);
        }
        
        return 1;
    }
}
