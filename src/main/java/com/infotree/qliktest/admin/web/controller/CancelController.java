package com.infotree.qliktest.admin.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class CancelController {

	/**
	 * This method is to handle the cancel buttons in all the places
	 */
	@RequestMapping(value="/cancel", method = RequestMethod.GET)
	public String cancelTask(Model model,HttpServletRequest request){
		return "home";
	}
}
