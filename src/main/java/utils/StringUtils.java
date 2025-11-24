/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author admin
 */
public class StringUtils {
    public static boolean isEmpty(String s){
        if(s != null && !s.isEmpty()){
            return false;
        }
        return true;
    }
}
