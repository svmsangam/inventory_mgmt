<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<body>
<%@include file="../common/navbar.jsp" %>
<%@include file="../common/sidebar.jsp" %>
<%@include file="../common/header.jsp" %>
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
                    <div class="row">
                        <div class="col-md-12">
                            <div class="break"></div>

                            <form action="${pageContext.request.contextPath}/addBusiness" method="POST"
                                  modelAttribute="storeDto" enctype="multipart/form-data" class="col-md-12">

                                <h3>Create Business</h3>
                                <p>Fill in the form to add business</p>
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

                                        <p>Business Details</p>
                                    </div>
                                    <div class="f1-step">
                                        <div class="f1-step-icon">
                                            <svg class="glyph stroked download">
                                                <use xlink:href="#stroked-download"/>
                                            </svg>
                                        </div>
                                        <p>Owner Details</p>
                                    </div>
                                    <div class="f1-step">
                                        <div class="f1-step-icon">
                                            <svg class="glyph stroked app window with content">
                                                <use xlink:href="#stroked-app-window-with-content"/>
                                            </svg>
                                        </div>
                                        <p>User Details</p>
                                    </div>
                                </div>

                                <fieldset>
                                    <h4>Business Details</h4>
                                    <!-- <div class="form-group">
                                        <label class="sr-only" >Sending Account type</label>

                                        <select name="sendingAccountType" id="f1-sending-account-type" class="select2 form-control">
                                            <option value="default">Account Type</option>
                                            <option value="CURRENT">Current</option>
                                            <option value="SAVING">Saving</option>
                                        </select>

                                        input type="text" name="f1-first-name" placeholder="First name..." class="f1-first-name form-control" id="f1-first-name"
                                    </div>
                                    <div class="form-group">
                                        <label class="sr-only" >Sender</label>
                                        <select name="sender" id="f1-sending-account-type" class="select2 form-control">
                                            <option value="default">Sender</option>
                                        </select>
                                        <a href="#">Add Sender</a>
                                    </div>
                                    <div class="form-group">
                                        <label class="sr-only" for="f1-about-yourself">Sending Account Number</label>
                                       <input type="text" name="sendingAccountNumber" placeholder="Account Number." class="form-control" id="sendingAccountNumber">
                                    </div> -->


                                    <div class="input-group" id="inputDiv">
                                        <label class="lable">Business Name </label>
                                        <input type="text" id="input" class="form-control input-md" name="storeName"
                                               value="${storeInfo.storeName}" required/> <br/>
                                        <p class="error">${error.storeName}</p>
                                        <br/>
                                    </div>

                                    <div class="input-group" id="inputDiv">
                                        <label class="lable">PanNumber </label>
                                        <input type="text" id="input" class="form-control input-md" name="panNumber"
                                               value="${storeInfo.panNumber}" required/> <br/>
                                        <p class="error">${error.panNo}</p>
                                        <br/>
                                    </div>

                                    <div class="input-group" id="inputDiv">
                                        <label class="lable">Registration Number </label>
                                        <input type="text" id="input" class="form-control input-md"
                                               name="registrationNumber" value="${storeInfo.registrationNumber}"
                                               required/> <br/>
                                        <p class="error">${error.regNo}</p>
                                        <br/>
                                    </div>

                                    <div class="input-group" id="inputDiv">
                                        <label class="lable">Mobile Number </label>
                                        <input type="text" id="input" class="form-control input-md" name="mobileNumber"
                                               value="${storeInfo.mobileNumber}" required/> <br/>
                                        <p class="error">${error.mobile}</p>
                                        <br/>
                                    </div>

                                    <div class="input-group" id="inputDiv">
                                        <label class="lable">Landline </label>
                                        <input type="text" id="input" class="form-control input-md" name="landLine"
                                               value="${storeInfo.landLine}" required/> <br/>
                                        <p class="error">${error.landLineNo}</p>
                                        <br/>
                                    </div>

                                    <div class="form-group" id="inputDiv">
                                        <label class="lable">Attach Store License Image </label>
                                        <input name="file" id="input" class="form-control input-md" type="file"/>
                                        <p class="error"></p>
                                    </div>

                                    <!-- 		remaining map  -->
                                    <div id="map"></div>
                                    <br/>
                                    <div>
                                        <input name="location" id="address" class="form-control" type="text" value="0"
                                               style="width: 600px;"/> <br/>
                                        <p class="error">${error.location}</p>
                                        <input type="text" class="form-control" id="latitude" name="latitude" value="0"
                                               placeholder="Latitude"/>
                                        <input type="text" class="form-control" id="longitude" name="longitude"
                                               value="0" placeholder="Longitude"/>

                                    </div>

                                    <!-- <script type="text/javascript">
                                        $(document).on("focusin", "#latitude",
                                                function(event) {
                                                    $(this).prop('readonly', true);
                                                });

                                        $(document).on("focusout", "#latitude",
                                                function(event) {
                                                    $(this).prop('readonly', false);
                                                });

                                        $(document).on("focusin", "#longitude",
                                                function(event) {
                                                    $(this).prop('readonly', true);
                                                });

                                        $(document).on("focusout", "#longitude",
                                                function(event) {
                                                    $(this).prop('readonly', false);
                                                });
                                    </script> -->
                                    <script async defer
                                            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDKERYllGf5BfVR_c_45nqHVkbHoPgXPDA&callback=initMap"></script>
                                    <script>


                                        function initMap() {

                                            var myLatLng = {lat: 27.690515924781877, lng: 85.33531399999993};

                                            var map = new google.maps.Map(document.getElementById('map'), {
                                                zoom: 17,
                                                center: myLatLng
                                            });

                                            var geocoder = new google.maps.Geocoder;
                                            var infowindow = new google.maps.InfoWindow;


                                            var marker = new google.maps.Marker({
                                                map: map,
                                                draggable: true, //set marker draggable
                                                animation: google.maps.Animation.DROP, //bounce animation
//                     icon: "resources/images/pin_green.png" //custom pin icon
                                            });


                                            console.log("dhiraj ");
                                            if (navigator.geolocation) {
                                                console.log("dhiraj helo ");
                                                navigator.geolocation.getCurrentPosition(function (position) {
                                                    var pos = {
                                                        lat: position.coords.latitude,
                                                        lng: position.coords.longitude
                                                    };
                                                    console.log("dhiraj " + position.coords.latitude);
                                                    marker.setPosition(pos);
                                                    map.setCenter(pos);

                                                    geocoder.geocode({'latLng': pos}, function (results, status) {
                                                        if (status === 'OK') {
                                                            if (results[0]) {
                                                                console.log("dhiraj and sanjay");
                                                                $('#address').val(results[0].formatted_address);
                                                                $('#latitude').val(marker.getPosition().lat());
                                                                $('#longitude').val(marker.getPosition().lng());
                                                                infowindow.setContent(results[0].formatted_address);
                                                                infowindow.open(map, marker);
                                                            }
                                                        }
                                                    });

                                                    google.maps.event.addListener(marker, 'dragend', function () {
                                                        geocoder.geocode({'latLng': marker.getPosition()}, function (results, status) {
                                                            if (status == google.maps.GeocoderStatus.OK) {
                                                                if (results[0]) {
                                                                    $('#address').val(results[0].formatted_address);
                                                                    $('#latitude').val(marker.getPosition().lat());
                                                                    $('#longitude').val(marker.getPosition().lng());
                                                                    infowindow.setContent(results[0].formatted_address);
                                                                    infowindow.open(map, marker);
                                                                }
                                                            }
                                                        });
                                                    });

                                                }, function () {
                                                    handleLocationError(true, marker, map.getCenter());
                                                });
                                            } else {
                                                handleLocationError(false, marker, map.getCenter());
                                            }

                                        }

                                        function handleLocationError(browserHasGeolocation, marker, pos) {
                                            marker.setPosition(pos);
                                            marker.setContent(browserHasGeolocation ?
                                                'Error: The Geolocation service failed.' :
                                                'Error: Your browser doesn\'t support geolocation.');
                                        }
                                    </script>

                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="submit" value="Add Business Details" class="btn btn-sm btn-success">
                                    <button type="submit" class="btn btn-next">SaveP</button>
                            </form>
                        </div>
                    </div>

                    <div class="f1-buttons">
                        <button type="button" class="btn btn-next">Next</button>
                        <button type="submit" class="btn btn-next">Save</button>


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