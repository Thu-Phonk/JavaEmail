<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Join Our Email List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            margin: 0;
            padding: 40px 20px;
        }
        .container {
            max-width: 520px;
            margin: 0 auto;
            background: white;
            padding: 32px;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0,0,0,.08);
        }
        h1 { margin-top: 0; color: #174ea6; }
        label { display: block; margin: 16px 0 6px; font-weight: 600; }
        input {
            width: 100%;
            box-sizing: border-box;
            padding: 11px;
            border: 1px solid #bbb;
            border-radius: 6px;
            font-size: 16px;
        }
        button {
            margin-top: 24px;
            width: 100%;
            padding: 12px;
            border: 0;
            border-radius: 6px;
            background: #174ea6;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }
        .note { color: #666; font-size: 14px; line-height: 1.5; }
    </style>
</head>
<body>
<div class="container">
    <h1>Join Our Email List</h1>
    <p class="note">
        This is the Chapter 14 JavaMail demonstration: a servlet receives the
        form data and sends a welcome email through Gmail SMTP.
    </p>

    <form action="${pageContext.request.contextPath}/emailList" method="post">
        <input type="hidden" name="action" value="add">

        <label for="firstName">First name</label>
        <input id="firstName" name="firstName" type="text" required>

        <label for="lastName">Last name</label>
        <input id="lastName" name="lastName" type="text" required>

        <label for="email">Email</label>
        <input id="email" name="email" type="email" required>

        <button type="submit">Join Email List</button>
    </form>
</div>
</body>
</html>
