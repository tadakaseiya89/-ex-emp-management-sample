package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * @author 田高誠也
 *
 */
@Controller
@RequestMapping("")
public class AdministratorController {
	@Autowired
	private AdministratorService service;
	@Autowired
	private HttpSession session;

	/** InsertAdministratorFormをインスタント化しそのままreturnする */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	/** 「administrator/insert.html」にフォワードする */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/** 管理者情報を登録する */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administrator.setPass(form.getPassword());
		administrator.setEmail(form.getMailAddress());
		service.insert(administrator);
		return "/toInsert";
	}

	/** LoginFormをインスタント化しそのままreturnする */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/** 「administrator/login.html」にフォワードする */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator administrator = service.login(form.getMailAddress(), form.getPassword());
		if (administrator == null) {
			model.addAttribute("not", "メールアドレスまたはパスワードが不正です。");
			return "forward:/";
		} else {
			session.setAttribute("administratorName", administrator);
			return "forward:/employee/showList";
		}
	}
}