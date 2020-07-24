package chapter3;

import chapter3.helper.BeanHelper;
import chapter3.helper.ClassHelper;
import chapter3.helper.ControllerHelper;
import chapter3.helper.IocHelper;
import chapter3.utils.ClassUtil;

/**
 * 加载相应的Helper类
 *
 */

public final class HelperLoader {

    public static void init(){
        Class<?>[] classList={
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls :
                classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
