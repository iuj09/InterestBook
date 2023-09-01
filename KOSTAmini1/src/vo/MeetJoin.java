package vo;

import common.VO;

public class MeetJoin extends VO {
    private int memberNo;
    private int meetNo;

    public MeetJoin(int memberNo, int meetNo) {
        this.memberNo = memberNo;
        this.meetNo = meetNo;
    }

    public int getMemberNo() {
        return memberNo;
    }
    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }
    public int getMeetNo() {
        return meetNo;
    }
    public void setMeetNo(int meetNo) {
        this.meetNo = meetNo;
    }

    @Override
    public String toString() {
        return "MeetJoin [memberNo=" + memberNo + ", meetNo=" + meetNo + "]";
    }
}
