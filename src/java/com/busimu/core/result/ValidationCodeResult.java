package com.busimu.core.result;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.busimu.core.action.GenerateValidationCodeAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class ValidationCodeResult implements Result {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		GenerateValidationCodeAction action = (GenerateValidationCodeAction) invocation.getAction();
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		response.setContentType("image/jpeg");
		response.setContentLength(action.getImageInBytes().length);

		ServletOutputStream sos = response.getOutputStream();

		sos.write(action.getImageInBytes());
		sos.close();
	}
}
