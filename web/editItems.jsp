<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商品信息</title>

</head>
<body>
<form id="itemForm" action="${pageContext.request.contextPath }/Teacher/inputGradeByEXCEL.action" method="post" enctype="multipart/form-data">
	<input type="file"  name="gradexcel"/>
	<input type="submit" value="提交"/>
</form>
</body>

</html>
