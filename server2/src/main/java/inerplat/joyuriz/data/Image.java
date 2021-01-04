package inerplat.joyuriz.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="feedback")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "hash")
    private String hash;

    @Column(name = "face_top")
    private Integer top;

    @Column(name = "face_right")
    private Integer right;

    @Column(name = "face_bottom")
    private Integer bottom;

    @Column(name = "face_left")
    private Integer left;


    @Column(name = "predict")
    private String predict;

    @Column(name = "vote_chaewon")
    @Setter
    private Integer chaewon;

    @Column(name = "vote_yuri")
    @Setter
    private Integer yuri;

    @Column(name = "vote_yaena")
    @Setter
    private Integer yaena;

    @Column(name = "path")
    private String path;

    @Column(name = "request")
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
