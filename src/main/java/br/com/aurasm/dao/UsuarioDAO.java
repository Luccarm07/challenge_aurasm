package br.com.aurasm.dao;

import br.com.aurasm.config.DatabaseConfig;
import br.com.aurasm.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_ASM_USUARIO ORDER BY CD_USUARIO";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setCdUsuario(rs.getInt("CD_USUARIO"));
                usuario.setCdPessoa(rs.getInt("CD_PESSOA"));
                usuario.setDsEmail(rs.getString("DS_EMAIL"));
                usuario.setDsSenha(rs.getString("DS_SENHA"));
                usuario.setNmUsuario(rs.getString("NM_USUARIO"));
                lista.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
        }
        
        return lista;
    }

    public Usuario buscarPorId(Integer id) {
        String sql = "SELECT * FROM T_ASM_USUARIO WHERE CD_USUARIO = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setCdUsuario(rs.getInt("CD_USUARIO"));
                usuario.setCdPessoa(rs.getInt("CD_PESSOA"));
                usuario.setDsEmail(rs.getString("DS_EMAIL"));
                usuario.setDsSenha(rs.getString("DS_SENHA"));
                usuario.setNmUsuario(rs.getString("NM_USUARIO"));
                return usuario;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage(), e);
        }
        
        return null;
    }

    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM T_ASM_USUARIO WHERE DS_EMAIL = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setCdUsuario(rs.getInt("CD_USUARIO"));
                usuario.setCdPessoa(rs.getInt("CD_PESSOA"));
                usuario.setDsEmail(rs.getString("DS_EMAIL"));
                usuario.setDsSenha(rs.getString("DS_SENHA"));
                usuario.setNmUsuario(rs.getString("NM_USUARIO"));
                return usuario;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por email: " + e.getMessage(), e);
        }
        
        return null;
    }

    public Integer criar(Usuario usuario) {
        String sql = "INSERT INTO T_ASM_USUARIO (CD_USUARIO, CD_PESSOA, DS_EMAIL, DS_SENHA, NM_USUARIO) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            Integer proximoId = obterProximoId();
            
            stmt.setInt(1, proximoId);
            stmt.setInt(2, usuario.getCdPessoa());
            stmt.setString(3, usuario.getDsEmail());
            stmt.setString(4, usuario.getDsSenha());
            stmt.setString(5, usuario.getNmUsuario());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                return proximoId;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage(), e);
        }
        
        return -1;
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE T_ASM_USUARIO SET CD_PESSOA = ?, DS_EMAIL = ?, DS_SENHA = ?, NM_USUARIO = ? " +
                     "WHERE CD_USUARIO = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuario.getCdPessoa());
            stmt.setString(2, usuario.getDsEmail());
            stmt.setString(3, usuario.getDsSenha());
            stmt.setString(4, usuario.getNmUsuario());
            stmt.setInt(5, usuario.getCdUsuario());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    public void deletar(Integer id) {
        String sql = "DELETE FROM T_ASM_USUARIO WHERE CD_USUARIO = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }

    private Integer obterProximoId() {
        String sql = "SELECT NVL(MAX(CD_USUARIO), 0) + 1 AS PROXIMO_ID FROM T_ASM_USUARIO";
        
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
