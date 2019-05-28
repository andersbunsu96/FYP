package andersonlau.fyp;

public class viewCreatedJobList {
    private String jobID, jobTitle, jobDescription, teamLeader, location, jobDone;

    public viewCreatedJobList(String jobID, String jobTitle, String jobDescription, String teamLeader, String location, String jobDone) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.teamLeader = teamLeader;
        this.location = location;
        this.jobDone = jobDone;
    }

    public String getJobID() {
        return jobID;
    }

    public String getJobTitle() { return jobTitle;}

    public String getJobDescription() {
        return jobDescription;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public String getLocation(){return location;}

    public String getJobDone(){return jobDone;}


}