package jp.co.sample.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 田高誠也
 *
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message = "名前は必須です。")
	private String name;
	/** メールアドレス*/
	@Size(min=1,max=120,message="メールアドレスは1文字以上120文字以内で記載してください。")
	@Email(message = "メールアドレスの形式が不正です")
	private String mailAddress;
	/** パスワード*/
	@Size(min=1,max=120,message="パスワードは1文字以上120文字以内で記載してください。")
	private String password;
	public String getName() {
		return name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}

}