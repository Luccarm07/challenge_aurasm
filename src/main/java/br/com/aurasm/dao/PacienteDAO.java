package br.com.aurasm.dao;

import br.com.aurasm.config.DatabaseConfig;
import br.com.aurasm.model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_PACIENTE ORDER BY CD_PACIENTE";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setCdPaciente(rs.getInt("CD_PACIENTE"));
                paciente.setCdPessoa(rs.getInt("CD_PESSOA"));
                paciente.setNmPlanoSaude(rs.getString("NM_PLANO_SAUDE"));
                lista.add(paciente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pacientes: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public Paciente buscarPorId(Integer id) {
        String sql = "SELECT * FROM T_ASM_PACIENTE WHERE CD_PACIENTE = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setCdPaciente(rs.getInt("CD_PACIENTE"));
                paciente.setCdPessoa(rs.getInt("CD_PESSOA"));
                paciente.setNmPlanoSaude(rs.getString("NM_PLANO_SAUDE"));
                return paciente;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente: " + e.getMessage(), e);
        }
        
        return null;
    }

    public Paciente buscarPorPessoa(Integer cdPessoa) {
        String sql = "SELECT * FROM T_ASM_PACIENTE WHERE CD_PESSOA = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cdPessoa);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setCdPaciente(rs.getInt("CD_PACIENTE"));
                paciente.setCdPessoa(rs.getInt("CD_PESSOA"));
                paciente.setNmPlanoSaude(rs.getString("NM_PLANO_SAUDE"));
                return paciente;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente por pessoa: " + e.getMessage(), e);
        }
        
        return null;
    }

    public Integer criar(Paciente paciente) {
        String sql = "INSERT INTO T_ASM_PACIENTE (CD_PACIENTE, CD_PESSOA, NM_PLANO_SAUDE) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            Integer proximoId = obterProximoId();
            
            stmt.setInt(1, proximoId);
            stmt.setInt(2, paciente.getCdPessoa());
            stmt.setString(3, paciente.getNmPlanoSaude());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                return proximoId;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar paciente: " + e.getMessage(), e);
        }
        
        return -1;
    }

    public void atualizar(Paciente paciente) {
        String sql = "UPDATE T_ASM_PACIENTE SET CD_PESSOA = ?, NM_PLANO_SAUDE = ? WHERE CD_PACIENTE = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, paciente.getCdPessoa());
            stmt.setString(2, paciente.getNmPlanoSaude());
            stmt.setInt(3, paciente.getCdPaciente());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar paciente: " + e.getMessage(), e);
        }
    }

    public void deletar(Integer id) {
        String sql = "DELETE FROM T_ASM_PACIENTE WHERE CD_PACIENTE = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar paciente: " + e.getMessage(), e);
        }
    }

    private Integer obterProximoId() {
        String sql = "SELECT NVL(MAX(CD_PACIENTE), 0) + 1 AS PROXIMO_ID FROM T_ASM_PACIENTE";
        
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
