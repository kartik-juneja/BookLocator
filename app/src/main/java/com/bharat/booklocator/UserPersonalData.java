package com.bharat.booklocator;

public class UserPersonalData {
    private  String Branch,semester,RollNo;

    public UserPersonalData() {
    }

    public UserPersonalData(String branch, String semester, String rollNo) {
        Branch = branch;
        this.semester = semester;
        RollNo = rollNo;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }
}
