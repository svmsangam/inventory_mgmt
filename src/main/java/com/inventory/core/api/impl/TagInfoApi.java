package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ITagInfoApi;
import com.inventory.core.model.converter.TagInfoConverter;
import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.dto.TagInfoDTO;
import com.inventory.core.model.entity.TagInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.TagInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/9/17.
 */
@Service
@Transactional
public class TagInfoApi implements ITagInfoApi {

    @Autowired
    private TagInfoConverter tagInfoConverter;

    @Autowired
    private TagInfoRepository tagInfoRepository;

    @Override
    public TagInfoDTO save(TagInfoDTO tagInfoDTO) throws InterruptedException {

        TagInfo tagInfo = tagInfoConverter.convertToEntity(tagInfoDTO);

        tagInfo.setStatus(Status.ACTIVE);

        tagInfo = tagInfoRepository.save(tagInfo);

        return tagInfoConverter.convertToDto(tagInfo);
    }

    @Override
    public TagInfoDTO update(TagInfoDTO tagInfoDTO) {

        TagInfo tagInfo = tagInfoRepository.findById(tagInfoDTO.getTagId()).orElseThrow();

        tagInfo = tagInfoConverter.copyConvertToEntity(tagInfoDTO, tagInfo);

        return tagInfoConverter.convertToDto(tagInfoRepository.save(tagInfo));
    }

    @Override
    public void delete(long tagId) {

        TagInfo tagInfo = tagInfoRepository.findById(tagId);

        tagInfo.setStatus(Status.DELETED);

        tagInfoRepository.save(tagInfo);
    }

    @Override
    public TagInfoDTO show(long tagId, long storeId, Status status) {
        return tagInfoConverter.convertToDto(tagInfoRepository.findByIdAndStatusAndStoreInfo(tagId, status, storeId));
    }

    @Override
    public List<TagInfoDTO> list(Status status, long storeId) {
        return tagInfoConverter.convertToDtoList(tagInfoRepository.findAllByStatusAndStoreInfo(status, storeId));
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return PageRequest.of(page, size, Sort.by(direction, properties));
    }

    @Override
    public List<TagInfoDTO> search(Status status, String q, int page, int size, long storeId) {
        Pageable pageable = createPageRequest(page, size ,"name" , Sort.Direction.ASC);

        return tagInfoConverter.convertToDtoList(tagInfoRepository.findAllBySearch(q , Status.ACTIVE , storeId , pageable));
    }

    @Override
    public TagInfoDTO getTagByNameAndStoreAndStatus(String tagName, long storeId, Status status) {
        return tagInfoConverter.convertToDto(tagInfoRepository.findByNameAndStatusAndStoreInfo(tagName, status, storeId));
    }

    @Override
    public TagInfoDTO getTagByIdAndStatusAndStore(long id, Status status, long storeId){
        return tagInfoConverter.convertToDto(tagInfoRepository.findByIdAndStatusAndStoreInfo(id, status, storeId));
    }

    @Override
    public long tagCount(Status status, long storeId) {

        Long count = tagInfoRepository.countAllByStatusAndStoreInfo(status, storeId);
        if (count == null)
            return 0;

        return count;
    }
}
