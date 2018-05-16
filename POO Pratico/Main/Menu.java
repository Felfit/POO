package Main;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.File;
import java.util.HashMap;

import Contribuintes.Contribuintes;
import Contribuintes.Contribuinte;
import Contribuintes.ContribuinteIndividual;
import Contribuintes.ContribuinteEmpresarial;
import Fatura.Faturas;
/**
 * Write a description of class Menu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Menu
{   
    private static Faturas f;
    private static Contribuintes c;
    
    public static void saveContribuintes(String filepath) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileOutputStream fos = new FileOutputStream(new File(filepath));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(c);
    }
    
    private static int menuContrIndiv(ContribuinteIndividual contr){
        System.out.println("Em que lhe posso ajudar "+ contr.getNome());
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        menuString.add("Ver despesas");
        menuString.add("Ver montante de deduçao fiscal acumulado");
        menuString.add("Associar uma atividade economica a uma despesa");
        menuString.add("Corrigir classificaçao de uma atividade economica");
        menuString.add("Ver lista de facturas de uma empresa");
        menuString.add("Log out");
        
        return -1;
        
    }
    
    private static int menuContrEmpr(ContribuinteEmpresarial contr){
        System.out.println("Em que lhe posso ajudar "+ contr.getNome());
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Criar fatura");
        menuString.add("Ver faturas");
        menuString.add("Ver faturas por contribuinte num intervalo de tempo");
        menuString.add("Ver faturas por contribuinte");
        menuString.add("Ver total faturado num intervalo de tempo");
        menuString.add("Log out");
        
        return genericMenu(menuString, toRun);
        
    }
    
    
    
    private static int menuContr(Contribuinte contr){
        if(contr instanceof ContribuinteIndividual)
            return menuContrIndiv((ContribuinteIndividual) contr);
        else return menuContrEmpr((ContribuinteEmpresarial) contr);
    }
    
   
    private static Object getInfo(String Message, Class<?> cls){
        do{
            System.out.println(Message);
            Scanner s = new Scanner(System.in);
            if(cls == String.class){
                try{
                    String res = s.nextLine();
                    return res;
                } catch (InputMismatchException e){
                    System.out.println("Insert a text please");
                }
            }
            if(cls == Integer.class){
                try{
                    int res = s.nextInt();
                    return res;
                } catch (InputMismatchException e){
                    System.out.println("Insert a number only please");
                }
            }
        }while(true);
    }
    
    

    
    private static int registerMenuContrInd(){
        ContribuinteIndividual contr = new ContribuinteIndividual();
        contr.setNif((int) getInfo("Introduza o Nif", Integer.class));
        contr.setNome((String) getInfo("Introduza o Nome", String.class));
        contr.setEmail((String) getInfo("Introduza o Email", String.class));
        contr.setMorada((String) getInfo("Introduza a Morada", String.class));
        contr.setNumDependentesAgregado((int) getInfo("Introduza o numero do agregado familiar", Integer.class)); 
        contr.setCoefFiscal((int) getInfo("Introduza o coeficiente fiscal", Integer.class));
        contr.setPassword((String) getInfo("Introduza a sua palavra-passe", String.class));

        try{
            c.addContribuinte(contr);
            //System.out.println("contr: " + contr);
            //System.out.println("c: " + c);
        } catch (NullPointerException e){
            System.out.println("Couldn't register Contribuinte");
            //System.out.println("contr: " + contr);
            //System.out.println("c: " + c);
        }   
        return welcomeMenu();
    }
    
    private static int registerMenuContrEmpr(){
        ContribuinteEmpresarial contr = new ContribuinteEmpresarial();
        contr.setNif((int) getInfo("Introduza o Nif", Integer.class));
        contr.setNome((String) getInfo("Introduza o Nome", String.class));
        contr.setEmail((String) getInfo("Introduza o Email", String.class));
        contr.setMorada((String) getInfo("Introduza a Morada", String.class));
        contr.setFatorDeducao((int) getInfo("Introduza o fator de deduçao", Integer.class));
        contr.setPassword((String) getInfo("Introduza a sua palavra-passe", String.class));

        try{
            c.addContribuinte(contr);
        } catch (NullPointerException e){
            System.out.println("Couldn't register Contribuinte");
        }
        return welcomeMenu();
    }
    
    private static int registerMenu(){
        System.out.println("Registrando um novo Contribuinte");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Registrar contribuinte Individual");
        menuString.add("Registrar contribuinte Empresarial");
        menuString.add("Retroceder");
        toRun.add(Menu::registerMenuContrInd);
        toRun.add(Menu::registerMenuContrEmpr);
        toRun.add(Menu::welcomeMenu);
        
        return genericMenu(menuString, toRun);
    }
    
    private static int loginMenu(){
        int nif;
        String pass;
        Contribuinte contr = null;
        
        System.out.println("Introduza o seu NIF:");
        Scanner s1 = new Scanner(System.in);
        try{
            nif = s1.nextInt();
        } catch (InputMismatchException e){
            nif = -1;
        }
        s1.close();
        
        System.out.println("Introduza a sua palavra-pass:");
        Scanner s2 = new Scanner(System.in);
        try{
            pass = s2.nextLine();
        } catch (InputMismatchException e){
            pass = "";
        }
        s2.close();
        
        if(c != null)
            contr = c.login(nif, pass);
            
        if(contr == null){
            System.out.println("User not found/Wrong password");
            return welcomeMenu();
        }
        else{
            System.out.println("Authentication sucessful");
            return menuContr(contr);
        }
    }
    
    
    public static int welcomeMenu(){
        System.out.println("Welcome User");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Registrar novo Contribuinte");
        menuString.add("Log In");
        toRun.add(Menu::registerMenu);
        toRun.add(Menu::loginMenu);
        
        return genericMenu(menuString, toRun);
    }
        
    
    public static int genericMenu(ArrayList<String> menuString, ArrayList<Callable<Integer>> toRun){
        int op;
        int i = 1;
        for(String str: menuString){
            System.out.println( i + "-" + str);
            i++;
        }
        System.out.println("0-Sair");
        //System.out.println("c: "+ c);
        
        Scanner scan = new Scanner(System.in);
        do{
            try{
                op = scan.nextInt();
            } catch(InputMismatchException e){
                System.out.println("Input Errado");
                op = -1;
            }
            if(op > 0 && op <= toRun.size()){
                try{
                    op = toRun.get(op-1).call();
                } catch (Exception e){
                    System.out.println("Please try again " + e.getMessage());
                    return welcomeMenu();
                }
            }
        }while(op != 0);
        scan.close();
        return op;
    }
    
    public void run(Contribuintes cs){
        this.c = cs;
        //System.out.println("c: " + c);
        //System.out.println("cs " + cs);
        welcomeMenu();
    }
    
    public void run(){
        this.c = new Contribuintes();
        welcomeMenu();
    }
}
