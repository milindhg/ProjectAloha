<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:GlobalTemplate>
	<jsp:body>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" type="text/css"></link>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css"></link> 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css">
  	<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
    <!--[if IE]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
  <div class="input-container">
			<form:form name="editaccountdetails" method="post" action="${pageContext.request.contextPath}/editaccountdetails">
			
			<div style="margin-left:400px; margin-top:20px; border-color:green" class="span3 hero-unit ">
				<div>
					<label>First Name</label>
	 				<input type="text" name="fname" value="${user.getFirstName() }" width="100px" size = "20" pattern="[A-Za-z]{1,20}" title = "please enter only characters" required/>
				</div>
				<div>
				 <label>Last Name</label>
				 <input type="text" name="lname" value="${user.getLastName() }" width="100px" size = "20" pattern="[A-Za-z]{1,20}" title = "please enter only characters" required/>
				</div>
				<div> 
				 <label>Contact Number</label>
				 <input type="text" name="cnum" value="${user.getContactNumber() }" width="100px" pattern="\d{10}" title="please enter only 10 digits" required/>
				</div>
				<div> 
				 <label>Email-Id</label>
				 <input type="email" name="email" value="${user.getEmail() }" width="100px" disabled=""/>
				</div>
				<div> 
				 <label>Date of Birth</label>
				 <input type="date" name = "dob" value="${user.getDateOfBirth() }" min="1915-12-31" max="1997-12-31" title="Minimum age should be 18" required/>
				</div>
				 <button type="submit" class="btn btn-save"> Save</button>
				 <a style="font-size:24px" href="${pageContext.request.contextPath}/user_profile/">Back to Profile</a>
					
			</div>
			</form:form>
     </div>
	</jsp:body>
</t:GlobalTemplate>
			