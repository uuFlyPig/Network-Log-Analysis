package logcleaner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/***
 * 解析日志文件
 * 工具类
 */

public class ParserLog {
    //将字符串格式化位时间戳
    public static final SimpleDateFormat TIMESTAMP= new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
    public static final SimpleDateFormat TIMEFORMAT= new SimpleDateFormat("yyyyMMddmmss");
    /**
     * 解析IP地址
     * @param line
     * @return
     */
    public String parserIp(String line){
        String[] splits = line.split("- -");
        String ip=splits[0].trim();
        return ip;
    }

    /**
     * 解析获取时间
     * @param line
     * @return
     */
    public String parserTime(String line){
        int start=line.indexOf("[");
        int end=line.indexOf("+0800]");
        //截取到时间的字符串
        String time=line.substring(start+1,end);
        //时间格式化,将时间戳格式化成yyyyMMddmmss
        Date date=null;
        try {
                date= TIMESTAMP.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TIMEFORMAT.format(date);
    }

    /**
     * 解析URL
     * @param line
     * @return
     */
    public String parserUrl(String  line){
        int start=line.indexOf("\"");
        int end = line.lastIndexOf("\"");
        String url=line.substring(start+1,end);
        return url;
    }

    /***
     * 解析状态码
     * @param line
     * @return
     */
    public String parserStatus(String line){
        int start = line.lastIndexOf("\"");
        String[] status=line.substring(start+1).trim().split(" ");
        return status[0];
    }

    /***
     * 解析流量
     * @param line
     * @return
     */
    public String parserFlow(String line){
        int start = line.lastIndexOf("\"");
        String[] status=line.substring(start+1).trim().split(" ");
        return status[1];
    }

    public String[] parser(String line){
        String ip = parserIp(line);
        String time = parserTime(line);
        String url = parserUrl(line);
        String status = parserStatus(line);
        String flow = parserFlow(line);
        return new String[]{ip,time,url,status,flow};
    }
}
