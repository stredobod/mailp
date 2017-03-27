/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mailp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rastislav
 */
public class DictionaryHandler {
    
    public String[] readDictionary(){
     String[] slova=null;
     List<String> lines = null;
        try {
           lines = Files.readAllLines(Paths.get("C:/Projects/slovnik.csv"));
        } catch (IOException ex) {
            Logger.getLogger(DictionaryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
     for (String line:lines){
      line = line.trim();
      slova = line.split(",");
     }
     
     return slova;
    }
}
