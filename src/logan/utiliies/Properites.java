/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.utiliies;

import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class Properites {
    public static HashMap<String,String> patternMap=new HashMap<String,String>();
    
    static{
    patternMap.put("SystemLog", "");
    patternMap.put("AppLog", "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3}) \\[(.*)\\] ([^ ]*) ([^ ]*) - (.*)$");
    patternMap.put("CustomLog", "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3}) \\[(.*)\\] ([^ ]*) ([^ ]*) - (.*)$");


     
    }
}
