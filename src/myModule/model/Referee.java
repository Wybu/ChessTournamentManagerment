package myModule.model;

public class Referee {
    private int ID;
    private String name;
    private String dob;
    private String nationality;

    public Referee(int ID, String name, String dob, String nationality) {
        this.ID = ID;
        this.name = name;
        this.dob = dob;
        this.nationality = nationality;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return dob;
    }

    public void setDOB(String dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}