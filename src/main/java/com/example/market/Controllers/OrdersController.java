package com.example.market.Controllers;

import com.example.market.DAO.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;


@Controller
public class OrdersController {
    private final OrderDAO orderDAO;


    @Autowired
    public OrdersController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping("/orders-my")
    public String getUserOrders(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("orders", orderDAO.findByUsername(username));
        return "orders";
    }

    /*
    @GetMapping("/orders-report")
    public void generateOrdersReport(Principal principal,
                                     HttpServletResponse response) throws IOException {
        String username = principal.getName();
        List<Order> orders = orderDAO.findByUsername(username);

        Font russianFont = FontFactory.getFont("static/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=orders_report_" + username + ".pdf");

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            Image logo = Image.getInstance("src/main/resources/static/img/logo.png");
            logo.scaleToFit(100, 100);
            logo.setAbsolutePosition(50, 750);
            document.add(logo);

            Paragraph title = new Paragraph("Заказы пользователя: " + username, russianFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);


            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);


            Font headerFont = russianFont;
            addTableHeader(table, "Номер заказа", headerFont);
            addTableHeader(table, "Карта", headerFont);
            addTableHeader(table, "Товар", headerFont);
            addTableHeader(table, "Статус", headerFont);
            addTableHeader(table, "Дата создания", headerFont);
            addTableHeader(table, "Дата доставки", headerFont);


            Font cellFont = russianFont;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            for (Order order : orders) {
                addTableCell(table, order.getNumber().toString(), cellFont);
                addTableCell(table, order.getCard(), cellFont);
                addTableCell(table, order.getNomenclature(), cellFont);
                addTableCell(table, order.getStatus(), cellFont);
                addTableCell(table, dateFormat.format(order.getCreationDate()), cellFont);
                addTableCell(table,
                        order.getDeliveryDate() != null ? dateFormat.format(order.getDeliveryDate()) : "Не доставлен",
                        cellFont);
            }

            document.add(table);


            Font footerFont = russianFont;
            Paragraph footer = new Paragraph("Отчёт сгенерирован: " + dateFormat.format(new Date()), footerFont);
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

            document.close();
        } catch (DocumentException e) {
            throw new IOException("Error generating PDF", e);
        }
    }

    private void addTableHeader(PdfPTable table, String text, Font font) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(new Color(200, 200, 200));
        header.setBorderWidth(1);
        header.setPhrase(new Phrase(text, font));
        table.addCell(header);
    }

    private void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorderWidth(1);
        cell.setPadding(5);
        table.addCell(cell);
    } */
}
