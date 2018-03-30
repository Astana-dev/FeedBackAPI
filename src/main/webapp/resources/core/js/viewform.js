/**
 * Created by Nurzhan on 31.10.2017.
 */
window.onload = function () {
    setTimeout(function () {
        let x = new XMLHttpRequest();
        x.open("GET", "http://localhost/view/form-1", true);
        x.onload = function (data) {
            createFormFB(data, document.createElement('div'));
            createCoockie();
        };
        x.send(null);
    }, 4000);
};

function createFormFB(form, d) {
    d.style.position = "fixed";
    d.style.zIndex = "999999";
    d.style.bottom = "20px";
    d.style.right = "20px";
    d.id = "getFeedBack";
    document.body.appendChild(d);
    document.getElementById("getFeedBack").innerHTML = form.target.response;
}
let maxId = 0;
function createCoockie() {
    let ses = new XMLHttpRequest();
    ses.open("GET", "http://localhost/data/session", true);
    ses.onload = function (data) {
        let session = JSON.parse(data.target.response);
        document.cookie = "FDBKAPI=" + session.id;
        document.cookie = "FDBKD=" + session.domain_id;
        createMessage(session.message);
        setInterval(getMessage, 2000);
    };
    ses.send(null);
}

function getMessage() {
    let _res = '';
    let ses = new XMLHttpRequest();
    ses.open("GET", "http://localhost/data/message/" + maxId, true);
    ses.onload = function (data) {
        let message = JSON.parse(data.target.response);
        createMessage(message)
    };
    ses.send(null);
}

function createMessage(data) {
    let _res = '';
    if (data == null) return;
    if (data.length) {
        data.sort((a, b) => {
            if (a.id < b.id) return -1;
            if (a.id > b.id) return 1;
            return 0;
        });
        for (let i = 0; i < data.length; i++) {
            _res += "<hr><div>" + data[i].dateCreateFormat + "</div>";
            _res += "<div style='word-wrap: break-word'>" + data[i].text + "</div>";
            if (data[i].id > maxId)
                maxId = data[i].id;
        }
        document.getElementById("FeedBackMessage").innerHTML += _res;
        document.getElementById("FeedBackMessage").scrollTop = document.getElementById("FeedBackMessage").scrollHeight;
    }
}

function saveMessage() {
    let message = document.getElementById("FeedBackCreateMessage");
    if (message.value) {
        let ses = new XMLHttpRequest();
        ses.open("GET", "http://localhost/data/save?user=guest&message=" + message.value, true);
        ses.onload = function (data) {
            message.value = "";
            getMessage();
        };
        ses.send(null);
    } else alert();
}


function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
