package com.busimu.core.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.busimu.core.util.ImageUtil;
import com.busimu.core.util.SessionScopeKey;
import com.busimu.core.util.ValidationCode;
import com.opensymphony.xwork2.Action;

public class GenerateValidationCodeAction implements Action, SessionAware {
	
	private Map<String, Object> sessionMap;
	
	private byte[] imageBytes;

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}


	@Override
	public String execute() throws Exception {
		char[] randomSequence = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			        'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
			        '6', '7', '8', '9'};
		ValidationCode validationCode = ImageUtil.getValidationCode(60, 20, 4, randomSequence);
		this.imageBytes = validationCode.getImageBytes();
		sessionMap.put(SessionScopeKey.VALIDATION_CODE, validationCode.getCode());
		
		return SUCCESS;
	}

	public byte[] getImageInBytes() {
		return imageBytes;
	}

}
