package com.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ashokit.entity.CourseDetails;

public interface CourseRepo extends JpaRepository<CourseDetails,Integer>{

	@Query("select courseName from CourseDetails")
	public List<String> getCourseNames();
	
	
	
	
	
}
