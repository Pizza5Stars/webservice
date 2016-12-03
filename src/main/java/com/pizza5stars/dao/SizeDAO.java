package com.pizza5stars.dao;

import com.pizza5stars.dao.mappers.SizeMapper;
import com.pizza5stars.representations.Size;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface SizeDAO {
    @Mapper(SizeMapper.class)
    @SqlQuery("select * from size")
    List<Size> getSizes();
}

