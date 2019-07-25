package com.baichen.JWT;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Program: CreateJwtTest2
 * @Author: baichen
 * @Description: 创建带有失效时间的token
 */
public class CreateJwtTest2 {
    public static void main(String[] args) {
        long now = System.currentTimeMillis();//当前时间
        long exp = now + 1000 * 60;//过期时间为1分钟
        JwtBuilder builder = Jwts.builder()
                .setId("666")
                .setSubject("大白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "baichen")
                .setExpiration(new Date(exp));  // setExpiration 方法用于设置过期时间
        System.out.println(builder.compact());
    }
}
