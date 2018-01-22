package com.gx.dy.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gx.dy.domain.PageData;


/**
 * @author MeepoGuan
 *
 * <p>Description: 常用工具类</p>
 *
 * 2017年4月30日
 *
 */
public class Tools {
	
	/**
	 * 随机生成六位数验证码 
	 * @return
	 */
	public static int getRandomNum(){
		 Random r = new Random();
		 return r.nextInt(900000)+100000;//(Math.random()*(999999-100000)+100000)
	}
	
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date){
		return date2Str(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date){
		if(notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		}else{
			return null;
		}
	}
	
	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 把时间根据时、分、秒转换为时间段
	 * @param StrDate
	 */
	public static String getTimes(String StrDate){
		String resultTimes = "";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    java.util.Date now;
	    
	    try {
	    	now = new Date();
	    	java.util.Date date=df.parse(StrDate);
	    	long times = now.getTime()-date.getTime();
	    	long day  =  times/(24*60*60*1000);
	    	long hour = (times/(60*60*1000)-day*24);
	    	long min  = ((times/(60*1000))-day*24*60-hour*60);
	    	long sec  = (times/1000-day*24*60*60-hour*60*60-min*60);
	        
	    	StringBuffer sb = new StringBuffer();
	    	//sb.append("发表于：");
	    	if(hour>0 ){
	    		sb.append(hour+"小时前");
	    	} else if(min>0){
	    		sb.append(min+"分钟前");
	    	} else{
	    		sb.append(sec+"秒前");
	    	}
	    		
	    	resultTimes = sb.toString();
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    
	    return resultTimes;
	}
	
	/**
	 * 写txt里的单行内容
	 * @param filePath  文件路径
	 * @param content  写入的内容
	 */
	public static void writeFile(String fileP,String content){
		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
		filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
		if(filePath.indexOf(":") != 1){
			filePath = File.separator + filePath;
		}
		try {
	        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath),"utf-8");      
	        BufferedWriter writer=new BufferedWriter(write);          
	        writer.write(content);      
	        writer.close(); 

	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * 验证邮箱
	  * @param email
	  * @return
	  */
	 public static boolean checkEmail(String email){
	  boolean flag = false;
	  try{
	    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(email);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	
	 /**
	  * 验证手机号码
	  * @param mobiles
	  * @return
	  */
	 public static boolean checkMobileNumber(String mobileNumber){
	  boolean flag = false;
	  try{
	    Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
	    Matcher matcher = regex.matcher(mobileNumber);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	 
	/**
	 * 检测KEY是否正确
	 * @param paraname  传入参数
	 * @param FKEY		接收的 KEY
	 * @return 为空则返回true，不否则返回false
	 */
/*	public static boolean checkKey(String paraname, String FKEY){
		paraname = (null == paraname)? "":paraname;
		return MD5.md5(paraname+DateUtil.getDays()+",fh,").equals(FKEY);
	}*/
	 
	/**
	 * 读取txt里的单行内容
	 * @param filePath  文件路径
	 */
	public static String readTxtFile(String fileP) {
		try {
			
			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if(filePath.indexOf(":") != 1){
				filePath = File.separator + filePath;
			}
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), encoding);	// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件,查看此路径是否正确:"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return "";
	}
	/**
	 * 获取配置文件
	 * @return
	 */
	public static Properties getConfigproperties(String propertiesPath) {
		Properties properties = new Properties();
		InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesPath);
		try {
			properties.load(file);
			return properties;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 集合筛选
	 * @param list 被筛选集合
	 * @param predicate 函数接口
	 * @return
	 */
	public static <T> List<T> listFilter(List<T> list,Predicate<T> predicate) {
		List<T> newList = new ArrayList<T>();
		for(final T t : list) {
			if(predicate.test(t)){
				newList.add(t);
			}
		}
		return newList;
	}
	
	public static List<PageData> subList(List<PageData> pds,int currentPage,int pageSize) throws Exception{
		if(pds==null||pds.size()==0){
			throw new Exception("没有查出数据！");
		}
		int start = 0;
		int end = 1;
		if(currentPage>1){
			start = (currentPage-1)*pageSize;
		}
		if(start>=pds.size()){
			throw new Exception("没有更多数据了！");
		}
		end = currentPage*pageSize;
		if(end>=pds.size()){
			 end = pds.size();
		}
		List<PageData> resultpds = pds.subList(start,end);
		return resultpds;
	}
	
    /*public static void main(String[] args) {
		System.out.println(getRandomNum());
		List<Map<String,Integer>> list = new ArrayList<Map<String,Integer>>();
		Map<String,Integer> map1 = new HashMap<String, Integer>();
		map1.put("张三", 28);
		map1.put("李四", 26);
		map1.put("王五", 23);
		map1.put("西葫芦", 26);
		list.add(map1);
		Map<String,Integer> map2 = new HashMap<String, Integer>();
		map2.put("西瓜",100);
		map2.put("冬瓜", 200);
		map2.put("哈密瓜", 300);
		map2.put("西葫芦", 300);
		list.add(map2);
		List<Map<String,Integer>> afterlist = listFilter(list,(Map<String,Integer> elem) ->  elem.get("西葫芦")>=20);
		for(Map<String, Integer> map: afterlist){
			System.out.println(map.toString());
		}
	}*/
	
	
	
	   /** 
	     * 计算地球上任意两点(经纬度)距离 
	     *  
	     * @param long1 
	     *            第一点经度 
	     * @param lat1 
	     *            第一点纬度 
	     * @param long2 
	     *            第二点经度 
	     * @param lat2 
	     *            第二点纬度 
	     * @return 返回距离 单位：米 
	     */  
	    public static double getDistance(double long1, double lat1, double long2,  
	            double lat2) {  
	        double a, b, R;  
	        R = 6378137; // 地球半径  
	        lat1 = lat1 * Math.PI / 180.0;  
	        lat2 = lat2 * Math.PI / 180.0;  
	        a = lat1 - lat2;  
	        b = (long1 - long2) * Math.PI / 180.0;  
	        double d;  
	        double sa2, sb2;  
	        sa2 = Math.sin(a / 2.0);  
	        sb2 = Math.sin(b / 2.0);  
	        d = 2  
	                * R  
	                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
	                        * Math.cos(lat2) * sb2 * sb2));  
	        return d;  
	    }  
}
