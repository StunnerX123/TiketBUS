/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiketbus;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 *
 * @author whiz
 */
public class BusHandle {
    Scanner sc = new Scanner(System.in);
    Views display = new Views();
    Database db = new Database();
    FormatData format = new FormatData();
    
    private String pil,kode,seat,jam;
    private String table = "t_bus";
    
    public void add(){
        boolean done = false;
        
        while(!done){
            display.clrscr();
            display.header();
            System.out.println("----------------------------------------------| TAMBAH DATA BUS |----------------------------------------------");
            System.out.println(" Contoh Kode Bus [ DS-01 ]");
            System.out.print(" Kode Bus : ");
            kode = sc.nextLine();
            kode = kode.toUpperCase();
            System.out.println("");
            System.out.println(" Jumlah Seat Min 1, Max 999");
            System.out.print(" Jumlah Seat : ");
            seat = sc.nextLine();
            System.out.println("");
            System.out.println(" Contoh Jam Berangkat [ 07:00 ]");
            System.out.print(" Jam Berangkat : ");
            jam = sc.nextLine();
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            if(!format.kodeBus(kode) || !format.seatBus(seat) || !format.jam(jam)){
                System.out.println(" Data yang Dimasukan Tidak Sesuai!");
                sc.nextLine();
            } else {
                String [] data = {kode,seat,jam};
                if(db.insert(table, data)){
                    System.out.println(" Penambahan Data Bus Berhasil!");
                    sc.nextLine();
                }else{
                    System.out.println(" Terjadi Kesalahan Saat Penambahan Data");
                    System.out.println(" Pastikan Kode Bus Unik");
                    System.out.println(" Pastikan data yang diinput sesuai dan terkoneksi ke database");
                    sc.nextLine();
                };
            }
            System.out.println(" Apakah anda ingin memasukan data lagi ?");
            System.out.println(" Pilih [y] untuk memasukan data, atau character lain untuk keluar");
            System.out.print(" Pilihan anda : ");
            pil = sc.nextLine();
            if(!pil.equals("y") && !pil.equals("Y")){
                done = true;
            }
        }
    }
    
