package com.homejim.framework.entity;

import com.homejim.framework.annotation.Column;
import com.homejim.framework.annotation.Id;
import com.homejim.framework.annotation.Table;
import lombok.Data;

@Data
@Table("user")
public class User {

    @Id
    @Column("id")
    private String id;

    @Column("user_name")
    private String name;
}
