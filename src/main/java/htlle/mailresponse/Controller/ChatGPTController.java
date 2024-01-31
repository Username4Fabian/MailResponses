package htlle.mailresponse.Controller;

import htlle.mailresponse.ChatGPT.ChatGPT;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatGPT")
public class ChatGPTController {

    @PostMapping("/getResponse")
    public String getResponse(@RequestParam String mood,@RequestParam String message) throws Exception {
        System.out.println(mood + " " + message);
        return ChatGPT.getAIResponseFromOpenAI(mood, message);
    }
}
