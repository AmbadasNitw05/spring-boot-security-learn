package com.security.learn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.learn.model.Student;
import com.security.learn.service.StudentService;

@RestController
@RequestMapping("student")
public class StudentController {

	@Autowired
	private StudentService service;
	
	@GetMapping
	public List<Student> getAll(){
		return service.getAll();
	}
	
	@PostMapping
	public Student add(@RequestBody Student obj) {
		return service.add(obj);
	}
}
