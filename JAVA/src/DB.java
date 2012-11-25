/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Patryk
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;

public class DB 
{
    static public Connection conn;
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
                    Connection con=DriverManager.getConnection(dbURL, "postgres", "qazqaz");
                    

                    //getWarnings - wyswietla dodatkowe inf i ostzezenia z obiektu Connection
                    SQLWarning warn=con.getWarnings();
                    while(warn!=null)
                    {
                            System.out.println("Stan SQL: "+ warn.getSQLState());
                            System.out.println("Komunikat: "+warn.getMessage());
                            System.out.println("Sprzedawca: "+warn.getErrorCode());
                            System.out.println("");
                            warn=warn.getNextWarning();
                    }
                    //obsluga polonczenia...
                    return con;
            }catch(ClassNotFoundException exc){        System.err.println(exc+". Nie mozna pobrac sterownika.");}
            catch(SQLException e){System.out.println("Nie mozna nawiazac poloczenia z BD "+e);}
            return null;
            
    }
        
    
}
