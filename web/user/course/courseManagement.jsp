<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="bt" uri="/busimu-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/fragment/common_header.inc" %>
<link type="text/css" href="<c:url value='/css/course-mng.css' />" rel="stylesheet" />
<link type="text/css" href="<c:url value='/js/jQuery/plugins/jquery-ui/themes/base/jquery.ui.all.css' />" rel="stylesheet" />
<script type="text/javascript" src="<c:url value='/js/jQuery/plugins/tree-menu/jquery.jstree.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/course-mng-tree-menu.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/jQuery/plugins/jquery-ui/ui/minified/jquery.ui.core.min.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/jQuery/plugins/jquery-ui/ui/minified/jquery.ui.widget.min.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/jQuery/plugins/jquery-ui/ui/minified/jquery.ui.datepicker.min.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/jQuery/plugins/jquery-ui/ui/i18n/jquery.ui.datepicker-zh-CN.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/course-schedule.js' />" ></script>
<title><s:text name="course.schedule.page.title" /></title>
</head>
<body>
<bt:init/>
<jsp:include page="/fragment/menu.inc"></jsp:include>
<s:if test="#attr.user.type.toString() == 'TEACHER' && #attr.user.createdCampaigns.size() == 0">
		<p><s:text name="course.mng.page.noncreated.campaign" /></p>
</s:if>
<s:elseif test="#attr.user.type.toString() == 'STUDENT' && #attr.user.joinedCampaigns.size() == 0">
		<p><s:text name="course.mng.page.nonjoined.campaign" /></p>
</s:elseif>
<s:else>
<table class="layout-table">
<tr>
<td class="align-top layout-table-left-col">
<div id="left-panel">
<%@ include file="/fragment/course_mng_menu.inc" %>
</div>
</td>
<td class="align-top">
<div id="right-panel">
<%@ include file="/fragment/course_mng.inc" %>
</div>
</td>
</tr>
</table>
</s:else>
</body>
</html>