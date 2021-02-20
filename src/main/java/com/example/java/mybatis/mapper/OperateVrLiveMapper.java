package com.example.java.mybatis.mapper;

import com.example.java.mybatis.entites.OperateVrLive;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author liqinchun
 * @version 1.0.0
 */
@Mapper
public interface OperateVrLiveMapper {

	public int insert(OperateVrLive operateVrLive);

	public int update(OperateVrLive operateVrLive);

	public OperateVrLive get(Long id);

	public List<OperateVrLive> getByIds(List<Long> list);

}