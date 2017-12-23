package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IOrderItemInfoApi;
import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


        renderWorkbook(workbook , response);



    }
}
