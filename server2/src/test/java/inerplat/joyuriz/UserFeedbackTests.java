package inerplat.joyuriz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.service.UserFeedback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
public class UserFeedbackTests {
    @Autowired
    UserFeedback userFeedback;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    ObjectMapper mapper = new ObjectMapper();
    @Test
    void test() throws JsonProcessingException {
        List<Image> images = userFeedback.findAll();
        Image img = images.get(0);
        logger.info(img.getHash());

        Image insert = new Image("testhash", 123,345,456,654,"Yaena", 99, 88  ,77, "images/", 1);
        userFeedback.saveAndFlush(insert);

        var resultList = userFeedback.findByHash("testhash");

        var result = resultList.get(resultList.size()-1);

        Assertions.assertNotEquals(result, insert);

        logger.info(result.getPredict());
        logger.info(mapper.writeValueAsString(result));
    }
}
