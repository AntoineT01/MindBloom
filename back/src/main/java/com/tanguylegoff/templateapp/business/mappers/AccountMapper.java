package com.tanguylegoff.templateapp.business.mappers;

import com.tanguylegoff.templateapp.api.models.AccountDto;
import com.tanguylegoff.templateapp.config.mappers.GenericMapper;
import com.tanguylegoff.templateapp.dao.db.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps Account and AccountDto
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AccountMapper extends GenericMapper<Account, AccountDto> {
}
