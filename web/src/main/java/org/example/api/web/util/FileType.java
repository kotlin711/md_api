package org.example.api.web.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class FileType {

    private static FileType fileType;
    private final Map<String, Integer> types = new HashMap<>();

    private FileType() {
//int(1=字体,
//                2=图片,
//                3=皮肤,
//                4=SO,
//                5=DEX,
//                6=其他)


        types.put(".FNT", 1);
        types.put(".TFT", 1);
        types.put(".OFT", 1);
        types.put(".SO", 4);
        types.put(".DEX", 5);
        types.put(".JPG", 2);
        types.put(".JPEG", 2);
        types.put(".TIFF", 2);
        types.put(".RAW", 2);
        types.put(".BMP", 2);
        types.put(".GIF", 2);
        types.put(".PNG", 2);


    }

    public static FileType getInstance() {
        if (fileType == null) {
            fileType = new FileType();
        }
        return fileType;
    }

    public int get_file_type(String file) {
        Integer integer = types.get(file.toUpperCase(Locale.ROOT));
        if (integer != null) {
            return integer;
        }
        return 6;
    }


}
