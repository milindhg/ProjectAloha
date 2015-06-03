<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:forEach items="${post.getComments()}" var="comment">
    <div class="feed-comment">
        <div class="feed-comment-list feed-comment-list-${comment.getPostId()}">
            <div class="feed-comment-block feed-comment-block-202">
                <div class="bcol-20x">
                    <div class="feed-comment-user">
                        <a href="http://feedstack.asia/renudeshmukh">
                            <img src="http://feedstack.asia/img/user.jpg" class="feed-comment-user-image">
                        </a>
                    </div>
                </div>
                <div class="bcol-80x">
                    <div class="feed-comment-card">
                        <div class="feed-comment-name">
                            <small>
                                <b>
                                    <a href="http://feedstack.asia/renudeshmukh">
                                        ${comment.getUserName()}
                                    </a>
                                </b>
                            </small>
                            <small>${comment.getCommentDate()}</small>

                            <span class="feed-comment-delete" comment-id="202"><i class="fa fa-trash-o "></i></span>
                        </div>
                        <div class="feed-comment-text">
                           ${comment.getCommentData()}
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
            <div class="feed-comment-entry feed-comment-entry-${comment.getPostId()}"></div>
        </div>
           </div>

</c:forEach>

<div class="feed-comment-input" feed-id="${post.getPostId()}">
            <div class="bcol-20x">
                <div class="feed-comment-user">
                    <a href="http://feedstack.asia/renudeshmukh">
                        <img src="http://feedstack.asia/img/user.jpg" class="feed-comment-user-image">
                    </a>
                </div>
            </div>
            <div class="bcol-80x">
                <div class="feed-comment-input-box">
                    <input type="text" id= "comment${post.getPostId()}" class="feed-comment-input-entry " maxlength="500" placeholder="Enter the comment...">
                </div>
            </div>
            <div class="clear"></div>
        </div>
 
