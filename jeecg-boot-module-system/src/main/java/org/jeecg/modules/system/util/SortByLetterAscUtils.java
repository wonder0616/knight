package org.jeecg.modules.system.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;



@Slf4j
public class SortByLetterAscUtils {

    /**
     *
     * @param object 一个集合
     * @param clazz 集合存放的类
     * @param field 要排序的类的字段,字段的getXyz() X要大写
     * @return
     * @throws Exception
     */
    public static Map<String,Object> sortByLetterAsc(Object object, Class clazz, String field) throws Exception {
        if(object instanceof List){
            List<Object> list = (List<Object>) object;
            Class<?> c = Class.forName(clazz.getName());
            Object o = c.newInstance();
            Map<String,Object> map = new HashMap<String,Object>();

            //按拼音首字母表排序
            Map<String, ArrayList<Object>> letterMap = new TreeMap<String,ArrayList<Object>>(new MapSortUtil().getMapKeyComparator());
            if(!list.isEmpty()) {
                for (Object t : list) {
                    o = t;
                    String pinYinFirstLetter = getFieldValue(field, o);
                    if(!letterMap.containsKey(pinYinFirstLetter) && pinYinFirstLetter.matches("[A-Z]")){
                        letterMap.put(pinYinFirstLetter, new ArrayList<Object>());
                    }
                }

                Iterator<Map.Entry<String, ArrayList<Object>>> entries  = letterMap.entrySet().iterator();
                while(entries.hasNext()){
                    Entry<String, ArrayList<Object>> next = entries.next();
                    ArrayList<Object> listTemp = new ArrayList<Object>();
                    for (Object t : list) {
                        o = t;
                        String pinYinFirstLetter = getFieldValue(field, o);
                        if(!StringUtils.isEmpty(pinYinFirstLetter) && next.getKey().equals(pinYinFirstLetter)){
                            listTemp.add(t);
                        }
                    }
                    next.getValue().addAll(listTemp);
                }
                log.debug("对letterMap按照升序排序,#放到最后");
            }

            ArrayList<Object> listTemp2 = new ArrayList<Object>();
            if(!list.isEmpty()){
                for (Object t : list) {
                    o = t;
                    String pinYinFirstLetter = getFieldValue(field, o);
                    if (!pinYinFirstLetter.matches("[A-Z]")) {
                        listTemp2.add(t);
                    }
                }
                if(!listTemp2.isEmpty()){
                    letterMap.put("#", listTemp2);
                }
            }

            //保证map排序后的顺序不
            JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("key", letterMap);
            map.put("data",jsonObject);
            return map;
        }else {
            log.info("转化为list失败");
            return null;
        }

    }

    /**
     * 获取传递字段的属性值
     * @param field 字段名 要大写 比如"Name"
     * @param o
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static String getFieldValue(String field, Object o)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        String name = field.substring(0, 1).toUpperCase() + field.substring(1);
        Method method = o.getClass().getMethod("get"+name);
        //获取字段属性值
        String value = (String) method.invoke(o);
        //取首字母大写返回
        String pinYinFirstLetter = PinYinUtil.getFullSpell(value).substring(0,1).toUpperCase();
        return pinYinFirstLetter;
    }

}
