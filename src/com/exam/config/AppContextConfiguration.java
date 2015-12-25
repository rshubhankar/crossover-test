package com.exam.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.exam.domain.Answer;
import com.exam.domain.Exam;
import com.exam.domain.Question;
import com.exam.web.interceptor.ExamInterceptor;

/**
 * Annotation Web Application Configuration 
 * 
 * @author shubhankar_roy
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"com.exam.domain", "com.exam.repository"})
public class AppContextConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	public Exam exam() {
		return new Exam();
	}
	
	@Bean
	public List<Question> questions() {
		return ListFactory.getInstance().getArrayList(Question.class);
	}
	
	@Bean
	public List<Answer> answers() {
		return ListFactory.getInstance().getArrayList(Answer.class);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ExamInterceptor());
	}
}
