import java.io.*;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ots.OTSClient;
import com.aliyun.openservices.ots.model.*;

public class MetaIni {
    private String otsEndpoint;
    private String otsAccessId;
    private String otsAccessKey;
    private String otsInstanceName;
    private String otsTableName;
    private PrimaryKeyValue startPk = new PrimaryKeyValue("51332.project_list.project.default.", PrimaryKeyType.STRING);
    private PrimaryKeyValue endPk = new PrimaryKeyValue("51332.project_list.project.default.~", PrimaryKeyType.STRING);
    private RangeRowQueryCriteria query;

    public MetaIni(String fileName) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(fileName));
            otsEndpoint = (String) p.get("otsEndpoint");
            otsAccessId = (String) p.get("otsAccessId");
            otsAccessKey = (String) p.get("otsAccessKey");
            otsInstanceName = (String) p.get("otsInstanceName");//默认空串
            otsTableName = (String) p.get("otsTableName");
        } catch (IOException e) {
            System.out.println("ini文件不存在:" + fileName);
            System.exit(1);
        }
        for (Object key : p.keySet()) {
            System.out.println((String) key);

        }
        System.out.println("===========\n" + otsEndpoint + "\n" + otsAccessId + "\n" + otsAccessKey + "\n" + otsInstanceName + "\n" + otsTableName);
        query = new RangeRowQueryCriteria(otsTableName);
        query.setRange("PARTITION_KEY", startPk, endPk);
    }

    public void ListProjects() {
        System.out.println("开始创建 OTSClient");
        OTSClient client = new OTSClient(otsEndpoint, otsAccessId, otsAccessKey, otsInstanceName);
        System.out.println("结束创建 OTSClient");
        List<Row> rows = client.getRowsByRange(query, "");
        System.out.println("结束创建 getRowsByRange");
        System.out.println("rows数量" + rows.size());

        for (Row r : rows) {
            Map<String, ColumnValue> columns = r.getColumns();
            for (Map.Entry<String, ColumnValue> entry : columns.entrySet()) {
                System.out.println("================>" + entry.getKey() + ":" + entry.getValue());
            }
            ColumnValue c = columns.get("BASIC_CELL");
            JSONObject jsonObject = JSONObject.parseObject(c.getValue());
            System.out.println(jsonObject.getString("accessMode"));
        }
    }

    public static void main(String[] args) {
        System.out.println("输入配置文件名:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
        String s = reader.readLine();//不能使用~
        File f = new File(s);
        System.out.println(f.exists());
        MetaIni meta = new MetaIni(s);
        meta.ListProjects();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}