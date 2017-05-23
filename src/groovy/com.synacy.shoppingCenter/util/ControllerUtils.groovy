package com.synacy.shoppingCenter.util

/**
 * Created by steven on 5/18/17.
 */
class ControllerUtils {

    boolean argumentNullChecker(Object[] args){
        for(def argument : args){
            if(!argument) return true
        }
        return false
    }
}
