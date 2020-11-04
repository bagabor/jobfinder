package com.example.jobfinder.services;

import com.example.jobfinder.dao.models.Client;
import com.example.jobfinder.dao.models.Position;
import com.example.jobfinder.dao.repositories.ClientRepository;
import com.example.jobfinder.dao.repositories.PositionRepository;
import com.example.jobfinder.resources.dtos.PositionDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PositionService {

    private final ClientRepository clientRepository;
    private final PositionRepository positionRepository;

    @Transactional
    public Long savePosition(PositionDto positionDto) throws RuntimeException{
        Client client = getClient(positionDto);

        Position position = positionRepository.save(Position.builder()
                .name(positionDto.getName())
                .local(positionDto.getLocal())
                .client(client)
                .build()
        );
        client.getPositions().add(
                position
        );
        return position.getId();
    }

    public Client getClient(PositionDto positionDto) throws RuntimeException{
        Client client = clientRepository.findByApiKey(positionDto.getApiKey());
        if(client == null) {
            log.error("Client is not registered for this api key!");
            throw new RuntimeException("Client is not registered for this api key!");
        }
        return client;
    }

    public List<Position> getAllPositions(PositionDto positionDto) {
        if(positionDto.getName() == null){
            positionDto.setName("");
        } if(positionDto.getLocal() == null) {
            positionDto.setLocal("");
        }
        return positionRepository.findByNameIgnoreCaseContainingOrLocalContaining(positionDto.getName(), positionDto.getLocal());
    }

    public Position getPositionById(Long id) {
        return positionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
