package net.javaguides.springboot.crudrestfulwebservices.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import net.javaguides.springboot.crudrestfulwebservices.model.Employee;
import net.javaguides.springboot.crudrestfulwebservices.repository.EmployeeRepository;
import net.javaguides.springboot.crudrestfulwebservices.exception.ResourceNotFoundException;

@CrossOrigin(origins ="http://localhost:3000")
@RestController
@RequestMapping("/api/v3/")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	

	//create get all employees api
	@GetMapping("/employees")
	      public  List<Employee> getAllEmployees(){
		 return employeeRepository.findAll();
	
	
	}
	//create employee
	
    @PostMapping("/employees")
	public Employee createEmployee(@Validated @RequestBody Employee employee) {
	return employeeRepository.save(employee);
	
	

}
    
    //get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long employeeId) throws ResourceNotFoundException{
           
    	Employee employee = employeeRepository.findById(employeeId)
    			.orElseThrow(() -> new ResourceNotFoundException("Employee Not found for this id :: " + employeeId));
    	return ResponseEntity.ok().body(employee);
    }
    
    //update employee id
    
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	// delete employee
	
	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
    
}
