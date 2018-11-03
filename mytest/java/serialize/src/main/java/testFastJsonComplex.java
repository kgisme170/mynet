import com.alibaba.fastjson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.Set;

public class testFastJsonComplex {
    public static void main(String[] args) {
        String fileName = "../../src/testArray.json";
        String s = testFastJsonComplex.class.getResource(".") + fileName;
        try {
            URI u = new URI(s);
            File f = new File(u);
            System.out.println(u + " exists: " + f.exists());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String path = testFastJsonComplex.class.getResource(".").getPath();
        System.out.println(path);
        //InputStream is = testFastJsonComplex.class.getResourceAsStream(path + fileName);
        try {
            String content = new Scanner(new File(path + fileName)).useDelimiter("/n").next();
            System.out.println(content + "\n");
            JSONObject jsonObject = JSONObject.parseObject(content);
            String location = jsonObject.getString("location");
            System.out.println(location);

            JSONObject machine = jsonObject.getJSONObject("machine");
            JSONObject m1 = machine.getJSONObject("m1");
            int quota = m1.getInteger("quota");
            System.out.println(quota);

            Set<String> set = machine.keySet();
            for (String k : set) {
                System.out.println(k);
                JSONObject m = machine.getJSONObject(k);
                System.out.println(m.getInteger("quota"));
                System.out.println(m.getString("others"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}