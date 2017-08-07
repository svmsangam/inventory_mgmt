<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="../common/navbar.jsp" %>
<%@include file="../common/sidebar.jsp" %>
<%@include file="../common/header.jsp" %>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<style>
    #map {
        height: 350px;
        width: 680px;

    }

    #input {
        border-radius: 10px;
        -webkit-border-radius: 10px;
        -moz-border-radius: 10px;
    }

    .input {
        float: right;
        margin-left: 0px;
        margin-right: 900px;
        margin-top: 0px;

    }

    .lable {
        float: left;
    }

    #inputDiv {
        margin-top: 10px;

    }
</style>


<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Icons</li>
        </ol>
    </div><!--/.row-->
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Edit Business</h1>
        </div>
    </div><!--/.row-->
    <div class="row">
        <div class="col-md-12">
            <div class="break"></div>
            <form action="${pageContext.request.contextPath}/editbusiness" method="POST" modelAttribute="storeDto"
                  class="col-md-12">
                <input type="hidden" name="storeId" value="${storeInfo.storeId}">
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
                    <input type="text" id="input" class="form-control input-md" name="registrationNumber"
                           value="${storeInfo.registrationNumber}" required/> <br/>
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

        </div>

        <div id="map"></div>
        <br/>
        <div>
            <input name="location" id="address" class="form-control" type="text" style="width: 600px;"/> <br/>
            <p class="error">${error.location}</p>
            <input type="text" class="form-control" id="latitude" name="latitude" placeholder="Latitude"/>
            <input type="text" class="form-control" id="longitude" name="longitude" placeholder="Longitude"/>

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
        <input type="submit" value="Update Business Details" class="btn btn-sm btn-success">
        </form>

        <%-- <form action="${pageContext.request.contextPath}/editStoreLicense" method="POST" enctype="multipart/form-data" modelAttribute="storeLicense">

            <img src="file://E:/storelicense/${storeInfo.storeId}.png" alt="/E:/storelicense/${storeInfo.storeId}.png" />
            <img src="/images/storelicense/${storeInfo.storeId}.png" alt="" />
            <input type="hidden" name="storeId" value="${storeInfo.storeId}">
            <div class="form-group">
                <label>Change STORE License Image:</label> <input name="file"
                    style="" class="form-control refFile input-sm" type="file" />
                <p class="error"></p>
            </div>

            <input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" />

            <input type="submit" value="Update STORE License"
                class="btn btn-sm btn-success">

        </form> --%>
    </div>
</div>

