package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.OrderFilterDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by dhiraj on 8/27/17.
 */
public interface IOrderInfoApi {

    OrderInfoDTO save(OrderInfoDTO orderInfoDTO);

    OrderInfoDTO saveQuickSale(OrderInfoDTO orderInfoDTO);

    OrderInfoDTO show(Status status , long orderId , long storeId);

    List<OrderInfoDTO> listSale(Status status , long storeId , int page , int size);

    List<OrderInfoDTO> listTopSale(Status status , long storeId , int page , int size);

    long countListSale(Status status , long storeId);

    long countSaleByStatusAndStoreInfoAndSaleTrack(Status status , long storeId , SalesOrderStatus  track);

    String generatOrderNumber(long storeId);

    @Lock(LockModeType.OPTIMISTIC)
    void updateAmount(long orderId);

    OrderInfoDTO updateSaleTrack(long orderId , SalesOrderStatus track , long createdById);

    OrderInfoDTO cancelQuickSale(long orderId);

    List<OrderInfoDTO> filter(OrderFilterDTO filterDTO);

    long filterCount(OrderFilterDTO filterDTO);
}
