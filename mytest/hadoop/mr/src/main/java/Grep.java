import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.map.InverseMapper;
import org.apache.hadoop.mapreduce.lib.map.RegexMapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer;

import java.util.Random;
/**
 * @author liming.glm
 */
public class Grep {
    public static void main(String[] args) throws Exception {
        final int min = 3;
        final int max = 4;
        if (args.length < min || args.length > max) {
            System.out.println("Grep <输入目录><输出目录><regex>[<分组>]");
            return;
        }
        Configuration conf = new Configuration();
        conf.set(RegexMapper.PATTERN, args[2]);
        if (args.length == max) {
            conf.set(RegexMapper.GROUP, args[3]);
        }
        Job grepJob = new Job(conf);
        grepJob.setJobName("Grep程序");
        Job sortJob = new Job(conf);
        sortJob.setJobName("Grep-sort");
        Path tmp = new Path("Grep-temp" + Integer.toString(new Random().nextInt(Integer.MAX_VALUE)));
        try {
            FileInputFormat.setInputPaths(grepJob, args[0]);
            grepJob.setMapOutputKeyClass(RegexMapper.class);
            grepJob.setCombinerClass(LongSumReducer.class);
            grepJob.setReducerClass(LongSumReducer.class);

            FileOutputFormat.setOutputPath(grepJob, tmp);
            grepJob.setOutputFormatClass(SequenceFileOutputFormat.class);
            grepJob.setOutputKeyClass(Text.class);
            grepJob.setOutputValueClass(LongWritable.class);
            grepJob.waitForCompletion(true);

            FileInputFormat.setInputPaths(sortJob, tmp);
            sortJob.setInputFormatClass(SequenceFileInputFormat.class);
            sortJob.setMapperClass(InverseMapper.class);
            sortJob.setNumReduceTasks(1);

            FileOutputFormat.setOutputPath(sortJob, new Path(args[1]));
            sortJob.setSortComparatorClass(LongWritable.DecreasingComparator.class);
            sortJob.waitForCompletion(true);
        } finally {
            FileSystem.get(conf).delete(tmp, true);
        }
    }
}