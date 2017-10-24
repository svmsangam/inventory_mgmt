package com.inventory.core.api.iapi;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dhiraj on 10/9/17.
 */
public interface IReportServiceApi {

    void writePdfReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException;

    void writeXlsReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException;
}
