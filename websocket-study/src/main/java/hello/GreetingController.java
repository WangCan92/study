package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.HashSet;
import java.util.Set;

@RestController
public class GreetingController {

    private Set<String> userSet = new HashSet<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
//        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @PostMapping("/sendMsg")
    public String sendWSMessage(@RequestBody HelloMessage helloMessage) {
        if (userSet.contains(helloMessage.getName())) {
            messagingTemplate.convertAndSendToUser(helloMessage.getName(), "p2p", helloMessage.getName() + "发消息啦");
            return "成功！";
        } else {
            return "用户未登录";
        }

    }

    @GetMapping("/login")
    public void login(String userId) {
        userSet.add(userId);

    }

}