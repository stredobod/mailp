
import com.mycompany.mailp.DictionaryHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rastislav
 */
public class TestDictionary {
    public void readDictionary(){
     String[] slova = null;
     List<String> lines = null;
        try {
           lines = Files.readAllLines(Paths.get("C:/Projects/slovnik.txt"));
        } catch (IOException ex) {
            Logger.getLogger(DictionaryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
     for (String line:lines){
      line = line.trim();
      slova = line.split(";");
     }
     for (String slovo:slova){
     System.out.println(slovo);
     }
   
    }
}
