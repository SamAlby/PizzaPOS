class user{
    String name = null;
    String id = null;
    boolean admin = false;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public boolean getAdmin(){
        return admin;
    }
    public void setAdmin(boolean admin){
        this.admin = admin;
    }
    public String toString(){
        return "Name: "+ name + ", ID: " + id + ", Admin: " + admin;
    }
}