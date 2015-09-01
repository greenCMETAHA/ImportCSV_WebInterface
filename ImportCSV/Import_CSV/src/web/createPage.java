package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.CommandModule;
import command.ServiceModule;

/**
 * Servlet implementation class createPage
 */
@WebServlet("/createPage")
public class createPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String variantOfPage=(String)request.getParameter("pageType");
		String newPage="";
		if (("Back to menu".equals(variantOfPage) | (variantOfPage==null))){  	//jsp ��� ����
			newPage="/pages/Menu.jsp";
			
			
		}else if (("Import new users".equals(variantOfPage))){					//jsp ��� ������� ������ 
			request.setAttribute("filePath", "d:\\Import.txt");  //�������� ��� ������
			newPage="/pages/Import.jsp";
			
			
		}else if (("Import".equals(variantOfPage))){	
			CommandModule.ImportFile((String)request.getParameter("filePath"),(String)request.getParameter("update"));//���������������� - ������ ������� ��������������� ������. ��� �������: ������ �������� 
			int shirts=CommandModule.getNumberOfLists();
			request.setAttribute("listUsers", CommandModule.getUsers("login", 1));  //�������� ��� ������
			request.setAttribute("pageSort", "name");  //����� �������� � ����������
			request.setAttribute("pageNumber", 1);  //����� �������� � ����������
			request.setAttribute("prenumPage", 1);  //����� �������� ����� �������
			request.setAttribute("nextnumPage", (shirts>1?2:1));  //����� �������� ����� �������
			request.setAttribute("pagesCount", shirts);  //���������� ������� �����
			newPage="/pages/ShowData.jsp";
			
			
		}else if (("Show users".equals(variantOfPage))){						//��� ������� ��� ��������, � ������ ���������� � ����������
			String strPageNumber=(String)request.getParameter("pageNumber");
			int pageNumber=1;
			if (strPageNumber!=null){
				if (strPageNumber.length()>0){
					pageNumber=new Integer(strPageNumber);
				}
			}
			String strPageSort=(String)request.getParameter("pageSort");
			if (strPageSort==null){
				strPageSort=ServiceModule.SORT_BY_LOGIN;  //����� ����� �� ���������
			}
			
			int shirts=CommandModule.getNumberOfLists();
			request.setAttribute("pageNumber", pageNumber);  //����� �������� � ����������
			request.setAttribute("pageSort", strPageSort);  //����� �������� � ����������
			request.setAttribute("prenumPage", (pageNumber==1?1:pageNumber-1));  //����� �������� ����� �������
			request.setAttribute("nextnumPage", (shirts==pageNumber?shirts:pageNumber+1));  //����� �������� ����� �������
			request.setAttribute("pagesCount", shirts);  //���������� ������� �����
			request.setAttribute("listUsers", CommandModule.getUsers(strPageSort, pageNumber));  //�������� ��� ������
			
			newPage="/pages/ShowData.jsp";
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(newPage);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
