package com.ashokit.service;

import java.util.List;
import java.util.Optional;

import com.ashokit.binding.DashboardResponse;
import com.ashokit.binding.EnquiryForm;
import com.ashokit.binding.EnquirySearchCriteria;
import com.ashokit.entity.CourseDetails;
import com.ashokit.entity.StatusDetails;
import com.ashokit.entity.StudentEnquiry;

public interface EnquiryService {
	
	public List<String> getcourseDetails();

	public List<String> getStatusDetails();

	
	public DashboardResponse getDashboardData(Integer userId);

	public String saveEnquiry(EnquiryForm enquiry);

	public List<StudentEnquiry> getFilterEnquiries(Integer userId ,EnquirySearchCriteria searchCriteria);

	public List<StudentEnquiry> getViewEnquiry ();

	
}
