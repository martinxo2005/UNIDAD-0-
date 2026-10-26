<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Búsqueda de Empleados</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Filtrar / Buscar Empleados</h2>
        <form action="buscarEmpleados" method="post">
            <div><label>DNI:</label> <input type="text" name="dni"></div>
            <div><label>Nombre:</label> <input type="text" name="nombre"></div>
            <div><label>Sexo (M/F):</label> <input type="text" name="sexo" maxlength="1"></div>
            <div><label>Categoría (1-10):</label> <input type="number" name="categoria" min="1" max="10"></div>
            <div><label>Años trabajados:</label> <input type="number" name="anyos" min="0"></div>
            <br>
            <button type="submit" class="btn">Buscar</button>
        </form>
        <br>
        <button onclick="history.back()" class="btn btn-secondary">Volver</button>
    </div>
</body>
</html>