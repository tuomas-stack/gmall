package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.service.ManagerService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ManageController {

    @Reference
    private ManagerService managerService;


    //http://localhost:8082/getCatalog1

    /**
     * 查询所有一级分类
     * @return
     */
    @RequestMapping("getCatalog1")
    public List<BaseCatalog1> getCatalog1(){
        return managerService.getCatalog1List();
    }

    /**
     * 根据一级分类id查询所有二级分类
     * @param catalog1Id
     * @return
     */
    @RequestMapping("getCatalog2")
    public List<BaseCatalog2> getCatalog2(String catalog1Id){
        return managerService.getCatalog2List(catalog1Id);
    }

    /**
     * 根据二级分类id查询所有三级分类
     * @param catalog2Id
     * @return
     */
    @RequestMapping("getCatalog3")
    public List<BaseCatalog3> getCatalog3(String catalog2Id){
        return managerService.getCatalog3List(catalog2Id);
    }

    /**
     * 根据三级分类id查询所有平台信息
     * @param catalog3Id
     * @return
     */
    //http://localhost:8082/attrInfoList?catalog3Id=61
    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> attrInfoList(String catalog3Id){
        return managerService.getBaseAttrInfoList(catalog3Id);
    }

    //http://localhost:8082/saveAttrInfo

    /**
     * 添加平台属性
     * 前台传的值：catalog3Id,attrName,valueName(多个),采用vo封装
     * 直接把实体类当做vo，在baseAttrInfo实体类中添加List<BaseAttrValue>属性
     */
    @RequestMapping("saveAttrInfo")
    public String saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        managerService.saveBaseAttrInfo(baseAttrInfo);
        return "ok";
    }
    //http://localhost:8082/getAttrValueList?attrId=70

    /**
     * 修改平台属性值，信息回显，方式一，直接通过attrId查baseattrvalue表得到数据
     * @param attrId
     * @return
     */
   /* @RequestMapping("getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(String attrId){
        return managerService.getBaseAttrValueList(attrId);
    }*/

    /**
     * 作修改平台属性时的信息回显，方式二
     * 直接查询出BaseAttrInfo,然后再获得List<BaseAttrValue>
     * @param attrId
     * @return
     */
    @RequestMapping("getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(String attrId){
        BaseAttrInfo baseAttrInfo = managerService.getBaseAttrInfoById(attrId);
        return baseAttrInfo.getAttrValueList();
    }
    //http://localhost:8082/spuList?catalog3Id=61

    @RequestMapping("spuList")
    public List<SpuInfo> spuList(String catalog3Id){
        return managerService.getSpuInfoList(catalog3Id);
    }
}
