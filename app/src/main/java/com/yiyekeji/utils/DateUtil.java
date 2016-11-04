package com.yiyekeji.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lxl on 2016/11/4.
 */
public class DateUtil {
    /**
     * 得到当前时间的字符串表示 格式2010-02-02 12:12:12
     *
     * @return
     */
    public static String getTimeString() {
        return format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期格式化
     *
     * @param c
     * @param pattern
     * @return
     */
    public static String format(Calendar c, String pattern) {
        Calendar calendar = null;
        if (c != null) {
            calendar = c;
        } else {
            calendar = Calendar.getInstance();
        }
        if (pattern == null || pattern.equals("")) {
            pattern = "yyyy年MM月dd日 HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(calendar.getTime());
    }
}
