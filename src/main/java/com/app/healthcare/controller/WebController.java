package com.app.healthcare.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.healthcare.dto.DoctorDto;
import com.app.healthcare.dto.HospitalDto;
import com.app.healthcare.dto.PatientDto;

@Controller
public class WebController {
	
	@RequestMapping(value = "/home")
	public String home(@Valid PatientDto patientDto, BindingResult result, Model model) {
		ObjectError error = new ObjectError("globalError", "");
		result.addError(error);

		return "home";
	}
	
	@RequestMapping(value = "/index")
	public String index(@Valid PatientDto patientDto, BindingResult result, Model model) {
		ObjectError error = new ObjectError("globalError", "");
		result.addError(error);

		return "index";
	}

	@RequestMapping(value = "/dindex")
	public String dindex(@Valid DoctorDto doctorDto, BindingResult result, Model model) {
		ObjectError error = new ObjectError("globalError", "");
		result.addError(error);

		return "dindex";
	}

	@RequestMapping(value = "/hindex")
	public String hindex(@Valid HospitalDto hospitalDto, BindingResult result, Model model) {
		ObjectError error = new ObjectError("globalError", "");
		result.addError(error);

		return "hindex";
	}

	@RequestMapping(value = "/signin")
	public String signin(@Valid PatientDto patientDto, BindingResult result, Model model) {
		ObjectError error = new ObjectError("globalError", "");
		result.addError(error);

		return "signin";
	}

	@RequestMapping(value = "/dsignin")
	public String dsignin(@Valid DoctorDto doctorDto, BindingResult result, Model model) {
		ObjectError error = new ObjectError("globalError", "");
		result.addError(error);

		return "dsignin";
	}

	@RequestMapping(value = "/hsignin")
	public String hsignin(@Valid HospitalDto hospitalDto, BindingResult result, Model model) {
		ObjectError error = new ObjectError("globalError", "");
		result.addError(error);

		return "hsignin";
	}

	@RequestMapping(value = "/signout")
	public String signout() {
		return "signout";
	}

	@RequestMapping(value = "/logout")
	public String logout(@Valid PatientDto patientDto, BindingResult result, Model model, HttpSession session) {
		ObjectError error = new ObjectError("globalError", "Logged out succesfully!");
		result.addError(error);

		// session close
		session.getAttribute("x");

		// invalidate session or destroy session
		session.invalidate();

		return "index";
	}
}
