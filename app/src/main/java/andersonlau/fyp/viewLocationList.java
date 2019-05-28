package andersonlau.fyp;

public class viewLocationList {
    private String username, location;

    public viewLocationList(String username, String location) {
        this.username = username;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public String getLocation() { return location; }
}