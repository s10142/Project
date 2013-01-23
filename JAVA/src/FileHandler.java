import java.io.File;

/**
 *
 * @author Patryk
 */
public class FileHandler {
    
    public static void addFile (File file)
    {
        if(file.exists())
        {
        	DB.addFile(file);
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
