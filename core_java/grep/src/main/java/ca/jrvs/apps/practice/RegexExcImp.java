package ca.jrvs.apps.practice;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexExcImp implements RegexExc {

  public boolean matchJpeg(String filename) {
    if (filename.contains(".jpg") || filename.contains(".jpeg")) {
      return true;
    } else {
      return false;
    }
  }

  public static final String IP_PATTERN =
      "^(\\d{1,3})\\."+"(\\d{1,3})\\."+"(\\d{1,3})\\."+"(\\d{1,3})$";

  public boolean matchIp(String ip){
    Pattern p = Pattern.compile(IP_PATTERN);
    Matcher m = p.matcher(ip);
    return m.matches();
  }

  public boolean isEmptyLine(String line) {
    int strLen;
    if (line == null || (strLen = line.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if ((!Character.isWhitespace(line.charAt(i)))) {
        return false;
      }
    }
    return true;
  }

  public static void main (String[] args) {
    String file = "hello.jpeg";
    String ip = "192.168.1.100";
    String line = "";

    RegexExcImp regexExcImp = new RegexExcImp();

    if (regexExcImp.matchJpeg(file)){
      System.out.println("The file " + file + " is valid");
    } else{
      System.out.println("The file " + file + " is not valid");
    }

    if (regexExcImp.matchIp(ip)){
      System.out.println("The IP Address " + ip + " is valid");
    } else{
      System.out.println("The IP Address " + ip + " is not valid");
    }

    if (regexExcImp.isEmptyLine(line)){
      System.out.println("This line is empty");
    } else{
      System.out.println("This line is not empty");
    }
  }
}