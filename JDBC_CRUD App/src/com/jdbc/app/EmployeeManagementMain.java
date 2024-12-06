package com.jdbc.app;

import java.util.List;

public class EmployeeManagementMain {

    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();

        // Add employees only if they don't already exist
        Employee emp1 = new Employee("Ram", "IT", 98500);
        Employee emp2 = new Employee("Sham", "HR", 87980);
        Employee emp3 = new Employee("Sushant", "IT", 125000);

        if (!repository.employeeExists("Ram", "IT")) {
            repository.addNewEmployee(emp1);
        }
        if (!repository.employeeExists("Sham", "HR")) {
            repository.addNewEmployee(emp2);
        }
        if (!repository.employeeExists("Sushant", "IT")) {
            repository.addNewEmployee(emp3);
        }

        // Fetch and print all employees
        System.out.println("Fetching all employees:");
        List<Employee> employees = repository.getAllEmployees();
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        // Update an employee with ID 2
        Employee updatedEmp = new Employee("Radha", "HR", 95000);
        boolean isUpdated = repository.updateEmployee(14, updatedEmp);
        if (isUpdated) {
            System.out.println("Employee details updated successfully.");
        } else {
            System.out.println("Failed to update employee details.");
        }

        // Fetch and print all employees after the update
        System.out.println("\nFetching all employees after update:");
        employees = repository.getAllEmployees();
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        // Delete an employee with ID 1
        boolean isDeleted = repository.deleteEmployee(13);
        if (isDeleted) {
            System.out.println("\nEmployee with ID 1 has been deleted.");
        }

        // Fetch and print all employees after deletion
        System.out.println("\nFetching all employees after deletion:");
        employees = repository.getAllEmployees();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
}
