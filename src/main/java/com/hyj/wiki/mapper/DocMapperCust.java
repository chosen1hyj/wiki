package com.hyj.wiki.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @author: Chosen1
 * @date: 2021/12/6 16:42
 */
public interface DocMapperCust {
    void increaseViewCount(@Param("id") Long id);

    void increaseVoteCount(@Param("id") Long id);
    void updateEbookInfo();

}
