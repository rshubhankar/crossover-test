package com.exam.service;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.domain.Answer;
import com.exam.domain.Candidate;
import com.exam.domain.Exam;
import com.exam.domain.Question;
import com.exam.repository.ExamMakerRepository;

/**
 * Service class to fetch exam details, questions and answers. 
 * 
 * @author shubhankar_roy
 *
 */
@Service
public class ExamMakerService {
	/**
	 * logger instance
	 */
	private final Logger logger = LoggerFactory.getLogger(ExamMakerService.class);
	/**
	 * repository instance
	 */
	@Autowired
	private ExamMakerRepository testMakerRepository;
	
	/**
	 * @return exam
	 */
	public Exam getExam() {
		List<Exam> exams = testMakerRepository.getExams();
		if(exams != null && !exams.isEmpty()) {
			Exam exam = exams.stream().findFirst().get();
			exam.setQuestions(testMakerRepository.getQuestionsWithAllAnswersForExam(exam.getId()));
			return exam;
		} else {
			throw new ExamMakerException("No exams found in Application.");
		}
	}
	
	/**
	 * @return exam
	 */
	public Exam getExam(int examId) {
		return testMakerRepository.getExamById(examId);
	}
	
	/**
	 * Get Exam Questions.
	 * 
	 * @return exam
	 */
	public List<Question> getExamQuestions(Exam exam) {
		Validate.notNull(exam, "Exam object cannot be null.");
		return testMakerRepository.getQuestionsWithAllAnswersForExam(exam.getId());
	}
	
	/**
	 * Evaluates the count of correct answers and returns count. 
	 * 
	 * @param examId
	 * @param answers
	 * @return score of the exam
	 */
	public long evaluateExam(int examId, List<Question> attemptedQuestions) {
		List<Question> questions = testMakerRepository.getQuestionsWithCorrectAnswersForExam(examId);
		return attemptedQuestions.stream().filter(q -> {
			int questionIndexInList = questions.indexOf(q);
			if(questionIndexInList != -1) {
				Question question = questions.get(questionIndexInList);
				List<Answer> answers = question.getAnswers();
				List<Answer> attemptedAnswers = q.getAnswers();
				if(answers.size() == 1 && attemptedAnswers.size() == 1) {
					return answers.stream().findFirst().get().equals(attemptedAnswers.stream().findFirst().get());
				} else {
					int correctAnswers = answers.size();
					if(correctAnswers > 1  && attemptedAnswers.size() > 1){
						return answers.stream().filter(a -> attemptedAnswers.contains(a)).count() == correctAnswers;
					} else {
						return false;
					}
				}
			} else {
				logger.error("Invalid question found: {}, for exam: {}", q.getId(), examId);
				return false;
			}
		}).count();
	}
	
	/**
	 * Authenticate the candidate, if candidate not present in DB then add this candidate.
	 * 
	 * @param username
	 * @param password
	 * @return authenticated
	 */
	public boolean autheticateCandidate(String username, String password) {
		Candidate candidate = testMakerRepository.getCandidate(username);
		if(candidate != null) {
			return candidate.getPassword().equals(password);
		} else {
			candidate = new Candidate();
			candidate.setUsername(username);
			candidate.setPassword(password);
			testMakerRepository.saveCandidate(candidate);
			return true;
		}
		
	}
}
