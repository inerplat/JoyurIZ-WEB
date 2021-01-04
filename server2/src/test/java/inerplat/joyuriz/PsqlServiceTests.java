package inerplat.joyuriz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.service.PsqlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class PsqlServiceTests {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    PsqlService psqlService;

    @Test
    void test() throws JsonProcessingException {
        List<Image> images = psqlService.findAll();
        Image img = images.get(0);
        log.info(img.getHash());

        Image insert = new Image("testhash", 123,345,456,654,"Yaena", 99, 88  ,77, "images/", 1);
        psqlService.saveAndFlush(insert);

        var result = psqlService.findByHash("testhash");

        Assertions.assertNotEquals(result, insert);

        log.info(result.getPredict());
        log.info(mapper.writeValueAsString(result));
    }
}
