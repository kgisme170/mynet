package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
/**
 * @author liming.glm
 */
@Controller
public class HelloController {
    @Value("${welcome.message:No suche message}")
    private String message = "Hello World";

    @RequestMapping("/hello")
    public String hello(Map<String, Object> model) {
        model.put("name", this.message);
        return "hello";
    }
}