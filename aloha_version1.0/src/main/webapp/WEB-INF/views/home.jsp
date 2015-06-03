<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<t:GlobalTemplate>
	<jsp:body>
	<div class="container-main pad-10">
      <div class="bcol-20" >
        <h3>profile info</h3>
      </div>
      <div class="app bcol-60">
        <h1>Scribbles/Wall</h1>
        <h1>
          <P>
            <c:forEach items="${users}" var="element">
              <tr>
                <td>${element}</td>
              </tr>
            </c:forEach>
          </P>
          <a href="login/">Login</a>
          </p>
          </P>
          <a href="friends/index">Friends Module Index</a>
          </p>
        </div>
      <div class="bcol-20">
        <div class="sidebar">
          <div class="follower-container">
            <div class="block">
             <div class="follower-title">
                <h3>Invite Friends</h3>
             </div>Texbox and invite button
            </div>
          <div class="follower-container">
            <div class="block">
              <div class="follower-title">
                <h3>Friend requests</h3>
              </div>
              Request 1  </br>
              Request 2	 </br>
              Request 3  </br>
            </div>
          </div>
          <div class="follower-container">
            <div class="block">
              <div class="follower-title">
                <h3>online friends</h3>
              </div>
              online friend 1  </br>
              online friend 2	 </br>
              online friend 3  </br>
            </div>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>
</t:GlobalTemplate>

