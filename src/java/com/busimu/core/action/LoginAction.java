package com.busimu.core.action;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.busimu.core.exception.DataValidationErrorCode;
import com.busimu.core.exception.DataValidationException;
import com.busimu.core.model.User;
import com.busimu.core.user.UserManagement;
import com.busimu.core.util.SessionScopeKey;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.ValidationParameter;

public class LoginAction extends ActionSupport implements SessionAware {

	/**
     * 
     */
	private static final long   serialVersionUID = 1L;

	private String              username;

	private String              password;
	
	private String validationCode;

	private Map<String, Object> sessionMap;

	
	public void setUsername(String username) {
		this.username = username;
	}

	@RequiredStringValidator(message = "Please input username", 
			shortCircuit = true,
			key = "login.username.validation.required"
	)
	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@RequiredStringValidator(message = "Please input password", 
			shortCircuit = true,
			key = "login.password.validation.required"
	)
	public String getPassword() {
		return password;
	}
	
	/**
     * @return the validationCode
     */
//	@RequiredStringValidator(
//			message = "Please input validation code", 
//			shortCircuit = true,
//			key = "login.validationCode.validation.required")
//	@CustomValidator(
//          type = "validationCodeValidator",
//          message = "${validationCode} is invalid",
//          key = "login.validationCode.validation.notMatch",
//          parameters =
//          {
//            @ValidationParameter( 
//                name = "realValidationCode", value = "#session.validationCode" )
//          }
//    )
//	TODO: add this in production
    public String getValidationCode() {
    	return validationCode;
    }

	/**
     * @param validationCode the validationCode to set
     */
    public void setValidationCode(String validationCode) {
    	this.validationCode = validationCode;
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

	@Override
	public String execute() throws Exception {

		try {
			User user = UserManagement.getInstance().authenticateUser(username, password);
			sessionMap.put(SessionScopeKey.LOGIN_DATE, new Date());
			sessionMap.put(SessionScopeKey.USER, user);
			return Action.SUCCESS;
		} catch (DataValidationException e) {
			String errorMsg = null;
			switch (e.getErrorCode()) {
				case DataValidationErrorCode.NONE_EXISTED_USER:
					errorMsg = "User\"" + username + "\" is not exists ";
					addFieldError("username", getText("login.username.validation.notExists", new String[]{username}));
					break;
				case DataValidationErrorCode.WRONG_PASSWORD:
					errorMsg = "Wrong password";
					addFieldError("password", getText("login.password.validation.notMatch", errorMsg));
					break;
				default:
					errorMsg = "Authentication failed";
					addFieldError("username", getText("login.internal.error", errorMsg));
			}

		}

		return Action.INPUT;
	}

}
