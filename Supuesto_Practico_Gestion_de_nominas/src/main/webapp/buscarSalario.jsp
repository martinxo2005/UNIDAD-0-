<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Buscar Salario</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Consultar Salario por DNI</h2>
        <form action="buscarSalario" method="post">
            <label for="dni">DNI del Empleado:</label>
            <input type="text" id="dni" name="dni" required placeholder="Ej: 12345678A">
            <button type="submit" class="btn">Obtener Salario</button>
        </form>
        <br>
        <button onclick="history.back()" class="btn btn-secondary">Volver</button>
    </div>
</body>
</html>