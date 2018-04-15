package com.light.springboot.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.light.springboot.utils.buildLadder;
import com.light.springboot.utils.getWordlist;
import com.light.springboot.utils.loginPeriod;

import javax.annotation.PostConstruct;

@RestController
public class WordsLadderController {

    private Map<String, String> userinfo = new HashMap();
    private Map<String, Date> loginusers = new HashMap();

    @PostConstruct
    public void createUserDatabase(){
        userinfo.put("root", "root");
        userinfo.put("123", "asd");
        userinfo.put("234", "qwe");
    }

    @RequestMapping("/login")

    public String login(@RequestParam("usn") String usn, @RequestParam("pwd") String pwd){

        if (loginusers.containsKey(usn)){
            Date login_time = loginusers.get(usn);
            Date time = new Date();
            //登陆时间小于300s
            if (loginPeriod.getDifference(login_time, time, 0) <= 300){
                return "Already logged in.";
            }
            else {
                loginusers.remove(usn);
            }
        }

        if (userinfo.containsKey(usn)){
            if (userinfo.get(usn).equals(pwd)){
                Date login_time = new Date();
                loginusers.put(usn, login_time);
                return "Login successfully!";
            }
            else{
                return "Wrong password";
            }
        }

        return "Username doesn't exist.";

    }

    @RequestMapping("/logout")
    public String login(@RequestParam("usn") String usn) {
        if (loginusers.containsKey(usn)){
            loginusers.remove(usn);
            return "Logout successfully!";
        }
        else{
            return "Already Logged out.";
        }
    }

    @RequestMapping("/wordsladder")
    public String Controller(@RequestParam("usn") String usn, @RequestParam("word1") String word1, @RequestParam("word2") String word2) {

        if (!loginusers.containsKey(usn)){
            return "Please log in at first.";
        }
        else {
            Date login_time = loginusers.get(usn);
            Date time = new Date();
            if(loginPeriod.getDifference(login_time, time, 0) > 300){
                return "Please log in at first.";
            }
            else{
                loginusers.put(usn, time);
            }
        }

        Map word_list = getWordlist.getDictionatyForWeb();
        String message = "";
        boolean invalid = false;
        if (word1 == "" || word2 == ""){
            message += "Word should not be empty.";
            return message;
        }
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