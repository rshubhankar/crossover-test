package com.exam.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.exam.domain.Exam;
import com.exam.service.ExamMakerService;

/**
 * Controller class for the 3 pages.
 * 
 * @author shubhankar_roy
 *
 */
@Controller
@RequestMapping(value="/Exam")
public class ExamController {

	/**
	 * logger instance
	 */
	private final Logger logger = LoggerFactory.getLogger(ExamController.class);
	
	/**
	 * Service instance
	 */
	@Autowired
	private ExamMakerService examMakerService;
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String showExam(Model model) {
		logger.debug("showExam() - [enter]");
		Exam exam = examMakerService.getExam();
		model.addAttribute("exam", exam);
		logger.debug("showExam() - [leave]");
		return "index";
	}
	
	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String login(Model model, @RequestParam String username, @RequestParam String password, @RequestParam Integer examId) {
		logger.debug("login() - [enter]");
		String forward;
		if(examMakerService.autheticateCandidate(username, password)) {
			Exam exam = examMakerService.getExam(examId);
			model.addAttribute("exam", exam);
			forward = "/question";
		} else {
			model.addAttribute("error", "Invalid password for username:" + username);
			forward = "index";
		}
		logger.debug("login() - [leave]");
		return forward;
	}
}
