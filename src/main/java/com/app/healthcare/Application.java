package com.app.healthcare;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;

import com.app.healthcare.service.PatientService;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication 
@EnableSwagger2
public class Application implements CommandLineRunner
{
	@Autowired
	public PatientService patientService;
	
    public static void main( String[] args )
    {
    	 SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Docket ValueEatsApi()
    {
       return new Docket(DocumentationType.SWAGGER_2).select()
          .apis(RequestHandlerSelectors.basePackage("com.app.healthcare.controller")).build();
    }
    
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }
    
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() 
    {
        return (container -> 
        {
            container.setSessionTimeout(1000);  // session timeout value
        });
    }
    
    @Override
    public void run(String... arg) throws Exception 
    {
    	patientService.init();
    }
}
