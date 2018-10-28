/*
 * Copyright (C) 2014 God
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.bkjzs.tp.util.net;

import com.bkjzs.tp.util.JsonUtil;
import com.bkjzs.tp.util.StringUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

class Baidu implements TranslateInter {

    private static final Logger LOG = Logger.getLogger(Baidu.class.getName());

    //private static final String API_KEY = "Tfgcp9Gl062tp4EnXRu5oA9q";
    private static final String API_URL = "https://fanyi.baidu.com/basetrans";

    //private static final String API_URL = "http://fanyi.baidu.com/transapi";
    //private static final int MAX_CHAR = 2000;
    private static final int REPEAT_TIME = 3;

    private static final Map<String, String> PROPERTIES = new HashMap<>();

    public Baidu() {
        PROPERTIES.put("Host", "fanyi.baidu.com");
        PROPERTIES.put("Origin", "http://fanyi.baidu.com");
        PROPERTIES.put("Referer", "http://fanyi.baidu.com/");
        PROPERTIES.put("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36");
        //PROPERTIES.put("Host", "fanyi.baidu.com");

    }

    @Override
    public String[] translate(String src, String from, String to) {
        //判断是否超过GET运行最大字符src
        if (StringUtil.isEmpty(src)) {
            throw new NullPointerException();
        }
        if (StringUtil.isEmpty(from) || StringUtil.isEmpty(to)) {
            throw new IllegalArgumentException("from or to is empty.");
        }
        Map<String, String> params = new HashMap<>(4);
        params.put("query", Translater.simpleHandle(src));
        params.put("from", from);
        params.put("to", to);
        //params.put("client_id", API_KEY);
        try {
            String json = translate(params, REPEAT_TIME);
            return getResultFromMap(JsonUtil.toMap(json));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @SuppressWarnings("InfiniteRecursion")
    private String translate(Map<String, String> params, int repeatTime)
            throws IOException {
        if (repeatTime-- == 1) {
            throw new IOException("网络超时。" + repeatTime);
        }
        String response = null;
        try {
            response = HttpUtil.doPost(API_URL, params, PROPERTIES);
        } catch (IOException ex) {
            LOG.log(Level.WARNING, null, ex);
            return translate(params, repeatTime);
        }
        return response;
    }

    private String[] getResultFromMap(Map<String, Object> map) {
        List<Map<String, String>> list
                = (List<Map<String, String>>) map.get("trans");
        String[] result = new String[list.size() * 2];
        for (int i = 0; i < list.size(); i++) {
            result[i * 2] = list.get(i).get("src");
            result[i * 2 + 1] = list.get(i).get("dst");
        }
        return result;
    }

}
