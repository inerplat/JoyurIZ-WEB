package inerplat.joyuriz.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.client.RestTemplate;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.Data;

import inerplat.joyuriz.service.FileStorageService;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ImageUploadController {

    @Autowired
    FileStorageService fileStorageService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper mapper;

    @PostMapping("/upload/image")
    public @ResponseBody Response handleFileUpload(@RequestParam("image") MultipartFile file,
                                     RedirectAttributes redirectAttributes) throws IOException, NoSuchAlgorithmException {

        logger.debug("[DEBUG] " + file.getContentType());
        if(!file.getContentType().split("/")[0].equals("image"))
            return null;

        String newFileName = fileStorageService.save(file);

        final String uri = "http://localhost:5000/predict";

        // RestTemplate rest = new RestTemplate();
        RestTemplateBuilder rest = new RestTemplateBuilder();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        rest.defaultHeader()

        ResponseEntity<String> result = rest.postForEntity(uri, requestEntity, String.class);

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(file.getBytes());
        String str = result.getBody();
        logger.debug("[DEBUG|Receive]: " + str);
        mapper = new JsonMapper();
        Response res = mapper.readValue(str, Response.class);


        ObjectNode response = mapper.createObjectNode();


        res.success = true;
        res.hash = md.toString();
        res.path = newFileName;
        res.voteChaewon = 0;
        res.voteYena = 0;
        res.voteYuri = 0;
        res.request = 1;

        logger.debug("[DEBUG|end]: " + String.valueOf(res));

        return res;
    }

    @Data
    public static class Response{
        Boolean success;
        String predictions;
        Integer top;
        Integer bottom;
        Integer left;
        Integer right;
        String hash;
        String path;
        Integer voteChaewon;
        Integer voteYuri;
        Integer voteYena;
        Integer request;
    }

}
