package com.example.demo.controller;

import com.example.demo.entity.MyFood;
import com.example.demo.entity.User;
import com.example.demo.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("sign")
public class SignController {

    private final SignService signService;

    @RequestMapping(value = "/in", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity get_sign_in(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            // null로 보내버리면 애초에 success가 되므로 값을 넘김.
            return ResponseEntity.ok("0");
        }
        Long user_id = user.getIdx();
        return ResponseEntity.status(HttpStatus.OK).body(signService.getMyFood(user_id));
    }

    @RequestMapping(value = "/in", method = RequestMethod.POST)
    public String sign_in(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User getUser = signService.getAccount(user);
        if(getUser != null) {
            session.setAttribute("user", getUser);
            return "redirect:/";
        }
        return "sign_in";
    }

    @RequestMapping(value = "/up")
    public String get_sign_up() {
        return "sign_up";
    }

    @RequestMapping(value = "/up", method = RequestMethod.POST)
    public String sign_up(User user) {
        signService.createAccount(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/out", method = RequestMethod.POST)
    @ResponseBody
    public void sign_out(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void add(@RequestBody MyFood myFood, HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        signService.add(user, myFood);
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    public void delete(@RequestParam("query") String query) {
        signService.delete(query);
    }
}
