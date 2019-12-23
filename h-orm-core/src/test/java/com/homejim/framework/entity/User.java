package com.homejim.framework.entity;

import com.homejim.framework.annotation.Column;
import com.homejim.framework.annotation.Id;
import com.homejim.framework.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("user")
public class User {

    @Id
    @Column("id")
    private String id;

    @Column("user_name")
    private String name;

    @Column("create_date")
    private Date createDate;
}
