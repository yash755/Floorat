package com.floorat;


public class Util {
    static int flag;
    public int getFlag(){
        return flag;
    }

    public void setFlag(int x)
    {
        flag = x;
        System.out.println("maine flag update kr diya hai "+flag);
    }
}
