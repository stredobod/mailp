/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mailp;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Rastislav
 */
public class PropertiesHandler {

   
    
    public Properties readProperties(){
    Properties prop = new Properties();
	InputStream input = null;

	try {
                //TODO: odebrat hardcoded value
		input = new FileInputStream("D:\\config.properties");

		//nacte properties
		prop.load(input);

		

	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        return prop;
    
    }
}
