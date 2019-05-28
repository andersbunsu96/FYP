package andersonlau.fyp;

public class viewCreatedJobDetailList {
    private String jobID, imageURL, message, userID, username_technician;

    public viewCreatedJobDetailList(String jobID, String imageURL, String message, String userID, String username_technician) {
        this.jobID = jobID;
        this.imageURL = imageURL;
        this.message = message;
        this.userID = userID;
        this.username_technician = username_technician;

    }

    public String getJobID() {
        return jobID;
    }

    public String getImageURL() { return imageURL;}

    public String getMessage() {
        return message;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername_technician(){return username_technician;}
}