    public void view(){
        display.clrscr();
        display.header();
        System.out.println("--------------------------------------------------| DATA BUS |-------------------------------------------------");
        System.out.println("\t\t\t\t      -----------------------------------");
        System.out.println("\t\t\t\t      | Kode Bus | Seat | Jam Berangkat |");
        System.out.println("\t\t\t\t      |----------|------|---------------|");
        List<Map<String, Object>> data = db.select(table, "");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("kode_bus").toString();
                seat = format.formatJmlhSeat(data.get(i).get("jmlh_seat").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                System.out.println("\t\t\t\t      |  "+kode +"   | "+ seat +"  |     "+ jam+"     |");
                System.out.println("\t\t\t\t      -----------------------------------");
            }
        } else {
            System.out.println("|---------------------------------------------| TIDAK ADA DATA |----------------------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
    
    public void update(){
        boolean first = true;
        boolean second = true;
        while(first){
            view();
            System.out.println(" Masukan 0 untuk kembali");
            System.out.print(" Masukan Kode Bus : ");
            pil = sc.nextLine();
            if(pil.equals("0")){
                first = false;
                second = false;
            } else if(format.kodeBus(pil)){
                String where = "`kode_bus`='"+pil+"'" ;
                List<Map<String, Object>> data = db.select(table, where);
                if(data.size() > 0){
                    first = false;
                    while(second){
                        display.clrscr();
                        display.header();
                        System.out.println("-----------------------------------------------| UBAH DATA BUS |-----------------------------------------------");
                        System.out.println(" Tekan [Enter] untuk Tidak mengubah data sebelumnya");
                        System.out.println(" Kode Bus Sebelumnya \t\t: "+data.get(0).get("kode_bus"));
                        System.out.print(" Kode Bus Baru \t\t\t: ");
                        kode = sc.nextLine();
                        System.out.println("");
                        System.out.println(" Seat Bus Sebelumnya \t\t: "+data.get(0).get("jmlh_seat"));
                        System.out.print(" Seat Bus Baru \t\t\t: ");
                        seat = sc.nextLine();
                        System.out.println("");
                        System.out.println(" Jam Berangkat Sebelumnya \t: "+format.formatJam(data.get(0).get("jam_berangkat").toString()));
                        System.out.print(" Jam Berangkat Baru \t\t: ");
                        jam = sc.nextLine();
                        System.out.println("");
                                                             
                        if(kode.equals("")){
                            kode = data.get(0).get("kode_bus").toString();
                        } 
                        if(seat.equals("")){
                            seat = data.get(0).get("jmlh_seat").toString();
                        }
                        if(jam.equals("")){
                            jam = format.formatJam(data.get(0).get("jam_berangkat").toString());
                        } 
                        
                        if(!format.kodeBus(kode)|| !format.seatBus(seat) || !format.jam(jam) ){
                            System.out.println(" Kode Bus atau Jumlah Seat atau Jam Berangkat Tidak Sesuai!");
                            sc.nextLine();
                        } else {
                            boolean hasil;
                            where = " WHERE `kode_bus`='"+pil+"' AND `aktif`='1'" ;
                            String[] uData = {kode,seat,jam};
                            String[] col = {"kode_bus","jmlh_seat","jam_berangkat"};
                            hasil = db.update(table, col, uData,where);
                            
                            if(hasil){
                                System.out.println(" Data Berhasil Diubah!");
                                sc.nextLine();
                                second=false;
                            } else {
                                System.out.println(" Terjadi Kesalahan Saat Penambahan Data");
                                System.out.println(" Pastikan Kode Bus Unik");
                                System.out.println(" Pastikan data yang diinput sesuai dan terkoneksi ke database");
                                sc.nextLine();
                            }
                        }
                    }
                } else {
                    System.out.println(" Tidak dapat menemukan Kode Bus");
                    sc.nextLine();
                }  
            } else {
                System.out.println(" Kode Bus Tidak Sesuai!");
                sc.nextLine();
            }
        }
    }
    
    public void search(){
        do{ 
            display.clrscr();
            display.header();
            display.searchBus();
            display.pilihan();

            pil = sc.nextLine();

            switch (pil) {
                case "1":
                    searchKode();              
                    break;
                case "2":
                    searchSeat();
                    break;
                case "3":
                    searchJam();
                    break;
                case "0":
                    break;
                default:
                    System.out.println(" Pilihan tidak Sesuai!");
                    sc.nextLine();
                    break;
            }
        } while (!pil.equals("0"));
    }
    
    public void searchKode(){
        String where;
        display.clrscr();
        display.header();
        System.out.println("--------------------------------------------| PENCARIAN KODE BUS |---------------------------------------------");
        System.out.print(" Masukan Kode Bus : ");
        where=sc.nextLine();
        System.out.println("");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------| HASIL PENCARIAN |----------------------------------------------");
        System.out.println("\t\t\t\t      -----------------------------------");
        System.out.println("\t\t\t\t      | Kode Bus | Seat | Jam Berangkat |");
        System.out.println("\t\t\t\t      |----------|------|---------------|");
        List<Map<String, Object>> data = db.select(table, "`kode_bus` LIKE '%"+where+"%'");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("kode_bus").toString();
                seat = format.formatJmlhSeat(data.get(i).get("jmlh_seat").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                System.out.println("\t\t\t\t      |  "+kode +"   | "+ seat +"  |     "+ jam +"     |");
                System.out.println("\t\t\t\t      -----------------------------------");
            }
        } else {
            System.out.println("|---------------------------------------------| TIDAK ADA DATA |----------------------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
    
    public void searchSeat(){
        String min,max;
        display.clrscr();
        display.header();
        System.out.println("------------------------------------------| PENCARIAN JUMLAH SEAT BUS |----------------------------------------");
        System.out.print(" Masukan Jumlah Seat Minimal  : ");
        min=sc.nextLine();
        System.out.println("");
        System.out.print(" Masukan Jumlah Seat Maksimal : ");
        max=sc.nextLine();
        System.out.println("");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------| HASIL PENCARIAN |----------------------------------------------");
        System.out.println("\t\t\t\t      -----------------------------------");
        System.out.println("\t\t\t\t      | Kode Bus | Seat | Jam Berangkat |");
        System.out.println("\t\t\t\t      |----------|------|---------------|");
        List<Map<String, Object>> data = db.select(table, "`jmlh_seat` >= '"+min+"' AND `jmlh_seat` <= '"+max+"';");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("kode_bus").toString();
                seat = format.formatJmlhSeat(data.get(i).get("jmlh_seat").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                System.out.println("\t\t\t\t      |  "+kode +"   | "+ seat +"  |     "+ jam +"     |");
                System.out.println("\t\t\t\t      -----------------------------------");

            }
        } else {
            System.out.println("|---------------------------------------------| TIDAK ADA DATA |----------------------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
    
    public void searchJam(){
        String min,max;
        display.clrscr();
        display.header();     
        System.out.println("---------------------------------------| PENCARIAN JAM BERANGKAT BUS |-----------------------------------------");
        System.out.println(" Jam = [jj:mm] Contoh = 10:30");
        System.out.print(" Masukan Jam Berangkat Minimal  : ");
        min=sc.nextLine();
        System.out.println("");
        System.out.print(" Masukan Jam Berangkat Maksimal : ");
        max=sc.nextLine();
        System.out.println("");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------| HASIL PENCARIAN |----------------------------------------------");
        System.out.println("\t\t\t\t      -----------------------------------");
        System.out.println("\t\t\t\t      | Kode Bus | Seat | Jam Berangkat |");
        System.out.println("\t\t\t\t      |----------|------|---------------|");
        List<Map<String, Object>> data = db.select(table, "`jam_berangkat` >= '"+min+"' AND `jam_berangkat` <= '"+max+"';");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("kode_bus").toString();
                seat = format.formatJmlhSeat(data.get(i).get("jmlh_seat").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                System.out.println("\t\t\t\t      |  "+kode +"   | "+ seat +"  |     "+ jam +"     |");
                System.out.println("\t\t\t\t      -----------------------------------");
            }
        } else {
            System.out.println("|---------------------------------------------| TIDAK ADA DATA |----------------------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
     
    public void delete(){
        boolean done = false;
        while(!done){
            view();
            System.out.println(" Masukan 0 untuk kembali");
            System.out.print(" Masukan Kode Bus Untuk Dihapus : ");
            pil = sc.nextLine();
            if(pil.equals("0")){
                done = true;
            } else if(format.kodeBus(pil)){
                String where = " `kode_bus`='"+pil+"'" ;
                String key = "kode_bus";
                String val = format.rawDate();
                if(db.delete( where, val, 2)){
                    System.out.println(" Data Bus Berhasil Dihapus");
                    done = true;
                    sc.nextLine();
                } else {
                    System.out.println(" Tidak dapat menemukan Kode Bus");
                    sc.nextLine();
                }  
            } else {
                System.out.println(" Kode Bus Tidak Sesuai!");
                sc.nextLine();
            }
        }
    }
    
    public void dataOnly(){
        System.out.println("------------------------------------------------| DATA BUS |---------------------------------------------------");
        System.out.println("\t\t\t\t    -----------------------------------");
        System.out.println("\t\t\t\t    | Kode Bus | Seat | Jam Berangkat |");
        System.out.println("\t\t\t\t    |----------|------|---------------|");
        List<Map<String, Object>> data = db.select(table, "");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("kode_bus").toString();
                seat = format.formatJmlhSeat(data.get(i).get("jmlh_seat").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                System.out.println("\t\t\t\t    |  "+kode +"   | "+ seat +"  |     "+ jam +"     |");
                System.out.println("\t\t\t\t    -----------------------------------");
            }
        } else {
        System.out.println("\t\t\t\t    |--------| TIDAK ADA DATA |-------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
}
