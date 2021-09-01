package inerplat.joyuriz.service;

import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.data.Response;
import inerplat.joyuriz.repo.RedisRepository;
import inerplat.joyuriz.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class PredictFaceService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private FileStorageService fileStorageService;

    @Value("${api.ip}") private String apiIp;
    @Value("${api.port}") private String apiPort;
    @Value("${api.method}") private String apiMethod;

    private final int FAIL_TO_FIND = -1;
    private final int INTERNAL_SERVER_ERROR = -2;

    public Response predict(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        Assert.isTrue(ImageUtil.isImage(file), "Uploaded File is Not Image");
        Response res = requestFromRedisByFile(file);
        if(res == null) {
            res = requestFromMlByFile(file);
            log.info("[redis]: " + res.getHash());
            Image img = new Image(res, 0, 0, 0, 1, res.getHash());
            redisService.setImage(img);
            fileStorageService.save(file);
        }
        return res;
    }

    private Response requestFromMlByFile(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        String hash = fileStorageService.getHash(file);
        WebClientModel client = new WebClientModel();
        client.setUri(String.format("%s://%s:%s", apiMethod, apiIp, apiPort));
        Response result;
        try {
            result = (Response) client.requestDetect("/api/v1/predict", file, Response.class).block();
            result.setHash(hash);
        } catch(RuntimeException err){
            return new Response(FAIL_TO_FIND);
        } catch(Exception err){
            log.error("[API] Internal Server Error:" + err.getMessage());
            return new Response(INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    private Response requestFromRedisByFile(MultipartFile file)
            throws NoSuchAlgorithmException, IOException {
        String hash = fileStorageService.getHash(file);
        Image img;
        try {
            img = redisService.getImageByHash(hash);
        } catch(Exception e){
            return null;
        }
        img.setRequest(img.getRequest()+1);
        redisService.setImage(img);
        return new Response(img, hash);
    }
}
