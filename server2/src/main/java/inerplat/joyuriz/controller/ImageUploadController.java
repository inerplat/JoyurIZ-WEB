package inerplat.joyuriz.controller;

import inerplat.joyuriz.data.Response;
import inerplat.joyuriz.service.FileStorageService;
import inerplat.joyuriz.service.PredictFaceService;
import inerplat.joyuriz.service.WebClientModel;
import inerplat.joyuriz.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @Autowired
    PredictFaceService predictFaceService;

    private final int FAIL_TO_FIND = -1;

    @PostMapping("/api/v1/upload/image")
    public ResponseEntity<Response> processImage(@RequestParam("image") MultipartFile file)
            throws NoSuchAlgorithmException, IOException {
        Response result = predictFaceService.predict(file);
        if(result.getPredict().equals("fail")){
            if (result.getTop() == FAIL_TO_FIND) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }
        return ResponseEntity.ok(result);
    }

}
