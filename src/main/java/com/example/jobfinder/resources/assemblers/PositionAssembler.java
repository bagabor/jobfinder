package com.example.jobfinder.resources.assemblers;

import com.example.jobfinder.dao.models.Position;
import com.example.jobfinder.resources.dtos.PositionDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PositionAssembler implements RepresentationModelAssembler<Position, EntityModel<PositionDto>> {


    //not used beacuse of the ResponseEntity type
    @Override
    public EntityModel<PositionDto> toModel(Position position) {
        return EntityModel.of(PositionDto.builder()
                .name(position.getName())
                .local(position.getLocal())
                .url(position.getUrl())
                .build());
    }

    public PositionDto buildPositionDto(Position position) {
        PositionDto positionDto = PositionDto.builder()
                .name(position.getName())
                .local(position.getLocal())
                .url(position.getUrl())
                .build();
        return positionDto;
    }
}
