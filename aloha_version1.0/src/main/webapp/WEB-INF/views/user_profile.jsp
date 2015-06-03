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
		var p = ${user.getPrivacy()}
		console.log(p);
		if(p==1)
			buttonval = "make public";
		else
			buttonval = "make private";
		$('.privacy-btn').html(buttonval);
	});	
function display_pform(){
	$('#personalform').css('display','block');
	$('#displaypersonalinfo').css('display','none');
}
function display_eform(){
	$('#educationform').css('display','block');
	$('#displayeducationinfo').css('display','none');
}
function savePersonalInfo(){
	$('#personalform').css('display','none');
	$('#displaypersonalinfo').css('display','block');
}
function saveEducationInfo(){
	$('#educationform').css('display','none');
	$('#displaypersonalinfo').css('display','block');
}
function setPrivacy(){
	var url = '${pageContext.request.contextPath}';
	var p = ${user.getPrivacy()}
	var buttonval = "";
	if(p==1)
		buttonval = "make public";
	else
		buttonval = "make private";
	$.ajax({
		headers : {
			'Accept' : 'application/json'
		},
		method : "POST",
		url : url+"/privacysetting",
		data : {
			
		},
		success : function(data) {
			$('.privacy-btn').html(buttonval);
		},
		error : function(data) {
			// alert(data);
			console.log(data);
		}
	})

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
                    <button class="btn btn-edit privacy-btn" onClick="setPrivacy()">Make Account Details Private</button>
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
				<form id="savepersonalinfo" action="${pageContext.request.contextPath}/personalinfo" method="post">				
					<div class = "feed-title">About Me</div>
					<textarea name="aboutme" class="feed-box" form="savepersonalinfo" placeholder="${personal.getAboutme() }" value="${personal.getAboutme() }" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 60px;"></textarea>
					
					<div class = "feed-title">Lives In</div>
					<input type="text" name="livesin" value="${personal.getCity() }" width="100px" size = "25" pattern="[a-zA-Z ]{1,25}" title = "please enter only characters" required/>
					<div class="profile-buttons" uid="858" liveuser-id="858">
							<button class="btn btn-edit" type="submit" onSubmit="savePersonalInfo()">Save</button>
					</div>
				</form>
			</div>
			
		</div>
		<div class="container" id="displayeducationinfo" style="display:block;">
			<div class= "feed-block" style="display:block;">
				<div class = "feed-title">Education</div>
				<div class = "feed-title">${education.getSchool()} ${education.getArea()}</div>
			</div>
			<div class="profile-buttons" uid="858" liveuser-id="858">
                     <button class="btn btn-edit" onClick="display_eform()">Edit Education Details</button>
            </div>
		</div>	
		<div class= "feed-block" id="educationform" style="display:none;">
				<form id="saveeducationinfo" action="${pageContext.request.contextPath}/educationinfo" method="post">				
					<div class = "feed-title">School</div>
					<input type="text" name="school" value="${education.getSchool()}" width="100px" size = "25" pattern="[a-zA-Z ]{1,25}" title = "please enter only characters (length less than 25)" required/>
					 
					<div class = "feed-title">Area</div>
					<input type="text" name="area" width="100px" value="${education.getArea()}" size = "25" pattern="[a-zA-Z ]{1,25}" title = "please enter only characters (length less than 25)" required/>
					<div class="profile-buttons" uid="858" liveuser-id="858">
							<button class="btn btn-edit" type="submit" onSubmit="saveEducationInfo()">Save</button>
					</div>
				</form>
		</div>
        <div class="clear"></div>

        </div>

      </div>
      <div class="sidebar bcol-30">

        <div class="notify-block">
          <div>
            <div class="notify-title">
                Pending Friends Requests <span class="unread"> ${pendingFriends.size()}</span>
              <c:forEach items="${pendingFriends}" var="friend">
                <div class="bcol-member-block">
                  <div class="member-name" id="pendingReqBox_${friend.user2.userId}">
                    <a href="${pageContext.request.contextPath}/profile?userId=${friend.user2.userId}">${friend.user2.firstName}</a>
                          <button id="acceptFriendBtn_${friend.user2.userId}"
											class="acceptFriendBtn btn btn-edit" userID="${friend.user2.userId}" acceptorID="${friend.user1.userId}" >
                            Accept
                          </button>
                          <button id="ignoreFriendBtn_${friend.user2.userId}"
											class="ignoreFriendBtn btn btn-edit" userID="${friend.user2.userId}" acceptorID="${friend.user1.userId}" >
                            Ignore
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
  </jsp:body>
</t:GlobalTemplate>
