package com.cinemacart;
import java.util.Scanner;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;

public class Main {

    private static void initFirebase() throws Exception {

        FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        FirebaseApp.initializeApp(options);
    }
    public static void main(String[] args) throws Exception {
        
        initFirebase();

        UserRepository userRepository = new UserRepository();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to CinemaCart! Login or Register to continue");
        System.out.println("Press 1 to Login, press 2 to register");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if(choice ==1) {
            System.out.println("Username: ");
            String username = scanner.nextLine();
            System.out.println("Password: ");
            String password = scanner.nextLine();
            UserAccount user = userRepository.findByUsername(username);
            if(user != null && user.passwordMatch(password)) {
                System.out.println("Welcome, " + user.getUsername());
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        }

        else if(choice == 2) {
            System.out.println("Enter a username: ");
            String username = scanner.nextLine();
            System.out.println("Enter an email: ");
            String email = scanner.nextLine();
            System.out.println("Enter a password: ");
            String password = scanner.nextLine();

            String userId = String.valueOf(System.currentTimeMillis()); //Generate unique user ID based on current time in milliseconds
            UserAccount newUser = new UserAccount(userId, username, email, password); //Creates new user object with the provided username, email, and password
            userRepository.save(newUser); //Saves user to the firestore database
            System.out.println("Registration successful! You can now login with your credentials.");
        } else {
            System.out.println("Invalid choice. Please restart the application and select either 1 or 2.");

        }
    }
}