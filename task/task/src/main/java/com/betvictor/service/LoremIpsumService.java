package com.betvictor.service;

import com.betvictor.entity.ProcessedResponse;
import com.betvictor.kafka.KafkaMessageProducer;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoremIpsumService {

    private static Logger logger = LoggerFactory.getLogger(LoremIpsumService.class);

    private KafkaMessageProducer kafkaMessageProducer;

    @Autowired
    public void setKafkaMessageProducer(KafkaMessageProducer kafkaMessageProducer){
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    private String parseHTML(String text){
        Document doc = Jsoup.parse(text);
        return doc.body().text();
    }

    private Integer calculateAverageParagraphLength(String text){
        String[] toParagraph = text.split("<p>");
        Integer sumParagraphLength = 0;
        for(String s : toParagraph){
            final Integer sLen = s.length() - "</p>".length();
            sumParagraphLength += sLen;
        }
       return sumParagraphLength / toParagraph.length;
    }

    private String calculateMostFrequentWord(String text){
        String decodedText = parseHTML(text).toLowerCase();
        String[] words = decodedText.split("\\W+");
        Map<String, Integer> map = new HashMap<>();
        for (String w : words) {
            Integer n = map.get(w);
            if(n == null){
                n = 1;
            }else{
                n+=1;
            }
            map.put(w, n);
        }
        return Collections.max(map.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public ProcessedResponse buildProcessedResponse(String response){
        long startTimeTotal = System.currentTimeMillis();
        final String mostFrequentWord = calculateMostFrequentWord(response);
        long startTimeParagraph = System.currentTimeMillis();
        final Integer averageParagraphLength = calculateAverageParagraphLength(response);
        long endTimeTotal = System.currentTimeMillis();
        String paragraphProcessingTime = endTimeTotal - startTimeParagraph + " ms";
        String totalProcessingTime = endTimeTotal - startTimeTotal + " ms";
        ProcessedResponse processedResponse = new ProcessedResponse(mostFrequentWord, averageParagraphLength, paragraphProcessingTime, totalProcessingTime);
        try{
            sendResponseToKafka(processedResponse);
        }catch(Exception e){
            logger.error("Error while trying to send message to kafka : ", e);
        }
        return processedResponse;
    }

    private void sendResponseToKafka(ProcessedResponse response){
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(response);
        kafkaMessageProducer.sendMessage(jsonMessage);
    }


}
