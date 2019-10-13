package logcleaner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class LogClean {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
       // 测试用
        // args=new String[]{"E:\\stupidIdea\\access_2018_05_30.log","E:\\stupidIdea\\result"};

        Configuration conf = new Configuration();
        Job job = Job.getInstance();

        //配置job信息
        job.setJarByClass(LogClean.class);

        job.setMapperClass(LogMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setNumReduceTasks(1);

        //输入路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        System.exit(job.waitForCompletion(true)? 0:1);
    }
}
