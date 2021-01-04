package inerplat.joyuriz.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.data.Response;
import inerplat.joyuriz.service.PsqlService;
import inerplat.joyuriz.service.WebClinetModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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

    @Autowired
    private PsqlService psql;

    @PostMapping("/upload/image")
    @ResponseBody
    public ResponseEntity<?> processImage(@RequestParam("image") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException, NoSuchAlgorithmException {
        Assert.isTrue(this.notImage(file), "Uploaded File is Not Image");

        WebClinetModel client = new WebClinetModel();

        String hash = fileStorageService.getHash(file);
        Image img = psql.findByHash(hash);
        if(img != null){
            img.setRequest(img.getRequest()+1);
            psql.saveAndFlush(img);
            return new ResponseEntity<>(new Response(img, hash), HttpStatus.OK);
        }

        String newFileName = fileStorageService.save(file);


        client.setUri("http://localhost:5000");
        Response result = (Response) client.requestDetect("/predict", file, Response.class).block();
        result.setHash(hash);

        psql.saveAndFlush(new Image(
                result,
                0, 0, 0, 1,
                "localhost:image/" + newFileName
        ));

        return new ResponseEntity<>(result, HttpStatus.OK);

    }



}
