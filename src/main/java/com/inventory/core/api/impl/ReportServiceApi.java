package com.inventory.core.api.impl;

/**
 * Created by dhiraj on 10/9/17.
 */

import com.inventory.core.api.iapi.IInvoiceInfoApi;
import com.inventory.core.api.iapi.IOrderItemInfoApi;
import com.inventory.core.api.iapi.IReportServiceApi;
import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.FilePath;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeQRCode;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.List;

@Service
class ReportServiceApi implements IReportServiceApi {

    @Autowired
    private IOrderItemInfoApi orderItemInfoApi;

    @Autowired
    private XLSReport xlsReport;

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    public void writePdfReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException {
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

    public void writeXlsReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException {
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

        String file = Long.toString(System.currentTimeMillis());
        //request.getServletPath()+"/PDFfile.pdf";

        String path = FilePath.getOSPath();

        OrderInfoDTO orderInfoDTO = invoice.getOrderInfo();

        ClientInfoDTO buyerInfo = orderInfoDTO.getClientInfo();

        List<OrderItemInfoDTO> orderItemList = orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, orderInfoDTO.getOrderId());

        Document document = new Document();

        document.setPageSize(PageSize.A4);

        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        Font font = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);

        Paragraph storename = new Paragraph("" + invoice.getStoreInfoDTO().getName(), font);
        storename.setAlignment(Element.ALIGN_CENTER);


        document.add(storename);

        Paragraph storeaddress = new Paragraph("" + invoice.getStoreInfoDTO().getCityName());
        storeaddress.setAlignment(Element.ALIGN_CENTER);
        document.add(storeaddress);

        Paragraph storecontact = new Paragraph("" + invoice.getStoreInfoDTO().getContact());
        storecontact.setAlignment(Element.ALIGN_CENTER);
        document.add(storecontact);

        Paragraph invoiceDate = new Paragraph("Invoice Date : " + getDate(invoice.getInvoiceDate()));
        invoiceDate.setAlignment(Element.ALIGN_RIGHT);
        document.add(invoiceDate);

        Paragraph invoiceNo = new Paragraph("Invoice No : " + invoice.getInvoiceNo());
        invoiceNo.setAlignment(Element.ALIGN_RIGHT);
        document.add(invoiceNo);

        Font font1 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
        Paragraph buyerDetails = new Paragraph("Buyer Details", font1);
        buyerDetails.setAlignment(Element.ALIGN_LEFT);
        document.add(buyerDetails);

        if (buyerInfo.getCompanyName() == null || "".equals(buyerInfo.getCompanyName())) {
            Paragraph buyerName = new Paragraph("Name: " + buyerInfo.getName());
            buyerName.setAlignment(Element.ALIGN_LEFT);
            document.add(buyerName);
        } else {
            Paragraph buyerName = new Paragraph("Name: " + buyerInfo.getCompanyName());
            buyerName.setAlignment(Element.ALIGN_LEFT);
            document.add(buyerName);
        }


        if (buyerInfo.getCityInfoDTO() != null) {
            Paragraph buyerAddress = new Paragraph("City: " + buyerInfo.getCityInfoDTO().getCityName());
            buyerAddress.setAlignment(Element.ALIGN_LEFT);
            document.add(buyerAddress);
        }

        if (buyerInfo.getStreet() != null && !"".equals(buyerInfo.getStreet())) {
            Paragraph buyerPan = new Paragraph("Street: " + buyerInfo.getStreet());
            buyerPan.setAlignment(Element.ALIGN_LEFT);
            document.add(buyerPan);
        }

        if (buyerInfo.getContact() != null && !"".equals(buyerInfo.getContact())) {
            Paragraph buyerPhone = new Paragraph("Contact: " + buyerInfo.getContact());
            buyerPhone.setAlignment(Element.ALIGN_LEFT);
            document.add(buyerPhone);
        }

        if (buyerInfo.getEmail() != null && !"".equals(buyerInfo.getEmail())) {
            Paragraph buyerEmail = new Paragraph("Email: " + buyerInfo.getEmail());
            buyerEmail.setAlignment(Element.ALIGN_LEFT);
            document.add(buyerEmail);
        }

        PdfPTable table = new PdfPTable(5);
        table.setSpacingBefore(15);
        table.setSpacingAfter(10);
        table.setWidthPercentage(530 / 5.23f);

        PdfPCell cell01 = new PdfPCell(new Phrase("Product"));
        cell01.setBackgroundColor(BaseColor.GRAY);
        cell01.setMinimumHeight(30);
        cell01.setPaddingLeft(10);
        table.addCell(cell01);

        PdfPCell cell02 = new PdfPCell(new Phrase("Quantity"));
        cell02.setBackgroundColor(BaseColor.GRAY);
        cell02.setMinimumHeight(30);
        cell02.setPaddingLeft(10);
        table.addCell(cell02);

        PdfPCell cell03 = new PdfPCell(new Phrase("Rate"));
        cell03.setBackgroundColor(BaseColor.GRAY);
        cell03.setMinimumHeight(30);
        cell03.setPaddingLeft(10);
        table.addCell(cell03);

        PdfPCell cell04 = new PdfPCell(new Phrase("Discount(%)"));
        cell04.setBackgroundColor(BaseColor.GRAY);
        cell04.setMinimumHeight(30);
        cell04.setPaddingLeft(10);
        table.addCell(cell04);

