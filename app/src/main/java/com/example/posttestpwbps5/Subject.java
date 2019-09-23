package com.example.posttestpwbps5;

public class Subject {

    String curDate;
    String subId;
    String subName;
    String subDesk;

    public Subject(){

    }

    public Subject(String curDate, String subId, String subName, String subDesk){
        this.curDate = curDate;
        this.subId = subId;
        this.subName = subName;
        this.subDesk = subDesk;

    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubDesk() {
        return subDesk;
    }

    public void setSubDesk(String subDesk) {
        this.subDesk = subDesk;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }
}
