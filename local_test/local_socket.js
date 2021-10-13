let socket;

function openSocket() {
    if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    } else {
        console.log("您的浏览器支持WebSocket");
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        //等同于socket = new WebSocket("ws://localhost:8888/xxxx/im/25");
        //var socketUrl="${request.contextPath}/im/"+$("#userId").val();
        let socketUrl = `http://localhost:9090/socket/${$("#account").val()}/${token}`;
        socketUrl = socketUrl.replace("https", "ws").replace("http", "ws");
        console.log(socketUrl);
        if (socket != null) {
            socket.close();
            socket = null;
        }
        socket = new WebSocket(socketUrl);
        // 初始化回调函数内容
        //打开事件
        socket.onopen = function () {
            console.log("websocket已打开");
            //socket.send("这是来自客户端的消息" + location.href + new Date());
        };
        //获得消息事件
        socket.onmessage = function (msg) {
            console.log(msg.data);
            //发现消息进入    开始处理前端触发逻辑
        };
        //关闭事件
        socket.onclose = function () {
            console.log("websocket已关闭");
        };
        //发生了错误事件
        socket.onerror = function () {
            console.log("websocket发生了错误");
        };
        // 收到消息 展示
        socket.onmessage = function (event) {
            let result = jQuery.parseJSON(event.data);
            if ("need" === result.login) {
                document.getElementById('message').innerHTML += "please login first" + '<br/>';
                closeSocket();
                window.location.href = `index.html`;
            } else
                document.getElementById('message').innerHTML += event.data + '<br/>';
        }
    }
}

function sendMessage() {
    let toUser = selectedItem.account;
    let content = $("#contentText");
    if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    } else {
        console.log("您的浏览器支持WebSocket");
        console.log('{"toUser":"' + toUser + '","contentText":"' + content.val() + '"}');
        socket.send('{"toUser":"' + toUser+ '","contentText":"' + content.val() + '"}');
    }
}

function closeSocket() {
    if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    } else {
        console.log("您的浏览器支持WebSocket");
        socket.close();
    }
}