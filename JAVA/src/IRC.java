/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.jibble.pircbot.*;
/**
 *
 * @author Patryk
 */
public class IRC extends PircBot
{
    public static IRC bot = new IRC();
    private static Boolean allowed = false;   
    public static void IRC () throws Exception {
        
        // Now start our bot up.
        
        
        bot.setName("AddFileBot");
        
        // Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        bot.connect("irc.synirc.org");

        // Join the #pircbot channel.
        bot.joinChannel("#anidb-spam");
        
    }
    public static void Send(String msg)
    {
        bot.sendMessage("Chii[AR]", msg);
    }
    @Override
    public void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) 
    {
        System.out.println(notice);
    }
    public static Boolean isAllowed()
    {
       return allowed;     
    }
    public static void setAllowed()
    {
        allowed = false;
    }
}
