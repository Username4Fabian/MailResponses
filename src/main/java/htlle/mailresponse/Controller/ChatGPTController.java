package htlle.mailresponse.Controller;

import htlle.mailresponse.ChatGPT.ChatGPT;
import org.springframework.web.bind.annotation.*;

/**
 * This class is responsible for handling requests related to the ChatGPT functionality.
 */
@RestController
@RequestMapping("/chatGPT")
public class ChatGPTController {

    /**
     * This method handles POST requests to get a response from the ChatGPT.
     *
     * @param mood    The mood of the user message.
     * @param message The user's message.
     * @return The AI-generated response.
     * @throws Exception If there is an error while getting the response.
     */
    @PostMapping("/getResponse")
    public String getResponse(@RequestParam String mood, @RequestParam String message) throws Exception {
        // Get the AI-generated response and return it
        return ChatGPT.getAIResponseFromOpenAI(mood, message);
    }
}