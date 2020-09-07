package com.jcm001.redirect.service;

import java.io.File;
import java.io.FileWriter;

public class Helper {
	
	private String LOG_PATH;
	
	public Helper() {
		LOG_PATH = "/var/log/JCM001/";
	}
	
	public void Log(String text) throws ClassNotFoundException {
		try {
			String path = LOG_PATH;
			File dir = new File(path);
			
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			String filename = path + "clicks.json";
			FileWriter fw = new FileWriter(filename, false);
			fw.write(text);
			fw.write("\r\n");
			fw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void BeginLog() throws ClassNotFoundException {
        try {
        	String ruta = LOG_PATH;
            File directory = new File(ruta);
            File[] fList = directory.listFiles();
            
            if (fList != null) {
                for (File file : fList) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
