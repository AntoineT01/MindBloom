package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps Account and AccountDto
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AccountMapper extends GenericMapper<Account, AccountDto> {
}
