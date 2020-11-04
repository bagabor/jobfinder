package com.example.jobfinder.resources.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
public class ClientDto {

    private Long id;

    @Size(max = 100, message = "The name cannot be longer than 100 character!")
    private String name;

    //it just an example, shouldn't check the email with regex
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email should have a valid format!")
    private String email;

    private UUID apiKey;
}
