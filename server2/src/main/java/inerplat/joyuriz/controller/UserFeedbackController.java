package inerplat.joyuriz.controller;

import inerplat.joyuriz.service.UserFeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserFeedbackController {
    @Autowired
    private UserFeedbackService userFeedbackService;

    @PostMapping("/api/v1/feedback")
    public ResponseEntity<String> feedback(@RequestBody Map<String, String> payload) {
        String feedback = payload.get("feedback");
        String hash = payload.get("hash");
        return userFeedbackService.updateFeedback(hash, feedback);
    }
}
