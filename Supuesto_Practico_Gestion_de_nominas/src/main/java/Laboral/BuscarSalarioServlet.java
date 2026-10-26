package Laboral;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/buscarSalario")
public class BuscarSalarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dni = req.getParameter("dni");

        try {
            GestionDB gestion = new GestionDB();
            Empleados emp = gestion.obtenerEmpleadoPorDni(dni);

            if (emp != null) {
                Integer salario = gestion.obtenerSueldoPorDni(dni);
                
                // Si la nómina aún no existía en la BD, la calculamos directamente
                if (salario == null) {
                    Nomina nom = new Nomina();
                    salario = nom.sueldo(emp);
                }

                req.setAttribute("empleado", emp);
                req.setAttribute("salario", salario);
                req.getRequestDispatcher("/mostrarSalario.jsp").forward(req, resp);
            } else {
                req.setAttribute("mensajeError", "No se encontró ningún empleado con el DNI: " + dni);
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("mensajeError", "Error procesando la solicitud: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}