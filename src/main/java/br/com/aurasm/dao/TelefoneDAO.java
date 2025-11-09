package br.com.aurasm.dao;

import br.com.aurasm.config.DatabaseConfig;
import br.com.aurasm.model.Telefone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefoneDAO {

    public List<Telefone> listarTodos() {
        List<Telefone> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_TELEFONE ORDER BY CD_TELEFONE";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Telefone telefone = new Telefone();
                telefone.setCdTelefone(rs.getInt("CD_TELEFONE"));
                telefone.setCdPessoa(rs.getInt("CD_PESSOA"));
                telefone.setCdAcompanhante(rs.getInt("CD_ACOMPANHANTE"));
                telefone.setDdd(rs.getInt("DDD"));
                telefone.setNmTelefone(rs.getLong("NM_TELEFONE"));
                lista.add(telefone);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar telefones: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public List<Telefone> buscarPorPessoa(Integer cdPessoa) {
        List<Telefone> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_TELEFONE WHERE CD_PESSOA = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cdPessoa);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Telefone telefone = new Telefone();
                telefone.setCdTelefone(rs.getInt("CD_TELEFONE"));
                telefone.setCdPessoa(rs.getInt("CD_PESSOA"));
                telefone.setCdAcompanhante(rs.getInt("CD_ACOMPANHANTE"));
                telefone.setDdd(rs.getInt("DDD"));
                telefone.setNmTelefone(rs.getLong("NM_TELEFONE"));
                lista.add(telefone);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar telefones por pessoa: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public List<Telefone> buscarPorAcompanhante(Integer cdAcompanhante) {
        List<Telefone> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_TELEFONE WHERE CD_ACOMPANHANTE = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cdAcompanhante);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Telefone telefone = new Telefone();
                telefone.setCdTelefone(rs.getInt("CD_TELEFONE"));
                telefone.setCdPessoa(rs.getInt("CD_PESSOA"));
                telefone.setCdAcompanhante(rs.getInt("CD_ACOMPANHANTE"));
                telefone.setDdd(rs.getInt("DDD"));
                telefone.setNmTelefone(rs.getLong("NM_TELEFONE"));
                lista.add(telefone);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar telefones por acompanhante: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public Integer criar(Telefone telefone) {
        String sql = "INSERT INTO T_ASM_TELEFONE (CD_TELEFONE, CD_PESSOA, CD_ACOMPANHANTE, DDD, NM_TELEFONE) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            Integer proximoId = obterProximoId();
            
            stmt.setInt(1, proximoId);
            stmt.setInt(2, telefone.getCdPessoa());
            stmt.setInt(3, telefone.getCdAcompanhante());
            stmt.setInt(4, telefone.getDdd());
            stmt.setLong(5, telefone.getNmTelefone());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                return proximoId;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar telefone: " + e.getMessage(), e);
        }
        
        return -1;
    }

    public void atualizar(Telefone telefone) {
        String sql = "UPDATE T_ASM_TELEFONE SET CD_PESSOA = ?, CD_ACOMPANHANTE = ?, DDD = ?, NM_TELEFONE = ? " +
                     "WHERE CD_TELEFONE = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, telefone.getCdPessoa());
            stmt.setInt(2, telefone.getCdAcompanhante());
            stmt.setInt(3, telefone.getDdd());
            stmt.setLong(4, telefone.getNmTelefone());
            stmt.setInt(5, telefone.getCdTelefone());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar telefone: " + e.getMessage(), e);
        }
    }

    public void deletar(Integer id) {
        String sql = "DELETE FROM T_ASM_TELEFONE WHERE CD_TELEFONE = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar telefone: " + e.getMessage(), e);
        }
    }

    private Integer obterProximoId() {
        String sql = "SELECT NVL(MAX(CD_TELEFONE), 0) + 1 AS PROXIMO_ID FROM T_ASM_TELEFONE";
        
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
