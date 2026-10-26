package Laboral;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/modificarEmpleado")
public class ModificarEmpleadoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dni = req.getParameter("dni");
        String nombre = req.getParameter("nombre");
        char sexo = req.getParameter("sexo").charAt(0);
        int categoria = Integer.parseInt(req.getParameter("categoria"));
        int anyos = Integer.parseInt(req.getParameter("anyos"));

        try {
            // Construimos el objeto actualizado
            Empleados empActualizado = new Empleados(categoria, anyos, nombre, dni, sexo);
            
            GestionDB gestion = new GestionDB();
            // Llama a tu método que actualiza en BD y recalcula automáticamente el sueldo en Nominas
            gestion.guardarOActualizarEmpleado(empActualizado);

            // Redirige al listado de empleados para ver los cambios guardados
            resp.sendRedirect("listarEmpleados");
        } catch (Exception e) {
            req.setAttribute("mensajeError", "Error al actualizar los datos del empleado: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}