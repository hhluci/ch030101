package com.bee.sample.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee.sample.conf.ApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class ErrorController extends AbstractErrorController{

	Log log = LogFactory.getLog(ErrorController.class);
	@Autowired
	ObjectMapper objectMapper;
	
	public ErrorController() {
		super(new DefaultErrorAttributes());
		
	}
	@RequestMapping("/error")  //通过此注解来改变默认的错误处理方法String getErrorPath()
	public ModelAndView getErrorPath(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(request,false));
		
		
		int status = (Integer) model.get("status");
		String message = (String) model.get("message");
		Throwable cause = getCause(request);
		log.info(status+","+message, cause);
		response.setStatus(status);
		String errorMessage = getErrorMessage(cause);
		
		if(!isJsonRequest(request)) {
			ModelAndView view = new ModelAndView("/error.html");
			view.addAllObjects(model);
			view.addObject("errorMessage",errorMessage);
			view.addObject("status",status);
			view.addObject("cause",cause);
			return view;
		}
		else {
			Map error = new HashMap();
			error.put("success",false);
			error.put("errorMessage", errorMessage);
			error.put("message",message);
			writeJson(response,error);
			return null;
		}
		
		
	}
	protected void writeJson(HttpServletResponse response,Map error) {
		try {
			response.getWriter().print("{'success':"+error.get("success")+"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected Throwable getCause(HttpServletRequest request) {
		Throwable error = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if(error !=null) {
			while(error instanceof ServletException && error.getCause() !=null) {
				error = ((ServletException)error).getCause();
			}
		}
		return error;
	}
	protected String getErrorMessage(Throwable ex) {
		if(ex instanceof ApplicationException) {
			return ((ApplicationException)ex).getMessage();
		}
		return "服务器错误,请联系管理员";
	}
	protected boolean isJsonRequest(HttpServletRequest request) {
		String requestUri = (String)request.getAttribute("javax.servlet.error.request_uri");
		if((requestUri != null && requestUri.endsWith(".json")) 
				|| request.getHeader("Accept").contains("application/json")){
			return true;
		}
      return false;
	}
	
	@Override
	public String getErrorPath() {

		return null;
	}
}
