package fun.toodo;

import static fun.toodo.Common.*;

/**
 * 常用转换工具 -- 字节转换
 * Author：Rui Ma
 */
public class ConversionUtil {
    private static final Integer BYTE_SIZE = 1024;

    public ConversionUtil() {
    }

    /**
     * 字节转换工具
     * @param value 字节大小
     * @param size 进制，1024或者1000
     * @return 保留两位小数带单位的字符串
     */
    public static String byteConversion(Long value, Integer size) {
        String[] units = {"B", "KB", "MB", "GB", "TB", "PB"};
        for (String unit: units) {
            if (value / size < 1 || unit.equals("PB")) {
                return decimalFormat.format(value) + unit;
            }
            value = value / size;
        }
        return null;
    }

    /**
     * 字节转换工具
     * @param value 字节大小
     * @return 保留两位小数带单位的字符串
     */
    public static String byteConversion(Long value) {
        return byteConversion(value, BYTE_SIZE);
    }

    /**
     * 字节转换工具
     * @param value 字节大小
     * @param size 进制，1024或者1000
     * @return 保留两位小数带单位的字符串
     */
    public static String byteConversion(Integer value, Integer size) {
        return byteConversion(Long.valueOf(value), size);
    }

    /**
     * 字节转换工具
     * @param value 字节大小
     * @return 保留两位小数带单位的字符串
     */
    public static String byteConversion(Integer value) {
        return byteConversion(Long.valueOf(value), BYTE_SIZE);
    }
}
