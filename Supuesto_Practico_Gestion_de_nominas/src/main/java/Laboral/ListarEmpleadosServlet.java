package Laboral;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/listarEmpleados")
public class ListarEmpleadosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GestionDB gestion = new GestionDB();
            List<Empleados> lista = gestion.obtenerTodosEmpleados();
            
            req.setAttribute("listaEmpleados", lista);
            req.getRequestDispatcher("/mostrarEmpleados.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("mensajeError", "Error al consultar la base de datos: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}