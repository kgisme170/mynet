import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;
import java.io.IOException;
public class counter {
    public static String inputFile = "customers.txt";
    public static String outputDir = "myResult";

    public static void main(String[] args) {
        class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
            @Override
            protected void map(LongWritable l1, Text v1, Context context) throws IOException, InterruptedException {
                System.out.println("map函数:" + l1);
                String[] arr = v1.toString().split(" \t");
                for (String s : arr) {
                    context.write(new Text(s), new LongWritable(1));
                }
            }
        }
        class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
            @Override
            protected void reduce(Text k2, Iterable<LongWritable> v2s, Context context) throws IOException, InterruptedException {
                System.out.println("reduce函数" + k2.toString());
                long sum = 0;
                for (LongWritable v : v2s) {
                    sum += v.get();
                }
                context.write(k2, new LongWritable(sum));
            }
        }
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(conf);
            Path p = new Path(outputDir);
            if (fs.exists(p)) {
                fs.delete(p, true);
            }
            Job job = Job.getInstance();
            job.setJarByClass(counter.class);
            FileInputFormat.setInputPaths(job, new Path(inputFile));
            job.setInputFormatClass(TextInputFormat.class);
            job.setMapperClass(MyMapper.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(LongWritable.class);

            job.setNumReduceTasks(1);
            job.setPartitionerClass(HashPartitioner.class);

            job.setReducerClass(MyReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(LongWritable.class);
            job.setOutputFormatClass(TextOutputFormat.class);
            FileOutputFormat.setOutputPath(job, p);
            job.waitForCompletion(true);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException" + e.getMessage());
        } catch (java.io.InterruptedIOException e) {
            System.out.println("InterruptedIOException" + e.getMessage());
        } catch (IOException e) {
            System.out.println("FileSystem异常" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("InterruptedException" + e.getMessage());
        }
    }
}
