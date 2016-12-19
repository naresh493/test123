/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class MyLogOutHandler implements LogoutHandler {

@Override
public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    System.out.println("logout!");

    }
}