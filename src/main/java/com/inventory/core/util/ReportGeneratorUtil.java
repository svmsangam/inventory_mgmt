package com.inventory.core.util;

import java.awt.Color;
import java.util.Date;
import java.util.List;

import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.dto.LedgerInfoDTO;
import com.inventory.core.model.dto.ProductInfoDTO;
import com.inventory.core.model.enumconstant.AccountEntryType;
import com.inventory.core.model.enumconstant.LedgerEntryType;

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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Created by dhiraj on 10/10/17.
 */
public class ReportGeneratorUtil {

	/*
	 * public JasperPrint ledgerReport(List<LedgerInfoDTO> list, String title,
	 * String subTitle , double dr , double cr , double balance) throws
	 * ColumnBuilderException, JRException, ClassNotFoundException {
	 * 
	 * Style headerStyle = createHeaderStyle(); Style detailTextStyle =
	 * createDetailTextStyle(); Style detailNumberStyle = createDetailNumberStyle();
	 * 
	 * DynamicReport dynaReport = ledgerReportProcessor(title, subTitle,
	 * headerStyle, detailTextStyle, detailNumberStyle);
	 * 
	 * 
	 * JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new
	 * ClassicLayoutManager(), new JRBeanCollectionDataSource(list));
	 * 
	 * List<LedgerReportFooterDTO> ledgerReportFooterDTOS = new ArrayList<>();
	 * 
	 * LedgerReportFooterDTO ledgerReportFooterDTO = new LedgerReportFooterDTO();
	 * 
	 * ledgerReportFooterDTO.setCr(cr); ledgerReportFooterDTO.setDr(dr);
	 * ledgerReportFooterDTO.setBalance(balance);
	 * 
	 * ledgerReportFooterDTOS.add(ledgerReportFooterDTO);
	 * 
	 * 
	 * DynamicReport dynaReportFooter = ledgerReportProcessorFooter(headerStyle ,
	 * detailNumberStyle);
	 * 
	 * JasperPrint jp1 = DynamicJasperHelper.generateJasperPrint(dynaReportFooter,
	 * new ClassicLayoutManager(),new
	 * JRBeanCollectionDataSource(ledgerReportFooterDTOS));
	 * 
	 * for (int i = 0; i < jp1.getPages().size(); i++) {
	 * jp.addPage(jp1.getPages().get(i)); }
	 * 
	 * 
	 * return jp; }
	 */

	public JasperPrint ledgerReport(List<LedgerInfoDTO> list, String title, String subTitle)
			throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();

