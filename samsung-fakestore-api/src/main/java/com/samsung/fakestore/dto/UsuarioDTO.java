package com.samsung.fakestore.dto;

public class UsuarioDTO {
    private int id;
    private String email;
    private String username;
    private String password;
    private Name name;

    public static class Name {
        private String firstname;
        private String lastname;

        public String getFirstname() { return firstname; }
        public void setFirstname(String firstname) { this.firstname = firstname; }
        public String getLastname() { return lastname; }
        public void setLastname(String lastname) { this.lastname = lastname; }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Name getName() { return name; }
    public void setName(Name name) { this.name = name; }
}
