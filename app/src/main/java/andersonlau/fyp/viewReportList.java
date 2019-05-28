package andersonlau.fyp;

public class viewReportList {
    private String jobDoneID, jobID, RID, message, username_pc, jobTitle, jobDescription, location, imageURL;

    public viewReportList(String jobDoneID, String jobID, String RID, String message, String username_pc, String jobTitle, String jobDescription, String location, String imageURL) {
        this.jobDoneID = jobDoneID;
        this.jobID = jobID;
        this.RID = RID;
        this.message = message;
        this.username_pc = username_pc;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.location = location;
        this.imageURL = imageURL;
    }

    public String getJobDoneID() {
        return jobDoneID;
    }

    public String getJobID() { return jobID;}

    public String getRID() {
        return RID;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername_pc(){return username_pc;}

    public String getJobTitle(){return jobTitle;}

    public String getJobDescription(){return jobDescription;}

    public String getLocation(){return location;}

    public String getImageURL(){return imageURL;}

}