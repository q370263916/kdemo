package com.ypwh.cron.msg.util;

import java.util.Map;

public interface kafkaProductService {
    public Map<String,Object> sndMesForTemplate(String valueString);

}
