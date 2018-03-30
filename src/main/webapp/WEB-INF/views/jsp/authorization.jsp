<%--
  Created by IntelliJ IDEA.
  User: Nurzhan
  Date: 27.11.2017
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Авторизация</title>
    <jsp:include page="../jsp/static.jsp"/>

</head>
<style>
    * {
        margin: 0;
        padding: 0;
    }

    body, html {
        height: 100%;
        background: url("/resources/core/img/auth-background.jpg");
        background-size: cover;
    }

    .container-auth {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
    }

    .auth-div {
        background: #f2f2f2;
        border-radius: 10px;
        padding-bottom: 15px;
        padding-top: 15px;
        top: -50px;
    }

    .auth-link {
        font-size: 11px;
        color: white;
        padding-top: 5px;
        top: -50px;
    }

</style>
<body onload="deleteCookie()">
<div id="el"><%--Сообщение--%>
    <div id="userMessge" class="modal fade" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h5 class="modal-title" align="center">Написать письмо</h5>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            Для
                            <input class="form-control input-sm" value="nurzhansagatov@gmail.com" readonly>
                        </div>
                        <div class="col-md-12">
                            От <label style="color: red">*</label>
                            <input id="guestUserInputID" class="form-control input-sm" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            Текст <label style="color: red">*</label>
                            <textarea id="guestUserMessageID" class="form-control" style="resize: none" rows="3"></textarea>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-12" align="right">
                            <button id="buttonCloseId" type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                            <button type="button" class="btn btn-primary" onclick="postGuestMessage()">Отправить</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container-auth">
        <div class="row" style="width: 200px;">
            <div class="col-md-12 auth-div">
                Логин: <input id="login" class="form-control" onkeydown="EnterKeyWait(event.keyCode)">
                Пароль: <input id="password" type="password" class="form-control" onkeydown="EnterKeyWait(event.keyCode)">
                <h6 id="error-label" align="center" style="color: #d82424; padding-top: 5px; padding-bottom: 5px"></h6>
                <button class="form-control btn-info" @click="postAuth()">Войти</button>
            </div>
            <div class="col-md-12 auth-link" align="center">
                <a class="auth-link" href="#userMessge" data-toggle="modal">nurzhansagatov@gmail.com</a>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var app = new Vue({
        el: '#el',
        data: function () {
            return {

            }
        },
        methods: {
            postAuth: function () {
                var inspection = {
                    login: document.getElementById("login").value,
                    password:  document.getElementById("password").value
                };
                this.$http.post("/data/auth", inspection).then(
                    function (response) {
                        console.log(response.data);
                        var obj = response.data;
                        if (obj.access) {
                            document.cookie = 'FDBKJWT=' + obj.jwt;
                            document.cookie = 'FDBKSUP=' + obj.supportName;
                        }
//                        document.cookie = 'FDBKSUP=' + encodeURIComponent(response.data[0]);
//                        console.log(decodeURIComponent(getCookie("FDBKSUP")));
//                        var _arr = decodeURIComponent(getCookie("FDBKSUP")).split(",");
//                        for (var i = 0; i < _arr.length; i++) {
//                            console.log(_arr[i]);
//                        }
                    }, function (error) {
                        console.log(error);
                    }
                );
            },
        },
        mounted: function () {
        },
        watch: {
        },
        computed: {
        }
    })
</script>
<script>
    var _error = document.getElementById("error-label");

    /*Выход из системы*/
    function deleteCookie() {
//        document.cookie = "FDBKSUP=";
//        document.cookie = "supportToken=";
//        console.log(document.cookie);
    }

    /*Отлов Enter*/
    function EnterKeyWait(code) {
        if (code == 13) getUserAccess();
    }

    /*Авторизация пользователя*/
//    function getUserAccess() {
//        var _login = document.getElementById("login").value;
//        var _pass = document.getElementById("password").value;
//        if (_login && _pass) {
//            $.getJSON('/data/user-access/' + _login + '/' + _pass, {}, function (json) {
//                if (json) {
//                    document.cookie = "FDBKSUP=" + _login;
////                console.log(document.cookie);
//                    location.reload();
//                } else {
//                    _error.innerHTML = "Ошибка авторизации!";
//                }
//            })
//        } else {
//            var _mess = "Для входа в систему нужно ввести ";
//            if (!_login) _mess += "\"Логин\"";
//            if (!_login && !_pass) _mess += " и ";
//            if (!_pass) _mess += "\"Пароль\"";
//            _mess += "!";
//            _error.innerHTML = _mess;
//        }
//    }

    /*Сообщение админу*/
//    function postGuestMessage() {
//        var _json = {
//            guest: document.getElementById("guestUserInputID").value,
//            text: document.getElementById("guestUserMessageID").value
//        };
//        if (_json.guest && _json.text) {
//            jQuery.ajax({
//                method: 'POST',
//                url: "/data/guest-message",
//                data: _json,
//                dataType: 'json',
//                contentType: 'text/plain',
//                success: function (data, textStatus, jqXHR) {
//                    alert("Сообщение отправлено!");
//                    $("#buttonCloseId").click();
//                }
//            })
//        } else {
//            alert("Введите обязательные поля!")
//        }
//    }
</script>


</html>

