import java.util.ArrayList;

/**
 * Created by lee on 9/26/16.
 */
public class User {
    private String name;
    private String password;
    private ArrayList<Sandwich> sandwiches = new ArrayList<>();
    int sandwichIndex = 0;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Sandwich> getSandwiches() {
        return sandwiches;
    }
}
