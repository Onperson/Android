package com.fenjiread.learner.activity.model;

/**
 * 词云养成的词能数据对象
 * @author guotianhui
 */
public class WordEnergy {

    public WordEnergy(String name, int energy){
        this.wordName = name;
        this.wordsEnergy =energy;
    }
    private String wordName;
    private int wordsEnergy;

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public int getWordsEnergy() {
        return wordsEnergy;
    }

    public void setWordsEnergy(int wordsEnergy) {
        this.wordsEnergy = wordsEnergy;
    }
}