package com.ashokit.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ashokit.entity.CourseDetails;
import com.ashokit.entity.StatusDetails;
import com.ashokit.repo.CourseRepo;
import com.ashokit.repo.EnquiryStatusRepo;

@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnquiryStatusRepo statusRepo;

	@Override
	public void run(String... args) throws Exception {
		
		courseRepo.deleteAll();
		statusRepo.deleteAll();
		
		CourseDetails course1 = new CourseDetails();
		course1.setCourseName("Java FullStack");
		
		CourseDetails course2 = new CourseDetails();
		course2.setCourseName("Devops");

		CourseDetails course3 = new CourseDetails();
		course3.setCourseName("AWS");
		
		List<CourseDetails> list = Arrays.asList(course1,course2,course3);
		courseRepo.saveAll(list);
		
		
		StatusDetails status1 = new StatusDetails();
		status1.setStatus("New");
		
		StatusDetails status2 = new StatusDetails();
		status2.setStatus("Enrolled");
		
		StatusDetails status3 = new StatusDetails();
		status3.setStatus("Lost");
		
		List<StatusDetails> statusList = Arrays.asList(status1,status2,status3);
		
		statusRepo.saveAll(statusList);
		System.out.println("Data is inserted into the database");
		
		
		
	}

}
