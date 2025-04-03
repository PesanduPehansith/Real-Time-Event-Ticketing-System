// Vendor class that implements the Runnable interface
public class Vendor implements Runnable{
    private String vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;
    private TicketPool ticketPool;
    private volatile boolean running = true;

    // Constructor to initialize vendor information
    public Vendor (String vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool){
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    // Getters and setters
    public String getVendorId(){
        return vendorId;
    }
    public void setVendorId(String vendorId){
        this.vendorId = vendorId;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }
    public void setTicketsPerRelease(int ticketsPerRelease){
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public int getReleaseInterval(){
        return releaseInterval;
    }
    public void setReleaseInterval(int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }

    // Run method that implements the Runnable interface
    public void stop() {
        running = false;
    }
    @Override
    public void run() {
        try {
            while (running) {
                boolean added = ticketPool.addTickets(ticketsPerRelease, vendorId);
                if (!added) {
                    stop(); // Stop adding tickets permanently if the pool is full
                }
                Thread.sleep(releaseInterval);
            }
        } catch (InterruptedException e) {
            System.out.println(vendorId + " interrupted: " + e.getMessage());
            Logger.log(vendorId + " interrupted: " + e.getMessage());
        }
    }
}