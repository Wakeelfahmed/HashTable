package comp.cw1.pkg2022;
import java.io.FileWriter;
import java.io.IOException;
public class StaffHash implements IStaffDB {
    private Employee[] hashTable;
    private int size;
    private static int capacity = 16;
    private  static final double loadFactorThreshold = 0.5; // 50% load factor threshold
    private FileWriter logFile;
    public StaffHash() {
        this.size = 0;
        this.hashTable = new Employee[16];
        System.out.println("Hash Table");
        try {
            logFile = new FileWriter("log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public StaffHash(int initialCapacity) {
        capacity = initialCapacity;
        this.size = 0;
        this.hashTable = new Employee[initialCapacity];
        System.out.println("Hash Table");
        try {
            logFile = new FileWriter("log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int hash(String name) {
        int hash = 0;
        for (int i = 0; i < name.length(); i++) {
            hash = (hash * 31 + name.charAt(i)) % capacity;
        }
        return hash;
    }
    private int quadraticProbing(int hashCode, int attempt) {
        return (hashCode + (int) Math.pow(attempt, 2)) % capacity;      // Quadratic probing collision resolution
    }
    @Override
    public void clearDB() {
        hashTable = new Employee[capacity];
        size = 0;
    }
    @Override
    public boolean containsName(String name) {
        int index = findEmployee(name);
        return index != -1;
    }
    @Override
    public Employee get(String name) {
        int index = findEmployee(name);
        if (index != -1) {
            return hashTable[index];
        }
        return null;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
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

        size++;

        // Check for rehashing
        double loadFactor = (double) size / capacity;
        if (loadFactor >= loadFactorThreshold) {
            rehash();
        }

        System.out.println("Load Factor: " + String.format("%.2f %%", loadFactor * 100) + "\t\tRecords/Total capacity: " + size +"/" + capacity);
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
            System.out.println("Load Factor: " + String.format("%.2f %%", loadFactor * 100) + "\t\tRecords/Total capacity: " + size +"/" + capacity);
            return removed;
        }
        return null;
    }

    @Override
    public void displayDB() {
        for (Employee employee : hashTable) {
            if (employee != null) {
                System.out.println(employee);
            }
        }    }

    // Helper method to find an employee in the hash table
    private int findEmployee(String name) {
        int hashCode = hash(name);
        int index = hashCode;
        int attempt = 0;

        while (hashTable[index] != null) {
            if (hashTable[index].getName().equals(name)) {
                return index;
            }
            attempt++;
            index = quadraticProbing(hashCode, attempt);
        }
        return -1; // Employee not found
    }

    private void rehash() {
        capacity *= 2;
        Employee[] newHashTable = new Employee[capacity];
        int i = 0;
        for (Employee employee : hashTable) {
//            hashTable[i] = null;
            if (employee != null) {
                int hashCode = hash(employee.getName());
                int index = hashCode;
                int attempt = 0;

                while (newHashTable[index] != null) {
                    attempt++;
                    index = quadraticProbing(hashCode, attempt);
                }

                newHashTable[index] = employee;
            }
            i++;
        }
        hashTable = newHashTable;
    }
    public void logEmployeeInfo(String operation, Employee employee, int hashCode) {
        try {
            logFile.write("Operation: " + operation + "\n");
            logFile.write("Employee Name: " + employee.getName() + "\n");
            logFile.write("Hash Value: " + hashCode + "\n");

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
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
