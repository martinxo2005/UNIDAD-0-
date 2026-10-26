<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Laboral.Empleados" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Salario del Empleado</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Salario Calculado</h2>
        <%
            Empleados emp = (Empleados) request.getAttribute("empleado");
            Number salario = (Number) request.getAttribute("salario");
        %>
        <% if (emp != null && salario != null) { %>
        <table>
            <tr><th>DNI</th><td><%= emp.dni %></td></tr>
            <tr><th>Nombre</th><td><%= emp.nombre %></td></tr>
            <tr><th>Categoría</th><td><%= emp.getCategoria() %></td></tr>
            <tr><th>Años Trabajados</th><td><%= emp.anyos %></td></tr>
            <tr><th>Salario Total</th><td><strong><%= String.format("%.2f €", salario.doubleValue()) %></strong></td></tr>
        </table>
        <% } %>
        <br>
        <button onclick="history.back()" class="btn">Volver</button>
    </div>
</body>
</html>