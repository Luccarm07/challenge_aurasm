package br.com.aurasm.controller;

import br.com.aurasm.dao.AcompanhanteDAO;
import br.com.aurasm.model.Acompanhante;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/acompanhantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AcompanhanteController {

    private final AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO();

    @GET
    public Response listarTodos() {
        try {
            List<Acompanhante> acompanhantes = acompanhanteDAO.listarTodos();
            return Response.ok(acompanhantes).build();
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
            Acompanhante acompanhante = acompanhanteDAO.buscarPorId(id);
            if (acompanhante == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Acompanhante não encontrado"))
                        .build();
            }
            return Response.ok(acompanhante).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/paciente/{idPaciente}")
    public Response buscarPorPaciente(@PathParam("idPaciente") Integer idPaciente) {
        try {
            List<Acompanhante> acompanhantes = acompanhanteDAO.buscarPorPaciente(idPaciente);
            return Response.ok(acompanhantes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    public Response criar(Acompanhante acompanhante) {
        try {
            if (acompanhante.getCdPaciente() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Código do paciente é obrigatório"))
                        .build();
            }
            if (acompanhante.getNmAcompanhante() == null || acompanhante.getNmAcompanhante().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Nome do acompanhante é obrigatório"))
                        .build();
            }
            if (acompanhante.getDsParentesco() == null || acompanhante.getDsParentesco().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Parentesco é obrigatório"))
                        .build();
            }
            
            Integer id = acompanhanteDAO.criar(acompanhante);
            Acompanhante novoAcompanhante = acompanhanteDAO.buscarPorId(id);
            return Response.status(Response.Status.CREATED).entity(novoAcompanhante).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Acompanhante acompanhante) {
        try {
            Acompanhante acompanhanteExistente = acompanhanteDAO.buscarPorId(id);
            if (acompanhanteExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Acompanhante não encontrado"))
                        .build();
            }
            
            acompanhante.setCdAcompanhante(id);
            acompanhanteDAO.atualizar(acompanhante);
            Acompanhante acompanhanteAtualizado = acompanhanteDAO.buscarPorId(id);
            return Response.ok(acompanhanteAtualizado).build();
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
            Acompanhante acompanhante = acompanhanteDAO.buscarPorId(id);
            if (acompanhante == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Acompanhante não encontrado"))
                        .build();
            }
            
            acompanhanteDAO.deletar(id);
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
