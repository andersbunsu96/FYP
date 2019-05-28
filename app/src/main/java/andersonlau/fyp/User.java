package andersonlau.fyp;

public class User {
    private int id;
    private String name, userType;

    public User(int id, String name, String userType) {
        this.id = id;
        this.userType = userType;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}