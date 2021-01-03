package inerplat.joyuriz.data;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;


@Entity
@Table(name="feedback")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "hash")
    private String hash;

    @Column(name = "face_top")
    private Integer face_top;

    @Column(name = "face_right")
    private Integer face_right;

    @Column(name = "face_bottom")
    private Integer face_bottom;

    @Column(name = "face_left")
    private Integer face_left;


    @Column(name = "predict")
    private String predict;

    @Column(name = "vote_chaewon")
    private Integer vote_chaewon;

    @Column(name = "vote_yuri")
    private Integer vote_yuri;

    @Column(name = "vote_yaena")
    private Integer vote_yaena;

    @Column(name = "path")
    private String path;

    @Column(name = "request")
    private Integer request;

    public Image() {
    }

    public Image(String hash, Integer face_top, Integer face_right, Integer face_bottom, Integer face_left, String predict, Integer vote_chaewon, Integer vote_yuri, Integer vote_yaena, String path, Integer request) {
        this.hash = hash;
        this.face_top = face_top;
        this.face_right = face_right;
        this.face_bottom = face_bottom;
        this.face_left = face_left;
        this.predict = predict;
        this.vote_chaewon = vote_chaewon;
        this.vote_yuri = vote_yuri;
        this.vote_yaena = vote_yaena;
        this.path = path;
        this.request = request;
    }
}
