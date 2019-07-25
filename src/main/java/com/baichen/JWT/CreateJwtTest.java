package com.baichen.JWT;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Program: CreateJwtTest
 * @Author: baichen
 * @Description: JWT测试类，用于生成Token
 */
public class CreateJwtTest {
    public static void main(String[] args) {
//  setIssuedAt用于设置签发时间;signWith用于设置签名秘钥
//  载荷中加入了时间，所以每次运行的结果都是不同的
        JwtBuilder builder= Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"baichen");
        System.out.println( builder.compact() );
    }
}
