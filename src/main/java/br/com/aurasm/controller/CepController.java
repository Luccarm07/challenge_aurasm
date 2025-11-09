package br.com.aurasm.controller;

import br.com.aurasm.model.dto.CepResponse;
import br.com.aurasm.service.ViaCepService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/cep")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CepController {

    @Inject
    @RestClient
    ViaCepService viaCepService;

    @GET
    @Path("/{cep}")
    public Response buscarCep(@PathParam("cep") String cep) {
        try {
            // Remover caracteres não numéricos do CEP
            String cepLimpo = cep.replaceAll("[^0-9]", "");
            
            // Validar formato do CEP
            if (cepLimpo.length() != 8) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("CEP inválido. Deve conter 8 dígitos."))
                        .build();
            }
            
            // Buscar CEP na API ViaCEP
            CepResponse cepResponse = viaCepService.buscarCep(cepLimpo);
            
            // Verificar se o CEP foi encontrado
            if (cepResponse.getErro() != null && cepResponse.getErro()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("CEP não encontrado"))
                        .build();
            }
            
            return Response.ok(cepResponse).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao buscar CEP: " + e.getMessage()))
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
