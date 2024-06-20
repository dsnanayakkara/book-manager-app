package com.dushanz.bookmanager.mapper;

import com.dushanz.bookmanager.dto.BookDTO;
import com.dushanz.bookmanager.entity.BookInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookInfoMapper {
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "isbn", target = "isbn")
    BookInfo toEntity(BookDTO bookDTO);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "isbn", target = "isbn")
    BookDTO toDto(BookInfo bookInfo);
}

