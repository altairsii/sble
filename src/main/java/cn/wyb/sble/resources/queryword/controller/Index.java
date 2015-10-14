package cn.wyb.sble.resources.queryword.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Index {

	@RequestMapping("/index")
	public ModelAndView  index(Model model){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello.html");
		Map<String,String> root = new HashMap<String,String>();
		root.put("user", "wyb");
		mv.addObject("root",root);
		return mv;
	}
}
