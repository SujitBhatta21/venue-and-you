import java.util.List;

public class TestApp {
  public static void main(String[] args) {
    System.out.println("🔄 Starting TestApp...");

    // ✅ Create an instance of BoxOfficeAPIImp
    BoxOfficeAPIImp test = new BoxOfficeAPIImp();
    System.out.println("✅ BoxOfficeAPIImp instance created.\n");

    // Call the test methods using the instance
    TestApp app = new TestApp();
    app.testGetTicketSales(test);
    app.testProcessTicketRefund(test);
    app.testGetSeatAvailability(test);
    app.testBookTickets(test);

    System.out.println("\n🚀 TestApp execution completed.");
  }

  /** Test Ticket Sales Retrieval */
  private void testGetTicketSales(BoxOfficeAPIImp test) {
    System.out.println("\n🎟️ Testing getTicketSales...");
    TicketSales sales = test.getTicketSales("E101");
    if (sales != null) {
        System.out.println("✅ Ticket Sales: " + sales);
    } else {
        System.out.println("❌ No sales data found for event E101.");
    }
  }

  /** Test Processing a Ticket Refund */
  private void testProcessTicketRefund(BoxOfficeAPIImp test) {
    System.out.println("\n💰 Testing processTicketRefund...");
    RefundConfirmation refund = test.processTicketRefund("TXN123", 50.00);
    if (refund.isSuccess()) {
        System.out.println("✅ Refund processed: " + refund);
    } else {
        System.out.println("❌ Refund failed: " + refund);
    }
  }

  /** Test Seat Availability Retrieval */
  private void testGetSeatAvailability(BoxOfficeAPIImp test) {
    System.out.println("\n💺 Testing getSeatAvailability...");
    List<SeatAvailability> seats = test.getSeatAvailability("E101");
    if (!seats.isEmpty()) {
        System.out.println("✅ Available seats:");
        for (SeatAvailability seat : seats) {
            System.out.println(seat);
        }
    } else {
        System.out.println("❌ No seat availability data found.");
    }
  }

  /** Test Booking Tickets */
  private void testBookTickets(BoxOfficeAPIImp test) {
    System.out.println("\n📅 Testing bookTickets...");
    BookingRequest request = new BookingRequest("E101", List.of("A1", "A2"), "John Doe", "Card Payment");
    BookingConfirmation confirmation = test.bookTickets(request);
    if (confirmation.isSuccess()) {
        System.out.println("✅ Booking successful: " + confirmation);
    } else {
        System.out.println("❌ Booking failed: " + confirmation);
    }
  }
}

