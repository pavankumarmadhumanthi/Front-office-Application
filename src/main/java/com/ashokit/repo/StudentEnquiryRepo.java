package com.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashokit.binding.DashboardResponse;
import com.ashokit.entity.StudentEnquiry;

public interface StudentEnquiryRepo extends JpaRepository<StudentEnquiry, Integer> {

	 @Query("SELECT s FROM StudentEnquiry s WHERE s.userDetails.userId = :userId")
	    List<StudentEnquiry> findByUserId(@Param("userId") Integer userId);
	
	
	
	
	
	
	
	
}
