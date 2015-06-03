var PostManager = new function() {

	this.Posts = [];
	this.Root = '';
	this.DateTimeForPaging='';
	this.isMore = true;
	this.Comments = [];

	this.init = function(root) {
		this.Root = root;
		//this.validateSession();
		
		this.getPosts();

		// this.savePost();
	};
	
	this.validateSession = function() {

		$.ajax({
			headers : {
				'Accept' : 'application/json'
			},
			method : "POST",
			data:{
				key : 'value'
			},
			url : PostManager.Root + "/validate",
			success : function(data) {
				if (data == false) {
					window.location.replace(PostManager.Root + "/login");
				}
			},
			error : function(data) {
				// alert(data);
				console.log(data);
			}
		})
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
	
	this.areEnglishChars = function(value) {

		var englishPattern = new RegExp(/^[\w\-\s?,!.']+$/);
		var check2 = englishPattern.test(value);
		
		return check2;
	};


	this.addPost = function() {

		var value = $('#txtPost').val();
		if(value.length ==0)
			{
				alert("Please scribble something !!");
				return;
			}
		if (!PostManager.isInvalidEntry(value)) {

			if(PostManager.areEnglishChars(value)){
			var json = {
				postData : value
			};
			PostManager.isMore == true;
			$.ajax({
				method : "POST",
				url : PostManager.Root + "/post/add",
				data : {
					postData : value
				},
				success : function(data) {
					if (data.statusCode == 0) {

						$('#txtPost').val('');
						var posts = PostManager.Posts;
						PostManager.Posts = [];
						PostManager.Posts.push(data.post);
						for (var i = 0; i < posts.length; i++) {
							PostManager.Posts.push(posts[i]);
						}
						PostManager.renderPosts();
					}else if(data.statusCode == 2){
						alert('Please scribble something !!')
						return;
					}  else {
						window.location.replace(PostManager.Root + "/login");
					}
				}
			});
			} else {alert("Special characters not allowed !!");}

		} else {
			alert("Scripts not allowed !!");
		}
	};

	this.addComment = function() {
		$('.feed-comment-input-entry')
				.keyup(
						function(event) {
							var commentBox = $(this);
							var keycode = (event.keyCode ? event.keyCode
									: event.which);
							if (keycode == '13') {
								var comment = commentBox.val();
								
								if(comment.length ==0)
									{
										alert("Please type a comment !!");
										return;
									}
								
								if (!PostManager.isInvalidEntry(comment)) {
									if(PostManager.areEnglishChars(comment)){
										
									var feed_id = $(this).attr('feed-id');
									var user_id = $(this).attr('user-id');
									if (comment) {
										$
												.ajax({
													headers : {
														'Accept' : 'application/json',
														'Access-Control-Allow-Origin': '*'
													},
													url : PostManager.Root
															+ "/comm/add",
													method : "POST",
													data : {
														"commentData" : comment,
														"postId" : feed_id
													},
													success : function(data) {
														if (data.statusCode == 0) {

															for (var i = 0; i < PostManager.Posts.length; i++) {
																if (PostManager.Posts[i].postId == feed_id) {
																	PostManager.Posts[i].comments
																			.push(data.comment);
																	break;
																}
															}
															PostManager
																	.renderPosts();
															var container = $('.comment-block-entry-'
																	+ feed_id);
															container
																	.slideDown();
														} else if(data.statusCode == 2){
															alert('Please type a comment !!')
															return;
														} 
														else {
															window.location
																	.replace(PostManager.Root
																			+ "/login");
														}
													},
													error : function(data) {
														console.log(data);
													}
												});
									}
									} else {alert("Special characters not allowed !!");}
								} else {
									alert("Scripts not allowed !!");
								}
							}
						})
	};

	this.getPosts = function() {
		if(PostManager.isMore == true){
		console.log(this.Root);
		$.ajax({
			headers : {
				'Accept' : 'application/json',
					'Access-Control-Allow-Origin': '*'
			},
			method : "POST",
			url : PostManager.Root + "/post/getAll",
			data : {
				datetime : PostManager.DateTimeForPaging
			},
			success : function(data) {
				if (data.statusCode == 0) {
					for (var i = 0; i < data.posts.length; i++) {
						PostManager.Posts.push(data.posts[i]);
					}
					if(data.posts.length > 0)
						PostManager.DateTimeForPaging = data.posts[data.posts.length - 1].postDate;
						console.log(PostManager.DateTimeForPaging);
					if(data.posts.length < 20){
						$('.no-more-feeds').css('display', 'block');
						$('.more-feeds').css('display', 'none');
						PostManager.isMore = false;
					}
					PostManager.renderPosts();
				} else {
					window.location.replace(PostManager.Root + "/login");
				}
			},
			error : function(data) {
				// alert(data);
				console.log(data);
			}
		})
		}
	};

	this.renderPosts = function() {
		$('#postContainer').setTemplateURL(
				PostManager.Root + '/resources/pages/postTemplate.jsp');
		$('#postContainer').processTemplate(PostManager.Posts);
		
		PostManager.commentEnterEvent();
		PostManager.deletePostEvent();
		PostManager.deleteCommentEvent();
		PostManager.addComment();
		PostManager.scribbleLike();
		PostManager.scribbleDislike();
		PostManager.getMoreFeeds();
	};

	this.deletePostEvent = function() {
		$('.feed-delete').click(function() {
			var feed_id = $(this).attr('feed-id');

			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Access-Control-Allow-Origin': '*'
				},
				method : "POST",
				url : PostManager.Root + "/post/del",
				data : {
					postId : feed_id
				},
				success : function(data) {
					if (data.statusCode == 0) {

						$('.feed-' + feed_id).slideUp();
						$('.feed-' + feed_id).remove();
						for (var i = 0; i < PostManager.Posts.length; i++) {
							if (PostManager.Posts[i].postId == feed_id) {
								PostManager.Posts.splice(i, 1);
								break;
							}
						}
					} else {
						window.location.replace(PostManager.Root + "/login");
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
		$('.feed-comment-delete').click(
				function() {
					var comm_id = $(this).attr('comment-id');
					var feed_id = $(this).attr('feed-id');
					$.ajax({
						headers : {
							'Accept' : 'application/json',
							'Access-Control-Allow-Origin': '*'
						},
						method : "POST",
						url : PostManager.Root + "/comm/del",
						data : {
							commId : comm_id
						},
						success : function(data) {
							if(data.statusCode == 0){
							
							$('#commDiv' + comm_id).slideUp();
							$('.commDiv' + comm_id).remove();
							var count = parseInt($('.comment-count-' + feed_id)
									.html()) - 1;
							$('.comment-count-' + feed_id).html(count);
							}else {
								window.location.replace(PostManager.Root + "/login");
							}
						},
						error : function(data) {
							// alert(data);
							console.log(data);
						}
					});
				})
	};

	this.scribbleLike = function() {
		$('.feed-like')
				.click(
						function() {
							var postId = $(this).attr('feed-id');
							var userId = $(this).attr('user-id');
							var likeType = -1;
							var counter = 0;
							var counter1 = 0;
							var elem = $(this).find("i").hasClass(
									"fa-thumbs-o-up");
							if (elem == true) {
								$(this).find("i").removeClass("fa-thumbs-o-up")
										.addClass("fa-thumbs-up");
								$(this).find("span").html('Liked');

								if ($('.feed-dislike-' + postId).find("i")
										.hasClass("fa-thumbs-down")) {
									$('.feed-dislike-' + postId).find("i")
											.removeClass("fa-thumbs-down")
											.addClass("fa-thumbs-o-down");
									$('.feed-dislike-' + postId).find("span")
											.html('Dislike');
									counter = 1;
								}
								likeType = 1;

							} else {
								$(this).find("i").removeClass("fa-thumbs-up")
										.addClass("fa-thumbs-o-up");
								$(this).find("span").html('Like');
								likeType = 0;
								counter1 = 1;
							}

							$
									.ajax({
										headers : {
											'Accept' : 'application/json',
											'Access-Control-Allow-Origin': '*'
										},
										method : "POST",
										url : PostManager.Root + "/post/like",
										data : {
											likeType : likeType,
											postId : postId,
											
										},
										success : function(data) {
											if(data.statusCode == 0){
											if (data.likeType == 1 || data.likeType == 2) {
												if (likeType == 1) {
													var count = parseInt($(
															'.like-count-'
																	+ postId)
															.html()) + 1;
													$('.like-count-' + postId)
															.html(count);
												} else {
													var count = parseInt($(
															'.like-count-'
																	+ postId)
															.html()) - 1;
													$('.like-count-' + postId)
															.html(count);
												}

												var cnt = parseInt($(
														'.dislike-count-'
																+ postId)
														.html())
														- counter;
												$('.dislike-count-' + postId)
														.html(cnt);
											}else{
												var count = parseInt($(
														'.like-count-'
																+ postId)
														.html()) - 1;
												$('.like-count-' + postId)
														.html(count);	
											}
											}else {
												window.location.replace(PostManager.Root + "/login");
											}
										},
										error : function(data) {
											// alert(data);
											console.log(data);
										}
									});

						});
	};

	this.scribbleDislike = function() {
		$('.feed-dislike')
				.click(
						function() {
							var postId = $(this).attr('feed-id');
							var userId = $(this).attr('user-id');
							var likeType = -1;
							var counter = 0;
							var elem = $(this).find("i").hasClass(
									"fa-thumbs-o-down");
							if (elem == true) {
								$(this).find("i").removeClass(
										"fa-thumbs-o-down").addClass(
										"fa-thumbs-down");
								$(this).find("span").html('Disliked');

								if ($('.feed-like-' + postId).find("i")
										.hasClass("fa-thumbs-up")) {
									$('.feed-like-' + postId).find("i")
											.removeClass("fa-thumbs-up")
											.addClass("fa-thumbs-o-up");
									$('.feed-like-' + postId).find("span")
											.html('Like');
									counter = 1;
								}

								likeType = 2;

							} else {
								$(this).find("i").removeClass("fa-thumbs-down")
										.addClass("fa-thumbs-o-down");
								$(this).find("span").html('Dislike');
								likeType = 0;

							}

							$
									.ajax({
										headers : {
											'Accept' : 'application/json',
											'Access-Control-Allow-Origin': '*'
										},
										method : "POST",
										url : PostManager.Root
												+ "/post/dislike",
										data : {
											likeType : likeType,
											postId : postId,
											
										},
										success : function(data) {
											if(data.statusCode == 0){

											if (data.likeType == 1 || data.likeType == 2) {
												if (likeType == 2) {
													var count = parseInt($(
															'.dislike-count-'
																	+ postId)
															.html()) + 1;
													$(
															'.dislike-count-'
																	+ postId)
															.html(count);
												} else {
													var count = parseInt($(
															'.dislike-count-'
																	+ postId)
															.html()) - 1;
													$(
															'.dislike-count-'
																	+ postId)
															.html(count);
												}

												var cnt = parseInt($(
														'.like-count-' + postId)
														.html())
														- counter;
												$('.like-count-' + postId)
														.html(cnt);
											}else {
												var count = parseInt($(
														'.dislike-count-'
																+ postId)
														.html()) - 1;
												$(
														'.dislike-count-'
																+ postId)
														.html(count);
											}
											}else {
												window.location.replace(PostManager.Root + "/login");
											}
										},
										error : function(data) {
											// alert(data);
											console.log(data);
										}
									});
						});
	};
	
	this.getMoreFeeds = function() {
		$('.more-feeds').click(
				function() {
			PostManager.getPosts();
		});
	};

}