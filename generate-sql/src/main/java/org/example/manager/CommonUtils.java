package org.example.manager;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @description: 通用工具类
 * @author: wshbiao
 * @create: 2023-03-08
 */

public class CommonUtils {
    public  static boolean isNullOrEmpty(final Object object) {
        return object == null
                || (object != null && object.getClass().isArray() && ((Object[]) object).length == 0)
                || (object instanceof Map && ((Map) object).size() == 0)
                || (object instanceof Collection && CollectionUtils.isEmpty((Collection) object))
                || (object instanceof String && (object == null || (String)object == ""));
    }
}
