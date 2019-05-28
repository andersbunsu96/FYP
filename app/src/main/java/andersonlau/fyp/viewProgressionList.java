package andersonlau.fyp;

public class viewProgressionList {
    private String RID, requestTitle, requestSent, assignmentSent, FODApprove, createJob, technicianAssign, jobDone;

    public viewProgressionList(String RID, String requestTitle, String requestSent, String assignmentSent, String FODApprove, String createJob, String technicianAssign, String jobDone) {
        this.RID = RID;
        this.requestTitle = requestTitle;
        this.requestSent = requestSent;
        this.assignmentSent = assignmentSent;
        this.FODApprove = FODApprove;
        this.createJob = createJob;
        this.technicianAssign = technicianAssign;
        this.jobDone = jobDone;
    }

    public String getRID() {
        return RID;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public String getRequestSent(){return requestSent;}

    public String getAssignmentSent(){return assignmentSent;}

    public String getFODApprove(){return FODApprove;}

    public String getCreateJob(){return createJob;}

    public String getTechnicianAssign(){return technicianAssign;}

    public String getJobDone(){return jobDone;}

}