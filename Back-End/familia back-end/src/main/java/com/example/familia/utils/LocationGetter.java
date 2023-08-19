package com.example.familia.utils;

import org.springframework.stereotype.Component;

@Component
public class LocationGetter {
	
	    public String getCurrentProjectPath() {
	        String path = getClass().getClassLoader().getResource("").getPath();
	        
	        return path;
	    }

}
