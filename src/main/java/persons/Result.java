package persons;

public class Result {
    private int id;
    private int result;
    private String date;
    private String nameChild;

    public int getId() {
        return id;
    }

    public String getNameChild() {
        return nameChild;
    }

    public int getResult() {
        return result;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setNameChild(String nameChild) {
        this.nameChild = nameChild;
    }

    public void printInfo(){
        System.out.println(getId() );
        System.out.println(getResult() );
        System.out.println(getDate() );
        System.out.println(getNameChild() );
    }
}
