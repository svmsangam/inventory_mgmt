package com.inventory.core.util;

import com.inventory.web.util.LoggerUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ParseUtls {

    public static boolean isValidMacAddress(String[] deviceMacAddress, String[] resultMacAddress) {
        if (deviceMacAddress == null) {
            LoggerUtil.logException(ParseUtls.class, new Exception("either invalid mac address command or anything else"));
            return false;
        }

        if (resultMacAddress == null) {
            LoggerUtil.logException(ParseUtls.class, new Exception("please provide valid mac address"));
            return false;
        }

        if (deviceMacAddress.length != resultMacAddress.length) {
            LoggerUtil.logException(ParseUtls.class, new Exception("length of device and result of mac address does not matched"));
            return false;
        }

        for (int i = 0; i < resultMacAddress.length; i++) {
            if (!resultMacAddress[i].equals(deviceMacAddress[i])) {
                return false;
            }
        }

        return true;
    }

    public static String getMacAddress() {

        if (isLenux()){
            return getMacAddressOfLenux();
        }else if (isWindow()){
            return getMacAddressOfWindows();
        }else if (isMac()){
            return getMacAddressOfMac();
        }

        return "";
    }

    public static String getMacAddressOfLenux() {
        List<String> mac = ShellUtls.run(getCommandToGetMacAddr());

        if (mac == null) {
            return "";
        }

        for (String str : mac) {
            str = str.replaceAll("\n", "").replaceAll("\r", "");
            if (isNotNull(str) && str.length() == 17) {
                return str;
            }
        }

        return "";
    }

    public static String getMacAddressOfWindows() {
        List<String> mac = ShellUtls.run(getCommandToGetMacAddr());

        if (mac == null) {
            return "";
        }

        return mac.get(2).trim();
       /* for (String str : mac) {
            //write your necessary filter logic to get mac only
        	
            if (isNotNull(str) && str.length() == 17) {
                return str;
            }
        }*/

      /*  return "";*/
    }

    public static String getMacAddressOfMac() {
        List<String> mac = ShellUtls.run(getCommandToGetMacAddr());

        if (mac == null) {
            return "";
        }

        for (String str : mac) {
            //write your necessary filter logic to get mac only
            if (isNotNull(str) && str.length() == 17) {
                return str;
            }
        }

        return "";
    }

    public static List<String> getCommandToGetMacAddr() {

        List<String> command = new ArrayList<>();

        if (isLenux()) {
            String prefix = "/bin/bash";
            String c = "-c";
            String terminalCommand = "ifconfig -a | grep -ioE '([a-z0-9]{2}:){5}..'";
            command.add(prefix);
            command.add(c);
            command.add(terminalCommand);
        } else if (isWindow()) {

            String prefix = "cmd.exe";
            String c = "/C";
            String terminalCommand = "wmic bios get serialnumber";// write the command to filter out mac address only
            command.add(prefix);
            command.add(c);
            command.add(terminalCommand);

        } else if (isMac()) {
            //write command for windows like lenux
        }

        return command;
    }

    public static boolean isLenux() {
        String OS = System.getProperty("os.name").toLowerCase();

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    public static boolean isWindow() {
        String OS = System.getProperty("os.name").toLowerCase();

        return OS.indexOf("win") >= 0;
    }

    public static boolean isMac() {
        String OS = System.getProperty("os.name").toLowerCase();

        return OS.indexOf("mac") >= 0;
    }

    public static boolean isNull(String value) {
        if (value == null) {
            return true;
        } else if (value.isEmpty() || value.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNull(Long value) {
        return value == null;
    }

    public static boolean isNotNull(Long value) {
        return !isNull(value);
    }

    public static boolean isNotNull(String value) {
        return !isNull(value);
    }

    public static boolean isNull(Double value, boolean considerZero) {
        if (value == null) {
            return true;
        } else if (!considerZero && value == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNull(Double value, boolean considerZero) {
        return !isNull(value, considerZero);
    }

    public static Double getDoubleFromString(String value, Double defaultValue) {
        if (isNull(value) || !isNumber(trimString(value))) {
            return defaultValue;
        }

        try {
            return doubleFormatter(Double.parseDouble(trimString(value)));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean isNumber(String str) {

        return isNotNull(str) && (str.trim().matches("[-\\+]?\\d+(\\.\\d+)?") || str.trim().matches("[-\\+]?+(\\.\\d+)?") || /*match a -ve number that ends with (.) */ str.trim().matches("[-\\+]?\\d+(\\.)?"));  //match a number with optional '-' and decimal.;
    }

    public static String trimString(String value) {
        if (value == null) {
            return value;
        }

        return value.trim();
    }

    public static double doubleFormatter(Double value) {

        DecimalFormat df = new DecimalFormat("###.###");

        if (value == null) {
            return 0;
        }

        return Double.parseDouble(df.format(value));

    }

    public static boolean isDuplicateForLong(List<Long> value) {

        if (value == null) {
            return false;
        }

        if (value.size() <= 1) {
            return false;
        }

        return value.stream().sequential().allMatch(new HashSet<>()::add);
    }

    public static Long parseLong(String value) {
        if (isNull(value)) {
            return null;
        }

        if (!isNumber(value)) {
            return null;
        }

        try {
            return Long.parseLong(trimString(value));
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer parseInt(String value) {
        if (isNull(value)) {
            return null;
        }

        if (!isNumber(value)) {
            return null;
        }

        try {
            return Integer.parseInt(trimString(value));
        } catch (Exception e) {
            return null;
        }
    }

    public static Double parseDouble(String value) {
        if (isNull(value)) {
            return null;
        }

        if (!isNumber(value)) {
            return null;
        }

        try {
            return doubleFormatter(Double.parseDouble(trimString(value)));
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Long> getLongList(Long... idList) {
        return new ArrayList<>(Arrays.asList(idList));
    }

    public static List<Object> getObjectList(Object... objects) {
        return new ArrayList<>(Arrays.asList(objects));
    }

    /*   public static List<AbstractDto> getDtoList(AbstractDto... objects){
           return new ArrayList<>(Arrays.asList(objects));
       }
   */
    public static List<String> output(InputStream inputStream) throws IOException {
        List<String> sb = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.add(line + System.getProperty("line.separator"));
            }
        } finally {
            br.close();
        }
        return sb;
    }
}
