<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:GlobalTemplate>
	<jsp:body>
<link href="http://feedstack.asia/lib/expresscss/style.css"
					rel="stylesheet"></link>
<script src="http://feedstack.asia/app/script/profile.js"></script>
<script src="http://feedstack.asia/app/script/readmore.js"></script>
<script src="http://feedstack.asia/app/script/popup.js"></script>
<script src="http://feedstack.asia/app/script/wall.js"></script>
<script src="http://feedstack.asia/app/script/profile.js"></script>
<!-- ToolTip -->
<link href="http://feedstack.asia/app/script/tooltip/css/tooltipster.css" rel="stylesheet" />
<script src="http://feedstack.asia/app/script/tooltip/js/jquery.tooltipster.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/friends.js" type="text/javascript"></script>
<script>	
	$(document).ready(function(){
		FriendJS.init("${pageContext.request.contextPath}");
	
	});	
</script>
<script>
function display_pform(){
	$('#personalform').css('display','block');
	$('#displaypersonalinfo').css('display','none');
}
</script>
<script>
function savepersonalinfo(){
	$('#personalform').css('display','block');	
}
</script>

    <div class="container-main pad-20">

      <div class="app bcol-70">
        <div class="wall">

          <div class="profile">
            <div class="profile-mobile-bg">


              <img src="" class="profile-image-mobile" />
            </div>
            <div class="bcol-30">
            <!-- "feed-user mobile-hidden mobile-hidden-main-image" -->
              <div class="feed-user mobile-hidden mobile-hidden-main-image">
              	<img id="profileimage" userId="${user.getUserId()}" src="${pageContext.request.contextPath}/displayimage?id=${user.getUserId()}" class="profile-image">

              </div>
              <div class="profile-buttons" uid="858" liveuser-id="858">
                  <a href="${pageContext.request.contextPath}/editprofile">
                    <button class="btn btn-edit">Upload Picture</button>
                  </a>
                </div>
            </div>
            <div class="bcol-70">
              <div class="profile-container">
                <div class="profile-name">
                  <div class="profile-name-span">${user.getFirstName()} ${user.getLastName()} </div>
                </div>
				<div class="profile-desc"> 
					<div>Brith Date: ${user.getDateOfBirth()} </div>
					<div>Contact: ${user.getContactNumber()} </div>
					<div>Email: ${user.getEmail()} </div>
				</div>
				<div class="profile-buttons" uid="858" liveuser-id="858">
                  <a href="${pageContext.request.contextPath}/editaccountdetails">
                    <button class="btn btn-edit">Account Settings</button>
                  </a>
                    <button class="btn btn-edit">Make Account Details Private</button>
                </div>
                
              </div>
            </div>
            <div class="clear"></div>
          </div>
        </div>
<div class="profile-container-box">
<style>
.footer{
	background: white;
}
.profile-container-box{
	min-height: 210px;
}
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
				<div class="profile-buttons" uid="858" liveuser-id="858">
	                  
	                    <button class="btn btn-edit" onClick="display_pform()">Edit Personal Information</button>
	                 
	            </div>
	        </div>
			<div class= "feed-block" id="personalform" style="display:none;">
				<form id="savepersonalinfo">
				
				<div class = "feed-title">About Me</div>
				<textarea class="feed-box" placeholder="hey friends I am a new user!" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 60px;"></textarea>
				
				<div class = "feed-title">Lives In</div>
				<input type="text" name="livesin" width="100px" size = "15" pattern="[a-zA-Z]{1,10}" title = "please enter only characters" required/>
				<div class="profile-buttons" uid="858" liveuser-id="858">
					<button class="btn btn-edit" type="submit" onSubmit="savePersonalInfo()">Save</button>
				</div>
				</form>
			</div>
			
		</div>
		<div class="container">
			<div class= "feed-block">
				<div class = "feed-title">Education</div>
				<div class = "feed-title">${education.getSchool() } ${education.getArea()}</div>
			</div>
			<div class="profile-buttons" uid="858" liveuser-id="858">
                  <a href="${pageContext.request.contextPath}/educationinfo">
                    <button class="btn btn-edit">Edit Education Details</button>
                  </a>
            </div>
		</div>	

        <a href = "${pageContext.request.contextPath}/chat"><h6>chat</h6>
        <div class="clear"></div>

        </div>

      </div>
      <div class="sidebar bcol-30">

        <div class="notify-block">
          <div>
            <div class="notify-title">
              <a href="http://feedstack.asia/milindhg/notifications">
                Pending Friends Requests <span class="unread"> 0 unread</span>
              </a>
              <c:forEach items="${pendingFriends}" var="friend">

                <div class="bcol-member-block">
                  <%--                   <div class="member-image">
                    <a href="${pageContext.request.contextPath}/profile?userId=${friend.user1.firstName}">
                      <img src="http://feedstack.asia/img/user.jpg" class="member">		</a>
                  </div> --%>
                  <div class="member-name">
                    <a href="${pageContext.request.contextPath}/profile?userId=${friend.user2.userId}">${friend.user2.firstName}</a>
                          <button id="acceptFriendBtn_${friend.user2.userId}"
											class="acceptFriendBtn btn btn-edit" userID="${friend.user2.userId}" acceptorID="${friend.user1.userId}" >
                            Accept Friend
                          </button>

                  </div>
                </div>
              </c:forEach>
            </div>
          </div>
        </div>
      </div>

      <div class="clear"></div>

    </div>
  </div>
  </jsp:body>
</t:GlobalTemplate>
