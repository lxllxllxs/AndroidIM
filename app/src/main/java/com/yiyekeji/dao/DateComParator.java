package com.yiyekeji.dao;

import com.yiyekeji.utils.DateUtil;

import java.util.Comparator;
import java.util.Date;

public class DateComParator implements Comparator<ChatMessage> {
        public int compare(ChatMessage c1, ChatMessage c2) {
            Date date1 = DateUtil.dateStringToDate(c1.getDate());
            Date date2 = DateUtil.dateStringToDate(c2.getDate());
            assert date2 != null;
            if (date2.before(date1)) {
                return 1;
            } else {
                return -1;
            }
        }
    }