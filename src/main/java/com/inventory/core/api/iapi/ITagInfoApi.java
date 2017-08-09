package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.TagInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 8/9/17.
 */
public interface ITagInfoApi {

    TagInfoDTO save(TagInfoDTO tagInfoDTO);

    TagInfoDTO update(TagInfoDTO tagInfoDTO);

    void delete(long tagId);

    TagInfoDTO show(long tagId , long storeId , Status status);

    List<TagInfoDTO> list(Status status , long storeId);

    TagInfoDTO getTagByNameAndStoreAndStatus(String tagName , long storeId , Status status);

    long tagCount(Status status  , long storeId);

}
