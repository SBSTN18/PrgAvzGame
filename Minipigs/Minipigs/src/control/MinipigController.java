package control;

import modelo.Minipig;

public class MinipigController {

    private Minipig minipig;
    public MinipigController(){
    }

    public void minipigDB(int codigo, String nombre, 
        String genero, String idChip, String raza, String peso, 
        String color, String altura, String caracteristica1, 
        String caracteristica2, String foto){


        this.minipig = new Minipig(codigo, nombre, genero, idChip, 
            raza, peso, color, altura, caracteristica1, 
            caracteristica2, foto);    
    }

    public void crearMinipig(String nombre, 
        String genero, String idChip, String raza, String peso, 
        String color, String altura, String caracteristica1, 
        String caracteristica2, String foto){
        
    this.minipig = new Minipig(nombre, genero, idChip, 
        raza, peso, color, altura, caracteristica1, 
        caracteristica2, foto);
    }

    public void cambiarDatos(String nombre, 
        String genero, String idChip, String raza, String peso, 
        String color, String altura, String caracteristica1, 
        String caracteristica2, String foto){
        
        minipig.setNombre(nombre);

        }
}
