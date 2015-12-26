<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome, ${username}</title>
<spring:url value="/js/jquery-1.11.3.min.js" var="jqueryUrl" ></spring:url>
<script type="text/javascript" src="${jqueryUrl}"></script>
<script type="text/javascript">
	var timeinterval;
	var backTimeInterval;
	var timer;
	
	function startTest() {
		var questionSelect = document.getElementById('question_no');
		questionSelect.options[0].text = "Select a question";
		questionSelect.disabled = false;
		
		var stop_button = document.getElementById("stop_button");
		stop_button.style.visibility = 'visible';
		
		var toggle_button = document.getElementById("toggle_button");
		toggle_button.value = "Go";
		toggle_button.setAttribute("onclick", "showQuestion()");
		toggle_button.disabled = false;
		startTimer(document.examForm.examTimer.value, document.getElementById("timerSpan"));
	}
	
	function stopTest() {
		if(timer > 0 && !checkIsTestCompleted()) {
			return;
		} 
		var questionSelect = document.getElementById('question_no');
		questionSelect.disabled = true;
		questionSelect.selectedIndex = "0";
		timer = 0;
		updateTimer(document.getElementById("timerSpan"));	
	}
	
	function checkIsTestCompleted() {
		var unansweredCount = 0;
		var unanswered;
		for(var i = 0; ; i++) {
			var question = document.getElementById("questions[" + i + "].id");
			
			if(question) {
				unanswered = true;
				for (j = 0; ; j++) {
					var radioGroup = "questions[" + i + "].answers[" + j + "].id";
					if(document.getElementsByName(radioGroup).length == 0) {
						break;
					}
					
			        if (document.getElementsByName(radioGroup)[0].checked) {
			        	unanswered = false;
			        	break;
			        }
			    }
				
				if(unanswered) {
					unansweredCount++;
				}
			} else {
				break;
			}
		}
		if(unansweredCount > 0) {
			return confirm('Are you sure you want to submit. There are still ' + unansweredCount + ' question(s) left.');
		}
		
		return true;
	}
	
	function startTimer(duration, display) {
	    timer = duration;

	    backTimeInterval = setInterval(function() {
			$.post("callbackTimer", {
			        timeLeft: timer,
			        candidateExamId: "${candidateExamId}"
			    },function(data, status){
		        	console.log("Status: " + status);
		    });
		}, 2000);
	    
	    updateTimer(display);	    
	    timeinterval = setInterval(function () {
	    	updateTimer(display);
	        if (timer < 0) {
	            //timer = duration;
	            stopTest();
	            clearInterval(timeinterval);
	            clearInterval(backTimeInterval);
	    		window.alert("Your exam has finished, click Ok to show the result.");
	    		document.examForm.submit();
	        }
	    }, 1000);
	}
	
	
	function updateTimer(display) {
		var hours, minutes, seconds;
		hours = parseInt(timer / 3600, 10);
    	timer = timer % 3600;
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        hours = hours < 10 ? "0" + hours : hours;
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        display.innerHTML = (hours + ":" + minutes + ":" + seconds);
        
        timer--;
	}
	
	var currentQuestionNo = 0;
	
	function showQuestion() {
		var questionT = document.getElementById('question_' + currentQuestionNo);
		if(questionT) {
			questionT.style.visibility = 'hidden';
			questionT.style.display = 'none';	
		}
		
		var select = document.getElementById('question_no');
		var selectedValue = select.options[select.selectedIndex].value;
		questionT = document.getElementById('question_' + selectedValue);
		questionT.style.visibility = 'visible';
		questionT.style.display = 'block';
		currentQuestionNo = selectedValue;
	}
</script>
</head>
<body>
	<form:form commandName="exam" name="examForm" action="evaluate" method="POST">
		<table border="1" style="width: 100%">
			<tr>
				<td style="width: 20%">
					<select id="question_no" name="question_no" disabled="disabled">
						<option value="0">Click on Start to begin the Timer</option>
						<c:forEach items="${exam.questions}" var="question" varStatus="status">
							<option value="${status.count}">Question ${status.count}</option>
						</c:forEach>
					</select>
				</td>
				<td align="center">
					<span style="float: left;">
						<input type="button" id="toggle_button" value="Start" onclick="startTest()"/>
					</span>
					<span style="visibility: hidden; float: right;">
						<input type="button" id="stop_button" value="Stop" onclick="stopTest()"/>
					</span>
					<span id="timerSpan"></span>
					<input type="hidden" name="examTimer" value="${exam.timer.duration}" />
				</td>
			</tr>
		</table>
		
		<c:forEach items="${exam.questions}" var="question" varStatus="status">
			<div id="question_${status.count}" style="visibility: hidden; display: none;">
				<table style="vertical-align: middle;">
			        <tr>
			            <td align="left">${status.count}</td>
		        		<td align="left">${question.description}
		        			<input type="hidden" value="${question.id}" id="questions[${status.index}].id" name="questions[${status.index}].id" />
		        		</td>
		        	</tr>
		        	<c:forEach items="${question.answers}" var="answer" varStatus="answer_status">
		        		<tr>
				            <td colspan="2"><input type="checkbox" name="questions[${status.index}].answers[${answer_status.index}].id" value="${answer.id}"/>${answer.detail}</td>
				        </tr>
		        	</c:forEach> 
		        </table>
	        </div>
	    </c:forEach>
    	<form:hidden path="id"/>
    	<input type="hidden" name="username" value="${username}"/>
    	<input type="hidden" name="candidateExamId" value="${candidateExamId}"/>
	</form:form>
</body>
</html>