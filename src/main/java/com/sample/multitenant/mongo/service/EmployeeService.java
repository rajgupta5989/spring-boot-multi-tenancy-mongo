package com.sample.multitenant.mongo.service;

import com.sample.multitenant.mongo.domain.Employee;
import com.sample.multitenant.mongo.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * User: rajeshgupta
 * Date: 08/03/21
 */
@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public Employee findById(final String id) {
        return employeeRepository.findById(id).get();
    }

    public Employee save(final Employee employee) {
        return employeeRepository.save(employee);
    }
}
