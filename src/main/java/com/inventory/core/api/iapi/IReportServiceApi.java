package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.itextpdf.text.DocumentException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by dhiraj on 10/9/17.
 */
public interface IReportServiceApi {

    void writePdfReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException;

    void writeXlsReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException;

    String pdfWriterForInvoice(InvoiceInfoDTO invoice) throws FileNotFoundException, DocumentException;

    void writeXlsReport(long invoiceId, long storeId, HttpServletResponse response, HttpServletRequest request) throws Exception;

    String qrCodeGenerator(String code);
}
