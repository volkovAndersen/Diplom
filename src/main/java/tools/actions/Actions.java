package tools.actions;

import main.MainApp;
import tools.windows.OpenNewWindow;

public class Actions {

    public static void goTo(OpenNewWindow window, String path){
       window = new OpenNewWindow();

       try {
            window.openNewScene(path, MainApp.getStage());
        }
        catch (Exception e){
            System.out.println("Что-то пошло не так открытием страницы: " + path);
        }
    }
}
