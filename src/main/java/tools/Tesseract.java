package tools;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import tools.utils.LanguageTesseract;

import java.awt.image.BufferedImage;

public class Tesseract {

    private static ITesseract instance;

    public static void setUp(String lang){
        try {
            instance = new Tesseract1( );  // JNA Interface Mapping
            instance.setDatapath("src/tessdata"); // path to tessdata directory
            instance.setLanguage(lang);
        }
        catch (Exception e){
            System.out.println("Что-то пошло не так с установкой языка обработки" );
        }
    }

    public void searchText(BufferedImage bufferedImage){

        BufferedImage img = bufferedImage;

        try {
            String result = instance.doOCR(bufferedImage);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }

}
