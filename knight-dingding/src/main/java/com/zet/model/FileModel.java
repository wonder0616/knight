package com.zet.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Blob;

/**
 * @Description
 * @Author 
 * @Date  2023/8/7 16:36
 * @Version 1.0
**/
@Data
public class FileModel implements Serializable {

    private static final long serialVersionUID = 7794283487543532449L;

    private String name;

    private Blob file;

}
