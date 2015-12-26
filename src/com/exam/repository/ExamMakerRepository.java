package com.exam.repository;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.exam.config.DomainFactory;
import com.exam.config.WebPropertiesConfigurationListener;
import com.exam.domain.Answer;
import com.exam.domain.Candidate;
import com.exam.domain.Exam;
import com.exam.domain.Question;
import com.exam.domain.Timer;

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
	
	private final DomainFactory domainFactory = DomainFactory.getInstance();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * @return list of exams.
	 */
	public List<Exam> getExams() {
		logger.debug("Get Exams");
		return jdbcTemplate.query("SELECT * FROM EXAM", (rs, rnum) -> {
			Exam exam = domainFactory.getBean(Exam.class);
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
				exam = domainFactory.getBean(Exam.class);
				exam.setId(rs.getInt("id"));
				exam.setDescription(rs.getString("description"));
				Timer timer = new Timer();
				long duration = rs.getLong("timer");
				TimeUnit dbTimeUnit = TimeUnit.valueOf(rs.getString("time_unit"));
				TimeUnit timeUnit = timer.getTimeUnit();
				duration = timeUnit.convert(duration, dbTimeUnit);
				timer.setDuration(duration);
				exam.setTimer(timer);
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
			final List<Question> questions = domainFactory.getBean("questions");
			final Map<Integer, Question> questionsMap = new HashMap<>();
			int questionId;
			while(rs.next()) {
				questionId = rs.getInt("ques_id");
				Question question = questionsMap.get(questionId);
				if(question == null) {
					// TODO Question object is created with a default answer ???
					//question = domainFactory.getBean(Question.class);
					question = new Question();
					question.setId(questionId);
					question.setDescription(rs.getString("ques_desc"));
					questionsMap.put(questionId, question);
				}
				Answer answer = domainFactory.getBean(Answer.class);
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
		
		return jdbcTemplate.query("SELECT * FROM CANDIDATE WHERE LOWER(USERNAME) = LOWER(?)", new Object[]{username}, (ResultSetExtractor<Candidate>)(rs) -> {
			Candidate candidate = null;
			if(rs.next()) {
				candidate = domainFactory.getBean(Candidate.class);
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

	/**
	 * Add Candidate Exam.
	 * 
	 * @param candidate
	 * @param exam
	 * @param result
	 * @return candidate exam id
	 */
	public int addCandidateExam(Candidate candidate, Exam exam) {
		logger.debug("Add Candidate Exam entry: {}", candidate);
		Calendar calendar = Calendar.getInstance();
		jdbcTemplate.update("INSERT INTO CANDIDATE_EXAM (CANDIDATE_ID, EXAM_ID, DATE_START) VALUES (?, ?, ?)", new Object[] {candidate.getId(), exam.getId(), calendar.getTime()});
		return jdbcTemplate.query("SELECT MAX(ID) AS ID FROM CANDIDATE_EXAM WHERE CANDIDATE_ID = ? AND EXAM_ID = ?", new Object[] {candidate.getId(), exam.getId()}, ((ResultSetExtractor<Integer>)rs -> {
			Integer id = null;
			if(rs.next()) {
				id = rs.getInt("ID");
			}
			return id;
		})); 
	}
	
	/**
	 * Save Candidate result.
	 * 
	 * @param candidate
	 * @param exam
	 * @param result
	 */
	public void saveCandidateResult(int candidateExamId, double result) {
		logger.debug("Save candidate result for {}", candidateExamId);
		Calendar calendar = Calendar.getInstance();
		jdbcTemplate.update("UPDATE CANDIDATE_EXAM SET RESULT = ?, DATE_END = ? WHERE ID = ?", new Object[] {result, calendar.getTime(), candidateExamId});
	}
}
