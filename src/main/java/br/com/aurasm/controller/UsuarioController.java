package br.com.aurasm.controller;

import br.com.aurasm.dao.UsuarioDAO;
import br.com.aurasm.model.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @GET
    public Response listarTodos() {
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Usuário não encontrado"))
                        .build();
            }
            return Response.ok(usuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    public Response criar(Usuario usuario) {
        try {
            if (usuario.getCdPessoa() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Código da pessoa é obrigatório"))
                        .build();
            }
            if (usuario.getDsEmail() == null || usuario.getDsEmail().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Email é obrigatório"))
                        .build();
            }
            if (usuario.getDsSenha() == null || usuario.getDsSenha().length() < 8) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Senha deve ter no mínimo 8 caracteres"))
                        .build();
            }
            if (usuario.getNmUsuario() == null || usuario.getNmUsuario().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Nome de usuário é obrigatório"))
                        .build();
            }
            
            // Verificar se email já existe
            Usuario emailExistente = usuarioDAO.buscarPorEmail(usuario.getDsEmail());
            if (emailExistente != null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Email já cadastrado"))
                        .build();
            }
            
            Integer id = usuarioDAO.criar(usuario);
            Usuario novoUsuario = usuarioDAO.buscarPorId(id);
            return Response.status(Response.Status.CREATED).entity(novoUsuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Usuario usuario) {
        try {
            Usuario usuarioExistente = usuarioDAO.buscarPorId(id);
            if (usuarioExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Usuário não encontrado"))
                        .build();
            }
            
            if (usuario.getDsSenha() != null && usuario.getDsSenha().length() < 8) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Senha deve ter no mínimo 8 caracteres"))
                        .build();
            }
            
            usuario.setCdUsuario(id);
            usuarioDAO.atualizar(usuario);
            Usuario usuarioAtualizado = usuarioDAO.buscarPorId(id);
            return Response.ok(usuarioAtualizado).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Usuário não encontrado"))
                        .build();
            }
            
            usuarioDAO.deletar(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    public static class ErrorResponse {
        public String error;
        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
