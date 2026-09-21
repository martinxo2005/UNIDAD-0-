package Laboral;

public class CalculaNominas {
    
    
    Nomina n = new Nomina();
    
        private void escribe(Empleados e1,Empleados e2){
        System.out.println("EL empleado 1 tiene como atributos");
        e1.imprime();
        
        System.out.println("EL empleado 1 gana de sueldo :"+ n.sueldo(e2));
        
        System.out.println("EL empleado 2 tiene como atributos");
        e2.imprime();
        
        System.out.println("EL empleado 1 gana de sueldo :" +n.sueldo(e2));
        

    }
        
    public static void main(String[] args) throws DatosNoCorrectosException {
   
    Empleados e1 = new Empleados(4, 7, "32000032G", "James Cosling",'M');
    Empleados e2 = new Empleados("Ada Lovelace","32000031R",'F');
    
    CalculaNominas cn = new CalculaNominas();
    Nomina n = new Nomina();
        
        //primera llamada a escribe 
        cn.escribe(e1, e2);
        
        //cambiar categoria y añadir años
        e2.setCategoria(9);
        e2.incrAnyo();
        
        //imprimir de nuevo los empleados y el sueldo
        cn.escribe(e1, e2);
        
        
    }    
}
