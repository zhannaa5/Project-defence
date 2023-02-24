import controller.AuthenticatorController;
import controller.MarketController;
import controller.UserController;
import java.util.InputMismatchException;
import java.util.Scanner;

import static controller.AuthenticatorController.getAuthenticatedUser;

public class MyApplication {
    private final UserController controller;
    private final AuthenticatorController authController;
    private final MarketController marketController;
    Scanner sc = new Scanner(System.in);
    Scanner input = new Scanner(System.in);
    public MyApplication(UserController controller, AuthenticatorController authController, MarketController marketController) {
        this.controller = controller;
        this.authController = authController;
        this.marketController = marketController;
    }

    public void start() {
        while (true) {
            System.out.println();
            System.out.println("Welcome to My Application");
            System.out.println("Select option:");
            System.out.println("1. Sign In");
            System.out.println("2. Sign in as a guest");
            System.out.println("3. Create User");
            System.out.println("4. Reset Password");
            System.out.println("0. Exit");
            System.out.println();
            try
            {
                int num = sc.nextInt();
                if (num == 1)
                    signIn();
                if (num == 2)
                    mainPage();
                if (num == 3)
                    createAcc();
                if (num == 4)
                    resetPassword();
                if(num == 0)
                    break;
            }
            catch (InputMismatchException exception) {
                System.out.println("Input must be integer");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void signIn() {
        System.out.println("Please enter email");
        String email = sc.next();
        System.out.println("Please enter password");
        String password = sc.next();
        if(authController.signIn(email, password)) {
            System.out.println("You are in System");
            mainPage();
        }
        else
            System.out.println("Please check your email or password");
    }

    public void createAcc() {
        System.out.println("Please enter name");
        String name = sc.next();

        System.out.println("Please enter email");
        String email = sc.next();
        if (authController.isExist(email))
        {
            System.out.println("Account with this mail is already exist!");
            System.out.println("Do you want to sign in?");
            System.out.println("Yes/No");
            String message = sc.next();

            if (message.equalsIgnoreCase("no")) {
                while (true) {
                    if (authController.isExist(email)) {
                        System.out.println("Enter another email");
                        email = sc.next();
                    } else
                        break;
                }
            } else {
                signIn();
                System.exit(0);
            }
        }
        System.out.println("Please enter password");
        String password = sc.next();

        String response = controller.createUser(name, email, password);
        System.out.println(response);
    }

    public void resetPassword() {
        System.out.println("Lets reset your password");
        System.out.println("Enter your email");
        String email = sc.next();
        if(authController.isExist(email)) {
            String newPassword = sc.next();
            String response = authController.resetPassword(email, newPassword);
            System.out.println(response);
        }
    }

    public void mainPage() {
        try
        {
            while (true) {
                System.out.println();
                System.out.println("Welcome to the market " + getAuthenticatedUser().getName());
                System.out.println("What do want to do");
                System.out.println("1. Create product");
                System.out.println("2. Get all the products");
                System.out.println("3. Remove product");
                System.out.println("4. Get sorted products by price");
                System.out.println("5. Get products by type");
                System.out.println("0. Exit from the market");
                System.out.println();

                int n = input.nextInt();

                if (n == 1)
                    createProduct();
                if (n == 2)
                    getAllProducts();
                if (n == 3)
                    removeProd();
                if (n == 4)
                    getSortedProducts();
                if (n == 5)
                    getProductsByType();
                if (n == 0)
                    break;

            }
        }
        catch (InputMismatchException e) {
            System.out.println("Input must be integer");
        }
    }

    public void createProduct() {

        System.out.println("Enter what do you want to add");
        String name = sc.next();
        System.out.println("Enter the price you want");
        int price = input.nextInt();
        System.out.println("Enter some description");
        String description = input.next();
        System.out.println("Enter type of product");
        String type = input.next();
        String response = marketController.createUser(name, price, description, type);

        System.out.println(response);
    }

    public void getAllProducts() {
        String response = marketController.getAllUsers();
        System.out.println(response);
    }

    public void getSortedProducts() {
        String response = marketController.sortProductByPrice();
        System.out.println(response);
    }

    public void getProductsByType(){
        System.out.println("Enter type of product");
        String type = sc.next();
        String response = marketController.getProductsByType(type);
        System.out.println(response);
    }

    public void removeProd() {
        System.out.println("Enter the id of product which you need");
        int id = input.nextInt();
        String response = marketController.removeProduct(id);
        System.out.println(response);
    }

}
