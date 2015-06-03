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
  
  <div style="margin-left:400px; margin-top:20px; border-color:green" class="span3 hero-unit ">
  
        <h1>Reset Password</h1>
        <h6>${headerMessage}</h6>
        <form:form name="sign_up" method="post" onSubmit="return Validate()" action="${pageContext.request.contextPath}/changepassword?id=${id}"> 
        	<label>Verification Password</label>
			 <input type="password" name="vpwd" width="100px" size="20" required/>		      
            <label>Password</label>
			 <input type="password" name="pwd" width="100px" 
			 pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).{8,15}" 
			 title="Please enter at least one uppercase, one lowercase and one numeric. Length should be at least 8 and maximum 15" 
			 required/>
			 <label>Confirm Password</label>
			 <input type="password" name="cpwd" width="100px" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}" required/>			 
            <input type="submit"/>       		
        </form:form>
  </div>      
<script>
function Validate(){
      var in_pswd = document.forms["sign_up"]["pwd"].value;
	  var c_pswd = document.forms["sign_up"]["cpwd"].value;	  
      if(c_pswd.match(in_pswd))
	  {
	    ret = true;
	  }
	  else
	  {
		  $("#cpwd_e").css('display','block');
       	  ret = false;
       	  document.forms["sign_up"].reset();
	  }
	  
	  return ret;
}
</script>        
	</jsp:body>
</t:GlobalTemplate>
