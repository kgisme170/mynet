import java.util.HashMap;
import java.util.Map;
/**
 * @author liming.gong
 */
public class UseEnum {
    enum Status {
        SCUUESS("1", "成功"),
        FAILED("2", "失败");

        public String value;
        public String desc;

        Status(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static void main(String[] args) {
        System.out.println(Status.SCUUESS.getValue());
        System.out.println(Status.SCUUESS.getDesc());
        System.out.println(Status.FAILED.getValue());
        System.out.println(Status.FAILED.getDesc());
        Map<Status, String> m = new HashMap<>(0);
        System.out.println(m);
    }
}