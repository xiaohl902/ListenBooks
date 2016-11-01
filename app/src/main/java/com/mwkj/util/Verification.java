package com.mwkj.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verification {

//    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188

//    联通：130、131、132、152、155、156、185、186

//    电信：133、153、180、189、（1349卫通）

//    那么现在就可以正则匹配测试了，

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    //判断是否是手机号码
    public static boolean isP() {
        String value = "手机号";

        String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";

        Pattern p = Pattern.compile(regExp);

        Matcher m = p.matcher(value);

        return m.find();//boolean
    }

    // 电话验证
    public static boolean phoneFormat(String phoneNumber){
        Pattern pattern = Pattern
                .compile("((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}-(\\d{1,4})$))");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


    String str = "^[1-9][0-9]{5}$";
    /**
     * 判断邮编
     * @return
     */
    public static boolean isZipNO(String zipString){
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }


//    java-正则表达式判断 Email邮箱 是否合法
    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
