package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Api01 {

    @RequestMapping("/api01")
    public String index() {
        return "api01";
    }

}
