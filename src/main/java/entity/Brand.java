package com.mycompany.javaweb.entity;


public class Brand {
    private long id;
    private String name;
    private String logo; // Dựa trên CSDL của bạn

    public Brand() {
    }

    public Brand(long id, String name, String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    }

    // --- Getters and Setters ---
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}