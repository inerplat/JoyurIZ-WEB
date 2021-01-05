package inerplat.joyuriz.controller;

import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.data.Response;
import inerplat.joyuriz.service.FileStorageService;
import inerplat.joyuriz.service.PsqlService;
import inerplat.joyuriz.service.WebClientModel;
import inerplat.joyuriz.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ImageUploadController {

    private final FileStorageService fileStorageService;
    private final PsqlService psql;

    @PostMapping("/upload/image")
    public ResponseEntity<Response> processImage(@RequestParam("image") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        Assert.isTrue(ImageUtil.isImage(file), "Uploaded File is Not Image");

        WebClientModel client = new WebClientModel();

        String hash = fileStorageService.getHash(file);
        Image img = psql.findByHash(hash);
        if(img != null){
            img.setRequest(img.getRequest()+1);
            psql.saveAndFlush(img);
            return ResponseEntity.ok(new Response(img, hash));
        }

        String newFileName = fileStorageService.save(file);


        client.setUri("http://127.0.0.1:5000");
        Response result = client.requestDetect("/predict", file, Response.class).block();
        result.setHash(hash);

        psql.saveAndFlush(new Image(
                result,
                0, 0, 0, 1,
                "localhost:image/" + newFileName
        ));

        return ResponseEntity.ok(result);
    }

}
