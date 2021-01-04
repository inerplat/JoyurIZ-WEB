package inerplat.joyuriz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.service.PsqlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PsqlServiceTests {
    @Autowired
    PsqlService psqlService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    ObjectMapper mapper = new ObjectMapper();
    @Test
    void test() throws JsonProcessingException {
        List<Image> images = psqlService.findAll();
        Image img = images.get(0);
        logger.info(img.getHash());

        Image insert = new Image("testhash", 123,345,456,654,"Yaena", 99, 88  ,77, "images/", 1);
        psqlService.saveAndFlush(insert);

        var resultList = psqlService.findByHash("testhash");

        var result = resultList.get(resultList.size()-1);

        Assertions.assertNotEquals(result, insert);

        logger.info(result.getPredict());
        logger.info(mapper.writeValueAsString(result));
    }
}
