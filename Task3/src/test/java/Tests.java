import com.westonline.Main;
import org.junit.Test;

import java.sql.SQLException;

public class Tests {
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
    //        ---> c. price
    //          ---> b+c

    //2. orders
    //         --->b. time
    //          ---->c. price---->订单详细信息（buy）-->删除一个
    //                                           -->删除多个
    //                                           -->增加一个
    //                                           -->增加多个
    //         ---> b+c

    //IV 删除功能
    //1. item
    //2. orders

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
}
