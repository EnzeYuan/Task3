package com.westonline;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
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

    public static void updated() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Task3", "root", "123456");
        System.out.println("要修改的类型（order/item）");
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        if("order".equals(type)){
            System.out.println("请输入你要修改的订单对应的编码");

            String id = scanner.nextLine();

            //使用事务
            connection.setAutoCommit(false);
//            System.out.println("请问您要修改orderID吗？ T/F");
//            String ans1 = scanner.nextLine();
//
//            if("T".equals(ans1)){
//                System.out.println("您要修改成？");
//                int ans2 = scanner.nextInt();
//                PreparedStatement preparedStatement1 = connection.prepareStatement("update orders set order_id=? where order_id=?");
//                preparedStatement1.setInt(1, ans2);
//                preparedStatement1.setInt(2, Integer.parseInt(id));
//                preparedStatement1.executeUpdate();
//                preparedStatement1.close();
//                id=Integer.toString(ans2);
//
//            }

            System.out.println("请问您要修改orderTime吗？ T/F");
            String ans2 = scanner.nextLine();
            if("T".equals(ans2)){
                System.out.println("您要修改成？（yyyy-MM-dd HH:mm:ss）");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = null;
                try {
                    parsedDate = dateFormat.parse(scanner.nextLine());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Timestamp timestamp = new Timestamp(parsedDate.getTime());
                PreparedStatement preparedStatement1 = connection.prepareStatement("update orders set time=? where order_id=?");
                preparedStatement1.setTimestamp(1, timestamp);
                preparedStatement1.setInt(2, Integer.parseInt(id));
                preparedStatement1.executeUpdate();
                preparedStatement1.close();
            }



            System.out.println("您是否要修改订单的详细信息，即包含的商品  T/F");
            String ans3 = scanner.nextLine();
            if("T".equals(ans3)){
                System.out.println("A:删除商品B：添加商品（多个商品ID之间用,分开）  A/B");
                String ans4 = scanner.nextLine();

                if("A".equals(ans4)){
                    System.out.println("请输入您要删除的商品 （多个商品ID之间用,分开）");
                    String deletions = scanner.nextLine();
                    String[] deletionsArray = deletions.split(",");
                    for (String s : deletionsArray) {
                        PreparedStatement preparedStatement1 = connection.prepareStatement("delete from buy where order_id=? and item_id=?");
                        preparedStatement1.setInt(1, Integer.parseInt(id));
                        preparedStatement1.setInt(2, Integer.parseInt(s));
                        int i = preparedStatement1.executeUpdate();
                        if (i > 0) {
                            System.out.println(s+"删除成功");
                        }else {
                            System.out.println(s+"删除失败");
                        }
                        preparedStatement1.close();
                    }
                    //更新订单价钱
                    double newPrice = 0;
                    PreparedStatement preparedStatement2 = connection.prepareStatement("select item_id from buy where order_id=?");
                    preparedStatement2.setInt(1, Integer.parseInt(id));
                    ResultSet resultSet = preparedStatement2.executeQuery();
                    while (resultSet.next()) {
                        PreparedStatement preparedStatement3 = connection.prepareStatement("select price from items where item_id=?");
                        preparedStatement3.setInt(1, resultSet.getInt("item_id"));
                        ResultSet resultSet1 = preparedStatement3.executeQuery();
                        while (resultSet1.next()) {
                            newPrice += resultSet1.getDouble("price");
                        }
                    }
                    PreparedStatement preparedStatement4 = connection.prepareStatement("update orders set order_price=? where order_id=?");
                    preparedStatement4.setDouble(1, newPrice);
                    preparedStatement4.setInt(2, Integer.parseInt(id));
                    preparedStatement4.executeUpdate();

                    preparedStatement4.close();
                    preparedStatement2.close();

                }else if("B".equals(ans4)){
                    System.out.println("请输入您要添加的商品 （多个商品ID之间用,分开）");
                    String addition = scanner.nextLine();
                    String[] additionArray = addition.split(",");
                    PreparedStatement preparedStatement1 = connection.prepareStatement("insert into buy values (?,?)");
                    for (String s : additionArray) {
                        preparedStatement1.setInt(1,Integer.parseInt(s) );
                        preparedStatement1.setInt(2, Integer.parseInt(id));
                        int i = preparedStatement1.executeUpdate();
                        if (i > 0) {
                            System.out.println(s+"添加成功");
                        }else {
                            System.out.println(s+"添加失败");
                        }
                    }
                    preparedStatement1.close();

                    //更新订单价钱
                    double newPrice = 0;
                    PreparedStatement preparedStatement2 = connection.prepareStatement("select item_id from buy where order_id=?");
                    preparedStatement2.setInt(1, Integer.parseInt(id));
                    ResultSet resultSet = preparedStatement2.executeQuery();
                    while (resultSet.next()) {
                        PreparedStatement preparedStatement3 = connection.prepareStatement("select price from items where item_id=?");
                        preparedStatement3.setInt(1, resultSet.getInt("item_id"));
                        ResultSet resultSet1 = preparedStatement3.executeQuery();
                        while (resultSet1.next()) {
                            newPrice += resultSet1.getDouble("price");
                        }
                    }
                    PreparedStatement preparedStatement4 = connection.prepareStatement("update orders set order_price=? where order_id=?");
                    preparedStatement4.setDouble(1, newPrice);
                    preparedStatement4.setInt(2, Integer.parseInt(id));
                    preparedStatement4.executeUpdate();
                    preparedStatement4.close();
                    preparedStatement2.close();

                }else{
                    System.out.println("输入的选项无效");
                }
            }


            PreparedStatement preparedStatement = connection.prepareStatement("select order_id,time,order_price from orders where order_id=?");
            preparedStatement.setInt(1,Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int num = resultSet.getInt("order_id");
                String time = resultSet.getString("time");
                double price = resultSet.getDouble("order_price");
                System.out.println("orderID: " + num + "\t" + "Time: " + time + "\t" + "Price: " + price);
            }

            System.out.println("如果符合您要修改的数据请输入“OK“，反之回复no");
            String response = scanner.nextLine().toLowerCase();
            if("no".equals(response)){
                connection.rollback();
            } else if ("ok".equals(response)) {
                connection.commit();
            }else {
                connection.rollback();
            }
            preparedStatement.close();
            connection.close();

        }
        else if ("item".equals(type)) {
            System.out.println("请输入你要修改的商品对应的编码");
            String id = scanner.nextLine();
            //使用事务
            connection.setAutoCommit(false);


            System.out.println("请问您要修改itemName吗？ T/F");
            String ans2 = scanner.nextLine();
            if("T".equals(ans2)){
                System.out.println("您要修改成？");
                String ans3 = scanner.nextLine();
                PreparedStatement preparedStatement1 = connection.prepareStatement("update items set item_name=? where item_id=?");
                preparedStatement1.setString(1, ans3);
                preparedStatement1.setInt(2, Integer.parseInt(id));
                preparedStatement1.executeUpdate();
                preparedStatement1.close();
            }


            System.out.println("请问您要修改itemPrice吗？ T/F");
            String ans = scanner.nextLine();
            if("T".equals(ans)){
                System.out.println("您要修改成？");
                String ans3 = scanner.nextLine();
                PreparedStatement preparedStatement1 = connection.prepareStatement("update items set price=? where item_id=?");
                preparedStatement1.setDouble(1, Double.parseDouble(ans3));
                preparedStatement1.setInt(2, Integer.parseInt(id));
                preparedStatement1.executeUpdate();
                preparedStatement1.close();
            }


            PreparedStatement preparedStatement = connection.prepareStatement("select item_id,item_name,price from items where item_id=?");
            preparedStatement.setInt(1,Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int num = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name");
                double price = resultSet.getDouble("price");
                System.out.println("itemID: "+num+"\t"+"Name: "+itemName+"\t"+"Price: "+price);
            }

            System.out.println("如果符合您要修改的数据请输入“OK“，反之回复no");
            String response = scanner.nextLine().toLowerCase();
            if("no".equals(response)){
                connection.rollback();
            } else if ("ok".equals(response)) {
                connection.commit();
            }else {
                connection.rollback();
            }
            preparedStatement.close();
            connection.close();
            //重新计算包含该商品的订单的价格
            updatePrice(Integer.parseInt(id));
        }
        else {
            System.out.println("您输入的表格不存在");
        }
    }

    public static void deletion() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Task3", "root", "123456");
        System.out.println("要删除的类型（order/item）");
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        if("order".equals(type)){
            System.out.println("请输入你要删除的订单对应的编码");
            String id = scanner.nextLine();
            //使用事务
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("select order_id,time,order_price from orders where order_id=?");
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int num = resultSet.getInt("order_id");
                String time = resultSet.getString("time");
                double price = resultSet.getDouble("order_price");
                System.out.println("orderID: " + num + "\t" + "Time: " + time + "\t" + "Price: " + price);
            }

            resultSet.close();
            preparedStatement.close();

            PreparedStatement preparedStatement1 = connection.prepareStatement("delete from orders where order_id=?");
            preparedStatement1.setInt(1, Integer.parseInt(id));
            preparedStatement1.executeUpdate();

            System.out.println("如果符合您要删除的数据请输入“OK“，反之回复no");
            String response = scanner.nextLine().toLowerCase();
            if("no".equals(response)){
                connection.rollback();
            } else if ("ok".equals(response)) {
                connection.commit();
            }else {
                connection.rollback();
            }

            preparedStatement1.close();
            connection.close();
        }
        else if ("item".equals(type)) {

                System.out.println("请输入你要删除的商品对应的编码");
                String id = scanner.nextLine();
                //使用事务
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement("select item_id,item_name,price from items where item_id=?");
                preparedStatement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int num = resultSet.getInt("item_id");
                    String itemName = resultSet.getString("item_name");
                    double price = resultSet.getDouble("price");
                    System.out.println("itemID: "+num+"\t"+"Name: "+itemName+"\t"+"Price: "+price);
                }

                resultSet.close();
                preparedStatement.close();

                PreparedStatement preparedStatement1 = connection.prepareStatement("delete from items where item_id=?");
                preparedStatement1.setInt(1, Integer.parseInt(id));
                preparedStatement1.executeUpdate();

                System.out.println("如果符合您要删除的数据请输入“OK“，反之回复no");
                String response = scanner.nextLine().toLowerCase();
                if("no".equals(response)){
                    connection.rollback();
                } else if ("ok".equals(response)) {
                    //相关订单价格更新
                    updatePrice(Integer.parseInt(id));
                    connection.commit();
                }else {
                    connection.rollback();
                }

                preparedStatement1.close();
                connection.close();
        }else {
            System.out.println("您输入的类型不存在");
        }

    }

    public static void query() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Task3", "root", "123456");
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入查询的表格 orders/items/buy");
        String tableName = scanner.nextLine();
        if ("items".equals(tableName)) {
            System.out.println("输入all查询全部商品，输入要查询的商品号查询特定商品");
            String id = scanner.nextLine();

            if("all".equals(id)){
                String sql2 = "select item_id, item_name, price from items order by item_id";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int num = resultSet.getInt("item_id");
                    String itemName = resultSet.getString("item_name");
                    double price = resultSet.getDouble("price");
                    System.out.println("itemID: "+num+"\t"+"Name: "+itemName+"\t"+"Price: "+price);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }
            else{
                String sql1 = "select item_id, item_name , price from items where item_id=? order by item_id";
                PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int num = resultSet.getInt("item_id");
                    String itemName = resultSet.getString("item_name");
                    double price = resultSet.getDouble("price");
                    System.out.println("itemID: "+num+"\t"+"Name: "+itemName+"\t"+"Price: "+price);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }


        }

        if ("orders".equals(tableName)) {
            System.out.println("输入all查询全部订单，输入要查询的订单号查询特定订单");
            String id = scanner.nextLine();

            if ("all".equals(id)) {
                String sql2 = "select order_id,time,order_price from orders order by order_id";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int num = resultSet.getInt("order_id");
                    String time = resultSet.getString("time");
                    double price = resultSet.getDouble("order_price");
                    System.out.println("orderID: " + num + "\t" + "Time: " + time + "\t" + "Price: " + price);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }else{
                String sql1 = "select order_id,time,order_price from orders where order_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int num = resultSet.getInt("order_id");
                    String time = resultSet.getString("time");
                    double price = resultSet.getDouble("order_price");
                    System.out.println("orderID: " + num + "\t" + "Time: " + time + "\t" + "Price: " + price);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }


        }

        if("buy".equals(tableName)){

            System.out.println("输入all查询全部订单，输入要查询的订单号查询特定订单详细信息");
            String id = scanner.nextLine();
            if("all".equals(id)){
                String sql = "select item_id,order_id from buy";
                ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
                HashMap<Integer, HashSet<Integer>> detail = new HashMap<>();
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int  itemId = resultSet.getInt("item_id");
                    if(detail.containsKey(orderId)){
                        HashSet<Integer> set = detail.get(orderId);
                        set.add(itemId);
                    }else {
                        detail.put(orderId, new HashSet<>());
                        detail.get(orderId).add(itemId);
                    }
                }
                detail.forEach((k,v)->{
                    System.out.println(k+"包含以下商品");
                    detail.get(k).forEach(a->System.out.print(a+" "));
                    System.out.println();
                    System.out.println("-------------------------");
                });
                resultSet.close();
                connection.close();
            }else {
                String sql = "select item_id,order_id from buy where order_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = preparedStatement.executeQuery();
                HashMap<Integer, HashSet<Integer>> detail = new HashMap<>();
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int  itemId = resultSet.getInt("item_id");
                    if(detail.containsKey(orderId)){
                        HashSet<Integer> set = detail.get(orderId);
                        set.add(itemId);
                    }else {
                        detail.put(orderId, new HashSet<>());
                        detail.get(orderId).add(itemId);
                    }
                }
                detail.forEach((k,v)->{
                    System.out.println(k+"包含以下商品");
                    detail.get(k).forEach(a->System.out.print(a+" "));
                    System.out.println();
                    System.out.println("--------------------------------");
                });
                resultSet.close();
                connection.close();
            }

        }
    }

    public static void insertion() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Task3", "root", "123456");
        //解决注入危机
        String sql1 = "insert into items (item_id,item_name,price)values(?,?,?)";
        String sql2 = "insert into orders (order_id,time,order_price)values(?,?,0)";
        System.out.println("请输入您要插入的类型 items/orders");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        if ("items".equals(line)) {
            System.out.println("请输入商品的编号，名字，价格(一行一个属性）");
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, scanner.nextLine());
            preparedStatement.setString(2, scanner.nextLine());
            preparedStatement.setDouble(3, scanner.nextDouble());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("OK");
            }
            preparedStatement.close();
            connection.close();
        }
        else if ("orders".equals(line)) {
            System.out.println("请输入订单的编号，时间(yyyy-MM-dd HH:mm:ss)    (一行一个属性）");
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            String id = scanner.nextLine();
            preparedStatement.setString(1, id);
            //插入时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = null;
            try {
                parsedDate = dateFormat.parse(scanner.nextLine());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            preparedStatement.setTimestamp(2, timestamp);
            int i = preparedStatement.executeUpdate();

            if (i > 0) {
                System.out.println("OK");
            }

            //插入订单详细信息
            System.out.println("请输入订单包含的商品的ID(分行)，完成请输入finish");
            String sql3 = "insert into buy values (?,?)";
            preparedStatement = connection.prepareStatement(sql3);

            while(true){
                String s=scanner.nextLine();
                if("finish".equals(s)){
                    break;
                }else{
                    preparedStatement.setString(1, s);
                    preparedStatement.setString(2, id);
                    int i1 = preparedStatement.executeUpdate();
                    if (i1 > 0) {
                        System.out.println("OK");
                    }
                }
            }
            //求订单总价值
            String sql4 = "select Round(sum(i.price),2) from items as i,orders as o,buy as b where o.order_id=b.order_id and i.item_id=b.item_id and b.order_id=?";
            preparedStatement = connection.prepareStatement(sql4);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            double price = 0;
            while (resultSet.next()) {
                price = resultSet.getDouble(1);
            }
            //将结果赋值给订单
            String sql5 = "update orders set order_price=? where order_id=?";
            preparedStatement = connection.prepareStatement(sql5);
            preparedStatement.setDouble(1,price);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }
    }

    public static void updatePrice(int itemId) throws SQLException {
        //找出包含该商品的订单
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Task3", "root", "123456");
        connection.setAutoCommit(false);
        String sql = "select order_id from buy where item_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, itemId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int orderId = resultSet.getInt("order_id");
            String sql4 = "select Round(sum(i.price),2) from items as i,orders as o,buy as b where o.order_id=b.order_id and i.item_id=b.item_id and b.order_id=?";
            preparedStatement = connection.prepareStatement(sql4);
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            double price = 0;
            while (resultSet1.next()) {
                price = resultSet1.getDouble(1);
            }

            //将结果赋值给订单
            String sql5 = "update orders set order_price=? where order_id=?";
            preparedStatement = connection.prepareStatement(sql5);
            preparedStatement.setDouble(1,price);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
        }
        connection.commit();
        preparedStatement.close();
        connection.close();
    }


}