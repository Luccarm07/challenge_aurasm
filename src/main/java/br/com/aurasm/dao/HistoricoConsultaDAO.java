package br.com.aurasm.dao;

import br.com.aurasm.config.DatabaseConfig;
import br.com.aurasm.model.HistoricoConsulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoConsultaDAO {

    public List<HistoricoConsulta> listarTodos() {
        List<HistoricoConsulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_HISTORICO_CONSULTA ORDER BY CD_HISTORICO";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                HistoricoConsulta historico = new HistoricoConsulta();
                historico.setCdHistorico(rs.getInt("CD_HISTORICO"));
                historico.setCdConsulta(rs.getInt("CD_CONSULTA"));
                historico.setCdMedico(rs.getInt("CD_MEDICO"));
                historico.setNmMedico(rs.getString("NM_MEDICO"));
                historico.setDtInicio(rs.getTimestamp("DT_INICIO"));
                historico.setDtFim(rs.getTimestamp("DT_FIM"));
                historico.setDsConsulta(rs.getString("DS_CONSULTA"));
                lista.add(historico);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar histórico de consultas: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public HistoricoConsulta buscarPorId(Integer id) {
        String sql = "SELECT * FROM T_ASM_HISTORICO_CONSULTA WHERE CD_HISTORICO = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                HistoricoConsulta historico = new HistoricoConsulta();
                historico.setCdHistorico(rs.getInt("CD_HISTORICO"));
                historico.setCdConsulta(rs.getInt("CD_CONSULTA"));
                historico.setCdMedico(rs.getInt("CD_MEDICO"));
                historico.setNmMedico(rs.getString("NM_MEDICO"));
                historico.setDtInicio(rs.getTimestamp("DT_INICIO"));
                historico.setDtFim(rs.getTimestamp("DT_FIM"));
                historico.setDsConsulta(rs.getString("DS_CONSULTA"));
                return historico;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar histórico: " + e.getMessage(), e);
        }
        
        return null;
    }

    public List<HistoricoConsulta> buscarPorConsulta(Integer cdConsulta) {
        List<HistoricoConsulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_HISTORICO_CONSULTA WHERE CD_CONSULTA = ? ORDER BY DT_INICIO DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cdConsulta);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                HistoricoConsulta historico = new HistoricoConsulta();
                historico.setCdHistorico(rs.getInt("CD_HISTORICO"));
                historico.setCdConsulta(rs.getInt("CD_CONSULTA"));
                historico.setCdMedico(rs.getInt("CD_MEDICO"));
                historico.setNmMedico(rs.getString("NM_MEDICO"));
                historico.setDtInicio(rs.getTimestamp("DT_INICIO"));
                historico.setDtFim(rs.getTimestamp("DT_FIM"));
                historico.setDsConsulta(rs.getString("DS_CONSULTA"));
                lista.add(historico);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar histórico por consulta: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public Integer criar(HistoricoConsulta historico) {
        String sql = "INSERT INTO T_ASM_HISTORICO_CONSULTA (CD_HISTORICO, CD_CONSULTA, CD_MEDICO, NM_MEDICO, DT_INICIO, DT_FIM, DS_CONSULTA) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            Integer proximoId = obterProximoId();
            
            stmt.setInt(1, proximoId);
            stmt.setInt(2, historico.getCdConsulta());
            stmt.setInt(3, historico.getCdMedico());
            stmt.setString(4, historico.getNmMedico());
            stmt.setTimestamp(5, new Timestamp(historico.getDtInicio().getTime()));
            stmt.setTimestamp(6, new Timestamp(historico.getDtFim().getTime()));
            stmt.setString(7, historico.getDsConsulta());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                return proximoId;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar histórico: " + e.getMessage(), e);
        }
        
        return -1;
    }

    public void deletar(Integer id) {
        String sql = "DELETE FROM T_ASM_HISTORICO_CONSULTA WHERE CD_HISTORICO = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar histórico: " + e.getMessage(), e);
        }
    }

    private Integer obterProximoId() {
        String sql = "SELECT NVL(MAX(CD_HISTORICO), 0) + 1 AS PROXIMO_ID FROM T_ASM_HISTORICO_CONSULTA";
        
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
