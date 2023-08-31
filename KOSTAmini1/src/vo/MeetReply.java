package vo;

import java.sql.Date;

import common.VO;

public class MeetReply extends VO {
    private int no;
    private String content;
    private Date w_date;
    private Date e_date;
    private int in_replay;
    private int meets_no;
    private int members_no;

    public MeetReply() {}
    public MeetReply(int no, String content, Date w_date, Date e_date, int in_replay, int meets_no, int members_no) {
        this.no = no;
        this.content = content;
        this.w_date = w_date;
        this.e_date = e_date;
        this.in_replay = in_replay;
        this.meets_no = meets_no;
        this.members_no = members_no;
    }
    
    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getW_date() {
        return w_date;
    }
    public void setW_date(Date w_date) {
        this.w_date = w_date;
    }
    public Date getE_date() {
        return e_date;
    }
    public void setE_date(Date e_date) {
        this.e_date = e_date;
    }
    public int getIn_replay() {
        return in_replay;
    }
    public void setIn_replay(int in_replay) {
        this.in_replay = in_replay;
    }
    public int getMeets_no() {
        return meets_no;
    }
    public void setMeets_no(int meets_no) {
        this.meets_no = meets_no;
    }
    public int getMembers_no() {
        return members_no;
    }
    public void setMembers_no(int members_no) {
        this.members_no = members_no;
    }

    @Override
    public String toString() {
        return "MeetReply [no=" + no + ", content=" + content + ", w_date=" + w_date + ", e_date=" + e_date
                + ", in_replay=" + in_replay + ", meets_no=" + meets_no + ", members_no=" + members_no + "]";
    }
}
