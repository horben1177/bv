package com.betvictor.client;

import com.betvictor.entity.LengthEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class LoremIpsumClient {

        private boolean checkLength(String l){
                if(l == null || l.isBlank()) {
                        return false;
                }
                return l.equals(LengthEnum.SHORT.getLength()) || l.equals(LengthEnum.MEDIUM.getLength()) ||
                        l.equals(LengthEnum.LONG.getLength()) || l.equals(LengthEnum.VERYLONG.getLength());
        }

        private String buildURI(Integer p, String l){
                String baseURI = "https://loripsum.net/api";
                StringBuilder sb = new StringBuilder(baseURI);
                final boolean pCheck = p != null && p > 0 && p < Integer.MAX_VALUE;
                if(pCheck){
                        sb.append("/");
                        sb.append(p);
                }
                if(checkLength(l)){
                        sb.append("/");
                        sb.append(l);
                }
                return sb.toString();
        }

        public String sendGetRequestToLoremIpsum(Integer p, String l){
                WebClient webClient = WebClient.create();
                final String uri = buildURI(p, l);
                final Mono<String> monoResponse = webClient.get().
                        uri(uri).retrieve().bodyToMono(String.class);
                final String response = monoResponse.block();
                return response;
        }

}
