package inerplat.joyuriz.service;

import inerplat.joyuriz.data.Image;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFeedback extends JpaRepository<Image, Long> {
    List<Image> findByHash(String hash);

    @Override
    List<Image> findAll();

    @Override
    List<Image> findAll(Sort sort);

    @Override
    List<Image> findAllById(Iterable<Long> longs);

    @Override
    <S extends Image> List<S> saveAll(Iterable<S> entities);

    @Override
    void flush();

    @Override
    <S extends Image> S saveAndFlush(S entity);

    @Override
    void deleteInBatch(Iterable<Image> entities);

    @Override
    void deleteAllInBatch();

    @Override
    Image getOne(Long aLong);

    @Override
    <S extends Image> List<S> findAll(Example<S> example);

    @Override
    <S extends Image> List<S> findAll(Example<S> example, Sort sort);
}
