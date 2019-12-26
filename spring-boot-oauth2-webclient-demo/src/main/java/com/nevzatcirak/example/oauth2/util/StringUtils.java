package com.nevzatcirak.example.oauth2.util;

import java.util.ArrayList;
import java.util.List;

/**
 * String Util Methods
 *
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */
public class StringUtils {

    public static final String DEFAULT_EMPTY_STRING = "";

    public static final String DEFAULT_NULL_STRING = "null";

    /**
     * <pre>
     * StringUtils.isEmpty(null)          	= true
     * StringUtils.isEmpty("")            	= true
     * StringUtils.isEmpty("  ")       	  	= true
     * StringUtils.isEmpty("null")        	= true
     * </pre>
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || DEFAULT_EMPTY_STRING.equals(str.trim()) || DEFAULT_NULL_STRING.equals(str.trim());
    }

    /**
     * <pre>
     * StringUtils.hasLength(null)          = false
     * StringUtils.hasLength("")            = false
     * StringUtils.hasLength("  ")       	= true
     * StringUtils.hasLength(" abc ")       = true
     * </pre>
     *
     * @param str
     * @return
     */
    public static boolean hasLength(String str) {
        return str == null ? false : str.length() > 0;
    }

    /**
     * <pre>
     * StringUtils.trim(null)          		= null
     * StringUtils.trim("")            		= ""
     * StringUtils.trim("     ")       		= ""
     * StringUtils.trim("abc")         		= "abc"
     * StringUtils.trim("  abc  ") 			= "abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * <pre>
     * StringUtils.trim(null)          		= null
     * StringUtils.trim("")            		= ""
     * StringUtils.trim("     ")       		= ""
     * StringUtils.trim("  abc  ")         	= "abc"
     * StringUtils.trim(" a b c ") 			= "abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String trimAll(String str) {
        return str == null ? null : str.trim().replace(" ", "");
    }

    /**
     * <pre>
     * StringUtils.trimToNull(null)          = null
     * StringUtils.trimToNull("")            = null
     * StringUtils.trimToNull("null")        = null
     * StringUtils.trimToNull("     ")       = null
     * StringUtils.trimToNull("abc")         = "abc"
     * StringUtils.trimToNull("  abc  ") 	 = "abc"
     *
     * </pre>
     *
     * @param str
     * @return
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * <pre>
     * StringUtils.trimToEmpty(null)          = ""
     * StringUtils.trimToEmpty("")            = ""
     * StringUtils.trimToEmpty("null")        = ""
     * StringUtils.trimToEmpty("     ")       = ""
     * StringUtils.trimToEmpty("abc")         = "abc"
     * StringUtils.trimToEmpty("  abc  ")     = "abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String trimToEmpty(String str) {
        return str == null ? DEFAULT_EMPTY_STRING : str.trim();
    }

    /**
     * <pre>
     * StringUtils.defaultIfNull(null)   		= ""
     * StringUtils.defaultIfNull("")  			= ""
     * StringUtils.defaultIfNull("  ")  		= "  "
     * StringUtils.defaultIfNull("abc") 		= "abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String defaultIfNull(String str) {
        return defaultIfNull(str, DEFAULT_EMPTY_STRING);
    }

    /**
     * <pre>
     * StringUtils.defaultIfNull(null, "abc")   	= "abc"
     * StringUtils.defaultIfNull("", "abc")  		= ""
     * StringUtils.defaultIfNull("  ", "abc")  		= "  "
     * StringUtils.defaultIfNull("abc", "abc") 		= "abc"
     * </pre>
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static String defaultIfNull(String str, String defaultValue) {
        return str == null ? defaultValue : str;
    }

    /**
     * <p>如果字符串为null则将其转换为""</p>
     *
     * <pre>
     * StringUtils.defaultIfEmpty(null)   		= ""
     * StringUtils.defaultIfEmpty("")  			= ""
     * StringUtils.defaultIfEmpty("  ")  		= "  "
     * StringUtils.defaultIfEmpty("abc") 		= "abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String defaultIfEmpty(String str) {
        return defaultIfEmpty(str, DEFAULT_EMPTY_STRING);
    }

    /**
     * <pre>
     * StringUtils.defaultIfEmpty(null, "abc")   	= "abc"
     * StringUtils.defaultIfEmpty("", "abc")  		= "abc"
     * StringUtils.defaultIfEmpty("null", "abc")  	= "abc"
     * StringUtils.defaultIfEmpty("  ", "abc")  	= "abc"
     * StringUtils.defaultIfEmpty("abc", "abc") 	= "abc"
     * </pre>
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static String defaultIfEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    /**
     * <pre>
     * StringUtils.equals(null, null)   	= true
     * StringUtils.equals(null, "abc")  	= false
     * StringUtils.equals("abc", null)  	= false
     * StringUtils.equals("abc", "abc") 	= true
     * StringUtils.equals("abc", "ABC") 	= false
     * </pre>
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * <pre>
     * StringUtils.equals(null, null)   	= true
     * StringUtils.equals(null, "abc")  	= false
     * StringUtils.equals("abc", null)  	= false
     * StringUtils.equals("abc", "abc") 	= true
     * StringUtils.equals("abc", "ABC") 	= true
     * </pre>
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * <pre>
     * StringUtils.trimEquals(null, null)   	= true
     * StringUtils.trimEquals("", "  ")   		= true
     * StringUtils.trimEquals(null, "abc")  	= false
     * StringUtils.trimEquals("abc", null)  	= false
     * StringUtils.trimEquals(" abc ", "abc") 	= true
     * StringUtils.trimEquals("abc", "ABC") 	= false
     * </pre>
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean trimEquals(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return str1 == str2;
        }
        return str1.trim().equals(str2.trim());
    }

    /**
     * <pre>
     * StringUtils.trimEquals(null, null)   	= true
     * StringUtils.trimEquals("", "  ")   		= true
     * StringUtils.trimEquals(null, "abc")  	= false
     * StringUtils.trimEquals("abc", null)  	= false
     * StringUtils.trimEquals(" abc ", "abc") 	= true
     * StringUtils.trimEquals("abc", "ABC") 	= true
     * </pre>
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean trimEqualsIgnoreCase(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return str1 == str2;
        }
        return str1.trim().equalsIgnoreCase(str2.trim());
    }

    /**
     * <pre>
     * StringUtils.strip(null, *)          = null
     * StringUtils.strip("", *)            = ""
     * StringUtils.strip("abc", null)      = "abc"
     * StringUtils.strip("  abc", null)    = "abc"
     * StringUtils.strip("abc  ", null)    = "abc"
     * StringUtils.strip(" abc ", null)    = "abc"
     * StringUtils.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str
     * @param stripChars
     * @return
     */
    public static String strip(String str, String stripChars) {
        str = stripStart(str, stripChars);
        return stripEnd(str, stripChars);
    }

