{#foreach $T as Post}
            <div class="feed feed-{ $T.Post.postId}" liveuser-id="{ $T.Post.userId}"  feed-id='{ $T.Post.postId}'>
                <div class="bcol-15">
                    <div class="feed-user mobile-hidden">
                        <a href="http://feedstack.asia/renudeshmukh">
                            <!-- <img src="http://feedstack.asia/img/user.jpg" class="feed-user-image" /> -->
                            <img class="feed-user-image" src="/common/displayimage?id={ $T.Post.userId}">
                        </a>
                    </div>
                </div>
                <div class="bcol-85">
                    <div class="feed-container">
                        <div class="feed-block">
                            <div class="bcol-10x mobile-visible">
                                <div class="feed-user">
                                    <a href="http://feedstack.asia/renudeshmukh">
                                        <!-- <img src="http://feedstack.asia/img/user.jpg" class="feed-user-image-mobile" /> -->
                                        <!-- <img class="feed-user-image" src="/common/displayimage?id={ $T.Post.userId}"> -->
                                    </a>
                                </div>
                            </div>
                            <div class="bcol-90x">
                                <div class="feed-title">
                                    <b><a href="http://feedstack.asia/renudeshmukh">{ $T.Post.userName}</a></b>
                                    
                                    {#if $T.Post.canDelete == '1'}
                                    <span class="feed-delete" feed-id="{ $T.Post.postId}"><i class="fa fa-trash-o "></i></span>
                                      {#/if} 
                                    <small class="feed-time-mobile mobile-visible">{$T.Post.postDate} </small>
                                </div>
                            </div>

                            <div class="clear"></div>
                            <div class="feed-content">
                                <div class='article'>{ $T.Post.postData}</div>
                            </div>
                            <small>
                            	<span class="feed-like-count feed-like feed-like-{ $T.Post.postId}" liveuser-id="" feed-id='{ $T.Post.postId}' user-id="{ $T.Post.userId}"
                                      like='0' count='0'>
                                      {#if $T.Post.userLikeType == 1}
                                    <i class="fa fa-thumbs-up"></i>
                                    <span class="likes-word-{ $T.Post.postId}">Liked</span>
                                    {#else}
                                    <i class="fa fa-thumbs-o-up"></i>
                                    <span class="likes-word-{ $T.Post.postId}">Like</span>
                                    {#/if}
                                </span>
                                <span class="feed-like-count feed-likes feed-like-count-{ $T.Post.postId}"  liveuser-id="" feed-id='{ $T.Post.postId}'
                                      like='0' count='0'>
                                    
                                    <span class="like-count-{ $T.Post.postId}">{ $T.Post.numLikes}</span>
                                    <span class="likes-word-{ $T.Post.postId}">Likes</span>
                                </span>
                                <span class="feed-like-count feed-dislike feed-dislike-{ $T.Post.postId}" liveuser-id="" feed-id='{ $T.Post.postId}' user-id="{ $T.Post.userId}"
                                      unlike='0' count='0'>
                                      {#if $T.Post.userLikeType == 2}
                                      <i class="fa fa-thumbs-down"></i>
                                    <span class="likes-word-{ $T.Post.postId}">Disliked</span>
                                      {#else}
                                    <i class="fa fa-thumbs-o-down"></i>
                                    <span class="likes-word-{ $T.Post.postId}">Dislike</span>
                                     {#/if}
                                </span>
                                <span class="feed-like-count feed-likes feed-dislike-count{ $T.Post.postId}" liveuser-id="" feed-id='{ $T.Post.postId}' 
                                      unlike='0' count='0'>
                                   
                                    <span class="dislike-count-{ $T.Post.postId}">{ $T.Post.numDislikes}</span>
                                    <span class="likes-word-{ $T.Post.postId}"> Dislikes</span>
                                </span>
                                <span class="feed-comment-count" feed-id='{ $T.Post.postId}'>
                                    <i class="fa fa-comment-o "></i>
                                    <span class="comment-count-{ $T.Post.postId}">
                                        { $T.Post.comments.length}
                                    </span> Comments
                                </span>
                                <span class="loading-likers" style="display:none"><img src="http://feedstack.asia/img/loading1.gif" /></span>
                                <div class="feed-time mobile-hidden">{ $T.Post.postDate}</div>
                            </small>
                        </div>
                        <div class='like-entry-{ $T.Post.postId}'><span class='you-like-{ $T.Post.postId}'></span></div>		
                        <div class="comment-block-entry-{ $T.Post.postId}" style="display:none">
                        
{#foreach $T.Post.comments as comment}
    <div class="feed-comment" id="commDiv{ $T.comment.commentId}">
        <div class="feed-comment-list feed-comment-list-{ $T.comment.commentId}">
            <div class="feed-comment-block feed-comment-block-202">
                <div class="bcol-20x">
                    <div class="feed-comment-user">
                        <a href="http://feedstack.asia/{ $T.comment.userId}">
                            <!-- <img src="http://feedstack.asia/img/user.jpg" class="feed-comment-user-image"> -->
                            <img class="feed-user-image" src="/common/displayimage?id={ $T.comment.userId}">
                        </a>
                    </div>
                </div>
                <div class="bcol-80x">
                    <div class="feed-comment-card">
                        <div class="feed-comment-name">
                            <small>
                                <b>
                                    <a href="http://feedstack.asia/{ $T.comment.userId}">
                                        { $T.comment.userName}
                                    </a>
                                </b>
                            </small>
                            <small>{ $T.comment.commentDate}</small>

							{#if $T.comment.canDelete == '1'}     
                            <span class="feed-comment-delete" comment-id="{ $T.comment.commentId}" feed-id="{ $T.comment.postId}"><i class="fa fa-trash-o "></i></span>
                            {#/if} 
                        </div>
                        <div class="feed-comment-text">
                           { $T.comment.commentData}
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
            <div class="feed-comment-entry feed-comment-entry-{ $T.comment.postId}"></div>
        </div>
           </div>

{#/for}



<div class="feed-comment-input" feed-id="{ $T.Post.postId}">
            <div class="bcol-20x">
                <div class="feed-comment-user">
                    <a href="http://feedstack.asia/{ $T.Post.userId}">
                        <img src="http://feedstack.asia/img/user.jpg" class="feed-comment-user-image">
                    </a>
                </div>
            </div>
            <div class="bcol-80x">
                <div class="feed-comment-input-box">
                    <input type="text" id= "comment{ $T.Post.postId}" feed-id="{ $T.Post.postId}" user-id="{ $T.Post.userId}" class="feed-comment-input-entry " maxlength="500" placeholder="Enter the comment...">
                </div>
            </div>
            <div class="clear"></div>
        </div>
 




    					
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
 {#/for}
 
 