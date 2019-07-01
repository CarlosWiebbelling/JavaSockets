/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm_sockets;

import java.util.List;
/**
 *
 * @author carlos
 */
public interface SystemATM {
    abstract void verifyMoney (int code);
    void deposit (int code, float value);
    void withdow (int code, float value);
    void transfer (int origin, int destination, float value);
    void createAccount (int code, String name, float money, String password);
    void findAll (String password);
}
