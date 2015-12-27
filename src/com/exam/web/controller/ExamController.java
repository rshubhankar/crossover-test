package com.exam.web.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.exam.domain.Candidate;
import com.exam.domain.Exam;
import com.exam.service.ExamMakerService;

/**
 * Controller class for the 3 pages.
 * 
 * @author shubhankar_roy
 *
 */
@Controller
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
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String showExam(Model model) {
		logger.debug("showExam() - [enter]");
		Exam exam = examMakerService.getExam();
		model.addAttribute("exam", exam);
		if(!model.containsAttribute("candidate")) {
			model.addAttribute("candidate", new Candidate());
		}
		logger.debug("showExam() - [leave]");
		return "index";
	}
	
	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String login(Model model, @Valid @ModelAttribute Candidate candidate, BindingResult result, HttpSession session) {
		logger.debug("login() - [enter]");
		String forward;
		if(result.hasErrors()) {
			forward = showExam(model);
		} else if(examMakerService.autheticateCandidate(candidate)) {
			session.setAttribute("username", candidate.getUsername());
			forward = "forward:/questions"; //(model, candidate, examId, session);
		} else {
			result.addError(new FieldError("candidate", "username", "Invalid username/password."));
			forward = showExam(model);
		}
		logger.debug("login() - [leave]");
		return forward;
	}

	@RequestMapping(value = { "/questions" })
	private String questions(Model model, @RequestParam(defaultValue="1") int examId, HttpSession session) {
		Exam exam = examMakerService.getExam(examId);
		exam.setQuestions(examMakerService.getExamQuestions(exam));
		model.addAttribute("exam", exam);
		String username = (String) session.getAttribute("username");
		int candidateExamId = examMakerService.addCandidateExam(examMakerService.getCandidate(username), exam);
		model.addAttribute("candidateExamId", candidateExamId);
		return "questions";
	}
	
	@RequestMapping(value = { "/evaluate" }, method = RequestMethod.POST)
	public String evaluate(@ModelAttribute Exam exam, Model model, 
			@RequestParam int candidateExamId, @RequestParam String username) {
		double result = examMakerService.evaluateExam(exam.getId(), exam.getQuestions());
		examMakerService.saveCandidateResult(candidateExamId, result);
		model.addAttribute("exam", exam);
		model.addAttribute("result", result);
		return "result";
	}
	
	@RequestMapping(value = { "/callbackTimer" }, method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void callbackTimer(@RequestParam int candidateExamId, @RequestParam long timeLeft) {
		logger.debug("callbackTimer() Candidate Exam Id {}, Time Left: {}", candidateExamId, timeLeft);
	}
}
