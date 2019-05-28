package andersonlau.fyp;

public class viewRecordedRequestList {
    private String RID, requestTitle, requestDescription;
    public viewRecordedRequestList(String RID, String requestTitle, String requestDescription) {
        this.RID = RID;
        this.requestTitle = requestTitle;
        this.requestDescription = requestDescription;

    }

    public String getRID() {
        return RID;
    }
    public String getRequestTitle() {
        return requestTitle;
    }
    public String getRequestDescription() {
        return requestDescription;
    }
}