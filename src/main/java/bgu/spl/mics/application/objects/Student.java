package bgu.spl.mics.application.objects;

/**
 * Passive object representing single student.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Student {
    /**
     * Enum representing the Degree the student is studying for.
     */
    enum Degree {
        MSc, PhD
    }

    private String name;
    private String department;
    private Degree status;
    private int publications;
    private int papersRead;


    public Student(String name, String department, Degree status, int publications, int papersRead) {
        this.name = name;
        this.department = department;
        this.status = status;
        this.publications = publications;
        this.papersRead = papersRead;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDepartment() {return department;}

    public void setDepartment(String department) {this.department = department;}

    public Degree getStatus() {return status;}

    public void setStatus(Degree status) {this.status = status;}

    public int getPublications() {return publications;}

    public void setPublications(int publications) {this.publications = publications;}

    public int getPapersRead() {return papersRead;}

    public void setPapersRead(int papersRead) {this.papersRead = papersRead;}

    public void addPapersRead(int add) {this.papersRead += add;}

    public void addPublications(int add) {this.publications += add;}

}
