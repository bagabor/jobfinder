package com.example.jobfinder.resources.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
public class PositionDto {

    @Size(max = 50, message = "Position name cannot be longer than 50 character!")
    private String name;

    @Size(max = 50, message = "Local name cannot be longer than 50 character!")
    private String local;

    private UUID apiKey;
}
