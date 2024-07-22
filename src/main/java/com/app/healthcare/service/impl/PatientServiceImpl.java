package com.app.healthcare.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import com.app.healthcare.dto.AppointmentDto;
import com.app.healthcare.dto.DoctorDto;
import com.app.healthcare.dto.LabReportDto;
import com.app.healthcare.dto.PatientDto;
import com.app.healthcare.dto.PrescriptionDto;
import com.app.healthcare.dto.SearchAppointmentDto;
import com.app.healthcare.model.AppointmentEntity;
import com.app.healthcare.model.DoctorEntity;
import com.app.healthcare.model.HospitalEntity;
import com.app.healthcare.model.LabReportEntity;
import com.app.healthcare.model.PatientEntity;
import com.app.healthcare.model.PrescriptionEntity;
import com.app.healthcare.repository.AppointmentRepository;
import com.app.healthcare.repository.DoctorRepository;
import com.app.healthcare.repository.HospitalDoctorRepository;
import com.app.healthcare.repository.HospitalRepository;
import com.app.healthcare.repository.LabReportRepository;
import com.app.healthcare.repository.PatientRepository;
import com.app.healthcare.repository.PrescriptionRepository;
import com.app.healthcare.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService
{
	@Autowired
	public PatientRepository patientRepository;
	
	@Autowired
	public DoctorRepository doctorRepository;
	
	@Autowired
	public HospitalDoctorRepository hospitalDoctorRepository;
	
	@Autowired
	public HospitalRepository hospitalRepository;
	
	@Autowired
	public AppointmentRepository appointmentRepository;
	
	@Autowired
	public LabReportRepository labReportRepository;
	
	@Autowired
	public PrescriptionRepository prescriptionRepository;
	
	@Autowired
	public ModelMapper modelMapper;
	
	@Value("${upload.file.path}")
	private String filePath;
	
	public Path root;
	
	@Override
	public void init() 
	{
	    try 
	    {
	    	root = Paths.get(filePath);
	    	
	    	Files.createDirectories(Paths.get(filePath));
	    }
	    catch (IOException e) 
	    {
	      throw new RuntimeException("Could not initialize folder for upload!");
	    }
	}
		
	@Override
	public String addNewPatient(PatientDto patientDto, BindingResult result, Model model) 
	{
		PatientEntity patientEntity = modelMapper.map(patientDto, PatientEntity.class);
		patientEntity.setPatientRecordedDate(LocalDateTime.now());
		
		Optional<PatientEntity> optionalPatientEntity = patientRepository.findByMobileNo(patientDto.getPatientPhoneno());
		
		if(optionalPatientEntity.isPresent())
		{
			ObjectError error = new ObjectError("globalError", "Patient details already present!");
	        result.addError(error);
			 
			return "index";
		}
		else
		{
			PatientEntity user = patientRepository.save(patientEntity);
	        
	        if(null != user)
			{
				ObjectError error = new ObjectError("globalError", "Registered successfully!");
		        result.addError(error);
				 
		        patientDto = null;
		        
				return "index";
			}
		}
        
        return "signin";
	}

	@Override
	public String loginPatient(PatientDto patientDto, BindingResult result, Model model, HttpSession session) 
	{
		if(null != patientDto 
				&& (null != patientDto.getPatientUserName() && !patientDto.getPatientUserName().equalsIgnoreCase(""))
				&& (null !=patientDto.getPatientPassword() && !patientDto.getPatientPassword().equalsIgnoreCase(""))) 
		{
			PatientEntity patientEntity = patientRepository.loginUser(patientDto.getPatientUserName(), patientDto.getPatientPassword());
			
			if(null == patientEntity)
			{
				ObjectError error = new ObjectError("globalError", "Incorrect username or password!");
		        result.addError(error);
				 
				return "index";
			}
			else
			{
				session.setAttribute("x", null != patientEntity && null != patientEntity.getPatientUserName() ? patientEntity.getPatientUserName() : "-");
				session.setAttribute("y", null != patientEntity && null != patientEntity.getPatientId() ? patientEntity.getPatientId() : "-");
				
				return "redirect:/patient/profile";
			}			
		}
		else
		{
			 ObjectError error = new ObjectError("globalError", "Kindly fill both username and password!");
		     result.addError(error);
			 
			 return "index";
		}
	}

	@Override
	public String viewProfile(Model model, HttpSession session) 
	{
		model.addAttribute("patient", patientRepository.findByPatientId(Long.parseLong(session.getAttribute("y").toString())));
		
		return "viewPProfile";
	}

	@Override
	public String searchDoctor(PatientDto patientDto, DoctorDto doctorDto, BindingResult result, Model model, HttpSession session) 
	{
		List<DoctorDto> objDoctorEntity = null;
		
		List<Object[]> objHospitalDoctorEntity = hospitalDoctorRepository.findAllHospitalDoctors();
			
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
		
		return "searchPDoctor";
	}

	@Override
	public String advancedsearch(DoctorDto doctorDto, BindingResult result, Model model, HttpSession session) 
	{
		if(null != doctorDto && null != doctorDto.getDoctorName() && !doctorDto.getDoctorName().equalsIgnoreCase(""))
		{
			List<DoctorDto> objDoctorEntity = null;
			
			List<Object[]> objHospitalDoctorEntity = hospitalDoctorRepository.findByDoctorName(doctorDto.getDoctorName());
				
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
			
			return "searchPDoctor";
		}
		else
		{
			List<DoctorDto> objDoctorEntity = null;
			
			List<Object[]> objHospitalDoctorEntity = hospitalDoctorRepository.findAllHospitalDoctors();
				
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
			
			ObjectError error = new ObjectError("globalError", "Please Fill All Mandatory Details!");
	        result.addError(error);
			 
			return "searchPDoctor";
		}
	}

	@Override
	public String advancedAdd(long id, Model model) 
	{
		model.addAttribute("doctor", doctorRepository.findByDoctorId(id));
		
		List<Long> objHospitalList = hospitalDoctorRepository.findByDoctorId(id);
		
		List<HospitalEntity> objHospitalEntityList = null;
		
		if(!objHospitalList.isEmpty())
		{
			objHospitalEntityList = hospitalRepository.findByHospitalIds(objHospitalList);
		}
		
		model.addAttribute("hospitals", objHospitalEntityList);
		
		return "addPAppoinment";
	}

	@Override
	public String bookAppoinment(AppointmentDto appointmentDto, DoctorDto doctorDto, BindingResult result, Model model, HttpSession session) 
	{
		if(null != appointmentDto && null != appointmentDto.getHospitalId() && !appointmentDto.getSearchDate().toString().equalsIgnoreCase(""))
		{
			HospitalEntity hospital = new HospitalEntity();
			hospital.setHospitalId(appointmentDto.getHospitalId());
			
			DoctorEntity doctor = new DoctorEntity();
			doctor.setDoctorId(appointmentDto.getDoctorId());
			
			PatientEntity patient = new PatientEntity();
			patient.setPatientId(Long.parseLong(session.getAttribute("y").toString()));
			
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
			appointmentEntity.setAppointmentDate(appointmentstartDate);
			
			Optional<AppointmentEntity> appointmentEntitys = appointmentRepository.findByIdsAndAppointmentDate(appointmentEntity.getPatient().getPatientId(), appointmentEntity.getDoctor().getDoctorId(), appointmentEntity.getHospital().getHospitalId(), appointmentstartDate, appointmentendDate);
			
			if(appointmentEntitys.isPresent())
			{
				ObjectError error = new ObjectError("globalError", "Appointment already present for that particulat date in our system!");
		        result.addError(error);
				 
		        List<DoctorDto> objDoctorEntity = null;
				
				List<Object[]> objHospitalDoctorEntity = hospitalDoctorRepository.findAllHospitalDoctors();
					
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
				
				return "searchPDoctor";
			}
			else
			{
				appointmentRepository.save(appointmentEntity);
				
				ObjectError error = new ObjectError("globalError", "Appointment inserted successfully!");
		        result.addError(error);
				
		        List<DoctorDto> objDoctorEntity = null;
				
				List<Object[]> objHospitalDoctorEntity = hospitalDoctorRepository.findAllHospitalDoctors();
					
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
				
				return "searchPDoctor";
			}
		}
		else
		{
			model.addAttribute("doctor", doctorRepository.findByDoctorId(appointmentDto.getDoctorId()));
			
			List<DoctorDto> objDoctorEntity = null;
			
			List<Object[]> objHospitalDoctorEntity = hospitalDoctorRepository.findByHospitalDoctorId(appointmentDto.getDoctorId());
				
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
			
			
			List<Long> objHospitalList = hospitalDoctorRepository.findByDoctorId(appointmentDto.getDoctorId());
			
			List<HospitalEntity> objHospitalEntityList = null;
			
			if(!objHospitalList.isEmpty())
			{
				objHospitalEntityList = hospitalRepository.findByHospitalIds(objHospitalList);
			}
			
			model.addAttribute("hospitals", objHospitalEntityList);
			
			ObjectError error = new ObjectError("globalError", "Please Fill All Mandatory Details!");
	        result.addError(error);
	        
			return "addPAppoinment";
		}
	}

	@Override
	public String searchAppointment(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session) 
	{
		List<Object[]> objAppointmentEntityList = appointmentRepository.findAllByPatientId(Long.parseLong(session.getAttribute("y").toString()));
		
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
		
		return "searchPAppointment";
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
						
				return "searchPAppointment";
			}
			else
			{
				model.addAttribute("appointments", objAppointmentEntityList);
				
				ObjectError error = new ObjectError("globalError", "Patient mobile Number is not present our system!");
		        result.addError(error);
				 
				return "searchPAppointment";
			}			
		}
		else if(null != searchAppointmentDto && null != searchAppointmentDto.getAppointmentDate() && !searchAppointmentDto.getAppointmentDate().equalsIgnoreCase(""))
		{
			String startDate = searchAppointmentDto.getAppointmentDate().trim() +  "T00:00:00";
			String endDate = searchAppointmentDto.getAppointmentDate().trim() +  "T23:59:59";
			
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			
			LocalDateTime appointmentstartDate = LocalDateTime.parse(startDate, dateTimeFormatter);
			LocalDateTime appointmentendDate = LocalDateTime.parse(endDate, dateTimeFormatter);
			
			List<Object[]> objAppointmentEntityList = appointmentRepository.findByPatientIdAndAppointmentDate(Long.parseLong(session.getAttribute("y").toString()), appointmentstartDate, appointmentendDate);
			
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
			
			return "searchPAppointment";
		}
		else
		{
			ObjectError error = new ObjectError("globalError", "Please Fill All Mandatory Details!");
	        result.addError(error);
	        
	        List<Object[]> objAppointmentEntityList = appointmentRepository.findAllByPatientId(Long.parseLong(session.getAttribute("y").toString()));
	        
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
			
			return "searchPAppointment";
		}
	}
	
	@Override
	public String addReport(long id, LabReportDto labReportDto, Model model) 
	{
		model.addAttribute("appointments", appointmentRepository.findByAppointmentId(id));
		
		return "addPUpload";
	}

	@Override
	public String uploadFile(LabReportDto labReportDto, Model model, HttpSession session, MultipartFile file) 
	{
		String message = "";
		
		try
		{
			if(null != file && null != file.getOriginalFilename() && !file.getOriginalFilename().toString().equalsIgnoreCase("") && null != labReportDto.getAppointmentId())
	    	{
				model.addAttribute("appointments", appointmentRepository.findByAppointmentId(labReportDto.getAppointmentId()));
				
	    		if(file.getOriginalFilename().toString().contains("."))
	    		{
	    			String[] fileparts = file.getOriginalFilename().toString().split("\\.");
	    			
	    			if((null != fileparts[1]) && (fileparts[1].equalsIgnoreCase("pdf") || fileparts[1].equalsIgnoreCase("png") || fileparts[1].equalsIgnoreCase("jpg") || fileparts[1].equalsIgnoreCase("jpeg")))
	    			{
	    				if(file.getSize() > 0)
	    				{
	    					try 
	    					{
	    					    Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
	    					    
	    					    LabReportEntity objLabReportEntity = new LabReportEntity();
	    					    objLabReportEntity.setAppointmentId(labReportDto.getAppointmentId());
	    					    objLabReportEntity.setFileName(fileparts[0]);
	    					    objLabReportEntity.setFileExtension(fileparts[1]);
	    					    objLabReportEntity.setRemarks(labReportDto.getRemarks());
	    					    objLabReportEntity.setStatus(Boolean.TRUE);
	    					    objLabReportEntity.setRecordedDate(LocalDateTime.now());
	    					    
	    					    labReportRepository.saveAndFlush(objLabReportEntity);
	    					    
	    					    message = "File uploaded successfully.";
	    					} 
	    					catch (Exception e)
	    					{
	    					      if (e instanceof FileAlreadyExistsException) 
	    					      {
	    					    	  message = "The Uploaded file of that name already exists.";
	    					      }
	    					}
	    				}
	    				else
	    				{
	    					 message = "Please verify the upload file, it's empty.";
	    				}
	    			}
	    			else
	    			{
	    				 message = "Kindly upload pdf, png, jpg, jpeg file only.";
	    			}
	    		}
	    		else
	    		{
	    			 message = "Uploaded file name should contain file extension.";
	    		}
	    	}
	    	else
	    	{
	    		 message = "Kindly choose the mandatory file to upload.";
	    	}
		}
		catch (Exception e) 
	    {
			e.printStackTrace();
			
	    	message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
	    }
		
		return message;
	}

	@Override
	public String viewReport(long id, LabReportDto labReportDto, Model model) 
	{
		List<LabReportDto> objLabReportDtoList = new ArrayList<LabReportDto>();
		
		List<AppointmentEntity> objAppointmentEntity = appointmentRepository.findByAppointmentId(id);
		
		if(!objAppointmentEntity.isEmpty())
		{
			List<LabReportEntity> objLabReportEntity = labReportRepository.findByAppointmentId(id);
			
			List<LabReportDto> labReportDtoList = objLabReportEntity
					  .stream()
					  .map(user -> modelMapper.map(user, LabReportDto.class))
					  .collect(Collectors.toList());
			
			for(LabReportDto objLabReportDto: labReportDtoList)
			{
				objLabReportDto.setFileFullName(objLabReportDto.getFileName() + "." + objLabReportDto.getFileExtension());
				
				objLabReportDtoList.add(objLabReportDto);
			}
		}
		
		model.addAttribute("reports", objLabReportDtoList);
		
		return "viewReport";
	}
	
	@Override
	public Resource load(String filename) 
	{
	    try 
	    {
	      Path file = root.resolve(filename);
	      
	      Resource resource = new UrlResource(file.toUri());

	      if (resource.exists() || resource.isReadable()) 
	      {
	        return resource;
	      } 
	      else 
	      {
	        throw new RuntimeException("Could not read the file!");
	      }
	    }
	    catch (MalformedURLException e) 
	    {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	}
	
	@Override
	public String viewDReport(long id, LabReportDto labReportDto, Model model) 
	{
		List<LabReportDto> objLabReportDtoList = new ArrayList<LabReportDto>();
		
		List<AppointmentEntity> objAppointmentEntity = appointmentRepository.findByAppointmentId(id);
		
		if(!objAppointmentEntity.isEmpty())
		{
			List<LabReportEntity> objLabReportEntity = labReportRepository.findByAppointmentId(id);
			
			List<LabReportDto> labReportDtoList = objLabReportEntity
					  .stream()
					  .map(user -> modelMapper.map(user, LabReportDto.class))
					  .collect(Collectors.toList());
			
			for(LabReportDto objLabReportDto: labReportDtoList)
			{
				objLabReportDto.setFileFullName(objLabReportDto.getFileName() + "." + objLabReportDto.getFileExtension());
				
				objLabReportDtoList.add(objLabReportDto);
			}
		}
		
		model.addAttribute("reports", objLabReportDtoList);
		
		return "viewDReport";
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
		
		return "viewPPrescription";
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
