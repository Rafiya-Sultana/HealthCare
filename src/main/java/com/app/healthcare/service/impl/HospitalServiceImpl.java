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
import com.app.healthcare.dto.HospitalDoctorDto;
import com.app.healthcare.dto.HospitalDto;
import com.app.healthcare.dto.SearchAppointmentDto;
import com.app.healthcare.model.AppointmentEntity;
import com.app.healthcare.model.DoctorEntity;
import com.app.healthcare.model.HospitalDoctorEntity;
import com.app.healthcare.model.HospitalEntity;
import com.app.healthcare.model.PatientEntity;
import com.app.healthcare.repository.AppointmentRepository;
import com.app.healthcare.repository.DoctorRepository;
import com.app.healthcare.repository.HospitalDoctorRepository;
import com.app.healthcare.repository.HospitalRepository;
import com.app.healthcare.repository.PatientRepository;
import com.app.healthcare.service.HospitalService;

@Service
public class HospitalServiceImpl implements HospitalService
{
	@Autowired
	public HospitalRepository hospitalRepository;
	
	@Autowired
	public HospitalDoctorRepository hospitalDoctorRepository;
	
	@Autowired
	public DoctorRepository doctorRepository;
	
	@Autowired
	public PatientRepository patientRepository;
	
	@Autowired
	public AppointmentRepository appointmentRepository;
	
	@Autowired
	public ModelMapper modelMapper;
		
	@Override
	public String addNewHospital(HospitalDto hospitalDto, BindingResult result, Model model) 
	{
		HospitalEntity hospitalEntity = modelMapper.map(hospitalDto, HospitalEntity.class);
		hospitalEntity.setHospitalRecordedDate(LocalDateTime.now());
		
		Optional<HospitalEntity> optionalHospitalEntity = hospitalRepository.findByMobileNo(hospitalDto.getHospitalPhoneno());
		
		if(optionalHospitalEntity.isPresent())
		{
			ObjectError error = new ObjectError("globalError", "Hospital details already present!");
	        result.addError(error);
			 
			return "hindex";
		}
		else
		{
			HospitalEntity user = hospitalRepository.save(hospitalEntity);
	        
	        if(null != user)
			{
				ObjectError error = new ObjectError("globalError", "Registered successfully!");
		        result.addError(error);
				 
		        hospitalDto = null;
		        
				return "hindex";
			}
		}
        
        return "hsignin";
	}

	@Override
	public String loginHospital(HospitalDto hospitalDto, BindingResult result, Model model, HttpSession session) 
	{
		if(null != hospitalDto 
				&& (null != hospitalDto.getHospitalUserName() && !hospitalDto.getHospitalUserName().equalsIgnoreCase(""))
				&& (null !=hospitalDto.getHospitalPassword() && !hospitalDto.getHospitalPassword().equalsIgnoreCase(""))) 
		{
			HospitalEntity hospitalEntity = hospitalRepository.loginUser(hospitalDto.getHospitalUserName(), hospitalDto.getHospitalPassword());
			
			if(null == hospitalEntity)
			{
				ObjectError error = new ObjectError("globalError", "Incorrect username or password!");
		        result.addError(error);
				 
				return "hindex";
			}
			else
			{
				session.setAttribute("x", null != hospitalEntity && null != hospitalEntity.getHospitalUserName() ? hospitalEntity.getHospitalUserName() : "-");
				session.setAttribute("y", null != hospitalEntity && null != hospitalEntity.getHospitalId() ? hospitalEntity.getHospitalId() : "-");
				
				return "redirect:/hospital/profile";
			}			
		}
		else
		{
			 ObjectError error = new ObjectError("globalError", "Kindly fill both username and password!");
		     result.addError(error);
			 
			 return "hindex";
		}
	}

	@Override
	public String viewProfile(Model model, HttpSession session) 
	{
		model.addAttribute("hospital", hospitalRepository.findByHospitalId(Long.parseLong(session.getAttribute("y").toString())));
		
		return "viewHProfile";
	}

