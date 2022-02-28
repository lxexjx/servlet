<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <meta charset="UTF-8">
</head>
<body>
성공
<ul>
 <li>id=${member.id}</li>   //id=<%=((Member)request.getAttribute("member")).getID()%>
 <li>username=${member.username}</li>   //username=<%=((Member)request.getAttribute("member")).getUsername()%>
 <li>age=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>