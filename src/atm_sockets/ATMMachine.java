/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm_sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class ATMMachine {

    private static List<Account> accounts;
    private static String password;
    private static Socket client;
    private static Scanner inputData;
    private static PrintStream outputData;
    private static ServerSocket server;
    
    public ATMMachine(String password, Socket client, ServerSocket server) {
        this.accounts = new ArrayList<>();
        this.password = password;
        this.client = client;
        this.server = server;
        try {
            inputData = new Scanner(client.getInputStream());
            outputData = new PrintStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ATMMachine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void options() {
        int code;
        
        do {
            // receive option
            code = Integer.valueOf(inputData.nextLine());

            switch(code) {
                case 1:
                    verifyMoney();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdow();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    createAccount();
                    break;
                case 6:
                    findAll();
                    break;
                case 7:
                    try {
                        server.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ATMMachine.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        break;
                    }
                default: 
                    break;
            }
        } while(code != 7);
    }
    
    public void verifyMoney() {
        String code = inputData.nextLine();
        Account acc = findOne(Integer.valueOf(code));
        if(acc == null) {
            outputData.println("Conta não encontrada!");
            return;
        }
        outputData.println("Conta " + acc.getCode() + " Saldo: " + acc.getMoney());
    }

    public void deposit() {
        String info = inputData.nextLine();
        String[] splited = info.split("\\s+");
        
        Account acc = findOne(Integer.valueOf(splited[0]));
        
        if(acc == null) {
            outputData.println("Conta não encontrada");
            return;
        }
        acc.setDeposit(Float.valueOf(splited[1]));
        
        outputData.println("Depósito realizado com sucesso!");
    }

    public void withdow() {
        String info = inputData.nextLine();
        String[] splited = info.split("\\s+");
        
        Account acc = findOne(Integer.valueOf(splited[0]));
        
        if(acc == null) {
            outputData.println("Conta não encontrada");
            return;
            
        } else if (acc.getMoney() < Float.valueOf(splited[1])) {
            outputData.println("Saldo insuficiente");
            return;
            
        }
        acc.setWithdraw(Float.valueOf(splited[1]));
        
        outputData.println("Saque realizado com sucesso!");
    }

    public void transfer() {  
        String info = inputData.nextLine();
        String[] splited = info.split("\\s+");
        
        Account accOrigin = findOne(Integer.valueOf(splited[0]));
        
        if(accOrigin == null) {
            outputData.println("Conta de origem inexistente!");
            return;
            
        } else if(accOrigin.getMoney() < Float.valueOf(splited[2])) {
            outputData.println("Saldo insuficiente!");
            return;
        }
        
        Account accDestination = findOne(Integer.valueOf(splited[1]));
        
        if(accDestination == null) {
            outputData.println("Conta de destino inexistente!");
            return;
        }
        
        accOrigin.setWithdraw(Float.valueOf(splited[2]));
        accDestination.setDeposit(Float.valueOf(splited[2]));
        
        outputData.println("Transferência realizada com sucesso!");
    }

    public void createAccount() {
        String passwordInput = inputData.nextLine();
        
        if(!passwordInput.equals(password)) {
            outputData.println("Senha não confere");
            return;
        }
        
        outputData.println("Autenticado com sucesso!");
        
        String info = inputData.nextLine();

        String[] splited = info.split("\\s+");
        
        Account acc = findOne(Integer.valueOf(splited[0]));
        
        if(acc == null) {
            accounts.add(new Account(
                    Integer.valueOf(splited[0]), 
                    splited[1], 
                    Float.valueOf(splited[2])
                )
            );
            outputData.println("Conta criada com sucesso!");
        } else {
            outputData.println("Código da conta em uso!");
        }
        
    }

    public void findAll() {
        String passwordInput = inputData.nextLine();
        
        if(!passwordInput.equals(password)) {
            outputData.println("Senha não confere");
            return;
        }
        
        if(accounts.isEmpty())
            outputData.println("Nenhuma conta existente!");
        else 
            for(Account acc : accounts) {
                outputData.println(
                        "Conta: " + acc.getCode() +
                        " Name: " + acc.getName() +
                        " Saldo: R$" + acc.getMoney()
                );
            }
        outputData.println("end");
    }
    
    public Account findOne(int code) {
        for(Account acc : accounts)
            if(acc.getCode() == code)
                return acc;
        return null;
    }
}
