package com.security.learn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.learn.model.Course;
import com.security.learn.service.CourseService;

@RestController
@RequestMapping("course")
public class CourseController {

	@Autowired
	private CourseService service;
	
	@GetMapping
	public ResponseEntity<List<Course>> getAll(){
		return service.getAll();
	}
	
	@PostMapping
	public ResponseEntity<Course> add(@RequestBody Course obj) {
		return service.add(obj);
	}
}
