package comp.cw1.pkg2022;

public interface IStaffDB {
    public void clearDB();

    public boolean containsName(String name);

    public Employee get(String name);

    public int size();

    public boolean isEmpty();

    public Employee put(Employee employee);

    public Employee remove(String name);

    public void displayDB();
}
