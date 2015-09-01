package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import beans.User;
import dao.DAO;

/**
 * @author ����������� ����, 
 * 27.08.2015
 * ����� ��� ��������� ������ �����/����� ������ � ��
 *
 */
public class CommandModule {
	
	public CommandModule() {
	}
	
	/**
	 * ����� ��� �������� �� ����������� ����� �������
	 */	
	private static boolean isFileExist(String strFile){ 
		boolean result=false;
		
		File file=new File(strFile);
		result=file.exists();
		
		return result;
	}

	/**
	 * ����� ��� ������������ ���� User �� ������
	 *
	 */
	private static void CreateUser(String currentLine, ArrayList<User> listUsers){
		char divider=ServiceModule.CSV_DIVIDER;   // ����������� ����� � CSV-�����
		
		String str=currentLine;
		if (str.indexOf(divider)!=-1){  //���� ���� ��-���� ��������� ������. ���� ��� - �� ����� �������� ��������
			User currentUser=new User();
			String value="";
			value=str.substring(0, str.indexOf(divider));  //�� ������� ����������� - name
			if (value!=null){
				currentUser.setName(value);
			}
			str=str.substring(str.indexOf(divider)+1);
			
			if (str.indexOf(divider)!=-1){					//�� ������� ����������� - surname
				value=str.substring(0, str.indexOf(divider));
				if (value!=null){
					currentUser.setSurname(value);
				}
				str=str.substring(str.indexOf(divider)+1);
			}	
			if (str.indexOf(divider)!=-1){					//�� �������� ���������� - �����
				value=str.substring(0, str.indexOf(divider));
				if (value!=null){
					currentUser.setLogin(value);
				}else{										//����� �� ������ ���� ������, ��� �������� ����
					System.out.println("Fild 'login' is empty: "+currentUser.getName());
					return;
				}
				str=str.substring(str.indexOf(divider)+1);
			}
			if (str.indexOf(divider)!=-1){					//�� ��������� ���������� - �����
				value=str.substring(0, str.indexOf(divider));
				if (value!=null){
					currentUser.setEmail(value);
				}
				str=str.substring(str.indexOf(divider)+1);
			}
			if (str.length()>0){							//���� ���-�� �������� - �������
				currentUser.setPhone(str);
			}
			if (!currentUser.isEmpty()){  //���� ����� � ������ CSV-����� ���� �������� ������ - ����� ����� � ���������
				listUsers.add(currentUser);
			}
		}
	}

	/**
	 * ����� ��� �������� ����� �������
	 */		
	public static synchronized boolean ImportFile(String strFile, String update){
		boolean result=false;
		
		ArrayList<User> listUsers=new ArrayList<User>();
		try{
		
			if (isFileExist(strFile)){		//�������� �� ����������� �����
				String currentLine;
				BufferedReader buffReader=new BufferedReader(new FileReader(strFile.trim()));
				while (( currentLine = buffReader.readLine()) != null) { //������ ������
					CreateUser(currentLine, listUsers);					 //���������� ������������ � ���������
			        }
			}
			result=true;
		}catch(Exception e){
			System.out.println("Can not read CSV-file: "+strFile);
			//e.printStackTrace();
		}
			
		DAO.startDAO().importUsers(listUsers,new Integer(update));   //���������� ��������� ���������� � ��
		
		return result;
	}

	/**
	 * ����� ��� �������� ���������� ������ ��� ����������
	 */		
	public static int getNumberOfLists(){
		int result=0;
		
		int numberOfUsers=DAO.startDAO().getNumberOfUsers();				//������
		result=(int)(numberOfUsers/ServiceModule.ROWS_ON_PAGE);
		if (numberOfUsers%ServiceModule.ROWS_ON_PAGE>0) 
			result++;
		
		if (result==0){
			result++;  //����� 1 �������� �������. ���� � ������, ���� ������ ��� �� ���������.
		}
		return result;
	}

	/**
	 * ����� ��� ��������� ������ �� ��
	 */		
	public static ArrayList getUsers(String sortVariant, int pageNumber){
		ArrayList<User> result;
	
		result=(ArrayList)DAO.startDAO().showUsers(sortVariant, pageNumber-1);
	
		return result;
	}
	

}
