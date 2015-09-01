/**
 * 
 */
package beans;

/**
 * @author Васильченко Глеб, 
 * 27.08.2015
 * Бин для записи таблицы Users БД Import_CSV
 *
 */
public class User {
	private String name;	
	private String surname;
	private String login;
	private String email;
	private String phone; //phone number
	
	public User(){
		name="";
		surname="";
		login="";
		email="";
		phone="";
		
	}
	
	public User(String name, String surname, String login, String email, String phone) {
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.email = email;
		this.phone = phone;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString(){
		String result;
		
		result=getName()+", "+getSurname()+", "+getLogin()+", "+getEmail()+", "+getPhone();
		
		return result;
	}

	/**
	 * Метод проверяет, можно ли грузить строку из файла-импорта в БД: если нет ключевого поля - нельзя
	 */	
	public boolean isEmpty(){  //если вдруг в строке CSV-файла не было значимых данных
		boolean result=false;
		
		if (((getName()+", "+getSurname()+", "+getLogin()+", "+getEmail()+", "+getPhone()).trim().length()==0) 
				| (getLogin().length()==0)){
			result=true;
		}
		
		return result;
		
	}
	
}
