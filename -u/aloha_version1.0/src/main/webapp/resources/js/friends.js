var FriendJS = new function() {

	this.root;

	this.init = function(root) {
		this.root = root;
		this.inviteFriendEvent();
		this.acceptFriendEvent();
		this.ignoreFriendEvent();
		this.addFriendEvent();
		this.unFriendEvent();
	}

	this.isEmailValid = function(value) {
		// pattern for email validation of invite friends.
		var pattern = new RegExp(
				/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i);
		// var pattern = new
		// RegExp(/^[A-z0-9._%+-]+@[A-z0-9.-]+\.[A-z]{2,6}$/g);
		return pattern.test(value);
	};

	this.inviteFriendEvent = function() {
		$('#inviteFriendBtn').click(function() {
			FriendJS.inviteFriend();
		});
	};

	this.acceptFriendEvent = function() {
		$('.acceptFriendBtn').click(function() {
			var userId = $(this).attr('userID');
			var acceptorId = $(this).attr('acceptorID');
			FriendJS.acceptFriend(userId, acceptorId);
		});
	};

	this.ignoreFriendEvent = function() {
		$('.ignoreFriendBtn').click(function() {
			var userId = $(this).attr('userID');
			var acceptorId = $(this).attr('acceptorID');
			FriendJS.ignoreFriend(userId, acceptorId);
		});
	};

	this.addFriendEvent = function() {
		$('#addFriendBtn').click(function() {
			var userId = $(this).attr('userID');
			FriendJS.addFriend(userId);
		});
	};

	this.unFriendEvent = function() {
		$('#unFriendBtn').click(function() {
			var friendshipId = $(this).attr('friendshipId');
			FriendJS.unFriend(friendshipId);
		});
	};

	this.inviteFriend = function() {
		var emailAddr = $('#email').val();
		if (emailAddr == '' || !FriendJS.isEmailValid(emailAddr)) {
			$('#inviteFriendStatus').html('Please enter a valid email address');
		} else {
			$('#email').text('');
			console.log(emailAddr);
			$.ajax({
				headers : {
					'Accept' : 'application/json'
				},
				method : "POST",
				url : FriendJS.root + "/friendsinvite",
				data : {
					email : emailAddr
				},
				success : function(data) {
					console.log(data);
					if (data == true) {
						$('#email').val('');
						$('#inviteFriendStatus').html('Friend Invited');
					} else {
						$('#inviteFriendStatus').html('Invalid Email Address');
					}
				},
				error : function(data) {
					console.log('error occurred');
					console.log(data);
					$('#inviteFriendStatus').html('Invalid Email Address');
				}
			});
		}
	};

	this.addFriend = function(userId) {
		console.log(userId);
		$.ajax({
			headers : {
				'Accept' : 'application/json'
			},
			method : "POST",
			url : FriendJS.root + "/friends/add",
			data : {
				userIdToAdd : userId
			},
			success : function(data) {
				console.log('success');
				$('#addFriendBtn').html('Friend Request Sent')
			},
			error : function(data) {
				console.log('error occurred');
				console.log(data);
			}
		});
	};

	this.acceptFriend = function(userId, acceptorId) {
		console.log("userId" + userId);
		console.log("acceptorId" + acceptorId);
		$
				.ajax({
					headers : {
						'Accept' : 'application/json'
					},
					method : "POST",
					url : FriendJS.root + "/friends/accept",
					data : {
						userIdToAccept : userId,
						acceptor : acceptorId
					},
					success : function(data) {
						console.log('success');
						$('#acceptFriendBtn_' + userId).html(
								'Friend Request Accepted');
						$('#pendingReqBox_' + userId).html('Friend Request Accepted');
					},
					error : function(data) {
						console.log('error occurred');
						console.log(data);
					}
				});
	};

	this.ignoreFriend = function(userId, acceptorId) {
		console.log("userId" + userId);
		console.log("acceptorId" + acceptorId);
		$.ajax({
			headers : {
				'Accept' : 'application/json'
			},
			method : "POST",
			url : FriendJS.root + "/friends/ignore",
			data : {
				userIdToIgnore : userId,
				acceptor : acceptorId
			},
			success : function(data) {
				console.log('success');
				$('#ignoreFriendBtn_' + userId).html('Friend Request Ignored');
				$('#pendingReqBox_' + userId).html('Friend Request Accepted');
			},
			error : function(data) {
				console.log('error occurred');
				console.log(data);
			}
		});
	};

	this.unFriend = function(friendshipId) {
		console.log(friendshipId);
		$.ajax({
			headers : {
				'Accept' : 'application/json'
			},
			method : "POST",
			url : FriendJS.root + "/friends/remove",
			data : {
				friendshipIdToRemove : friendshipId
			},
			success : function(data) {
				console.log('success');
				$('#unFriendBtn').html('Friend Removed')
			},
			error : function(data) {
				console.log('error occurred');
				console.log(data);
			}
		});
	};

};
