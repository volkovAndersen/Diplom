package tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Grey {

    public BufferedImage toGrey(BufferedImage img){
//        BufferedImage img = null;
//        File f = null;
//
//        //read image
//        try{
//            f = new File(String.format("images/" + path));
//            img = ImageIO.read(f);
//        }catch(IOException e){
//            System.out.println(e);
//        }

        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        //convert to grayscale
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //calculate average
                int avg = (r+g+b)/3;

                //replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, p);
            }
        }

//        //write image
//        try{
//            f = new File("images/greyPhoto.jpg");
//            ImageIO.write(img, "jpg", f);
//        }catch(IOException e){
//            System.out.println(e);
//        }
//
//        System.out.println("Converted to grey");

        return img;
    }

}
