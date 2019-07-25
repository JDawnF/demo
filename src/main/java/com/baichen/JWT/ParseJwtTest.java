package com.baichen.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @Program: ParseJwtTest
 * @Author: baichen
 * @Description: 解析Token
 */
public class ParseJwtTest {
    public static void main(String[] args) {
        String compactJws="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NjQwMjk5MTR9.0dVhvPkjyhtjt7SFDltCJhx9v3jHviZuNO8IlJgGJH8";
        Claims claims = Jwts.parser().setSigningKey("baichen").parseClaimsJws(compactJws).getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("IssuedAt:"+claims.getIssuedAt());
    }
}
