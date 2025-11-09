package br.com.aurasm.controller;

import br.com.aurasm.dao.HistoricoConsultaDAO;
import br.com.aurasm.model.HistoricoConsulta;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/historico-consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoricoConsultaController {

    private final HistoricoConsultaDAO historicoDAO = new HistoricoConsultaDAO();

    @GET
    public Response listarTodos() {
        try {
            List<HistoricoConsulta> historicos = historicoDAO.listarTodos();
            return Response.ok(historicos).build();
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
            HistoricoConsulta historico = historicoDAO.buscarPorId(id);
            if (historico == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Histórico não encontrado"))
                        .build();
            }
            return Response.ok(historico).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/consulta/{idConsulta}")
    public Response buscarPorConsulta(@PathParam("idConsulta") Integer idConsulta) {
        try {
            List<HistoricoConsulta> historicos = historicoDAO.buscarPorConsulta(idConsulta);
            return Response.ok(historicos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    public Response criar(HistoricoConsulta historico) {
        try {
            if (historico.getCdConsulta() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Código da consulta é obrigatório"))
                        .build();
            }
            if (historico.getCdMedico() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Código do médico é obrigatório"))
                        .build();
            }
            if (historico.getNmMedico() == null || historico.getNmMedico().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Nome do médico é obrigatório"))
                        .build();
            }
            if (historico.getDtInicio() == null || historico.getDtFim() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Datas de início e fim são obrigatórias"))
                        .build();
            }
            if (historico.getDsConsulta() == null || historico.getDsConsulta().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Descrição da consulta é obrigatória"))
                        .build();
            }
            
            Integer id = historicoDAO.criar(historico);
            HistoricoConsulta novoHistorico = historicoDAO.buscarPorId(id);
            return Response.status(Response.Status.CREATED).entity(novoHistorico).build();
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
            HistoricoConsulta historico = historicoDAO.buscarPorId(id);
            if (historico == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Histórico não encontrado"))
                        .build();
            }
            
            historicoDAO.deletar(id);
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
