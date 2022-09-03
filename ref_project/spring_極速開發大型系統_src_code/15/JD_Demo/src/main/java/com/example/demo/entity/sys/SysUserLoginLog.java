﻿package com.example.demo.entity.sys;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author longzhonghua
 * @data 2018/11/04 22:30
 */
@Entity
@Data
public class SysUserLoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//由資料庫控制,auto是程式統一控制
    private long id;
    private long logintime;
    private String loginip;
    private String username;
    private int states;
}
