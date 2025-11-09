package br.com.aurasm.dao;

import br.com.aurasm.config.DatabaseConfig;
import br.com.aurasm.model.Consulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public List<Consulta> listarTodos() {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_CONSULTA ORDER BY CD_CONSULTA";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setCdConsulta(rs.getInt("CD_CONSULTA"));
                consulta.setCdPaciente(rs.getInt("CD_PACIENTE"));
                consulta.setCdMedico(rs.getInt("CD_MEDICO"));
                consulta.setDtInicio(rs.getTimestamp("DT_INICIO"));
                consulta.setDtFim(rs.getTimestamp("DT_FIM"));
                consulta.setDsObservacao(rs.getString("DS_OBSERVACAO"));
                consulta.setDsStatus(rs.getString("DS_STATUS"));
                lista.add(consulta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public Consulta buscarPorId(Integer id) {
        String sql = "SELECT * FROM T_ASM_CONSULTA WHERE CD_CONSULTA = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setCdConsulta(rs.getInt("CD_CONSULTA"));
                consulta.setCdPaciente(rs.getInt("CD_PACIENTE"));
                consulta.setCdMedico(rs.getInt("CD_MEDICO"));
                consulta.setDtInicio(rs.getTimestamp("DT_INICIO"));
                consulta.setDtFim(rs.getTimestamp("DT_FIM"));
                consulta.setDsObservacao(rs.getString("DS_OBSERVACAO"));
                consulta.setDsStatus(rs.getString("DS_STATUS"));
                return consulta;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta: " + e.getMessage(), e);
        }
        
        return null;
    }

    public List<Consulta> buscarPorPaciente(Integer cdPaciente) {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_CONSULTA WHERE CD_PACIENTE = ? ORDER BY DT_INICIO DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cdPaciente);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setCdConsulta(rs.getInt("CD_CONSULTA"));
                consulta.setCdPaciente(rs.getInt("CD_PACIENTE"));
                consulta.setCdMedico(rs.getInt("CD_MEDICO"));
                consulta.setDtInicio(rs.getTimestamp("DT_INICIO"));
                consulta.setDtFim(rs.getTimestamp("DT_FIM"));
                consulta.setDsObservacao(rs.getString("DS_OBSERVACAO"));
                consulta.setDsStatus(rs.getString("DS_STATUS"));
                lista.add(consulta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consultas por paciente: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public List<Consulta> buscarPorMedico(Integer cdMedico) {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_CONSULTA WHERE CD_MEDICO = ? ORDER BY DT_INICIO DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cdMedico);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setCdConsulta(rs.getInt("CD_CONSULTA"));
                consulta.setCdPaciente(rs.getInt("CD_PACIENTE"));
                consulta.setCdMedico(rs.getInt("CD_MEDICO"));
                consulta.setDtInicio(rs.getTimestamp("DT_INICIO"));
                consulta.setDtFim(rs.getTimestamp("DT_FIM"));
                consulta.setDsObservacao(rs.getString("DS_OBSERVACAO"));
                consulta.setDsStatus(rs.getString("DS_STATUS"));
                lista.add(consulta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consultas por médico: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public Integer criar(Consulta consulta) {
        String sql = "INSERT INTO T_ASM_CONSULTA (CD_CONSULTA, CD_PACIENTE, CD_MEDICO, DT_INICIO, DT_FIM, DS_OBSERVACAO, DS_STATUS) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            Integer proximoId = obterProximoId();
            
            stmt.setInt(1, proximoId);
            stmt.setInt(2, consulta.getCdPaciente());
            stmt.setInt(3, consulta.getCdMedico());
            stmt.setTimestamp(4, new Timestamp(consulta.getDtInicio().getTime()));
            stmt.setTimestamp(5, new Timestamp(consulta.getDtFim().getTime()));
            stmt.setString(6, consulta.getDsObservacao());
            stmt.setString(7, consulta.getDsStatus());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                return proximoId;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar consulta: " + e.getMessage(), e);
        }
        
        return -1;
    }

    public void atualizar(Consulta consulta) {
        String sql = "UPDATE T_ASM_CONSULTA SET CD_PACIENTE = ?, CD_MEDICO = ?, DT_INICIO = ?, DT_FIM = ?, " +
                     "DS_OBSERVACAO = ?, DS_STATUS = ? WHERE CD_CONSULTA = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, consulta.getCdPaciente());
            stmt.setInt(2, consulta.getCdMedico());
            stmt.setTimestamp(3, new Timestamp(consulta.getDtInicio().getTime()));
            stmt.setTimestamp(4, new Timestamp(consulta.getDtFim().getTime()));
            stmt.setString(5, consulta.getDsObservacao());
            stmt.setString(6, consulta.getDsStatus());
            stmt.setInt(7, consulta.getCdConsulta());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar consulta: " + e.getMessage(), e);
        }
    }

    public void deletar(Integer id) {
        String sql = "DELETE FROM T_ASM_CONSULTA WHERE CD_CONSULTA = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar consulta: " + e.getMessage(), e);
        }
    }

    private Integer obterProximoId() {
        String sql = "SELECT NVL(MAX(CD_CONSULTA), 0) + 1 AS PROXIMO_ID FROM T_ASM_CONSULTA";
        
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
