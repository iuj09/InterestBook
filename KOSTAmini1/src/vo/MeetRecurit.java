package vo;

public class MeetRecurit {
    private int meetNo;
    private int memberNo;
    private int exc;

    public MeetRecurit() {}
    public MeetRecurit(int meetNo, int memberNo, int exc) {
        this.meetNo = meetNo;
        this.memberNo = memberNo;
        this.exc = exc;
    }

    public int getMeetNo() {
        return meetNo;
    }
    public void setMeetNo(int meetNo) {
        this.meetNo = meetNo;
    }
    public int getMemberNo() {
        return memberNo;
    }
    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }
    public int getExc() {
        return exc;
    }
    public void setExc(int exc) {
        this.exc = exc;
    }

    @Override
    public String toString() {
        return "MeetRecurit [meetNo=" + meetNo + ", memberNo=" + memberNo + ", exc=" + exc + "]";
    }
}
