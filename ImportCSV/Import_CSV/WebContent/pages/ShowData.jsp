<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Распечатка данных в базе</title>
</head>
<body>
<c:set var="numPage" value="${requestScope.pageNumber}"></c:set>
Список импортированных пользователей:
<!-- шапка с кнопками сортировки  -->
<form action="./createPage">
	<table width="100%">
		<tr>
			<td width="300" bgcolor="#eeeeee" valign="top">
				<table>
					<tr>
						<td align="left">Name</td>
						<td align="right">
							<c:url value="./createPage" var="CategoryURL">
					  			 <c:param name="pageType" value="Show users"/>
					  			 <c:param name="pageNumber" value="${numPage}"/>
					  			 <c:param name="pageSort" value="name"/>
							</c:url>
							<a href="${CategoryURL}"><img src="resources/sort_icon.png" width="15" height="15" ></a>	
						</td>
					</tr>
				</table>	
			</td>
			<td width="300" bgcolor="#eeeeee" valign="top">
				<table>
					<tr>
						<td align="left">Surname</td>
						<td align="right">
							<c:url value="./createPage" var="CategoryURL">
					  			 <c:param name="pageType" value="Show users"/>
					  			 <c:param name="pageNumber" value="${numPage}"/>
					  			 <c:param name="pageSort" value="Surname"/>
							</c:url>
							<a href="${CategoryURL}"><img src="resources/sort_icon.png" width="15" height="15" ></a>
						</td>
					</tr>
				</table>	
			</td>
			<td width="300" bgcolor="#eeeeee" valign="top">
				<table>
					<tr>
						<td align="left">Login</td>
						<td align="right">
							<c:url value="./createPage" var="CategoryURL">
					  			 <c:param name="pageType" value="Show users"/>
					  			 <c:param name="pageNumber" value="${numPage}"/>
					  			 <c:param name="pageSort" value="login"/>
							</c:url>
							<a href="${CategoryURL}"><img src="resources/sort_icon.png" width="15" height="15" ></a>
						</td>
					</tr>
				</table>	
			</td>
			<td width="300" bgcolor="#eeeeee" valign="top">
				<table>
					<tr>
						<td align="left">e-mail</td>
						<td align="right">
							<c:url value="./createPage" var="CategoryURL">
					  			 <c:param name="pageType" value="Show users"/>
					  			 <c:param name="pageNumber" value="${numPage}"/>
					  			 <c:param name="pageSort" value="email"/>
							</c:url>
							<a href="${CategoryURL}"><img src="resources/sort_icon.png" width="15" height="15" ></a>
						</td>
					</tr>
				</table>	
			</td>
			<td width="300" bgcolor="#eeeeee" valign="top">
				<table>
					<tr>
						<td align="left">Phone number</td>
						<td align="right">
							<c:url value="./createPage" var="CategoryURL">
					  			 <c:param name="pageType" value="Show users"/>
					  			 <c:param name="pageNumber" value="${numPage}"/>
					  			 <c:param name="pageSort" value="phone"/>
							</c:url>
							<a href="${CategoryURL}"><img src="resources/sort_icon.png" width="15" height="15" ></a>
						</td>
					</tr>
				</table>	
			</td>
	</tr>
	
	<!-- вывод данных -->
	<c:forEach var="currentUser" items="${requestScope.listUsers}">
	<tr>
		<td align="left">
			<c:out value="${currentUser.getName()}" /> 
		</td>
		<td align="left">
			<c:out value="${currentUser.getSurname()}" /> 
		</td>
	 	<td align="left">
			<c:out value="${currentUser.getLogin()}"  />
		</td>
		<td align="left">
			<c:out value="${currentUser.getEmail()}"  />
		</td>
		<td align="left">
			<c:out value="${currentUser.getPhone()}"  />
		</td>   
	</tr>
	</c:forEach>
	</table>
	<!-- пэджинация -->
	<hr>
	<c:set var="pagesQuantity" value="${requestScope.pagesCount}"></c:set>
	<table width="100%">
		<tr>
			<td align="left">
				<c:url value="./createPage" var="CategoryURL">
					<c:param name="pageType" value="Show users"/>
					<c:param name="pageNumber" value="1"/>
				</c:url>
				<a href="${CategoryURL}">&lt&lt </a>
				<c:set var="prenumPage" value="${requestScope.prenumPage}"></c:set>
				<c:url value="./createPage" var="CategoryURL">
					<c:param name="pageType" value="Show users"/>
					<c:param name="pageNumber" value="${prenumPage}"/>
				</c:url>
				<a href="${CategoryURL}"> &lt </a>
				<c:set var="nextnumPage" value="${requestScope.nextnumPage}"></c:set>
				<c:url value="./createPage" var="CategoryURL">
					<c:param name="pageType" value="Show users"/>
					<c:param name="pageNumber" value="${nextnumPage}"/>
				</c:url>
				<a href="${CategoryURL}"> &gt </a>
				<c:url value="./createPage" var="CategoryURL">
					<c:param name="pageType" value="Show users"/>
					<c:param name="pageNumber" value="${pagesQuantity}"/>
				</c:url>
				<a href="${CategoryURL}">&gt&gt </a>
			</td>
			<td align="right">
				(Page  <c:out value="${numPage}" /> from <c:out value="${pagesQuantity}" /> )
			</td>
		</tr>
	</table>
	<p>
	<p>
	<input type="submit" name="pageType" value="Back to menu">
</form>


</body>
</html>