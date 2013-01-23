package MAIN;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

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
    	ArrayList<String> l = new ArrayList<String>();
		try {
			l = DB.selHash();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (l.size()!=0)
		{
	        for (int i=0;i<l.size();i++)
	        {
	        	Main.append("Rozpoczyna hashowanie "+l.get(i)+":");
	        	String hash = Hash.hash(l.get(i));
	            Main.append("   Hash to "+hash.substring(0, 32));
	            try {
					DB.addHash(hash.substring(0, 32), l.get(i));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
		else Main.append("Brak plików do hashowania");
        Main.buttonSet4(false);
        Main.buttonSet3(true);
        return;
    }
    
    
    
}
