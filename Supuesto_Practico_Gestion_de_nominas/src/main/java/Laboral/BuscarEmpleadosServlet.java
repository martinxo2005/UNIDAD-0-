package Laboral;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/buscarEmpleados")
public class BuscarEmpleadosServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dni = req.getParameter("dni");
        String nombre = req.getParameter("nombre");
        String sexo = req.getParameter("sexo");
        String categoria = req.getParameter("categoria");
        String anyos = req.getParameter("anyos");

        try {
            GestionDB gestion = new GestionDB();
            List<Empleados> resultados = gestion.buscarFiltrado(dni, nombre, sexo, categoria, anyos);
            
            req.setAttribute("resultados", resultados);
            req.getRequestDispatcher("/resultadoBusqueda.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("mensajeError", "Error durante la búsqueda: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}