/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm_sockets;

/**
 *
 * @author carlos
 */
public class Account {
    
    public int code;
    public String name;
    public float money;

    public Account(int code, String name, float money) {
        this.code = code;
        this.name = name;
        this.money = money;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
    
    public void setWithdraw(float money) {
        this.money -= money;
    }
    
    public void setDeposit(float money) {
        this.money += money;
    }
}
