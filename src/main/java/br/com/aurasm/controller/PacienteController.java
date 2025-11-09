package br.com.aurasm.controller;

import br.com.aurasm.dao.PacienteDAO;
import br.com.aurasm.exception.BusinessException;
import br.com.aurasm.model.Paciente;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteController {

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    @GET
    public Response listarTodos() {
        try {
            List<Paciente> pacientes = pacienteDAO.listarTodos();
            return Response.ok(pacientes).build();
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
            Paciente paciente = pacienteDAO.buscarPorId(id);
            if (paciente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Paciente não encontrado"))
                        .build();
            }
            return Response.ok(paciente).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    public Response criar(Paciente paciente) {
        try {
            if (paciente.getCdPessoa() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Código da pessoa é obrigatório"))
                        .build();
            }
            if (paciente.getNmPlanoSaude() == null || paciente.getNmPlanoSaude().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Nome do plano de saúde é obrigatório"))
                        .build();
            }
            
            Integer id = pacienteDAO.criar(paciente);
            Paciente novoPaciente = pacienteDAO.buscarPorId(id);
            return Response.status(Response.Status.CREATED).entity(novoPaciente).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Paciente paciente) {
        try {
            Paciente pacienteExistente = pacienteDAO.buscarPorId(id);
            if (pacienteExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Paciente não encontrado"))
                        .build();
            }
            
            paciente.setCdPaciente(id);
            pacienteDAO.atualizar(paciente);
            Paciente pacienteAtualizado = pacienteDAO.buscarPorId(id);
            return Response.ok(pacienteAtualizado).build();
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
            Paciente paciente = pacienteDAO.buscarPorId(id);
            if (paciente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Paciente não encontrado"))
                        .build();
            }
            
            pacienteDAO.deletar(id);
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
