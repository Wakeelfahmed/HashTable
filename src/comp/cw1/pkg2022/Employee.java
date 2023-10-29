package comp.cw1.pkg2022;

public class Employee implements IEmployee {
    private String name;
    private String affiliation;

    public Employee(String name, String affiliation) {
        this.name = name;
        this.affiliation = affiliation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAffiliation() {
        return affiliation;
    }

    @Override
    public String toString() {
        return name + ", " + affiliation;
    }
}