    /**
     * <pre>
     * StringUtils.stripStart(null, *)          = null
     * StringUtils.stripStart("", *)            = ""
     * StringUtils.stripStart("abc", "")        = "abc"
     * StringUtils.stripStart("abc", null)      = "abc"
     * StringUtils.stripStart("  abc", null)    = "abc"
     * StringUtils.stripStart("abc  ", null)    = "abc  "
     * StringUtils.stripStart(" abc ", null)    = "abc "
     * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while (start != strLen && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
                start++;
            }
        }
        return str.substring(start);
    }

    /**
     * <pre>
     * StringUtils.stripEnd(null, *)          = null
     * StringUtils.stripEnd("", *)            = ""
     * StringUtils.stripEnd("abc", "")        = "abc"
     * StringUtils.stripEnd("abc", null)      = "abc"
     * StringUtils.stripEnd("  abc", null)    = "  abc"
     * StringUtils.stripEnd("abc  ", null)    = "abc"
     * StringUtils.stripEnd(" abc ", null)    = " abc"
     * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
     * StringUtils.stripEnd("120.00", ".0")   = "12"
     * </pre>
     *
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    /**
     * @param targetStr
     * @param appendChar
     * @param length
     * @return
     */
    public static String leftPad(String targetStr, char appendChar, int length) {
        if (targetStr == null) {
            return null;
        }
        int len = targetStr.length();
        while (len++ < length) {
            targetStr = appendChar + targetStr;
        }
        return targetStr;
    }

    /**
     * @param targetStr
     * @param appendChar
     * @param length
     * @return
     */
    public static String rightPad(String targetStr, char appendChar, int length) {
        if (targetStr == null) {
            return null;
        }
        int len = targetStr.length();
        while (len++ < length) {
            targetStr += appendChar;
        }
        return targetStr;
    }

    /**
     * <pre>
     * StringUtils.isAlpha(null)   		= false
     * StringUtils.isAlpha("")     		= false
     * StringUtils.isAlpha("  ")  		= false
     * StringUtils.isAlpha("abc")  		= true
     * StringUtils.isAlpha("ab2c") 		= false
     * StringUtils.isAlpha("ab-c") 		= false
     * </pre>
     *
     * @param str
     * @return
     */
    public static boolean isAlpha(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetter(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * [a-zA-Z0-9]
     *
     * @param str
     * @return
     */
    public static boolean isAlphanumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetterOrDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <pre>
     * StringUtils.reverse(null)  		= null
     * StringUtils.reverse("")    		= ""
     * StringUtils.reverse("bat") 		= "tab"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * @param strs
     * @param delimiter
     * @return
     */
    public static String join(String[] strs, String delimiter) {
        return join(strs, delimiter, null);
    }

    /**
     * @param strs
     * @param delimiter
     * @param wrapChar  [a,b,c] ==> 'a','b','c'
     * @return
     */
    public static String join(String[] strs, String delimiter, Character wrapChar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = strs.length; i < len; i++) {
            sb.append(strs[i]);
            if (i < len - 1) {
                if (wrapChar != null) {
                    sb.append(wrapChar + delimiter + wrapChar);
                } else {
                    sb.append(delimiter);
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param str
     * @param delimiter
     * @return
     */
    public static String[] split(String str, String delimiter) {
        if (str != null) {
            String[] strs = str.split(delimiter);
            List<String> strList = new ArrayList<String>();
            for (String s : strs) {
                strList.add(StringUtils.trim(s));
            }
            return strList.toArray(new String[0]);
        }
        return null;
    }

    /**
     * @param word
     * @return
     */
    public static String firstLetterLowerCase(String word) {
        if (word != null) {
            return word.substring(0, 1).toLowerCase() + word.substring(1);
        }
        return null;
    }

    /**
     * @param word
     * @return
     */
    public static String firstLetterUpperCase(String word) {
        if (word != null) {
            return word.substring(0, 1).toUpperCase() + word.substring(1);
        }
        return null;
    }
}
