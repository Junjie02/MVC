package chapter3.helper;


import chapter3.annotation.Action;
import chapter3.bean.Handler;
import chapter3.bean.Request;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 */
public final class ControllerHelper {

    /**
     * 用于存放请求与处理器的映射关系（简称 Action Map）
     */
    private static final Map<Request, Handler> ACTION_MAP=new HashMap<Request, Handler>();

    static{
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet=ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)){
            //遍历这些Controller类
            for (Class<?> controllerClass :
                    controllerClassSet) {
                //获取Controller类中定义的方法
                Method[] methods=controllerClass.getMethods();
                if (ArrayUtil.isNotEmpty(methods)){
                    //遍历Controller类中的方法
                    for (Method method :
                            methods) {
                        //判断当前方法是否带有Action注释
                        if (method.isAnnotationPresent(Action.class)){
                            //从Action注释中或i去URL映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping=action.value();
                            //验证URL映射规则
                            if (mapping.matches("\\w+:/\\w*")){
                                String[] array=mapping.split(":");
                                if(ArrayUtil.isNotEmpty(array) && array.length==2){
                                    //获取请求路径与请求方法
                                    String requestMethod=array[0];
                                    String requestPath=array[1];
                                    Request request=new Request(requestMethod,requestPath);
                                    Handler handler=new Handler(controllerClass,method);
                                    //初始化Action Map
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     */
    public static Handler getHandler(String requesteMethod,String requestPath){
        Request request=new Request(requesteMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}