	@Override
	public String addDoctor(HospitalDoctorDto hospitalDoctorDto, BindingResult result, Model model, HttpSession session) 
	{
		model.addAttribute("doctors", doctorRepository.findAllDoctors());
		
		HospitalDoctorEntity hospitalDoctorEntity = modelMapper.map(hospitalDoctorDto, HospitalDoctorEntity.class);
		hospitalDoctorEntity.setHospitalId(Long.parseLong(session.getAttribute("y").toString()));
		hospitalDoctorEntity.setStatus(Boolean.TRUE);
		hospitalDoctorEntity.setHdRecordedDate(LocalDateTime.now());
		
		Optional<HospitalDoctorEntity> hospitalDoctorEntitys = hospitalDoctorRepository.findByHospitalAndDoctorId(hospitalDoctorEntity.getHospitalId(), hospitalDoctorEntity.getDoctorId());
		
		if(hospitalDoctorEntitys.isPresent())
		{
			ObjectError error = new ObjectError("globalError", "Doctor already present in our system!");
	        result.addError(error);
			 
			return "addDoctor";
		}
		else
		{
			HospitalDoctorEntity hdEntity = hospitalDoctorRepository.save(hospitalDoctorEntity);
	        
	        if(null != hdEntity)
			{
				ObjectError error = new ObjectError("globalError", "Doctor added successfully!");
		        result.addError(error);
				 
				return "addDoctor";
			}
		}
		
        return "addDoctor";
	}

	@Override
	public String mapDoctor(HospitalDoctorDto hospitalDoctorDto, Model model, HttpSession session) 
	{
		model.addAttribute("doctors", doctorRepository.findAllDoctors());
		
		return "addDoctor";
	}

	@Override
	public String viewDoctor(Model model, HttpSession session) 
	{
		List<DoctorEntity> objDoctorEntity = new ArrayList<DoctorEntity>();
		
		List<Long> objHospitalDoctorEntityList = hospitalDoctorRepository.findByHospitalId(Long.parseLong(session.getAttribute("y").toString()));
		
		if(!objHospitalDoctorEntityList.isEmpty())
		{
			objDoctorEntity = doctorRepository.findHospitalDoctors(objHospitalDoctorEntityList);
		}
				
		model.addAttribute("doctors", objDoctorEntity);
		
		return "viewHDoctor";
	}

	@Override
	public String searchDoctor(DoctorDto doctorDto, BindingResult result, Model model, HttpSession session) 
	{
		List<DoctorDto> objDoctorEntity = null;
		
		List<Object[]> objHospitalDoctorEntity = null;
		
		List<Long> objHospitalDoctorEntityList = hospitalDoctorRepository.findByHospitalId(Long.parseLong(session.getAttribute("y").toString()));
		
		if(!objHospitalDoctorEntityList.isEmpty())
		{
			objHospitalDoctorEntity = hospitalDoctorRepository.findHospitalDoctors(objHospitalDoctorEntityList);
			
			for(Object[] hospitalDoctorEntity: objHospitalDoctorEntity)
			{
				objDoctorEntity = new ArrayList<DoctorDto>();
				
				DoctorDto doctor = new DoctorDto();
				doctor.setDoctorId(Long.parseLong(hospitalDoctorEntity[0].toString()));
				doctor.setDoctorName(hospitalDoctorEntity[1].toString());
				doctor.setDoctorPhoneno(Long.parseLong(hospitalDoctorEntity[2].toString()));
				doctor.setDoctorEmailId(hospitalDoctorEntity[3].toString());
				doctor.setDoctorDegree(hospitalDoctorEntity[4].toString());
				doctor.setDoctorDesigination(hospitalDoctorEntity[5].toString());
				doctor.setDoctorAddress(hospitalDoctorEntity[6].toString());
				doctor.setVisitingHours(hospitalDoctorEntity[7].toString());
				
				objDoctorEntity.add(doctor);
			}
		}
		
		model.addAttribute("doctors", objDoctorEntity);
		
		return "searchDoctor";
	}

