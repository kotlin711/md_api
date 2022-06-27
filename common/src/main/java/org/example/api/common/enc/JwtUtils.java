package org.example.api.common.enc;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public  final  class JwtUtils {



    private  static String SING ="ldjfklajsfjas";

    /**
     * 三小时过期
     * @param map
     * @return
     */
    public static String getToken(Map<String,String> map)
    {
        final Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,10);
        final JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        return  builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SING));
    }

    public  static void    verify(String token)
    {
            JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    public  static DecodedJWT getTokenInfo(String token)
    {
        return  JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }




}
