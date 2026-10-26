package Laboral;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cargarEmpleado")
public class CargarEmpleadoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dni = req.getParameter("dni");

        try {
            GestionDB gestion = new GestionDB();
            Empleados emp = gestion.obtenerEmpleadoPorDni(dni);

            if (emp != null) {
                Integer salario = gestion.obtenerSueldoPorDni(dni);
                if (salario == null) {
                    Nomina nom = new Nomina();
                    salario = nom.sueldo(emp);
                }

                req.setAttribute("empleado", emp);
                req.setAttribute("salario", salario);
                req.getRequestDispatcher("/modificarEmpleado.jsp").forward(req, resp);
            } else {
                req.setAttribute("mensajeError", "No se encontró el empleado a modificar.");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("mensajeError", "Error al cargar datos del empleado: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}