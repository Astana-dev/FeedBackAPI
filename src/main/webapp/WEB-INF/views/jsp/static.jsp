<%--
  Created by IntelliJ IDEA.
  User: Nurzhan
  Date: 17.10.2017
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="/resources/core/css/bootstrap.min.css">
<%--***********************************************************************************--%>
<%--<%@ include file="../vue/component/pageContainer.vue" %>--%>
<%--***********************************************************************************--%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="/resources/core/js/bootstrap.min.js"></script>
<script src="/resources/core/js/calendar.js"></script>
<script src="/resources/core/js/vue.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue-resource@1.3.4"></script>
<script>
    function getCookie(name) {
        let matches = document.cookie.match(new RegExp(
            "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
        ));
        return matches ? decodeURIComponent(matches[1]) : undefined;
    }
</script>

<%--<script src="/resources/core/js/viewform.js"></script>--%>
<%--<script src="http://localhost/feedback/form-1"></script>--%>

<style type="text/css">
    [v-cloak] {
        display: none;
    }
</style>

<style type="text/css">
    .risk-table-light:hover{
        background: #dfffb1;
    }
    .risk-poster{
        position:relative;
    }
    .risk-descr{
        display:none;
        top: 60px;
        left: 0px;
    }
    .risk-descr-in{
        border: 3px solid #fff; /* Белая рамка */
        border-radius: 10px; /* Радиус скругления */
        background: #EFEFEF;
        width: 300px;
    }
    .risk-descr-in-org{
        border: 3px solid #fff; /* Белая рамка */
        border-radius: 10px; /* Радиус скругления */
        background: #EFEFEF;
        top: 20px;
    }
    .risk-poster:hover .risk-descr{
        display:block;
        position:absolute;
        z-index:10;
    }
</style>
