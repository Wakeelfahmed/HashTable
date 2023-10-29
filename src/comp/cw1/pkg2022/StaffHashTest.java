package comp.cw1.pkg2022;

public class StaffHashTest {
    public void Run() {
        // Test Case 1: Basic Insertion and Retrieval
        testBasicInsertionAndRetrieval();

        // Test Case 2: Deletion and Re-insertion
        testDeletionAndReinsertion();

        // Test Case 3: Load Factor and Rehashing
        testLoadFactorAndRehashing();

        // Test Case 4: Testing Collisions
        testCollisions();

        // Test Case 5: Displaying an Empty Database
        testDisplayEmptyDatabase();

        // Test Case 5: Testing Size and Empty Check
        testSizeAndEmptyCheck();
        // Test Case 7: Retrieving Non-Existent Employee
        testRetrieveNonExistentEmployee();
        // Test Case 8: Removing Non-Existent Employee
        testRemoveNonExistentEmployee();
    }

    // Test Case 1: Basic Insertion and Retrieval
    public static void testBasicInsertionAndRetrieval() {
        StaffHash staffHash = new StaffHash(10);
        Employee employee1 = new Employee("John, Doe", "HR");
        Employee employee2 = new Employee("Jane, Smith", "Finance");

        staffHash.put(employee1);
        staffHash.put(employee2);

        Employee retrieved1 = staffHash.get("John, Doe");
        Employee retrieved2 = staffHash.get("Jane, Smith");

        if (retrieved1 != null && retrieved1.equals(employee1) && retrieved2 != null && retrieved2.equals(employee2)) {
            System.out.println("Test Case 1 (Basic Insertion and Retrieval): Passed");
        } else {
            System.out.println("Test Case 1 (Basic Insertion and Retrieval): FAILED");
        }
    }

    // Test Case 2: Deletion and Re-insertion
    public static void testDeletionAndReinsertion() {
        StaffHash staffHash = new StaffHash(5);
        Employee employee = new Employee("Alice, Johnson", "Marketing");

        staffHash.put(employee);

        Employee deleted = staffHash.remove("Alice, Johnson");
        Employee notFound = staffHash.remove("Unknown, Employee");

        if (deleted != null && deleted.equals(employee) && notFound == null) {
            // Test re-insertion
            Employee reinserted = new Employee("Alice, Johnson", "Sales");
            staffHash.put(reinserted);

            Employee retrieved = staffHash.get("Alice, Johnson");
            if (retrieved != null && retrieved.equals(reinserted)) {
                System.out.println("Test Case 2 (Deletion and Re-insertion): Passed");
            } else {
                System.out.println("Test Case 2 (Deletion and Re-insertion): FAILED (Re-insertion)");
            }
        } else {
            System.out.println("Test Case 2 (Deletion and Re-insertion): FAILED");
        }
    }

    // Test Case 3: Load Factor and Rehashing
    public static void testLoadFactorAndRehashing() {
        StaffHash staffHash = new StaffHash(5);

        Employee employee1 = new Employee("John, Doe", "HR");
        Employee employee2 = new Employee("Jane, Smith", "Finance");
        Employee employee3 = new Employee("Alice, Johnson", "Marketing");
        Employee employee4 = new Employee("Bob, Brown", "Sales");

        staffHash.put(employee1);
        staffHash.put(employee2);
        staffHash.put(employee3);
        staffHash.put(employee4);

        if (staffHash.size() == 4 && staffHash.get("John, Doe") != null) {
            System.out.println("Test Case 3 (Load Factor and Rehashing): Passed");
        } else {
            System.out.println("Test Case 3 (Load Factor and Rehashing): FAILED");
        }
    }

    // Test Case 4: Testing Collisions
    public static void testCollisions() {
        StaffHash staffHash = new StaffHash(5);

        Employee employee1 = new Employee("John, Doe", "HR");
        Employee employee2 = new Employee("Jane, Smith", "Finance");
        Employee employee3 = new Employee("Jill, Doe", "IT");

        staffHash.put(employee1);
        staffHash.put(employee2);
        staffHash.put(employee3);

        if (staffHash.size() == 3 && staffHash.get("Jill, Doe") != null) {
            System.out.println("Test Case 4 (Testing Collisions): Passed");
        } else {
            System.out.println("Test Case 4 (Testing Collisions): FAILED");
        }
    }

    // Test Case 5: Displaying an Empty Database
    public static void testDisplayEmptyDatabase() {
        StaffHash staffHash = new StaffHash(5);
        staffHash.displayDB();
        System.out.println("Test Case 5 (Displaying an Empty Database): Passed (Visual Check)");
    }
    // Test Case 6: Testing Size and Empty Check
    public static void testSizeAndEmptyCheck() {
        StaffHash staffHash = new StaffHash();

        // Initially, the hash table is empty
        if (staffHash.size() == 0 && staffHash.isEmpty()) {
            System.out.println("Test Case 6 (Size and Empty Check - Empty Table): Passed");
        } else {
            System.out.println("Test Case 6 (Size and Empty Check - Empty Table): FAILED");
        }
        // Insert a single employee and check size and emptiness
        Employee employee = new Employee("Alice, Johnson", "Marketing");
        staffHash.put(employee);

        if (staffHash.size() == 1 && !staffHash.isEmpty()) {
            System.out.println("Test Case 6 (Size and Empty Check - Non-empty Table): Passed");
        } else {
            System.out.println("Test Case 6 (Size and Empty Check - Non-empty Table): FAILED");
        }
    }
    // Test Case 7: Retrieving Non-Existent Employee
    public static void testRetrieveNonExistentEmployee() {
        StaffHash staffHash = new StaffHash();
        Employee employee = new Employee("Alice, Johnson", "Marketing");

        // Try to retrieve an employee that doesn't exist in the table
        Employee retrieved = staffHash.get("Nonexistent, Employee");

        if (retrieved == null) {
            System.out.println("Test Case 7 (Retrieving Non-Existent Employee): Passed");
        } else {
            System.out.println("Test Case 7 (Retrieving Non-Existent Employee): FAILED");
        }
    }
    // Test Case 8: Removing Non-Existent Employee
    public static void testRemoveNonExistentEmployee() {
        StaffHash staffHash = new StaffHash();
        Employee employee = new Employee("Alice, Johnson", "Marketing");

        // Try to remove an employee that doesn't exist in the table
        Employee removed = staffHash.remove("Nonexistent, Employee");

        if (removed == null) {
            System.out.println("Test Case 8 (Removing Non-Existent Employee): Passed");
        } else {
            System.out.println("Test Case 8 (Removing Non-Existent Employee): FAILED");
        }
    }
}
