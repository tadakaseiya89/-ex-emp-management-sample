package ex.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import ex.sample.domain.Employee;

public class EmployeeRepository {
	/** テンプレート作成 */
	@Autowired
	private NamedParameterJdbcTemplate template;
	/** RowMapper作成 */
	private static final RowMapper<Employee> EMP_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hireDate"));
		employee.setMailAddress(rs.getString("mailAddress"));
		employee.setZipCode(rs.getString("zipCode"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependentsCount"));
		return employee;
	};

	// ** 従業員⼀覧情報を⼊社⽇順(降順)で取得する*/
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM administrators ODER BY hire_date DESC";
		List<Employee> employeeList = template.query(sql, EMP_ROW_MAPPER);
		if (employeeList.size() == 0) {
			return null;
		}
		return employeeList;
	}

	// **主キーから従業員情報を取得する*/
	public Employee load(Integer id) {
		String spl = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM administrators WHERE id=:id";
		SqlParameterSource pram = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(spl, pram, EMP_ROW_MAPPER);
		return employee;
	}

	// **従業員情報を変更する*/
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		if (employee.getId() == null) {
			String sql = "INSERT INTO administrators(id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count) VALUES (:name,:image,:gender,:hireDate,:mailAddress,:zipCode,:address,:telephone,:salary,:characteristics,:dependentsCount)";
			KeyHolder keyHandler = new GeneratedKeyHolder();
			String[] keyNanber = { "id" };
			template.update(sql, param, keyHandler, keyNanber);
			employee.setId(keyHandler.getKey().intValue());
			System.out.println("idが無かったため追加しました。");

		} else {
			String sql = "UPDATE administrators SET name=:name,image=:image,gender=:gender,hire_date=:hireDate,mail_address=:mailAddress,zip_code=:zipCode,address=:address,telephone=:telephone,salary=:salary,characteristics=:characteristics,dependents_count=:dependentsCount WHERE id=:id";
			template.update(sql, param);
		}
	}
}
