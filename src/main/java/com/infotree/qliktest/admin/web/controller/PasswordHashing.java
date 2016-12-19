/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordHashing {
	private static final Logger LOG = LoggerFactory.getLogger(PasswordHashing.class);
	/**
	 * This method is to encrypt the password by using the MD5 algorithm
	 */
	   public static String cryptWithMD5(String input){
		   String result=null;
		try {
			result = input;
			    if(input != null) {
			        MessageDigest md = null;
					try {
						md = MessageDigest.getInstance("MD5");
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} //or "SHA-1"
			        md.update(input.getBytes());
			        BigInteger hash = new BigInteger(1, md.digest());
			        result = hash.toString(16);
			        while(result.length() < 32) { //40 for SHA-1
			            result = "0" + result;
			        }
			    }
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		    return result;
	   }
}
