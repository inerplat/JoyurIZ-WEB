package inerplat.joyuriz.util;

import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {

    public static boolean isImage(MultipartFile file) {
        return file.getContentType().split("/")[0].equals("image");
    }

}
