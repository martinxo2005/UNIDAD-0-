package Laboral;


public class Nomina {
    
    private static final int SUELDO_BASE[] = {50000, 70000, 90000, 110000, 130000, 
        150000, 170000, 190000, 210000, 230000} ;
    
    
    public int sueldo(Empleados e){
        int sueldoObtenido;
        return sueldoObtenido = SUELDO_BASE[e.getCategoria()]+ 5000 * e.anyos;
    }
}
