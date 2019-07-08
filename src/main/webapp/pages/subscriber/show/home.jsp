<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 2/7/18
  Time: 1:56 PM
  To change this template use File | Settings | File Templates.
--%>

<div class="row">
    <div class="col-md-4">
        <h3 class="profile-username text-center">${subscriber.fullName}</h3>
    </div>
</div>

<div class="row">
    <div class="col-md-6">
        <hr>
            <strong><i class="fa fa-book margin-r-5"></i> Contact</strong>
            <p class="text-muted">${subscriber.mobile}</p>
            <p class="text-muted">${subscriber.contact}</p>
            <p class="text-muted">${subscriber.email}</p>
        <hr>
    </div>

    <div class="col-md-6">
        <hr>
        <strong><i class="fa fa-map-marker margin-r-5"></i> Location</strong>
        <p class="text-muted">${subscriber.cityName}</p>
        <p class="text-muted">${subscriber.street}</p>
        <hr>
    </div>
</div>
