package inerplat.joyuriz.controller;

import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.service.PsqlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserFeedbackTrainController {
    private final PsqlService psql;

    @PostMapping("/feedback")
    public ResponseEntity<String> feedback(@RequestBody Map<String, String> payload) {
        log.info(payload.get("feedback"));
        log.info(payload.get("hash"));

        String feedback = payload.get("feedback");
        String hash = payload.get("hash");

        Image img = psql.findTop1ByHash(hash);
        if(img == null)
            return ResponseEntity.badRequest().body("Bad request");

        switch (feedback) {
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
                return ResponseEntity.badRequest().body("Bad request");
        }

        psql.saveAndFlush(img);

        return ResponseEntity.ok("OK");
    }


}
