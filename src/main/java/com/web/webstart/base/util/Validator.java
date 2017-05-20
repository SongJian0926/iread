package com.web.webstart.base.util;

import java.util.regex.Pattern;

/**
 * @Autor: zhangl
 * @Times: 2015-9-18下午07:37:09
 * 类的说明：验证数据
 **/
public class Validator {
	
	/**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";
 
    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
 
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1[3-9]\\d{9}$";
    
    /**
     * 正则表达式：验证电话号码
     */
    public static final String REGEX_TELEPHONE = "^(?:(?:0\\d{2,3}[\\- ]?[1-9]\\d{6,7})|(?:[48]00[\\- ]?[1-9]\\d{6}))$";
 
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
 
    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
 
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
 
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
 
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    
    /**
     * 正则表达式：验证日期格式
     */
    public static final String REGEX_DATE = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
    
    /**
     * 正则表达式：验证邮编格式
     */
    public static final String REGEX_POSTCODE = "^[1-9]\\d{5}$";
    
    /**
     * 正则表达式：验证数字(整数)
     */
    public static final String REGEX_NUMBER = "^\\d+$";
    
    /**
     * 正则表达式：验证数字(小数)
     */
    public static final String REGEX_DOUBLE = "^(-?\\d+)(\\.\\d+)?$";
 
    /**
     * 校验用户名
     * 
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }
 
    /**
     * 校验密码
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
 
    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
    
    /**
     * 校验电话号码
     * 
     * @param telephone
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isTelephone(String telephone) {
    	return Pattern.matches(REGEX_TELEPHONE, telephone);
    }
 
    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
 
    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }
 
    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
 
    /**
     * 校验URL
     * 
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }
 
    /**
     * 校验IP地址
     * 
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
    
    /**
     * 校验邮编
     * 
     * @param postcode
     * @return
     */
    public static boolean isPostCode(String postcode) {
    	return Pattern.matches(REGEX_POSTCODE, postcode);
    }
    
    /**
     * 校验日期格式
     * 
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
    	return Pattern.matches(REGEX_DATE, date);
    }
    
    /**
     * 校验数字(整数)
     * 
     * @param number
     * @return
     */
    public static boolean isNumber(String number) {
    	return Pattern.matches(REGEX_NUMBER, number);
    }
    
    /**
     * 校验数字(小数)
     * 
     * @param lnumber
     * @return
     */
    public static boolean isDouble(String lnumber) {
    	return Pattern.matches(REGEX_DOUBLE, lnumber);
    }

}
