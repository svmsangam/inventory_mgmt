<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>AMT</title>

<!-- CSS -->
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/multistepform/assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/multistepform/assets/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/multistepform/assets/css/form-elements.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/multistepform/assets/css/style.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/multistepform/assets/css/select2.css" />

<!--link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/favicon.png"-->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-144-precomposed.png" />
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-114-precomposed.png" />
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-72-precomposed.png" />
<link rel="apple-touch-icon-precomposed"
	href="${pageContext.request.contextPath}/resources/multistepform/ssets/ico/apple-touch-icon-57-precomposed.png" />
<%@include file="../common/header.jsp"%>
<%@include file="../common/navbar.jsp"%>
<%@include file="../common/sidebar.jsp"%>
<body>

	<!-- Top content -->
	<div class="top-content">
		<div class="container select2-container">

			<div class="row">
				<div
					class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3 form-box">
					<div class="form-group">
						<form method="post" enctype="multipart/form-data"
							action="${pageContext.request.contextPath}/uploadTransactionFile">
							<h3>Transaction File Upload</h3>
							<p>Please select transaction file to upload</p>
							<input type="file" name="file" /> <br /> <input type="submit"
								value="Upload File" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- Javascript -->
	<script
		src="${pageContext.request.contextPath}/resources/multistepform/assets/js/jquery-1.11.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/multistepform/assets/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/multistepform/assets/js/jquery.backstretch.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/multistepform/assets/js/retina-1.1.0.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/multistepform/assets/js/scripts.js"></script>

	<!--[if lt IE 10]>
            <script src="assets/js/placeholder.js"></script>
        <![endif]-->

</body>

</html>