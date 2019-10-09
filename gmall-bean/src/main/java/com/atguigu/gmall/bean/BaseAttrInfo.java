package com.atguigu.gmall.bean;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class BaseAttrInfo implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)//表示获得添加后的id
    private String id;
    @Column
    private String attrName;
    @Column
    private String catalog3Id;

    @Transient //表示非表中的字段
    private List<BaseAttrValue> attrValueList;
}
