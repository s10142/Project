/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patryk
 */
public class FileHandler {
    
    public static void addFile (File file)
    {
        if(file.exists())
        {
            Connection conn = DB.initDB();
            try
            {
                Main.append("Dodawanie "+file.getAbsolutePath()+":");
                Statement st = conn.createStatement();
                st.execute("INSERT INTO files (path,size) VALUES ('"+file.getAbsolutePath()+"','"+file.length()+"')");
                st.close();
                Main.append("     Dodano "+file.getAbsolutePath()+"");
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                Main.append("     Plik już w bazie lub coś poszło nie tak"); 
            }
            try 
            {
                conn.close();
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else Main.append("Plik nie istnieje");
    }
        
    public static void crawl(File file)
    {
        if(file.isDirectory())
           
        {
            File files[]=file.listFiles();
            for(File x : files)
            {
              crawl(x);
            }
        }
        else
        {
            addFile(file);    
        }
    }
}
