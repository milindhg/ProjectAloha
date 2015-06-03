<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:GlobalTemplate>
    <jsp:body>
<script src="http://feedstack.asia/app/script/jquery.autosize.min.js"></script>


<script >
$(document).on('click','.feed-comment-count',function(){
		var feed_id = $(this).closest('.feed').attr('feed-id');
		var container = $('.comment-block-entry-'+feed_id);
		var comment_count =parseInt($('.comment-count-'+feed_id).html());
		var ele = $(this).next('.loading-likers');

		if(container.is(':visible')){
			container.slideUp();
		}else{
			ele.show();
			container.slideDown();
			ele.hide();
		}
	});
	
/*$(function(){
	//$('textarea').autosize();
});*/



function isInvalidEntry(value) {
	
    var pattern = new RegExp(/<[a-zA-Z]*script[\s\S]*?>[\s\S]*?<\/[a-zA-Z]*script>/g);
    return pattern.test(value);
};

function savePost(){
var value = $('#txtPost').val();
	if(!isInvalidEntry(value)){
	   
       var json = { postData : value};
       
       	$.ajax({
    			  headers:{'Accept': 'application/json'},
    			  method: "POST",
    			  url: "http://localhost:8080/common/post/add",
    			  data: { postData: "milind"},
    			  success: function(data) { console.log(data);}
    			});
    		//window.location.href = "${pageContext.request.contextPath}/search/users?searchKey=" + $('#txtPost').val();
    	
         }
	else{
	alert("Scripts not allowed !!");
	}
};



$(document).on('keypress', '.feed-comment-input-entry', function (event) {
    if (!isInvalidEntry()) {
        var feed_id = $(this).closest('.feed').attr('feed-id');
        var lid = $(this).closest('.feed').attr('liveuser-id');
        //var uid = $(this).closest('.feed').attr('uid');

        var commentBox = $(this);
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
            var comment = commentBox.val();
            if (comment) {
                 var json = { "producer" : producer, "model" : model, "price": price};
       
    $.ajax({
        url: $("#newSmartphoneForm").attr( "action"),
        data: JSON.stringify(json),
        type: "POST",
         
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(smartphone) {
            //var respContent = "";
             
            //respContent += "<span class='success'>Smartphone was created: [";
            //respContent += smartphone.producer + " : ";
            //respContent += smartphone.model + " : " ;
            //respContent += smartphone.price + "]</span>";
             
            //$("#sPhoneFromResponse").html(respContent);         
        }
    });
               
            }
        }
    } else {
        alert("Scripts not allowed !!");
    }
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
					<img src="http://feedstack.asia/img/user.jpg" class="feed-user-image"> 
				</a>	
			</div>
		</div>
		<div class="bcol-85">
			<div class="feed-container">
				<div class="feed-input">
					<textarea class="feed-box" id="txtPost" placeholder="Scribble..." maxlength="500" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 60px;"></textarea>
					<div>
						&nbsp;<img src="http://feedstack.asia/img/loading.gif" class="loading" style="display: none;">
						<span class="right"><button class="post-btn" onclick="savePost()">post</button></span>
					</div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>

        <div class="profile-container-box">
            <script src="http://feedstack.asia/app/script/readmore.js"></script>
            <script src="http://feedstack.asia/app/script/popup.js"></script>
            
            <style>
                .footer {
                    background: white;
                }

                .profile-container-box {
                    min-height: 210px;
                }
            </style>

<c:forEach items="${posts}" var="element">
            <div class="feed feed-${element.getPostId()}" liveuser-id="${element.getUserId()}"  feed-id='${element.getPostId()}'>
                <div class="bcol-15">
                    <div class="feed-user mobile-hidden">
                        <a href="http://feedstack.asia/renudeshmukh">
                            <img src="http://feedstack.asia/img/user.jpg" class="feed-user-image" />
                        </a>
                    </div>
                </div>
                <div class="bcol-85">
                    <div class="feed-container">
                        <div class="feed-block">
                            <div class="bcol-10x mobile-visible">
                                <div class="feed-user">
                                    <a href="http://feedstack.asia/renudeshmukh">
                                        <img src="http://feedstack.asia/img/user.jpg" class="feed-user-image-mobile" />
                                    </a>
                                </div>
                            </div>
                            <div class="bcol-90x">
                                <div class="feed-title">
                                    <b><a href="http://feedstack.asia/renudeshmukh">${element.getUserName()}</a></b>
                                    <span class="feed-delete"><i class="fa fa-trash-o "></i></span>
                                    <small class="feed-time-mobile mobile-visible">${element.getPostDate()} </small>
                                </div>
                            </div>

                            <div class="clear"></div>
                            <div class="feed-content">
                                <div class='article'>${element.getPostData()}</div>
                            </div>
                            <small>
                                <span class="feed-view-count">
                                    <a href="http://feedstack.asia/feed/${element.getPostId()}"><i class="fa fa-eye "></i> 1 Likes </a>
                                </span>
                                <span class="feed-like-count feed-like feed-like-${element.getPostId()}" liveuser-id="" feed-id='${element.getPostId()}'
                                      unlike='0' count='0'>
                                    <i class="fa fa-thumbs-o-up"></i>
                                    <span class="like-count-${element.getPostId()}">0</span>
                                    <span class="likes-word-${element.getPostId()}"> Dislikes</span>
                                </span>
                                <span class="feed-comment-count" feed-id='${element.getPostId()}'>
                                    <i class="fa fa-comment-o "></i>
                                    <span class="comment-count-${element.getPostId()}">
                                        1
                                    </span> Comments
                                </span>
                                <span class="loading-likers" style="display:none"><img src="http://feedstack.asia/img/loading1.gif" /></span>
                                <div class="feed-time mobile-hidden">${element.getPostDate()}</div>
                            </small>
                        </div>
                        <div class='like-entry-${element.getPostId()}'><span class='you-like-${element.getPostId()}'></span></div>		
                        <div class="comment-block-entry-${element.getPostId()}" style="display:none">
                        
<c:set var="post" value="${element}" scope="request" />
<jsp:include page="comment.jsp">
  <jsp:param name="post" value="post"/>
</jsp:include>

    					
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
</c:forEach>

            <div class="bcol-15">&nbsp;</div>
            <div class="bcol-85">
                <div class="no-feeds">No more Feeds</div>
            </div>
            <div class="clear"></div>
            <br>

        </div>
    </div>
       <div class="clear"></div>
</div>


    </jsp:body>
</t:GlobalTemplate>
