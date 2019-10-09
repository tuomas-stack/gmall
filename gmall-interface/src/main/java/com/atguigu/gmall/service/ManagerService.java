package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface ManagerService {
    //获得所有一级分类
    List<BaseCatalog1> getCatalog1List();

    //根据一级分类id查询二级分类
    List<BaseCatalog2> getCatalog2List(String catalog1Id);

    //根据二级分类id查询所有三级分类
    List<BaseCatalog3> getCatalog3List(String catalog2Id);

    //根据三级分类id查询平台属性信息
    //http://localhost:8082/attrInfoList?catalog3Id=61
    List<BaseAttrInfo> getBaseAttrInfoList(String catalog3Id);

    //添加平台属性信息
    void saveBaseAttrInfo(BaseAttrInfo baseAttrInfo);

    //修改平台属性值，信息回显
    List<BaseAttrValue> getBaseAttrValueList(String attrId);

    BaseAttrInfo getBaseAttrInfoById(String attrId);

    //根据三级分类id查询销售属性
    //http://localhost:8082/spuList?catalog3Id=61
    List<SpuInfo> getSpuInfoList(String catalog3Id);
}
