package main;

import java.util.Scanner;

import operations.LibraryOperations;

public class LibrarySystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n📖 Library Management System 📖");
            System.out.println("1️⃣ Add Book");
            System.out.println("2️⃣ View Books");
            System.out.println("3️⃣ Search a Book");
            System.out.println("4️⃣ Issue a Book");
            System.out.println("5️⃣ Return a Book");
            System.out.println("6️⃣ Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    LibraryOperations.addBook(id, title, author);
                    break;

                case 2:
                    LibraryOperations.viewBooks();
                    break;

                case 3:
                    System.out.print("Enter Book ID to Search: ");
                    int searchId = sc.nextInt();
                    LibraryOperations.searchBook(searchId);
                    break;

                case 4:
                    System.out.print("Enter Book ID to Issue: ");
                    int issueId = sc.nextInt();
                    LibraryOperations.issueBook(issueId);
                    break;

                case 5:
                    System.out.print("Enter Book ID to Return: ");
                    int returnId = sc.nextInt();
                    LibraryOperations.returnBook(returnId);
                    break;

                case 6:
                    System.out.println("📌 Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice, try again.");
            }
        }
    }
}
