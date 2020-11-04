package com.example.jobfinder.resources;

import com.example.jobfinder.resources.dtos.ClientDto;
import com.example.jobfinder.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(toList());
    }
}
