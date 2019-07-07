package com.spring.dao;

import com.spring.bean.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class EmployeeDAO {
	JdbcTemplate jdbcTemplate;
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USER = "system";
	public static final String PASS = "1234";

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int saveEmployee(Employee emp) {
		String sql = "insert into Emp(emp_id,emp_name,emp_sal,designation) values('" + emp.getId() + "','"
				+ emp.getName() + "'," + emp.getSalary() + ",'" + emp.getDesignation() + "')";

		return this.jdbcTemplate.update(sql);
	}

	/*
	 * public Employee getEmpById(Integer empId) {
	 * System.out.println("empid in dao :" + empId);
	 * 
	 * String sql = "select * from emp where emp_id=?";
	 * 
	 * Employee employee = null; try { employee = (Employee)
	 * this.jdbcTemplate.queryForObject(sql, new Object[] { empId }, new
	 * BeanPropertyRowMapper(Employee.class));
	 * System.out.println("empid in dao before return :" + employee.getId());
	 * System.out.println("empname in dao before return :" + employee.getName());
	 * System.out.println("empdes in dao before return :" +
	 * employee.getDesignation());
	 * System.out.println("empsal in dao before return :" + employee.getSalary()); }
	 * catch (EmptyResultDataAccessException e) { e.printStackTrace(); } return
	 * employee; }
	 */

	public int update(Employee employee) {
		String sql = "update emp set emp_name='" + employee.getName() + "', emp_sal=" + employee.getSalary()
				+ ", designation='" + employee.getDesignation() + "' where emp_id=" + employee.getId();
		int i = this.jdbcTemplate.update(sql);
		try {
			this.jdbcTemplate.getDataSource().getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public int delete(Integer empId) {
		String sql = "delete from emp where emp_id=" + empId;
		return this.jdbcTemplate.update(sql);
	}

	public List<Employee> getEmployees() {
		return this.jdbcTemplate.query("select * from emp order by emp_id", new RowMapper() {
			public Employee mapRow(ResultSet rs, int row) throws SQLException {
				Employee e = new Employee();
				e.setId(Integer.valueOf(rs.getInt(1)));
				e.setName(rs.getString(2));
				e.setSalary(Double.valueOf(rs.getDouble(3)));
				e.setDesignation(rs.getString(4));
				return e;
			}
		});
	}

	public Employee getEmpByIdJDBC(Integer empId) throws ClassNotFoundException {
		Employee employee = new Employee();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select * from emp where emp_id=?";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");
			ps = connection.prepareStatement(sql);
			ps.setInt(1, empId.intValue());
			rs = ps.executeQuery();
			if (rs.next()) {
				employee.setId(Integer.valueOf(rs.getInt(1)));
				employee.setName(rs.getString(2));
				employee.setSalary(Double.valueOf(rs.getDouble(3)));
				employee.setDesignation(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}
}
