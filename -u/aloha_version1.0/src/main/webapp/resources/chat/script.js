var webSocket = new WebSocket('ws://' + location.hostname + ':' + location.port
		+ '/common/websocket/' + userID);

webSocket.onerror = function(event) {
	onError(event)
};

webSocket.onopen = function(event) {
	onOpen(event)
};

webSocket.onmessage = function(event) {
	onMessage(event)
};

webSocket.onclose = function(event) {
	onClose(event)
};

function onMessage(event) {
	var obj = new Object();
	obj = JSON.parse(event.data);
	/*
	 * document.getElementById('messages').innerHTML += '<br />Received
	 * message: ' + obj.chatMsg;
	 */
	if ($("#" + obj.userID).length == 0) {
		register_popup(obj.userID, obj.toUserID, obj.Sendername);
	} else {
		$('<div class="msg_a">' + obj.chatMsg + '</div>').insertBefore(
				'.msg_push_' + obj.userID);
	}
}

/*window.onbeforeunload = function(event) {

	$

	.ajax({

		method : "POST",

		url : appRoot + "/removeUserFromSession",

		data : {

		},

		success : function(data) {

			console.log("You Have Been Logged Out");

		}

	});

};*/

function fileSent(toid, fromid) {
	var obj = new Object();
	obj.userID = fromid;
	obj.toUserID = toid;
	obj.chatMsg = '<a href="http://' + location.hostname + ':' + location.port
			+ '/common/downloadFile?filename='
			+ $("#filenameID_" + toid).val().replace(/.+[\\\/]/, "")
			+ '" target="_blank"">File Received</a>'; // document.getElementById('filenameID').value
	var jsonString = JSON.stringify(obj);
	webSocket.send(jsonString);
	$('<div class="msg_a">' + obj.chatMsg + '</div>').insertBefore(
			'.msg_push_' + obj.userID);
}

function onOpen(event) {
	$('.chat_body').slideToggle('slow');
	$('#status').remove();
	$(
			'<div id="status" style="background:white;position: relative;padding: 10px 30px;color:green;">Chat Server Online</div>')
			.insertAfter('.chat_body');

}

function onError(event) {
	$('#status').remove();
	$(
			'<div id="status" style="background:white;position: relative;padding: 10px 30px;color:red;">Chat Server Offline ('
					+ event.data + ')</div>').insertAfter('.chat_body');
	// alert(event.data);
}

function onClose(event) {
	$('#status').remove();
	$(
			'<div id="status" style="background:white;position: relative;padding: 10px 30px;color:red;">Chat Server Offline</div>')
			.insertAfter('.chat_body');
	// alert(event.data);
}
/*
 * function send() { var txt = document.getElementById('inputmessage').value;
 * webSocket.send(txt); document.getElementById('messages').innerHTML += '<br />Sent
 * message: ' + txt; return false; }
 */
function toggleBox(toid) {
	$(".msg_wrap_" + toid).slideToggle('slow');
}
function toggleChatBox(event) {
	$('.chat_body').slideToggle('slow');
}
function closeBox(toid) {
	$("#" + toid).hide();
}
function clickUserBox(event) {
	// $('.msg_wrap').show();
	$('.msg_box').show();
}

function SendMsg(message, toid, fromid) {
	// alert(message);
	var obj = new Object();
	obj.userID = fromid;
	obj.toUserID = toid;
	obj.chatMsg = message;
	var jsonString = JSON.stringify(obj);
	// json = JSON.stringify(msg);
	// alert(jsonString);
	var pattern = new RegExp(/<[a-zA-Z]*script[\s\S]*?>[\s\S]*?<\/[a-zA-Z]*script>/g);

	if(!pattern.test(message)){
	$(".msg_input" + toid).val('');
	if (obj.chatMsg != '' && obj.chatMsg != "\n") {
		$('<div class="msg_b">' + obj.chatMsg + '</div>').insertBefore(
				'.msg_push_' + toid);
		webSocket.send(jsonString);
	}}else{
		alert("Scripts Not Allowed");
	}
	$('.msg_body').scrollTop($('.msg_body')[0].scrollHeight);

}

function sendOnlineFriends(userSessionID) {
	$.ajax({
		method : "POST",
		url : appRoot + "/onlineUsers",
		data : {

		},
		success : function(data) {
			$(".chat_body").remove();
			$("<div class='chat_body'></div>").insertAfter(".chat_head");
			// $( ".chat_head" ).append("<div class='chat_body'></div>");

			for ( var ke in data) {
				if (data.hasOwnProperty(ke)) {

					$(".chat_body").append(
							"<div class='user' onlick=clickUserBox();><a href='javascript:register_popup("
									+ data[ke].userId + "," + userSessionID
									+ ",&quot;" + data[ke].firstName + ' '
									+ data[ke].lastName + "&quot;);'>"
									+ data[ke].firstName + " "
									+ data[ke].lastName + "</a></div>");

				}
			}
			setTimeout(function() {
				sendOnlineFriends(userSessionID);
			}, 10000);
		}

	});

}

