package com.betvictor.entity;

import lombok.Data;

@Data
public class ProcessedResponse {

    public ProcessedResponse(){}

    public ProcessedResponse(String freq_word, Integer avg_paragraph_size, String avg_paragraph_processing_time, String total_processing_time) {
        this.freq_word = freq_word;
        this.avg_paragraph_size = avg_paragraph_size;
        this.avg_paragraph_processing_time = avg_paragraph_processing_time;
        this.total_processing_time = total_processing_time;
    }

    private String freq_word;
    private Integer avg_paragraph_size;
    private String avg_paragraph_processing_time;
    private String total_processing_time;

}
