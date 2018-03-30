<%--
  Created by IntelliJ IDEA.
  User: Nurzhan
  Date: 08.11.2017
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../jsp/static.jsp"/>
</head>
<body>
<div id="el" class="col-md-12">
    <div class="row">
        <div class="col-md-3">
            <div class="row">
                <div class="col-md-2" style="background-color: #313340">
                    <div class="row" style="height: 100vh;">

                    </div>
                </div>
                <div class="col-md-10" style="background-color: #373a49">
                    <div class="row" style="height: 100vh;">
                        <div class="row">
                            <div class="col-md-12" style="color: whitesmoke">
                                Входящие<br>
                                <div v-for="user in users"
                                     v-on:click="saveSessionId(user.id)">
                                    <label v-if="user.user == null">Юзер-{{user.id}}</label>
                                    <label v-if="user.user != null">{{user.user}}</label>
                                    <hr style="margin-bottom: 8px; margin-top: 4px;">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="row" style="overflow: hidden">
                <div id="messagePanel" style="overflow-y: scroll; height: 90vh;">
                    <div class="col-md-12" v-for="item in message.message">
                        <div>{{item.dateCreateFormat}}</div>
                        <div>{{item.text}}</div>
                        <hr>
                    </div>
                </div>
                <div style="height: 10vh;">
                    <div class="col-md-10">
                        <textarea class="form-control" v-model="setMessage">

                        </textarea>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-primary form-control"
                                v-on:click="saveMessage()">Отправить</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var app = new Vue({
        el: '#el',
        data: function () {
            return {
                _scroll: false,
                lastId: 0,
                domain_id: ${domain},
                users: [],
                message: {},
                setMessage: '',
                session: 0,
                startInterval: setInterval(function () {
                    if (app.session) app.getUserMessage(app.session);
                }, 2000)
            }
        },
        methods: {
            getUsers: function () {
                this.$http.get("/data/sessions").then(
                    function (response) {
                        this.users = response.data;
                    }, function (error) {
                        console.log(error);
                    }
                );
            },
            saveSessionId: function (id) {
                this.session = id;
                this._scroll = true;
                this.getUserMessage(this.session);
            },
            getUserMessage: function (id) {
                this.$http.get("/data/session/" + id).then(
                    function (response) {
                        this.message = response.data;
                        if (this._scroll) {
                            setTimeout(function () {
                                document.getElementById("messagePanel").scrollTop = document.getElementById("messagePanel").scrollHeight;
                                app._scroll = false;
                            }, 100);
                        }
                        for (let i = 0; i < this.message.message.length; i++) {
                            if (this.message.message[i].id > this.lastId)
                                this.lastId = this.message.message[i].id;

                        }
                        console.log(this.lastId);
                    }, function (error) {
                        console.log(error)
                    }
                );
            },
            saveMessage: function () {
                let _save = {};
                _save.domain_id = this.message.domain_id;
                _save.session_id = this.message.id;
                _save.text = this.setMessage;
                this.$http.post("/data/save", _save).then(
                    function (response) {
                        app.setMessage = '';
                        app._scroll = true;
                        app.getUserMessage(response.data);
                    }, function (error) {
                        console.log(error)
                    }
                )
            }
        },
        mounted: function () {
            this.getUsers();
        },
        watch: {

        },
        computed: {

        }
    })
</script>
</body>
</html>
