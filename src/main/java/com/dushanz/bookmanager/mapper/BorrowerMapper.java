package com.dushanz.bookmanager.mapper;

import com.dushanz.bookmanager.dto.BorrowerDTO;
import com.dushanz.bookmanager.entity.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapstruct for mapping a Borrower entity instance to BorrowerDTO instance and vice versa.
 */
@Mapper
public interface BorrowerMapper {
    BorrowerMapper INSTANCE = Mappers.getMapper(BorrowerMapper.class);

    BorrowerDTO borrowerToBorrowerDTO(Borrower borrower);

    Borrower borrowerDTOToBorrower(BorrowerDTO borrowerDTO);
}

