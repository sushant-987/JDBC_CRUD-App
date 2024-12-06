package com.jdbc.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

	private static final String dbUrl = "jdbc:mysql://localhost:3306/my_first_db";
	private static final String dbUname = "root";
	private static final String dbUpwd = "root";

	Connection connection = null;
	{
		System.out.println("Inside instance block of EmployeeRepository!!");
		try {
			connection = DriverManager.getConnection(dbUrl, dbUname, dbUpwd);
			System.out.println("Connection to Database established successfully..!!");

		} catch (SQLException e) {
			System.out.println("Some error occurred during connection to Database..!!" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Some error occurred!!" + e.getMessage());
		}
	}

	// CRUD - R - Read
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		String selectQuery = "SELECT * FROM employee"; // Query to fetch all employees
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				// Mapping result set to Employee object
				Employee employee = new Employee(resultSet.getInt("id"), // Assuming 'id' column exists
						resultSet.getString("name"), resultSet.getString("dept"), resultSet.getInt("salary"));
				employees.add(employee);
			}

			if (employees.isEmpty()) {
				System.out.println("No employees found in the database.");
			}

		} catch (SQLException e) {
			System.out.println("Error while fetching employees: " + e.getMessage());
			e.printStackTrace();
		}
		return employees;
	}

	// CRUD - C - Create or ADD

	public boolean addNewEmployee(Employee employee) {

		String insertEmployeeQuery = "Insert into employee (name,dept,salary) values(?,?,?)";
		boolean result = false;
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(insertEmployeeQuery);
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setString(2, employee.getDept());
			preparedStatement.setInt(3, employee.getSalary());

			int res = preparedStatement.executeUpdate();

			if (res > 0) {
				System.out.println("Employee record added to Database Successfully..!!");
				result = true;
			}

			else
				System.out.println("Employee record not added to Database..!!");

		} catch (SQLException e) {
			System.out.println(
					"Some error occurred while executing PreparedStatement for insertEmployee !!" + e.getMessage());

		}
		return result;
	}

	// CRUD - U - Update
	public boolean updateEmployee(int id, Employee updatedEmployee) {
		String updateQuery = "UPDATE employee SET name = ?, dept = ?, salary = ? WHERE id = ?";
		boolean isUpdated = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setString(1, updatedEmployee.getName());
			preparedStatement.setString(2, updatedEmployee.getDept());
			preparedStatement.setInt(3, updatedEmployee.getSalary());
			preparedStatement.setInt(4, id);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Employee record with ID " + id + " updated successfully!");
				isUpdated = true;
			} else {
				System.out.println("Employee with ID " + id + " not found!");
			}

		} catch (SQLException e) {
			System.out.println("Error while updating employee: " + e.getMessage());
			e.printStackTrace();
		}
		return isUpdated;
	}

	// CRUD - D - Delete
	public boolean deleteEmployee(int id) {
		String deleteQuery = "DELETE FROM employee WHERE id = ?";
		boolean isDeleted = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, id);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Employee record with ID " + id + " deleted successfully!");
				isDeleted = true;
			} else {
				System.out.println("Employee with ID " + id + " not found!");
			}

		} catch (SQLException e) {
			System.out.println("Error while deleting employee: " + e.getMessage());
			e.printStackTrace();
		}
		return isDeleted;
	}

	// Check if an employee already exists
	public boolean employeeExists(String name, String dept) {
		String query = "SELECT COUNT(*) FROM employee WHERE name = ? AND dept = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, dept);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next() && resultSet.getInt(1) > 0) {
				return true; // Employee exists
			}
		} catch (SQLException e) {
			System.out.println("Error while checking employee existence: " + e.getMessage());
			e.printStackTrace();
		}
		return false; // Employee does not exist
	}

}
