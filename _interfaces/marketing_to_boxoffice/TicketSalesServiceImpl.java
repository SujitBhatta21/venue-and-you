public class TicketSalesServiceImpl
        implements TicketSalesService {
    @Override
    public void applyDiscount(String eventName, String discountCode, double percentage) {

    }

    @Override
    public boolean verifyDiscount(String discountCode, String customerCategory) {
        return false;
    }

    @Override
    public void updateGroupBooking(String eventName, String date, int seatCount, String groupName) {

    }

    @Override
    public void notifyPromotionalEvent(String eventName, String promotionDetails) {

    }
}
