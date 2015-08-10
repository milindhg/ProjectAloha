<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:GlobalTemplate>
	<jsp:body>
          <link href="${pageContext.request.contextPath}/resources/Login/css/bootstrap.css" rel="stylesheet" type="text/css"></link>
	<link href="${pageContext.request.contextPath}/resources/Login/css/bootstrap-responsive.css" rel="stylesheet" type="text/css"></link> 
	
    <!--[if IE]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
  <style type="text/css">

 
.container {
		   margin-top:50px;		   
 }
</style>
  <div class="container">

<!-- Start of FORM -->
<form name="forgot" method="post" action="${pageContext.request.contextPath}/forgotpassword">


<div style="margin-left:400px; margin-top:20px; border-color:green" class="span3 hero-unit ">
	 <h2>Forgot Password</h2><br/>
	 <h6>${headerMessage}</h6>
	 <label>Email-Id</label><input type="email" name="email" width="100px"  required/>
	 <input class="btn btn-primary" type="submit" value="submit" name="submit" align="middle"><br/>
	 <a style="font-size:20px" href="${pageContext.request.contextPath}/login/sign_up/">Not a member yet? Join Us</a>
</div>


</form>
</div>
<!-- End of FORM -->
 
  <script src ="http://code.jquery.com/jquery-latest.js"></script>
<script src="${pageContext.request.contextPath}/resources/Login/js/bootstrap.min.js"></script>
        
	</jsp:body>
</t:GlobalTemplate>
	