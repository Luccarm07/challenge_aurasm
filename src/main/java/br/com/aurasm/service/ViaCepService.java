package br.com.aurasm.service;

import br.com.aurasm.model.dto.CepResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey = "viacep-api")
public interface ViaCepService {
    
    @GET
    @Path("/{cep}/json/")
    @Produces(MediaType.APPLICATION_JSON)
    CepResponse buscarCep(@PathParam("cep") String cep);
}
