var PostManager = new function() {

	this.Posts = [];
	this.Root = '';

	this.Comments = [];

	this.init = function(root) {
		// alert('in init');
		this.Root = root;
		this.getPosts();

		// this.savePost();
	};

	this.commentEnterEvent = function() {
		$('.feed-comment-count').click(
				function() {
					var feed_id = $(this).closest('.feed').attr('feed-id');
					var container = $('.comment-block-entry-' + feed_id);
					var comment_count = parseInt($('.comment-count-' + feed_id)
							.html());
					var ele = $(this).next('.loading-likers');

					if (container.is(':visible')) {
						container.slideUp();
					} else {
						ele.show();
						container.slideDown();
						ele.hide();
					}
				});
	};

	this.isInvalidEntry = function(value) {

		var pattern = new RegExp(
				/<[a-zA-Z]*script[\s\S]*?>[\s\S]*?<\/[a-zA-Z]*script>/g);
		return pattern.test(value);
	};

	this.addPost = function() {

		var value = $('#txtPost').val();
		if (!this.isInvalidEntry(value)) {

			var json = {
				postData : value
			};

			$.ajax({
				method : "POST",
				url : "http://localhost:8080/common/post/add",
				data : {
					postData : value
				},
				success : function(data) {
					$('#txtPost').val('');
					var posts = PostManager.Posts;
					PostManager.Posts = [];
					PostManager.Posts.push(data);
					for (var i = 0; i < posts.length; i++) {
						PostManager.Posts.push(posts[i]);
					}
					PostManager.renderPosts();
				}
			});

		} else {
			alert("Scripts not allowed !!");
		}
	};

	 this.addComment = function () {
	        $('.feed-comment-input-entry').keyup(function (event) {
	            var commentBox = $(this);
	            var keycode = (event.keyCode ? event.keyCode : event.which);
	            if (keycode == '13') {
	            	var comment = commentBox.val();
	                if (!PostManager.isInvalidEntry(comment)) {
	                    
	                    var feed_id = $(this).attr('feed-id');
	                    var user_id = $(this).attr('user-id');
	                    if (comment) {
	                        $.ajax({
	                            headers: {
	                                'Accept': 'application/json'
	                            },
	                            url: PostManager.Root + "/comm/add",
	                            method: "POST",
	                            data: {
	                                "commentData": comment,
	                                "userId": user_id,
	                                "postId": feed_id
	                            },
	                            success: function (data) {
	                                for (var i = 0; i < PostManager.Posts.length; i++) {
	                                    if (PostManager.Posts[i].postId == feed_id) {
	                                        PostManager.Posts[i].comments.push(data);
	                                        break;
	                                    }
	                                }
	                                PostManager.renderPosts();
	                                var container = $('.comment-block-entry-' + feed_id);
	                                container.slideDown();
	                            },
	                            error: function (data) {
	                                console.log(data);
	                            }
	                        });
	                    }
	                } else {
	                    alert("Scripts not allowed !!");
	                }
	            }
	        })
	    };

	this.getPosts = function() {
		console.log(this.Root);
		$.ajax({
			headers : {
				'Accept' : 'application/json'
			},
			method : "POST",
			url : PostManager.Root + "/post/getAll",
			data : {
				searchKey : "ignore"
			},
			success : function(data) {
				console.log('success');
				for (var i = 0; i < data.length; i++) {
					PostManager.Posts.push(data[i]);
				}

				PostManager.renderPosts();
			},
			error : function(data) {
				// alert(data);
				console.log(data);
			}
		})
	};

	this.renderPosts = function() {
		$('#postContainer').setTemplateURL(
				PostManager.Root + '/resources/pages/postTemplate.jsp');
		$('#postContainer').processTemplate(PostManager.Posts);
		PostManager.commentEnterEvent();
		PostManager.deletePostEvent();
		PostManager.deleteCommentEvent();
		PostManager.addComment();
	};

	this.deletePostEvent = function() {
		$('.feed-delete').click(function() {
			var feed_id = $(this).attr('feed-id');

			$.ajax({
				headers : {
					'Accept' : 'application/json'
				},
				method : "POST",
				url : PostManager.Root + "/post/del",
				data : {
					postId : feed_id
				},
				success : function(data) {

					$('.feed-' + feed_id).slideUp();
					$('.feed-' + feed_id).remove();
					for (var i = 0; i < PostManager.Posts.length; i++) {
						if (PostManager.Posts[i].postId == feed_id) {
							PostManager.Posts.splice(i, 1);
							break;
						}
					}
				},
				error : function(data) {
					// alert(data);
					console.log(data);
				}
			});
		})
	};

	this.deleteCommentEvent = function() {
		$('.feed-comment-delete').click(function() {
			var comm_id = $(this).attr('comment-id');

			$.ajax({
				headers : {
					'Accept' : 'application/json'
				},
				method : "POST",
				url : PostManager.Root + "/comm/del",
				data : {
					commId : comm_id
				},
				success : function(data) {

					$('#commDiv' + comm_id).slideUp();
					$('.commDiv' + comm_id).remove();

				},
				error : function(data) {
					// alert(data);
					console.log(data);
				}
			});
		})
	};

}