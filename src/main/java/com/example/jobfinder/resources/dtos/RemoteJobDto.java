package com.example.jobfinder.resources.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemoteJobDto {
    private String url;
    private String location;
    private String title;
}
