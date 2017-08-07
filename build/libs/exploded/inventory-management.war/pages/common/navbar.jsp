<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/"><span>TMK INVENTORY</span><small> Admin </small></a>
				<ul class="user-menu">
				
					<li class="dropdown pull-right">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user-circle fa-lg" aria-hidden="true"></i>&nbsp; ${pageContext.request.userPrincipal.name} <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"><i class="fa fa-vcard" aria-hidden="true"></i>&nbsp; Profile</a></li>
							<li><a href="#"><i class="fa fa-cogs" aria-hidden="true"></i>&nbsp; Settings</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="${pageContext.request.contextPath}/home/logout"><i class="fa fa-sign-out" aria-hidden="true"></i>&nbsp; Logout</a></li>
						</ul>
					</li>
					
				</ul>
			</div>
							
		</div><!-- /.container-fluid -->
	</nav>
