/**
 * 
 */
package com.exam.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.exam.config.AppContextConfiguration;
import com.exam.config.ApplicationConfiguration;
import com.exam.config.DomainFactory;
import com.exam.domain.Candidate;
import com.exam.domain.Exam;
import com.exam.service.ExamMakerService;

/**
 * @author Shubhankar
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContextConfiguration.class})
@WebAppConfiguration
public class ExamControllerTest {

	private MockMvc mockMvc;
	
	@Autowired 
	private WebApplicationContext wac;
	 
	@Mock
	private ExamMakerService examMakerService;
 
    @InjectMocks
    private ExamController examController;
	
    @BeforeClass
	public static void setUpBeforeClass() {
		File file = new File("TestExamMaker-web.properties");
		System.out.println(file.getAbsolutePath());
		if(file.exists()) {
			ApplicationConfiguration.getInstance().initialize(file.getAbsolutePath());	
		}
	}
    
    @Before
    public void setUp() {
    	DomainFactory.getInstance().setContext(wac);
    	// Process mock annotations
        MockitoAnnotations.initMocks(this);
 
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(examController).build();
    }
    
	/**
	 * Test method for {@link com.exam.web.controller.ExamController#showExam(org.springframework.ui.Model)}.
	 * @throws Exception 
	 */
	@Test
	public void testShowExam() throws Exception {
		Exam mockExam = new Exam();
		Mockito.when(examMakerService.getExam()).thenReturn(mockExam);
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(forwardedUrl("index"))
                .andExpect(model().attributeExists("exam"))
				;
	}

	/**
	 * Test method for {@link com.exam.web.controller.ExamController#login(org.springframework.ui.Model, com.exam.domain.Candidate, org.springframework.validation.BindingResult, javax.servlet.http.HttpSession)}.
	 * @throws Exception 
	 */
	@Test
	public void testLogin() throws Exception {
		Mockito.when(examMakerService.autheticateCandidate(Mockito.any(Candidate.class))).thenReturn(true, false, true);
		Exam mockExam = new Exam();
		Mockito.when(examMakerService.getExam(Mockito.anyInt())).thenReturn(mockExam);
		mockMvc.perform(MockMvcRequestBuilders.post("/login").param("username", "abcdef").param("password", "qwerty").param("examId", "1"))
				.andExpect(status().isOk())
				.andExpect(view().name("forward:/questions"))
				.andExpect(forwardedUrl("/questions"));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/login").param("username", "abcdef").param("password", "qwerty1").param("examId", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"))
		.andExpect(forwardedUrl("index"))
		.andExpect(model().hasErrors());
		
		// Binding Result Error
		mockMvc.perform(MockMvcRequestBuilders.post("/login").param("username", "abcdef").param("password", "q").param("examId", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"))
		.andExpect(forwardedUrl("index"))
		.andExpect(model().hasErrors());
	}

	/**
	 * Test method for {@link com.exam.web.controller.ExamController#evaluate(com.exam.domain.Exam, org.springframework.ui.Model, int, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testEvaluate() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/evaluate").param("username", "abcdef").param("candidateExamId", "1").param("id", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("result"))
		.andExpect(forwardedUrl("result"))
		.andExpect(model().hasNoErrors())
		.andExpect(model().attributeExists("exam"))
		.andExpect(model().attributeExists("result"));
	}

	/**
	 * Test method for {@link com.exam.web.controller.ExamController#callbackTimer(int, long)}.
	 * @throws Exception 
	 */
	@Test
	public void testCallbackTimer() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/callbackTimer").param("candidateExamId", "1").param("timeLeft", "1800"))
		.andExpect(status().isOk());
	}

}
