package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

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
	private ExamMakerRepository examMakerRepository;
	
	/**
	 * @return exam
	 */
	public Exam getExam() {
		List<Exam> exams = examMakerRepository.getExams();
		if(exams != null && !exams.isEmpty()) {
			Exam exam = exams.stream().findFirst().get();
			exam.setQuestions(examMakerRepository.getQuestionsWithAllAnswersForExam(exam.getId()));
			return exam;
		} else {
			throw new ExamMakerException("No exams found in Application.");
		}
	}
	
	/**
	 * @return exam
	 */
	public Exam getExam(int examId) {
		return examMakerRepository.getExamById(examId);
	}
	
	/**
	 * Get Exam Questions.
	 * 
	 * @return exam
	 */
	public List<Question> getExamQuestions(Exam exam) {
		Validate.notNull(exam, "Exam object cannot be null.");
		return examMakerRepository.getQuestionsWithAllAnswersForExam(exam.getId());
	}
	
	/**
	 * Evaluates the count of correct answers and returns count. 
	 * 
	 * @param examId
	 * @param answers
	 * @return score of the exam
	 */
	public double evaluateExam(int examId, List<Question> attemptedQuestions) {
		List<Question> questions = examMakerRepository.getQuestionsWithCorrectAnswersForExam(examId);
		int totalCount = questions.size();
		attemptedQuestions.stream().forEach(q -> {
			List<Answer> answers = q.getAnswers();
			List<Answer> filteredAttemptedAnswers = answers.stream().filter(a -> a.getId() > 0).collect(Collectors.<Answer>toList());
			answers.clear();
			answers.addAll(filteredAttemptedAnswers);
		});
		
		long correctCount = attemptedQuestions.stream().filter(q -> {
			int questionIndexInList = questions.indexOf(q);
			List<Answer> attemptedAnswers = q.getAnswers();
			logger.debug(attemptedAnswers.toString());
			if(questionIndexInList != -1 && attemptedAnswers != null) {
				Question question = questions.get(questionIndexInList);
				List<Answer> answers = question.getAnswers();
				logger.debug(answers.toString());
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
				logger.error("Invalid question or question was not attempted : {}, for exam: {}", q.getId(), examId);
				return false;
			}
		}).count();
		
		return ((correctCount * 100.0)/ totalCount);
	}
	
	/**
	 * Save Candidate result.
	 * 
	 * @param candidate
	 * @param exam
	 * @param result
	 * @return candidate exam id 
	 */
	public int addCandidateExam(Candidate candidate, Exam exam) {
		return examMakerRepository.addCandidateExam(candidate, exam);
	}
	
	/**
	 * Save Candidate result.
	 * 
	 * @param candidate
	 * @param exam
	 * @param result
	 */
	public void saveCandidateResult(int candidateExamId, double result) {
		examMakerRepository.saveCandidateResult(candidateExamId, result);
	}
	
	/**
	 * Authenticate the candidate, if candidate not present in DB then add this candidate.
	 * 
	 * @param username
	 * @param password
	 * @return authenticated
	 */
	public boolean autheticateCandidate(Candidate candidate) {
		Candidate pCandidate = examMakerRepository.getCandidate(candidate.getUsername());
		if(pCandidate != null) {
			return pCandidate.getPassword().equals(candidate.getPassword());
		} else {
			examMakerRepository.saveCandidate(candidate);
			return true;
		}
	}
	
	/**
	 * Get Candidate object.
	 * 
	 * @param username
	 * @return candidate object
	 */
	public Candidate getCandidate(String username) {
		return examMakerRepository.getCandidate(username);
	}
}
