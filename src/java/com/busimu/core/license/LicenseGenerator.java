package com.busimu.core.license;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Required;

import com.busimu.core.dao.UserMngDaoPolicy;
import com.busimu.core.model.License;

public class LicenseGenerator {
	
	private static LicenseGenerator instance;
	
//	private Log log = LogFactory.getLog(LicenseGenerator.class);
	
	private UserMngDaoPolicy policy;

	public void init(){
		instance = this;
	}
	
	/**
     * @param policy the policy to set
     */
	@Required
    public void setUserMngDaoPolicy(UserMngDaoPolicy policy) {
    	this.policy = policy;
    }
	
	public static LicenseGenerator getInstance() {
		return instance;
	}
	
	public StringBuffer getLicenses(int number, License.Type type){
		Set<License> generatedLicenses = new HashSet<License>();
		StringBuffer licenses = new StringBuffer();
		int i = 0;
		while(i < number){
			String licenseCode = generateLicenseCode();
			if(policy.isExistedLicense(licenseCode) || generatedLicenses.contains(licenseCode)) {
				continue;
			}
			generatedLicenses.add(new License(licenseCode, type));
			licenses.append(licenseCode);
			licenses.append("\r\n");
			i++;
		}
		policy.storeLicenses(generatedLicenses);
		return licenses;
	}
	
	private String generateLicenseCode(){
    	String licenseCode = "";
    	for(int i = 0; i < 16; i++) {
    		int index = (int)(Math.random()*62);
    		if(index < 10) {
    			licenseCode += (char)(index+'0');
    		} else if(index < 36){
    			licenseCode += (char)(index+'A'-10);
    		} else if(index < 62){
    			licenseCode += (char)(index+'a'-36);
    		} else {
    			throw new RuntimeException("Wrong Index "+index);
    		}
    		if(i%4 == 3 && i != 15) {
    			licenseCode += '-';
    		}
    	}
    	return licenseCode;
    }
}
