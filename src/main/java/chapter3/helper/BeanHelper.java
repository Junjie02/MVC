package chapter3.helper;

import chapter3.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * bean 助手类
 */

public final class BeanHelper {

    /**
     * 定义  Bean 映射（用助手类存放Bean类与Bean实例的映射关系）
     */
    private static final Map<Class<?>,Object> BEAN_MAP=new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass :
                beanClassSet) {
            Object obj= ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }
    /**
     * 获取Bean映射
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }
    /**
     * 获取Bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("Can not get bean by class:"+cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

}
