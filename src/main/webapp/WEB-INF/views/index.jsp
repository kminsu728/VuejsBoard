<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JSP Page</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
<header class="header sticky-top">
<jsp:include page="header.jsp" />
</header>


<main class="body">
    <jsp:include page="board1.jsp" />
</main>

<footer class="footer fixed-bottom">
<jsp:include page="footer.jsp" />
</footer>

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
