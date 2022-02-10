package jp.co.sample.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * @author 田高誠也
 *
 */
public class LoginForm {
	/** メールアドレス */
	@Size(min=1,max=120,message="メールアドレスは1文字以上120文字以内で記載してください。")
	@Email(message = "Eメールの形式が不正です")
	private String mailAddress;
	/** パスワード */
	@Size(min=1,max=120,message="パスワードは1文字以上120文字以内で記載してください。")
	private String password;

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginForm [mailAddress=" + mailAddress + ", password=" + password + "]";
	}

}