package com.spring.controller;

import com.spring.bean.Employee;
import com.spring.dao.EmployeeDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController {
	@Autowired
	EmployeeDAO employeeDAO;

	@RequestMapping({ "/showForm" })
	public String showEmployeeForm(Model model) {
		model.addAttribute("loadForm", new Employee());
		return "employeeForm";
	}

	@RequestMapping(value = { "/save" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String save(@ModelAttribute("employee") Employee employee) {
		this.employeeDAO.saveEmployee(employee);
		return "redirect:/viewemp";
	}

	@RequestMapping({ "/viewemp" })
	public String viewEmployee(Model model) {
		List<Employee> list = this.employeeDAO.getEmployees();
		model.addAttribute("employeelist", list);
		return "empView";
	}

	@RequestMapping({ "/editemployee/{id}" })
	public String showUpdateEmpForm(@PathVariable("id") Integer empId, Model model) throws ClassNotFoundException {
		Employee employee = this.employeeDAO.getEmpByIdJDBC(empId);

		System.out.println("emp name :" + employee.getName());
		System.out.println("emp id :" + employee.getId());
		System.out.println("emp sal :" + employee.getSalary());
		System.out.println("emp des :" + employee.getDesignation());
		model.addAttribute("empForm", employee);

		return "empEditForm";
	}

	@RequestMapping(value = { "/editemployee/editsave" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public String editsave(@ModelAttribute("empForm") Employee employee) {
		System.out.println("emp name" + employee.getName());
		System.out.println("emp id " + employee.getId());
		this.employeeDAO.update(employee);
		return "redirect:/viewemp";
	}

	@RequestMapping(value = { "/deleteemployee/{id}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String delete(@PathVariable("id") Integer empId) {
		this.employeeDAO.delete(empId);

		return "redirect:/viewemp";
	}
}
