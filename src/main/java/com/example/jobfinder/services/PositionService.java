package com.example.jobfinder.services;

import com.example.jobfinder.clients.JobClient;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class PositionService {

    private final ClientRepository clientRepository;
    private final PositionRepository positionRepository;
    private final JobClient jobClient;

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
        //can be replaced with a Query annotation
        if(positionDto.getName() == null){
            positionDto.setName("");
        } if(positionDto.getLocal() == null) {
            positionDto.setLocal("");
        }
        List<Position> positionsFromLocalDb = positionRepository.findByNameIgnoreCaseContainingOrLocalContaining(positionDto.getName(), positionDto.getLocal());
        List<Position> positionsFromClient = jobClient.getPositions(positionDto.getName(), positionDto.getLocal()).stream()
                .map(e -> Position.builder()
                        .name(e.getTitle())
                        .local(e.getLocation())
                        .build()
                ).collect(toList());
        return Stream.concat(positionsFromLocalDb.stream(), positionsFromClient.stream()).collect(toList());
    }

    public Position getPositionById(Long id) {
        return positionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
