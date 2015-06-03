<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:GlobalTemplate>
  <jsp:body>
    <div class="container-main pad-20">
      <div class="app">
        <script src="http://feedstack.asia/app/script/members.js"></script>
        <div class="member-body">
          <div class="header-member">
            <i class="fa fa-space fa-group"></i> Friends of ${user.firstName} |      
            <span class="members-count"> </span>
<!--             <span class="member-seach-span"> -->
            <a href="friendsinvite"> invite more friends </a> | 
            <a href="friendsuggestions"> Friend Suggestions </a>
<!--               <input type="text"
							class="member-search" placeholder="search..."
							root="http://feedstack.asia/"> -->
              <!-- </span> -->
          </div>
          <div class="root" root="http://feedstack.asia/" access-token=""></div>
          <div class="entry">
            <div class="member-container">
              <c:forEach items="${users}" var="friend">

                <div class="bcol-member-block">
                  <div class="member-image">
                    <a href="${pageContext.request.contextPath}/profile?userId=${friend.userId}">
                      <!-- <img src="http://feedstack.asia/img/user.jpg" class="member">		</a> -->
                      <img id="profileimage" src="${pageContext.request.contextPath}/displayimage?id=${friend.getUserId()}" class="profile-image">
                  </div>
                  <div class="member-name">
                    <a href="${pageContext.request.contextPath}/profile?userId=${friend.userId}">${friend.firstName} ${friend.lastName}</a>
                  </div>
                </div>
              </c:forEach>
              
              <div class="clear"></div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </jsp:body>
</t:GlobalTemplate>

