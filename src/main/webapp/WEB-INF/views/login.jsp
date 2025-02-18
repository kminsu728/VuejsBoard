<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (username != null && password != null) {
        //String response = (new java.net.URL("http://localhost:8080/auth/login?username=" + username + "&password=" + password)).openStream().toString();
        //if ("success".equals(response)) {
        //    session.setAttribute("user", username);
            //response.sendRedirect("home.jsp");
    } else {
%>
<script>alert("로그인 실패! 아이디 또는 비밀번호를 확인하세요."); history.back();</script>
<%
    }

%>
