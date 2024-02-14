package com.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ashokit.entity.StatusDetails;

public interface EnquiryStatusRepo extends JpaRepository<StatusDetails,Integer> {

	@Query("select status from StatusDetails")
	public List<String> getStatus();
	
	
	
	
}
