package com.leyou.order.service.impl;

import com.leyou.order.service.OrderStatusService;
import com.leyou.order.utils.JsonUtils;
import com.leyou.order.vo.CommentsParameter;
import com.leyou.order.vo.OrderStatusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 98050
 * @Time: 2018-12-10 23:24
 * @Feature:
 */
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusServiceImpl.class);


    /**
     * 发送延时消息到延时队列中
     *
     * @param orderStatusMessage
     */
    @Override
    public void sendMessage(OrderStatusMessage orderStatusMessage) {
        String json = JsonUtils.serialize(orderStatusMessage);
        MessageProperties properties;
//        自动收货
        if (orderStatusMessage.getType() == 1) {
            // 持久性 non-persistent (1) or persistent (2)
            properties = MessagePropertiesBuilder.newInstance().setExpiration("60000").setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        } else {
//            自动好评
            properties = MessagePropertiesBuilder.newInstance().setExpiration("90000").setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        }

        Message message = MessageBuilder.withBody(json.getBytes()).andProperties(properties).build();
        //发送消息到延时队列 ttl到后由交换机根据路由进行处理
        try {
            this.amqpTemplate.convertAndSend("", "leyou.order.delay.queue", message);
        } catch (Exception e) {
            LOGGER.error("延时消息发送异常，订单号为：id：{}，用户id为：{}", orderStatusMessage.getOrderId(), orderStatusMessage.getUserId(), e);
        }
    }

    /**
     * 将评论发送到消息队列中
     *
     * @param commentsParameterF //
     */
    @Override
    public void sendComments(CommentsParameter commentsParameter) {
        String json = JsonUtils.serialize(commentsParameter);
        try {
//            直接发送给交换机  交换机根据路由进行处理
            this.amqpTemplate.convertAndSend("leyou.comments.exchange", "user.comments", json);
        } catch (Exception e) {
            LOGGER.error("评论消息发送异常，订单id：{}", commentsParameter.getOrderId(), e);
        }
    }
}
