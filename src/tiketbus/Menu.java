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
public class Menu {
        Views display = new Views();
        Scanner sc = new Scanner(System.in);
        
      public String bus(){
        BusHandle bus = new BusHandle();
        String pil;
        
        do{ 
            display.clrscr();
            display.header();
            display.menuBus();
            display.pilihan();

            pil = sc.nextLine();

            switch (pil) {
                case "1":
                    bus.add();                 
                    break;
                case "2":
                    bus.view();
                    System.out.println("Tekan [Enter] Untuk Kembali...");
                    sc.nextLine();
                    break;
                case "3":
                    bus.update();
                    break;
                case "4":
                    bus.search();
                    break;
                case "5":
                    bus.delete();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Pilihan tidak Sesuai!");
                    sc.nextLine();
                    break;
            }
        } while (!pil.equals("0"));
        
        return "-1";
    }
      
    public String trip(){
        
        TripHandle trip = new TripHandle();
        
        String pil;
        
        do{ 
            display.clrscr();
            display.header();
            display.menuTrip();
            display.pilihan();

            pil = sc.nextLine();

            switch (pil) {
                case "1":
                    trip.add();                 
                    break;
                case "2":
                    trip.view();
                    System.out.println("Tekan [Enter] Untuk Kembali...");
                    sc.nextLine();
                    break;
                case "3":
                    trip.update();
                    break;
                case "4":
                    trip.search();
                    break;
                case "5":
                    trip.delete();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Pilihan tidak Sesuai!");
                    sc.nextLine();
                    break;
            }
        } while (!pil.equals("0"));
        
        return "-1";
    }
    
    public String booking(){
        
        BookingHandle booking = new BookingHandle();
        
        String pil;
        
        do{ 
            display.clrscr();
            display.header();
            display.menuBooking();
            display.pilihan();

            pil = sc.nextLine();

            switch (pil) {
                case "1":
                    booking.add();                 
                    break;
                case "2":
                    booking.view();
                    System.out.println("Tekan [Enter] Untuk Kembali...");
                    sc.nextLine();
                    break;
                case "3":
                    booking.update();
                    break;
                case "4":
                    booking.search();
                    break;
                case "5":
                    booking.delete();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Pilihan tidak Sesuai!");
                    sc.nextLine();
                    break;
            }
        } while (!pil.equals("0"));
        
        return "-1";
    }
}
