package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;



@Repository
public class AdministratorRepository {
	/** テンプレート作成 */
	@Autowired
	private NamedParameterJdbcTemplate template;
	/** RowMapper作成 */
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mailAddress"));
		administrator.setPassword("password");
		return administrator;
	};

	/** 管理者情報を挿⼊ */
	public void insert(Administrator administrator) {
		String sql = "INSERT INTO administrators(name,mail_address,password) values(:name,:email,:pass)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		template.update(sql, param);
	}

	/** メアドとパスから管理者情報を取得する */
	public Administrator findByMailAddressAndPassword(String email, String pass) {
		String sql = "SELECT name,mail_address,password  FROM administrators WHERE mail_address=:email AND password=:pass";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("pass", pass);

		try {
			return template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
		} catch (Exception e) {
			return null;
		}
	}
}
