package com.inventory.core.api.impl;

/**
 * Created by dhiraj on 10/9/17.
 */

import com.inventory.core.api.iapi.IOrderItemInfoApi;
import com.inventory.core.api.iapi.IReportServiceApi;
import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.FilePath;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Service
class ReportServiceApi implements IReportServiceApi{

    @Autowired
    private IOrderItemInfoApi orderItemInfoApi;

    public void writePdfReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException{
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=" + (reportName == null ? jp.getName() : reportName).replace('"', '_') + ".pdf");

        OutputStream outStream = response.getOutputStream();

        final byte[] pdfBytes = JasperExportManager.exportReportToPdf(jp);

        response.setContentLength(pdfBytes.length);

        final ByteArrayInputStream bais = new ByteArrayInputStream(pdfBytes);
        IOUtils.copy(bais, outStream);

        outStream.flush();

        IOUtils.closeQuietly(bais);
        IOUtils.closeQuietly(outStream);
    }

    public void writeXlsReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException{
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition", "inline; filename=" + (reportName == null ? jp.getName() : reportName).replace('"', '_') + ".xlsx");


        JRXlsxExporter xlsxExporter = new JRXlsxExporter();


        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();

        xlsxExporter.setExporterInput(new SimpleExporterInput(jp));
        xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
        xlsxExporter.exportReport();

        final byte[] rawBytes = xlsReport.toByteArray();
        response.setContentLength(rawBytes.length);

        final ByteArrayInputStream bais = new ByteArrayInputStream(rawBytes);

        final OutputStream outStream = response.getOutputStream();
        IOUtils.copy(bais, outStream);

        outStream.flush();

        IOUtils.closeQuietly(xlsReport);
        IOUtils.closeQuietly(bais);
        IOUtils.closeQuietly(outStream);
    }



    @Override
    public String pdfWriterForInvoice(InvoiceInfoDTO invoice) throws FileNotFoundException, DocumentException {

        String file = Long.toString(System.currentTimeMillis());;//request.getServletPath()+"/PDFfile.pdf";

        String path = FilePath.getOSPath();

        OrderInfoDTO orderInfoDTO = invoice.getOrderInfo();

        ClientInfoDTO buyerInfo = orderInfoDTO.getClientInfo();

        List<OrderItemInfoDTO> orderItemList = orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, orderInfoDTO.getOrderId());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        // Left
        Paragraph invoiceDate = new Paragraph(invoice.getInvoiceDate().toString());
        invoiceDate.setAlignment(Element.ALIGN_RIGHT);
        document.add(invoiceDate);

        Paragraph invoiceNo = new Paragraph(invoice.getInvoiceNo());
        invoiceNo.setAlignment(Element.ALIGN_RIGHT);
        document.add(invoiceNo);

        Paragraph orderNo = new Paragraph(orderInfoDTO.getOrderNo());
        orderNo.setAlignment(Element.ALIGN_RIGHT);
        document.add(orderNo);

        Paragraph buyerDetails = new Paragraph("Buyer Details");
        buyerDetails.setAlignment(Element.ALIGN_LEFT);
        document.add(buyerDetails);

        Paragraph buyerName = new Paragraph("Name: "+ buyerInfo.getName());
        buyerName.setAlignment(Element.ALIGN_LEFT);
        document.add(buyerName);

        Paragraph buyerAddress = new Paragraph("City: "+ buyerInfo.getCityInfoDTO().getCityName());
        buyerAddress.setAlignment(Element.ALIGN_LEFT);
        document.add(buyerAddress);

        Paragraph buyerPan = new Paragraph("Street: " + buyerInfo.getStreet());
        buyerPan.setAlignment(Element.ALIGN_LEFT);
        document.add(buyerPan);

        Paragraph buyerPhone = new Paragraph("Contact: "+ buyerInfo.getContact());
        buyerPhone.setAlignment(Element.ALIGN_LEFT);
        document.add(buyerPhone);

        Paragraph buyerEmail = new Paragraph("Email: "+ buyerInfo.getEmail());
        buyerEmail.setAlignment(Element.ALIGN_LEFT);
        document.add(buyerEmail);

        Paragraph orderDetails = new Paragraph("Order and Delivery Details");
        orderDetails.setAlignment(Element.ALIGN_RIGHT);
        document.add(orderDetails);

        Paragraph ordered = new Paragraph("Order Date: "+ orderInfoDTO.getOrderDate());
        ordered.setAlignment(Element.ALIGN_RIGHT);
        document.add(ordered);

        Paragraph deliveryDate = new Paragraph("Delivery Date: " + orderInfoDTO.getDeliveryDate());
        deliveryDate.setAlignment(Element.ALIGN_RIGHT);
        document.add(deliveryDate);

        Paragraph deliveryAddress = new Paragraph("Delivered To: "+ orderInfoDTO.getDeliveryAddress());
        deliveryAddress.setAlignment(Element.ALIGN_RIGHT);
        document.add(deliveryAddress);

        Paragraph receivableAmount = new Paragraph("Receivalble Amount: " + invoice.getReceivableAmount());
        receivableAmount.setAlignment(Element.ALIGN_RIGHT);
        document.add(receivableAmount);


        PdfPTable table = new PdfPTable(5);
        // the cell object
        PdfPCell cell01 = new PdfPCell(new Phrase("Product"));
        table.addCell(cell01);

        PdfPCell cell02 = new PdfPCell(new Phrase("Quantity"));
        table.addCell(cell02);

        PdfPCell cell03 =  new PdfPCell(new Phrase("Rate"));
        table.addCell(cell03);

        PdfPCell cell04 = new PdfPCell(new Phrase("Discount"));
        table.addCell(cell04);

        PdfPCell cell05 = new PdfPCell(new Phrase("Total"));
        table.addCell(cell05);

        for (OrderItemInfoDTO orderItemDTO : orderItemList) {


            PdfPCell cell1 = new PdfPCell(new Phrase(orderItemDTO.getItemInfoDTO().getProductInfo().getName()));
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase(String.valueOf(orderItemDTO.getQuantity())));
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(orderItemDTO.getRate())));
            table.addCell(cell3);

            PdfPCell cell4=  new PdfPCell(new Phrase(String.valueOf(orderItemDTO.getDiscount())));
            table.addCell(cell4);

            double amount = orderItemDTO.getRate() * orderItemDTO.getQuantity();

            amount = amount - (amount * (orderItemDTO.getDiscount() /100));

            PdfPCell cell5=  new PdfPCell(new Phrase(String.valueOf(amount)));
            table.addCell(cell5);
        }


        document.add(table);
        Paragraph enteredBy = new Paragraph(invoice.getCreatedByName());
        enteredBy.setAlignment(Element.ALIGN_RIGHT);
        document.add(enteredBy);
        document.close();

        return file;

    }

}
