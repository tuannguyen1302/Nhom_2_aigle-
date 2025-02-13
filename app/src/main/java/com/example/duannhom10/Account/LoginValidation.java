package com.example.duannhom10.Account;
import com.example.duannhom10.model.user;

public class LoginValidation {
    public boolean correct;

    public user curUser;
    public boolean isCorrect() {
        return correct;
    }
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
    public user getCurUser() {
        return curUser;
    }
    public void setCurUser(user curUser) {
        this.curUser = curUser;
    }
    public LoginValidation(user curUser,boolean correct) {
        this.curUser = curUser;
        this.correct = correct;
    }
    public LoginValidation(boolean correct) {
        this.correct = correct;
    }


}
