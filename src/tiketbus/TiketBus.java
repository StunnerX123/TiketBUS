/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiketbus;
import java.util.Scanner;
/**
 *
 * @author whiz
 */
public class TiketBus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Views display = new Views();
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        Database db = new Database();
        
        String pil;
            do{ 
                display.clrscr();
                display.header();
                if(db.testKoneksi()){
                    display.menu();
                    display.pilihan();

                    pil = sc.nextLine();

                    switch (pil) {
                        case "1":
                            pil =  menu.bus();                
                            break;
                        case "2":
                            pil =  menu.trip();
                            break;
                        case "3":
                            pil = menu.booking();
                            break;
                        case "0":
                            display.bye();
                            sc.nextLine();
                            break;
                        default:
                            System.out.println("Pilihan tidak Sesuai!");
                            sc.nextLine();
                            break;
                    }
                } else {
                    System.out.print(" Koneksi ke Database Gagal ! Tekan [Enter] Untuk Keluar...");
                    pil="0";
                    sc.nextLine();
                }
            } while (!pil.equals("0"));
    }       
}
