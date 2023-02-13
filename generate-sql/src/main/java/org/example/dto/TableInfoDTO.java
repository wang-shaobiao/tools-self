package org.example.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @description: 表相关信息
 * @author: wshbiao
 * @create: 2023-02-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TableInfoDTO implements Serializable {
    private static final long serialVersionUID = -1621784556530121934L;
    private String tableName;
    private String nameColumn;
    private String residColumn;
    private String condition;
    private String note;

}
