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
import java.util.List;
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
    public Long savePosition(PositionDto positionDto) throws RuntimeException {
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

    public Client getClient(PositionDto positionDto) throws RuntimeException {
        Client client = clientRepository.findByApiKey(positionDto.getApiKey());
        if (client == null) {
            log.error("Client is not registered for this api key!");
            throw new RuntimeException("Client is not registered for this api key!");
        }
        return client;
    }

    public List<Position> getAllPositions(PositionDto positionDto) {
        List<Position> positionsFromLocalDb = getClient(positionDto).getPositions().stream()
                .filter(pos -> {
                    if ((positionDto.getName() != null && pos.getName().toLowerCase().contains(positionDto.getName().toLowerCase())) ||
                            (positionDto.getLocal() != null && pos.getLocal().toLowerCase().contains(positionDto.getLocal().toLowerCase()))) {
                        return true;
                    }
                    return false;
                })
                .collect(toList());

        List<Position> positionsFromClient = jobClient.getPositions(positionDto.getName(), positionDto.getLocal()).stream()
                .map(e -> Position.builder()
                        .name(e.getTitle())
                        .local(e.getLocation())
                        .url(e.getUrl())
                        .build()
                ).collect(toList());
        return Stream.concat(positionsFromLocalDb.stream(), positionsFromClient.stream()).collect(toList());
    }

    public Position getPositionById(Long id, PositionDto positionDto) {

        Position position = getClient(positionDto)
                .getPositions().stream().filter(pos -> pos.getId().equals(id)).findFirst().orElseThrow(EntityNotFoundException::new);
        return position;
    }
}
