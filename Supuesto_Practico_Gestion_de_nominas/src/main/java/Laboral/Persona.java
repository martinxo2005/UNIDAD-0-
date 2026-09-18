package Laboral;

public class Persona {

//atributos clase 
    public String nombre;
    public String dni;
    public char sexo;

//constructor
     public Persona(String nombre, String dni, char sexo) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
    }

//set
public void setDni (String dni){
    this.dni= dni;
}    

public void Imprime(){
    System.out.println("El nombre del empleado es "+ nombre+ "y su dni "+dni);
}


}    

