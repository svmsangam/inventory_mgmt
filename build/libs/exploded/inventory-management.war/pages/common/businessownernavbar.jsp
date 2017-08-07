<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/resources/dashboard/js/angular.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/notification.js"></script>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
 
		<div class="container-fluid" ng-app="notifyapp" ng-controller="notifyCtrl">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/"><span>TMK INVENTORY</span><small> Business Owner </small></a>
				<ul class="user-menu">
					<li class="dropdown pull-right">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user-circle fa-lg" aria-hidden="true"></i>&nbsp;  ${pageContext.request.userPrincipal.name} <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"><i class="fa fa-vcard" aria-hidden="true"></i>&nbsp; Profile</a></li>
							<li><a href="#"><i class="fa fa-cogs" aria-hidden="true"></i>&nbsp; Settings</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="${pageContext.request.contextPath}/home/logout"><i class="fa fa-sign-out" aria-hidden="true"></i>&nbsp; Logout</a></li>
						</ul>
					</li>
					
					<li class="dropdown pull-right">
					  <a id="dLabel" role="button" data-toggle="dropdown" data-target="#" href="">
					    <i class="glyphicon glyphicon-bell"><span class="badge" style="background-color: red;">{{notifyList.length}}</span></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  </a>
					  <ul class="dropdown-menu notifications" role="menu" aria-labelledby="dLabel">
					    <div class="notification-heading"><h4 class="menu-title">Notifications</h4></div>
					    <li class="divider"></li>
					   <div class="notifications-wrapper">
					      <div ng-repeat="notification in notifyList">
						     <a class="content" href="/oldItem">
						       <div class="notification-oldItem">
						        <h2 class="oldItem-title">&nbsp Low Stock Warning! oldItem : {{notification.itemName}}</h2>
						       </div>
						     </a>
					     </div>
					   </div>
					    <li class="divider"></li>
					    <div class="notification-footer pull-right"><h4 class="menu-title">See all</h4></div>
					  </ul>
					</li>
					
				</ul>
			</div>
							
		</div><!-- /.container-fluid -->
	</nav>