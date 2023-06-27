package main.entity;

public class SearchUserEntity extends User {
    private int order;

    /**
     * 构造函数，目的是为了方便底层的搜索函数，可以直接根据类的信息来处理搜索信息
     *
     * @param userName    用户名,如果为空就说明不查找该项
     * @param password    密码,如果为空就说明不查找该项
     * @param name        真实姓名,如果为空就说明不查找该项
     * @param phoneNumber 电话号码,如果为空就说明不查找该项
     * @param order       排序，如果为负数则返回倒叙，否则默认逆序，
     *                    举例：如果为 -3 则说明根据name进行逆序排序
     */
    public SearchUserEntity(String userName, String password, String name, String phoneNumber, int order) {
        super(userName, password, name, phoneNumber);
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String orderString() {
        String turn = order < 0 ? "desc" : "";
        int temp = Math.abs(order);
        switch (temp) {
            case 1:
                return "username " + turn;
            case 2:
                return "password " + turn;
            case 3:
                return "name " + turn;
            case 4:
                return "phoneNumber " + turn;
        }
        return "";
    }
}
