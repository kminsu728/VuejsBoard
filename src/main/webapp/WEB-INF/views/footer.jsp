<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Bottom Section</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-10">
            <p>&copy; Vuejs Board Site</p>
        </div>
    </div>
</div>


<h2>댓글 알림 시스템</h2>
<script>
    let stompClient = null;
    let notificationContainer = null;

    function connect() {
        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            // "/topic/notifications" 구독하여 메시지 수신
            stompClient.subscribe('/topic/notifications', function (notification) {
                showNotification(JSON.parse(notification.body));
            });
        });
    }

    function showNotification(notification) {
        console.log("notification : " + notification);

        if (!notificationContainer) {
            notificationContainer = document.createElement("div");
            notificationContainer.style.position = "fixed";
            notificationContainer.style.top = "20px";
            notificationContainer.style.right = "20px";
            notificationContainer.style.zIndex = "9999"; // 최상위에 배치
            notificationContainer.style.display = "flex";
            notificationContainer.style.flexDirection = "column";
            notificationContainer.style.gap = "10px";
            document.body.appendChild(notificationContainer);
        }

        let div = document.createElement("div");
        div.style.padding = "15px";
        div.style.backgroundColor = "rgba(0, 0, 0, 0.85)";
        div.style.color = "white";
        div.style.borderRadius = "8px";
        div.style.boxShadow = "0px 4px 10px rgba(0,0,0,0.3)";
        div.style.opacity = "0"; // 처음에 투명
        div.style.transform = "translateX(50px)"; // 오른쪽에서 등장
        div.style.transition = "opacity 0.5s ease-out, transform 0.5s ease-out";
        div.style.maxWidth = "300px";
        div.innerHTML = '<strong>' + notification.title + '</strong><br>' + notification.content;

        notificationContainer.appendChild(div);

        // 애니메이션 시작
        setTimeout(() => {
            div.style.opacity = "1";
            div.style.transform = "translateX(0)";
        }, 10);

        // 3초 후 사라지는 애니메이션 적용 후 삭제
        setTimeout(() => {
            div.style.opacity = "0";
            div.style.transform = "translateX(50px)";
            setTimeout(() => {
                notificationContainer.removeChild(div);
                if (notificationContainer.children.length === 0) {
                    document.body.removeChild(notificationContainer);
                    notificationContainer = null;
                }
            }, 500); // 애니메이션 시간 고려하여 500ms 후 삭제
        }, 3000);
    }

    connect();
</script>

</body>
</html>
