<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>FINA-STAT Login Page</title>

    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css">

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.cookies.2.2.0.min.js"></script>

</head>
<body>

<form action='j_security_check' id="loginForm" method="post" class="loginbox" autocomplete="on">
    <fieldset>
        <div class="logo">Stochastic Automats</div>

        <p style="display: none;" class="error">Authentication Failure!</p>

        <label for="login">Login</label>
        <input name="j_username" type="text" id="j_username"/>
        <label for="password">Password</label>
        <input name="j_password" type="password" id="j_password"/>
        <input type="submit" id="submit" value="LOGIN"/>

    </fieldset>
</form>
<script type="text/javascript">
    var cookieEnabled = (navigator.cookieEnabled);
    document.getElementById('submit').disabled = false;
    if (!cookieEnabled) {
        document.getElementById('submit').disabled = true;
        document.getElementById('error').innerHTML = "<h3 style='color:red;'> Cookies are not enabled </h3>";
    }
    if ((document.location + "").indexOf('j_security_check') != -1) {
        $(function () {
            $('.error').css('display', 'block');
            $('#loginForm').css({
                'height': '200px'
            });
        });
    }
</script>
<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
<noscript>
    <div
            style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled in order for this
        application to display correctly.
    </div>
</noscript>
</body>
</html>