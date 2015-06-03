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
      <div class="app bcol-70">
          <div class="wall">

            <div class="profile">
              <div class="profile-mobile-bg">


                <img src="http://feedstack.asia/img/user.jpg"
								class="profile-image-mobile" />
                </div>
              <div class="bcol-30">
              <div class="feed-user mobile-hidden mobile-hidden-main-image">
              	<img id="profileimage" userId="${user.getUserId()}" src="${pageContext.request.contextPath}/displayimage?id=${user.getUserId()}" class="profile-image">
			</div>
              </div>
              <div class="bcol-70">
                <div class="profile-container">
                  <div class="profile-name">
                    <div class="profile-name-span">${user.firstName} ${user.lastName}</div>
                  </div>
                  <div class="profile-desc">
					<div>Brith Date: ${user.getDateOfBirth()} </div>
					<div>Contact: ${user.getContactNumber()} </div>
					<div>Email: ${user.getEmail()} </div>
                  </div>
                  <div class="profile-buttons" uid="836"
									liveuser-id="836">
                      <c:choose>
                        <c:when test="${empty friendship}">
                          <button id="addFriendBtn" class="btn btn-edit" userId="${user.userId}" >
                            Add Friend
                          </button>
                        </c:when>
                        <c:otherwise>
                          	
                            ${friendship.status}
                            
<%--                           <c:choose>
                          <c:when test"${(friendship.getStatus() = '1')}">
                          <button class="acceptFriendBtn btn btn-edit" userID="${sessionUser.userId}" acceptorID="${friendship.friendshipId}"> Accept Friendship </button>
                          </c:when>
                          <c:otherwise> --%>
                          <button id="unFriendBtn" class="btn btn-edit" friendshipId="${friendship.friendshipId}"> Unfriend </button>
<%--                           </c:otherwise>
                          </c:choose> --%>
                        </c:otherwise>
                      </c:choose>

                  </div>
                </div>
              </div>
              <div class="clear"></div>
            </div>
          </div>
          
          <div class="profile-container-box">
            <script src="http://feedstack.asia/app/script/readmore.js"></script>
            <script src="http://feedstack.asia/app/script/popup.js"></script>
            <script src="http://feedstack.asia/app/script/wall.js"></script>
<style>
.footer {
	background: white;
}

.profile-container-box {
	min-height: 210px;
}
</style>

		<div class="container">
			<div class = "container" id="displaypersonalinfo" style="display:block;">
				<div class= "feed-block">
					<div class = "feed-title">About Me</div>
					<div class = "feed-title">${personal.getAboutme()}</div>
				</div>
				<div class= "feed-block">
					<div class = "feed-title">Lives In</div>
					<div class = "feed-title">${personal.getCity()}</div>
				</div>
	        </div>
		</div>

		<div class="container" id="displayeducationinfo" style="display:block;">
			<div class= "feed-block" style="display:block;">
				<div class = "feed-title">Education</div>
				<div class = "feed-title">${education.getSchool()} ${education.getArea()}</div>
			</div>
		</div>	


</div>
      </div>
      <div class="sidebar bcol-30">

        <div class="notify-block">
          <div>
            <div class="notify-title">
                <a href="${pageContext.request.contextPath}/friends?userId=${user.getUserId()}"> ${user.firstName}'s Friends </a> <span class="unread"></span>
            </div>
          </div>
        </div>
      </div>
      
       <div class="clear"></div>
	</div>
  </jsp:body>
</t:GlobalTemplate>
