package com.sample.multitenant.mongo.repository;

import com.sample.multitenant.mongo.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * User: rajeshgupta
 * Date: 08/03/21
 */
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
