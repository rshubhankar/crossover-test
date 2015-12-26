package com.exam.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.exam.domain.Answer;
import com.exam.domain.Candidate;
import com.exam.domain.Exam;
import com.exam.domain.Question;
import com.exam.repository.ExamMakerRepository;
import com.exam.service.ExamMakerService;
import com.exam.web.interceptor.ExamInterceptor;

/**
 * Annotation Web Application Configuration 
 * 
 * @author shubhankar_roy
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"com.exam.domain", "com.exam.repository", "com.exam.service", "com.exam.web.controller", "com.exam.web.interceptor"})
public class AppContextConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Exam exam() {
		return new Exam();
	}
	
	@Bean
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Question question() {
		return new Question();
	}
	
	@Bean
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Answer answer() {
		return new Answer();
	}
	
	@Bean
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Candidate candidate() {
		return new Candidate();
	}
	
	@Bean
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public List<Question> questions() {
		return ListFactory.getInstance().getArrayList(Question.class);
	}
	
	@Bean
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public List<Answer> answers() {
		return ListFactory.getInstance().getArrayList(Answer.class);
	}
	
	@Bean
	public DataSource dataSource() throws ClassNotFoundException {
		ApplicationConfiguration appConfig = ApplicationConfiguration.getInstance();
		Class.forName(appConfig.get("jdbcDriver"));
		return new DriverManagerDataSource(appConfig.get("jdbcUrl"), appConfig.get("jdbcUser"), appConfig.get("jdbcPassword"));
	}
	
	@Bean
	@Autowired
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public ExamMakerRepository examMakerRepository() {
		return new ExamMakerRepository();
	}
	
	@Bean
	public ExamMakerService examMakerService() {
		return new ExamMakerService();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ExamInterceptor());
	}
	
	@Bean
	public ViewResolver internalViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
	}
}
