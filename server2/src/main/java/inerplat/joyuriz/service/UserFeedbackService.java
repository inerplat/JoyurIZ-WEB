package inerplat.joyuriz.service;

import inerplat.joyuriz.data.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserFeedbackService {
    @Autowired
    RedisService redisService;

    public ResponseEntity<String> updateFeedback(String hash, String member){
        Image img;
        try {
            img = redisService.getImageByHash(hash);
        } catch (Exception err){
            return ResponseEntity.badRequest().body("Bad Request");
        }
        switch(member){
            case "Chaewon":
                img.setChaewon(img.getChaewon() + 1);
                break;
            case "Yaena":
                img.setYaena(img.getYaena() + 1);
                break;
            case "Yuri":
                img.setYuri(img.getYuri() + 1);
                break;
            default:
                return ResponseEntity.badRequest().body("Bad Request");
        }
        redisService.setImage(img);
        return ResponseEntity.ok("{\"feedback\": \"OK\"}");
    }
}
