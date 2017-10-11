package com.inventory.core.util;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.inventory.core.model.dto.LedgerInfoDTO;
import com.inventory.core.model.enumconstant.AccountEntryType;
import com.inventory.core.model.enumconstant.LedgerEntryType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.awt.Color;

import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 10/10/17.
 */
public class ReportGeneratorUtil {

/*    public JasperPrint invoiceReport(List<TransactionDTO> listSale, String title, String subTitle) throws ColumnBuilderException, JRException, ClassNotFoundException {
        Style headerStyle = createHeaderStyle();
        Style detailTextStyle = createDetailTextStyle();
        Style detailNumberStyle = createDetailNumberStyle();
        DynamicReport dynaReport = transactionReportProcessor(title, subTitle, headerStyle, detailTextStyle, detailNumberStyle);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
                new JRBeanCollectionDataSource(listSale));

//		jp.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
        return jp;
    }*/

    public JasperPrint ledgerReport(List<LedgerInfoDTO> list, String title, String subTitle) throws ColumnBuilderException, JRException, ClassNotFoundException {
        Style headerStyle = createHeaderStyle();
        Style detailTextStyle = createDetailTextStyle();
        Style detailNumberStyle = createDetailNumberStyle();
        DynamicReport dynaReport = ledgerReportProcessor(title, subTitle, headerStyle, detailTextStyle, detailNumberStyle);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
                new JRBeanCollectionDataSource(list));


        return jp;
    }

    private Style createHeaderStyle() {

        StyleBuilder sb = new StyleBuilder(true);
		/*sb.setFont(Font.VERDANA_MEDIUM_BOLD);*/
		/*sb.setFont(new Font(Font.SMALL, Font._FONT_GEORGIA, true));*/
        sb.setBorder(Border.THIN());
        sb.setBorderBottom(Border.PEN_2_POINT());
        sb.setBorderColor(Color.BLACK);
        sb.setBackgroundColor(Color.getHSBColor(357.08f, 71.29f, 39.61f));
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.CENTER);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setTransparency(Transparency.OPAQUE);
        return sb.build();

    }

    private Style createDetailTextStyle() {
        StyleBuilder sb = new StyleBuilder(true);
		/*sb.setFont(Font.VERDANA_MEDIUM);*/
        sb.setBorder(Border.DOTTED());
        sb.setBorderColor(Color.BLACK);
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.LEFT);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setPaddingLeft(5);
        return sb.build();
    }

    private Style createDetailNumberStyle() {
        StyleBuilder sb = new StyleBuilder(true);
		/*sb.setFont(Font.VERDANA_MEDIUM);*/
        sb.setBorder(Border.DOTTED());
        sb.setBorderColor(Color.BLACK);
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.RIGHT);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setPaddingRight(5);
        sb.setPattern("#,##0.00");
        return sb.build();
    }

    private AbstractColumn createColumn(String property, Class<?> type, String title, int width, Style headerStyle, Style detailStyle)
            throws ColumnBuilderException {
        AbstractColumn columnState = ColumnBuilder.getNew()
                .setColumnProperty(property, type.getName())
                .setTitle(title)
                .setWidth(Integer.valueOf(width))
                .setStyle(detailStyle)
                .setHeaderStyle(headerStyle).build();
        return columnState;
    }

/*    private DynamicReport transactionReportProcessor(String title, String subTitle, Style headerStyle, Style detailTextStyle, Style detailNumStyle)
            throws ColumnBuilderException, ClassNotFoundException {

        DynamicReportBuilder report = new DynamicReportBuilder();

        AbstractColumn datTime = createColumn("date", String.class, "Date and Time", 50, headerStyle, detailTextStyle);
        AbstractColumn TransactionIde = createColumn("transactionIdentifier", String.class, "Transaction Identifier", 50, headerStyle, detailTextStyle);
        AbstractColumn sourceUSer = createColumn("originatingUserName", String.class, "Source User", 50, headerStyle, detailNumStyle);
        AbstractColumn destinationUser = createColumn("destinationUserName", String.class, "Destination User", 50, headerStyle, detailNumStyle);
        AbstractColumn amount = createColumn("amount", Double.class, "Amount", 30, headerStyle, detailTextStyle);
        AbstractColumn transactionStatus = createColumn("transactionStatus", TransactionStatus.class, "Transaction Status", 50, headerStyle, detailTextStyle);
        AbstractColumn TransactionType = createColumn("transactionType", TransactionType.class, "Transaction Type", 50, headerStyle, detailTextStyle);

        report.addColumn(datTime).addColumn(TransactionIde).addColumn(sourceUSer).addColumn(destinationUser)
                .addColumn(amount).addColumn(transactionStatus).addColumn(TransactionType);

        StyleBuilder titleStyle = new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
	*//*	titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true));*//*

        StyleBuilder subTitleStyle = new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		*//*subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true));*//*

        report.setTitle(title);
        report.setTitleStyle(titleStyle.build());
        report.setSubtitle(subTitle);
        report.setSubtitleStyle(subTitleStyle.build());
        report.setUseFullPageWidth(true);
        return report.build();
    }*/


    private DynamicReport ledgerReportProcessor(String title, String subTitle, Style headerStyle, Style detailTextStyle, Style detailNumStyle)
            throws ColumnBuilderException, ClassNotFoundException {

        DynamicReportBuilder report = new DynamicReportBuilder();

        AbstractColumn dateTime = createColumn("date", Date.class, "Date and Time", 50, headerStyle, detailTextStyle);
        AbstractColumn accountNo = createColumn("accountNo", String.class, "Account Number", 50, headerStyle, detailTextStyle);
        AbstractColumn amount = createColumn("amount", Double.class, "Amount", 50, headerStyle, detailNumStyle);
        AbstractColumn ledgerEntryType = createColumn("ledgerEntryType", LedgerEntryType.class, "LedgerEntry Type", 50, headerStyle, detailNumStyle);
        AbstractColumn accountEntryType = createColumn("accountEntryType", AccountEntryType.class, "AccountEntry Type", 30, headerStyle, detailTextStyle);
        AbstractColumn remarks = createColumn("remarks", String.class, "Remarks", 30, headerStyle, detailTextStyle);


        report.addColumn(dateTime).addColumn(accountNo).addColumn(amount).addColumn(ledgerEntryType)
                .addColumn(accountEntryType).addColumn(remarks);

        StyleBuilder titleStyle = new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
	/*	titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true));*/

        StyleBuilder subTitleStyle = new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		/*subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true));*/

        report.setTitle(title);
        report.setTitleStyle(titleStyle.build());
        report.setSubtitle(subTitle);
        report.setSubtitleStyle(subTitleStyle.build());
        report.setUseFullPageWidth(true);
        return report.build();

    }
}
