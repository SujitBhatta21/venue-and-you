// Interface for Ticket Sales & Promotions Box Office  Marketing)
public interface TicketSalesService {
    void applyDiscount(String eventName, String discountCode, double percentage);

    boolean verifyDiscount(String discountCode, String customerCategory);

    void updateGroupBooking(String eventName, String date, int seatCount, String groupName);

    void notifyPromotionalEvent(String eventName, String promotionDetails);
}