public interface RevenueReportingService {
    void generateSalesReport(String startDate, String endDate);
    double getEventRevenue(String eventName, String date); void sendAudienceDemographicsReport(String eventName, String demographicsData);
}
