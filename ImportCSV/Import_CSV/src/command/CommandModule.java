package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import beans.User;
import dao.DAO;

/**
 * @author Васильченко Глеб, 
 * 27.08.2015
 * Класс для обработки данных перед/после работы с БД
 *
 */
public class CommandModule {
	
	public CommandModule() {
	}
	
	/**
	 * Метод для проверки на присутствие файла импорта
	 */	
	private static boolean isFileExist(String strFile){ 
		boolean result=false;
		
		File file=new File(strFile);
		result=file.exists();
		
		return result;
	}

	/**
	 * Метод для формирования бина User из строки
	 *
	 */
	private static void CreateUser(String currentLine, ArrayList<User> listUsers){
		char divider=ServiceModule.CSV_DIVIDER;   // разделитель полей в CSV-файле
		
		String str=currentLine;
		if (str.indexOf(divider)!=-1){  //если файл всё-таки соблюдает формат. Если нет - всё равно ошибочно загрузит
			User currentUser=new User();
			String value="";
			value=str.substring(0, str.indexOf(divider));  //до первого разделителя - name
			if (value!=null){
				currentUser.setName(value);
			}
			str=str.substring(str.indexOf(divider)+1);
			
			if (str.indexOf(divider)!=-1){					//до второго разделителя - surname
				value=str.substring(0, str.indexOf(divider));
				if (value!=null){
					currentUser.setSurname(value);
				}
				str=str.substring(str.indexOf(divider)+1);
			}	
			if (str.indexOf(divider)!=-1){					//до третьего разделитея - логин
				value=str.substring(0, str.indexOf(divider));
				if (value!=null){
					currentUser.setLogin(value);
				}else{										//логин не должен быть пустым, это ключевое поле
					System.out.println("Fild 'login' is empty: "+currentUser.getName());
					return;
				}
				str=str.substring(str.indexOf(divider)+1);
			}
			if (str.indexOf(divider)!=-1){					//до четвёртого разделитея - почта
				value=str.substring(0, str.indexOf(divider));
				if (value!=null){
					currentUser.setEmail(value);
				}
				str=str.substring(str.indexOf(divider)+1);
			}
			if (str.length()>0){							//если что-то осталось - телефон
				currentUser.setPhone(str);
			}
			if (!currentUser.isEmpty()){  //если вдруг в строке CSV-файла были значимые данные - внесём юзера в коллекцию
				listUsers.add(currentUser);
			}
		}
	}

	/**
	 * Метод для парсинга файла импорта
	 */		
	public static synchronized boolean ImportFile(String strFile, String update){
		boolean result=false;
		
		ArrayList<User> listUsers=new ArrayList<User>();
		try{
		
			if (isFileExist(strFile)){		//проверка на присутствие файла
				String currentLine;
				BufferedReader buffReader=new BufferedReader(new FileReader(strFile.trim()));
				while (( currentLine = buffReader.readLine()) != null) { //читаем строку
					CreateUser(currentLine, listUsers);					 //закидываем пользователя в коллекцию
			        }
			}
			result=true;
		}catch(Exception e){
			System.out.println("Can not read CSV-file: "+strFile);
			//e.printStackTrace();
		}
			
		DAO.startDAO().importUsers(listUsers,new Integer(update));   //полученную коллекцию записываем в БД
		
		return result;
	}

	/**
	 * Метод для рассчета количества листов при педжинации
	 */		
	public static int getNumberOfLists(){
		int result=0;
		
		int numberOfUsers=DAO.startDAO().getNumberOfUsers();				//получи
		result=(int)(numberOfUsers/ServiceModule.ROWS_ON_PAGE);
		if (numberOfUsers%ServiceModule.ROWS_ON_PAGE>0) 
			result++;
		
		if (result==0){
			result++;  //будет 1 страница минимум. Хоть и пустая, если данные ещё не загрузили.
		}
		return result;
	}

	/**
	 * Метод для получения данных из БД
	 */		
	public static ArrayList getUsers(String sortVariant, int pageNumber){
		ArrayList<User> result;
	
		result=(ArrayList)DAO.startDAO().showUsers(sortVariant, pageNumber-1);
	
		return result;
	}
	

}
