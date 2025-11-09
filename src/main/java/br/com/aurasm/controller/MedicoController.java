package br.com.aurasm.controller;

import br.com.aurasm.dao.MedicoDAO;
import br.com.aurasm.model.Medico;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicoController {

    private final MedicoDAO medicoDAO = new MedicoDAO();

    @GET
    public Response listarTodos() {
        try {
            List<Medico> medicos = medicoDAO.listarTodos();
            return Response.ok(medicos).build();
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
            Medico medico = medicoDAO.buscarPorId(id);
            if (medico == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Médico não encontrado"))
                        .build();
            }
            return Response.ok(medico).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    public Response criar(Medico medico) {
        try {
            if (medico.getCdPessoa() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Código da pessoa é obrigatório"))
                        .build();
            }
            if (medico.getNrCrm() == null || medico.getNrCrm().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("CRM é obrigatório"))
                        .build();
            }
            if (medico.getDsEspecialidade() == null || medico.getDsEspecialidade().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Especialidade é obrigatória"))
                        .build();
            }
            if (medico.getVlSalario() == null || medico.getVlSalario() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Salário deve ser maior que zero"))
                        .build();
            }
            
            // Verificar se CRM já existe
            Medico crmExistente = medicoDAO.buscarPorCrm(medico.getNrCrm());
            if (crmExistente != null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("CRM já cadastrado"))
                        .build();
            }
            
            Integer id = medicoDAO.criar(medico);
            Medico novoMedico = medicoDAO.buscarPorId(id);
            return Response.status(Response.Status.CREATED).entity(novoMedico).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Medico medico) {
        try {
            Medico medicoExistente = medicoDAO.buscarPorId(id);
            if (medicoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Médico não encontrado"))
                        .build();
            }
            
            if (medico.getVlSalario() != null && medico.getVlSalario() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Salário deve ser maior que zero"))
                        .build();
            }
            
            medico.setCdMedico(id);
            medicoDAO.atualizar(medico);
            Medico medicoAtualizado = medicoDAO.buscarPorId(id);
            return Response.ok(medicoAtualizado).build();
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
            Medico medico = medicoDAO.buscarPorId(id);
            if (medico == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Médico não encontrado"))
                        .build();
            }
            
            medicoDAO.deletar(id);
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
