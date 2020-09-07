package com.jcm001.redirect.service;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcm001.redirect.dto.Endpoint;

@Service
public class EndpointService {
	
	Helper HE = new Helper();
	
	public Endpoint getEndpoint(String code) throws ClassNotFoundException {
		Endpoint endpoint;
		
		try {
			Database db = new Database();
			String query = "CALL JCM001.get_endpoint('" + code + "');";
			ResultSet rs = db.execQuery(query);
			
			if(rs != null && rs.first()) {
				if(rs.getInt(1) == 0) {
					endpoint = new Endpoint(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
					if (endpoint.getQuantity() > endpoint.getTimes()) {
						endpoint = null;
					}
				} else {
					endpoint = null;
				}
				db.EndConnection();
			} else {
				endpoint = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			endpoint = null;
		}
		
		return endpoint;
	}
	
	public JSONObject getResponseJson(@RequestHeader Map<String, String> headers, @RequestParam Map<String, String> params, HttpServletRequest request) {
		JSONObject jo;
		
		try {
			jo = new JSONObject();
			
			jo.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			jo.put("request-uri", (request.getRequestURI() != null ? request.getRequestURI().toString() : ""));
			jo.put("request-url", (request.getRequestURL() != null ? request.getRequestURL().toString() : ""));
			jo.put("method", (request.getMethod() != null ? request.getMethod().toString() : ""));
			jo.put("protocol", (request.getProtocol() != null ? request.getProtocol().toString() : ""));
			jo.put("locale", (request.getLocale() != null ? request.getLocale().toString() : ""));
			
			jo.put("remote-address", (request.getRemoteAddr() != null ? request.getRemoteAddr().toString() : ""));
			jo.put("remote-host", (request.getRemoteHost() != null ? request.getRemoteHost().toString() : ""));
			jo.put("remote-port", request.getRemotePort());
			
			jo.put("local-address", (request.getLocalAddr() != null ? request.getLocalAddr().toString() : ""));
			jo.put("local-name", (request.getLocalName() != null ? request.getLocalName().toString() : ""));
			jo.put("local-port", request.getLocalPort());
			
			if (request.getCookies() != null) {
				JSONObject jo1 = new JSONObject();
				for (int i = 0; i < request.getCookies().length; i++) {
					jo1.put(request.getCookies()[i].getName().toString(), request.getCookies()[i].getValue().toString());
				}
				jo.put("cookies", jo1);
			}
			
			if (headers != null) {
				if (headers.size() > 0) {
					JSONObject jo1 = new JSONObject();
					headers.forEach((key, value) -> {
						jo1.put(key, value);
					});
					jo.put("headers", jo1);
				}
			}
			
			if (params != null) {
				if (params.size() > 0) {
					JSONObject jo1 = new JSONObject();
					params.forEach((key, value) -> {
						jo1.put(key, value);
					});
					jo.put("parameters", jo1);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			jo = null;
		}
		
		return jo;
	}
	
	public void resetService() {
		try {
			Database db = new Database();
			String query = "CALL JCM001.reset_logs();";
			ResultSet rs = db.execQuery(query);
			
			if (rs != null) {
				db.EndConnection();
				HE.BeginLog();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}