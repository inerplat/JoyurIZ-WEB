package inerplat.joyuriz.service;

import inerplat.joyuriz.data.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsqlService extends JpaRepository<Image, Long> {
    Image findTop1ByHash(String hash);
}
