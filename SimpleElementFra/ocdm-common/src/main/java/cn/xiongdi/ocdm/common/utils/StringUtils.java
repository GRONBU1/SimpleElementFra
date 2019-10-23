package cn.xiongdi.ocdm.common.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * @创建人 汪礼
 * @创建时间 2018-09-12
 * @描述 字符串操作工具类
 */
public class StringUtils {


    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    /**
     * 比较两个字符串是否相等，null和“”等同
     * 
     * @param strA String
     * @param strB String
     * @return boolean (true:相等 false:不相等)
     */
    public static boolean equals(String strA, String strB) {
        if (strA == null || strA.length() == 0) {
            if (strB == null || strB.length() == 0) {
                return true;
            }
            return false;
        }

        return strA.equals(strB);
    }

    /**
     * 判断字符串是否为null或是长度为0
     * 
     * @param str String 字符串
     * @return boolean (true:是  false:否)
     */
    public static boolean isEmptyOrNull(String str) {
        return Boolean.valueOf(str == null || str.length() == 0);
    }

    /**
     * 将传入的字符串数组中的所有字符串依次连接成一个字符串并返回
     * 
     * @param strs 要连接的字符串数组
     * @return 连接后的字符串
     */
    public static String concatStrings(String... strs) {
        if (strs == null || strs.length == 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (String str : strs) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * 去除字符串前后的半角和全角空格
     * 
     * @param str 源字符串
     * @return 去除前后的半角和全角空格后的字符串
     */
    public static String trim(String str) {
        if (isEmptyOrNull(str)) {
            return str;
        }

        int start = 0;
        int len = str.length();
        int end = len;

        while ((start < len) && (str.charAt(start) == ' ' || str.charAt(start) == '　')) {
            start++;
        }

        while ((start < end) && (str.charAt(end - 1) == ' ' || str.charAt(end - 1) == '　')) {
            end--;
        }

        return ((start > 0) || (end < len)) ? str.substring(start, end) : str;
    }

    /**
     * 对象转成字符串
     * 
     * @param obj Object 源对象
     * @return 返回以后的字符串
     */
    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * 用HTML的格式显示多行文字，每一行根据要求截取指定长度的字符(汉、日、韩文字符长度为2), 不区分中英文,如果数字不正好，则少取一个字符位
     * 
     * @param aString 原始字符串
     * @param specialCharsLength 截取长度(汉、日、韩文字一个字符长度为2)
     * @param row 分割显示行数
     * @return 分割处理后的字符串List
     */
    public static String cutString(String aString, int specialCharsLength, int row) {
        List<String> outputStr = subStr(aString, specialCharsLength, row);
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        for (int i = 0; i < outputStr.size(); i++) {
            sb.append(outputStr.get(i));
            sb.append("<br>");
        }
        sb.append("</html>");
        return sb.toString();
    }

    /**
     * 截取一段字符的长度(汉、日、韩文字符长度为2),不区分中英文,如果数字不正好，则少取一个字符位
     * 
     * @param str 原始字符串
     * @param specialCharsLength 截取长度(汉、日、韩文字符长度为2)
     * @param row 分割显示行数
     * @return 分割处理后的字符串List
     */
    private static List<String> subStr(String str, int specialCharsLength, int row) {
        if (str == null || "".equals(str) || specialCharsLength < 1) {
            return null;
        }
        List<String> strList = new ArrayList<String>();
        char[] chars = str.toCharArray();
        int tempLength = 0;
        for (int i = 0; i < row; i++) {
            int charsLength = getCharsLength(chars, specialCharsLength, tempLength);
            strList.add(new String(chars, tempLength, charsLength));
            tempLength = tempLength + charsLength;
            if (tempLength >= str.length()) {
                break;
            }
        }
        return strList;
    }

    /**
     * 获取一段字符的长度，输入长度中汉、日、韩文字符长度为2，输出长度中所有字符均长度为1
     * 
     * @param chars 一段字符
     * @param specialCharsLength 输入长度，汉、日、韩文字符长度为2
     * @param tempLength int
     * @return 输出长度，所有字符均长度为1
     */
    private static int getCharsLength(char[] chars, int specialCharsLength, int tempLength) {
        int count = 0;
        int normalCharsLength = 0;
        for (int i = tempLength; i < chars.length; i++) {
            int specialCharLength = getSpecialCharLength(chars[i]);
            if (count <= specialCharsLength - specialCharLength) {
                count += specialCharLength;
                normalCharsLength++;
            } else {
                break;
            }
        }
        return normalCharsLength;
    }

    /**
     * 获取字符长度：汉、日、韩文字符长度为2，ASCII码等字符长度为1
     * 
     * @param c 字符
     * @return 字符长度
     */
    private static int getSpecialCharLength(char c) {
        if (isLetter(c)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     * 
     * @param c char, 需要判断的字符
     * @return boolean (true:Ascill字符 false:其它字符)
     */
    private static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 判断一个字符串所有字符全是Ascill字符
     * 
     * @param str String, 需要判断的字符
     * @return boolean (true:全是Ascill字符 false:有其它字符)
     */
    public static boolean isLetter(String str) {
        if (!isEmptyOrNull(str)) {
            char[] chars = str.toCharArray();
            for (char c : chars) {
                int k = 0x80;
                if (c / k != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断当前字符str是否包含specialStr(、、：*？“《》|\\/:?\"<>)等特殊字符
     * 
     * @param str String
     * @param specialStr String
     * @return 如果包含了就返回TRUE，否则返回false
     */
    public static boolean isExistSpecialStr(String str, String specialStr) {

        if (!StringUtils.isEmptyOrNull(str) && !StringUtils.isEmptyOrNull(specialStr)) {
            for (int j = 0; j < specialStr.length(); j++) {

                if (str.indexOf(specialStr.charAt(j)) != -1) { // 如果当前字符存在specialStr所包含的字符中
                    return true;
                    // 特殊字符不存在
                } else {
                }
            }
            return false;
        } else {
            return false;
        }
    }


    /**
     * 格式化字符串内的变量
     *
     * @param strVar
     *            String
     * @param args
     *            String[]
     * @return String
     */
    public static String format(String strVar, String[] args) {
        String str = strVar;
        if (str.indexOf("%s") != -1) {
            str = formatDep(str, args);
        }
        return MessageFormat.format(str, (Object[]) args);
    }

    /**
     * 格式化字符串内的变量
     *
     * @param strVar
     *            String
     * @param args
     *            String[]
     * @return String
     */
    private static String formatDep(String strVar, String[] args) {
        String str = strVar;
        if (args == null) {
            return str;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null && str.indexOf("%s") != -1) {
                str = str.replaceFirst("\\%s", args[i]);
            }
        }

        return str;
    }

    /**
     * @deprecated Method format is deprecated
     * @param str
     *            String
     * @param arg
     *            String
     * @return String
     */
    public static String format(String str, String arg) {
        return format(str, new String[] { arg });
    }

    /**
     * @deprecated Method format is deprecated
     * @param str
     *            String
     * @param arg1
     *            String
     * @param arg2
     *            String
     * @return String
     */
    public static String format(String str, String arg1, String arg2) {
        return format(str, new String[] { arg1, arg2 });
    }

    /**
     * @deprecated Method format is deprecated
     * @param str
     *            String
     * @param arg1
     *            String
     * @param arg2
     *            String
     * @param arg3
     *            String
     * @return String
     */
    public static String format(String str, String arg1, String arg2,
                                String arg3) {
        return format(str, new String[] { arg1, arg2, arg3 });
    }

    /**
     * 判断字符串是否为空
     *
     * @param strVar
     *            String
     * @return boolean
     */
    public static boolean isBlank(String strVar) {
        String str = strVar;
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str
     *            String
     * @return boolean
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断是否为中文
     * @param c 字符
     * @return boolean
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 格式化字符串为Java规则命名
     *
     * @param name
     *            String
     * @param firstCharUpperCase
     *            boolean
     * @return String
     */
    public static final String formatJavaName(String name,
                                              boolean firstCharUpperCase) {
        if (name == null || name.length() <= 1) {
            return name;
        }
        StringTokenizer tokenizer = new StringTokenizer(name, "-_");
        StringBuffer tmp = new StringBuffer();
        String token;
        for (; tokenizer.hasMoreTokens(); tmp.append(
                Character.toUpperCase(token.charAt(0))).append(
                token.substring(1))) {
            token = tokenizer.nextToken();
        }

        if (!firstCharUpperCase) {
            String ch = String.valueOf(Character.toLowerCase(tmp.charAt(0)));
            tmp.replace(0, 1, ch);
        }
        return tmp.toString();
    }

    /**
     * 判断是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
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

    /**
     * 格式化字符串为Java规则命名
     *
     * @param name
     *            String
     * @return String
     */
    public static final String formatJavaName(String name) {
        return formatJavaName(name, false);
    }

    public static final String EMPTY_STRING = "";
    public static final char DOT = 46;
    public static final char UNDERSCORE = 95;
    public static final String COMMA_SPACE = ", ";
    public static final String COMMA = ",";
    public static final String OPEN_PAREN = "(";
    public static final String CLOSE_PAREN = ")";
    public static final String EMPTY = "";
}