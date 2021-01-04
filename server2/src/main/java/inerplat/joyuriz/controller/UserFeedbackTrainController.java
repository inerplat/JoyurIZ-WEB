package inerplat.joyuriz.controller;

import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.service.PsqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@Controller()
public class UserFeedbackTrainController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PsqlService psql;

    @PostMapping("/feedback")
    @ResponseBody
    public ResponseEntity<?> feedback(@RequestBody Map<String, String> payload,
                                      RedirectAttributes redirectAttributes) throws IOException, NoSuchAlgorithmException {
        logger.info(payload.get("feedback"));
        logger.info(payload.get("hash"));

        String feedback = payload.get("feedback");
        String hash = payload.get("hash");

        Image img = psql.findByHash(hash);
        if(img == null)
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

        if(feedback.equals("Chaewon"))
            img.setChaewon(img.getChaewon()+1);
        else if(feedback.equals("Yaena"))
            img.setYaena(img.getYaena()+1);
        else if(feedback.equals("Yuri"))
            img.setYuri(img.getYuri()+1);
        else
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

        psql.saveAndFlush(img);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


}
