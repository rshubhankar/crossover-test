package com.exam.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.exam.config.WebPropertiesConfigurationListener;
import com.exam.domain.Answer;
import com.exam.domain.Candidate;
import com.exam.domain.Exam;
import com.exam.domain.Question;

/**
 * Repository class to fetch exam details, questions and answers. This class can also be used to store user answers.
 * 
 * @author shubhankar_roy
 *
 */
@Repository
public class ExamMakerRepository {
	/**
	 * logger instance
	 */
	private final Logger logger = LoggerFactory.getLogger(WebPropertiesConfigurationListener.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * @return list of exams.
	 */
	public List<Exam> getExams() {
		logger.debug("Get Exams");
		return jdbcTemplate.query("SELECT * FROM EXAM", (rs, rnum) -> {
			Exam exam = new Exam();
			exam.setId(rs.getInt("id"));
			exam.setDescription(rs.getString("description"));
			return exam;
		});
	}
	
	/**
	 * Get Exam by Id.
	 * 
	 * @param examId
	 * @return exam
	 */
	public Exam getExamById(int examId) {
		logger.debug("Get Exam by id: {}", examId);
		return jdbcTemplate.query("SELECT * FROM EXAM WHERE ID = ?", 
					(ResultSetExtractor<Exam>)((rs) -> {
			Exam exam = null;
			if(rs.next()) {  
				exam = new Exam();
				exam.setId(rs.getInt("id"));
				exam.setDescription(rs.getString("description"));
			}
			return exam;
		}), new Object[] {examId});
	}
	
	/**
	 * Get list of questions for an exam.
	 * 
	 * @param exam
	 * @return list of questions
	 */
	private List<Question> getQuestionsForExam(String questionsQuery, Object...args) {
		return jdbcTemplate.query(questionsQuery, args, 
					(ResultSetExtractor<List<Question>>)(rs -> {
			final List<Question> questions = new ArrayList<>();
			final Map<Integer, Question> questionsMap = new HashMap<>();
			int questionId;
			while(rs.next()) {
				questionId = rs.getInt("ques_id");
				Question question = questionsMap.get(questionId);
				if(question == null) {
					question = new Question();
					question.setId(questionId);
					question.setDescription(rs.getString("ques_desc"));
					questionsMap.put(questionId, question);
				}
				Answer answer = new Answer(question);
				answer.setId(rs.getInt("ans_id"));
				answer.setDetail(rs.getString("ans_desc"));
				question.getAnswers().add(answer);
			}
			questionsMap.forEach((qId, q) -> {
				questions.add(q);
			});
			return questions;
		}));
	}
	
	/**
	 * Get list of questions with all answers.
	 * 
	 * @param exam
	 * @return list of questions
	 */
	public List<Question> getQuestionsWithAllAnswersForExam(int examId) {
		logger.debug("Get Questions for Exam: {}", examId);
		return getQuestionsForExam("SELECT Q.ID AS QUES_ID, Q.DESCRIPTION AS QUES_DESC, A.ID AS ANS_ID, A.DESCRIPTION AS ANS_DESC FROM QUESTION Q, ANSWER A WHERE EXAM_ID = ? AND Q.ID = A.QUESTION_ID", 
					new Object[] {examId});
	}
	
	/**
	 * Get list of questions with only correct answers.
	 * 
	 * @param exam
	 * @return list of questions
	 */
	public List<Question> getQuestionsWithCorrectAnswersForExam(int examId) {
		logger.debug("Get Questions for Exam: {}", examId);
		return getQuestionsForExam("SELECT Q.ID AS QUES_ID, Q.DESCRIPTION AS QUES_DESC, A.ID AS ANS_ID, A.DESCRIPTION AS ANS_DESC FROM QUESTION Q, ANSWER A WHERE EXAM_ID = ? AND Q.ID = A.QUESTION_ID AND A.IS_CORRECT = 1", 
					new Object[] {examId});
	}
	
	public Candidate getCandidate(String username) {
		
		return jdbcTemplate.query("SELECT * FROM CANDIDATE WHERE LOWER(USERNAME) = LOWER(?)", (ResultSetExtractor<Candidate>)(rs) -> {
			Candidate candidate = null;
			if(rs.next()) {
				candidate = new Candidate();
				candidate.setId(rs.getInt("id"));
				candidate.setUsername(rs.getString("username"));
				candidate.setPassword(rs.getString("password"));
			}
			return candidate;
		});
	}
	
	/**
	 * Save candidate in database
	 * 
	 * @param candidate
	 */
	public void saveCandidate(Candidate candidate) {
		logger.debug("Save candidate: {}", candidate);
		jdbcTemplate.update("INSERT INTO CANDIDATE (USERNAME, PASSWORD) VALUES (?, ?)", new Object[] {candidate.getUsername(), candidate.getPassword()}); 
	}
}
