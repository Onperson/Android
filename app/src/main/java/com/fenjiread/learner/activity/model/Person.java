package com.fenjiread.learner.activity.model;

import com.fenjiread.learner.activity.utils.PinYinUtils;

/**
 * 作用： 姓名 ：阿三
 *       拼音：ASAN
 */

public class Person {

    private String name;

    private String pinyin;

    public Person(String name) {  //生成带name参数的构造方法
        this.name = name;
        this.pinyin = PinYinUtils.getPinYin(name);
    }

    //Getter和Setter方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPinyin() {
        return pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    //toString方法 （Alt+Insert）
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
