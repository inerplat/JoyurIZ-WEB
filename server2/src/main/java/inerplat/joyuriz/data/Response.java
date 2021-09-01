package inerplat.joyuriz.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class Response{
    private String predict;
    private Integer top;
    private Integer bottom;
    private Integer left;
    private Integer right;
    private String hash;


    @JsonCreator
    public Response(
            @JsonProperty("predict")        String predict,
            @JsonProperty("top")            Integer top,
            @JsonProperty("bottom")         Integer bottom,
            @JsonProperty("left")           Integer left,
            @JsonProperty("right")          Integer right
    ) {
        this.predict = predict.strip();
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public Response(Image img, String hash){
        this.predict = img.getPredict();
        this.top = img.getTop();
        this.bottom = img.getBottom();
        this.left = img.getLeft();
        this.right = img.getRight();
        this.hash = hash;
    }

    public Response(int error){
        this.predict = "fail";
        this.top = this.bottom = this.left = this.right = error;
        this.hash = null;
    }
}
