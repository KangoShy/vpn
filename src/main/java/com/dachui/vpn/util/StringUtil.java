//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dachui.vpn.util;

import org.apache.commons.lang.StringUtils;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {
    public static final String EMPTY = "";
    private static final String SPOTS = "·";

    public StringUtil() {
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty() || s.equalsIgnoreCase("null");
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || isEmpty(obj.toString());
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isAllEmpty(String... s) {
        return s == null || s.length == 0 || Arrays.stream(s).allMatch(StringUtil::isEmpty);
    }

    public static boolean isAnyEmpty(String... s) {
        return s == null || s.length == 0 || Arrays.stream(s).anyMatch(StringUtil::isEmpty);
    }

    public static String toString(Object obj) {
        return obj == null ? null : obj.toString().trim();
    }

    public static String toStringNotNull(Object obj) {
        if (obj == null) {
            return "";
        } else {
            String s = obj.toString();
            return isEmpty(s) ? "" : s;
        }
    }

    /** @deprecated */
    @Deprecated
    public static Integer toInteger(Object obj) {
        return obj != null && obj != "" ? Integer.parseInt(obj.toString()) : 0;
    }


    public static String toStrFromDouble(Double d) {
        return d != null && d != 0.0D ? String.format("%.2f", d) : "";
    }

    public static String toStrFromList(List<Object> list, String separator) {
        if (list != null && !list.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Object s = var3.next();
                if (isNotEmpty(toStringNotNull(s))) {
                    sb.append(s.toString());
                    sb.append(separator);
                }
            }

            return sb.length() <= 0 ? "" : sb.substring(0, sb.length() - 1);
        } else {
            return "";
        }
    }

    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String removeBlank(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            return m.replaceAll("");
        }
    }

    public static String removeUndefined(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            str = StringUtils.replace(str, "undefined|", "");
            str = StringUtils.replace(str, "undefined", "");
            return str;
        }
    }

    public static String replaceSpeSymbol(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            str = StringUtils.replace(str, "\r\n", " ");
            str = StringUtils.replace(str, "\u0006", "");
            str = StringUtils.replace(str, "\u0000", "");
            str = StringUtils.replace(str, "\u001c", "");
            str = StringUtils.replace(str, "\u001c", "");
            str = StringUtils.replace(str, "\\u0006", "");
            str = StringUtils.replace(str, "\\u0000", "");
            str = StringUtils.replace(str, "\\u001C", "");
            str = StringUtils.replace(str, "\\u001c", "");
            str = StringUtils.replace(str, "&#8226;", "·");
            str = StringUtils.replace(str, "\\\\u2022", "·");
            str = StringUtils.replace(str, "\\u2022", "·");
            str = StringUtils.replace(str, "•", "·");
            str = StringUtils.replace(str, "u2022", "·");
            return str;
        }
    }

    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();

        for(int i = 0; i < ch.length; ++i) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isChinese(char c) {
        UnicodeBlock ub = UnicodeBlock.of(c);
        return ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == UnicodeBlock.GENERAL_PUNCTUATION;
    }

    public static boolean isConSpeCharacters(String str) {
        String regEx = "[`~!@#$%^&*+=|';'//[//].<>/?~！@#￥%……&*——+|【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static String getRegExStr(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?！￥……（）—\\-［］【】‘；：”“’。，、？　 \\\\～·＠％＾｛＋＼／｜＂＇＜＞－《》＃＿．＊_]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll("").trim();
        }
    }

    public static String convert(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            if (str.contains("ö")) {
                str = str.replace("ö", "&ouml;");
            }

            if (str.contains("ü")) {
                str = str.replace("ü", "&uuml;");
            }

            return str;
        }
    }

    public static String convertUnicode(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            if (str.contains("&ouml;")) {
                str = str.replace("&ouml;", "ö");
            }

            if (str.contains("&uuml;")) {
                str = str.replace("&uuml;", "ü");
            }

            return str;
        }
    }

    public static String replaceSpots(String name) {
        return name != null && !"".equals(name) ? name.trim().replaceAll("((&#8226;)|(&#9642;)|(•)|·|⋅|∙|・|•|●)", "·") : "";
    }

    public static String getNames(String key) {
        if (isEmpty(key)) {
            return "''";
        } else {
            key = key.trim();
            StringBuffer sb = new StringBuffer();
            sb.append("'").append(key).append("'");
            String newKey;
            if (key.contains("(")) {
                newKey = StringUtils.replace(key, "(", "（");
                newKey = StringUtils.replace(newKey, ")", "）");
                sb.append(",'").append(newKey).append("'");
            } else if (key.contains("（")) {
                newKey = StringUtils.replace(key, "（", "(");
                newKey = StringUtils.replace(newKey, "）", ")");
                sb.append(",'").append(newKey).append("'");
            }

            return sb.toString();
        }
    }

    public static String toSqlStrFromList(List<String> list) {
        if (list != null && !list.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                String s = (String)var2.next();
                if (isNotEmpty(s)) {
                    sb.append("'");
                    sb.append(convertSqlValue(s));
                    sb.append("',");
                }
            }

            return sb.length() <= 0 ? "" : sb.substring(0, sb.length() - 1);
        } else {
            return "";
        }
    }

    public static String toSqlStrFromSet(Set<String> list) {
        if (list != null && !list.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                String s = (String)var2.next();
                if (isNotEmpty(s)) {
                    sb.append("'");
                    sb.append(convertSqlValue(s));
                    sb.append("',");
                }
            }

            return sb.length() <= 0 ? "" : sb.substring(0, sb.length() - 1);
        } else {
            return "";
        }
    }

    public static String convertSqlValue(String value) {
        if (null == value) {
            return null;
        } else if (isEmpty(value)) {
            return "";
        } else {
            if (value.contains("'")) {
                value = value.replace("'", "''");
            }

            if (value.endsWith("\\")) {
                char[] arr = value.toCharArray();
                int count = 0;

                for(int i = arr.length; i > 0 && '\\' == arr[i - 1]; --i) {
                    ++count;
                }

                if ((count & 1) != 0) {
                    value = value + "\\";
                }
            }

            return value;
        }
    }

    public static boolean checkObjFieldIsNull(Object obj) {
        boolean flag = false;

        try {
            Field[] var2 = obj.getClass().getDeclaredFields();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Field f = var2[var4];
                if (!"serialVersionUID".equals(f.getName())) {
                    f.setAccessible(true);
                    Object x = f.get(obj);
                    if (x != null && x instanceof String && !"".equals(x)) {
                        flag = true;
                        break;
                    }

                    if (x != null && x instanceof List && !((List)x).isEmpty()) {
                        flag = true;
                        break;
                    }
                }
            }
        } catch (Exception var7) {
            flag = true;
        }

        return !flag;
    }


    public static String halfToFull(String value) {
        if (isEmpty(value)) {
            return "";
        } else {
            char[] cha = value.toCharArray();

            for(int i = 0; i < cha.length; ++i) {
                if (cha[i] == ' ') {
                    cha[i] = 12288;
                } else if (cha[i] < 127) {
                    cha[i] += 'ﻠ';
                }
            }

            return new String(cha);
        }
    }

    public static String divition(double divisor, double dividend) {
        return divition(divisor, dividend, 2);
    }

    public static String divition(double divisor, double dividend, int num) {
        return dividend == 0.0D ? "" : String.format("%." + num + "f", divisor / dividend);
    }


    public static String getNumFromStr(String s) {
        if (isEmpty(s)) {
            return "";
        } else {
            String regEx = "[^0-9.]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(s);
            return m.replaceAll("").trim();
        }
    }

    public static String getMapToString(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        String[] keyArray = (String[])keySet.toArray(new String[0]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < keyArray.length; ++i) {
            if (((String)map.get(keyArray[i])).trim().length() > 0) {
                sb.append(keyArray[i]).append("=").append(((String)map.get(keyArray[i])).trim());
            }

            if (i != keyArray.length - 1) {
                sb.append("&");
            }
        }

        return sb.toString();
    }

}
