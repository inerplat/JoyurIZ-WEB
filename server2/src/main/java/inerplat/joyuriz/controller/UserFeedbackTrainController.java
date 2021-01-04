package inerplat.joyuriz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import inerplat.joyuriz.service.FileStorageService;
import inerplat.joyuriz.service.WebClinetModel;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


@Controller()
public class UserFeedbackTrainController {

    @PostMapping("/feedback")
    @ResponseBody
    public ResponseEntity<?> feedback(RedirectAttributes redirectAttributes) throws IOException, NoSuchAlgorithmException {
        return null;
    }


}
