<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:GlobalTemplate>
	<jsp:body>
    
    	<div class="container-main pad-20">
	        <div class="app">
				<script src="http://feedstack.asia/app/script/members.js"></script>
				<div class="member-body">
					<div class="header-member">
						<i class="fa fa-space fa-group"></i> Friends Module Index
					</div>
					<p><a href="${pageContext.request.contextPath}/friends">Display Friends</a></p>
					<p><a href="#friends/add">Add Friend</a></p>
					<p><a href="#friendsinvite">Invite Friend and send mail</a></p>
					<p><a href="#friends/online">Display Online friends</a></p>
					<p><a href="${pageContext.request.contextPath}/search/users">Search Users</a></p>
					<p><a href="#friends/suggest">Suggest Friends</a></p>
					<p>Printing User from session: ${sessionUser.firstName} </p>
					<div class="root" root="http://feedstack.asia/" access-token=""></div>	
					<div class="entry">
						<div class="member-container">
							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
      </div>
    </jsp:body>
</t:GlobalTemplate>
