<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, Laboral.Empleados" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Listado de Empleados</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Listado General de Empleados</h2>
        <table>
            <thead>
                <tr>
                    <th>DNI</th>
                    <th>Nombre</th>
                    <th>Sexo</th>
                    <th>Categoría</th>
                    <th>Años Trabajados</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Empleados> lista = (List<Empleados>) request.getAttribute("listaEmpleados");
                    if (lista != null && !lista.isEmpty()) {
                        for (Empleados emp : lista) {
                %>
                <tr>
                    <td><%= emp.dni %></td>
                    <td><%= emp.nombre %></td>
                    <td><%= emp.sexo %></td>
                    <td><%= emp.getCategoria() %></td>
                    <td><%= emp.anyos %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="5">No se encontraron empleados registrados.</td></tr>
                <% } %>
            </tbody>
        </table>
        <br>
        <button onclick="history.back()" class="btn">Volver</button>
    </div>
</body>
</html>