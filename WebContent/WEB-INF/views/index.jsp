<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Online Test Exam Maker</title>
</head>
<body>
	<form:form method="POST" action="login" commandName="candidate">
		<table style="width: 100%; height: 100%; position:fixed; ">
			<tr>
				<td style="width: 80%; vertical-align: middle;" align="center">${exam.description}</td>
				<td style="vertical-align: middle;">
					<form:label path="username">Username</form:label>
					<span style="color: red;"><form:errors path="username"/></span><br/>
					<form:input path="username"/><br/>
					<form:label path="password">Password</form:label>
					<span style="color: red;"><form:errors path="password"/></span><br/>
					<form:password path="password"/><br/>
					<input type="hidden" name="examId" value="${exam.id}"/>
					<input type="submit" value="Login"/>
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>