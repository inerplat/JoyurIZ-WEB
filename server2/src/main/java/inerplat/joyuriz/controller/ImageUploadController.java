package inerplat.joyuriz.controller;

import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.data.Response;
import inerplat.joyuriz.service.FileStorageService;
import inerplat.joyuriz.service.PsqlService;
import inerplat.joyuriz.service.WebClientModel;
import inerplat.joyuriz.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageUploadController {

    private final FileStorageService fileStorageService;

    private final PsqlService psql;

    @Value("${api.ip}") private String apiIp;
    @Value("${api.port}") private String apiPort;
    @Value("${api.method}") private String apiMethod;

    @PostMapping("/api/v1/upload/image")
    public ResponseEntity<Response> processImage(@RequestParam("image") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        Assert.isTrue(ImageUtil.isImage(file), "Uploaded File is Not Image");

        WebClientModel client = new WebClientModel();

        String hash = fileStorageService.getHash(file);
        Image img = psql.findTop1ByHash(hash);

        if(img != null){
            img.setRequest(img.getRequest()+1);
            psql.saveAndFlush(img);
            log.info(String.format("[DB]\t%s: %s", img.getHash(), img.getPredict()));
            return ResponseEntity.ok(new Response(img, hash));
        }

        String newFileName = fileStorageService.save(file);

        client.setUri(String.format("%s://%s:%s", apiMethod, apiIp, apiPort));
        Response result;

        try {
            result = (Response) client.requestDetect("/api/v1/predict", file, Response.class).block();
        } catch(RuntimeException err){
            log.error("[API]\tFace detect Error: " + err.getMessage());
            return ResponseEntity.notFound().build();
        } catch(Exception err){
            log.error("[API] Internal Server Error:" + err.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }

        assert result != null;
        result.setHash(hash);
        psql.saveAndFlush(new Image(
                result,
                0, 0, 0, 1,
                "localhost:image/" + newFileName
        ));

        log.info(String.format("[API]\t%s: %s", result.getHash(), result.getPredict()));
        return ResponseEntity.ok(result);
    }

}
