package com.dachui.vpn.util;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class FigureUtil {

    private static final List emptyList = Collections.EMPTY_LIST;

    public static List<Object> castListIf(Object obj) {
        if (obj == null) {
            return emptyList;
        }
        if (obj instanceof List) {
            List castList = (List) obj;
            if (CollectionUtils.isEmpty(castList)) {
                return emptyList;
            }else{
                return castList;
            }
        }
        return emptyList;
    }

}
