package comp.cw1.pkg2022;

public class StaffHashTest {
    public void Run() {
        testBasicInsertionAndRetrieval();   // Test Case 1: Basic Insertion and Retrieval
        testDeletionAndReinsertion();       // Test Case 2: Deletion and Re-insertion
        testLoadFactorAndRehashing();       // Test Case 3: Load Factor and Rehashing
        testCollisions();                   // Test Case 4: Testing Collisions
        testDisplayEmptyDatabase();         // Test Case 5: Displaying an Empty Database
        testSizeAndEmptyCheck();            // Test Case 5: Testing Size and Empty Check
        testRetrieveNonExistentEmployee();  // Test Case 7: Retrieving Non-Existent Employee
        testRemoveNonExistentEmployee();    // Test Case 8: Removing Non-Existent Employee
    }
    // Test Case 1: Basic Insertion and Retrieval
    public static void testBasicInsertionAndRetrieval() {
        StaffHash staffHash = new StaffHash(10);
        System.out.println("\nTest 1");
        Employee employee1 = new Employee("John, Doe", "HR");
        Employee employee2 = new Employee("Jane, Smith", "Finance");

        System.out.println("Inserting \"John, Doe\", HR\"\t\t");
        staffHash.put(employee1);
        System.out.println("Inserting \"Jane, Smith, Finance\"\t\t");
        staffHash.put(employee2);

        Employee retrieved1 = staffHash.get("John, Doe");
        Employee retrieved2 = staffHash.get("Jane, Smith");

        System.out.print("Retrieving \"John, Doe\"" + "\t\tGot -> \t");
        System.out.println(retrieved1.toString());

        System.out.print("Retrieving \"Jane, Smith\"" + "\t\tGot -> \t");
        System.out.println(retrieved2.toString());

        if (retrieved1 != null && retrieved1.equals(employee1) && retrieved2 != null && retrieved2.equals(employee2)) {
            System.out.println("Test Case 1 (Basic Insertion and Retrieval): Passed");
        } else {
            System.out.println("Test Case 1 (Basic Insertion and Retrieval): FAILED");
        }
    }

