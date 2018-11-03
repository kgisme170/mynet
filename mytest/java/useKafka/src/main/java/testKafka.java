import kafka.admin.AdminUtils;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class testKafka {
    ZkUtils zkUtils = null;

    public testKafka() {
        zkUtils = ZkUtils.apply("localhost:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());
    }

    @Override
    protected void finalize() {
        zkUtils.close();
    }

    public void createTopic(String topic, int partition, int replica, Properties properties) {
        try {
            if (!AdminUtils.topicExists(zkUtils, topic)) {
                AdminUtils.createTopic(zkUtils, topic, partition, replica,
                        properties, AdminUtils.createTopic$default$6());
            } else {
                System.out.println("创建主题失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyTopicConfig(String topic, Properties properties) {
        try {
            Properties curProp = AdminUtils.fetchEntityConfig(zkUtils,
                    ConfigType.Topic(), topic);
            curProp.putAll(properties);
            AdminUtils.changeTopicConfig(zkUtils, topic, curProp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPartition(String topic, String partitionName) {
        //AdminUtils.addPartitions(zkUtils,topic,1,
        //        "1:1,1:1", 1, AdminUtils.addPartitions$default$6());
    }

    public void deleteTopic(String topic) {
        AdminUtils.deleteTopic(zkUtils, topic);
    }

    public void showTopic(String topic) {
        Properties properties = AdminUtils.fetchEntityConfig(zkUtils,
                ConfigType.Topic(), topic);
        Iterator it = properties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + ":" + value);
        }
    }

    public static void main(String[] args) {
        testKafka t = new testKafka();
        t.createTopic("myTopic", 1, 1, null);
    }
}