# 第三次考核

---

## 简介
主要实现了订单管理系统，bonus还没有完成

---

## 功能概述
- 增删改查功能实现
- item，order，buy,之间保证了数据的完整性
- buy存储不同订单包含的商品，因此订单价格也是由商品单价和buy内容决定的

---

## 技术栈
- **编程语言**：Java
- **框架/库**：Maven
- **数据库**：MySql

---


## 使用方法
***运行Main,根据提示语操作***<br>

**部分代码**
```java
public class Main {
    public static void main(String[] args) {
        System.out.println("请输入您要进行的操作 1：查询 2：删除操作 3：更改数据 4：插入数据     请输入选项：1/2/3/4");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        switch (i) {
            case 1: {
                query();
                break;
            }
            case 2: {
                deletion();
                break;
            }
            case 3: {
                updated();
                break;
            }
            case 4: {
                insertion();
                break;
            } default:{
                System.out.println("无效选项");
            }
        }
    }
}
```

## 测试
```java
    //I.测试插入功能
    //1. item 表插入（5 条）
    //2. order 表插入--->buy（订单详细信息）插入（5 条）

    //II. 测试查询功能
    //1. items 表查询--> 全查询
    //              --> 特定查询
    //2. orders 表查询--> 全查询
    //               --> 特定查询
    //3. buy 表查询--> 全查询
    //            --> 特定查询

    //III. 更新功能
    //1. items
    //         ---> b. name
    //         ---> c. price
    //         ---> b+c

    //2. orders
    //         --->b. time
    //         ---->c. price---->订单详细信息（buy）-->删除一个
    //                                           -->删除多个
    //                                           -->增加一个
    //                                           -->增加多个
    //         ---> b+c

    //IV 删除功能
    //1. item
    //2. orders
```
### 运行展示
```java
 @Test
    public void test() throws SQLException {
            //依次输入 items
            //1001 item001 20.5
            //依次输入 items
            //1002 item002 30.5
            //依次输入 items
            //1003 item003 50
            //依次输入 items
            //1004 item004 20.6
            //依次输入 items
            //1005 item005 10
        for (int i = 0; i < 5; i++) {
            Main.insertion();
        }
    }
    @Test
    public void test2() throws SQLException {
        //依次输入 orders
        //101 2020-05-01 20:50:30    1001  1003  1004 finish
        //依次输入 orders
        //102 2023-11-01 22:00:30    1001  1002  1004 1005 finish
        //依次输入 orders
        //103 2020-05-12 05:50:30    1001  1005  1004 finish
        //依次输入 orders
        //104 2020-05-01 21:30:11    1003  1004 finish
        //依次输入 orders
        //105 2022-09-20 15:50:30    1001  1003  1004 finish
        for (int i = 0; i < 5; i++) {
            Main.insertion();
        }
    }
    @Test
    public void test3() throws SQLException {
        //依次输入 items all
        Main.query();
    }
    @Test
    public void test4() throws SQLException {
        //依次输入 items 1001
        Main.query();
    }
    @Test
    public void test5() throws SQLException {
        //依次输入 orders all
        Main.query();
    }
    @Test
    public void test6() throws SQLException {
        //依次输入 orders 101
        Main.query();
    }
    @Test
    public void test7() throws SQLException {
        //依次输入 buy all
        Main.query();
    }
    @Test
    public void test8() throws SQLException {
        //依次输入 buy 101
        Main.query();
    }
    @Test
    public void test9() throws SQLException {
        //依次输入 item 1001 T item006 F OK
        Main.updated();
    }
    @Test
    public void test10() throws SQLException {
        //依次输入 item 1001 F  T  0 OK
        Main.updated();
    }
    @Test
    public void test11() throws SQLException {
        //依次输入 item 1001 T item001 T 20.5 ok
        Main.updated();
    }
    @Test
    public void test12() throws SQLException {
        //依次输入 order 101 T 2025-01-01 01:01:01 F ok
        Main.updated();
    }
    @Test
    public void test13() throws SQLException {
        //依次输入 order 101 F T  A 1001 ok
        Main.updated();
    }
    @Test
    public void test14() throws SQLException {
        //依次输入 order 101 F T A 1003,1004 OK
        Main.updated();
    }
    @Test
    public void test15() throws SQLException {
        //依次输入 order 101 F T B 1001 ok
        Main.updated();
    }
    @Test
    public void test16() throws SQLException {
        //依次输入 order 101 F T B 1003,1004 ok
        Main.updated();
    }
    @Test
    public void test17() throws SQLException {
        //依次输入 order 101 T 2023-05-02 23:59:59 T B 1005 ok
        Main.updated();
    }
    @Test
    public void test18() throws SQLException {
        //依次输入 item 1005 ok
        Main.deletion();
    }
    @Test
    public void test19() throws SQLException {
        //依次输入 order 105 ok
        Main.deletion();
    }