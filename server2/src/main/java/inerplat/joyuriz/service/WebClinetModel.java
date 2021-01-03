package inerplat.joyuriz.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.String;

@RestController
public class WebClinetModel {
    private WebClient webClient = null;
    private String uri;

    public void setUri(String baseUri){
        this.uri = uri;
        this.webClient = WebClient.builder()
                .baseUrl(baseUri)
                .build();
    }
    public Mono<?> requestDetect(String uri, MultipartFile file, Class<?> clazz) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", file.getResource());
        MultiValueMap<String, HttpEntity<?>> body = builder.build();
        return webClient.mutate()
                .build()
                .post()
                .uri(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(body))
                .retrieve()
                .bodyToMono(clazz);
    }
}
