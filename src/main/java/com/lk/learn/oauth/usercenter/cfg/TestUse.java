package com.lk.learn.oauth.usercenter.cfg;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class TestUse {
    public static void main(String[] args) {

        String redirect = "https://api.weibo.com/oauth2/access_token?client_id=973886123&client_secret=3253f16e8324a73f6ede08c7405c0bad&grant_type=authorization_code&redirect_uri=http%3A%2F%2F192.168.1.103%3A8082%2FsecuredPage&code=%s";
        String  code = "e40c975139567dad4c700aa6372fa837";
        String accessTokenUrl = String.format(redirect, code);
        System.out.println("accessTokenUrl = " + accessTokenUrl);


        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String access_token = "ghoeOftatPvQwgj24AEwgOHY4dth7qs7V3M1Vxo";
        String userId = "9073658";
        String subject1 = access_token + "|" + userId;
        String jws_token = Jwts.builder().setSubject(subject1).signWith(key).compact();
        System.out.println("jws_token = " + jws_token);

        jws_token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaG9lT2Z0YXRQdlF3Z2oyNEFFd2dPSFk0ZHRoN3FzN1YzTTFWeG98OTA3MzY1OCJ9.Ydvv0W0OJjQwoabinb8irPt97a4Tc3Fe6c8sjx0Liso";
        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws_token).getBody().getSubject();
        System.out.println("subject = " + subject);


        String nb = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaG9fZU9mdGF0UHZRd2dqMjRBRXdnT0hZNGR0aDdxczdWM00xVnhvXzkwNzM2NTgifQ.wgTqCIxcaMrjo233u72oJ-sB0GPhI3Q0qbIunIkRif4";
        String jws = Jwts.builder().setSubject(subject1).signWith(key).compact();
        System.out.println(jws);

//          subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(nb).getBody().getSubject();
        System.out.println("subject = " + subject);

        System.out.println(
                subject.equals(nb)
        ); //true


    }
}
