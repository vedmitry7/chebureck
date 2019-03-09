package com.vedmitryapps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    private final VisitsRepository visitsRepository;
    @Autowired
    private MessagesRepository messagesRepository;

    public GreetingController(VisitsRepository visitsRepository) {
        this.visitsRepository = visitsRepository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }


    @GetMapping
    public String main(Map<String, Object> model){
        Iterable<Message> messages = messagesRepository.findAll();

        model.put("messages", messages);

        Visit visit = new Visit();
        visit.description = String.format("Visited at %s", LocalDateTime.now());
        visitsRepository.save(visit);

        return "main";
    }


    @PostMapping
    public String add(@RequestParam String text,
                      @RequestParam String tag,
                      Map<String, Object> model){

        Message message = new Message(text, tag);

        messagesRepository.save(message);

        Iterable<Message> messages = messagesRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String text,
                      Map<String, Object> model){
        //List<Message> messages = messagesRepository.findByTag(text);

        Iterable <Message> messages;
        if(text !=null && !text.isEmpty()){
            messages = messagesRepository.findByTag(text);
        } else {
            messages = messagesRepository.findAll();
        }

        model.put("messages", messages);
        return "main";
    }
}