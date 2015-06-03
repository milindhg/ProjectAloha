<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:GlobalTemplate>
	<jsp:body>
	<script
			src="${pageContext.request.contextPath}/resources/js/search.js"
			type="text/javascript"></script>
	<script>
		$(document).ready(function() {
			SearchJS.init("${pageContext.request.contextPath}");
		});
	</script>

    <div class="container-main pad-20">
      <div class="app">
<!--         <script src="http://feedstack.asia/app/script/members.js"></script> -->
        <div class="member-body">
          <div class="header-member">
            <i class="fa fa-space fa-group"></i> Search Users
            <span class="members-count"></span>
            <span class="member-seach-span">
              <input type="text" class="member-search"
							placeholder="search..." root="http://feedstack.asia/"
							id="txtPost">
							<button id="searchBtn" class="post-btn" >Search</button>
            </span>
          </div>
          <div class="root" root="http://feedstack.asia/"
						access-token=""></div>
          <div class="entry">
            <div class="member-container">
            	<!-- Here comes the dynamic users display from the javascript. -->
            </div>
          </div>
        </div>
      </div>
    </div>


  </jsp:body>
</t:GlobalTemplate>

