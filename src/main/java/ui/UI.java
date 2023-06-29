package ui;

import server.PurchaseCar;

import java.util.Scanner;

public class UI {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean flag = false; // 判断是否退出

        do {
            System.out.print("1.登录\n2.注册\n3.退出\n请选择（1~3）：");
            int n0 = input.nextInt();

            if (n0 == 1) {
                /*
                  登录界面
                 */

                if (/*user.name.equal(admin)*/ false) {
                    // 管理员登录

                    //管理员业务
                    do {
                        System.out.println("1.增加车辆\n2.删除车辆\n3.修改车辆信息\n4.查找车辆");
                        int n1 = input.nextInt();
                        switch (n1) {
                            case 1:
                                /*
                                增加车辆
                                 */
                                break;
                            case 2:
                                /*
                                删除车辆
                                 */
                                break;
                            case 3:
                                /*
                                修改车辆信息
                                 */
                                break;
                            case 4:
                                /*
                                查找车辆信息
                                 */
                                break;
                            default:
                                flag = true;
                                break;
                        }

                        System.out.println("是否继续操作?（y/n）:");
                        String s = input.next();
                        if (s.equals("n") || s.equals("N")) flag = true;

                    } while (!flag);

                } else {
                    // 用户登录

                    System.out.print("输入用户名：");
                    String username = input.next();
                    System.out.print("输入密码：");
                    String password = input.next();

                    /*
                    从数据库中查找用户
                     */

                    if (/*登陆成功*/ true) {

                    }


                    /*
                    购买车辆界面
                    显示购买次数较多的汽车信息
                     */

                    PurchaseCar purchase = new PurchaseCar();

                    // 显示车辆信息
                    //ArrayList<Car> carList = purchase.show();

                    /*
                    换页输出
                     */


                    // 用户业务
                    do {
                        System.out.println("1.查询汽车\n2.购买汽车\n3.查询订单\n4.撤销订单\n");
                        int a = input.nextInt();

                        switch (a) {
                            case 1:
                            /*
                            输入要查询的字段查询汽车
                             */
                                break;
                            case 2:
                            /*
                            输入汽车属性购买汽车
                             */

                            /*
                            显示订单
                             */

                             /*
                             支付
                              */

                            /*
                            支付成功向订单数据库中添加订单
                             */

                                break;
                            case 3:
                            /*
                            查询个人订单
                             */
                                break;
                            case 4:
                            /*
                            撤销订单
                             */
                                break;
                            default:
                                flag = true;
                                break;
                        }

                        System.out.println("是否继续操作?（y/n）:");
                        String s = input.next();
                        if (s.equals("n") || s.equals("N")) flag = true;
                    } while (!flag);
                }


            } else if (n0 == 2) {
                /*
                  注册界面
                 */

                System.out.print("输入用户名：");
                String userName = input.next();

                boolean flag1 = false; // 判断密码是否输入一致

                do {
                    System.out.print("输入密码:");
                    String password1 = input.next();
                    System.out.print("再次输入密码：");
                    String password2 = input.next();
                    if (!password2.equals(password1)) {
                        System.out.println("两次输入不一致,请重新输入");
                    } else flag1 = true;
                } while (!flag1);

                System.out.print("输入真实姓名：");
                String name = input.next();

                System.out.print("输入电话号码：");
                String phoneNumber = input.next();

                /*
                向数据库中添加成员
                 */
                if (/*成功*/true) {
                    System.out.println("注册成功，请返回登录");

                } else {
                    System.out.println("注册失败"  /* 失败原因 */);
                }


            } else flag = true;

        } while (!flag);
    }
}
