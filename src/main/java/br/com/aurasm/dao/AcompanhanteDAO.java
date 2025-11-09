package br.com.aurasm.dao;

import br.com.aurasm.config.DatabaseConfig;
import br.com.aurasm.model.Acompanhante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcompanhanteDAO {

    public List<Acompanhante> listarTodos() {
        List<Acompanhante> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_ACOMPANHANTE ORDER BY CD_ACOMPANHANTE";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Acompanhante acompanhante = new Acompanhante();
                acompanhante.setCdAcompanhante(rs.getInt("CD_ACOMPANHANTE"));
                acompanhante.setCdPaciente(rs.getInt("CD_PACIENTE"));
                acompanhante.setNmAcompanhante(rs.getString("NM_ACOMPANHANTE"));
                acompanhante.setDsParentesco(rs.getString("DS_PARENTESCO"));
                lista.add(acompanhante);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar acompanhantes: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public Acompanhante buscarPorId(Integer id) {
        String sql = "SELECT * FROM T_ASM_ACOMPANHANTE WHERE CD_ACOMPANHANTE = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Acompanhante acompanhante = new Acompanhante();
                acompanhante.setCdAcompanhante(rs.getInt("CD_ACOMPANHANTE"));
                acompanhante.setCdPaciente(rs.getInt("CD_PACIENTE"));
                acompanhante.setNmAcompanhante(rs.getString("NM_ACOMPANHANTE"));
                acompanhante.setDsParentesco(rs.getString("DS_PARENTESCO"));
                return acompanhante;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar acompanhante: " + e.getMessage(), e);
        }
        
        return null;
    }

    public List<Acompanhante> buscarPorPaciente(Integer cdPaciente) {
        List<Acompanhante> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_ACOMPANHANTE WHERE CD_PACIENTE = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cdPaciente);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Acompanhante acompanhante = new Acompanhante();
                acompanhante.setCdAcompanhante(rs.getInt("CD_ACOMPANHANTE"));
                acompanhante.setCdPaciente(rs.getInt("CD_PACIENTE"));
                acompanhante.setNmAcompanhante(rs.getString("NM_ACOMPANHANTE"));
                acompanhante.setDsParentesco(rs.getString("DS_PARENTESCO"));
                lista.add(acompanhante);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar acompanhantes por paciente: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public Integer criar(Acompanhante acompanhante) {
        String sql = "INSERT INTO T_ASM_ACOMPANHANTE (CD_ACOMPANHANTE, CD_PACIENTE, NM_ACOMPANHANTE, DS_PARENTESCO) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            Integer proximoId = obterProximoId();
            
            stmt.setInt(1, proximoId);
            stmt.setInt(2, acompanhante.getCdPaciente());
            stmt.setString(3, acompanhante.getNmAcompanhante());
            stmt.setString(4, acompanhante.getDsParentesco());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                return proximoId;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar acompanhante: " + e.getMessage(), e);
        }
        
        return -1;
    }

    public void atualizar(Acompanhante acompanhante) {
        String sql = "UPDATE T_ASM_ACOMPANHANTE SET CD_PACIENTE = ?, NM_ACOMPANHANTE = ?, DS_PARENTESCO = ? " +
                     "WHERE CD_ACOMPANHANTE = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, acompanhante.getCdPaciente());
            stmt.setString(2, acompanhante.getNmAcompanhante());
            stmt.setString(3, acompanhante.getDsParentesco());
            stmt.setInt(4, acompanhante.getCdAcompanhante());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar acompanhante: " + e.getMessage(), e);
        }
    }

    public void deletar(Integer id) {
        String sql = "DELETE FROM T_ASM_ACOMPANHANTE WHERE CD_ACOMPANHANTE = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar acompanhante: " + e.getMessage(), e);
        }
    }

    private Integer obterProximoId() {
        String sql = "SELECT NVL(MAX(CD_ACOMPANHANTE), 0) + 1 AS PROXIMO_ID FROM T_ASM_ACOMPANHANTE";
        
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
