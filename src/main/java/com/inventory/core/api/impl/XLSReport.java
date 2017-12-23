package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IOrderItemInfoApi;
import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dhiraj on 12/23/17.
 */
@Service
public class XLSReport extends AbstractXlsxView {

    @Autowired
    private IOrderItemInfoApi orderItemInfoApi;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        InvoiceInfoDTO invoice = (InvoiceInfoDTO) model.get("invoice");

        OrderInfoDTO orderInfoDTO = invoice.getOrderInfo();

        ClientInfoDTO buyerInfo = orderInfoDTO.getClientInfo();

        List<OrderItemInfoDTO> orderItemList = orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, orderInfoDTO.getOrderId());


        String filename = buyerInfo.getName() + "_" + invoice.getInvoiceNo() + ".xls";

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=" +filename);


        Sheet sheet = workbook.createSheet("Invoice");

        //store info start
        Row storenamerow = sheet.createRow(2);
        Cell storenamecell = storenamerow.createCell(4);
        storenamecell.setCellValue(invoice.getStoreInfoDTO().getName());

        Row storeaddressrow = sheet.createRow(3);
        Cell storeaddresscell = storeaddressrow.createCell(4);
        storeaddresscell.setCellValue(invoice.getStoreInfoDTO().getCityName());

        Row storecontactrow = sheet.createRow(4);
        Cell storecontactcell = storecontactrow.createCell(4);
        storecontactcell.setCellValue(invoice.getStoreInfoDTO().getContact());

        //store info end

        //invoice info start
        Row invoicerow = sheet.createRow(6);
        Cell invoicenocell = invoicerow.createCell(6);
        invoicenocell.setCellValue("invoiceNo");
        Cell invoicenonextcell = invoicerow.createCell(7);
        invoicenonextcell.setCellValue(invoice.getInvoiceNo());

        Row invoicedaterow = sheet.createRow(7);
        Cell invoicedatestrcell = invoicedaterow.createCell(6);
        invoicedatestrcell.setCellValue("invoice date");
        Cell invoicedatecell = invoicedaterow.createCell(7);
        invoicedatecell.setCellValue(getDate(invoice.getInvoiceDate()));

        //invoice info end

        //buyer info start

        Cell buyernamestrcell = invoicerow.createCell(1);
        buyernamestrcell.setCellValue("Buyer Details");

        Cell buyernameheadercell = invoicedaterow.createCell(1);
        buyernameheadercell.setCellValue("Name");

        Cell buyernamecell = invoicedaterow.createCell(2);
        buyernamecell.setCellValue(buyerInfo.getName());

        Row cityrow = sheet.createRow(8);
        Cell cityheadercell = cityrow.createCell(1);
        cityheadercell.setCellValue("City");
        Cell citycell = cityrow.createCell(2);
        citycell.setCellValue(buyerInfo.getCityInfoDTO().getCityName());

        Row streetrow = sheet.createRow(9);
        Cell streetheadercell = streetrow.createCell(1);
        streetheadercell.setCellValue("Street");
        Cell streetcell = streetrow.createCell(2);
        streetcell.setCellValue(buyerInfo.getStreet());

        Row contactrow = sheet.createRow(10);
        Cell contactheadercell = contactrow.createCell(1);
        contactheadercell.setCellValue("Contact");
        Cell contactcell = contactrow.createCell(2);
        contactcell.setCellValue(buyerInfo.getContact());

        Row emailrow = sheet.createRow(11);
        Cell emailheadercell = emailrow.createCell(1);
        emailheadercell.setCellValue("Email");
        Cell emailcell = emailrow.createCell(2);
        emailcell.setCellValue(buyerInfo.getEmail());

        //buyer info end


        //order item info start

        Row row = sheet.createRow(13);

        Cell cellProductHeader = row.createCell(1);
        cellProductHeader.setCellValue("Product");

        Cell cellQuantityHeader = row.createCell(2);
        cellQuantityHeader.setCellValue("Quantity");

        Cell cellRateHeader = row.createCell(3);
        cellRateHeader.setCellValue("Rate");

        Cell cellDiscountHeader = row.createCell(4);
        cellDiscountHeader.setCellValue("Discount(%)");

        Cell cellTotalHeader = row.createCell(5);
        cellTotalHeader.setCellValue("Total");

        int count = 14;

        for (OrderItemInfoDTO orderItemDTO : orderItemList) {

            Row rownew = sheet.createRow(count);

            Cell cellProduct = rownew.createCell(1);
            cellProduct.setCellValue(orderItemDTO.getItemInfoDTO().getProductInfo().getName());

            Cell cellQuantity = rownew.createCell(2);
            cellQuantity.setCellValue(orderItemDTO.getQuantity());

            Cell cellRate = rownew.createCell(3);
            cellRate.setCellValue(orderItemDTO.getRate());

            Cell cellDiscount = rownew.createCell(4);
            cellDiscount.setCellValue(orderItemDTO.getDiscount());

            double amount = orderItemDTO.getRate() * orderItemDTO.getQuantity();

            amount = amount - (amount * (orderItemDTO.getDiscount() / 100));

            Cell cellTotal = rownew.createCell(5);
            cellTotal.setCellValue(amount);

            count ++;

        }

        //order item info end


        //total amount start

        count = count +1;

        Row rowTotal = sheet.createRow(count);

        Cell cellSubTotalHeader = rowTotal.createCell(4);
        cellSubTotalHeader.setCellValue("SubTotal");
        Cell cellSubTotal = rowTotal.createCell(5);
        cellSubTotal.setCellValue(invoice.getOrderInfo().getTotalAmount());

        count = count +1;

        Row rowTax = sheet.createRow(count);

        Cell cellTaxHeader = rowTax.createCell(4);
        cellTaxHeader.setCellValue("Tax(%)");
        Cell cellTax = rowTax.createCell(5);
        cellTax.setCellValue(invoice.getOrderInfo().getTax());

        count = count +1;

        Row rowGrandTotal = sheet.createRow(count);

        Cell cellGrandTotalHeader = rowGrandTotal.createCell(4);
        cellGrandTotalHeader.setCellValue("Grand Total");
        Cell cellGrandTotal = rowGrandTotal.createCell(5);
        cellGrandTotal.setCellValue(invoice.getTotalAmount());

        count = count +1;

        Row rowEnteredBy = sheet.createRow(count);

        Cell cellEnteredByHeader = rowEnteredBy.createCell(4);
        cellEnteredByHeader.setCellValue("Entered By");
        Cell cellEnteredBy = rowEnteredBy.createCell(5);
        cellEnteredBy.setCellValue(invoice.getCreatedByName());



        //total amount end

        renderWorkbook(workbook , response);



    }

    private String getDate(Date date){
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("MMM dd, yyyy");

        return dateFormatYear.format(new Date());
    }
}
