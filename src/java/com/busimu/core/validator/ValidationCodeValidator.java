package com.busimu.core.validator;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class ValidationCodeValidator extends FieldValidatorSupport {

	private String  realValidationCode;

	private boolean trim = true;

	public String getRealValidationCode() {
		return realValidationCode;
	}

	public void setRealValidationCode(String realValidationCode) {
		this.realValidationCode = realValidationCode;
	}

	public boolean isTrim() {
		return trim;
	}

	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	public void validate(Object object) throws ValidationException {
		Object objValidateCode = getFieldValue(realValidationCode, object);

		String fieldName = getFieldName();
		String fieldValue = (String) getFieldValue(fieldName, object);

		if (fieldValue == null)
			return;

		if (trim)
			fieldValue = fieldValue.trim();

		if (fieldValue.length() == 0)
			return;

		if (!fieldValue.equals(objValidateCode)) {
			addFieldError(fieldName, object);
		}
	}
}
