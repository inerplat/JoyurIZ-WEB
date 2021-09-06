package inerplat.joyuriz.repo;

import inerplat.joyuriz.data.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<Image, String> {

}
