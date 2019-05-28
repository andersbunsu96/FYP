package andersonlau.fyp;

public class technicianViewJobList {
    private String jobID, jobTitle, jobDescription, jobDone;

    public technicianViewJobList(String jobID, String jobTitle, String jobDescription, String jobDone) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobDone = jobDone;

    }

    public String getJobID() {
        return jobID;
    }

    public String getJobTitle() { return jobTitle;}

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobDone(){return jobDone;}


}