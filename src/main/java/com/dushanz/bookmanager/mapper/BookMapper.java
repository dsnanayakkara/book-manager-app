package com.dushanz.bookmanager.mapper;

import com.dushanz.bookmanager.dto.BookDTO;
import com.dushanz.bookmanager.entity.Book;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "bookInfo.title", target = "title")
    @Mapping(source = "bookInfo.author", target = "author")
    @Mapping(source = "bookInfo.isbn", target = "isbn")
    BookDTO entityToDto(Book book);

    @InheritInverseConfiguration
    Book dtoToEntity(BookDTO bookDTO);
}

