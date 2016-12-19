package com.infotree.qliktest.admin.selenium;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class TestSeleniumUserExtension {

	public static void main(String[] args) {

		Selenium browser;
		try {
			browser = new DefaultSelenium("localhost", 5003, "*firefox", "http://www.google.com"){
				public void open(String url){
					commandProcessor.doCommand("open", new String[]{url, "true"});
				}
			};
			browser.start();
			browser.windowMaximize();
			browser.open("/");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
