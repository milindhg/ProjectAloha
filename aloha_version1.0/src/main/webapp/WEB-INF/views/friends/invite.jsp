<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:GlobalTemplate>
	<jsp:body>
	<script
			src="${pageContext.request.contextPath}/resources/js/friends.js"
			type="text/javascript"></script>
	<script>
		$(document).ready(function() {
			FriendJS.init("${pageContext.request.contextPath}");
		});
	</script>
    	<div class="container-main pad-20">
	        <div class="app">
				<script src="http://feedstack.asia/app/script/members.js"></script>
				<div class="member-body">
					<div class="header-member">
						<i class="fa fa-space fa-group"></i> Invite Friends
					</div>
					<div class="entry" align="center">Enter email address of your friends to invite them to Aloha. If you wish to invite more than one friends please enter email addresses separated by comma.
					<br/>
					<input type="text" id="email" name="email"
							placeholder="Enter Email Address..." width="100px"
							pattern="^[A-z0-9._%+-]+@[A-z0-9.-]+\.[A-z]{2,6}$"
							title="please enter a valid email address" required />
	 				<input id="inviteFriendBtn" class="btn btn-primary" type="button"
							value="Invite" name="sign_up" align="middle">
						<br/> <span id="inviteFriendStatus"></span>
					<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
  </jsp:body>
</t:GlobalTemplate>
