<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, Laboral.Empleados" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultados de la Búsqueda</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Coincidencias Encontradas</h2>
        <table>
            <thead>
                <tr>
                    <th>DNI</th>
                    <th>Nombre</th>
                    <th>Sexo</th>
                    <th>Categoría</th>
                    <th>Años</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Empleados> resultados = (List<Empleados>) request.getAttribute("resultados");
                    if (resultados != null && !resultados.isEmpty()) {
                        for (Empleados emp : resultados) {
                %>
                <tr>
                    <td><%= emp.dni %></td>
                    <td><%= emp.nombre %></td>
                    <td><%= emp.sexo %></td>
                    <td><%= emp.getCategoria() %></td>
                    <td><%= emp.anyos %></td>
                    <td>
                        <a href="cargarEmpleado?dni=<%= emp.dni %>" class="btn btn-sm">Modificar</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="6">No se encontraron empleados con los criterios introducidos.</td></tr>
                <% } %>
            </tbody>
        </table>
        <br>
        <button onclick="history.back()" class="btn">Volver</button>
    </div>
</body>
</html>