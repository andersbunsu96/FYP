package andersonlau.fyp;

public class FCViewAssignmentList {
    private String ID, RID, assignmentName, assignmentDesc, FOD_approved, jobCreated;

    public FCViewAssignmentList(String RID, String assignmentName, String assignmentDesc, String ID, String FOD_approved, String jobCreated) {
        this.RID = RID;
        this.assignmentName = assignmentName;
        this.assignmentDesc = assignmentDesc;
        this.ID = ID;
        this.FOD_approved = FOD_approved;
        this.jobCreated = jobCreated;

    }

    public String getRID() {
        return RID;
    }

    public String getID() { return ID;}

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getAssignmentDesc() {
        return assignmentDesc;
    }

    public String getFOD_approved(){return FOD_approved;}

    public String getJobCreated(){return jobCreated;}


}