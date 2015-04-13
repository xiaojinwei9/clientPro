package utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HttpUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			readContentFromGet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    
    public static void readContentFromGet() throws IOException{
    	String con="VV段主任医术高明，我还是四五年前在他那<script>alert('aaa')</script>里治疗的，到现在还是很好，最主要的是他的医德高尚，心肠好不图回报，仁心仁术不象有的医生那么不地道，我衷心地祝福他及全家平安幸福。段主任医术高明，我还是四五年前在他那里治疗的，到现在还是很好，最主要的是他的医德高尚，心肠好不图回报，仁心仁术不象有的医生那么不地道，我衷心地祝福他及全家平安幸福。";
        String GET_URL = "http://api.yyk.39.net"
    	//System.out.println("md5after:"+StrUtils.md5("addr=%CC%EC%BA%D3%C7%F8,areaCode=44,cityCode=4401,email=234124@qq.com,idCardNo=130201198301030071,name=%E6%C3%E6%C3,ph=13800138000,pid=23538128,PrivateKey=8d992bcce652e9e222b2391c3eb88947,token=cb4e40ee95bf05bddeb80bc5e113df77,uuid=iosv2"));
    	//String GET_URL = "http://172.16.1.48:80"
    	 //String GET_URL = "http://127.0.0.1:80"
        //取就诊人列表
        //+"/v1/list/register/user.jsonp?uuid=androidv2&token=5e1aa525e2e62fdd0108f972d8807d1d&uid=23139550";
        //取省市
        //+"/v1/app/area/list.jsonp?uuid=webv2&token=5c38c74f9fa6c7e23e9634b79a52d87d&areaId=0&hotCity=1&allCity=1&country=1";
        //+"/v1/update/register/user.jsonp?uuid=webv2&token=5c38c74f9fa6c7e23e9634b79a52d87d&uid=21724034&ruid=12174&mc=0&mc_no=&ccn=&emrno=&g=&gin=&wn=&birth=1981-01-01&city=4401&pc=&address=天河梅花园小区&email=351118863@qq.com&ph=13420003622&sex=0";
        //取医院		
        //+"/v1/app/area/list.jsonp?uuid=aliTest&token=4db58b917061739aac50b5d99196e2e3&areaId=0&hotCity=1";
        +"/v1/app/doctor/detail.jsonp?token=4db58b917061739aac50b5d99196e2e3&uuid=aliTest&doctorId=208100";
        //+"/v1/app/hospital/list.jsonp?uuid=iosv2&token=cb4e40ee95bf05bddeb80bc5e113df77&areaId=0&registerFlag=Y&cityName=123@qq.com";//encode;
    	//+"/v1/app/hospital/list.jsonp?uuid=iosv2&token=eed480fa2afa68a2f0e744b269fc9197&areaId=0&registerFlag=Y&cityName="+URLEncoder.encode("广州","GBK");//encode;
        //+"/v1/update/register/user.jsonp?uuid=webv2&token=5c38c74f9fa6c7e23e9634b79a52d87d&uid=21724034&ruid=12174&mc=0&mc_no=&ccn=&emrno=&g=&gin=&wn=&birth=1981-01-01&city=4401&pc=&address=天河梅花园小区&email=351118863@qq.com&ph=13420003622&sex=0";//encode;
        //+"/v1/list/register/order.jsonp?uuid=webv2&token=4f9b54b5cb874c388214ccfe2d046925&uid=21724034";
    	//+"/v1/app/hospital/list.jsonp?uuid=androidv2&token=5e1aa525e2e62fdd0108f972d8807d1d&areaId=0&registerFlag=Y&cityName=广州";//encode;
        //取科室
        //+"/v1/app/hospital/lab/list.jsonp?uuid=androidv2&token=5e1aa525e2e62fdd0108f972d8807d1d&hospitalId=20214&registerFlag=Y";
        //取基础数据版本
        //+"/v1/app/basicDataVersion/list.jsonp?uuid=iosv2&token=cb4e40ee95bf05bddeb80bc5e113df77";
        //+"/v1/39my/searchDoctor.jsonp?keyword="+URLEncoder.encode("感冒","GBK")+"&hospitalName="+URLEncoder.encode("中医","GBK")+"&uuid=39myv2&token=28b952e6976077b3abece985c686b874&areas="+URLEncoder.encode("广州","GBK")+"&pageSize=5";
        //+"/v1/39cms/searchDoctor.jsonp?keyword=&pagesize=10&uuid=39cmscenterv2&token=29a92b051ad697664322e983cf6e2b7b";
    	//+"/v1/app/registerUser/add.jsonp?addr="+URLEncoder.encode("天河区","GBK")+"&areaCode=44&cityCode=4401&email=234124@qq.com&idCardNo=130201198301030071&name="+URLEncoder.encode("婷婷","GBK")+"&ph=13800138000&pid=23538128&token=cb4e40ee95bf05bddeb80bc5e113df77&uuid=iosv2";
    	//+"/v1/app/registerUser/add.jsonp?addr="+URLEncoder.encode("天河区","GBK")+"&areaCode=44&cityCode=4401&email="+URLEncoder.encode("234124@qq.com","GBK")+"&idCardNo=130201198301030071&name="+URLEncoder.encode("婷婷","GBK")+"&ph=13800138000&pid=23538128&token=cb4e40ee95bf05bddeb80bc5e113df77&uuid=iosv2";
    	//+"/v1/app/registerUser/add.jsonp?addr=%CC%EC%BA%D3%C7%F8&areaCode=44&cityCode=4401&email="+URLEncoder.encode("234124@qq.com","GBK")+"&idCardNo=130201198301030071&name=%E6%C3%E6%C3&ph=13800138000&pid=23538128&token=cb4e40ee95bf05bddeb80bc5e113df77&uuid=iosv2";
    	//+"/v1/app/doctor/detail.jsonp?token=cb4e40ee95bf05bddeb80bc5e113df77&uuid=iosv2&doctorId=291647&pid=7711456&withExperience=1";
    	//+"/v1/app/doctor/experience/list.jsonp?uuid=iosv2&token=cb4e40ee95bf05bddeb80bc5e113df77&doctorId=291647&pageindex=1&pagesize=10";
        //+"/v1/app/feedback/add.jsonp?uuid=iosv2&token=cb4e40ee95bf05bddeb80bc5e113df77&content="+URLEncoder.encode("内容","GBK")+"&contect=13453434";
    	//+"/v1/app/appRegisterInfo/add.jsonp?uuid=iosv2&token=cb4e40ee95bf05bddeb80bc5e113df77&memberId=23538141&type=1&userId=d8ac04670260d332e1855bb6e8482489870111bd7dafdad2a325e66a85a426bd";
        // +"/v1/app/appRegisterInfo/add.jsonp?areaId=0&diseaseId=1408&pageNo=1&pageSize=1&token=e4b7221159ad03b5f372fa5b9fb50d0c&uuid=39diseasev2";
        //+"/v1/out/list/hospital.jsonp?pageNo=1&pageSize=3&asdf=&token=e4b7221159ad03b5f372fa5b9fb50d0c&uuid=39diseasev2&areaid=0";
        //+"/v1/app/registerUser/list.jsonp?uuid=wapAppv32&token=f57fea9275ee21d6d5ad839273d61703&pid=21551939&pi=9&isFirst=0";
        //生成sign,组装新的url,get...
    	String params=GET_URL.substring(GET_URL.lastIndexOf("?")+1, GET_URL.length());
    	System.out.println("params:"+params);
    	String[] paramsArr=params.split("&");
    	Map<String,String> paramsMap=new HashMap<String,String>();
    	for(int i=0;i<paramsArr.length;i++){
    		String[] param=paramsArr[i].split("=");
    		if(param.length==2){
    			paramsMap.put(param[0], param[1]);
    		}else if(param.length==1){
    			paramsMap.put(param[0], "");
    		}
    	}
    	paramsMap.put("PrivateKey","14c234c6deb5dca67f26f3b4169d1dd3");//把私钥加入运算之中
    	String[] paramsKeys = (String[])paramsMap.keySet().toArray(new String[0]);
		Arrays.sort(paramsKeys, String.CASE_INSENSITIVE_ORDER);//忽略大小写,升序
		 StringBuilder sb = new StringBuilder();
		for (int i = 0; i < paramsKeys.length; i++) {
			    String value=paramsMap.get(paramsKeys[i]);//escape?
			    sb.append(paramsKeys[i]+"="+value+",");
		}
		if(sb.length() > 0){
            sb.deleteCharAt(sb.length()-1);
        }
		System.out.println("md5:"+sb.toString());
		String sign=StrUtils.md5(sb.toString());
        String getURL = GET_URL + "&sign=" + sign;
        //String getURL = GET_URL;
       System.out.println("getURL:"+getURL);
       //to get
        URL getUrl = new URL(getURL);
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
        // 服务器
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        connection.connect();
        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));//设置编码,否则中文乱码
        System.out.println("=============================");
        System.out.println("Contents of get request");
        System.out.println("=============================");
        String lines;
        String reJson="";
        while ((lines = reader.readLine()) != null){
        	//lines = new String(lines.getBytes(), "utf-8");
            //System.out.println(lines);
        	reJson+=lines;
        }
        System.out.println(reJson);
        
        //toObj(reJson);
        reader.close();
        // 断开连接
        connection.disconnect();
        System.out.println("=============================");
        System.out.println("Contents of get request ends");
        System.out.println("=============================");
    }
    
    public static void toObj(String json){
    	Gson gson = new Gson();
    	Type type=(Type) new TypeToken<Map<String, Object>>(){}.getType();   
    	Map<String,Object> map=gson.fromJson(json,type);
    	Map<String,Object> response=(Map<String,Object>)map.get("response");
    	System.out.println(response);
    	List<Map<String,Object>> provinces=(List<Map<String,Object>>)response.get("list");
    	for(Map<String,Object> province:provinces){
    		System.out.println("province:"+province.get("NAME")+">"+province.get("ID"));
    		List<Map<String,Object>> citys=(List<Map<String,Object>>)province.get("cityList");
    		for(Map<String,Object> city:citys){
    			System.out.println("city:"+city.get("NAME")+">>"+city.get("ID"));
    		}
    	}
    	System.out.println("------------");
    	System.out.println(gson.toJson(map));
    }
}
