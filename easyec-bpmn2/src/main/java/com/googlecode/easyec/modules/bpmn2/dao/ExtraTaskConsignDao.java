package com.googlecode.easyec.modules.bpmn2.dao;

import com.googlecode.easyec.modules.bpmn2.domain.ExtraTaskConsign;
import com.googlecode.easyec.spirit.dao.paging.Page;

import java.util.List;
import java.util.Map;

public interface ExtraTaskConsignDao {

    int deleteByPrimaryKey(Long uidPk);

    int insert(ExtraTaskConsign record);

    ExtraTaskConsign selectByPrimaryKey(Long uidPk);

    int updateByPrimaryKey(ExtraTaskConsign record);

    List<ExtraTaskConsign> find(Map<String, Object> params);

    Page find(Page page);

    long countTaskConsigns(Map<String, Object> params);
}