<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Laboral.Empleados" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Modificar Empleado</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Modificar Datos del Empleado</h2>
        <%
            Empleados emp = (Empleados) request.getAttribute("empleado");
            Number salario = (Number) request.getAttribute("salario");
        %>
        <% if (emp != null) { %>
        <form action="modificarEmpleado" method="post">
            <div class="form-group">
                <label>DNI (No editable):</label>
                <input type="text" name="dni" value="<%= emp.dni %>" readonly style="background-color: #e9ecef;">
            </div>
            
            <div class="form-group">
                <label>Nombre:</label>
                <input type="text" name="nombre" value="<%= emp.nombre %>" required>
            </div>

            <div class="form-group">
                <label>Sexo:</label>
                <select name="sexo">
                    <option value="M" <%= emp.sexo == 'M' ? "selected" : "" %>>Masculino (M)</option>
                    <option value="F" <%= emp.sexo == 'F' ? "selected" : "" %>>Femenino (F)</option>
                </select>
            </div>

            <div class="form-group">
                <label>Categoría (1-10):</label>
                <input type="number" name="categoria" value="<%= emp.getCategoria() %>" min="1" max="10" required>
            </div>

            <div class="form-group">
                <label>Años Trabajados:</label>
                <input type="number" name="anyos" value="<%= emp.anyos %>" min="0" required>
            </div>

            <div class="form-group">
                <label>Sueldo Actual (Calculado automáticamente):</label>
                <input type="text" value="<%= String.format("%.2f €", salario != null ? salario.doubleValue() : 0.0) %>" disabled style="background-color: #e9ecef; font-weight: bold;">
                <small style="color: #6c757d; display: block;">El sueldo no es modificable manualmente, se actualizará según la categoría y los años.</small>
            </div>

            <br>
            <button type="submit" class="btn">Guardar Cambios</button>
        </form>
        <% } %>
        <br>
        <button onclick="history.back()" class="btn btn-secondary">Volver</button>
    </div>
</body>
</html>