package com.example.jobfinder.resources;

import com.example.jobfinder.dao.models.Position;
import com.example.jobfinder.resources.assemblers.PositionAssembler;
import com.example.jobfinder.resources.dtos.CreatePositionResponseDto;
import com.example.jobfinder.resources.dtos.PositionDto;
import com.example.jobfinder.services.PositionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
public class PositionResource {

    private static final String BASE_URL = "http://localhost:8080/positions";

    private final PositionService positionService;
    private final PositionAssembler positionAssembler;

    //PositionDto is needed because of the apiKey.. it's not secure to pass it as a url param
    @GetMapping(path = "/positions")
    public ResponseEntity getAllPosition(@RequestBody @Valid PositionDto positionDto) {
        try {
            List<Position> positions = positionService.getAllPositions(positionDto);
            positions.forEach(position -> position.setUrl(BASE_URL + "/" + position.getId()));
            List<PositionDto> response = positions.stream()
                    .map(positionAssembler::buildPositionDto)
                    .collect(toList());
            return ResponseEntity.status(HttpStatus.OK) //
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping(path = "/positions/{id}")
    public ResponseEntity getPosition(@PathVariable("id") Long id, @RequestBody @Valid PositionDto positionDto) {
        try {
            Position position = positionService.getPositionById(id, positionDto);
            position.setUrl(BASE_URL + "/" + id);
            PositionDto response = positionAssembler.buildPositionDto(position);
            return ResponseEntity.status(HttpStatus.OK) //
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping(path = "/positions")
    public ResponseEntity createPosition(@RequestBody @Valid PositionDto positionDto) {
        try {
            Long positionId = null;
            CreatePositionResponseDto createPositionResponseDto = CreatePositionResponseDto.builder().build();
            positionId = positionService.savePosition(positionDto);
            createPositionResponseDto.setUrl(BASE_URL + "/" + positionId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createPositionResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
