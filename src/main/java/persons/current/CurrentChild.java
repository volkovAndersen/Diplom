package persons.current;

public class CurrentChild {

    private static int idOfCurrentChild = 1211; //просто заглушка

    public static void setIdOfCurrentChild(int idOfCurrentChild) {
        CurrentChild.idOfCurrentChild = idOfCurrentChild;
    }

    public static int getIdOfCurrentChild() {
        return idOfCurrentChild;
    }
}
