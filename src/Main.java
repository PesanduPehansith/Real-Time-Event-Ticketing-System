import java.util.*;

public class Main {
    public static void main(String[] args) {

        // Display instructions for user input
        System.out.println("INSTRUCTIONS: ");
        System.out.println("* Ticket Count Must Be Positive");
        System.out.println("* Ticket Release Rate Must Be Between 1 and 1000");
        System.out.println("* Customer Retrieval Rate Must Be Between 1 and 1000");
        System.out.println("* Maximum Ticket Capacity Must Be Between 1 and 10000");
        System.out.println("* Total Number of Tickets should not exceed Maximum Ticket Capacity");
        System.out.println();

        // Get validated user inputs
        try {
            Scanner input = new Scanner(System.in);
            int totalTickets = validateInput(input, "Enter the Total Number of Tickets: ");
            int ticketReleaseRate = validateInput(input, "Enter Ticket Release Rate: ");
            int customerRetrievalRate = validateInput(input, "Enter Customer Retrieval Rate: ");
            int maxTicketCapacity = validateInput(input, "Enter Maximum Ticket Capacity: ");

            // Create Configuration object with user-provided values
            Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);

            // Save configuration to JSON
            config.saveConfigToJson("Config.json");
            System.out.println("\nConfiguration saved to Config.json");
            Logger.log("Configuration saved to config.json.");
            System.out.println();

            // Load configuration from JSON
            Configuration loadedConfigJson = Configuration.loadConfigFromJson("Config.json");
            if (loadedConfigJson != null) {
                System.out.println("Configuration loaded from JSON:");
                Logger.log("Configuration loaded from JSON:");
                printConfiguration(loadedConfigJson);
            }
            // Save configuration to text file
            config.saveConfigToTextFile("Config.txt");
            System.out.println("Configuration saved to Config.txt");
            Logger.log("Configuration saved to config.txt.");
            System.out.println();

            // Load configuration from text file
            Configuration loadedConfigText = Configuration.loadConfigFromTextFile("Config.txt");
            if (loadedConfigText != null) {
                System.out.println("Configuration loaded from text file:");
                Logger.log("Configuration loaded from text file:");
                printConfiguration(loadedConfigText);
            }

            // Create a TicketPool object with initial tickets and capacity
            TicketPool ticketPool = new TicketPool(config.getTotalTickets(), config.getMaxTicketCapacity());

            // Create Vendor objects with configurations
            Vendor vendor1 = new Vendor("Vendor1", config.getTicketReleaseRate(), 1000, ticketPool);
            Vendor vendor2 = new Vendor("Vendor2", config.getTicketReleaseRate(), 1000, ticketPool);
            Vendor vendor3 = new Vendor("Vendor3", config.getTicketReleaseRate(), 1000, ticketPool);
            Vendor vendor4 = new Vendor("Vendor4", config.getTicketReleaseRate(), 1000, ticketPool);

            // Create Customer objects with configurations
            Customer customer1 = new Customer("Customer1", config.getCustomerRetrievalRate(), 1000, ticketPool);
            Customer customer2 = new Customer("Customer2", config.getCustomerRetrievalRate(), 1000, ticketPool);

            // Create threads for vendors
            Thread vendorThread1 = new Thread(vendor1);
            Thread vendorThread2 = new Thread(vendor2);
            Thread vendorThread3 = new Thread(vendor3);
            Thread vendorThread4 = new Thread(vendor4);

            // Create threads for customers
            Thread customerThread1 = new Thread(customer1);
            Thread customerThread2 = new Thread(customer2);

            // Prompt user to start or stop operations
            while (true) {
                System.out.println("Do you want to START the operations? (START/STOP): ");
                String command = input.next().toLowerCase();
                if (command.equals("start")) {
                    // Start the vendor and customer threads
                    vendorThread1.start();
                    vendorThread2.start();
                    vendorThread3.start();
                    vendorThread4.start();
                    customerThread1.start();
                    customerThread2.start();
                    System.out.println();
                    break;
                } else if (command.equals("stop")) {
                    System.out.println("System Terminated !");
                    Logger.log("System Terminated !");
                    System.exit(0);
                } else {
                    System.out.println("Invalid Input !");
                    Logger.log("Invalid Input !");
                }
            }

            System.out.println("System initialized successfully.");
            Logger.log("System initialized successfully.");
            System.out.println();

            // Monitor ticket pool and stop threads when empty
            new Thread(() -> {
                while (true) {
                    if (ticketPool.isEmpty()) {
                        System.out.println("Ticket pool is empty. Stopping all threads.");
                        Logger.log("Ticket pool is empty. Stopping all threads.");
                        vendor1.stop();
                        vendor2.stop();
                        vendor3.stop();
                        vendor4.stop();
                        customer1.stop();
                        customer2.stop();

                        try {
                            vendorThread1.join();
                            vendorThread2.join();
                            vendorThread3.join();
                            vendorThread4.join();
                            customerThread1.join();
                            customerThread2.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("System shutdown successfully.");
                        Logger.log("System shutdown successfully.");
                        System.exit(0);
                    }
                }
            }).start();

        } catch(IllegalArgumentException e){
            System.err.println("Invalid:" + e.getMessage());
            Logger.log("Invalid input: " + e.getMessage());
        } catch(Exception e){
            System.out.println("Critical error in system initialization: \" + e.getMessage()");
            Logger.log("Critical error in system initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to validate integer inputs
    private static int validateInput(Scanner input, String prompt) {
        int validInput = -1;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt); // Display prompt on the same line
            try {
                validInput = input.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input! Please enter an integer.\n");
                Logger.log("Invalid input! Please enter an integer.");
                input.nextLine(); // Clear invalid input from scanner buffer
                System.out.println();
            }
        }
        return validInput;
    }

    private static void printConfiguration(Configuration config) {
        System.out.println("Total Number of Tickets : " + config.getTotalTickets());
        System.out.println("Ticket Release Rate : " + config.getTicketReleaseRate());
        System.out.println("Customer Retrieval Rate : " + config.getCustomerRetrievalRate());
        System.out.println("Maximum Ticket Capacity : " + config.getMaxTicketCapacity());
        System.out.println();
    }
}