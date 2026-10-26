<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container error-box">
        <h2>Se ha producido un error</h2>
        <p><%= request.getAttribute("mensajeError") != null ? request.getAttribute("mensajeError") : "Ocurrió un error inesperado." %></p>
        <br>
        <button onclick="history.back()" class="btn">Volver</button>
    </div>
</body>
</html>