<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Upload File Request Page</title>

<c:url var="home" value="/" scope="request" />
<spring:url value="/resources/core/js/jquery.1.10.2.min.js"	var="jqueryJs" />
<script src="${jqueryJs}"></script>

</head>
<body>

	<form method="POST" action="uploadFile" enctype="multipart/form-data">
<!-- 		File to upload: <input type="file" name="file"><br />  -->
		Name : <input type="text" name="name" id="fname"><br /> <br /> 
		Email-id : <input type="text" name="name" id="mobNumber"><br /> <br /> 
		<input type="button" id="otpGenerate" value="Generate OTP" onclick="myFunction()"/> 
		<br />
		<p id="otpGenerated"></p>
		Otp : <input type="text" name="otpname" id="otpname"><br /> <br /> 
		<input type="button" id="verifyOtp1" value="Verify OTP" onclick="verifyOtp()"/> 
		<p id="otpStatus"></p>
<!-- 		<input type="submit" value="Upload"> Press here to upload the file! -->
	</form>

	<script>
	function myFunction() {
		var search = {}
		value = $("#mobNumber").val();
		search["mobilenumber"]=value
		
		$("#fname").attr('disabled','disabled');
		$("#mobNumber").attr('disabled','disabled');
		
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "${home}generateOtp",
				data : JSON.stringify(search),
				dataType : 'json',
				timeout : 100000,
				success : function(data) {
					console.log("SUCCESS: ", data);
					$("#otpGenerated").text();
					alert(data);
				},
				error : function(e) {
					console.log("ERROR: ", e);
					alert(e);
				},
				done : function(e) {
					console.log("DONE");
					alert("DONE")
					enableSearchButton(true);
				}
			});
		
	
	}
	function verifyOtp() {
		var search = {}
		search["mobilenumber"]=$("#mobNumber").val();
		value = $("#otpname").val(); 
		search["otpData"]=value
		
		alert("OTP DATA " +search["otpData"])
		alert("Mobile Number " +search["mobilenumber"])
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "${home}verifyOtp",
		data : JSON.stringify(search),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			$("#otpStatus").text(data["message"]);
			alert(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			alert(e);
		},
		done : function(e) {
			console.log("DONE");
			alert("DONE")
			enableSearchButton(true);
		}
	});
	}
</script>

</body>
</html>