package com.swg.config;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swg.constant.UserMomentsConstant;
import com.swg.entity.UserFollowing;
import com.swg.entity.UserMoments;
import com.swg.service.IUserFollowingService;
import io.netty.util.internal.StringUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: swg
 * @create: 2022-06-13 16:57
 **/
@Configuration
public class RocketMQConfig {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IUserFollowingService userFollowingService;

    @Value("${rocketmq.name.server.address}")
    private String nameServerAddr;

    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }

    public DefaultMQPushConsumer momentsConsumer() throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS,"*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = list.get(0);
                if (msg == null){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String bodyStr = new String(msg.getBody());
                UserMoments userMoment = JSONUtil.toBean(bodyStr, UserMoments.class);
                Long userId = userMoment.getUserId();
                List<UserFollowing>fanList = userFollowingService.getUserFans(userId);
                for(UserFollowing fan : fanList){
                    String key = "subscribed-" + fan.getUserId();
                    String subscribedListStr = redisTemplate.opsForValue().get(key);
                    List<UserMoments> subscribedList;
                    if(StringUtil.isNullOrEmpty(subscribedListStr)){
                        subscribedList = new ArrayList<>();
                    }else{
                        subscribedList = JSONArray.parseArray(subscribedListStr, UserMoments.class);
                    }
                    subscribedList.add(userMoment);
                    redisTemplate.opsForValue().set(key, JSONObject.toJSONString(subscribedList));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;
    }

    @Bean("danmusProducer")
    public DefaultMQProducer danmusProducer() throws Exception{
        // ????????????????????????Producer
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_DANMUS);
        // ??????NameServer?????????
        producer.setNamesrvAddr(nameServerAddr);
        // ??????Producer??????
        producer.start();
        return producer;
    }

    @Bean("danmusConsumer")
    public DefaultMQPushConsumer danmusConsumer() throws Exception{
        // ??????????????????
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_DANMUS);
        // ??????NameServer?????????
        consumer.setNamesrvAddr(nameServerAddr);
        // ????????????????????????Topic?????????Tag??????????????????????????????
        consumer.subscribe(UserMomentsConstant.TOPIC_DANMUS, "*");
        // ?????????????????????????????????broker?????????????????????
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt msg = msgs.get(0);
                byte[] msgByte = msg.getBody();
                String bodyStr = new String(msgByte);
                JSONObject jsonObject = JSONObject.parseObject(bodyStr);
                String sessionId = jsonObject.getString("sessionId");
                String message = jsonObject.getString("message");
//                WebSocketService webSocketService = WebSocketService.WEBSOCKET_MAP.get(sessionId);
//                if(webSocketService.getSession().isOpen()){
//                    try {
//                        webSocketService.sendMessage(message);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
                // ????????????????????????????????????
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // ?????????????????????
        consumer.start();
        return consumer;
    }
}
