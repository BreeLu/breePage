package com.springapp.breepage.core.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class JsonUtils {

    private static Gson gson = new GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(java.sql.Date.class, new JsonSerializer<Date>() {
                @Override
                public JsonElement serialize(java.sql.Date src, Type typeOfSrc, JsonSerializationContext context) {
                    return src == null ? null : new JsonPrimitive(DateUtil.dateFormatter.get().format(src));
                }
            })
            .registerTypeAdapter(java.sql.Date.class, new JsonDeserializer<Date>() {
                @Override
                public java.sql.Date deserialize(JsonElement json, Type typeOfT,
                                                 JsonDeserializationContext context) throws JsonParseException {
                    try {
                        return json == null ? null : new Date(DateUtil.dateFormatter.get().parse(json.getAsString()).getTime());
                    } catch (ParseException e) {
                        throw new RuntimeException("bad date format");
                    }
                }
            })
            .registerTypeAdapter(Timestamp.class, new JsonSerializer<java.sql.Timestamp>() {
                @Override
                public JsonElement serialize(java.sql.Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
                    return src == null ? null : new JsonPrimitive(DateUtil.datetimeFormatter.get().format(src));
                }
            })
            .registerTypeAdapter(java.sql.Timestamp.class, new JsonDeserializer<java.sql.Timestamp>() {
                @Override
                public java.sql.Timestamp deserialize(JsonElement json, Type typeOfT,
                                                      JsonDeserializationContext context) throws JsonParseException {
                    try {
                        return json == null ? null : new Timestamp(DateUtil.datetimeFormatter.get().parse(json.getAsString()).getTime());
                    } catch (ParseException e) {
                        throw new RuntimeException("bad date format");
                    }
                }
            })
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                    if (src == src.longValue())
                        return new JsonPrimitive(src.longValue());
                    return new JsonPrimitive(src);
                }
            })
            .disableHtmlEscaping().create();

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Type type) {
        return (T) gson.fromJson(json, type);
    }

    public static <T> T fromJson(Reader json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(Reader json, Type type) {
        return (T) gson.fromJson(json, type);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static JsonElement toJsonTree(Object obj) {
        return gson.toJsonTree(obj);
    }

    private static String getNumberStringValue(Map objectMap, String property) {
        Object obj = objectMap.get(property);
        if (obj == null) {
            return null;
        }
        String value = String.valueOf(obj);
        if (value.trim().isEmpty()) {
            return null;
        }
        return value;
    }

    /*
     * parse long from json string value
     * return null when value is empty string or null
     * eg.
     * {
     *   "value": "3"
     * }
     */
    public static Boolean parseBoolean(Map objectMap, String property) {
        String value = getString(objectMap, property);
        if (value == null) {
            return null;
        } else {
            return Boolean.valueOf(value);
        }
    }

    /*
     * get long value from json
     * return null when value is null
     * eg.
     * {
     *   "value": 3
     * }
     */
    public static Boolean getBoolean(Map objectMap, String property) {
        Object obj = objectMap.get(property);
        if (obj == null) {
            return null;
        }
        return (Boolean) obj;
    }

    /*
     * parse long from json string value
     * return null when value is empty string or null
     * eg.
     * {
     *   "value": "3"
     * }
     */
    public static Long parseLong(Map objectMap, String property) {
        String value = getNumberStringValue(objectMap, property);
        if (value == null) {
            return null;
        } else {
            return Long.valueOf(value);
        }
    }

    /*
     * get long value from json
     * return null when value is null
     * eg.
     * {
     *   "value": 3
     * }
     */
    public static Long getLong(Map objectMap, String property) {
        Object obj = objectMap.get(property);
        if (obj == null) {
            return null;
        }
        return ((Double) obj).longValue();
    }

    /*
     * parse int from json string value
     * return null when value is empty string or null
     * eg.
     * {
     *   "value": "3"
     * }
     */
    public static Integer parseInt(Map objectMap, String property) {
        String value = getNumberStringValue(objectMap, property);
        if (value == null) {
            return null;
        } else {
            return Integer.valueOf(value);
        }
    }

    /*
     * get int value from json
     * return null when value is null
     * eg.
     * {
     *   "value": 3
     * }
     */
    public static Integer getInt(Map objectMap, String property) {
        Object obj = objectMap.get(property);
        if (obj == null) {
            return null;
        }
        return (new Double(obj.toString())).intValue();
    }

    /*
     * parse double from json string value
     * return null when value is empty string or null
     * eg.
     * {
     *   "value": "3.1415"
     * }
     */
    public static Double parseDouble(Map objectMap, String property) {
        String value = getNumberStringValue(objectMap, property);
        if (value == null) {
            return null;
        } else {
            return Double.valueOf(value);
        }
    }

    /*
     * get double value from json
     * return null when value is null
     * eg.
     * {
     *   "value": 3.1415
     * }
     */
    public static Double getDouble(Map objectMap, String property) {
        Object obj = objectMap.get(property);
        if (obj == null) {
            return null;
        }
        return (Double) obj;
    }

    public static Date parseDate(Map objectMap, String property) {
        Object value = objectMap.get(property);
        //北京接口返回的publish_time有可能返回'0000-00-00'
        //当攻略没有发布日期的时候，就会这么显示
        if (value == null || "0000-00-00".equals(value)) {
            return null;
        }
        SimpleDateFormat dateFormat = DateUtil.dateFormatter.get();
        try {
            return new Date(dateFormat.parse(String.valueOf(value)).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Timestamp parseDateTime(Map objectMap, String property) {
        Object value = objectMap.get(property);
        if (value == null) {
            return null;
        }
        SimpleDateFormat datetimeFormat = DateUtil.datetimeFormatter.get();
        try {
            return new Timestamp(datetimeFormat.parse(String.valueOf(value)).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String getString(Map objectMap, String property) {
        Object value = objectMap.get(property);
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    public static Map getMap(Map objectMap, String property){
        Object value = objectMap.get(property);
        if(value == null){
            return null;
        }
        return (Map)value;
    }
}