	@Override
	public String advancedsearch(DoctorDto doctorDto, BindingResult result, Model model, HttpSession session) 
	{
		List<DoctorDto> objDoctorEntity = null;
		
		List<Object[]> objHospitalDoctorEntity = null;
		
		List<Long> objHospitalDoctorEntityList = hospitalDoctorRepository.findByHospitalId(Long.parseLong(session.getAttribute("y").toString()));
		
		if(null != doctorDto && null != doctorDto.getDoctorDesigination() && !doctorDto.getDoctorDesigination().equalsIgnoreCase(""))
		{
			objHospitalDoctorEntity = hospitalDoctorRepository.findHospitalDoctorsByDesignation(objHospitalDoctorEntityList, doctorDto.getDoctorDesigination());
			
			for(Object[] hospitalDoctorEntity: objHospitalDoctorEntity)
			{
				objDoctorEntity = new ArrayList<DoctorDto>();
				
				DoctorDto doctor = new DoctorDto();
				doctor.setDoctorId(Long.parseLong(hospitalDoctorEntity[0].toString()));
				doctor.setDoctorName(hospitalDoctorEntity[1].toString());
				doctor.setDoctorPhoneno(Long.parseLong(hospitalDoctorEntity[2].toString()));
				doctor.setDoctorEmailId(hospitalDoctorEntity[3].toString());
				doctor.setDoctorDegree(hospitalDoctorEntity[4].toString());
				doctor.setDoctorDesigination(hospitalDoctorEntity[5].toString());
				doctor.setDoctorAddress(hospitalDoctorEntity[6].toString());
				doctor.setVisitingHours(hospitalDoctorEntity[7].toString());
				
				objDoctorEntity.add(doctor);
			}
			
			model.addAttribute("doctors", objDoctorEntity);
			
			return "searchDoctor";
		}
		else
		{
			if(!objHospitalDoctorEntityList.isEmpty())
			{
				objHospitalDoctorEntity = hospitalDoctorRepository.findHospitalDoctors(objHospitalDoctorEntityList);
				
				for(Object[] hospitalDoctorEntity: objHospitalDoctorEntity)
				{
					objDoctorEntity = new ArrayList<DoctorDto>();
					
					DoctorDto doctor = new DoctorDto();
					doctor.setDoctorId(Long.parseLong(hospitalDoctorEntity[0].toString()));
					doctor.setDoctorName(hospitalDoctorEntity[1].toString());
					doctor.setDoctorPhoneno(Long.parseLong(hospitalDoctorEntity[2].toString()));
					doctor.setDoctorEmailId(hospitalDoctorEntity[3].toString());
					doctor.setDoctorDegree(hospitalDoctorEntity[4].toString());
					doctor.setDoctorDesigination(hospitalDoctorEntity[5].toString());
					doctor.setDoctorAddress(hospitalDoctorEntity[6].toString());
					doctor.setVisitingHours(hospitalDoctorEntity[7].toString());
					
					objDoctorEntity.add(doctor);
				}
			}
			
			model.addAttribute("doctors", objDoctorEntity);
			
			ObjectError error = new ObjectError("globalError", "Please Fill All Mandatory Details!");
	        result.addError(error);
			 
			return "searchDoctor";
		}
	}

	@Override
	public String advancedAdd(long id, Model model) 
	{
		model.addAttribute("doctor", doctorRepository.findByDoctorId(id));
		model.addAttribute("patients", patientRepository.findAll());
		
		return "addAppoinment";
	}

