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
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WordsLadderController {


    @GetMapping({"/", "/index", "/home"})
    @CrossOrigin
    public String root(){
        return "index";
    }

    @GetMapping("/login")
    @CrossOrigin
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    @CrossOrigin
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies !=  null){
            for (int i = 0; i < cookies.length; ++i){
                System.out.println("here");
                cookies[i].setMaxAge(0);
                cookies[i].setValue("");
                cookies[i].setPath("/");
                cookies[i].setDomain("127.0.0.1");
                response.addCookie(cookies[i]);
            }
        }
        return "login";
    }

    @GetMapping(value = "/login", params = "token")
    @CrossOrigin
    public String loginsuccess(@RequestParam("token") String token, HttpServletRequest request, HttpServletResponse response ){
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setDomain("127.0.0.1");
        cookie.setMaxAge(120);
        response.addCookie(cookie);
        return "user/user";
    }

    @GetMapping(value = "/login", params = "msg")
    @CrossOrigin
    public ModelAndView loginfail(@RequestParam("msg") String msg){
        List<String> messages = new ArrayList<>();
        messages.add(msg);
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("messages", messages);
        return mav;
    }

    @RequestMapping(path = "/wordladder", method = RequestMethod.GET)
    @CrossOrigin
    public ModelAndView wlGetController(HttpServletRequest request, ModelMap model){
        String msg = request.getParameter("msg");
        if (msg != null){
            return new ModelAndView("wordladder");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                System.out.println("Success");
                System.out.println(cookies[i].getValue());

            }
            model.put("token", cookies[0].getValue());
        }
        else{
            model.put("token", "no");
        }
        return new ModelAndView(new RedirectView("http://127.0.0.1:8082/check"), model);
    }

    @RequestMapping(path="/wordladder", method = RequestMethod.POST)
        public ModelAndView wlGetController(String word1, String word2,HttpServletRequest request) {

            word1 = request.getParameter("word1");
            word2 = request.getParameter("word2");
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
                if (splitstr.length == 1){
                    messages.add(message);
                }
                else{
                    messages.add(splitstr[0]+':');
                    messages.add(splitstr[1]);
                }

            }
            ModelAndView mav = new ModelAndView("wordladder");
            mav.addObject("messages", messages);
            return mav;
        }
}






