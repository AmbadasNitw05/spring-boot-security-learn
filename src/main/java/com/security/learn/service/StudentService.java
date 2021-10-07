package com.security.learn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.security.learn.model.Student;

@Service
public class StudentService {

	private List<Student> list;

	public StudentService() {
		list = new ArrayList<>();
		list.add(new Student(1, "Ambadas"));
		list.add(new Student(2, "Anitha"));
	}
	
	
	public List<Student> getAll(){
		return list;
	}
	
	public Student add(Student obj) {
		list.add(obj);
		return obj;
	}
	
}
