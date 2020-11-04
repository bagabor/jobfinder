package com.example.jobfinder.resources;

import com.example.jobfinder.resources.dtos.ClientDto;
import com.example.jobfinder.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class ClientResource {

    private final ClientService clientService;

    @PostMapping(path = "/clients")
    public String createClient(@RequestBody @Valid ClientDto clientDto) {
        String savedUUId;
        try {
            savedUUId = clientService.saveClient(clientDto);
        } catch (RuntimeException e) {
            return "Client is already registered!";
        }
        return savedUUId;
    }
}
