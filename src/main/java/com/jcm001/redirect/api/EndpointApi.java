package com.jcm001.redirect.api;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jcm001.redirect.dto.Endpoint;
import com.jcm001.redirect.service.EndpointService;
import com.jcm001.redirect.service.Helper;

@RestController
@RequestMapping(path="/link/{code}")
public class EndpointApi {
	
	ArrayList<JSONObject> requests_api = new ArrayList<JSONObject>();
	
	@Autowired
	EndpointService endpointService;
	Helper HE = new Helper();
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView redirectLink(@PathVariable(value="code") String code, @RequestHeader Map<String, String> headers, @RequestParam Map<String, String> params, HttpServletRequest request) throws ClassNotFoundException {
		ModelAndView modelAndView;
		try {
			requests_api.add(endpointService.getResponseJson(headers, params, request));
			HE.Log(requests_api.toString());
			
			Endpoint endpoint = endpointService.getEndpoint(code);
			
			if (endpoint != null) {
				modelAndView = new ModelAndView("redirect:" + endpoint.getLink());
			} else {
				modelAndView = notFound("Link not found");
			}
		} catch (Exception e) {
			modelAndView = null;
		}
		
		return modelAndView;
	}
	
	public ModelAndView notFound(String message) {
		ModelAndView modelAndView;
		
		try {
			modelAndView = new ModelAndView();
			modelAndView.addObject("message", message);
			modelAndView.setViewName("error/404");
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return modelAndView;
	}

}
