package com.example.jobfinder.resources;

import com.example.jobfinder.resources.assemblers.PositionAssembler;
import com.example.jobfinder.resources.dtos.CreatePositionResponseDto;
import com.example.jobfinder.resources.dtos.PositionDto;
import com.example.jobfinder.services.PositionService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class PositionResource {


    private final PositionService positionService;
    private final PositionAssembler positionAssembler;

    @GetMapping(path = "/positions")
    public CollectionModel<EntityModel<PositionDto>> getAllPositionDtos() {
        List<EntityModel<PositionDto>> positions = positionService.getAllPositions().stream()
                .map(positionAssembler::toModel)
                .collect(toList());
        return CollectionModel.of(positions, linkTo(methodOn(PositionResource.class).getAllPositionDtos()).withSelfRel());
    }

    @PostMapping(path = "/positions")
    public EntityModel<CreatePositionResponseDto> createPosition(@RequestBody @Valid PositionDto positionDto) {
        Long positionId;
        CreatePositionResponseDto createPositionResponseDto = CreatePositionResponseDto.builder().build();
        try {
            positionId = positionService.savePosition(positionDto);
        } catch (RuntimeException e) {
            createPositionResponseDto.setErrorMessage("Client is not registered for this api key!");
        }
        return EntityModel.of(createPositionResponseDto);
//        return positionId.toString();
//        return EntityModel.of(linkTo(methodOn(PositionResource.class) ))
    }

}
