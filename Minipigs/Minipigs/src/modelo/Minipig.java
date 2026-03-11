package modelo;

public class Minipig {
    private int codigo;
    private String nombre;
    private String genero;
    private String idChip;
    private String raza;
    private String peso;
    private String color;
    private String altura;
    private String carac1;
    private String carac2;
    private String urlFoto;

    public Minipig(String nombre, String genero, String idChip,
        String raza, String peso, String color, String altura,
        String caracteristica1, String caracteristica2, String foto){
            this.nombre = nombre;
            this.genero = genero;
            this.idChip = idChip;
            this.raza = raza;
            this.peso = peso;
            this.color = color;
            this.altura = altura;
            this.carac1 = caracteristica1;
            this.carac2 = caracteristica2;
            this.urlFoto = foto;
    }


    public Minipig(int codigo, String nombre, String genero, 
        String idChip, String raza, String peso, String color, 
        String altura, String caracteristica1, 
        String caracteristica2, String foto){
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.idChip = idChip;
        this.raza = raza;
        this.peso = peso;
        this.color = color;
        this.altura = altura;
        this.carac1 = caracteristica1;
        this.carac2 = caracteristica2;
        this.urlFoto = foto;
    }

}
