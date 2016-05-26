package com.izeye.throwaway;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by izeye on 15. 10. 7..
 */
@Controller
@RequestMapping(path = "/test")
public class TestController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		return "test";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String post(@RequestParam String name) {
		return name;
	}
	
	@RequestMapping(path = "/redirect_to_test", method = RequestMethod.GET)
	public String redirectToTest() {
		return "redirect:/test";
	}

	@RequestMapping(path = "/redirect_to_does_not_exist", method = RequestMethod.GET)
	public String redirectToDoesNotExist() {
		return "redirect:/{doesNotExist}";
	}

	@RequestMapping(path = "/redirect_to_does_not_exist_with_custom_redirect_view",
			method = RequestMethod.GET)
	public RedirectView redirectToDoesNotExistWithCustomRedirectView() {
		RedirectView redirectView = new RedirectView("{doesNotExist}");
		redirectView.setExpandUriTemplateVariables(false);
		return redirectView;
	}
	
}