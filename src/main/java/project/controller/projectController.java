package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.dataService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class projectController {
    @Autowired()
    private dataService dataServices;

    @RequestMapping(value = "")
    public ModelAndView authorizationPage(HttpServletRequest request) {
        System.out.println(dataServices.getDecode(request.getCookies()));
        ModelAndView modelAndView = new ModelAndView("authorization");
        return modelAndView;
    }

    @RequestMapping(value = "/id{domain}")
    public ModelAndView chatPage(@PathVariable("domain") Integer id) {
        ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addObject("domain_id", id);
        return modelAndView;
    }

	@RequestMapping(value = "/test")
	public ModelAndView testtesttest(HttpServletRequest request) {
		return new ModelAndView("testView");
	}
}