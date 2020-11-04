package com.example.jobfinder.services;

import com.example.jobfinder.dao.models.Client;
import com.example.jobfinder.dao.repositories.ClientRepository;
import com.example.jobfinder.resources.dtos.ClientDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;

    public String saveClient(ClientDto clientDto) throws RuntimeException{
        Client client = clientRepository.findByEmail(clientDto.getEmail());
        if(client != null) {
            log.error("Client is already registered!");
            throw new RuntimeException("Client is already registered!");
        }

        client = clientRepository.save(Client.builder()
                .name(clientDto.getName())
                .email(clientDto.getEmail())
                .build());
        return client.getApiKey().toString();
    }
}
