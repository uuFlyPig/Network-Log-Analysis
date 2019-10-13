package logcleaner;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


import java.io.IOException;

public class LogMapper extends Mapper<LongWritable, Text, NullWritable,Text> {
    ParserLog parserLog = new ParserLog();
    Text output= new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //日志清洗
        String line = value.toString();
        String[] parser = parserLog.parser(line);
        //1.过滤掉静态的资源请求
        if (parser[2].startsWith("GET /static") || parser[2].startsWith("GET /uc_server")){
            return;
        }
        //2.过滤掉无用的字符串（get/post，HTTP etc）
        if(parser[2].startsWith("GET /")){
            int start=parser[2].indexOf("/");
            parser[2]=parser[2].substring(start+1);
        }else if(parser[2].startsWith("POST /")){
            int start=parser[2].indexOf("/");
            parser[2]=parser[2].substring(start+1);
        }
        //3.过滤HTTP
        if(parser[2].endsWith("HTTP/1")){
            int end=parser[2].indexOf("HTTP/1");
            parser[2]=parser[2].substring(0,end).trim();
        }

        //4.输出清洗后的日志
        output.set(parser[0]+"\t"+parser[1]+"\t"+parser[2]);
        context.write(NullWritable.get(),output);
    }
}
