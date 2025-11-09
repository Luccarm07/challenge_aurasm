package br.com.aurasm.dao;

import br.com.aurasm.config.DatabaseConfig;
import br.com.aurasm.model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public List<Medico> listarTodos() {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_MEDICO ORDER BY CD_MEDICO";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setCdMedico(rs.getInt("CD_MEDICO"));
                medico.setCdPessoa(rs.getInt("CD_PESSOA"));
                medico.setNrCrm(rs.getString("NR_CRM"));
                medico.setDsEspecialidade(rs.getString("DS_ESPECIALIDADE"));
                medico.setVlSalario(rs.getDouble("VL_SALARIO"));
                lista.add(medico);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar médicos: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public Medico buscarPorId(Integer id) {
        String sql = "SELECT * FROM T_ASM_MEDICO WHERE CD_MEDICO = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Medico medico = new Medico();
                medico.setCdMedico(rs.getInt("CD_MEDICO"));
                medico.setCdPessoa(rs.getInt("CD_PESSOA"));
                medico.setNrCrm(rs.getString("NR_CRM"));
                medico.setDsEspecialidade(rs.getString("DS_ESPECIALIDADE"));
                medico.setVlSalario(rs.getDouble("VL_SALARIO"));
                return medico;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar médico: " + e.getMessage(), e);
        }
        
        return null;
    }

    public Medico buscarPorCrm(String crm) {
        String sql = "SELECT * FROM T_ASM_MEDICO WHERE NR_CRM = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, crm);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Medico medico = new Medico();
                medico.setCdMedico(rs.getInt("CD_MEDICO"));
                medico.setCdPessoa(rs.getInt("CD_PESSOA"));
                medico.setNrCrm(rs.getString("NR_CRM"));
                medico.setDsEspecialidade(rs.getString("DS_ESPECIALIDADE"));
                medico.setVlSalario(rs.getDouble("VL_SALARIO"));
                return medico;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar médico por CRM: " + e.getMessage(), e);
        }
        
        return null;
    }

    public Integer criar(Medico medico) {
        String sql = "INSERT INTO T_ASM_MEDICO (CD_MEDICO, CD_PESSOA, NR_CRM, DS_ESPECIALIDADE, VL_SALARIO) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            Integer proximoId = obterProximoId();
            
            stmt.setInt(1, proximoId);
            stmt.setInt(2, medico.getCdPessoa());
            stmt.setString(3, medico.getNrCrm());
            stmt.setString(4, medico.getDsEspecialidade());
            stmt.setDouble(5, medico.getVlSalario());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                return proximoId;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar médico: " + e.getMessage(), e);
        }
        
        return -1;
    }

    public void atualizar(Medico medico) {
        String sql = "UPDATE T_ASM_MEDICO SET CD_PESSOA = ?, NR_CRM = ?, DS_ESPECIALIDADE = ?, VL_SALARIO = ? " +
                     "WHERE CD_MEDICO = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, medico.getCdPessoa());
            stmt.setString(2, medico.getNrCrm());
            stmt.setString(3, medico.getDsEspecialidade());
            stmt.setDouble(4, medico.getVlSalario());
            stmt.setInt(5, medico.getCdMedico());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar médico: " + e.getMessage(), e);
        }
    }

    public void deletar(Integer id) {
        String sql = "DELETE FROM T_ASM_MEDICO WHERE CD_MEDICO = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar médico: " + e.getMessage(), e);
        }
    }

    private Integer obterProximoId() {
        String sql = "SELECT NVL(MAX(CD_MEDICO), 0) + 1 AS PROXIMO_ID FROM T_ASM_MEDICO";
        
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
