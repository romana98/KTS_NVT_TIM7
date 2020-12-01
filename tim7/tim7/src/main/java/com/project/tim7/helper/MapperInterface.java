package com.project.tim7.helper;

public interface MapperInterface<T,U> {

        T toEntity(U dto);

        U toDto(T entity);
}
