package utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.activation.MimetypesFileTypeMap;

import com.google.gson.Gson;

import play.Logger;
import play.Play;

public class HttpPost {

	public static void main(String[] args){
		query_bindlist();
		//push_msg();
	}
	
	public static void push_msg(){
		Gson gson = new Gson();
		Logger.info("go..");
		String method="channel/channel";
		Map<String, Object> paramsMap=new HashMap<>();
		paramsMap.put("method", "push_msg");
		paramsMap.put("apikey", "Bid6At75Zj9h7Ls7lvnAbMzG");
		paramsMap.put("push_type", 3);
		paramsMap.put("timestamp", new Date().getTime());
		//paramsMap.put("v", "1");
		paramsMap.put("device_type", 3);
		paramsMap.put("message_type", 1);
		Map<String,String> messagesMap=new HashMap<>();
		messagesMap.put("title", "hello");
		messagesMap.put("description", "hello world");
		paramsMap.put("messages", "\"{\\\"title\\\":\\\"hello\\\",\\\"description\\\":\\\"hello\\\"}\"");
		paramsMap.put("msg_keys", "\"testkey\"");
		paramsConstruction(method,paramsMap);
		Logger.info("end");
	}
	
	public static void query_bindlist(){
		Logger.info("go..");
		String method="channel/channel";
		Map<String, Object> paramsMap=new HashMap<>();
		paramsMap.put("method", "query_bindlist");
		paramsMap.put("apikey", "et6zXRnSELoX3wSKtVxgxSHM");
		paramsMap.put("timestamp", new Date().getTime());
		paramsMap.put("user_id", "827560851841769726");
		paramsConstruction(method,paramsMap);
		Logger.info("end");
	}
	
	public static String paramsConstruction(String method,Map<String, Object> paramsMap){
		Logger.info("paramsMap"+paramsMap);
		String httpMethod="POST";//
		String url="https://channel.api.duapp.com/rest/2.0/"+method;
		String SecretKey="gxEuK4g97VpbQa5hjDL3hN5MKVX3UrVy";//
		String[] paramsKeys = (String[])paramsMap.keySet().toArray(new String[0]);//参数字符串数组
		Arrays.sort(paramsKeys,String.CASE_INSENSITIVE_ORDER);//忽略大小写,升序
		String signPre = httpMethod+url;
		for (int i = 0; i < paramsKeys.length; i++) {
			    String value="";
				try {
					value = URLEncoder.encode(paramsMap.get(paramsKeys[i])+"","utf-8");//encode
					paramsMap.put(paramsKeys[i]+"",value);
					signPre += paramsKeys[i]+"="+value;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		}
		signPre+=SecretKey;
		Logger.info("checkToken-tokenPre:" + signPre);
		String md5Token="";
		try {
			md5Token = StrUtils.md5(URLEncoder.encode(signPre,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String md5Token=StrUtils.md5(signPre);
		//Logger.info("md5Token:"+md5Token);
		paramsMap.put("sign", md5Token);
		Logger.info("paramsMapEnd:"+paramsMap);//最终post提交参数
		
		Map<String, String> fileMap = new HashMap<String, String>();
		
		//fileMap.put("userfile", filepath);
		//String url="http://localhost"+method;
		String ret = formUpload(url, paramsMap, fileMap);
		Logger.info("ret:"+ret);
		//JsonObject retObj=GsonUtils.parseJson(ret);
		//Logger.info("retObj:"+retObj);
		return ret;
	}
	
	/**
	 * txt,上传图片
	 * 
	 * @param urlStr
	 * @param textMap
	 * @param fileMap
	 * @return
	 */
	public static String formUpload(String urlStr, Map<String, Object> textMap,
			Map<String, String> fileMap) {
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符
		try {
			Logger.info("newUrl:"+urlStr);
			URL url = new URL(urlStr);
			Logger.info("newUrlEnd:"+urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
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
			Logger.info("DataOutputStreamConn");
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			Logger.info("DataOutputStreamConnEnd");
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
				out.write(strBuf.toString().getBytes("UTF-8"));
			}
			Logger.info("writeTxtDataEnd");
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
			Logger.info("writeFileDataEnd");
			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(),"UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
			Logger.info("returnDataEnd");
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
	
	public static String saveFile(File file){
		String fileNameA=file.getName();
		//生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理） 
		Random r = new Random();
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; //获取随机数 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); //时间格式化的格式 
		String nowTimeStr = dateFormat.format(new Date()); //当前时间 
		String fileType=fileNameA.substring(fileNameA.lastIndexOf("."), fileNameA.length());
		String fileNameB=nowTimeStr+"_"+rannum+fileType;
		
		fileNameB="/public/uploads/"+fileNameB;
		String url="Cons.client_url"+fileNameB;
		String filePath=Play.applicationPath.getAbsoluteFile()+fileNameB;
		Logger.info("filePath:"+filePath);
		Logger.info("fileUrl:"+url);
		File uploadFile=new File(filePath);   
	    play.libs.Files.copy(file,uploadFile);
	    String files=fileNameA+"-"+url;
	    return files;
	}
}
