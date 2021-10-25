package com.betvictor.controller;

import com.betvictor.client.LoremIpsumClient;
import com.betvictor.entity.ProcessedResponse;
import com.betvictor.service.LoremIpsumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/betvictor")
public class LoremIpsumController {

    private LoremIpsumClient loremIpsumClient;
    private LoremIpsumService loremIpsumService;

    @Autowired
    public void setLoremIpsumClient(LoremIpsumClient loremIpsumClient){
        this.loremIpsumClient = loremIpsumClient;
    }

    @Autowired
    public void setLoremIpsumService(LoremIpsumService loremIpsumService) { this.loremIpsumService = loremIpsumService; }

    @GetMapping(value="/text", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessedResponse> callClient(@RequestParam(required = false) Integer p, @RequestParam(required = false) String l){
        final String responseToProcess = loremIpsumClient.sendGetRequestToLoremIpsum(p, l);
        ProcessedResponse response = loremIpsumService.buildProcessedResponse(responseToProcess);
        return ResponseEntity.ok().body(response);
    }

}
