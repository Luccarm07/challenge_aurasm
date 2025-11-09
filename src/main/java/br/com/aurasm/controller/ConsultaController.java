package br.com.aurasm.controller;

import br.com.aurasm.dao.ConsultaDAO;
import br.com.aurasm.model.Consulta;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaController {

    private final ConsultaDAO consultaDAO = new ConsultaDAO();

    @GET
    public Response listarTodos() {
        try {
            List<Consulta> consultas = consultaDAO.listarTodos();
            return Response.ok(consultas).build();
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
            Consulta consulta = consultaDAO.buscarPorId(id);
            if (consulta == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Consulta não encontrada"))
                        .build();
            }
            return Response.ok(consulta).build();
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
            List<Consulta> consultas = consultaDAO.buscarPorPaciente(idPaciente);
            return Response.ok(consultas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/medico/{idMedico}")
    public Response buscarPorMedico(@PathParam("idMedico") Integer idMedico) {
        try {
            List<Consulta> consultas = consultaDAO.buscarPorMedico(idMedico);
            return Response.ok(consultas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    public Response criar(Consulta consulta) {
        try {
            if (consulta.getCdPaciente() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Código do paciente é obrigatório"))
                        .build();
            }
            if (consulta.getCdMedico() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Código do médico é obrigatório"))
                        .build();
            }
            if (consulta.getDtInicio() == null || consulta.getDtFim() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Datas de início e fim são obrigatórias"))
                        .build();
            }
            if (consulta.getDtInicio().after(consulta.getDtFim())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Data de início deve ser anterior à data de fim"))
                        .build();
            }
            if (consulta.getDsStatus() == null || consulta.getDsStatus().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Status é obrigatório"))
                        .build();
            }
            
            Integer id = consultaDAO.criar(consulta);
            Consulta novaConsulta = consultaDAO.buscarPorId(id);
            return Response.status(Response.Status.CREATED).entity(novaConsulta).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Consulta consulta) {
        try {
            Consulta consultaExistente = consultaDAO.buscarPorId(id);
            if (consultaExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Consulta não encontrada"))
                        .build();
            }
            
            if (consulta.getDtInicio() != null && consulta.getDtFim() != null) {
                if (consulta.getDtInicio().after(consulta.getDtFim())) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Data de início deve ser anterior à data de fim"))
                            .build();
                }
            }
            
            consulta.setCdConsulta(id);
            consultaDAO.atualizar(consulta);
            Consulta consultaAtualizada = consultaDAO.buscarPorId(id);
            return Response.ok(consultaAtualizada).build();
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
            Consulta consulta = consultaDAO.buscarPorId(id);
            if (consulta == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Consulta não encontrada"))
                        .build();
            }
            
            consultaDAO.deletar(id);
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
