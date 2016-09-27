/**
 * Created by lee on 9/26/16.
 */
public class Sandwich {
    private String sandwichName;
    private int id;
    private boolean display = true;
    private boolean mine = false;

    public Sandwich(String sandwichName, int id) {
        this.sandwichName = sandwichName;
        this.id = id;
    }

    public String getSandwichName() {
        return sandwichName;
    }

    public int getId() {
        return id;
    }

    public boolean isDisplay() {
        return display;
    }

    public boolean isMine() {
        return mine;
    }

    public void setSandwichName(String sandwichName) {
        this.sandwichName = sandwichName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
