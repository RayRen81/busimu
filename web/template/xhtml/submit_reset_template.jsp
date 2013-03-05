<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<tr>
  <td>
    <s:submit value="%{parameters.submit}" theme="simple" />
    <s:reset value="%{parameters.reset}" theme="simple" />
  </td>
</tr>
