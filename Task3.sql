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
create database Task3;
use Task3;

create table items(
    item_id integer primary key ,
    item_name varchar(20) not null,
    price double not null
);

create table orders(
    order_id integer primary key ,
    time timestamp not null,
    order_price double not null
);

create table buy(
     item_id integer,
    order_id integer,
    PRIMARY KEY (item_id, order_id),
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE ON UPDATE  CASCADE ,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE  CASCADE
);

select order_id,time,order_price from orders;
select item_id, item_name, price from items;
select  * from buy;



