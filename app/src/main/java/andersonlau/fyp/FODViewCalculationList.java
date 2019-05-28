package andersonlau.fyp;

public class FODViewCalculationList {
    private String calcID, AID, username_pc, username_fc, calcDesc, approved;
    public FODViewCalculationList(String calcID, String AID, String username_fc, String username_pc, String calcDesc, String approved) {
        this.calcID = calcID;
        this.AID = AID;
        this.username_fc = username_fc;
        this.username_pc = username_pc;
        this.calcDesc = calcDesc;
        this.approved = approved;

    }

    public String getCalcID() { return calcID; }
    public String getAID() {
        return AID;
    }
    public String getUsername_fc() {
        return username_fc;
    }
    public String getUsername_pc() {
        return username_pc;
    }
    public String getCalcDesc() {
        return calcDesc;
    }
    public String getApproved() {return approved;}
}