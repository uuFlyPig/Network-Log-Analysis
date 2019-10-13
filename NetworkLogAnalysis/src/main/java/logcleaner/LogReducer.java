package logcleaner;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/***
 * 只使用Map过程清洗数据，Reduce的存在是为了使结果文件都输出在同一个结果文件里
 */
public class LogReducer extends Reducer<LongWritable, Text,Text, NullWritable> {

    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for(Text t:values){
            context.write(t,NullWritable.get());
        }
    }
}
