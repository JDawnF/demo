package com.baichen.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Program: ParseJwtTest
 * @Author: baichen
 * @Description: 解析Token
 */
public class ParseJwtTest3 {
    public static void main(String[] args) {
        String compactJws = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlpKfnmb0iLCJpYXQiOjE1NjQwMzA3MzEsImV4cCI6MTU2NDAzMDc5MCwicm9sZXMiOiJhZG1pbiIsImxvZ28iOiJsb2dvLnBuZyJ9.10vSq7_yipfE9VHWMlbSYgVf37tdtxyygfscoX0ckDg";
        Claims claims = Jwts.parser().setSigningKey("baichen").parseClaimsJws(compactJws).getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("IssuedAt:" + claims.getIssuedAt());
        System.out.println("roles:"+claims.get("roles"));
        System.out.println("logo:"+claims.get("logo"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
        System.out.println("签发时间:" + sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间:" + sdf.format(claims.getExpiration()));
        System.out.println("当前时间:" + sdf.format(new Date()));
    }
}
