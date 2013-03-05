<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="%{fieldErrors['validationCode'] != null}">
<tr>
<td>
<s:fielderror >
  <s:param value="'validationCode'"/>
</s:fielderror>
</td>
</tr>
</s:if>
<tr>
  <td>
    <label><s:text name="validation.code.label"></s:text></label>
  </td>
      <td>
        <s:textfield name="validationCode" theme="simple" />
      </td>
      <td>
        <img src="<s:url action='/icon/genValidationCode'/>" />
      </td>
</tr>
