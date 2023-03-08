package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: billitem_base表中的cstyle专用的数据传输类
 * @author: wshbiao
 * @create: 2023-03-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CstyleHandleDTO implements Serializable {
    private static final long serialVersionUID = -419010494398179029L;
    private String tenant_id;
    private String cSubId;
    private String cStyle;
    private String cName;
    private String cFieldName;
    private String cDataSourceName;
    private String cBillNo;

}
