package com.swg.utils;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: swg
 * @create: 2022-06-13 19:05
 **/
public class RocketMQUtil {

    public static void syncSendMsg(DefaultMQProducer producer, Message message)throws Exception{
        SendResult result = producer.send(message);
        System.out.println(result);
    }

    public static void asyncSendMsg(DefaultMQProducer producer,Message message)throws Exception{
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                Logger logger = LoggerFactory.getLogger(RocketMQUtil.class);
                logger.info("异步发送消息成功，消息id：" + sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
