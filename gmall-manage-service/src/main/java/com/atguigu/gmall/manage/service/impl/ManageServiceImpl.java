package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.mapper.*;
import com.atguigu.gmall.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ManageServiceImpl implements ManagerService {

    @Autowired
    private BaseCatalog1Mapper catalog1Mapper;
    @Autowired
    private BaseCatalog2Mapper catalog2Mapper;
    @Autowired
    private BaseCatalog3Mapper catalog3Mapper;
    @Autowired
    private BaseAttrInfoMapper attrInfoMapper;
    @Autowired
    private BaseAttrValueMapper attrValueMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Override
    public List<BaseCatalog1> getCatalog1List() {
        return catalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2List(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return catalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3List(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        return catalog3Mapper.select(baseCatalog3);
    }

    //根据三级分类id查询平台属性信息
    @Override
    public List<BaseAttrInfo> getBaseAttrInfoList(String catalog3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        return attrInfoMapper.select(baseAttrInfo);
    }

    /**
     * 添加ADN修改平台属性与平台属性值
     * @param baseAttrInfo
     */
    @Override
    @Transactional
    public void saveBaseAttrInfo(BaseAttrInfo baseAttrInfo) {
        //1.判断是添加还是修改
        if(baseAttrInfo.getId()!=null){
            //修改
            attrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else {
            //保存平台属性..添加
            attrInfoMapper.insertSelective(baseAttrInfo);
        }

        //保存平台属性值
        //在添加之前先删除所有平台属性值
        BaseAttrValue baseAttrValueDel = new BaseAttrValue();
        baseAttrValueDel.setAttrId(baseAttrInfo.getId());
        attrValueMapper.delete(baseAttrValueDel);
        //再添加
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if(attrValueList !=null && attrValueList.size()>0){
            for (BaseAttrValue baseAttrValue : attrValueList) {
                //拿到添加平台属性后的id值，需要在实体类的id属性上添加注解
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                attrValueMapper.insertSelective(baseAttrValue);
            }
        }
    }

    /**
     * 回显方式一
     * @param attrId
     * @return
     */
    @Override
    public List<BaseAttrValue> getBaseAttrValueList(String attrId) {
        /*BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        return attrValueMapper.select(baseAttrValue);*/
        //两种方式都可以
        Example example = new Example(BaseAttrValue.class);
        example.createCriteria().andEqualTo("attrId",attrId);
        return attrValueMapper.selectByExample(example);
    }

    /**
     * 回显方式二
     * @param attrId
     * @return
     */
    @Override
    public BaseAttrInfo getBaseAttrInfoById(String attrId) {
        BaseAttrInfo baseAttrInfo = attrInfoMapper.selectByPrimaryKey(attrId);
        baseAttrInfo.setAttrValueList(getBaseAttrValueList(attrId));
        return baseAttrInfo;
    }

    /**
     * 根据三级分类id查询spuInfo
     * @param catalog3Id
     * @return
     */
    @Override
    public List<SpuInfo> getSpuInfoList(String catalog3Id) {
        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        return spuInfoMapper.select(spuInfo);
    }
}