	@Override
	public String bookAppoinment(AppointmentDto appointmentDto, DoctorDto doctorDto, BindingResult result, Model model, HttpSession session) 
	{
		model.addAttribute("doctor", doctorRepository.findByDoctorId(appointmentDto.getDoctorId()));
		
		List<DoctorDto> objDoctorEntity = null;
		
		List<Object[]> objHospitalDoctorEntity = null;
		
		if(null != appointmentDto && null != appointmentDto.getPatientId() && !appointmentDto.getSearchDate().equalsIgnoreCase(""))
		{
			HospitalEntity hospital = new HospitalEntity();
			hospital.setHospitalId(Long.parseLong(session.getAttribute("y").toString()));
			
			DoctorEntity doctor = new DoctorEntity();
			doctor.setDoctorId(appointmentDto.getDoctorId());
			
			PatientEntity patient = new PatientEntity();
			patient.setPatientId(appointmentDto.getPatientId());
			
			AppointmentEntity appointmentEntity = modelMapper.map(appointmentDto, AppointmentEntity.class);
			appointmentEntity.setHospital(hospital);
			appointmentEntity.setDoctor(doctor);
			appointmentEntity.setPatient(patient);
			appointmentEntity.setStatus(Boolean.FALSE);
			appointmentEntity.setRecordedDate(LocalDateTime.now());
			
			String startDate = appointmentDto.getSearchDate().trim() +  "T00:00:00";
			String endDate = appointmentDto.getSearchDate().trim() +  "T23:59:59";
			
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			
			LocalDateTime appointmentstartDate = LocalDateTime.parse(startDate, dateTimeFormatter);
			LocalDateTime appointmentendDate = LocalDateTime.parse(endDate, dateTimeFormatter);
			
			appointmentEntity.setOnlineURL("https://meet.google.com/" + getAlphaNumericString(3) + "-" + getAlphaNumericString(3) + "-" + getAlphaNumericString(3));
			appointmentEntity.setAppointmentDate(appointmentendDate);
			
			Optional<AppointmentEntity> appointmentEntitys = appointmentRepository.findByIdsAndAppointmentDate(appointmentEntity.getPatient().getPatientId(), appointmentEntity.getDoctor().getDoctorId(), appointmentEntity.getHospital().getHospitalId(), appointmentstartDate, appointmentendDate);
			
			if(appointmentEntitys.isPresent())
			{
				ObjectError error = new ObjectError("globalError", "Appointment already present for that particulat date in our system!");
		        result.addError(error);
				 
		        List<Long> objHospitalDoctorEntityList = hospitalDoctorRepository.findByHospitalId(Long.parseLong(session.getAttribute("y").toString()));
				
		        objHospitalDoctorEntity = hospitalDoctorRepository.findHospitalDoctors(objHospitalDoctorEntityList);
				
				for(Object[] hospitalDoctorEntity: objHospitalDoctorEntity)
				{
					objDoctorEntity = new ArrayList<DoctorDto>();
					
					DoctorDto doctor1 = new DoctorDto();
					doctor1.setDoctorId(Long.parseLong(hospitalDoctorEntity[0].toString()));
					doctor1.setDoctorName(hospitalDoctorEntity[1].toString());
					doctor1.setDoctorPhoneno(Long.parseLong(hospitalDoctorEntity[2].toString()));
					doctor1.setDoctorEmailId(hospitalDoctorEntity[3].toString());
					doctor1.setDoctorDegree(hospitalDoctorEntity[4].toString());
					doctor1.setDoctorDesigination(hospitalDoctorEntity[5].toString());
					doctor1.setDoctorAddress(hospitalDoctorEntity[6].toString());
					doctor1.setVisitingHours(hospitalDoctorEntity[7].toString());
					
					objDoctorEntity.add(doctor1);
				}
				
				model.addAttribute("doctors", objDoctorEntity);
				
				return "searchDoctor";
			}
			else
			{
				appointmentRepository.save(appointmentEntity);
				
				List<Long> objHospitalDoctorEntityList = hospitalDoctorRepository.findByHospitalId(Long.parseLong(session.getAttribute("y").toString()));
					
				objHospitalDoctorEntity = hospitalDoctorRepository.findHospitalDoctors(objHospitalDoctorEntityList);
				
				for(Object[] hospitalDoctorEntity: objHospitalDoctorEntity)
				{
					objDoctorEntity = new ArrayList<DoctorDto>();
					
					DoctorDto doctor1 = new DoctorDto();
					doctor1.setDoctorId(Long.parseLong(hospitalDoctorEntity[0].toString()));
					doctor1.setDoctorName(hospitalDoctorEntity[1].toString());
					doctor1.setDoctorPhoneno(Long.parseLong(hospitalDoctorEntity[2].toString()));
					doctor1.setDoctorEmailId(hospitalDoctorEntity[3].toString());
					doctor1.setDoctorDegree(hospitalDoctorEntity[4].toString());
					doctor1.setDoctorDesigination(hospitalDoctorEntity[5].toString());
					doctor1.setDoctorAddress(hospitalDoctorEntity[6].toString());
					doctor1.setVisitingHours(hospitalDoctorEntity[7].toString());
					
					objDoctorEntity.add(doctor1);
				}
				
				model.addAttribute("doctors", objDoctorEntity);
				
				ObjectError error = new ObjectError("globalError", "Appointment inserted successfully!");
		        result.addError(error);
				
				return "searchDoctor";
			}
		}
		else
		{
			model.addAttribute("doctor", doctorRepository.findByDoctorId(appointmentDto.getDoctorId()));
			model.addAttribute("patients", patientRepository.findAll());
			
			ObjectError error = new ObjectError("globalError", "Please Fill All Mandatory Details!");
	        result.addError(error);
	        
			return "addAppoinment";
		}
	}
	
