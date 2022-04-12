package fun.toodo.utils;

import java.util.regex.Pattern;

import static cn.hutool.core.text.CharSequenceUtil.isEmpty;
import static fun.toodo.utils.ToodoUtils.*;

public class CommonUtil {
    public CommonUtil() {}

    /**
     * 是否为手机号
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isMobile(CharSequence value) {
        return isMatch(PATTERN_MOBILE, value);
    }

    /**
     * 是否为URL
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isURL(CharSequence value) {
        return isMatch(PATTERN_URL, value);
    }

    /**
     * 是否为HTTP网址
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isURLHttp(CharSequence value) {
        return isMatch(PATTERN_URL_HTTP, value);
    }

    /**
     * 是否为中文
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isChinese(CharSequence value) {
        return isMatch(PATTERN_CHINESES, value);
    }

    /**
     * 是否为IPV4
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isIPV4(CharSequence value) {
        return isMatch(PATTERN_IPV4, value);
    }

    /**
     * 是否为IPV6
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isIPV6(CharSequence value) {
        return isMatch(PATTERN_IPV6, value);
    }

    /**
     * 是否为邮箱
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isEMail(CharSequence value) {
        return isMatch(PATTERN_EMAIL, value);
    }

    /**
     * 是否为UUID
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isUUID(CharSequence value) {
        return isMatch(PATTERN_UUID, value);
    }

    /**
     * 是否为UUID不带'-'的字符串
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isUUIDSimple(CharSequence value) {
        return isMatch(PATTERN_UUID_SIMPLE, value);
    }

    /**
     * 是否为MAC地址
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isMacAddress(CharSequence value) {
        return isMatch(PATTERN_MAC_ADDRESS, value);
    }

    /**
     * 正则验证
     * @param pattern 正则表达式
     * @param value 需要验证的字符串
     * @return 是否匹配
     */
    public static Boolean isMatch(Pattern pattern, CharSequence value) {
        return pattern.matcher(value).matches();
    }

    /**
     * 时间戳转换为相对时间
     */
    public static String getRelativeTime(Long timestamp) {
        if (timestamp == null) {
            return "";
        }
        Long now = System.currentTimeMillis();
        long diff = now - timestamp;
        if (diff < 0) {
            return "";
        }
        if (diff < 1000) {
            return "刚刚";
        }
        if (diff < 60 * 1000) {
            return diff / 1000 + "秒前";
        }
        if (diff < 60 * 60 * 1000) {
            return diff / (60 * 1000) + "分钟前";
        }
        if (diff < 24 * 60 * 60 * 1000) {
            return diff / (60 * 60 * 1000) + "小时前";
        }
        if (diff < 30L * 24 * 60 * 60 * 1000) {
            return diff / (24 * 60 * 60 * 1000) + "天前";
        }
        if (diff < 12L * 30 * 24 * 60 * 60 * 1000) {
            return diff / (30L * 24 * 60 * 60 * 1000) + "月前";
        }
        return diff / (12L * 30 * 24 * 60 * 60 * 1000) + "年前";
    }

    /**
     * 时间戳转换为相对时间
     */
    public static String getRelativeTime(String timestamp) {
        if (isEmpty(timestamp)) {
            return "";
        }
        return getRelativeTime(Long.valueOf(timestamp));
    }
}
