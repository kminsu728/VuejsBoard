<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JSP Page</title>
</head>
<body>

<jsp:include page="header.jsp" />

<div id="content-container">
    <jsp:include page="board_1.jsp" />
</div>

<jsp:include page="footer.jsp" />


<script>
    function loadContent(page) {
        fetch("/" + page)  // Controller URL 호출
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.text();
            })
            .then(html => {
                document.getElementById("content-container").innerHTML = html;
            })
            .catch(error => console.error("Error loading page:", error));
    }
</script>
</body>
</html>
