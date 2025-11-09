package br.com.aurasm.controller;

import br.com.aurasm.dao.TelefoneDAO;
import br.com.aurasm.model.Telefone;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneController {

    private final TelefoneDAO telefoneDAO = new TelefoneDAO();

    @GET
    public Response listarTodos() {
        try {
            List<Telefone> telefones = telefoneDAO.listarTodos();
            return Response.ok(telefones).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/pessoa/{idPessoa}")
    public Response buscarPorPessoa(@PathParam("idPessoa") Integer idPessoa) {
        try {
            List<Telefone> telefones = telefoneDAO.buscarPorPessoa(idPessoa);
            return Response.ok(telefones).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/acompanhante/{idAcompanhante}")
    public Response buscarPorAcompanhante(@PathParam("idAcompanhante") Integer idAcompanhante) {
        try {
            List<Telefone> telefones = telefoneDAO.buscarPorAcompanhante(idAcompanhante);
            return Response.ok(telefones).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    public Response criar(Telefone telefone) {
        try {
            if (telefone.getCdPessoa() == null && telefone.getCdAcompanhante() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Código da pessoa ou acompanhante é obrigatório"))
                        .build();
            }
            if (telefone.getDdd() == null || telefone.getDdd() < 11 || telefone.getDdd() > 99) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("DDD inválido (deve estar entre 11 e 99)"))
                        .build();
            }
            if (telefone.getNmTelefone() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Número de telefone é obrigatório"))
                        .build();
            }
            
            Integer id = telefoneDAO.criar(telefone);
            return Response.status(Response.Status.CREATED)
                    .entity(new SuccessResponse("Telefone criado com sucesso", id))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Telefone telefone) {
        try {
            if (telefone.getDdd() != null && (telefone.getDdd() < 11 || telefone.getDdd() > 99)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("DDD inválido (deve estar entre 11 e 99)"))
                        .build();
            }
            
            telefone.setCdTelefone(id);
            telefoneDAO.atualizar(telefone);
            return Response.ok(new SuccessResponse("Telefone atualizado com sucesso", id)).build();
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
            telefoneDAO.deletar(id);
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

    public static class SuccessResponse {
        public String message;
        public Integer id;
        public SuccessResponse(String message, Integer id) {
            this.message = message;
            this.id = id;
        }
    }
}
