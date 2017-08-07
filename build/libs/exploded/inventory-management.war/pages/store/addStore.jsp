<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">

<!-- Javascript -->
<script src="${pageContext.request.contextPath}/resources/multistepform/assets/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/multistepform/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/multistepform/assets/js/jquery.backstretch.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/multistepform/assets/js/retina-1.1.0.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/multistepform/assets/js/scripts.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/form-elements.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/style.css">

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
<%@include file="../common/navbar.jsp" %>
<%@include file="../common/sidebar.jsp" %>

<style>
    #map {
        height: 350px;
        width: 680px;
    }
</style>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Home</li>
            <li>

            </li>
        </ol>
    </div><!--/.row-->
    <div class="row">
        <div class="col-sm-9">
            <h1 class="page-header">Add Business</h1>
        </div>
    </div><!--/.row-->

    <!--  	<div class="top-content">
                <div class="container select2-container">
                     -->
    <!-- <div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3 form-box"> -->
    <div class="container-wrapper">
        <form action="${pageContext.request.contextPath}/addbusiness" method="POST" modelAttribute="storeDto"
              enctype="multipart/form-data">

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
                    <p>Service Plan</p>
                </div>
            </div>

            <div class="f1-steps">
                <div class="col-lg-12">
                    <div class="col-lg-6">
                        <div class="form-group" id="inputDiv">
                            <label class="lable">Business Name </label>
                            <input type="text" id="input" class="form-control" name="storeName"
                                   value="${storeInfo.storeName}" required/>
                            <p class="error">${error.storeName}</p>
                        </div>
                    </div>
                </div>

                <div class="col-lg-12">
                    <div class="col-lg-4">
                        <div class="form-group" id="inputDiv">
                            <label class="lable">PAN Number </label>
                            <input type="text" id="input" class="form-control" name="panNumber"
                                   value="${storeInfo.panNumber}" required/>
                            <p class="error">${error.panNo}</p>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="form-group" id="inputDiv">
                            <label class="lable">Registration Number </label>
                            <input type="text" id="input" class="form-control" name="registrationNumber"
                                   value="${storeInfo.registrationNumber}" required/>
                            <p class="error">${error.regNo}</p>
                        </div>
                    </div>
                </div>

                <div class="col-lg-12">
                    <div class="col-lg-4">
                        <div class="form-group" id="inputDiv">
                            <label class="lable">Mobile Number </label>
                            <input type="text" id="input" class="form-control" name="mobileNumber"
                                   value="${storeInfo.mobileNumber}" required/>
                            <p class="error">${error.mobile}</p>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="form-group" id="inputDiv">
                            <label class="lable">Landline Number</label>
                            <input type="text" id="input" class="form-control" name="landLine"
                                   value="${storeInfo.landLine}" required/>
                            <p class="error">${error.landLineNo}</p>
                        </div>
                    </div>
                </div>

                <div class="col-lg-12">
                    <div class="col-lg-4">
                        <div class="form-group" id="inputDiv">
                            <label>Attach Business License Image</label>
                            <input name="file" id="input" class="form-control" type="file"/>
                            <p class="error"></p>
                        </div>
                    </div>
                </div>
                <br/>
                <div class="col-lg-12">
                    <div id="map"></div>
                    <br/>
                    <div class="col-lg-8">
                        <label>Your Address</label>
                        <input name="location" id="address" class="form-group" type="text" value="0"
                               style="width: 600px;"/>
                    </div>
                    <p class="error">${error.location}</p>
                </div>
                <div class="col-lg-12">
                    <div class="col-lg-4">
                        <label>Latitude</label>
                        <input type="text" class="form-group" id="latitude" name="latitude" value="0"
                               placeholder="Latitude"/>
                    </div>
                    <div class="col-lg-4">
                        <label>Longitude</label>
                        <input type="text" class="form-group" id="longitude" name="longitude" value="0"
                               placeholder="Longitude"/>
                    </div>
                </div>
            </div>

            <script type="text/javascript">
                $(document).on("focusin", "#latitude",
                    function (event) {
                        $(this).prop('readonly', true);
                    });

                $(document).on("focusout", "#latitude",
                    function (event) {
                        $(this).prop('readonly', false);
                    });

                $(document).on("focusin", "#longitude",
                    function (event) {
                        $(this).prop('readonly', true);
                    });

                $(document).on("focusout", "#longitude",
                    function (event) {
                        $(this).prop('readonly', false);
                    });
            </script>
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
            <div class="f1-buttons">
                <button type="submit" class="btn btn-next">Next</button>
            </div>

        </form>
    </div>
</div>

<%@include file="../common/footer.jsp" %>