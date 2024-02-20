package com.ashokit.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashokit.binding.DashboardResponse;
import com.ashokit.binding.EnquiryForm;
import com.ashokit.binding.EnquirySearchCriteria;
import com.ashokit.entity.StudentEnquiry;
import com.ashokit.repo.StudentEnquiryRepo;
import com.ashokit.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	@Autowired
	private StudentEnquiryRepo repo;

	@Autowired
	private HttpSession session;

	@Autowired
	private EnquiryService service;

	@Autowired
	private StudentEnquiry entity;

	@GetMapping("/logout")
	public String logout() {

		session.invalidate();

		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboradPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		DashboardResponse dashboardData = service.getDashboardData(userId);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	private void initForm(Model model) {
		// get courses for drop down
		model.addAttribute("courses", service.getcourseDetails());
		// get status for drop down
		model.addAttribute("status", service.getStatusDetails());

		// create binding class obj
		EnquiryForm formObj = new EnquiryForm();

		// set data in model obj
		model.addAttribute("add", new EnquiryForm());
		model.addAttribute("courses", service.getcourseDetails());
		model.addAttribute("status", service.getStatusDetails());

	}

	@GetMapping("/addEnquiry")
	public String addEnquiry(Model model) {
		initForm(model);

		return "add-enquiry";
	}

	@PostMapping("/saveEnq")
	public String saveEnq(@ModelAttribute("add") EnquiryForm form, Model model) {

		String msg = service.saveEnquiry(form);
		model.addAttribute("succMsg", msg);

		initForm(model);

		return "add-enquiry";

		/*
		 * boolean status = service.saveEnquiry(form);
		 * 
		 * if (status) { model.addAttribute("succMsg", "Enquiry Added"); } else {
		 * model.addAttribute("errMsg", "Problem Occured"); }
		 * 
		 * initForm(model);
		 * 
		 * return "add-enquiry";
		 */

	}

	@GetMapping("/viewEnquiries")
	public String viewEnquiry(Model model) {

		List<StudentEnquiry> viewEnquiry = service.getViewEnquiry();

		model.addAttribute("enquiries", viewEnquiry);
		model.addAttribute("courses", service.getcourseDetails());

		model.addAttribute("status", service.getStatusDetails());

		return "view-enquiries";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("enquiryId") Integer enquiryId, Model model) {

		initForm(model);

		Optional<StudentEnquiry> editEnquiry = repo.findById(enquiryId);

		if (editEnquiry.isPresent()) {
			StudentEnquiry studentEnquiryForm = editEnquiry.get();

			model.addAttribute("add", studentEnquiryForm);
			model.addAttribute("hidden", enquiryId);

		}

		return "add-enquiry";

	}

	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String course,

			@RequestParam String status, @RequestParam String classMode, Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();

		criteria.setCourse(course);

		criteria.setClassMode(classMode);

		criteria.setStatus(status);

		System.out.println(criteria);

		Integer userId = (Integer) session.getAttribute("userId");

		List<StudentEnquiry> filterEnquiries = service.getFilterEnquiries(userId, criteria);

		model.addAttribute("enquiries", filterEnquiries);

		return "filter-enquiry-page";

	}

}
