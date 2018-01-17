package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.OrderReturnInfoDTO;

/**
 * Created by dhiraj on 1/17/18.
 */
public interface IOrderReturnInfoApi {

    OrderReturnInfoDTO save(OrderReturnInfoDTO orderReturnInfoDTO);
}
