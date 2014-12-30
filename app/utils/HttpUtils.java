package utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import play.Logger;

public class HttpUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String privateKey=StrUtils.md5("taskApiWeb");
		System.out.println(privateKey);
		String filepath="E:\\ziliao\\0.jpg";
		
		String urlStr = "http://www.recb.com.cn/testapi/add";
		//String urlStr = "http://www.taskapi.com/testapi/add";
		//String urlStr = "http://www.recb.com.cn/testapi/del";
		//String urlStr = "http://www.recb.com.cn/testapi/get";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userId", "200A");
		paramsMap.put("userName", "xiaojinVVV不是吧");
		paramsMap.put("sysId", "web");
		Object[] paramsKeys = paramsMap.keySet().toArray();
		Arrays.sort(paramsKeys);//升序
		String tokenPre = "";
		for (int i = 0; i < paramsKeys.length; i++) {
			    String value=StrUtils.escape(paramsMap.get(paramsKeys[i]));//escape
			    paramsMap.put(paramsKeys[i]+"",value);
			    tokenPre += value;
		}
		tokenPre+="829c70fe3772013f22faa6620a411f3b";//需保密码,与sysId是一对值,一个sysId分配唯一token
		Logger.info("checkToken-tokenPre:" + tokenPre);
		String md5Token=StrUtils.md5(tokenPre);
		paramsMap.put("token", md5Token);
		Map<String, String> fileMap = new HashMap<String, String>();
		
		//fileMap.put("userfile", filepath);
		
		String ret = formUpload(urlStr, paramsMap, fileMap);
		
		System.out.println(ret);
		

	}
	
	

	/**
	 * 上传图片
	 * 
	 * @param urlStr
	 * @param textMap
	 * @param fileMap
	 * @return
	 */
	public static String formUpload(String urlStr, Map<String, String> textMap,
			Map<String, String> fileMap) {
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);//向服务器写入数据
			conn.setDoInput(true);//从服务器获取数据
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text
			if (textMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator iter = textMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\""
							+ inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes("utf-8"));
			}

			// file
			if (fileMap != null) {
				Iterator iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					File file = new File(inputValue);
					String filename = file.getName();
					String contentType = new MimetypesFileTypeMap()
							.getContentType(file);
					if (filename.endsWith(".png")) {
						contentType = "image/png";
					}
					if (contentType == null || contentType.equals("")) {
						contentType = "application/octet-stream";
					}

					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(BOUNDARY).append(
							"\r\n");
					strBuf.append("Content-Disposition: form-data; name=\""
							+ inputName + "\"; filename=\"" + filename
							+ "\"\r\n");
					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

					out.write(strBuf.toString().getBytes());

					DataInputStream in = new DataInputStream(
							new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			System.out.println("发送POST请求出错。" + urlStr);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}

}
