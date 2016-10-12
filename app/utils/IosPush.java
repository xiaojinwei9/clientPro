package utils;

import java.util.ArrayList;
import java.util.List;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiaojinwei
 * @date   2015-3-5
 * @Description:
 */
public class IosPush {
	
	private static final Logger log = LoggerFactory.getLogger(IosPush.class);
	 /************************************************
	 测试推送服务器地址：gateway.sandbox.push.apple.com /2195 
	 产品推送服务器地址：gateway.push.apple.com / 2195 
	需要javaPNS_2.2.jar包
	 ***************************************************/
	/**
   *这是一个比较简单的推送方法，
   * apple的推送方法
   * @param tokens   iphone手机获取的token
   * @param path 这里是一个.p12格式的文件路径，需要去apple官网申请一个 
   * @param password  p12的密码 此处注意导出的证书密码不能为空因为空密码会报错
   * @param message 推送消息的内容
   * @param count 应用图标上小红圈上的数值
   * @param sendCount 单发还是群发  true：单发 false：群发
   */

	public void sendpush(List<String> tokens, String message,String alertText,Integer count,boolean sendCount) {
	try {
			//message是一个json的字符串{“aps”:{“alert”:”iphone推送测试”}}
			PushNotificationPayload payLoad =  PushNotificationPayload.fromJSON(message);
			payLoad.addAlert(alertText); // 消息内容
			payLoad.addBadge(count); // iphone应用图标上小红圈上的数值
			payLoad.addSound("default"); // 铃音 默认
			PushNotificationManager pushManager = new PushNotificationManager();
			//String path=PropertyUtil.getProperty("pushConfig", "push.ios.certificates");
			String path="";
			//String password=PropertyUtil.getProperty("pushConfig", "push.ios.password");;
			String password="";
			//true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager.initializeConnection(new AppleNotificationServerBasicImpl(path, password, true));
			List<PushedNotification> notifications = new ArrayList<PushedNotification>(); 
			// 发送push消息
			if (sendCount) {
				log.info("pushRegisterInfoList-pushIos-start:"+tokens.get(0));
				Device device = new BasicDevice();
				device.setToken(tokens.get(0));
				PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
				notifications.add(notification);
			} else {
				log.info("--------------------------apple推送 群-------");
				List<Device> device = new ArrayList<Device>();
				for (String token : tokens) {
					device.add(new BasicDevice(token));
				}
				notifications = pushManager.sendNotifications(payLoad, device);
			}
			List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
			List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
			int failed = failedNotifications.size();
			int successful = successfulNotifications.size();
			
			if (successful > 0 && failed == 0) {
				log.info("pushRegisterInfoList-pushIos-success:" + successfulNotifications.size() + ":userId:"+tokens.get(0));
			} else if (successful == 0 && failed > 0) {
				log.info("pushRegisterInfoList-pushIos-fail:(" + failedNotifications.size() + "):");
			} else if (successful == 0 && failed == 0) {
				log.info("pushRegisterInfoList-pushIos-:No notifications could be sent, probably because of a critical error");
			} else {
				log.info("------pushIos-success:(" + successfulNotifications.size() + ")fail:(" + failedNotifications.size() + ")");
			}
	
	// pushManager.stopConnection();

	} catch (Exception e) {
	
	e.printStackTrace();
	
	}

}
	
	/**
	 * TODO
	 * @param args
	 */
	public static void main(String[] args) {
		IosPush send=new IosPush();
		List<String> tokens=new ArrayList<String>();
		tokens.add("d8ac04670260d332e1855bb6e8482489870111bd7dafdad2a325e66a85a426bd");
		//tokens.add("dc2cf037bd4465c851b1d96a86b0a028307bc7e443435b6fafe93c2957bb415c");
		String message="{'aps':{'alert':'iphone推送测试alert2AAA'},'url':'http://wwwwww.net/gz/zonghe/4cc90.html'}";
		Integer count=1;
		boolean sendCount=true;
		send.sendpush(tokens,message,"内容内容",count, sendCount);
	}
}
