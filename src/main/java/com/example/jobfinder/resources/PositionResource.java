package com.example.jobfinder.resources;

import com.example.jobfinder.dao.models.Position;
import com.example.jobfinder.resources.dtos.PositionDto;
import com.example.jobfinder.services.PositionService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class PositionResource {


    private final PositionService positionService;

    @PostMapping(path = "/positions")
    public String createPosition(@RequestBody @Valid PositionDto positionDto) {
        Long positionId;
        try{
            positionId = positionService.savePosition(positionDto);
        } catch (RuntimeException e){
            return "Client is not registered for this api key!";
        }
        return positionId.toString();
    }

}
