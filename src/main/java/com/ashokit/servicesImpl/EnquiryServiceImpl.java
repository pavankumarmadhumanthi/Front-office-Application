package com.ashokit.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.DashboardResponse;
import com.ashokit.binding.EnquiryForm;
import com.ashokit.binding.EnquirySearchCriteria;
import com.ashokit.entity.StudentEnquiry;
import com.ashokit.entity.UserDetails;
import com.ashokit.repo.CourseRepo;
import com.ashokit.repo.EnquiryStatusRepo;
import com.ashokit.repo.StudentEnquiryRepo;
import com.ashokit.repo.UserDetailsRepo;
import com.ashokit.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private CourseRepo course;

	@Autowired
	private EnquiryStatusRepo status;

	@Autowired
	private UserDetailsRepo userDtlsRepo;

	@Autowired
	private StudentEnquiryRepo studentRepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDetails> findById = userDtlsRepo.findByUserId(userId);

		if (findById.isPresent()) {
			UserDetails userEntity = findById.get();

			List<StudentEnquiry> enquiries = userEntity.getStudentEnquiry();

			Integer totalCnt = enquiries.size();

			Integer enrolledCnt = enquiries.stream().filter(e -> e.getStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lostCnt = enquiries.stream().filter(e -> e.getStatus().equals("Lost")).collect(Collectors.toList())
					.size();

			response.setTotalEnquiryCnt(totalCnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);

			response.setTotalEnquiryCnt(totalCnt);
		}

		return response;
	}

	@Override
	public List<String> getcourseDetails() {

		// get the dropdown course values
		List<String> courseDropdown = course.getCourseNames();

		return courseDropdown;
	}

	@Override
	public List<String> getStatusDetails() {

		// get the dropdown status values

		List<String> statusDropdown = status.getStatus();

		return statusDropdown;
	}

	@Override
	public String saveEnquiry(EnquiryForm form) {

		Integer userId = (Integer) session.getAttribute("userId");

		Optional<UserDetails> user = userDtlsRepo.findById(userId);
		UserDetails userDetails = user.get();

		if (form.getEnquiryId() != null) {

			Optional<StudentEnquiry> findById2 = studentRepo.findById(form.getEnquiryId());

			StudentEnquiry entity = findById2.get();

			entity.setClassMode(form.getClassMode());
			entity.setCourse(form.getCourse());
			entity.setStatus(form.getStatus());
			entity.setStudentNumber(form.getStudentNumber());
			entity.setStudentName(form.getStudentName());

			entity.setUserDetails(userDetails);

			studentRepo.save(entity);
			return "Record is Updated successfully";

		}

		StudentEnquiry enqEntity = new StudentEnquiry();

		BeanUtils.copyProperties(form, enqEntity);

		enqEntity.setUserDetails(userDetails);

		studentRepo.save(enqEntity);

		return "Record is saved successfully";

	}
	/*
	 * StudentEnquiry enqEntity = new StudentEnquiry();
	 * BeanUtils.copyProperties(form, enqEntity);
	 * 
	 * Integer userId = (Integer) session.getAttribute("userId");
	 * 
	 * UserDetails userEntity = userDtlsRepo.findByUserId(userId).get();
	 * enqEntity.setUserDetails(userEntity);
	 * 
	 * enqRepo.save(enqEntity);
	 */

	@Override
	public List<StudentEnquiry> getViewEnquiry() {

		Integer userId = (Integer) session.getAttribute("userId");
		List<StudentEnquiry> enquiries = studentRepo.findByUserId(userId);

		return enquiries;
	}

	/*
	 * @Override public List<StudentEnquiry> getViewEnquiry() {
	 * 
	 * Integer userId = (Integer) session.getAttribute("userId");
	 * 
	 * Optional<UserDetails> findById = userDtlsRepo.findById(userId); if
	 * (findById.isPresent()) { UserDetails userDetails = findById.get();
	 * List<StudentEnquiry> studentEnquiries = userDetails.getStudentEnquiry();
	 * 
	 * return studentEnquiries;
	 * 
	 * } return null;
	 * 
	 * }
	 */

	@Override
	public List<StudentEnquiry> getFilterEnquiries(Integer userId, EnquirySearchCriteria criteria) {

		Optional<UserDetails> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetails userDetails = findById.get();
			List<StudentEnquiry> enquiries = userDetails.getStudentEnquiry();

			// filter logic

			if (null != criteria.getCourse() && !"".equals(criteria.getCourse())) {
				enquiries = enquiries.stream().filter(e -> e.getCourse().equals(criteria.getCourse()))
						.collect(Collectors.toList());

			}

			if (null != criteria.getStatus() && !"".equals(criteria.getStatus())) {
				enquiries = enquiries.stream().filter(e -> e.getStatus().equals(criteria.getStatus()))
						.collect(Collectors.toList());

			}

			if (null != criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
				enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());

			}

			return enquiries;

		}

		return null;
	}

}
