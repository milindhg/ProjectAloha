<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:GlobalTemplate>
	<jsp:body>

        <h1>Please upload an image</h1>
        <form:form method="post"  action="${pageContext.request.contextPath}/uploadform" enctype="multipart/form-data">       
            <input type="file" name="file" required/>
            <input type="submit"/>       		
        </form:form>
        
	</jsp:body>
</t:GlobalTemplate>

        