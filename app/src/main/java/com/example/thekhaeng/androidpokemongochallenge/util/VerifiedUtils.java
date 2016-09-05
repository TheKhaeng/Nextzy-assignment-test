package com.example.thekhaeng.androidpokemongochallenge.util;

import android.content.Context;
import android.os.Bundle;

import com.example.thekhaeng.androidpokemongochallenge.R;


/**
 * Created by Nonthawit on 6/28/2016.
 */

public class VerifiedUtils{

    private final Context mContext;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String specialCharPattern = ".*[^a-zA-Z0-9].*";

    private String ERROR_NOT_EMAIL;
    private String ERROR_NAME_EMPTY;

    public VerifiedUtils( Context context ){
        mContext = context;
        ERROR_NOT_EMAIL = mContext.getString( R.string.error_not_email );
        ERROR_NAME_EMPTY = mContext.getString( R.string.error_name_empty );
    }

    public String verifiedSignIn( String email, String pass ){
        if( !isStringEmpty( email ) && !isStringEmpty( pass ) ){
            if( isEmail( email ) ){
                return null;
            }else{
                return ERROR_NOT_EMAIL;
            }
        }
        return ERROR_NAME_EMPTY;
    }


    /**********************/
    /** Private function **/
    /**********************/
    //<editor-fold desc="Private function folding">
    private boolean isHaveSpecialChar( String id ){
        if( id.matches( specialCharPattern ) ){
            return true;
        }
        return false;
    }

    public boolean isStringEmpty( String str ){
        return str == null || str.equals( "" ) || str.length() == 0;
    }

    private boolean isEmail( String email ){
        if( email.matches( emailPattern ) && !email.equals( "" ) && email.length() > 0 ){
            return true;
        }
        return false;
    }

    private boolean isMinString( String str, int atLeast ){
        if( str.length() >= atLeast ){
            return false;
        }
        return true;
    }

    private boolean isSamePassword( String pass1, String pass2 ){
        if( pass1.equals( pass2 ) ){
            return true;
        }
        return false;
    }

    //</editor-fold>

}
