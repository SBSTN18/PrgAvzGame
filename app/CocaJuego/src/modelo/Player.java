package modelo;

import java.io.Serializable;

public class Player implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
    private int code;

    public Player(String name, int code) {
        this.name = name;
        this.code = code;
        ;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

}