	@Override
	public String searchAppointment(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session) 
	{
		List<Object[]> objAppointmentEntityList = appointmentRepository.findAllByHospitalId(Long.parseLong(session.getAttribute("y").toString()));
		
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
		
		return "searchAppointment";
	}
	
	@Override
	public String advancedAppointmentSearch(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session) 
	{
		List<Object[]> objAppointmentEntityList = appointmentRepository.findAllByHospitalId(Long.parseLong(session.getAttribute("y").toString()));
				
		if(null != searchAppointmentDto && null != searchAppointmentDto.getPatientPhoneno())
		{
			Optional<PatientEntity> optionalPatientEntity = patientRepository.findByMobileNo(searchAppointmentDto.getPatientPhoneno());
			
			if(optionalPatientEntity.isPresent())
			{
				objAppointmentEntityList = appointmentRepository.findByHospitalIdAndPatientId(Long.parseLong(session.getAttribute("y").toString()), optionalPatientEntity.get().getPatientId());
				
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
						
				return "searchAppointment";
			}
			else
			{
				model.addAttribute("appointments", objAppointmentEntityList);
				
				ObjectError error = new ObjectError("globalError", "Patient mobile Number is not present our system!");
		        result.addError(error);
				 
				return "searchAppointment";
			}			
		}
		else if(null != searchAppointmentDto && null != searchAppointmentDto.getAppointmentDate())
		{
			String startDate = searchAppointmentDto.getAppointmentDate().trim() +  "T00:00:00";
			String endDate = searchAppointmentDto.getAppointmentDate().trim() +  "T23:59:59";
			
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			
			LocalDateTime appointmentstartDate = LocalDateTime.parse(startDate, dateTimeFormatter);
			LocalDateTime appointmentendDate = LocalDateTime.parse(endDate, dateTimeFormatter);
			
			objAppointmentEntityList = appointmentRepository.findByHospitalIdAndAppointmentDate(Long.parseLong(session.getAttribute("y").toString()), appointmentstartDate, appointmentendDate);
			
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
				
				LocalDateTime appointmentDate = LocalDateTime.parse(AppointmentEntity[5].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
				
				appointmentDto.setAppointmentDate(appointmentDate);		
				
				LocalDateTime recordedDate = LocalDateTime.parse(AppointmentEntity[6].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
				
				appointmentDto.setRecordedDate(recordedDate);	
				
				appointmentListDto.add(appointmentDto);
			}
			
			model.addAttribute("appointments", appointmentListDto);
			
			return "searchAppointment";
		}
		else
		{
			ObjectError error = new ObjectError("globalError", "Please Fill All Mandatory Details!");
	        result.addError(error);
	        
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
				
				LocalDateTime appointmentDate = LocalDateTime.parse(AppointmentEntity[5].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
				
				appointmentDto.setAppointmentDate(appointmentDate);		
				
				LocalDateTime recordedDate = LocalDateTime.parse(AppointmentEntity[6].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
				
				appointmentDto.setRecordedDate(recordedDate);	
				
				appointmentListDto.add(appointmentDto);
			}
			
			model.addAttribute("appointments", appointmentListDto);
			
			return "searchAppointment";
		}
	}
	
	public static String getAlphaNumericString(int n) 
	{
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) 
		{

			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}
}
