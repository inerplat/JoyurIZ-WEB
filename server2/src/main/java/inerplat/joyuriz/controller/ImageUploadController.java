package inerplat.joyuriz.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import inerplat.joyuriz.service.WebClinetModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.Data;

import inerplat.joyuriz.service.FileStorageService;


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
        WebClinetModel client = new WebClinetModel();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(file.getBytes());

        client.setUri("http://localhost:5000");
        Response res = (Response) client.requestDetect("/predict", file, Response.class).block();


        res.hash = md.toString();


        logger.debug("[DEBUG|end]: " + String.valueOf(res));

        return res;
    }


    @Data
    public static class Response{
        String predictions;
        Integer top;
        Integer bottom;
        Integer left;
        Integer right;
        String hash;
    }

}
