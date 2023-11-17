package comp.cw1.pkg2022;
import java.io.FileWriter;      //For Log File
import java.io.IOException;

import java.util.Arrays;        //To alphabetically sort the array before displaying
import java.util.Comparator;    //To alphabetically sort the array before displaying

public class StaffHash implements IStaffDB {
    private Employee[] hashTable;               // The hash table to store records
    private int size;                           // Number of records in the hash table
    private int capacity;                           // Current capacity of the hash table
    private final double loadFactorThreshold = 0.5; // 50% load factor threshold
    private static FileWriter logFile;

    static {
        try {
            logFile = new FileWriter("log.txt", false); // Use "true" to append to the existing log file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Default constructor
    public StaffHash() {
        this.size = 0;
        this.capacity = 16; //default capacity
        this.hashTable = new Employee[capacity];
        System.out.println("Hash Table");
    }
    // Constructor with an initial capacity
    public StaffHash(int initialCapacity) {
        capacity = initialCapacity;
        this.size = 0;
        this.hashTable = new Employee[initialCapacity];
        System.out.println("Hash Table");
    }
    // Hashing function to calculate the index for a given name
    private int hash(String name) {
        int hash = 0;
        for (int i = 0; i < name.length(); i++) {
            hash = (hash * 31 + name.charAt(i)) % capacity;
        }
        return hash;
    }
    // Hashing function to calculate the index for a given name
    private int quadraticProbing(int hash_Value, int attempt) {
        return (hash_Value + (int) Math.pow(attempt, 2)) % capacity;      // Quadratic probing collision resolution
    }

    @Override
    public void clearDB() { // Clear the hash table
        hashTable = new Employee[capacity];
        size = 0;
    }

    @Override
    public boolean containsName(String name) {
        int index = findEmployee(name); // Check if an employee with a given name exists
        return index != -1;
    }

    @Override
    public Employee get(String name) {  // Get the employee with a given name
        int index = findEmployee(name);
        if (index != -1) {
            return hashTable[index];
        }
        return null;
    }

    @Override
    public int size() { return size; } // Get the number of employees in the hash table
    public int getCapacity() { return capacity; }       //Get the current capacity of the hash table
    @Override
    public boolean isEmpty() {
        return size == 0;
    }       //Check if the hash table is empty

    @Override
    public Employee put(Employee employee) {
        if (employee == null || employee.getName() == null || employee.getName().isEmpty()) {
            return null;
        }
        int hashCode = hash(employee.getName());
        int index = hashCode;
        int attempt = 0;

        while (hashTable[index] != null && !hashTable[index].getName().equals(employee.getName())) {
            attempt++;
            if (attempt >= capacity) {
                // All available buckets are occupied, rehash the table
                rehash();
                // Recalculate the index and attempt after rehashing
                index = hashCode;
                attempt = 0;
            }
            index = quadraticProbing(hashCode, attempt);
        }
        Employee previous = hashTable[index];
        hashTable[index] = employee;
        logEmployeeInfo("Addition", employee, hashCode);

        if(previous != null && previous.getName().equals(employee.getName()))
            size--;
        size++;

        // Check for rehashing
        double loadFactor = (double) size / capacity;
        if (loadFactor >= loadFactorThreshold) {
            rehash();
        }

        System.out.println("Load Factor: " + String.format("%.2f %%", loadFactor * 100) + "\t\tRecords/Total capacity: " + size + "/" + capacity);
        return previous;
    }

    @Override
    public Employee remove(String name) {
        int index = findEmployee(name);
        if (index != -1) {
            Employee removed = hashTable[index];
            hashTable[index] = null;
            size--;
            logEmployeeInfo("remove", removed, hash(name));
            double loadFactor = (double) size / capacity;
            System.out.println("Load Factor: " + String.format("%.2f %%", loadFactor * 100) + "\t\tRecords/Total capacity: " + size + "/" + capacity);
            return removed;
        }
        return null;
    }

    @Override
    public void displayDB() {
        Employee[] employeeArray = new Employee[size];

        int index = 0;
        for (Employee employee : hashTable) {
            if (employee != null) {
                employeeArray[index] = employee;
                index++;
            }
        }

        // Sort the array in alphabetical order based on the first name
        Arrays.sort(employeeArray, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                // Split the names by comma and compare the first names
                String[] nameParts1 = e1.getName().split(", ");
                String[] nameParts2 = e2.getName().split(", ");
                String firstName1 = nameParts1[0];
                String firstName2 = nameParts2[0];

                return firstName1.compareTo(firstName2);
            }
        });

        for (Employee employee : employeeArray) {
            System.out.println(employee);
        }
    }

    // Helper method to find an employee in the hash table
    private int findEmployee(String name) {//fln
        int hash_Value = hash(name);
        int index = hash_Value;
        int attempt = 0;

        while (hashTable[index] != null) {
            if (hashTable[index].getName().equals(name)) {
                return index;
            }
            attempt++;
            index = quadraticProbing(hash_Value, attempt);
        }
        return -1; // Employee not found
    }

    private void rehash() {
        capacity *= 2;
        Employee[] newHashTable = new Employee[capacity];
        for (Employee employee : hashTable) {
            if (employee != null) {
                int hash_Value = hash(employee.getName());
                int index = hash_Value;
                int attempt = 0;

                while (newHashTable[index] != null) {
                    attempt++;
                    index = quadraticProbing(hash_Value, attempt);
                }

                newHashTable[index] = employee;
            }
        }
        hashTable = newHashTable;
    }

    public void logEmployeeInfo(String operation, Employee employee, int hash_Value) {
        try {
            logFile.write("Operation: " + operation + "\n");
            logFile.write("Employee Name: " + employee.getName() + "\n");
            logFile.write("Hash Value: " + hash_Value + "\n");

            int attempt = 0;
            int hashCodeOriginal = hash(employee.getName());
            int index = hashCodeOriginal;

            while (hashTable[index] != null) {
                logFile.write("Bucket " + attempt + ": " + index + "\n");
                if (hashTable[index].getName().equals(employee.getName())) {
                    // Employee found, no need to continue probing
                    break;
                }
                attempt++;
                index = quadraticProbing(hashCodeOriginal, attempt);
            }
            logFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
