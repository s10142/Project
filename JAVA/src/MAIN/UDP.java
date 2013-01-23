package MAIN;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.anidb.File;
import net.anidb.udp.AniDbException;
import net.anidb.udp.UdpConnection;
import net.anidb.udp.UdpConnectionException;
import net.anidb.udp.UdpConnectionFactory;
import net.anidb.udp.mask.FileMask;
import net.anidb.udp.mask.AnimeFileMask;

public class UDP 
{
	static FileMask fmask = new FileMask(false, false, false, true, false, false, false, false, false, false, false, false, false,
								  false, false, false, false, false, false, true, false, false, false, false, false, true);
	static AnimeFileMask amask = new AnimeFileMask(false, false, false, false, false, false, false, true, false, false, false, false,
											false, false, false, false, false, false, false, false, false, false);
	public static UdpConnection conn()
	{
		UdpConnectionFactory factory;
	    UdpConnection conn = null;
	    
	    factory = UdpConnectionFactory.getInstance();
	    try { conn = factory.connect(1025);} 
	    catch (Throwable t) {t.printStackTrace();} 
	    return conn;
	}
	public static void auth(UdpConnection conn)
	{
		try 
		{
			conn.authenticate("zigi90", "qwertyqaz", "javaproject", 1);
		} catch (Throwable t) {t.printStackTrace();}
	}
	public static void getData(UdpConnection conn, int id) throws UdpConnectionException, AniDbException
	{
		/*Connection con = DB.initDB(); 
        try 
        {
        	Statement st = con.createStatement();
        	ResultSet rs;
        	rs= st.executeQuery("SELECT size, hash FROM files WHERE id="+id+";");
        	rs.next();
        	Main.append("1");
        	*///java.util.List<File> fl = conn.getFiles(rs.getLong(1), rs.getString(2), fmask, amask);
			java.util.List<File> fl = conn.getFiles(63328258, "5604ec83b63aecb28743d1e1c91d3eaf", fmask, amask);
			Main.append("2");
        	File f = fl.get(0);
        	Main.append("3");
        	//st.execute("UPDATE files SET fid="+f.getFileId()+" WHERE id='"+id+"';");
        	//Main.append("4");
        	Main.append("Dzia³a coœ "+f.getAniDbFileName());
        	//Main.append("5");
        /*} catch (SQLException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            Main.append("       Nie dodano do bazy zajrzyj do kodu");
        }*/
        
	}
	
}
