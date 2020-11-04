package com.example.jobfinder.clients;

import com.example.jobfinder.resources.dtos.RemoteJobDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(value = "jobClient", url = "https://jobs.github.com/")
public interface JobClient {

    @RequestMapping(method = GET, value = "positions.json")
    List<RemoteJobDto> getPositions(@RequestParam("description") String name, @RequestParam("location") String local);
}
