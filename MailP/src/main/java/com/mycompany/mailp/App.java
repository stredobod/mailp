/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mailp;

/**
 *
 * @author Rastislav
 */
public class App {
    public static void main(String[] args){
    System.out.println("Initiation of MailP");
    
    MailClient client = new MailClient();
    client.readImap("01/08/16","","Automatická odpověď","EXPORT.xlsx","R");
    }
}
