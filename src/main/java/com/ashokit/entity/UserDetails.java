package com.ashokit.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class UserDetails {

	@Id
	@GeneratedValue
	private Integer userId;
	
	@Column(name = "Name")
	private String name;
	
	
	@Column(name = "Email")
	private String email;
	
	
	
	@Column(name="Phone_Number")
	private Long phoneNo;
	
	@Column(name="Password")
	private String password;
	
	
	@Column(name = "Account_Status")
	private String accountStatus;
	
	
	@OneToMany(cascade = CascadeType.ALL,fetch =FetchType.EAGER,mappedBy ="userDetails")
	private List<StudentEnquiry> studentEnquiry ;

}
