var SearchJS = new function() {

	this.root;

	this.init = function(root) {
		this.root = root;
		this.dynamicSearchEvent();
	};

	this.dynamicSearchEvent = function() {
		$(document).on('keyup', '.member-search', function(event) {
			SearchJS.searchUsers();
		});
	}

	this.searchUsers = function() {
		var searchKeyValue = $('#txtPost').val();
		if (searchKeyValue != '') {
			$
					.ajax({
						headers : {
							'Accept' : 'application/json'
						},
						method : "POST",
						url : SearchJS.root + "/search/users",
						data : {
							searchKey : searchKeyValue
						},
						success : function(data) {
							// alert(data.length);
							$('.member-container').html('');
							for (i = 0; i < data.length; i++) {
								$('.member-container')
										.append(
												"<div class=\"bcol-member-block\"> <div class=\"member-image\"> <a href=\"" + SearchJS.root +  "/profile?userId="
														+ data[i].userId
														+ "\"> <img src=\"" + SearchJS.root + "/displayimage?id=" + data[i].userId + "\" class=\"member\"> </a> </div> <div class=\"member-name\"> <a href=\"" + SearchJS.root +  "/profile?userId="
														+ data[i].userId
														+ "\">"
														+ data[i].firstName
														+ " "
														+ data[i].lastName
														+ "</a> </div> </div>");
							}
							$('.member-container').append(
									"<div class=\"clear\"></div>");
						}
					});
		} else {
			$('.member-container').html('');
		}
	};

};
