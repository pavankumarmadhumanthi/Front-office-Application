package com.ashokit.binding;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class EnquiryForm {

	@Id
	private Integer enquiryId;
	private String studentName;
	private Long studentNumber;
	private String classMode;
	private String course;
	private String status;
	
	
}
