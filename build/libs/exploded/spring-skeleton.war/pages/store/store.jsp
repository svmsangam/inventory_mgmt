<!-- Angular.JS -->
<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>

<!-- JQuery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/storeInfo.js"></script>

<body ng-app="storeapp" ng-controller="storeCtrl">

<!-- STOREtore Form -->
<div class="col-md-12" ng-if="showAddFormFlag">
    <div class="row col-md-4">
        <div class="break"></div>
        <form method="POST" ng-submit="addStore()"
              class="col-md-12">

            Store Name: <input type="text" ng-model="storeInfo.storeName"> <br/> <br/>
            Owner Name: <input type="text" ng-model="storeInfo.proprieterName"> <br/>
            <br/> Location: <input type="text" ng-model="storeInfo.location"> <br/>
            <br/> PanNumber: <input type="text" ng-model="storeInfo.panNumber"> <br/>
            <br/> Registration Number: <input type="text"
                                              ng-model="storeInfo.registrationNumber"> <br/> <br/> Mobile Number: <input
                type="text" ng-model="storeInfo.mobileNumber"> <br/> <br/> Landline:<input
                type="text" ng-model="storeInfo.landLine"> <br/> <br/> <br/> <br/>

            <fieldset>


                <div class="form-group">
                    <!-- <label for="signup_username" class="col-md-3 control-label">Username</label> -->
                    <div class="col-md-12">
                        <!-- <div class="input-group"> -->
                        <p>Drag the Marker to select your location</p>
                        <div id="google_map" style="height: 400px; width: 800px"></div>

                    </div>
                </div>

                <p>The latitude and longitude field below will be filled
                    according to the marker above in the map</p>
                <div class="form-group">
                    <label for="latitude" class="col-md-3 control-label">Latitude</label>
                    <div class="col-md-9">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-key"></i></span>

                            <input ng-model="storeInfo.latitude" id="listing_doctor_lat" type="text"
                                   class="form-control"
                                   value="latitude" class="testlat"
                                   placeholder="">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="longitude" class="col-md-3 control-label">Longitude</label>
                    <div class="col-md-9">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                            <input ng-model="storeInfo.longitude" id="listing_doctor_lng" type="text"
                                   class="form-control"
                                   value="longitude" class="testlong"
                                   placeholder="">
                        </div>
                    </div>
                </div>

            </fieldset>

            <script type="text/javascript">
                $(document).on("focusin", "#listing_doctor_lat",
                    function (event) {
                        $(this).prop('readonly', true);
                    });

                $(document).on("focusout", "#listing_doctor_lat",
                    function (event) {
                        $(this).prop('readonly', false);
                    });

                $(document).on("focusin", "#listing_doctor_lng",
                    function (event) {
                        $(this).prop('readonly', true);
                    });

                $(document).on("focusout", "#listing_doctor_lng",
                    function (event) {
                        $(this).prop('readonly', false);
                    });
            </script>

            <script async defer
                    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDKERYllGf5BfVR_c_45nqHVkbHoPgXPDA&signed_in=true&callback=initMap"></script>


            <script>
                function initMap() {

                    var myLatLng = {
                        lat: 27.6904693,
                        lng: 85.3332435
                    };

                    var map = new google.maps.Map(document
                        .getElementById('google_map'), {
                        zoom: 17,
                        center: myLatLng
                    });

                    var marker = new google.maps.Marker({
                        map: map,
                        draggable: true, //set marker draggable
                        animation: google.maps.Animation.DROP, //bounce animation
                        title: "Marker",
                        //                     icon: "resources/images/pin_green.png" //custom pin icon
                    });

                    if (navigator.geolocation) {
                        navigator.geolocation
                            .getCurrentPosition(
                                function (position) {
                                    var pos = {
                                        lat: position.coords.latitude,
                                        lng: position.coords.longitude
                                    };

                                    marker.setPosition(pos);
                                    map.setCenter(pos);

                                    document
                                        .getElementById("listing_doctor_lat").value = position.coords.latitude;
                                    document
                                        .getElementById("listing_doctor_lng").value = position.coords.longitude;

                                    google.maps.event
                                        .addListener(
                                            marker,
                                            'dragend',
                                            function (event) {
                                                document
                                                    .getElementById("listing_doctor_lat").value = event.latLng
                                                    .lat();
                                                document
                                                    .getElementById("listing_doctor_lng").value = event.latLng
                                                    .lng();
                                            });

                                    google.maps.event.addListener(
                                        marker, 'dragstart',
                                        function (event) {
                                        });

                                },
                                function () {
                                    handleLocationError(true,
                                        marker, map.getCenter());
                                });
                    } else {
                        // Browser doesn't support Geolocation
                        handleLocationError(false, marker, map.getCenter());
                    }

                }

                function handleLocationError(browserHasGeolocation, marker,
                                             pos) {
                    marker.setPosition(pos);
                    marker
                        .setContent(browserHasGeolocation ? 'Error: The Geolocation service failed.'
                            : 'Error: Your browser doesn\'t support geolocation.');
                }
            </script>

            <input type="hidden" ng-model="storeInfo._csrf" class="testcrf" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <input type="submit" value="Add Store" class="btn btn-sm btn-success">
        </form>
    </div>
</div>


<!-- List of Stores -->
<div ng-if="showListFlag">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Store ID</th>
            <th>Store Name</th>
            <th>Owner Name</th>
            <th>Address</th>
            <th>Mobile Number
            <th>Pan Number</th>
            <th>Registration Number</th>
            <th>LandLine Number</th>
            <th>Latitude</th>
            <th>Longitude</th>


        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="stores in storeList">
            <td>{{stores.storeId}}</td>
            <td>{{stores.storeName}}</td>
            <td>{{stores.proprieterName}}</td>
            <td>{{stores.location}}</td>
            <td>{{stores.mobileNumber}}</td>
            <td>{{stores.panNumber}}</td>
            <td>{{stores.registrationNumber}}</td>
            <td>{{stores.landLine}}</td>
            <td>{{stores.latitude}}</td>
            <td>{{stores.longitude}}</td>
            <td>
                <button ng-click="showEditForm(stores.storeId)" class="btn btn-primary">Edit</button>
            </td>
            <td>
                <button ng-click="deleteStore(stores.storeId)" class="btn btn-primary">Delete</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<!-- ESTOREtore Form -->
<div class="col-md-12" ng-if="showEditFormFlag">
    <div class="row col-md-4">
        <div class="break"></div>
        <form method="POST" class="col-md-12" ng-submit="editStore()">
            <input type="hidden" ng-model="storeInfo.storeId"/>
            Store Name: <input type="text" ng-model="storeInfo.storeName"> <br/> <br/>
            Owner Name: <input type="text" ng-model="storeInfo.proprieterName"> <br/>
            <br/> Location: <input type="text" ng-model="storeInfo.location"> <br/>
            <br/> PanNumber: <input type="text" ng-model="storeInfo.panNumber"> <br/>
            <br/> Registration Number: <input type="text" ng-model="storeInfo.registrationNumber"> <br/>
            <br/> Mobile Number: <input type="text" ng-model="storeInfo.mobileNumber"> <br/>
            <br/> Landline:<input type="text" ng-model="storeInfo.landLine"> <br/> <br/>
            <br/> <br/> <input type="hidden" ng-model="storeInfo._csrf" class="testcrf" name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
            <br/> <br/>
            <input type="submit" value="Update" class="btn btn-sm btn-success">


        </form>
    </div>
</div>

</body>