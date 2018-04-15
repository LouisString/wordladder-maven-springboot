package com.light.springboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.light.springboot.utils.buildLadder;
import com.light.springboot.utils.getWordlist;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WordsLadderController {

    @GetMapping({"/", "/index", "/home"})
    public String root(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/wordladder")
    public String Controller(){
        return "wordladder";
    }

    @RequestMapping(path="/wordladder", params = {"word1","word2"})
        public ModelAndView Controller(@RequestParam("word1") String word1,
                                       @RequestParam("word2") String word2) {
            Map word_list = getWordlist.getDictionatyForWeb();
            List<String> messages = new ArrayList<>();
            String message = "";
            boolean invalid = false;
            if (word1.equals("") || word2.equals("")){
                message = "Word should not be empty.";
                messages.add(message);
            }
            if (!word_list.containsKey(word1)&&(!word1.equals(""))){
                message = "Word 1 is not in the dictionary.";
                messages.add(message);
                invalid = true;
            }
            if (word1.length()!= word2.length()){
                message = "Word 1 and Word 2 are not in the same length.";
                messages.add(message);
                invalid = true;
            }
            if (!word_list.containsKey(word2)&&(!word2.equals(""))){
                message = "Word 2 is not in the dictionary.";
                messages.add(message);
                invalid = true;
            }
            if (!invalid){
                buildLadder process = new buildLadder();
                message = process.find_ladder_for_web(word1, word2, word_list);
                String[] splitstr=message.split(":");
                messages.add(splitstr[0]+':');
                messages.add(splitstr[1]);
            }
            ModelAndView mav = new ModelAndView("wordladder");
            mav.addObject("messages", messages);
            return mav;
        }
}






