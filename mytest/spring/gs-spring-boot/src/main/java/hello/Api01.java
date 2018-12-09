package hello;
import com.alibaba.fastjson.*;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author liming.gong
 */
@RestController
public class Api01 {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/another")
    public String another() {
        return "another!";
    }

    class Person {
        String name = "abc";

        @JSONField(name = "NAME")
        public String getname() {
            return name;
        }

        @JSONField(name = "ID")
        public void setname(String value) {
            this.name = name;
        }

        int id = 3;

        @JSONField(name = "ID")
        public int getid() {
            return id;
        }

        @JSONField(name = "ID")
        public void setid(int value) {
            this.id = id;
        }

    }

    @RequestMapping("/api01")
    public String api01() {

        try {
            return JSONObject.toJSONString(new Person());
        } catch (Exception e) {
            return "mapper exception";
        }
    }
}