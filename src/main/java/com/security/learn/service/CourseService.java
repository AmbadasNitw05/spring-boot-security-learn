package com.security.learn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.security.learn.model.Course;


@Service
public class CourseService {

	private List<Course> list;

	public CourseService() {
		list = new ArrayList<>();
		list.add(new Course(1, "Course1"));
		list.add(new Course(2, "Course2"));
	}
	
	
	public ResponseEntity<List<Course>> getAll(){
		return new ResponseEntity<List<Course>>(list, HttpStatus.OK);
	}
	
	public ResponseEntity<Course> add(Course obj) {
		list.add(obj);
		return new ResponseEntity<Course>(obj, HttpStatus.OK);
	}
}