		DynamicReport dynaReport = ledgerReportProcessor(title, subTitle, headerStyle, detailTextStyle,
				detailNumberStyle);

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(list));

		return jp;
	}

	public JasperPrint InvoiceReport(List<InvoiceInfoDTO> list, String title, String subTitle)
			throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();

		DynamicReport dynaReport = invoiceReportProcessor(title, subTitle, headerStyle, detailTextStyle,
				detailNumberStyle);

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(list));

		return jp;
	}

	public JasperPrint productReport(List<ProductInfoDTO> list, String title, String subTitle)
			throws ColumnBuilderException, JRException, ClassNotFoundException {

		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();

		DynamicReport dynaReport = productReportProcessor(title, subTitle, headerStyle, detailTextStyle,
				detailNumberStyle);

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
				new JRBeanCollectionDataSource(list));

		return jp;
	}

	private Style createHeaderStyle() {

		StyleBuilder sb = new StyleBuilder(true);
		/* sb.setFont(Font.VERDANA_MEDIUM_BOLD); */
		/* sb.setFont(new Font(Font.SMALL, Font._FONT_GEORGIA, true)); */
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
		/* sb.setFont(Font.VERDANA_MEDIUM); */
		sb.setBorder(Border.THIN());
		sb.setBorderColor(Color.BLACK);
		sb.setStretchWithOverflow(false);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.LEFT);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setPaddingLeft(2);
		return sb.build();
	}

	private Style createDetailNumberStyle() {
		StyleBuilder sb = new StyleBuilder(true);
		/* sb.setFont(Font.VERDANA_MEDIUM); */
		sb.setBorder(Border.THIN());
		sb.setBorderColor(Color.BLACK);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.RIGHT);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setPaddingRight(4);
		sb.setPattern("#,##0.00");
		return sb.build();
	}

	private Style oddRowStyle() {

		Style oddRowStyle = new Style();

		oddRowStyle.setBorder(Border.NO_BORDER());
		Color veryLightGrey = new Color(230, 230, 230);
		oddRowStyle.setBackgroundColor(veryLightGrey);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		return oddRowStyle;
	}

	private AbstractColumn createColumn(String property, Class<?> type, String title, int width, Style headerStyle,
			Style detailStyle) throws ColumnBuilderException {
		AbstractColumn columnState = ColumnBuilder.getNew().setColumnProperty(property, type.getName()).setTitle(title)
				.setWidth(Integer.valueOf(width)).setStyle(detailStyle).setHeaderStyle(headerStyle).build();
		return columnState;
	}

	private DynamicReport ledgerReportProcessor(String title, String subTitle, Style headerStyle, Style detailTextStyle,
			Style detailNumStyle) throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();

		AbstractColumn dateTime = createColumn("date", Date.class, "Date and Time", 48, headerStyle, detailTextStyle);
		AbstractColumn accountNo = createColumn("accountNo", String.class, "Account Number", 53, headerStyle,
				detailTextStyle);
		AbstractColumn amount = createColumn("amount", Double.class, "Amount", 45, headerStyle, detailNumStyle);
		AbstractColumn ledgerEntryType = createColumn("ledgerEntryType", LedgerEntryType.class, "Ledger Entry", 24,
				headerStyle, detailTextStyle);
		AbstractColumn accountEntryType = createColumn("accountEntryType", AccountEntryType.class, "Account Entry", 20,
				headerStyle, detailTextStyle);
		AbstractColumn remarks = createColumn("remarks", String.class, "Remarks", 68, headerStyle, detailTextStyle);

		report.addColumn(dateTime).addColumn(accountNo).addColumn(amount).addColumn(ledgerEntryType)
				.addColumn(accountEntryType).addColumn(remarks).setPrintBackgroundOnOddRows(true)
				.setOddRowBackgroundStyle(oddRowStyle()).setColumnsPerPage(1).setUseFullPageWidth(true)
				.setColumnSpace(5);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		/* titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true)); */

		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		/* subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true)); */

		report.setTitle(title);
		report.setTitleStyle(titleStyle.build());
		report.setSubtitle(subTitle);
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();

	}

	private DynamicReport invoiceReportProcessor(String title, String subTitle, Style headerStyle,
			Style detailTextStyle, Style detailNumStyle) throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();

		AbstractColumn dateTime = createColumn("invoiceDate", Date.class, "Invoice Date", 48, headerStyle,
				detailTextStyle);
		AbstractColumn invoiceNo = createColumn("invoiceNo", String.class, "Invoice Number", 30, headerStyle,
				detailTextStyle);
		AbstractColumn amount = createColumn("totalAmount", Double.class, "Amount", 30, headerStyle, detailNumStyle);
		AbstractColumn receivableamount = createColumn("receivableAmount", Double.class, "Receivable Amount", 30,
				headerStyle, detailNumStyle);
		AbstractColumn fiscalYear = createColumn("fiscalYearInfo.title", String.class, "Fiscal Fear", 24, headerStyle,
				detailTextStyle);
		AbstractColumn client = createColumn("orderInfo.clientInfo.name", String.class, "Client Name", 50, headerStyle,
				detailTextStyle);

		report.addColumn(dateTime).addColumn(invoiceNo).addColumn(amount).addColumn(receivableamount)
				.addColumn(fiscalYear).addColumn(client).setPrintBackgroundOnOddRows(true)
				.setOddRowBackgroundStyle(oddRowStyle()).setColumnsPerPage(1).setUseFullPageWidth(true)
				.setColumnSpace(5);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		/* titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true)); */

		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		/* subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true)); */

		report.setTitle(title);
		report.setTitleStyle(titleStyle.build());
		report.setSubtitle(subTitle);
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();

	}

	private DynamicReport productReportProcessor(String title, String subTitle, Style headerStyle,
			Style detailTextStyle, Style detailNumStyle) throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();

		AbstractColumn name = createColumn("name", String.class, "Name", 48, headerStyle, detailTextStyle);
		AbstractColumn trendingLevel = createColumn("trendingLevel.value", String.class, "Trending Level", 30,
				headerStyle, detailTextStyle);
		AbstractColumn subCategoryInfoName = createColumn("subCategoryInfo.name", String.class, "Category", 30,
				headerStyle, detailNumStyle);
		AbstractColumn inStock = createColumn("stockInfo.inStock", Integer.class, "InStock", 30, headerStyle,
				detailNumStyle);

		report.addColumn(name).addColumn(trendingLevel).addColumn(subCategoryInfoName).addColumn(inStock)
				.setPrintBackgroundOnOddRows(true).setOddRowBackgroundStyle(oddRowStyle()).setColumnsPerPage(1)
				.setUseFullPageWidth(true).setColumnSpace(5);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		/* titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true)); */

		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		/* subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true)); */

		report.setTitle(title);
		report.setTitleStyle(titleStyle.build());
		report.setSubtitle(subTitle);
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();

	}

	private DynamicReport ledgerReportProcessorFooter(Style hraderStyle, Style detailNumStyle)
			throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();

		AbstractColumn dr = createColumn("dr", Object.class, "Debit Amount", 45, hraderStyle, detailNumStyle);
		AbstractColumn cr = createColumn("cr", Object.class, "Credit Amount", 45, hraderStyle, detailNumStyle);
		AbstractColumn balance = createColumn("balance", Object.class, "Balance", 45, hraderStyle, detailNumStyle);

		report.addColumn(dr).addColumn(cr).addColumn(balance).setPrintBackgroundOnOddRows(true)
				.setOddRowBackgroundStyle(oddRowStyle()).setColumnsPerPage(1).setUseFullPageWidth(true)
				.setColumnSpace(5);

		report.setUseFullPageWidth(true);
		return report.build();

	}
}
