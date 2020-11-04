package com.example.jobfinder.services;

import com.example.jobfinder.dao.models.Client;
import com.example.jobfinder.dao.models.Position;
import com.example.jobfinder.dao.repositories.ClientRepository;
import com.example.jobfinder.resources.dtos.PositionDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class PositionService {

    private final ClientRepository clientRepository;

    @Transactional
    public void savePosition(PositionDto positionDto) {
        Client client = clientRepository.findByApiKey(positionDto.getApiKey());

        if(client == null) {
            log.error("Client is not registered for this api key!");
            throw new RuntimeException("Client is not registered for this api key!");
        }
        client.getPositions().add(Position.builder()
                .name(positionDto.getName())
                .local(positionDto.getLocal())
                .client(client)
                .build()
        );
    }
}
