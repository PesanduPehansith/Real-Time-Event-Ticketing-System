import java.util.*;

// TicketPool class to manage a pool of tickets
public class TicketPool {
    // Private fields to store tickets and maximum capacity
    private List<Integer> tickets;  // List to store tickets
    private int maxTicketCapacity;

    // Constructor to initialize the ticket pool
    public TicketPool(int initialTickets, int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        tickets = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < initialTickets; i++) {
            tickets.add(1); // Adding 1 to represent each available ticket
        }
        System.out.println("Ticket pool initialized with " + initialTickets + " tickets (Max Capacity: " + maxTicketCapacity + ")");
        Logger.log("Ticket pool initialized with " + initialTickets + " tickets (Max Capacity: " + maxTicketCapacity + ")");
        System.out.println();
    }

    // Synchronized method to add tickets to the pool
    public synchronized boolean addTickets(int ticketCount, String vendorId) {
        if (tickets.size() + ticketCount <= maxTicketCapacity) {
            for (int i = 0; i < ticketCount; i++) {
                tickets.add(1);
            }
            System.out.println(vendorId + " added " + ticketCount + " tickets. Current pool size: " + tickets.size());
            Logger.log(vendorId + " added " + ticketCount + " tickets. Current pool size: " + tickets.size());
            return true;
        } else {
            System.out.println(vendorId + " Cannot add tickets. Pool at max capacity: " + maxTicketCapacity);
            Logger.log(vendorId + " Cannot add tickets. Pool at max capacity: " + maxTicketCapacity);
            return false; // Indicates the pool is full
        }
    }

    // Synchronized method to remove tickets from the pool
    public synchronized boolean removeTickets(int ticketCount, String customerId) {
        if (tickets.size() >= ticketCount) {
            for (int i = 0; i < ticketCount; i++) {
                tickets.remove(0);
            }
            System.out.println(customerId + " purchased " + ticketCount + " tickets. Current pool size: " + tickets.size());
            Logger.log(customerId + " purchased " + ticketCount + " tickets. Current pool size: " + tickets.size());
            return true;
        } else {
            System.out.println("Not enough tickets available for " + customerId + " Current pool size: " + tickets.size());
            Logger.log("Not enough tickets available for " + customerId + " Current pool size: " + tickets.size());
            return false;
        }
    }

    // Synchronized method to check if the pool is empty
    public synchronized boolean isEmpty() {
        return tickets.isEmpty();
    }

}
