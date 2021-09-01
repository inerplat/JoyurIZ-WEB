package inerplat.joyuriz.service;

import inerplat.joyuriz.data.Image;
import inerplat.joyuriz.repo.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RedisService {
    @Autowired
    private RedisRepository redisRepository;
    public Image getImageByHash(String hash){
        var img = redisRepository.findById(hash);
        assert img.isPresent();
        return img.get();
    }
    public void setImage(Image img){
        redisRepository.save(img);
    }
}
