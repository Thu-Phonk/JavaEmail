<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JavaMail Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            margin: 0;
            padding: 40px 20px;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 32px;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0,0,0,.08);
        }
        h1 { margin-top: 0; color: #174ea6; }
        .success { color: #18794e; }
        .error { color: #b3261e; }
        a { color: #174ea6; }
    </style>
</head>
<body>
<div class="container">
    <h1>JavaMail Result</h1>

    <% if (request.getAttribute("successMessage") != null) { %>
        <p class="success"><%= request.getAttribute("successMessage") %></p>
        <p>Recipient: <%= request.getAttribute("email") %></p>
        <p>Thank you, <%= request.getAttribute("firstName") %>!</p>
    <% } else if (request.getAttribute("errorMessage") != null) { %>
        <p class="error"><%= request.getAttribute("errorMessage") %></p>
    <% } else { %>
        <p>Nothing was submitted yet.</p>
    <% } %>

    <p><a href="<%= request.getContextPath() %>/index.jsp">Back to the form</a></p>
</div>
</body>
</html>
