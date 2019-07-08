<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AMT</title>

<!-- CSS -->
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/multistepform/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/multistepform/assets/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/form-elements.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/select2.css">

<!--link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/favicon.png"-->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
      href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
      href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
      href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
      href="${pageContext.request.contextPath}/resources/multistepform/ssets/ico/apple-touch-icon-57-precomposed.png">

<%@include file="../common/header.jsp" %>

<body>
<%@include file="../common/navbar.jsp" %>
<%@include file="../common/sidebar.jsp" %>

<!-- Top menu -->

<!-- Top content -->
<div class="top-content">
    <div class="container select2-container">

        <!-- <div class="row">
            <div class="col-sm-8 col-sm-offset-2 text">
                <h1>Free <strong>Bootstrap</strong> Wizard</h1>
                <div class="description">
                       <p>
                        This is a free responsive Bootstrap form wizard.
                        Download it on <a href="http://azmind.com"><strong>AZMIND</strong></a>, customize and use it as you like!
                    </p>
                </div>
            </div>
        </div> -->

        <div class="row">
            <div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3 form-box">
                <form role="form" action="" method="post" class="f1">

                    <h3>Create Transaction</h3>
                    <p>Fill in the form to transfer money</p>
                    <div class="f1-steps">
                        <div class="f1-progress">
                            <div class="f1-progress-line" data-now-value="16.66" data-number-of-steps="3"
                                 style="width: 16.66%;"></div>
                        </div>
                        <div class="f1-step active">
                            <div class="f1-step-icon">
                                <svg class="glyph stroked chevron right">
                                    <use xlink:href="#stroked-chevron-right"/>
                                </svg>
                            </div>

                            <p>Sender</p>
                        </div>
                        <div class="f1-step">
                            <div class="f1-step-icon">
                                <svg class="glyph stroked download">
                                    <use xlink:href="#stroked-download"/>
                                </svg>
                            </div>
                            <p>Receiver</p>
                        </div>
                        <div class="f1-step">
                            <div class="f1-step-icon">
                                <svg class="glyph stroked app window with content">
                                    <use xlink:href="#stroked-app-window-with-content"/>
                                </svg>
                            </div>
                            <p>Amount</p>
                        </div>
                    </div>

                    <fieldset>
                        <h4>Sender details</h4>
                        <div class="form-group">
                            <label class="sr-only">Sending Account type</label>

                            <select name="sendingAccountType" id="f1-sending-account-type" class="select2 form-control">
                                <option value="default">Account Type</option>
                                <option value="CURRENT">Current</option>
                                <option value="SAVING">Saving</option>
                            </select>

                            <!-- input type="text" name="f1-first-name" placeholder="First name..." class="f1-first-name form-control" id="f1-first-name"-->
                        </div>
                        <div class="form-group">
                            <label class="sr-only">Sender</label>
                            <select name="sender" id="f1-sending-account-type" class="select2 form-control">
                                <option value="default">Sender</option>
                            </select>
                            <a href="#">Add Sender</a>
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="f1-about-yourself">Sending Account Number</label>
                            <input type="text" name="sendingAccountNumber" placeholder="Account Number."
                                   class="form-control" id="sendingAccountNumber">
                        </div>
                        <div class="f1-buttons">
                            <button type="button" class="btn btn-next">Next</button>
                        </div>
                    </fieldset>

                    <fieldset>
                        <h4>Receiver Details</h4>
                        <div class="form-group">
                            <label class="sr-only" for="f1-email">Receiving Bank</label>
                            <select name="receivingBank" id="receivingBank" class="select2 form-control">
                                <option value="default">Bank</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="f1-email">Receiving Branch</label>
                            <select name="receivingBranch" id="receivingBranch" class="select2 form-control">
                                <option value="default">Branch</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label class="sr-only">Receiving Account type</label>

                            <select name="receivingAccountType" id="f1-sending-account-type"
                                    class="select2 form-control">
                                <option value="default">Account Type</option>
                                <option value="CURRENT">Current</option>
                                <option value="SAVING">Saving</option>
                            </select>

                            <!-- input type="text" name="f1-first-name" placeholder="First name..." class="f1-first-name form-control" id="f1-first-name"-->
                        </div>
                        <div class="form-group">
                            <label class="sr-only">Receiver</label>
                            <select name="receiver" id="receiver" class="select2 form-control">
                                <option value="default">Receiver</option>
                            </select>
                            <a href="#">Add Receiver</a>
                        </div>
                        <div class="f1-buttons">
                            <button type="button" class="btn btn-previous">Previous</button>
                            <button type="button" class="btn btn-next">Next</button>
                        </div>
                    </fieldset>

                    <fieldset>
                        <h4>Amount</h4>
                        <div class="form-group">
                            <label class="sr-only" for="f1-facebook">Sending Amount</label>
                            <input type="text" name="sendingAmount" placeholder="Sending Amount" class="form-control"
                                   id="sendingAmount">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="f1-twitter">Receiving Amount</label>
                            <input type="text" name="receivingAmount" placeholder="Receiving Amount"
                                   class="form-control" id="receivingAmount">
                        </div>
                        <!--  <div class="form-group">
                             <label class="sr-only" for="f1-google-plus">Google plus</label>
                             <input type="text" name="f1-google-plus" placeholder="Google plus..." class="f1-google-plus form-control" id="f1-google-plus">
                         </div-->
                        <div class="f1-buttons">
                            <button type="button" class="btn btn-previous">Previous</button>
                            <button type="submit" class="btn btn-submit">Create Transaction</button>
                        </div>
                    </fieldset>

                </form>
            </div>
        </div>

    </div>
</div>


<!-- Javascript -->
<script src="${pageContext.request.contextPath}/resources/multistepform/assets/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/multistepform/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/multistepform/assets/js/jquery.backstretch.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/multistepform/assets/js/retina-1.1.0.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/multistepform/assets/js/scripts.js"></script>

<!--[if lt IE 10]>
<script src="assets/js/placeholder.js"></script>
<![endif]-->

</body>

</html>