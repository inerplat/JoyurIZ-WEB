package inerplat.joyuriz.controller;

import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.service.PsqlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
//@SpringBootTest
@Ignore
class ImageUploadControllerTest {

    @Autowired
    PsqlService psql;

    @Value("${DB_USER}")
    private String user;

    @Test
    public void test(){
        log.info(user);
        String hash = "26E73A3532A9ECCB02E2EC1A75351406";
        Image img = psql.findTop1ByHash(hash);

        if(img != null){
            log.info(img.getPredict());
            img.setRequest(img.getRequest()+1);
            psql.saveAndFlush(img);
            log.info("hi");
        }
    }
}