    // Test Case 2: Deletion and Re-insertion
    public static void testDeletionAndReinsertion() {
        System.out.println("\nTest 2");
        StaffHash staffHash = new StaffHash(5);
        Employee employee = new Employee("Alice, Johnson", "Marketing");

        System.out.println("Inserting \"Alice, Johnson, Marketing\"");
        staffHash.put(employee);

        System.out.println("Deleting \"Alice, Johnson\"");
        Employee deleted = staffHash.remove("Alice, Johnson");

        System.out.println("Deleting \"Unknown, Employee\"");
        Employee notFound = staffHash.remove("Unknown, Employee");
        System.out.print("Unknown, Employee");
        if (notFound != null) {
            System.out.println(" deleted");
        } else {
            System.out.println(" not found");
        }

        if (deleted != null && deleted.equals(employee) && notFound == null) {
            // Test re-insertion
            Employee reinserted = new Employee("Alice, Johnson", "Sales");
            System.out.println("Inserting \"Alice, Johnson, Sales\"");
            staffHash.put(reinserted);

            Employee retrieved = staffHash.get("Alice, Johnson");
            System.out.print("Retrieving \"Alice, Johnson\"\t\tGot -> ");
            System.out.println(retrieved.toString());

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
        System.out.println("\nTest 3");
        StaffHash staffHash = new StaffHash(5);

        Employee employee1 = new Employee("John, Doe", "HR");
        Employee employee2 = new Employee("Jane, Smith", "Finance");
        Employee employee3 = new Employee("Alice, Johnson", "Marketing");
        Employee employee4 = new Employee("Bob, Brown", "Sales");

        System.out.println("Hashtable Capacity at Start :" + staffHash.getCapacity());
        System.out.println("Inserting \"John, Doe, HR\"\t\t");
        staffHash.put(employee1);
        System.out.println("Inserting \"Jane, Smith, Finance\"\t\t");
        staffHash.put(employee2);
        System.out.println("Inserting \"Alice, Johnson, Marketing\"\t\t");
        staffHash.put(employee3);
        System.out.println("Inserting \"Bob, Brown, Sales\"\t\t");
        staffHash.put(employee4);
        System.out.println("Hashtable Capacity after 4 records inserted :" + staffHash.getCapacity());

        if (staffHash.size() == 4 && staffHash.get("John, Doe") != null) {
            System.out.println("Test Case 3 (Load Factor and Rehashing): Passed");
        } else {
            System.out.println("Test Case 3 (Load Factor and Rehashing): FAILED");
        }
    }
    // Test Case 4: Testing Collisions
    public static void testCollisions() {
        System.out.println("\nTest 4");
        StaffHash staffHash = new StaffHash(5);

        Employee employee1 = new Employee("John, Doe", "HR");
        Employee employee2 = new Employee("Jane, Smith", "Finance");
        Employee employee3 = new Employee("Jill, Doe", "IT");

        System.out.println("Inserting \"John, Doe\", HR\"");
        staffHash.put(employee1);
        System.out.println("Inserting \"Jane, Smith, Finance\"");
        staffHash.put(employee2);
        System.out.println("Inserting \"Jill, Doe, IT\"");
        staffHash.put(employee3);

        if (staffHash.size() == 3 && staffHash.get("Jill, Doe") != null && staffHash.get("Jane, Smith") != null) {
            System.out.println("Test Case 4 (Testing Collisions): Passed");
        } else {
            System.out.println("Test Case 4 (Testing Collisions): FAILED");
        }
    }

    // Test Case 5: Displaying an Empty Database
    public static void testDisplayEmptyDatabase() {
        System.out.println("\nTest 5");
        StaffHash staffHash = new StaffHash(5);
        staffHash.displayDB();
        System.out.println("Test Case 5 (Displaying an Empty Database): Passed (Visual Check)");
    }
    // Test Case 6: Testing Size and Empty Check
    public static void testSizeAndEmptyCheck() {
        System.out.println("\nTest 6");
        StaffHash staffHash = new StaffHash();

        // Initially, the hash table is empty
        if (staffHash.isEmpty() && staffHash.isEmpty()) {
            System.out.println("Test Case 6 (Size and Empty Check - Empty Table): Passed");
        } else {
            System.out.println("Test Case 6 (Size and Empty Check - Empty Table): FAILED");
        }
        // Insert a single employee and check size and emptiness
        Employee employee = new Employee("Alice, Johnson", "Marketing");
        System.out.println("Inserting \"John, Doe\", HR\"");
        staffHash.put(employee);

        if (staffHash.size() == 1 && !staffHash.isEmpty()) {
            System.out.println("Test Case 6 (Size and Empty Check - Non-empty Table): Passed");
        } else {
            System.out.println("Test Case 6 (Size and Empty Check - Non-empty Table): FAILED");
        }
    }
    // Test Case 7: Retrieving Non-Existent Employee
    public static void testRetrieveNonExistentEmployee() {
        System.out.println("\nTest 7");
        StaffHash staffHash = new StaffHash();

        // Try to retrieve an employee that doesn't exist in the table
        Employee retrieved = staffHash.get("Nonexistent, Employee");
        System.out.print("Retrieving \"Nonexistent, Employee\"\t\tGot ->\t");
        if (retrieved == null) {
            System.out.println("Not Found");
            System.out.println("Test Case 7 (Retrieving Non-Existent Employee): Passed");
        } else {
            System.out.println("Found");
            System.out.println("Test Case 7 (Retrieving Non-Existent Employee): FAILED");
        }
    }
    // Test Case 8: Removing Non-Existent Employee
    public static void testRemoveNonExistentEmployee() {
        System.out.println("\nTest 8");
        StaffHash staffHash = new StaffHash();

        // Try to remove an employee that doesn't exist in the table
        Employee removed = staffHash.remove("Nonexistent, Employee");

        if (removed == null) {
            System.out.println("Test Case 8 (Removing Non-Existent Employee): Passed");
        } else {
            System.out.println("Test Case 8 (Removing Non-Existent Employee): FAILED");
        }
    }
}
