package Laboral;

public class Empleados extends Persona{
    /** Atributos según los requisitos
     *
     */
     
    private int categoria; // Privado, entre 1 y 10
    public int anyos;      // Público, debe ser positivo

    /** 
     *Constructor por defecto
     */

   public Empleados(int categoria, int anyos, String nombre, String dni, char sexo)
        throws DatosNoCorrectosException {

    super(nombre, dni, sexo);

    if (categoria < 1 || categoria > 10 || anyos < 0) {
        throw new DatosNoCorrectosException();
    }

    this.categoria = categoria;
    this.anyos = anyos;
}

   /** 
     *Constructor pq sin otros atributos
     */
    public Empleados(String nombre, String dni, char sexo) throws DatosNoCorrectosException{
        super(nombre, dni, sexo);
        categoria= 1;
        anyos = 0;
    }
    
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getCategoria() {
        return categoria;
    }
    
    
    public void incrAnyo(){
        anyos++;
    }
    
    public void imprime(){
        System.out.println("El empleado "+ nombre+ "cuyo dni es :"+ dni+ "cuyo sexo es "+sexo
                + ", cuya categoria es "+ categoria+ " y que lleva en la empresa "+anyos);
          
    }
    
    
}
