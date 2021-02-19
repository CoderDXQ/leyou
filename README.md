# leyou

## Question:

???添加购物车的url不知道如何拼凑

？？？登录状态下加入购物车没调通 相关信息记录在微信里 还要检查后面其他的增删查改


1. 大部分功能只有查询，尝试把增删改查补全  可能要写mapper.xml或者写mapper接口也行

2. 完成作业中的功能 https://github.com/ohmyray/leyou

3. 搭建一个小型商城项目，重点关注其中的秒杀部分。如果没有秒杀部分，就参考其他工程加上这个功能部分。

4. 未登录的购物车功能是由前端实现的，这里没有做，只实现了登录状态下的购物车功能。


##  项目启动  
相关安装和配置需要自己来完成，部分服务在120机器的CentOS7.6系统的leyou账户上，启动步骤如下：

1. 启动nginx 

   在`/usr/local/etc/nginx`下执行`nginx`
   
2. 启动ES

   在部署ES的机器上的`/home/leyou/elasticsearch/bin`路径下执行`./elasticsearch`

3. 启动Kibana

   在`Kibana的解压文件夹`下启动即可
   
4. 启动数据库

   依次执行`nohup mysqld &`和`mysql -u root -p`

5. 启动服务

   在IDEA中按下`command`+`8`，然后一次启动所有的微服务即可，注意需要先启动Eureka服务，然后启动其他服务
   
6. 启动leyou-portal

   在IDEA的Terminal中执行`live-server --port=9002 `

7. 启动leyou-manage-web

   在IDEA的Terminal中执行`npm run dev`
   
8. 配置好hosts

   切换到root用户然后在`/private/etc`下使用`vim hosts`进行修改。
   添加以下内容：
   
   `127.0.0.1 api.leyou.com`
   
   `127.0.0.1 manage.leyou.com`
    
   `127.0.0.1 www.leyou.com`
    
   `10.108.163.120 image.leyou.com`
    
    然后在`/usr/local/etc/nginx`下执行`nginx -s reload`以重新启动nginx。

9. 启动消息队列RabbitMQ
   
   `service rabbitmq-server start`
   
   `rabbitmq-plugins enable rabbitmq_management`
   
   `service rabbitmq-server restart`
   
10. 启动Redis

    在部署了Redis的机器上使用`redis-server start`启动服务。
    
    或者在`/usr/local/bin`下执行二进制文件。
   
    
 ## 其他
 
 1. 本项目运行过程中所有生成或者存储的文件路径均与源工程同级。
 
 2. 文档地址：https://github.com/ohmyray/leyou
 
 
    

   
   
   
    