        PdfPCell cell05 = new PdfPCell(new Phrase("Total"));
        cell05.setBackgroundColor(BaseColor.GRAY);
        cell05.setMinimumHeight(30);
        cell05.setPaddingLeft(10);
        table.addCell(cell05);

        for (OrderItemInfoDTO orderItemDTO : orderItemList) {


            PdfPCell cell1 = new PdfPCell(new Phrase(orderItemDTO.getItemInfoDTO().getProductInfo().getName()));
            cell1.setMinimumHeight(20);
            cell1.setPaddingLeft(5);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase(String.valueOf(orderItemDTO.getQuantity())));
            cell2.setMinimumHeight(20);
            cell2.setPaddingLeft(5);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(formatter(orderItemDTO.getRate()))));
            cell3.setMinimumHeight(20);
            cell3.setPaddingLeft(5);
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Phrase(String.valueOf(formatter(orderItemDTO.getDiscount()))));
            cell4.setMinimumHeight(20);
            cell4.setPaddingLeft(5);
            table.addCell(cell4);

            double amount = orderItemDTO.getRate() * orderItemDTO.getQuantity();

            amount = amount - (amount * (orderItemDTO.getDiscount() / 100));

            PdfPCell cell5 = new PdfPCell(new Phrase(String.valueOf(formatter(amount))));
            cell5.setMinimumHeight(20);
            cell5.setPaddingLeft(5);
            table.addCell(cell5);
        }

        PdfPCell cell4 = new PdfPCell(new Phrase("Total : "));
        cell4.setColspan(4);
        cell4.setPaddingLeft(320);
        cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell4.setMinimumHeight(20);
        table.addCell(cell4);

        PdfPCell cell5 = new PdfPCell(new Phrase("" + formatter(invoice.getOrderInfo().getTotalAmount())));
        cell5.setMinimumHeight(20);
        cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell5);


        PdfPCell cell9 = new PdfPCell(new Phrase("Tax(%) : "));
        cell9.setColspan(4);
        cell9.setPaddingLeft(320);
        cell9.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell9.setMinimumHeight(20);
        table.addCell(cell9);

        PdfPCell cell10 = new PdfPCell(new Phrase("" + formatter(invoice.getOrderInfo().getTax())));
        cell10.setMinimumHeight(20);
        cell10.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell10);


        PdfPCell cell14 = new PdfPCell(new Phrase("Grand Total : "));
        cell14.setColspan(4);
        cell14.setPaddingLeft(320);
        cell14.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell14.setMinimumHeight(20);
        table.addCell(cell14);

        PdfPCell cell15 = new PdfPCell(new Phrase("" + formatter(invoice.getTotalAmount())));
        cell15.setMinimumHeight(20);
        cell15.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell15);

        PdfPCell cell19 = new PdfPCell(new Phrase("Entered By : "));
        cell19.setColspan(4);
        cell19.setPaddingLeft(320);
        cell19.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell19.setMinimumHeight(20);
        table.addCell(cell19);

        PdfPCell cell20 = new PdfPCell(new Phrase(invoice.getCreatedByName()));
        cell20.setMinimumHeight(20);
        cell20.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell20);

        document.add(table);

        String qrCode = storename + " " + invoice.getInvoiceNo() + " " + invoice.getTotalAmount();

        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(qrCode, 1000, 1000, null);
        Image codeQrImage = barcodeQRCode.getImage();
        codeQrImage.scaleAbsolute(100, 100);

        Paragraph qrcode = new Paragraph();
        qrcode.setAlignment(Element.ALIGN_RIGHT);
        qrcode.setIndentationLeft(430);
        qrcode.setIndentationRight(500);
        qrcode.add(codeQrImage);
        document.add(qrcode);

        document.close();

        return file;

    }

    @Override
    public void writeXlsReport(long invoiceId, long storeId, HttpServletResponse response, HttpServletRequest request) throws Exception {

        Map<String, Object> map = new HashMap<>();

        map.put("invoice", invoiceInfoApi.show(invoiceId, storeId, Status.ACTIVE));

        xlsReport.buildExcelDocument(map, new XSSFWorkbook(), request, response);
    }

    @Override
    public String qrCodeGenerator(String code){


        try {

            BarcodeQRCode barcodeQRCode = new BarcodeQRCode(code, 250, 250, null);
            Image codeQrImage = barcodeQRCode.getImage();
            java.awt.Image imageAwt = barcodeQRCode.createAwtImage(Color.BLACK, Color.WHITE);

            BufferedImage bImage= new BufferedImage(imageAwt.getWidth(null), (int) codeQrImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bImage.createGraphics();
            g.drawImage(imageAwt, 0, 0, null);
            g.dispose();

            String path = System.currentTimeMillis() + ".jpg";

            File outputfile = new File(FilePath.getOSPath());

            if (!outputfile.exists())
                outputfile.mkdirs();

            ImageIO.write(bImage, "jpg", new File(FilePath.getOSPath()+ "/" + path));
            System.out.println("image uploading..............."  + outputfile.getAbsolutePath());

            return path;

        } catch (BadElementException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private double formatter(double value){

        DecimalFormat df = new DecimalFormat("###.###");

        return Double.parseDouble(df.format(value));

    }

    private String getDate(Date date){
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("MMM dd, yyyy");

        return dateFormatYear.format(new Date());
    }

}
