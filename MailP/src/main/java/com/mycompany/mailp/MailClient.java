/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mailp;

import com.mycompany.util.functions.MailHelper;
import com.mycompany.util.functions.StringHandler;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

/**
 *
 * @author Rastislav
 */
public class MailClient {

    private PropertiesHandler propertiesHandler = new PropertiesHandler();
    private Properties config = propertiesHandler.readProperties();
    private String host = config.getProperty("host");
    private String user = config.getProperty("user");
    private String password = config.getProperty("password");
    private SearchTerm searchTerm = null;
    private ExcelHandler excelHandler = new ExcelHandler();
    private StringHandler stringHandler = new StringHandler();
    private MailHelper mailHelper = new MailHelper();

    public void readImap(String dateFrom, String dateTo, String keywordStrings, String exportName, String emailFolder) {
        List<String> keywords = new ArrayList();
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
        String dt = dateFrom;
        List<ArrayList<String>> export = new ArrayList();

        Date dTo = null;

        if (dateTo == "" || dateTo == null) {
            //
            System.out.println("Neni zadano datum OD");
        } else {
            try {
                dTo = df1.parse(dateTo);
                SearchTerm olderThan = new ReceivedDateTerm(ComparisonTerm.GT, dTo);
                addSearchTerm(olderThan);
            } catch (ParseException ex) {
                Logger.getLogger(MailClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        java.util.Date dDate = null;
        try {
            dDate = df1.parse(dt);
        } catch (ParseException ex) {
            Logger.getLogger(MailClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.GT, dDate);

        DictionaryHandler dictHandler = new DictionaryHandler();
        keywords = stringHandler.parseKeywordString(keywordStrings, ";");
//TODO:
        //Slovnik slovnik = new Slovnik(dictHandler.readDictionary());
//        for (String slovo : keywords) {
//            SearchTerm term = new SearchTerm() {
//                public boolean match(Message message) {
//                    try {
//                        String text = "";
//                        String subject = message.getSubject();
//                        Object content = message.getContent();
//                        if (content instanceof String) {
//
//                            text = (String) content;
//
//                            // System.out.println("SENT DATE:" + msg.getSentDate());
//                            // System.out.println("SUBJECT:" + msg.getSubject());
//                            // System.out.println("CONTENT:" + body);
//                        } else if (content instanceof Multipart) {
//                            Multipart mp = (Multipart) content;
//                            BodyPart bp = mp.getBodyPart(0);
//                            text = mailHelper.getText(bp);
//                            // System.out.println("SENT DATE:" + msg.getSentDate());
//                            //System.out.println("SUBJECT:" + msg.getSubject());
//                            //System.out.println("CONTENT:" + bp.getContent());
//                        }
//                        if (subject.contains(slovo) || text.contains(slovo)) {
//
//                            return true;
//                        }
//
//                    } catch (MessagingException ex) {
//                        Logger.getLogger(MailClient.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IOException ex) {
//                        Logger.getLogger(MailClient.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    return false;
//                }
//            };
//            addSearchTerm(term);
//        }
        addSearchTerm(newerThan);
        //SearchTerm andTerm = new AndTerm(searchTerm, newerThan);
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore("imap");
            System.out.println("Oteviram spojeni");
            store.connect(host, user, password);
//            Folder[] f = store.getFolder("Inbox").getFolder("ZZ_Aut");
//            for (Folder fd : f) {
//                System.out.println(">> " + fd.getName());
//            }
            //System.out.println(store.getDefaultFolder().list("*"));
            Folder inbox = null;
            if (emailFolder == "Inbox") {
                inbox = store.getFolder(emailFolder);
                System.out.println("Oteviram slozku" + emailFolder);
            } else {
                inbox = store.getFolder("Inbox").getFolder(emailFolder);
                System.out.println("Oteviram slozku" + emailFolder);
            }
            inbox.open(Folder.READ_ONLY);
            System.out.println("Ctu emaily, prosim pockejte...");
            Message[] msgs = inbox.search(searchTerm);

            int emailCount = 0;
            for (Message msg : msgs) {
                ArrayList<String> email = new ArrayList();
                Address[] in = msg.getFrom();
                String addressString = "";
                for (Address address : in) {
                    //System.out.println("FROM:" + address.toString());
                    //System.out.println(address.toString());
                    if (address.toString().contains("<")) {
                        String result = stringHandler.findBetweenDelimiters(address.toString(), "<", ">").trim();
                        addressString = addressString + result + ";";
                    } else {
                        //email.add(address.toString());
                        addressString = addressString + address.toString() + ";";
                    }
                }
                //System.out.println("Adresy maji znaku: "+addressString.length());
                email.add(addressString);
                String sentDate = null;
                try {
                    sentDate = msg.getSentDate().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (sentDate == null) {
                    sentDate = "";
                }
                email.add(sentDate);

                email.add(msg.getSubject());
                Object content = msg.getContent();
                if (content instanceof String) {

                    String body = (String) content;

                    email.add(body);
                    // System.out.println("SENT DATE:" + msg.getSentDate());
                    // System.out.println("SUBJECT:" + msg.getSubject());
                    // System.out.println("CONTENT:" + body);

                } else if (content instanceof Multipart) {
                    Multipart mp = (Multipart) content;
                    BodyPart bp = mp.getBodyPart(0);

                    email.add(mailHelper.getText(bp));
                    // System.out.println("SENT DATE:" + msg.getSentDate());
                    //System.out.println("SUBJECT:" + msg.getSubject());
                    //System.out.println("CONTENT:" + bp.getContent());

                }
                // Multipart mp = (Multipart) msg.getContent();
                export.add(email);

                emailCount++;
            }
            System.out.println("Exporting " + emailCount + " emails");
            excelHandler.Excel(export, exportName);
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

    private void addSearchTerm(SearchTerm term) {
        if (this.searchTerm != null) {
            this.searchTerm = new AndTerm(this.searchTerm, term);
        } else {
            this.searchTerm = term;
        }
    }

}
