package com.techtest.serviciopersonacliente.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.techtest.serviciopersonacliente.entity.Cliente;
import com.techtest.serviciopersonacliente.entity.ClienteCuenta;
import com.techtest.serviciopersonacliente.exception.BusinessRuleException;
import com.techtest.serviciopersonacliente.repository.ClienteRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ClienteService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ClienteRepository clienteRepository;


    HttpClient client = HttpClient.create()
            //Connection Timeout: is a period within which a connection between a client and a server must be established
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            //Response Timeout: The maximun time we wait to receive a response after sending a request
            .responseTimeout(Duration.ofSeconds(1))
            // Read and Write Timeout: A read timeout occurs when no data was read within a certain
            //period of time, while the write timeout when a write operation cannot finish at a specific time
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });


    public Optional<Cliente> get(long personaId) {
        return clienteRepository.findById(personaId);
    }

    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    public Cliente put(long personaId, Cliente cliente) {
        Cliente clienteActualiza = clienteRepository.findById(personaId).get();
        if(clienteActualiza != null){
            clienteActualiza.setPersonaNombre(cliente.getPersonaNombre());
            clienteActualiza.setPersonaGenero(cliente.getPersonaGenero());
            clienteActualiza.setPersonaEdad(cliente.getPersonaEdad());
            clienteActualiza.setPersonaIdentificacion(cliente.getPersonaIdentificacion());
            clienteActualiza.setPersonaDireccion(cliente.getPersonaDireccion());
            clienteActualiza.setPersonaTelefono(cliente.getPersonaTelefono());
            clienteActualiza.setClienteContraseña(cliente.getClienteContraseña());
            clienteActualiza.setClienteEstado(cliente.isClienteEstado());
        }
        return clienteRepository.save(clienteActualiza);
    }

    public Cliente post(Cliente cliente) throws BusinessRuleException, UnknownHostException {
        if(cliente.getCuentas() != null) {
            for (Iterator<ClienteCuenta> it = cliente.getCuentas().iterator(); it.hasNext();) {
                ClienteCuenta dto = it.next();
                String numeroCuenta = getClienteCuenta(dto.getCuentaId());
                if(numeroCuenta.isBlank()) {
                    BusinessRuleException businessRuleException = new BusinessRuleException("1025", "Error de Validación, Cuenta con id " + dto.getCuentaId()+ " no existe.", HttpStatus.PRECONDITION_FAILED);
                    throw  businessRuleException;
                }
                else{
                    dto.setCliente(cliente);
                }

            }
        }
        cliente.getCuentas().forEach(c -> c.setCliente(cliente));
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> delete(long personaId) {
        Optional<Cliente> clienteEliminar = clienteRepository.findById(personaId);
        if(clienteEliminar.get() != null){
            clienteRepository.delete(clienteEliminar.get());
        }
        return clienteEliminar;
    }

    public Cliente getCuentasCliente(long personaId) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(personaId);
        if(optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            List<ClienteCuenta> cuentas = cliente.getCuentas();

            cuentas.forEach(c -> {
                String numeroCta = null;
                try {
                    numeroCta = getClienteCuenta(c.getCuentaId());
                    c.setCuentaNumero(numeroCta);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            return cliente;
        }
        else {
            return null;
        }
    }


    private String getClienteCuenta(long cuentaId) throws UnknownHostException {
        String numeroCta = "";
        try {
            WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                    .baseUrl("http://localhost:8082/cuentas")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8082/cuentas"))
                    .build();

            JsonNode block = build.method(HttpMethod.GET).uri("/" + cuentaId)
                    .retrieve().bodyToMono(JsonNode.class).block();
            numeroCta = block.get("cuentaNumero").asText();
        } catch(WebClientResponseException ex) {
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return "";
            }
            else {
                throw new UnknownHostException(ex.getMessage());
            }
        }

        return numeroCta;
    }

}
