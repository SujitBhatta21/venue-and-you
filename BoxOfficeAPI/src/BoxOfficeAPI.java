import java.util.List;
public interface BoxOfficeAPI {

    /**
     * Retrieves ticket sales data for a specified event.
     *
     * @param eventId The ID of the event.
     * @return Ticket sales details.
     */
    TicketSales getTicketSales(String eventId);

    /**
     * Processes ticket refunds for a given transaction.
     *
     * @param transactionId The ID of the transaction.
     * @param refundAmount The amount to be refunded.
     * @return Confirmation of the refund process.
     */
    RefundConfirmation processTicketRefund(String transactionId, double refundAmount);

    /**
     * Retrieves real-time seat availability for a given event.
     *
     * @param eventId The ID of the event.
     * @return List of available seats.
     */
    List<SeatAvailability> getSeatAvailability(String eventId);

    /**
     * Books tickets for a given event.
     *
     * @param bookingRequest Details of the booking request.
     * @return Confirmation of ticket booking.
     */
    BookingConfirmation bookTickets(BookingRequest bookingRequest);
}

