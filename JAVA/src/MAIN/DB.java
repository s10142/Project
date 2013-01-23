package MAIN;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB 
{
    static String  dbURL="jdbc:postgresql://localhost:5432/postgres";//url z serwerem db

    public static Connection initDB() 
    {
            try{
                    //wczytanie sterownika dla bazy danych postgresql
                    Class d=Class.forName("org.postgresql.Driver");
                    System.out.println("Wczytano: "+d);

                    //umozliwienie rejestracji
                    DriverManager.setLogStream(System.err);//sprawia ze wszystkie komunuikaty bedo zapisywane w standardowym steruminiu bledow

                    //System.out.println("Nawiazanie poloczenia");
                    Connection conn=DriverManager.getConnection(dbURL, "postgres", "qazqaz");
                    

                    //getWarnings - wyswietla dodatkowe inf i ostzezenia z obiektu Connection
                    SQLWarning warn=conn.getWarnings();
                    while(warn!=null)
                    {
                            System.out.println("Stan SQL: "+ warn.getSQLState());
                            System.out.println("Komunikat: "+warn.getMessage());
                            System.out.println("Sprzedawca: "+warn.getErrorCode());
                            System.out.println("");
                            warn=warn.getNextWarning();
                    }
                    //obsluga polonczenia...
                    return conn;
            }catch(ClassNotFoundException exc){        System.err.println(exc+". Nie mozna pobrac sterownika.");}
            catch(SQLException e){System.out.println("Nie mozna nawiazac poloczenia z BD "+e);}
            return null;       
    }
    public static void addFile(File file)
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
            Main.append("     Plik ju¿ w bazie lub coœ posz³o nie tak"); 
        }
        try 
        {
            conn.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static ArrayList<String> selHash() throws SQLException
    {
    	ArrayList<String> l = new ArrayList<String>();
    	Connection conn = DB.initDB();
    	Statement st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery("SELECT path FROM files WHERE hash ISNULL ORDER BY id;");
        while (rs.next()) {l.add(rs.getString(1));}
        rs.close();
        st.close();
        conn.close();
		return l;
    }
    public static void addHash(String hash, String path) throws SQLException
    {
    	Connection conn = DB.initDB();
    	Statement st = conn.createStatement();
        Main.append("   Próba dodania do bazy:");
        st.execute("UPDATE files SET hash='"+hash+"' WHERE path='"+path+"';"); 
        Main.append("       Dodano do bazy");
    }
}
