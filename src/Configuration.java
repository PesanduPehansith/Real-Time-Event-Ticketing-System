import java.io.*;
import com.google.gson.*;

// Configuration class to store and manage system settings
public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Default constructor
    public Configuration(){
    }

    // Constructor to initialize configuration values
    public Configuration (int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity){
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Getters and setters with input validation
    public int getTotalTickets() {
        return totalTickets;
    }
    public void setTotalTickets(int totalTickets) {
        if (totalTickets < 0) {
            throw new IllegalArgumentException("Ticket Count Must Be Positive");
        }
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate(){
        return ticketReleaseRate;
    }
    public void setTicketReleaseRate(int ticketReleaseRate){
        if (ticketReleaseRate < 1 || ticketReleaseRate > 1000){
            throw new IllegalArgumentException("Ticket Release Rate Must Be Between 1 and 1000");
        }
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
    public void setCustomerRetrievalRate(int customerRetrievalRate){
        if (customerRetrievalRate < 1 || customerRetrievalRate > 1000){
            throw new IllegalArgumentException("Customer Retrieval Rate Must Be Between 1 and 1000");
        }
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity(){
        return maxTicketCapacity;
    }
    public void setMaxTicketCapacity(int maxTicketCapacity) {
        if (maxTicketCapacity < 1 || maxTicketCapacity > 10000){
            throw new IllegalArgumentException("Maximum Ticket Capacity Must Be Between 1 and 10000");
        }
        if (totalTickets > maxTicketCapacity){
            throw new IllegalArgumentException("Total Number of Tickets should not exceed Maximum Ticket Capacity");
        }
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Method to save configuration to a JSON file
    public void saveConfigToJson(String filename){
        try{
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(filename);
            gson.toJson(this, writer);
            writer.close();
        }catch (IOException e){
            System.err.println(("Error Occurred: " + e.getMessage()));
        }
    }

    // Method to load configuration from a JSON file
    public static Configuration loadConfigFromJson(String filename){
        try{
            File file = new File(filename);
            if (!file.exists()) {
                throw new FileNotFoundException("JSON file not found: " + filename);
            }
            Gson gson = new Gson();
            Reader reader = new FileReader(filename);
            Configuration config = gson.fromJson(reader, Configuration.class);  // Deserialize from JSON format
            reader.close();
            return config;
        }catch (IOException e){
            System.err.println("Error Occurred" + e.getMessage());
            return null;
        }
    }

    // Method to save configuration to a text file
    public void saveConfigToTextFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("TotalNumberofTickets=" + totalTickets);
            writer.newLine();
            writer.write("TicketReleaseRate=" + ticketReleaseRate);
            writer.newLine();
            writer.write("CustomerRetrievalRate=" + customerRetrievalRate);
            writer.newLine();
            writer.write("MaximumTicketCapacity=" + maxTicketCapacity);
        }
    }

    // Method to load configuration from a text file
    public static Configuration loadConfigFromTextFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException("Text file not found: " + filename);
        }
        Configuration config = new Configuration();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] keyValue = line.split("=");
                if (keyValue.length == 2) {
                    switch (keyValue[0].trim()) {
                        case "TotalNumberofTickets" :
                            config.setTotalTickets(Integer.parseInt(keyValue[1].trim()));
                            break;
                        case "TicketReleaseRate" :
                            config.setTicketReleaseRate(Integer.parseInt(keyValue[1].trim()));
                            break;
                        case "CustomerRetrievalRate" :
                            config.setCustomerRetrievalRate(Integer.parseInt(keyValue[1].trim()));
                            break;
                        case "MaximumTicketCapacity" :
                            config.setMaxTicketCapacity(Integer.parseInt(keyValue[1].trim()));
                            break;
                    }
                }
            }
        }
        return config;
    }
}