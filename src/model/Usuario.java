package model;

public class Usuario {
    
    String email;
    String password;

    public Usuario(String email, String password) {
        this.email = email; 
        this.password = password;
    }

    public String getEmail() { return email;}
    public String getPassword() { return password;}

    public String Guardado() { return email + password;}
}