
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Employees List</h1>
<table border="2" width="70%" cellpadding="2">
	<tr>
		<th>Id</th>
		<th>Name</th>
		<th>Salary</th>
		<th>Designation</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<c:forEach var="employee" items="${employeelist}">
		<tr>
			<td>${employee.id}</td>
			<td>${employee.name}</td>
			<td>${employee.salary}</td>
			<td>${employee.designation}</td>
			<td><a href="editemployee/${employee.id}">Edit</a></td>
			<td><a href="deleteemployee/${employee.id}">Delete</a></td>
		</tr>
	</c:forEach>
</table>
<br />
<a href="showForm">Add New Employee</a>
