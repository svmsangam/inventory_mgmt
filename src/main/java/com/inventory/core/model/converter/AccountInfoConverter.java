package com.inventory.core.model.converter;

import com.inventory.core.model.dto.AccountInfoDTO;
import com.inventory.core.model.entity.AccountInfo;
import com.inventory.core.util.ConvertUtil;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import com.inventory.core.util.ParseUtls;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 8/12/17.
 */

@Service
public class AccountInfoConverter implements IConvertable<AccountInfo, AccountInfoDTO>, IListConvertable<AccountInfo, AccountInfoDTO> {

    @Override
    public AccountInfo convertToEntity(AccountInfoDTO dto) {
        return copyConvertToEntity(dto, new AccountInfo());
    }

    @Override
    public AccountInfoDTO convertToDto(AccountInfo entity) {

        if (entity == null) {
            return null;
        }

        AccountInfoDTO dto = new AccountInfoDTO();

        dto.setAccountId(entity.getId());
        dto.setAcountNumber(entity.getAcountNumber());
        dto.setAssociateId(entity.getAssociateId());
        dto.setAssociateType(entity.getAssociateType());
        dto.setVersion(entity.getVersion());
        dto.setDebitAmount(entity.getDebitAmount());
        dto.setCreditAmount(entity.getCreditAmount());
        dto.setFormattedDebitAmount(ParseUtls.formatter(entity.getDebitAmount()));
        dto.setFormattedCreditAmount(ParseUtls.formatter(entity.getCreditAmount()));

        return dto;
    }

    @Override
    public AccountInfo copyConvertToEntity(AccountInfoDTO dto, AccountInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setAcountNumber(dto.getAcountNumber());
        entity.setAssociateId(dto.getAssociateId());
        entity.setAssociateType(dto.getAssociateType());

        return entity;
    }

    @Override
    public List<AccountInfoDTO> convertToDtoList(List<AccountInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<AccountInfo> convertToEntityList(List<AccountInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
