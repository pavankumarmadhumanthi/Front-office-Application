package com.ashokit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class StatusDetails {

	
	
	@Id
	@GeneratedValue
	private  Integer enquiryId;
	
	private String status;
}
