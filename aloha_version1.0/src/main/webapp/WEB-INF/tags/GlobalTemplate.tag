 <%@tag description="put the tag description here" pageEncoding="UTF-8"%>
 
<%@attribute name="header" fragment="true" %>
<html lang="en">
<head>
    <title>Aloha</title>
    <link rel="stylesheet" href="http://feedstack.asia/lib/font-awesome/css/font-awesome.css">
    <link href="http://feedstack.asia/themes/simplex/css/bottle.css" rel="stylesheet">
    <link href="http://feedstack.asia/themes/simplex/css/feed-menu.css" rel="stylesheet">
    <link href="http://feedstack.asia/themes/simplex/css/menu.css" rel="stylesheet">
    <link href="http://feedstack.asia/themes/simplex/css/index.css" rel="stylesheet">
    <link href="http://feedstack.asia/themes/simplex/css/userlog.css" rel="stylesheet">
    <link href="http://feedstack.asia/themes/simplex/css/members.css" rel="stylesheet">
    <link href="http://feedstack.asia/themes/simplex/css/wall.css" rel="stylesheet">
    <link href="http://feedstack.asia/themes/simplex/css/profile.css" rel="stylesheet">
   
    <script src="http://feedstack.asia/lib/jquery/jquery.js"></script>
    <script src="http://feedstack.asia/themes/simplex/script/menu.js"></script>
    <script src="http://feedstack.asia/themes/simplex/script/feed-menu.js"></script>
    <script src="http://feedstack.asia/app/script/wall.js"></script>
   
</head>
<body>
    <div class="header">
        <div class="container-main pad-header">
            <div class="bcol-25 logo">
                <a href="http://feedstack.asia/">
                    <img src="http://feedstack.asia/img/logo.png">
                </a>
            </div>
            <div class="bcol-75 menu">
                <span class="menu-text">+ menu</span>
                <ul class="menu-bar">
                    <a href="/common"><li><i class="fa fa-space fa-home"></i>Home</li></a>
                    <a href="http://feedstack.asia/renudeshmukh">
                        <li>
                            <i class="fa fa-space fa-user">
                            </i>Profile
                        </li>
                    </a>
                    <a href="http://feedstack.asia/members"><li><i class="fa fa-space fa-group"></i>Members</li></a>
                    <a href="http://feedstack.asia/user/logout"><li><i class="fa fa-space fa-sign-out"></i>Logout</li></a>
                </ul>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <div class="body" align="center">
          <jsp:doBody/>
    </div>

    <div class="footer">
        <div class="container-main pad-20">
            <div class="ftrr">
                <div class="bcol-70">
                    <ul class="ftr_menu">
                        <a href="http://feedstack.asia/"><li>Home</li></a>
                        <a href="http://feedstack.asia/about"><li>About</li></a>
                        <a href="/post"><li>Scribble</li></a>
                        <a href="http://feedstack.asia/terms"><li>Terms of use</li></a>
                        <a href="http://feedstack.asia/contact"><li>contact</li></a>
                    </ul>
                </div>
                <div class="bcol-30">
                   
                </div>
                <div class="clear"></div>
            </div>
            <div class="ftr">Aloha - A social networking portal | a <a href="http://localhost:8080/common/">Aloha Inc.</a> production </div>
        </div>
    </div>
</body>
</html>