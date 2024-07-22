package com.app.healthcare.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.app.healthcare.dto.AppointmentDto;
import com.app.healthcare.dto.DoctorDto;
import com.app.healthcare.dto.PrescriptionDto;
import com.app.healthcare.dto.SearchAppointmentDto;
import com.app.healthcare.model.AppointmentEntity;
import com.app.healthcare.model.DoctorEntity;
import com.app.healthcare.model.PatientEntity;
import com.app.healthcare.model.PrescriptionEntity;
import com.app.healthcare.repository.AppointmentRepository;
import com.app.healthcare.repository.DoctorRepository;
import com.app.healthcare.repository.PatientRepository;
import com.app.healthcare.repository.PrescriptionRepository;
import com.app.healthcare.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService
{
	@Autowired
	public DoctorRepository doctorRepository;
	
	@Autowired
	public AppointmentRepository appointmentRepository;
	
	@Autowired
	public PatientRepository patientRepository;
	
	@Autowired
	public PrescriptionRepository prescriptionRepository;
	
	@Autowired
	public ModelMapper modelMapper;
	
	@Override
	public String addNewDoctor(DoctorDto doctorDto, BindingResult result, Model model) 
	{
		DoctorEntity doctorEntity = modelMapper.map(doctorDto, DoctorEntity.class);
		doctorEntity.setDoctorRecordedDate(LocalDateTime.now());
		
		Optional<DoctorEntity> optionalDoctorEntity = doctorRepository.findByMobileNo(doctorEntity.getDoctorPhoneno());
		
		if(optionalDoctorEntity.isPresent())
		{
			ObjectError error = new ObjectError("globalError", "Doctor details already present!");
	        result.addError(error);
			 
			return "dindex";
		}
		else
		{
			DoctorEntity doctor = doctorRepository.save(doctorEntity);
	        
	        if(null != doctor)
			{
				ObjectError error = new ObjectError("globalError", "Registered successfully!");
		        result.addError(error);
		        
				return "dindex";
			}
		}
        
        return "dsignin";
	}

	@Override
	public String loginDoctor(DoctorDto doctorDto, BindingResult result, Model model, HttpSession session) 
	{
		if(null != doctorDto 
				&& (null != doctorDto.getDoctorUserName() && !doctorDto.getDoctorUserName().equalsIgnoreCase(""))
				&& (null !=doctorDto.getDoctorPassword() && !doctorDto.getDoctorPassword().equalsIgnoreCase(""))) 
		{
			DoctorEntity doctorEntity = doctorRepository.loginUser(doctorDto.getDoctorUserName(), doctorDto.getDoctorPassword());
			
			if(null == doctorEntity)
			{
				ObjectError error = new ObjectError("globalError", "Incorrect username or password!");
		        result.addError(error);
				 
				return "dindex";
			}
			else
			{
				session.setAttribute("x", null != doctorEntity && null != doctorEntity.getDoctorUserName() ? doctorEntity.getDoctorUserName() : "-");
				session.setAttribute("y", null != doctorEntity && null != doctorEntity.getDoctorId() ? doctorEntity.getDoctorId() : "-");
				
				return "redirect:/doctor/profile";
			}			
		}
		else
		{
			 ObjectError error = new ObjectError("globalError", "Kindly fill both username and password!");
		     result.addError(error);
			 
			 return "dindex";
		}
	}
	
	@Override
	public String viewProfile(Model model, HttpSession session) 
	{
		model.addAttribute("doctor", doctorRepository.findByDoctorId(Long.parseLong(session.getAttribute("y").toString())));
		
		return "viewDProfile";
	}

	@Override
	public String searchAppointment(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session) 
	{
		List<Object[]> objAppointmentEntityList = appointmentRepository.findAllByDoctorId(Long.parseLong(session.getAttribute("y").toString()));
		
		List<AppointmentDto> appointmentListDto = new ArrayList<AppointmentDto>();
		
		for(Object[] AppointmentEntity: objAppointmentEntityList)
		{
			AppointmentDto appointmentDto = new AppointmentDto();
			appointmentDto.setAppointmentId(Long.parseLong(AppointmentEntity[0].toString()));
			appointmentDto.setDoctorName(AppointmentEntity[1].toString());	
			appointmentDto.setPatientName(AppointmentEntity[2].toString());
			appointmentDto.setPatientPhoneno(Long.valueOf(AppointmentEntity[3].toString()));	
						
			if(AppointmentEntity[4].toString().equalsIgnoreCase("true"))
			{
				appointmentDto.setStatus(Boolean.TRUE);
			}
			else
			{
				appointmentDto.setStatus(Boolean.FALSE);
			};
			
			LocalDateTime appointmentDate = LocalDateTime.parse(AppointmentEntity[5].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
			
			appointmentDto.setAppointmentDate(appointmentDate);		
			
			LocalDateTime recordedDate = LocalDateTime.parse(AppointmentEntity[6].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
			
			appointmentDto.setRecordedDate(recordedDate);	
			appointmentDto.setOnlineURL(AppointmentEntity[7].toString());
			
			appointmentListDto.add(appointmentDto);
		}
		
		model.addAttribute("appointments", appointmentListDto);
		
		return "searchDAppointment";
	}
	
	@Override
	public String advancedAppointmentSearch(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session) 
	{
		if(null != searchAppointmentDto && null != searchAppointmentDto.getPatientPhoneno())
		{
			List<Object[]> objAppointmentEntityList = null;
			
			Optional<PatientEntity> optionalPatientEntity = patientRepository.findByMobileNo(searchAppointmentDto.getPatientPhoneno());
			
			if(optionalPatientEntity.isPresent())
			{
				objAppointmentEntityList = appointmentRepository.findAllByPatientId(optionalPatientEntity.get().getPatientId());
				
				List<AppointmentDto> appointmentListDto = new ArrayList<AppointmentDto>();
				
				for(Object[] AppointmentEntity: objAppointmentEntityList)
				{
					AppointmentDto appointmentDto = new AppointmentDto();
					appointmentDto.setAppointmentId(Long.parseLong(AppointmentEntity[0].toString()));
					appointmentDto.setDoctorName(AppointmentEntity[1].toString());	
					appointmentDto.setPatientName(AppointmentEntity[2].toString());
					appointmentDto.setPatientPhoneno(Long.valueOf(AppointmentEntity[3].toString()));	
					
					if(AppointmentEntity[4].toString().equalsIgnoreCase("true"))
					{
						appointmentDto.setStatus(Boolean.TRUE);
					}
					else
					{
						appointmentDto.setStatus(Boolean.FALSE);
					}
					
					LocalDateTime appointmentDate = LocalDateTime.parse(AppointmentEntity[5].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
					
					appointmentDto.setAppointmentDate(appointmentDate);		
					
					LocalDateTime recordedDate = LocalDateTime.parse(AppointmentEntity[6].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
					
					appointmentDto.setRecordedDate(recordedDate);	
					appointmentDto.setOnlineURL(AppointmentEntity[7].toString());
					
					appointmentListDto.add(appointmentDto);
				}
				
				model.addAttribute("appointments", appointmentListDto);
						
				return "searchDAppointment";
			}
			else
			{
				model.addAttribute("appointments", objAppointmentEntityList);
				
				ObjectError error = new ObjectError("globalError", "Patient mobile Number is not present our system!");
		        result.addError(error);
				 
				return "searchDAppointment";
			}			
		}
		else if(null != searchAppointmentDto && null != searchAppointmentDto.getAppointmentDate() && !searchAppointmentDto.getAppointmentDate().equalsIgnoreCase(""))
		{
			String startDate = searchAppointmentDto.getAppointmentDate().trim() +  "T00:00:00";
			String endDate = searchAppointmentDto.getAppointmentDate().trim() +  "T23:59:59";
			
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			
			LocalDateTime appointmentstartDate = LocalDateTime.parse(startDate, dateTimeFormatter);
			LocalDateTime appointmentendDate = LocalDateTime.parse(endDate, dateTimeFormatter);
			
			List<Object[]> objAppointmentEntityList = appointmentRepository.findByDoctorIdAndAppointmentDate(Long.parseLong(session.getAttribute("y").toString()), appointmentstartDate, appointmentendDate);
			
			List<AppointmentDto> appointmentListDto = new ArrayList<AppointmentDto>();
			
			for(Object[] AppointmentEntity: objAppointmentEntityList)
			{
				AppointmentDto appointmentDto = new AppointmentDto();
				appointmentDto.setAppointmentId(Long.parseLong(AppointmentEntity[0].toString()));
				appointmentDto.setDoctorName(AppointmentEntity[1].toString());	
				appointmentDto.setPatientName(AppointmentEntity[2].toString());
				appointmentDto.setPatientPhoneno(Long.valueOf(AppointmentEntity[3].toString()));	
				
				if(AppointmentEntity[4].toString().equalsIgnoreCase("true"))
				{
					appointmentDto.setStatus(Boolean.TRUE);
				}
				else
				{
					appointmentDto.setStatus(Boolean.FALSE);
				}
				
				LocalDateTime appointmentDate = LocalDateTime.parse(AppointmentEntity[5].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
				
				appointmentDto.setAppointmentDate(appointmentDate);		
				
				LocalDateTime recordedDate = LocalDateTime.parse(AppointmentEntity[6].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
				
				appointmentDto.setRecordedDate(recordedDate);
				appointmentDto.setOnlineURL(AppointmentEntity[7].toString());
				
				appointmentListDto.add(appointmentDto);
			}
			
			model.addAttribute("appointments", appointmentListDto);
			
			return "searchDAppointment";
		}
		else
		{
			ObjectError error = new ObjectError("globalError", "Please Fill All Mandatory Details!");
	        result.addError(error);
	        
	        List<Object[]> objAppointmentEntityList = appointmentRepository.findAllByDoctorId(Long.parseLong(session.getAttribute("y").toString()));
	        
	        List<AppointmentDto> appointmentListDto = new ArrayList<AppointmentDto>();
			
			for(Object[] AppointmentEntity: objAppointmentEntityList)
			{
				AppointmentDto appointmentDto = new AppointmentDto();
				appointmentDto.setAppointmentId(Long.parseLong(AppointmentEntity[0].toString()));
				appointmentDto.setDoctorName(AppointmentEntity[1].toString());	
				appointmentDto.setPatientName(AppointmentEntity[2].toString());
				appointmentDto.setPatientPhoneno(Long.valueOf(AppointmentEntity[3].toString()));	
				
				if(AppointmentEntity[4].toString().equalsIgnoreCase("true"))
				{
					appointmentDto.setStatus(Boolean.TRUE);
				}
				else
				{
					appointmentDto.setStatus(Boolean.FALSE);
				}
				
				LocalDateTime appointmentDate = LocalDateTime.parse(AppointmentEntity[5].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
				
				appointmentDto.setAppointmentDate(appointmentDate);		
				
				LocalDateTime recordedDate = LocalDateTime.parse(AppointmentEntity[6].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
				
				appointmentDto.setRecordedDate(recordedDate);	
				appointmentDto.setOnlineURL(AppointmentEntity[7].toString());
				
				appointmentListDto.add(appointmentDto);
			}
			
			model.addAttribute("appointments", appointmentListDto);
			
			return "searchDAppointment";
		}
	}

	@Override
	public String addPrescription(long id, PrescriptionDto prescriptionDto, Model model, HttpSession session)
	{
		model.addAttribute("appointments", appointmentRepository.findByAppointmentId(id));
		
		return "addDPrescription";
	}

	@Override
	public String savePrescription(PrescriptionDto prescriptionDto, SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session) 
	{
		if(null != prescriptionDto && null != prescriptionDto.getAppointmentId() && !prescriptionDto.getPrescription().toString().equalsIgnoreCase(""))
		{
			PrescriptionEntity prescription = new PrescriptionEntity();
			prescription.setAppointmentId(prescriptionDto.getAppointmentId());
			prescription.setPrescription(prescriptionDto.getPrescription());
			prescription.setLabTest(null != prescriptionDto.getLabTest() && !prescriptionDto.getLabTest().equalsIgnoreCase("") ? prescriptionDto.getLabTest() : "-");
			prescription.setStatus(Boolean.TRUE);
			prescription.setRecordedDate(LocalDateTime.now());
						
			Optional<PrescriptionEntity> prescriptionEntity = prescriptionRepository.findByAppointmentId(prescriptionDto.getAppointmentId());
			
			if(prescriptionEntity.isPresent())
			{
				ObjectError error = new ObjectError("globalError", "Prescription already present for that particulat date in our system!");
		        result.addError(error);
		        
		        model.addAttribute("appointments", appointmentRepository.findByAppointmentId(prescriptionDto.getAppointmentId()));
				
				return "addDPrescription";
			}
			else
			{
				prescriptionRepository.save(prescription);
				
				List<AppointmentEntity>  objAppointmentList = appointmentRepository.findByAppointmentId(prescriptionDto.getAppointmentId());
				
				AppointmentEntity objAppointmentEntity = objAppointmentList.get(0);
				objAppointmentEntity.setStatus(Boolean.TRUE);
				
				appointmentRepository.save(objAppointmentEntity);
				
				List<Object[]> objAppointmentEntityList = appointmentRepository.findAllByDoctorId(Long.parseLong(session.getAttribute("y").toString()));
				
				List<AppointmentDto> appointmentListDto = new ArrayList<AppointmentDto>();
				
				for(Object[] AppointmentEntity: objAppointmentEntityList)
				{
					AppointmentDto appointmentDto = new AppointmentDto();
					appointmentDto.setAppointmentId(Long.parseLong(AppointmentEntity[0].toString()));
					appointmentDto.setDoctorName(AppointmentEntity[1].toString());	
					appointmentDto.setPatientName(AppointmentEntity[2].toString());
					appointmentDto.setPatientPhoneno(Long.valueOf(AppointmentEntity[3].toString()));	
								
					if(AppointmentEntity[4].toString().equalsIgnoreCase("true"))
					{
						appointmentDto.setStatus(Boolean.TRUE);
					}
					else
					{
						appointmentDto.setStatus(Boolean.FALSE);
					};
					
					LocalDateTime appointmentDate = LocalDateTime.parse(AppointmentEntity[5].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
					
					appointmentDto.setAppointmentDate(appointmentDate);		
					
					LocalDateTime recordedDate = LocalDateTime.parse(AppointmentEntity[6].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
					
					appointmentDto.setRecordedDate(recordedDate);	
					
					appointmentListDto.add(appointmentDto);
				}
				
				model.addAttribute("appointments", appointmentListDto);
				
				ObjectError error = new ObjectError("globalError", "Prescription inserted successfully!");
		        result.addError(error);
		        
		        return "searchDAppointment";
			}
		}
		else
		{			
			ObjectError error = new ObjectError("globalError", "Please Fill All Mandatory Details!");
	        result.addError(error);
	        
	        model.addAttribute("appointments", appointmentRepository.findByAppointmentId(prescriptionDto.getAppointmentId()));
			
			return "addDPrescription";
		}
	}

	@Override
	public String viewPrescription(long id, PrescriptionDto prescriptionDto, Model model) 
	{
		PrescriptionEntity prescriptionEntity = null;
		
		Optional<PrescriptionEntity> objPrescriptionEntity = prescriptionRepository.findByAppointmentId(id);
		
		if(objPrescriptionEntity.isPresent())
		{
			prescriptionEntity = objPrescriptionEntity.get();
		}
		
		model.addAttribute("prescriptions", prescriptionEntity);
		
		return "viewDPrescription";
	}
}
