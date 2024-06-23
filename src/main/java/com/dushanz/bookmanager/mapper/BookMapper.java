package com.dushanz.bookmanager.mapper;

import com.dushanz.bookmanager.dto.BookDTO;
import com.dushanz.bookmanager.entity.Book;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapstruct for mapping a Book entity instance to BookDTO instance and vice versa.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "bookInfo.title", target = "title")
    @Mapping(source = "bookInfo.author", target = "author")
    @Mapping(source = "bookInfo.isbn", target = "isbn")
    BookDTO entityToDto(Book book);

    @InheritInverseConfiguration
    Book dtoToEntity(BookDTO bookDTO);
}

