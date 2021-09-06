package inerplat.joyuriz.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


// @Entity(name="feedback")
@RedisHash("feedback")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image implements Serializable {
    @Id
    private String hash;

    private Integer top;

    private Integer right;

    private Integer bottom;

    private Integer left;

    private String predict;

    @Setter
    private Integer chaewon;

    @Setter
    private Integer yuri;

    @Setter
    private Integer yaena;

    private String path;

    @Setter
    private Integer request;

    public Image(String hash, Integer face_top, Integer face_right, Integer face_bottom, Integer face_left, String predict, Integer chaewon, Integer yuri, Integer yaena, String path, Integer request) {
        this.hash = hash;
        this.top = face_top;
        this.right = face_right;
        this.bottom = face_bottom;
        this.left = face_left;
        this.predict = predict;
        this.chaewon = chaewon;
        this.yuri = yuri;
        this.yaena = yaena;
        this.path = path;
        this.request = request;
    }

    public Image(Response res, Integer chaewon, Integer yaena, Integer yuri, Integer request, String path){
        this.hash = res.getHash();
        this.top = res.getTop();
        this.right = res.getRight();
        this.bottom = res.getBottom();
        this.left = res.getLeft();
        this.predict = res.getPredict();
        this.chaewon = chaewon;
        this.yaena = yaena;
        this.yuri = yuri;
        this.request = request;
        this.path = path;
    }
}
