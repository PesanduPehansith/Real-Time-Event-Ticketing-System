// Customer class that implements the Runnable interface
public class Customer implements Runnable {
    private String customerId;
    private int ticketsToRetrieve;
    private int retrievalInterval;
    private TicketPool ticketPool;
    private volatile boolean running = true;

    // Constructor to initialize customer information
    public Customer (String customerId, int ticketsToRetrieve, int retrievalInterval, TicketPool ticketPool){
        this.customerId = customerId;
        this.ticketsToRetrieve = ticketsToRetrieve;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    // Getters and setters
    public String getCustomerId(){
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }
    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

    public void stop() {
        running = false;
    }

    // Run method that implements the Runnable interface
    @Override
    public void run() {
        try {
            while (running) {
                if (ticketPool.isEmpty()) {
                    stop(); // Stop if the pool is empty
                } else {
                    ticketPool.removeTickets(ticketsToRetrieve, customerId);
                }
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            System.out.println(customerId + " interrupted: " + e.getMessage());
            Logger.log(customerId + " interrupted: " + e.getMessage());
        }
    }
}
