package tools.utils;

public class LanguageTesseract {

    private static String lang = "";

    public static String getLang() {
        return lang;
    }

    public static void setLang(String lang) {
        LanguageTesseract.lang = lang;
    }
}
