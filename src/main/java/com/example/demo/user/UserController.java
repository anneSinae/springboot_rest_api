package com.example.demo.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.User;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired UserService userService;
	
	@GetMapping("login")
	public String login(HttpServletRequest req) {
		req.getSession().setAttribute("prevPage", req.getHeader("Referer"));
		System.out.println("---getSession prev " + req.getAttribute("prevPage"));
		return "user/login";
	}
	
	@GetMapping("signup")
	//@RequestMapping(value="/user/signup", method=RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("test", "sinae");
		return "user/signup";
	}
	
	@PostMapping("signupProc")
	public String signupProc(User user) {
		userService.createUser(user);
		return "user/login";
	}
	
	/*
	@GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }
    */
	
	@ResponseBody
	@GetMapping("/signup/duplicated")
	public int chkDuplicatedId(@RequestParam String id) {
		return userService.chkDuplicatedId(id);
	}
}