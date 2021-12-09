package com.cx.springboot.util;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenxi
 * @Package:
 * @Description:
 * @create 2021·11
 */
@Component
public class CanalUtil {

    public static Map<String, String> convertToMap(List<CanalEntry.Column> columnsList) {
        Map<String, String> map = new HashMap();
        columnsList.forEach(c ->map.put(c.getName(),c.getValue()));
        return map;
    }

}