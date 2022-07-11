package org.example.api.common.io;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public final class ImageConvert {
    private static  ImageConvert instance;
    public static ImageConvert getInstance() {
        if (instance == null) {
            instance = new ImageConvert();
        }
        return  instance;
    }

    public  void png2jpeg(String png,String jpg) {
        //读取图片
        FileOutputStream fos =null;
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(png));
            //转成jpeg、
            BufferedImage bufferedImage1 = new BufferedImage(bufferedImage.getWidth(),
                                                             bufferedImage.getHeight(),
                                                             BufferedImage.TYPE_INT_RGB);
            bufferedImage1.createGraphics().drawImage(bufferedImage,0,0, Color.white,null);
            fos = new FileOutputStream(jpg);
            ImageIO.write(bufferedImage,"jpg",fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                assert fos != null;
                fos.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public  BufferedImage png2jpeg(byte[] png) {
        //读取图片
            ByteArrayInputStream in =new ByteArrayInputStream(png);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //转成jpeg、
            BufferedImage bufferedImage1 = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            bufferedImage1.createGraphics().drawImage(bufferedImage,0,0, Color.white,null);
//            fos = new FileOutputStream(jpg);
//            ImageIO.w(bufferedImage,"jpg",fos);
//            fos.flush();
            return bufferedImage1;
    }


    public  void jpeg2png(String jpg,String png) {
        //读取图片
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(png));
            //转成png、
            BufferedImage bufferedImage1 = new BufferedImage(bufferedImage.getWidth(),
                                                            bufferedImage.getHeight(),
                                                            BufferedImage.TYPE_INT_ARGB);
            bufferedImage1.createGraphics().drawImage(bufferedImage,0,0, Color.white,null);
            FileOutputStream fos = new FileOutputStream(png);
            ImageIO.write(bufferedImage1,"png",fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}