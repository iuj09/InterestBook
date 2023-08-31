package vo;

import java.sql.Date;

import common.VO;

public class Meet extends VO {
    private int no;
    private int recurit;
    private String title;
    private String content;
    private Date wDate;
    private Date eDate;
    private Date deadLine;
    private int locationNo;
    private int memberNo;

    public Meet() {}

    public Meet(int no, int recurit, String title, String content, Date wDate, Date eDate, Date deadLine,
            int locationNo, int memberNo) {
        this.no = no;
        this.recurit = recurit;
        this.title = title;
        this.content = content;
        this.wDate = wDate;
        this.eDate = eDate;
        this.deadLine = deadLine;
        this.locationNo = locationNo;
        this.memberNo = memberNo;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getRecurit() {
        return recurit;
    }

    public void setRecurit(int recurit) {
        this.recurit = recurit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getwDate() {
        return wDate;
    }

    public void setwDate(Date wDate) {
        this.wDate = wDate;
    }

    public Date geteDate() {
        return eDate;
    }

    public void seteDate(Date eDate) {
        this.eDate = eDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public int getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(int locationNo) {
        this.locationNo = locationNo;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    @Override
    public String toString() {
        return "Meet [no=" + no + ", recurit=" + recurit + ", title=" + title + ", content=" + content + ", wDate="
                + wDate + ", eDate=" + eDate + ", deadLine=" + deadLine + ", locationNo=" + locationNo + ", memberNo="
                + memberNo + "]";
    }
}
