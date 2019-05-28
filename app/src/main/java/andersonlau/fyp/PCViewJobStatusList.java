package andersonlau.fyp;

public class PCViewJobStatusList {
    private String jobID, RID, jobTitle, jobDescription, teamLeader, jobDone;

    public PCViewJobStatusList(String jobID, String RID, String jobTitle, String jobDescription, String teamLeader, String jobDone) {
        this.jobID = jobID;
        this.RID = RID;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.teamLeader = teamLeader;
        this.jobDone = jobDone;

    }

    public String getJobID() {
        return jobID;
    }

    public String getRID() { return RID;}

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getTeamLeader(){return teamLeader;}

    public String getJobDone(){return jobDone;}


}