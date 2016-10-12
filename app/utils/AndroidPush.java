package utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;

/**
 * @author xiaojinwei
 * @date   2015-3-5
 * @Description:Baidu-Push-SDK-Java-1.1.2.zip
 */
public class AndroidPush {
	private static final Logger log = LoggerFactory.getLogger(AndroidPush.class);
	public  void sendpush(Long channelId,String userId,String message) {
        /*
         * @brief 推送单播通知(Android Push SDK拦截并解析) message_type = 1 (默认为0)
         */

        // 1. 设置developer平台的ApiKey/SecretKey
        //String apiKey = PropertyUtil.getProperty("pushConfig", "push.android.apiKey");
		String apiKey ="";
        //String secretKey = PropertyUtil.getProperty("pushConfig", "push.android.secretKey");
		String secretKey ="";
        ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);
        // 2. 创建BaiduChannelClient对象实例
        BaiduChannelClient channelClient = new BaiduChannelClient(pair);
        // 3. 若要了解交互细节，请注册YunLogHandler类
        channelClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
            	log.info(event.getMessage());
            }
        });

        try {
            // 4. 创建请求类对象
            // 手机端的ChannelId， 手机端的UserId， 先用1111111111111代替，用户需替换为自己的
            PushUnicastMessageRequest request = new PushUnicastMessageRequest();
            request.setDeviceType(3); // device_type => 1: web 2: pc 3:android
                                      // 4:ios 5:wp
            request.setChannelId(channelId);
            request.setUserId(userId);
            request.setMessageType(1);
            request.setMessage(message);
            // 5. 调用pushMessage接口
            PushUnicastMessageResponse response = channelClient
                    .pushUnicastMessage(request);
            // 6. 认证推送成功
            log.info("pushRegisterInfoList-pushAndroid-success:" + response.getSuccessAmount()+":userId:"+userId);
        } catch (ChannelClientException e) {
            // 处理客户端错误异常
            e.printStackTrace();
        } catch (ChannelServerException e) {
            // 处理服务端错误异常
        	log.error("pushRegisterInfoList-pushAndroid-error"+String.format(
                    "request_id: %d, error_code: %d, error_message: %s",
                    e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
        }

    }
	
	public static void main(String[] args){
		AndroidPush send=new AndroidPush();
		Long channelId=4561520892395290272L;
        String userId="707293214236580838";
        String message="{\"title\":\"就医助手\",\"description\":\"您预约了明天XXX医生的号，请提前准备就诊资料，如不能就诊请及时取消。\",\"open_type\":3,\"custom_content\": {\"url\":\"http://wwwxxxx/register.html?id=12343214\",\"des\":\"自字义字段2\"}}";
        send.sendpush(channelId, userId, message);
	}
	
}
