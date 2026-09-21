package Laboral;

public class Nomina {
    
    private static final int SUELDO_BASE[] = {50000, 70000, 90000, 110000, 130000, 
        150000, 170000, 190000, 210000, 230000};
    
    public int sueldo(Empleados e) {
        // e.getCategoria() da entre 1 y 10, restamos 1 para los índices del array (0 a 9)
        return SUELDO_BASE[e.getCategoria() - 1] + 5000 * e.anyos;
    }
}