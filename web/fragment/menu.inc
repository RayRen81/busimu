<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--  TODO: encapsulate the menu to taglib --%>

<div id="menu">
<ul class="menu">
  <c:choose>
    <c:when test="${'UNKNOWN' == user.type}">
      <li><a href="#"><span><s:text name="menu.user.activate" /></span></a></li>
    </c:when>
    <c:when test="${'TEACHER' == user.type}">
      <li><a href="<c:url value='/user/home'/>" class="parent"><span><s:text name="menu.user.home" /></span></a>
        <ul>
          <li><a href="#" ><span><s:text name="menu.user.modify.data"/></span></a></li>
          <li><a href="#" ><span><s:text name="menu.user.modify.password"/></span></a></li>
        </ul>
      </li>
      <li><a href="<c:url value='/user/courseManagement'/>" class="parent"><span><s:text name="menu.course.manage.teacher" /></span></a>
        <ul>
          <li><a href="<c:url value='/user/courseCreate'/>" ><span><s:text name="menu.course.manage.teacher.create"/></span></a></li>
          <li><a href="<c:url value='/user/courseManagement'/>" ><span><s:text name="menu.course.manage.teacher.edit"/></span></a></li>
        </ul>
      </li>
      <li><a href="<c:url value='/user/courseProgress'/>"><span><s:text name="menu.decision.process.teacher" /></span></a></li>
      <li><a href="#"><span><s:text name="menu.decision.result.teacher" /></span></a></li>
      <li><a href="<c:url value='/user/studentManagement'/>"><span><s:text name="menu.student.manage" /></span></a></li>
    </c:when>
    <c:when test="${'STUDENT' == user.type}">
      <li><a href="<c:url value='/user/home'/>" class="parent"><span><s:text name="menu.user.home" /></span></a>
        <ul>
          <li><a href="#" ><span><s:text name="menu.user.modify.data"/></span></a></li>
          <li><a href="#" ><span><s:text name="menu.user.modify.password"/></span></a></li>
        </ul>
      </li>
      <li><a href="<c:url value='/user/courseManagement'/>" class="parent"><span><s:text name="menu.course.manage.student" /></span></a>
      </li>
      <li><a href="<c:url value='/user/decisionProcess'/>"><span><s:text name="menu.decision.process.student" /></span></a></li>
      <li><a href="#"><span><s:text name="menu.decision.result.student" /></span></a></li>
    </c:when>
  </c:choose>
</ul>
</div>
<a href="http://apycom.com/" style="display: none">Apycom jQuery Menus</a>
