package utils;

/**
 * String工具类
 *
 * @author Sunyuejun
 */
public class StringUtil {

    /**
     * 判断一个或多个对象为空
     *
     * @param objects objects
     * @return 只要有一个对象为Blank, 就返回true
     */
    private static boolean isBlank(Object... objects) {
        boolean res = false;
        for (Object object : objects) {
            if (null == object || "Nothing".equals(object.toString().trim()) || "".equals(object.toString().trim()) || "null".equals(object.toString().trim())) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * 一次性判断多个或单个对象不为空。
     *
     * @param objects objects
     * @return 只要有一个元素不为Blank，则返回true
     */
    public static boolean isNotBlank(Object... objects) {
        return !isBlank(objects);
    }

    public static boolean isBlank(String... objects) {
        return isBlank((Object[]) objects);
    }

    public static boolean isNotBlank(String... objects) {
        return !isBlank((Object[]) objects);
    }

    public static boolean isBlank(String str) {
        return isBlank((Object) str);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank((Object) str);
    }

}
