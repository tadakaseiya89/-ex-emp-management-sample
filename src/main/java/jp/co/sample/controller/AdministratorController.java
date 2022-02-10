package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm administratorForm = new InsertAdministratorForm();
		return administratorForm;
	}
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	@RequestMapping("/insert")
	public String insert(@Validated InsertAdministratorForm form,BindingResult result) {
		if(result.hasErrors()) {
		return toInsert();
		}
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administratorService.insert(administrator);
		return "redirect:/";
	}
	
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/** ログアウトする機能*/
		@RequestMapping("/login")
		public String login(LoginForm form,Model model) {
			Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
			
			if(administrator == null) {
				model.addAttribute("notAdministrator","メールアドレスまたはパスワードが不正です。");
				
				return "administrator/login";
			}else {
				session.setAttribute("administratorName", administrator.getName());
				return "forward:/employee/showList";
			}
		}
		
		@RequestMapping("/logout")
		public String logout() {
			session.invalidate();
			return "redirect:/";
		}
		
	}