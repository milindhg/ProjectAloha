<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:GlobalTemplate>
    <jsp:body>

<script src="${pageContext.request.contextPath}/resources/js/jquery-jtemplates.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/post.js"></script>

<script >

$( document ).ready(function() {
	PostManager.init("${pageContext.request.contextPath}");
});

</script>



<div class="container-main pad-20">
    <div class="app bcol-70">
        <script src="http://feedstack.asia/app/script/profile.js"></script>
        <!-- ToolTip -->
        <link href="http://feedstack.asia/app/script/tooltip/css/tooltipster.css" rel="stylesheet">
        <script src="http://feedstack.asia/app/script/tooltip/js/jquery.tooltipster.min.js"></script>
        <script>
            $(function () {
                $('.tooltip').tooltipster();
            });
        </script>
        <div class="wall">
		<div class="feed">
		<div class="bcol-15">
			<div class="feed-user mobile-hidden" username="renudeshmukh" name="Renuka Deshmukh" uid="845">
				<a href="http://feedstack.asia/renudeshmukh">
					<!-- <img src="http://feedstack.asia/img/user.jpg" class="feed-user-image"> --> 
					<img class="feed-user-image" src="/common/displayimage?id=${userId}">
				</a>	
			</div>
		</div>
		<div class="bcol-85">
			<div class="feed-container">
				<div class="feed-input">
					<textarea class="feed-box" id="txtPost" placeholder="Scribble..." maxlength="500" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 60px;"></textarea>
					<div>
						&nbsp;<img src="http://feedstack.asia/img/loading.gif" class="loading" style="display: none;">
						<span class="right"><button class="post-btn" onclick="PostManager.addPost()">Scribble</button></span>
					</div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>

        <div class="profile-container-box">
            
            
            <style>
                .footer {
                    background: white;
                }

                .profile-container-box {
                    min-height: 210px;
                }
            </style>

<div id="postContainer">

</div>
 <div id="moreContainer">
	
 <div class="no-more-feeds" style="display:none">
 <div class="bcol-15">&nbsp;</div>
            <div class="bcol-85">
                <div class="no-feeds">No more Feeds</div>
            </div>
            <div class="clear"></div>
            <br>
 </div>
 
  <div class="more-feeds" >
 <div class="bcol-15">&nbsp;</div>
            <div class="bcol-85">
                <div class="no-feeds">Next</div>
            </div>
            <div class="clear"></div>
            <br>
 </div>

	
</div>          

        </div>
    </div>
       <div class="clear"></div>
</div>


    </jsp:body>
</t:GlobalTemplate>
