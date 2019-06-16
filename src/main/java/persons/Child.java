package persons;

public class Child {
    private int id;
    private String name;
    private String old;

    public Child() {

    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getOld() {
        return old;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public void setId(int id) {
        this.id = id;
    }
}
