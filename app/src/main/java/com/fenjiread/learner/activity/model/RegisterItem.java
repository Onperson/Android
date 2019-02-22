package com.fenjiread.learner.activity.model;

/**
 * 签到的数据类
 * @author guotianhui
 */
public class RegisterItem {

    private String registerDay;
    private boolean isRegister;
    private boolean isShowTwoDot;

    public String getRegisterDay() {
        return registerDay;
    }

    public void setRegisterDay(String registerDay) {
        this.registerDay = registerDay;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public boolean isShowTwoDot() {
        return isShowTwoDot;
    }

    public void setShowTwoDot(boolean showTwoDot) {
        isShowTwoDot = showTwoDot;
    }
}