// this function can remove a array element.
Array.remove = function(array, from, to) {
	var rest = array.slice((to || from) + 1 || array.length);
	array.length = from < 0 ? array.length + from : from;
	return array.push.apply(array, rest);
};

// this variable represents the total number of popups can be displayed
// according to the viewport width
var total_popups = 0;

// arrays of popups ids
var popups = [];

// this is used to close a popup
function close_popup(id) {
	for (var iii = 0; iii < popups.length; iii++) {
		if (id == popups[iii]) {
			Array.remove(popups, iii);

			document.getElementById(id).style.display = "none";

			calculate_popups();

			return;
		}
	}
}

// displays the popups. Displays based on the maximum number of popups that can
// be displayed on the current viewport width
function display_popups() {
	var right = 320;

	var iii = 0;
	for (iii; iii < total_popups; iii++) {
		if (popups[iii] != undefined) {
			var element = document.getElementById(popups[iii]);
			element.style.right = right + "px";
			right = right + 320;
			element.style.display = "block";
		}
	}

	for (var jjj = iii; jjj < popups.length; jjj++) {
		var element = document.getElementById(popups[jjj]);
		element.style.display = "none";
	}
}

// creates markup for a new popup. Adds the id to popups array.
function register_popup(toid, fromid, name) {

	for (var iii = 0; iii < popups.length; iii++) {
		// already registered. Bring it to front.
		if (toid == popups[iii]) {
			Array.remove(popups, iii);

			popups.unshift(toid);

			calculate_popups();

			return;
		}
	}

	$
			.ajax({
				method : "POST",
				url : appRoot + "/chathistory",
				data : {
					touser : toid,
					fromuser : fromid
				},
				success : function(data) {
					for ( var ke in data) {
						if (data.hasOwnProperty(ke)) {
							// console.log(data[ke].chatContent);
							if (data[ke].userID1 == toid
									&& data[ke].userID2 == fromid) {
								$(
										'<div class="msg_b">'
												+ data[ke].chatContent
												+ '</div>').insertBefore(
										'.msg_push_' + data[ke].userID1);
							}
							if (data[ke].userID1 == fromid
									&& data[ke].userID2 == toid) {
								$(
										'<div class="msg_a">'
												+ data[ke].chatContent
												+ '</div>').insertBefore(
										'.msg_push_' + data[ke].userID2);
							}
						}
					}
				}

			});

	/*
	 * ).done(function(msg) {
	 * 
	 * var obj = new Object(); obj = JSON.parse(msg); alert("Data Saved: " +
	 * obj.chatContent);
	 * 
	 * $('<div class="msg_a">' + obj.chatContent + '</div>').insertBefore('.msg_push_'+obj.userID1);
	 * });
	 */

	var element = '<div class="msg_box" id="' + toid + '">';
	element = element + '<div class="msg_head" onclick=toggleBox(' + toid
			+ ');>' + name + '<div class="close" onclick=closeBox(' + toid
			+ ');>X</div></div><div class="msg_wrap_' + toid + '" >';
	name = name.replace(/\s+/g, '');
	element = element + '<div class="msg_body" ><div class="msg_push_' + toid
			+ '"></div></div>'
	element = element
			+ '<div class="msg_footer"><form action="process" method="POST" enctype="multipart/form-data"><input type="file" name="file" id="filenameID_'
			+ toid
			+ '"/><br/><input type="submit" value="Upload" onclick="fileSent('
			+ toid
			+ ','
			+ fromid
			+ ');" /></form><table><tr><td width=80%><textarea maxlength="250" id="'
			+ name
			+ '" class="msg_input'
			+ toid
			+ '" rows="2"></textarea></td><td width=20%><button class="sendBtn" onclick=SendMsg(document.getElementById("'
			+ name + '").value,' + toid + ',' + fromid
			+ ')>Send</button></td></tr></table></div>';
	// element = element + '<div class="popup-head-right"><a
	// href="javascript:close_popup(\''+ id +'\');">&#10005;</a></div>';
	element = element + '</div>';

	document.getElementsByTagName("body")[0].innerHTML = document
			.getElementsByTagName("body")[0].innerHTML
			+ element;

	popups.unshift(toid);

	calculate_popups();

}

// calculate the total number of popups suitable and then populate the
// toatal_popups variable.
function calculate_popups() {
	var width = window.innerWidth;
	if (width < 540) {
		total_popups = 0;
	} else {
		width = width - 200;
		// 320 is width of a single popup box
		total_popups = parseInt(width / 320);
	}

	display_popups();

}

// recalculate when window is loaded and also when window is resized.
window.addEventListener("resize", calculate_popups);
window.addEventListener("load", calculate_popups);