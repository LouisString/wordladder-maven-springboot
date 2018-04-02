package com.light.springboot.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WordsLadderController {

    @RequestMapping("/wordsladder")
    @ResponseBody
    public String helloworld(@RequestParam("word1") String word1, @RequestParam("word2") String word2) {
        Map word_list = getWordlist.getDictionatyForWeb();
        String message = "";
        boolean invalid = false;
        if (!word_list.containsKey(word1)){
            message += "Word 1 is not in the dictionary. ";
            invalid = true;
        }
        if (word1.length()!= word2.length()){
            message += "Word 1 and Word 2 are not in the same length. ";
            invalid = true;
        }
        if (!word_list.containsKey(word2)){
            message += "Word 2 is not in the dictionary. ";
            invalid = true;
        }
        if (invalid){
            return message;
        }
        else{
            buildLadder process = new buildLadder();
            message = process.find_ladder_for_web(word1, word2, word_list);
            return message;
        }


    }

}