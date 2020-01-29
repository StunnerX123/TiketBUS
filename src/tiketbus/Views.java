/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiketbus;
import java.io.IOException;
/**
 *
 * @author whiz
 */
public class Views {
    
    FormatData format = new FormatData();
    
    public void header(){
        System.out.println("*****************************************************************************| Tanggal Hari Ini : "+format.getDate(false)+" |*");
        System.out.println("*    ____  __  _______    ____  ___    __  _____    __    ___       __ __ _______   ___________    _   _____  *");
        System.out.println("*   / __ )/ / / / ___/   / __ \\/   |  / / / /   |  / /   /   |     / //_// ____/ | / / ____/   |  / | / /   | *");
        System.out.println("*  / __  / / / /\\__ \\   / /_/ / /| | / /_/ / /| | / /   / /| |    / ,<  / __/ /  |/ / /   / /| | /  |/ / /| | *");
        System.out.println("* / /_/ / /_/ /___/ /  / ____/ ___ |/ __  / ___ |/ /___/ ___ |   / /| |/ /___/ /|  / /___/ ___ |/ /|  / ___ | *");
        System.out.println("*/_____/\\____//____/  /_/   /_/  |_/_/ /_/_/  |_/_____/_/  |_|  /_/ |_/_____/_/ |_/\\____/_/  |_/_/ |_/_/  |_| *");
        System.out.println("* \t\t\t\t\t\t\t\t\t\t\t\t\t      *");
        System.out.println("*\t\t\t\t      ____  ___    _   ______  __  ___   ________    \t\t\t      *");
        System.out.println("*\t\t\t\t     / __ )/   |  / | / / __ \\/ / / / | / / ____/   \t\t\t      *");
        System.out.println("*\t\t\t\t    / __  / /| | /  |/ / / / / / / /  |/ / / __      \t\t\t      *");
        System.out.println("*\t\t\t\t   / /_/ / ___ |/ /|  / /_/ / /_/ / /|  / /_/ /      \t\t\t      *");
        System.out.println("*\t\t\t\t  /_____/_/  |_/_/ |_/_____/\\____/_/ |_/\\____/     \t\t\t      *");
        System.out.println("* \t\t\t\t\t\t\t\t\t\t\t\t\t      *");
        System.out.println("********************************************| PBO-8 2020 UNIKOM |**********************************************");
        System.out.println("************************************| 10118325-10118342-10118312-10118321 |************************************");
    }
    
    public void menu(){
        System.out.println("----------------------------------------------------| MENU |---------------------------------------------------");
        System.out.println(" 1. Data Bus");
        System.out.println(" 2. Data Perjalanan");
        System.out.println(" 3. Data Booking Tiket");
        System.out.println(" 0. Keluar");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
    
    public void pilihan(){
        System.out.print(" Pilihan Anda : ");
    }
    
    public void menuBus(){
        System.out.println("--------------------------------------------------| MENU BUS |-------------------------------------------------");
        System.out.println(" 1. Tambah Data Bus");
        System.out.println(" 2. Tampilkan Data Bus");
        System.out.println(" 3. Ubah Data Bus");
        System.out.println(" 4. Cari Data Bus");
        System.out.println(" 5. Hapus Data Bus");
        System.out.println(" 0. Kembali");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
    
    public void menuTrip(){
        System.out.println("----------------------------------------------| MENU PERJALANAN |----------------------------------------------");
        System.out.println(" 1. Tambah Perjalanan");
        System.out.println(" 2. Tampilkan Perjalanan");
        System.out.println(" 3. Ubah Perjalanan");
        System.out.println(" 4. Cari Perjalanan");
        System.out.println(" 5. Hapus Perjalanan");
        System.out.println(" 0. Kembali");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
    
    public void menuBooking(){
        System.out.println("---------------------------------------------| MENU BOOKING TIKET |--------------------------------------------");
        System.out.println(" 1. Pesan Tiket Baru");
        System.out.println(" 2. Tampilkan Data Tiket");
        System.out.println(" 3. Ubah Data Tiket");
        System.out.println(" 4. Cari Data Tiket");
        System.out.println(" 5. Hapus Data Tiket");
        System.out.println(" 0. Kembali");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }
    
    public void searchBus(){
        System.out.println("-----------------------------------------------| PENCARIAN BUS |------------------------------------------------");
        System.out.println(" 1. Cari Berdasarkan Kode Bus");
        System.out.println(" 2. Cari Berdasarkan Jumlah Seat");
        System.out.println(" 3. Cari Berdasarkan Jam Berangkat");
        System.out.println(" 0. Kembali");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }
    
    public void searchTrip(){
        System.out.println("-------------------------------------------| PENCARIAN PERJALANAN |---------------------------------------------");
        System.out.println(" 1. Cari Berdasarkan Kode Perjalanan");
        System.out.println(" 2. Cari Berdasarkan Tujuan");
        System.out.println(" 3. Cari Berdasarkan Harga Perjalanan");
        System.out.println(" 4. Cari Berdasarkan Jumlah Makan");
        System.out.println(" 5. Cari Berdasarkan Jam Berangkat");
        System.out.println(" 0. Kembali");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }  
    
    public void searchBooking(){
        System.out.println("-------------------------------------------| PENCARIAN DATA TIKET |---------------------------------------------");
        System.out.println(" 1. Cari Berdasarkan Kode Tiket [ Detail ]");
        System.out.println(" 2. Cari Berdasarkan Nama Pembeli");
        System.out.println(" 3. Cari Berdasarkan Tujuan");
        System.out.println(" 4. Cari Berdasarkan Tanggal Pesan");
        System.out.println(" 5. Cari Berdasarkan Tanggal Berangkat");
        System.out.println(" 0. Kembali");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }
    
    public void clrscr(){
    //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
    
    public void bye(){
        System.out.println(" Bye..");
    }
    
}
