package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IInvoiceInfoApi;
import com.inventory.core.model.converter.InvoiceInfoConverter;
import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.entity.CodeGenerator;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.enumconstant.NumberStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CodeGeneratorRepository;
import com.inventory.core.model.repository.InvoiceInfoRepository;
import com.inventory.core.model.repository.StoreInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 9/13/17.
 */
@Service
@Transactional
public class InvoiceInfoApi implements IInvoiceInfoApi {

    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    @Autowired
    private CodeGeneratorRepository codeGeneratorRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private InvoiceInfoConverter invoiceInfoConverter;

    @Override
    public String generatInvoiceNumber(long storeId) {
        Long count = codeGeneratorRepository.findByStoreAndNumberStatus(storeId , NumberStatus.Order);

        if (count == null | 0 == count){
            CodeGenerator codeGenerator = new CodeGenerator();

            StoreInfo store = storeInfoRepository.findOne(storeId);

            String prefix = "I" + store.getName().substring(0 , 2).toUpperCase();

            codeGenerator.setStoreInfo(store);
            codeGenerator.setNumber(100001);
            codeGenerator.setNumberStatus(NumberStatus.Invoice);
            codeGenerator.setPrefix(prefix);

            codeGenerator = codeGeneratorRepository.save(codeGenerator);

            return codeGenerator.getPrefix() + "-" + codeGenerator.getNumber();

        } else {

            StoreInfo store = storeInfoRepository.findOne(storeId);

            long number = codeGeneratorRepository.findFirstByStoreInfoAndNumberStatusOrderByIdDesc(store, NumberStatus.Order).getNumber();

            CodeGenerator codeGenerator = new CodeGenerator();


            String prefix = "I" + store.getName().substring(0 , 2).toUpperCase();

            codeGenerator.setStoreInfo(store);
            codeGenerator.setNumber(number + 1);
            codeGenerator.setNumberStatus(NumberStatus.Invoice);
            codeGenerator.setPrefix(prefix);

            codeGenerator = codeGeneratorRepository.save(codeGenerator);

            return codeGenerator.getPrefix() + "-" + codeGenerator.getNumber();

        }
    }

    @Override
    public InvoiceInfoDTO save(long orderInfoId , long createdById) {

        InvoiceInfo invoiceInfo = invoiceInfoConverter.convertToEntity(orderInfoId , createdById);

        invoiceInfo.setInvoiceNo(generatInvoiceNumber(invoiceInfo.getStoreInfo().getId()));

        return invoiceInfoConverter.convertToDto(invoiceInfoRepository.save(invoiceInfo));
    }

    @Override
    public InvoiceInfoDTO show(long invoiceId, long storeId, Status status) {
        return invoiceInfoConverter.convertToDto(invoiceInfoRepository.findByIdAndStatusAndStoreInfo(invoiceId , status , storeId));
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return new PageRequest(page, size, new Sort(direction, properties));
    }

    @Override
    public List<InvoiceInfoDTO> list(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return invoiceInfoConverter.convertToDtoList(invoiceInfoRepository.findAllByStatusAndStoreInfo(status , storeId , pageable));
    }
}
