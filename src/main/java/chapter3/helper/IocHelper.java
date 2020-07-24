package chapter3.helper;


import chapter3.annotation.Inject;
import chapter3.utils.ReflectionUtil;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 */

public final class IocHelper {
    static {
        //获取所有的Bean类与Bean实例之间的映射关系（简称Bean map）
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
//        if (CollectionUtil.isNotEmpty(beanMap)) {
            //遍历BeanMap
            for (Map.Entry<Class<?>, Object> beanEntry :
                    beanMap.entrySet()) {
                //从BeanMap中取出Bean类与Bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取Bean类定义的所有成员变量（简称Bean Field）
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    //遍历bean field
                    for (Field beanField :
                            beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            //在Bean map中获取Bean Field 对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化 BeanField的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }

                }
            }
//        }
    }
}
