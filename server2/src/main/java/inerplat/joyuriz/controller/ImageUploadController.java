package inerplat.joyuriz.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.Data;

import inerplat.joyuriz.service.FileStorageService;
import reactor.core.publisher.Mono;


@CrossOrigin(origins = "http://localhost:3000")
@Controller()
public class ImageUploadController {
    @Autowired
    FileStorageService fileStorageService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper mapper;

    public boolean notImage(MultipartFile file) {
        return file.getContentType().split("/")[0].equals("image");
    }

    @PostMapping("/upload/image")
    public @ResponseBody Response processImage(@RequestParam("image") MultipartFile file,
                                               RedirectAttributes redirectAttributes) throws IOException, NoSuchAlgorithmException {

        Assert.isTrue(this.notImage(file), "Uploaded File is Not Image");

        String newFileName = fileStorageService.save(file);

        final String uri = "http://localhost:5000/predict";


        WebClinetController client = new WebClinetController();

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(file.getBytes());
        client.setUri("/predict");
        Response res = (Response) client.requestDetect(file, Response.class).block();

        res.success = true;
        res.hash = md.toString();
        res.path = newFileName;
        res.voteChaewon = 0;
        res.voteYena = 0;
        res.voteYuri = 0;
        res.request = 1;

        logger.debug("[DEBUG|end]: " + String.valueOf(res));
        //return new Response();
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
