import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;

/**
 *
 * @author Patryk
 */
public class Hash extends Thread
{
    
    public static String hash(String file)
    {
        AbstractChecksum checksum = null;
        try 
        {
            // select an algorithm (md5 in this case)
            checksum = JacksumAPI.getChecksumInstance("ed2k");
            // On some systems you get a better performance for particular
            // algorithms if you select an alternate algorithm (see also option -A)
            // checksum = JacksumAPI.getChecksumInstance("md5", true);
        } catch (NoSuchAlgorithmException nsae) {
            // algorithm doesn't exist
        }
        // updates the checksum with the content of a file
        try {
            checksum.readFile(file);
            return checksum.toString();
        } catch (IOException ioe) {
            // ...
        }
        return "";
    }
    
    public void run()
    {
        Connection conn = DB.initDB(); 
        try 
        {
            Statement st1 = conn.createStatement();
            ResultSet rs;
            rs = st1.executeQuery("SELECT path FROM files WHERE hash ISNULL ORDER BY id;");
            while (rs.next())
            {
                Main.append("Rozpoczyna hashowanie "+rs.getString(1)+":");
                String hash = Hash.hash(rs.getString(1)).substring(0, 32);
                Main.append("   Hash to "+hash);
                try
                {
                    Statement st2 = conn.createStatement();
                    Main.append("   Próba dodania do bazy:");
                    st2.execute("UPDATE files SET hash='"+hash+"' WHERE path='"+rs.getString(1)+"';"); 
                    Main.append("       Dodano do bazy");
                }
                catch (SQLException ex) 
                {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    Main.append("       Nie dodano do bazy zajrzyj do kodu");
                }
            }
            rs.close();
            st1.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main.buttonSet4(false);
        Main.buttonSet3(true);
        return;
    }
    
    
    
}
