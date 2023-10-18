import java.util.Scanner;

class POS {
    public static void main(String[] args) {
        Scanner uI = new Scanner(System.in);
        String ID = "";
        Boolean correctInput = false;
        int val = 0;
        while (!correctInput) {
            System.out.print("Enter User ID:");
            ID = uI.next();
            try {
                val = Integer.parseInt(ID);
                if (ID.length() > 4) {
                    System.out.println("Invalid input, please enter 4 numbers");
                } else if (ID.length() < 4) {
                    System.out.println("Invalid input, please enter 4 numbers");
                } else {
                    correctInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter 4 numbers");
            }
        }
        System.out.println("String ID: " + ID);
        System.out.println("Int ID: " + val);
    }
}