package tools.litary;

import java.util.ArrayList;
import java.util.List;

public class Litary {
    private List abc = new ArrayList();
    private List abc_buf = new ArrayList();
    private String currentLitary = "";

    public Litary(){
        abc.add("A");
        abc.add("R");

        abc.add("Q");
//        abc.add("W");
//        abc.add("E");
//        abc.add("T");
//        abc.add("Y");
//        abc.add("U");
//        abc.add("I");
//        abc.add("O");
//        abc.add("P");
//        abc.add("S");
//        abc.add("D");
//        abc.add("F");
//        abc.add("G");
//        abc.add("H");
//        abc.add("J");
//        abc.add("K");
//        abc.add("L");
//        abc.add("Z");
//        abc.add("X");
//        abc.add("C");
//        abc.add("V");
//        abc.add("B");
//        abc.add("N");
//        abc.add("M");

        abc_buf.addAll(abc);
    }

    public String getCurrentLitary() {
        int random_number = 0 + (int) (Math.random() * abc_buf.size());
        currentLitary = (String) abc_buf.get(random_number);
        return currentLitary;
    }

    public void deleteCurrentLitary(){
        abc_buf.remove(currentLitary);
    }
}
