package inerplat.joyuriz.controller;

import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.data.Response;
import inerplat.joyuriz.service.PsqlService;
import inerplat.joyuriz.service.WebClientModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@Slf4j
@SpringBootTest
class ImageUploadControllerTest {

    @Autowired
    PsqlService psql;
    
    @Value("${api.ip}") private String apiIp;
    @Value("${api.port}") private String apiPort;
    @Value("${api.method}") private String apiMethod;

    @Value("${DB_USER}") private String dbUser;
    @Value("${DB_URL}") private String dbUrl;
    @Value("${DB_PASSWORD}") private String dbPw;

    @Test
    public void test(){

        log.info("##############<EnvCheck>################");
        log.info("[API IP]:\t" + apiIp);
        log.info("[API PORT]:\t" + apiPort);
        log.info("[API Method]:\t" + apiMethod);
        log.info("[DB User]:\t" + dbUser);
        log.info("[DB URL]:\t" + dbUrl);
        log.info("[DB PW]:\t" + dbPw);
        log.info("##############</EnvCheck>################");

        log.info("1.##############<APITest>################");


        log.info("1.##############</APITest>################");

//
//        String hash = "26E73A3532A9ECCB02E2EC1A75351406";
//        Image img = psql.findTop1ByHash(hash);
//
//        if(img != null){
//            log.info(img.getPredict());
//            img.setRequest(img.getRequest()+1);
//            psql.saveAndFlush(img);
//            log.info("hi");
//        }
    }
}