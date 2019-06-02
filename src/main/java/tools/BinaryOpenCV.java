package tools;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class BinaryOpenCV {

    public BufferedImage toBinary(BufferedImage bufferedImage) throws Exception {
        // Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // Instantiating the Imgcodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        // File input = new File("C:/EXAMPLES/OpenCV/sample.jpg");
        String input = "images/greyPhoto.jpg";

        // Reading the image
        Mat src = imageCodecs.imread(input);

        // Creating the destination matrix
        Mat dst = new Mat();

//        //Converting the image to gray sacle and saving it in the dst matrix
//        Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);

        // Converting to binary image...
        Imgproc.threshold(src, dst, 200, 500, Imgproc.THRESH_BINARY);

        // Extracting data from the transformed image (dst)
        byte[] data1 = new byte[dst.rows() * dst.cols() * (int)(dst.elemSize())];
        dst.get(0, 0, data1);

        // Creating Buffered image using the data
        BufferedImage bufImage = new BufferedImage(dst.cols(),dst.rows(),
                BufferedImage.TYPE_BYTE_GRAY);

        ImageIO.write(bufImage, "jpg",new File("images/blackwhiteimage.jpg") );



        // Setting the data elements to the image
        bufImage.getRaster().setDataElements(0, 0, dst.cols(), dst.rows(), data1);

        // Creating a Writable image
        WritableImage writableImage = SwingFXUtils.toFXImage(bufImage, null);

        System.out.println("Converted to binary");

        return  bufImage;
//
//
//        return writableImage;
    }
}
