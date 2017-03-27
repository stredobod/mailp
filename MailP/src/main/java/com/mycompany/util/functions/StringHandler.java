/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.util.functions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rastislav
 */
public class StringHandler {

    public StringHandler() {
    }
    
    public List<String> parseKeywordString(String keywordString, String delimiter) {
        List<String> keywords = new ArrayList();
        String[] keys = keywordString.split(delimiter);
        for (String key : keys) {
            keywords.add(key);
        }
        return keywords;

    }

    public String findBetweenDelimiters(String input, String delimiter1, String delimiter2) {
        String result = input.substring(input.indexOf(delimiter1) + 1, input.indexOf(delimiter2));
        return result;
    }
    
    
}
