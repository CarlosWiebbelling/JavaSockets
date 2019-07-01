package atm_sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Socket client;
    private static int port = 3000;
    private static PrintStream outputData;
    private static Scanner inputData;
    private static Scanner scan;
    
    public static void main(String args[]) {
        try {
            client = new Socket("127.0.0.1", port);
            
            System.out.println("Connection Established");
            outputData = new PrintStream(client.getOutputStream());
            inputData = new Scanner(client.getInputStream());
            scan = new Scanner(System.in);
            
            options();
            
        } catch (IOException e) {
            System.out.println("err: " + e);
        }
    }
    
    public static void options(){
        try{
            scan = new Scanner(System.in);  
            int code;

            String menu = "\nAutomated Teller Machine\n\n" + 
                    "1) Verificar Saldo\n2) Realizar Depósito\n" + 
                    "3) Realizar Saque\n" + 
                    "4) Realizar Transferência entre contas\n" + 
                    "5) Criar conta (admin)\n" + 
                    "6) Listar todos os clientes (admin)\n" + 
                    "7) Finalizar";

            do {
                System.out.println(menu);
                code = scan.nextInt();
                scan.nextLine();
                
                switch(code) {
                    case 1:
                        System.out.println("\n1) - Verificar saldo");
                        verifyMoney();
                        break;
                    case 2:
                        System.out.println("\n2) - Realizar depósito");
                        deposit();
                        break;
                    case 3:
                        System.out.println("\n3) - Realizar Saque");
                        withdow();
                        break;
                    case 4:
                        System.out.println("\n4) - Realizar Transferência entre contas");
                        transfer();
                        break;
                    case 5:
                        System.out.println("\n5) Criar conta (admin)");
                        createAccount();
                        break;
                    case 6:
                        System.out.println("\n6) - Listar todos os clientes (admin)");
                        findAll();
                        break; 
                    case 7:
                        System.out.println("\n7) - Finalizar");
                        outputData.println("7");
                        break; 
                    default: 
                        System.out.println("\nInsira uma opção válida!");
                        break;
                }

            } while (code != 7);
        } catch(Exception e) {
            System.out.println("err: " + e);
        }
    }

    public static void verifyMoney() {
        System.out.println("Que conta desejas verificar o saldo? ");
        int codeAccount = scan.nextInt();
        
        if(verifyNegative(codeAccount))
            return;
        
        outputData.println("1");
        outputData.println(codeAccount);
        System.out.println(inputData.nextLine());
    }

    public static void deposit() {
        System.out.println("Para que conta desejas depositar? ");
        int code = scan.nextInt();
        
        if(verifyNegative(code))
            return;
        
        System.out.println("Qual o valor do depósito? ");
        float value = scan.nextFloat();
        
        if(verifyNegative(value))
            return;
        
        outputData.println("2");
        outputData.println(code + " " + value);
        System.out.println(inputData.nextLine());
    }

    public static void withdow() {
        System.out.println("De que conta desejas realizar saque? ");
        int code = scan.nextInt();
        
        if(verifyNegative(code))
            return;
        
        System.out.println("Qual o valor do saque? ");
        float value = scan.nextFloat();
        
        if(verifyNegative(value))
            return;
        
        outputData.println("3");
        outputData.println(code + " " + value);
        System.out.println(inputData.nextLine());
    }

    public static void transfer() {
        System.out.println("Conta de origem: ");
        int origin = scan.nextInt();
        
        if(verifyNegative(origin))
            return;
        
        scan.nextLine();
        
        System.out.println("Conta de destino: ");
        int destination = scan.nextInt();
        
        if(verifyNegative(destination))
            return;
        
        scan.nextLine();
        
        if(origin == destination) {
            System.out.println("Transação inválida");
        }
        
        System.out.println("Valor da transação: ");
        float value = scan.nextFloat();
        
        if(verifyNegative(value))
            return;
                
        outputData.println("4");
        outputData.println(origin + " " + destination + " " + value + " ");
        System.out.println(inputData.nextLine());
    }

    public static void createAccount() {
        System.out.println("Digite o código da conta: ");
        int code = scan.nextInt();
        
        if(verifyNegative(code))
            return;
        
        scan.nextLine();
        
        System.out.println("Nome da conta: ");
        String name = scan.nextLine();
        
        System.out.println("Saldo inicial: ");
        float value = scan.nextFloat();
        
        if(verifyNegative(value))
            return;
        
        scan.nextLine();
        
        outputData.println("5");
        
        System.out.println("Senha: ");
        String password = scan.nextLine();
        
        outputData.println(password);
        
        String result = inputData.nextLine();
        System.out.println(result);
        
        if(result.equals("Senha não confere"))
            return;

        outputData.println(code + " " + name + " " + value);
 
        System.out.println(inputData.nextLine());
    }

    public static void findAll() {
        outputData.println("6");
        
        System.out.println("Senha: ");
        String password = scan.nextLine();
        outputData.println(password);
        
        String result = inputData.nextLine();
        System.out.println(result);
        
        if(result.equals("Senha não confere"))
            return;
        
        String breakPoint;
        while(inputData.hasNext()) {
            breakPoint = inputData.nextLine();
            if(breakPoint.equals("end"))
                break;
            else
                System.out.println(breakPoint);
        }
    }
    
    public static boolean verifyNegative(int number) {
        if(number < 0) {
            System.out.println("Valor negativo inválido!");
            return true;
        }
        return false;
    }

    public static boolean verifyNegative(float number) {
        if(number < 0) {
            System.out.println("Valor negativo inválido!");
            return true;
        }
        return false;
    }
}
