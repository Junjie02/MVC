package chapter3.bean;

/**
 * 返回数据对象
 */

public class Data {
    /**
     * 数据模型
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
