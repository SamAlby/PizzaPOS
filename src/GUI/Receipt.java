package GUI;

public class Receipt {
    String Size = null;
    String[] Toppings = null;
    String[] Sodas = null;
    float tax = 0;
    double total = 0.0;


    public String getSize(){
        return Size;
    }
    public void setSize(String Size){
        this.Size = Size;
    }
    public String[] getToppings(){
        return Toppings;
    }
    public void setToppings(String[] Toppings){
        this.Toppings = Toppings;
    }
    public String[] getSodas(){
        return Sodas;
    }
    public void setSodas(String[] Sodas){
        this.Sodas = Sodas;
    }
    public float getTax(){
        return tax;
    }
    public void setTax(float tax){
        this.tax = tax;
    }
    public double getTotal(){
        return total;
    }
    public void setTotal(double total){
        this.total = total;
    }
    public String toString(){
        return "Size: "+ Size + ", Toppings: " + Toppings + ", Sodas: " + Sodas + ", tax rate: " + tax + ", total: " + total;
    }
}

