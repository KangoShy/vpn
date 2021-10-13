//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dachui.vpn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.sf.json.xml.XMLSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public JsonUtil() {
    }

    /** @deprecated */
    @Deprecated
    public static Map<String, Object> toMap(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return new HashMap();
        } else {
            Object argsMap;
            try {
                ObjectMapper om = new ObjectMapper();
                argsMap = (Map)om.readValue(jsonStr, Map.class);
            } catch (Exception var5) {
                if (jsonStr.contains("\\")) {
                    try {
                        jsonStr = jsonStr.replace("\\", "\\\\");
                        ObjectMapper om = new ObjectMapper();
                        argsMap = (Map)om.readValue(jsonStr, Map.class);
                    } catch (Exception var4) {
                        logger.error("JSON转Map出错，jsonStr=" + jsonStr, var4);
                        argsMap = new HashMap();
                    }
                } else {
                    logger.error("JSON转Map出错，jsonStr=" + jsonStr, var5);
                    argsMap = new HashMap();
                }
            }

            return (Map)argsMap;
        }
    }

    public static Map<String, Object> toMapFromJsonStr(Object obj) {
        return toMapFromJsonStr(obj.toString());
    }

    public static Map<String, Object> toMapFromJsonStr(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return new HashMap();
        } else {
            Object argsMap;
            try {
                argsMap = (Map)JSONObject.parseObject(jsonStr, Map.class);
            } catch (Exception var5) {
                if (jsonStr.contains("\\")) {
                    try {
                        jsonStr = jsonStr.replace("\\", "\\\\");
                        argsMap = (Map)JSONObject.parseObject(jsonStr, Map.class);
                    } catch (Exception var4) {
                        logger.error("JSON转Map出错，jsonStr=" + jsonStr, var4);
                        argsMap = new HashMap();
                    }
                } else {
                    logger.error("JSON转Map出错，jsonStr=" + jsonStr, var5);
                    argsMap = new HashMap();
                }
            }

            return (Map)argsMap;
        }
    }

    public static Map<String, Object> toMapFromJsonStrSort(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return new HashMap();
        } else {
            Object argsMap;
            try {
                argsMap = (Map)JSONObject.parseObject(jsonStr, Map.class, new Feature[]{Feature.OrderedField});
            } catch (Exception var5) {
                if (jsonStr.contains("\\")) {
                    try {
                        jsonStr = jsonStr.replace("\\", "\\\\");
                        argsMap = (Map)JSONObject.parseObject(jsonStr, Map.class, new Feature[]{Feature.OrderedField});
                    } catch (Exception var4) {
                        logger.error("JSON转Map出错，jsonStr=" + jsonStr, var4);
                        argsMap = new HashMap();
                    }
                } else {
                    logger.error("JSON转Map出错，jsonStr=" + jsonStr, var5);
                    argsMap = new HashMap();
                }
            }

            return (Map)argsMap;
        }
    }

    public static void main(String[] args) {
        String a = "{\"a\":\"a\",\"e\":\"e\",\"b\":\"b\",\"d\":\"d\"}";
        System.err.println(toMapFromJsonStr(a));
    }

    public static String toJsonFromXml(String xml) {
        if (StringUtils.isEmpty(xml)) {
            return null;
        } else {
            try {
                org.json.JSONObject xmlJSONObj = XML.toJSONObject(xml);
                return xmlJSONObj.toString(4);
            } catch (Exception var2) {
                logger.error("XML转JSON出错，xml=" + xml, var2);
                return null;
            }
        }
    }

    public static String toXmlFromJson(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        } else {
            try {
                net.sf.json.JSONObject jobj = net.sf.json.JSONObject.fromObject(jsonStr);
                return (new XMLSerializer()).write(jobj);
            } catch (Exception var2) {
                logger.error("JSON转XML出错，jsonStr=" + jsonStr, var2);
                return null;
            }
        }
    }

    /** @deprecated */
    public static String toJsonString(Object obj) {
        if (obj != null && !"".equals(obj)) {
            ObjectMapper om = new ObjectMapper();

            try {
                return om.writeValueAsString(obj);
            } catch (Exception var3) {
                logger.error("Json转对象出错，obj=" + obj, var3);
                return null;
            }
        } else {
            return "";
        }
    }

    public static String toJsonFromObject(Object obj) {
        if (obj != null && !"".equals(obj)) {
            try {
                return JSON.toJSONString(obj, new SerializerFeature[]{SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse});
            } catch (Exception var2) {
                logger.error("Json转对象出错，obj=" + obj, var2);
                return null;
            }
        } else {
            return "";
        }
    }

    public static String toJsonFromObjectMapNull(Object obj) {
        if (obj != null && !"".equals(obj)) {
            try {
                return JSON.toJSONString(obj, new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse});
            } catch (Exception var2) {
                logger.error("Json转对象出错，obj=" + obj, var2);
                return null;
            }
        } else {
            return "";
        }
    }

    public static String toSqlJsonFromObject(Object obj) {
        String sqlStr = toJsonFromObject(obj);
        return sqlStr == null ? null : sqlStr.replaceAll("\\\\", "\\\\\\\\");
    }

    /** @deprecated */
    @Deprecated
    public static <T> T fromString(String jsonStr, Class<T> c) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        } else {
            ObjectMapper om = new ObjectMapper();

            try {
                om.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return om.readValue(jsonStr, c);
            } catch (Exception var6) {
                if (jsonStr.contains("\\")) {
                    try {
                        jsonStr = jsonStr.replace("\\", "\\\\");
                        om.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        return om.readValue(jsonStr, c);
                    } catch (Exception var5) {
                        logger.error("JSON转对象出错，jsonStr=" + jsonStr, var5);
                    }
                } else {
                    logger.error("JSON转对象出错，jsonStr=" + jsonStr, var6);
                }

                return null;
            }
        }
    }

    public static <T> T toBeanFromStr(String jsonStr, Class<T> c) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        } else {
            try {
                return JSONObject.parseObject(jsonStr, c);
            } catch (Exception var5) {
                if (jsonStr.contains("\\")) {
                    try {
                        jsonStr = jsonStr.replace("\\", "\\\\");
                        return JSONObject.parseObject(jsonStr, c);
                    } catch (Exception var4) {
                        logger.error("JSON转对象出错，jsonStr=" + jsonStr, var4);
                    }
                } else {
                    logger.error("JSON转对象出错，jsonStr=" + jsonStr, var5);
                }

                return null;
            }
        }
    }

    public static <T> T toBeanFromStrSort(String jsonStr, Class<T> c) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        } else {
            try {
                return JSONObject.parseObject(jsonStr, c, new Feature[]{Feature.OrderedField});
            } catch (Exception var5) {
                if (jsonStr.contains("\\")) {
                    try {
                        jsonStr = jsonStr.replace("\\", "\\\\");
                        return JSONObject.parseObject(jsonStr, c, new Feature[]{Feature.OrderedField});
                    } catch (Exception var4) {
                        logger.error("JSON转对象出错，jsonStr=" + jsonStr, var4);
                    }
                } else {
                    logger.error("JSON转对象出错，jsonStr=" + jsonStr, var5);
                }

                return null;
            }
        }
    }

    public static <T> List<T> toList(Object obj, Class<T> c) {
        return obj instanceof String ? toList((String)obj, c) : toList(toJsonFromObject(obj), c);
    }

    public static <T> List<T> toList(String jsonStr, Class<T> c) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        } else {
            ObjectMapper om = new ObjectMapper();
            TypeFactory typeFactory = TypeFactory.defaultInstance();

            try {
                om.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return (List)om.readValue(jsonStr, typeFactory.constructCollectionType(List.class, c));
            } catch (Exception var7) {
                if (jsonStr.contains("\\")) {
                    try {
                        jsonStr = jsonStr.replace("\\", "\\\\");
                        om.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        return (List)om.readValue(jsonStr, typeFactory.constructCollectionType(List.class, c));
                    } catch (Exception var6) {
                        logger.error("JSON转List出错，jsonStr=" + jsonStr, var6);
                    }
                } else {
                    logger.error("JSON转List出错，jsonStr=" + jsonStr, var7);
                }

                return null;
            }
        }
    }

    public static <T> List<T> toMapList(String jsonStr, Class<Map> c) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        } else {
            ObjectMapper om = new ObjectMapper();
            TypeFactory typeFactory = TypeFactory.defaultInstance();

            try {
                om.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return (List)om.readValue(jsonStr, typeFactory.constructCollectionType(List.class, c));
            } catch (Exception var7) {
                if (jsonStr.contains("\\")) {
                    try {
                        jsonStr = jsonStr.replace("\\", "\\\\");
                        om.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        return (List)om.readValue(jsonStr, typeFactory.constructCollectionType(List.class, c));
                    } catch (Exception var6) {
                        logger.error("JSON转List出错，jsonStr=" + jsonStr, var6);
                    }
                } else {
                    logger.error("JSON转List出错，jsonStr=" + jsonStr, var7);
                }

                return null;
            }
        }
    }

    public static <T> List<T> toListFromJsonStr(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return new ArrayList();
        } else {
            try {
                List<T> argsMap = (List)JSONObject.parseObject(jsonStr, List.class);
                return argsMap;
            } catch (Exception var5) {
                if (jsonStr.contains("\\")) {
                    try {
                        jsonStr = jsonStr.replace("\\", "\\\\");
                        return (List)JSONObject.parseObject(jsonStr, List.class);
                    } catch (Exception var4) {
                        logger.error("JSON转List出错，jsonStr=" + jsonStr, var4);
                    }
                } else {
                    logger.error("JSON转List出错，jsonStr=" + jsonStr, var5);
                }

                return new ArrayList();
            }
        }
    }

    public static <T> List<T> toListFromJsonStrSort(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return new ArrayList();
        } else {
            try {
                List<T> argsMap = (List)JSONObject.parseObject(jsonStr, List.class, new Feature[]{Feature.OrderedField});
                return argsMap;
            } catch (Exception var5) {
                if (jsonStr.contains("\\")) {
                    try {
                        jsonStr = jsonStr.replace("\\", "\\\\");
                        return (List)JSONObject.parseObject(jsonStr, List.class, new Feature[]{Feature.OrderedField});
                    } catch (Exception var4) {
                        logger.error("JSON转List出错，jsonStr=" + jsonStr, var4);
                    }
                } else {
                    logger.error("JSON转List出错，jsonStr=" + jsonStr, var5);
                }

                return new ArrayList();
            }
        }
    }
}
