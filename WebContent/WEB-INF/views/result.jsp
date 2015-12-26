<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Result</title>
<script type="text/javascript">
	function test() {
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<h3>Your result is: ${result}</h3>
	<div>
		<form action="questions">
			<input type="hidden" name="examId" value="${exam.id}"/>
			<p>
				<a href="javascript: test()">Wanna give another try ?</a>
			</p>
		</form>
		
	</div>
</body>
</html>