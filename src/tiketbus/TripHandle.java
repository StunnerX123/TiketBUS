/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiketbus;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author whiz
 */
public class TripHandle {
    Scanner sc = new Scanner(System.in);
    Views display = new Views();
    Database db = new Database();
    FormatData format = new FormatData();
    BusHandle bus = new BusHandle();
    
    private String pil,kode,tujuan,harga,makan,kodebus,jam;
    private String table = "t_perjalanan";
    private String table2 = "t_perjalanan`,`t_bus";
    
    public void add(){
        boolean done = false;
        
        while(!done){
            display.clrscr();
            display.header();
            System.out.println("------------------------------------------| TAMBAH DATA PERJALANAN |-------------------------------------------");
            System.out.println(" Contoh Kode Perjalanan [ T-001 ] ");
            System.out.print(" Kode Perjalanan : T-");
            kode = "T-"+sc.nextLine();
            System.out.println("");
            System.out.println(" Contoh Tujuan [ Bali ] Min 3, Max 15 Karakter");
            System.out.print(" Tujuan Perjalanan : ");
            tujuan = WordUtils.capitalizeFully(sc.nextLine(),' ');
            System.out.println("");
            System.out.println(" Contoh Harga [ 452000 ] Min 1000, Max 9999999");
            System.out.print(" Harga Perjalanan : Rp. ");
            harga = sc.nextLine();
            System.out.println("");
            System.out.println(" Contoh Makan [ 3 ] Min 0, Max 99");
            System.out.print(" Jumlah Makan Perjalanan : ");
            makan = sc.nextLine();
            System.out.println("");
            bus.dataOnly();
            System.out.print(" Masukan Kode Bus : ");
            kodebus = sc.nextLine();
            kodebus = kodebus.toUpperCase();
            System.out.println("---------------------------------------------------------------------------------------------------------------");
           
            if(!format.kodeBus(kodebus) || !format.harga(harga) || !format.kodePerjalanan(kode) || !format.tujuan(tujuan) || !format.makan(makan)){
                System.out.println(" Data yang Dimasukan Tidak Sesuai!");
                sc.nextLine();
            } else if (!db.checkBusCode(kodebus)){
                System.out.println(" Kode Bus Sudah Digunakan Oleh Perjalanan Lain");
                sc.nextLine();
            } else {
                String [] data = {kode,tujuan,harga,makan,kodebus};
                if(db.insert(table, data)){
                    System.out.println(" Penambahan Data Perjalanan Berhasil!");
                    System.out.println("---------------------------------------------------------------------------------------------------");
                }else{
                    System.out.println(" Terjadi Kesalahan Saat Penambahan Data!");
                    System.out.println(" Pastikan Kode Perjalanan Unik");
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
        System.out.println("----------------------------------------------| DATA PERJALANAN |----------------------------------------------");
        System.out.println("\t\t----------------------------------------------------------------------------");
        System.out.println("\t\t| Kode Perjalanan |      Tujuan      |    Harga    | Jam Berangkat | Makan |");
        System.out.println("\t\t|-----------------|------------------|-------------|---------------|-------|");
        List<Map<String, Object>> data = db.select(table2, "t_perjalanan.kode_bus=t_bus.kode_bus");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("id_perjalanan").toString();
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                harga = format.formatHarga(data.get(i).get("harga").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                makan = format.formatMakan(data.get(i).get("makan").toString());
                System.out.println("\t\t|      "+kode+"      | "+tujuan +" | "+ harga +" |     "+jam+"     |  "+makan+"  |");
            }
        } else {
        System.out.println("\t\t|----------------------------| TIDAK ADA DATA |----------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
   
    public void update(){
        boolean first = true;
        boolean second = true;
        while(first){
            view();
            System.out.println(" Masukan 0 untuk kembali");
            System.out.print(" Masukan Kode Perjalanan : ");
            pil = sc.nextLine();
            if(pil.equals("0")){
                first = false;
                second = false;
            } else if(format.kodePerjalanan(pil)){
                String where = "`id_perjalanan`='"+pil+"'" ;
                List<Map<String, Object>> data = db.select(table, where);
                if(data.size() > 0){
                    first = false;
                    while(second){
                        display.clrscr();
                        display.header();
                        System.out.println("-------------------------------------------| UBAH DATA PERJALANAN |--------------------------------------------");
                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                        System.out.println(" Tekan [Enter] untuk Tidak mengubah data sebelumnya");
                        System.out.println(" Kode Perjalanan Sebelumnya \t\t: "+data.get(0).get("id_perjalanan"));
                        System.out.print(" Kode Perjalanan Baru \t\t\t: ");
                        kode = sc.nextLine();
                        System.out.println("");
                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                        System.out.println(" Tujuan Perjalanan Sebelumnya \t\t: "+data.get(0).get("tujuan"));
                        System.out.print(" Tujuan Bus Baru \t\t\t: ");
                        tujuan = sc.nextLine();
                        System.out.println("");
                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                        System.out.println(" Harga Perjalanan Sebelumnya \t\t: "+data.get(0).get("harga").toString());
                        System.out.print(" Harga Perjalanan Baru \t\t\t: Rp. ");
                        harga = sc.nextLine();
                        System.out.println("");
                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                        System.out.println(" Jumlah Makan Sebelumnya \t\t: "+data.get(0).get("makan").toString());
                        System.out.print(" Jumlah Makan Baru \t\t\t: ");
                        makan = sc.nextLine();
                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                        bus.dataOnly();
                        System.out.println(" Kode Bus Perjalanan Sebelumnya \t: "+data.get(0).get("kode_bus").toString());
                        System.out.print(" Kode Bus Perjalanan Baru \t\t: ");
                        kodebus = sc.nextLine();
                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                        
                        if(kode.equals("")){
                            kode = data.get(0).get("id_perjalanan").toString();
                        } 
                        if(tujuan.equals("")){
                            tujuan = data.get(0).get("tujuan").toString();
                        }
                        if(harga.equals("")){
                            harga = data.get(0).get("harga").toString();
                        }
                        if(makan.equals("")){
                            makan = data.get(0).get("makan").toString();
                        } 
                        if(kodebus.equals("")){
                            kodebus = data.get(0).get("kode_bus").toString();
                        } 
                        
                        if(!format.kodeBus(kodebus) || !format.harga(harga) || !format.kodePerjalanan(kode) || !format.tujuan(tujuan) || !format.makan(makan)){
                            System.out.println(" Data yang Dimasukan Tidak Sesuai!");
                            sc.nextLine();
                        } else if (!db.checkBusCode(kodebus)){
                            System.out.println(" Kode Bus Sudah Digunakan Oleh Perjalanan Lain");
                            sc.nextLine();
                        } else {
                            where = " WHERE `id_perjalanan`='"+pil+"' AND `aktif`='1'" ;
                            
                            String[] uData = {kode,tujuan,harga,makan,kodebus};
                            String[] col = {"id_perjalanan","tujuan","harga","makan","kode_bus"};
                            
                            if(db.update(table, col, uData, where)){
                                System.out.println(" Data Berhasil Diubah! Tekan [Enter] Untuk Kembali... ");
                                sc.nextLine();
                                second=false;
                            } else {
                                System.out.println(" Terjadi Kesalahan Saat Pengubahan Data");
                                System.out.println(" Pastikan Kode Perjalanan Unik");
                                System.out.println(" Pastikan data yang diinput sesuai dan terkoneksi ke database");
                                sc.nextLine();
                            }
                        }
                    }
                } else {
                    System.out.println(" Tidak dapat menemukan Kode Perjalanan! ");
                    sc.nextLine();
                }  
            } else {
                System.out.println(" Kode Perjalanan Tidak Sesuai!");
                sc.nextLine();
            }
        }
    }
    
    public void search(){
        do{ 
            display.clrscr();
            display.header();
            display.searchTrip();
            display.pilihan();

            pil = sc.nextLine();

            switch (pil) {
                case "1":
                    searchKode();              
                    break;
                case "2":
                    searchTujuan();
                    break;
                case "3":
                    searchHarga();
                    break;
                case "4":
                    searchMakan();
                    break; 
                case "5":
                    searchBerangkat();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Pilihan tidak Sesuai!");
                    sc.nextLine();
                    break;
            }
        } while (!pil.equals("0"));
    }
    
    public void searchKode(){
        String where;
        display.clrscr();
        display.header();
        System.out.println("-----------------------------------------| PENCARIAN KODE PERJALANAN |-----------------------------------------");
        System.out.print(" Masukan Kode Perjalanan : ");
        where=sc.nextLine();
        System.out.println("");
        System.out.println("\t\t  ---------------------------------------------------------------------------");
        System.out.println("\t\t  ---------------------------| HASIL PENCARIAN |-----------------------------");
        System.out.println("\t\t  ---------------------------------------------------------------------------");
        System.out.println("\t\t  | Kode Perjalanan |     Tujuan      |    Harga    | Jam Berangkat | Makan |");
        System.out.println("\t\t  |-----------------|-----------------|-------------|---------------|-------|");
        List<Map<String, Object>> data = db.select(table2, "t_perjalanan.kode_bus=t_bus.kode_bus AND id_perjalanan LIKE '%"+where+"%'");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("id_perjalanan").toString();
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                harga = format.formatHarga(data.get(i).get("harga").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                makan = format.formatMakan(data.get(i).get("makan").toString());
                System.out.println("\t\t  |      "+kode+"      | "+tujuan +" | "+ harga +" |     "+jam+"     |  "+makan+"  |");
            }
        } else {
            System.out.println("\t\t  |---------------------------| TIDAK ADA DATA |----------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
    
     public void searchTujuan(){
        String where;
        display.clrscr();
        display.header();
        System.out.println("----------------------------------------| PENCARIAN TUJUAN PERJALANAN |----------------------------------------");
        System.out.print(" Masukan Tujuan Perjalanan : ");
        where=sc.nextLine();
        System.out.println("");
        System.out.println("\t\t  ---------------------------------------------------------------------------");
        System.out.println("\t\t  ---------------------------| HASIL PENCARIAN |-----------------------------");
        System.out.println("\t\t  ---------------------------------------------------------------------------");
        System.out.println("\t\t  | Kode Perjalanan |     Tujuan      |    Harga    | Jam Berangkat | Makan |");
        System.out.println("\t\t  |-----------------|-----------------|-------------|---------------|-------|");
        List<Map<String, Object>> data = db.select(table2, "t_perjalanan.kode_bus=t_bus.kode_bus AND tujuan LIKE '%"+where+"%'");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("id_perjalanan").toString();
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                harga = format.formatHarga(data.get(i).get("harga").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                makan = format.formatMakan(data.get(i).get("makan").toString());
                System.out.println("\t\t  |      "+kode+"      | "+tujuan +" | "+ harga +" |     "+jam+"     |  "+makan+"  |");
            }
        } else {
            System.out.println("\t\t  |---------------------------| TIDAK ADA DATA |----------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
     
     public void searchHarga(){
        String min,max;
        display.clrscr();
        display.header();
        System.out.println("----------------------------------------| PENCARIAN HARGA PERJALANAN |-----------------------------------------");
        System.out.print(" Masukan Harga Minimal  : Rp. ");
        min=sc.nextLine();
        System.out.println("");
        System.out.print(" Masukan Harga Maksimal : Rp. ");
        max=sc.nextLine();
        System.out.println("");
        System.out.println("\t\t  ----------------------------------------------------------------------------");
        System.out.println("\t\t  ----------------------------| HASIL PENCARIAN |-----------------------------");
        System.out.println("\t\t  ----------------------------------------------------------------------------");
        System.out.println("\t\t  | Kode Perjalanan |      Tujuan      |    Harga    | Jam Berangkat | Makan |");
        System.out.println("\t\t  |-----------------|------------------|-------------|---------------|-------|");
        List<Map<String, Object>> data = db.select(table2, "t_perjalanan.kode_bus=t_bus.kode_bus AND `harga` >= '"+min+"' AND `harga` <= '"+max+"'");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("id_perjalanan").toString();
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                harga = format.formatHarga(data.get(i).get("harga").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                makan = format.formatMakan(data.get(i).get("makan").toString());
                System.out.println("\t\t  |      "+kode+"      | "+tujuan +" | "+ harga +" |     "+jam+"     |  "+makan+"  |");
            }
        } else {
            System.out.println("\t\t  |---------------------------| TIDAK ADA DATA |-----------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
     
      public void searchMakan(){
        String min,max;
        display.clrscr();
        display.header();
        System.out.println("-------------------------------------| PENCARIAN JUMLAH MAKAN PERJALANAN |-------------------------------------");
        System.out.print(" Masukan Jumlah Makan Minimal  : ");
        min=sc.nextLine();
          System.out.println("");
        System.out.print(" Masukan Jumlah Makan Maksimal : ");
        max=sc.nextLine();
          System.out.println("");
        System.out.println("\t\t  ----------------------------------------------------------------------------");
        System.out.println("\t\t  ---------------------------| HASIL PENCARIAN |------------------------------");
        System.out.println("\t\t  ----------------------------------------------------------------------------");
        System.out.println("\t\t  | Kode Perjalanan |      Tujuan      |    Harga    | Jam Berangkat | Makan |");
        System.out.println("\t\t  |-----------------|------------------|-------------|---------------|-------|");
        List<Map<String, Object>> data = db.select(table2, "t_perjalanan.kode_bus=t_bus.kode_bus AND `makan` >= '"+min+"' AND `makan` <= '"+max+"'");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("id_perjalanan").toString();
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                harga = format.formatHarga(data.get(i).get("harga").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                makan = format.formatMakan(data.get(i).get("makan").toString());
                System.out.println("\t\t  |      "+kode+"      | "+tujuan +" | "+ harga +" |     "+jam+"     |  "+makan+"  |");
            }
        } else {
            System.out.println("\t\t  |---------------------------| TIDAK ADA DATA |-----------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
     
     public void searchBerangkat(){
        String min,max;
        display.clrscr();
        display.header();
        System.out.println("------------------------------------------| PENCARIAN JAM BERANGKAT |------------------------------------------");
        System.out.println(" Jam = [jj:mm] Contoh = 10:30");
        System.out.print(" Masukan Jam Berangkat Minimal  : ");
        min=sc.nextLine();
         System.out.println("");
        System.out.print(" Masukan Jam Berangkat Maksimal : ");
        max=sc.nextLine();
         System.out.println("");
        System.out.println("\t\t  ----------------------------------------------------------------------------");
        System.out.println("\t\t  ----------------------------| HASIL PENCARIAN |-----------------------------");
        System.out.println("\t\t  ----------------------------------------------------------------------------");
        System.out.println("\t\t  | Kode Perjalanan |      Tujuan      |    Harga    | Jam Berangkat | Makan |");
        System.out.println("\t\t  |-----------------|------------------|-------------|---------------|-------|");
        List<Map<String, Object>> data = db.select(table2, "t_perjalanan.kode_bus=t_bus.kode_bus AND `jam_berangkat` >= '"+min+"' AND `jam_berangkat` <= '"+max+"'");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                kode = data.get(i).get("id_perjalanan").toString();
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                harga = format.formatHarga(data.get(i).get("harga").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                makan = format.formatMakan(data.get(i).get("makan").toString());
                System.out.println("\t\t  |      "+kode+"      | "+tujuan +" | "+ harga +" |     "+jam+"     |  "+makan+"  |");
            }
        } else {
            System.out.println("\t\t  |---------------------------| TIDAK ADA DATA |-----------------------------|");
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
            System.out.print(" Masukan Kode Perjalanan Untuk Dihapus : ");
            pil = sc.nextLine();
            if(pil.equals("0")){
                done = true;
            } else if(format.kodePerjalanan(pil)){
                String where = "`id_perjalanan`='"+pil+"'" ;
                String key = "id_perjalanan";
                String val = format.rawDate();
                if(db.delete( where, val, 1)){
                System.out.println("---------------------------------------------------------------------------------------------------------------");
                    System.out.println(" Data Perjalanan Berhasil Dihapus");
                    done = true;
                } else {
                    System.out.println(" Tidak dapat menemukan Kode Perjalanan");
                }  
            } else {
                System.out.println(" Kode Perjalalanan Tidak Sesuai!");
            }
            System.out.println(" Tekan [Enter] Untuk Kembali...");
            sc.nextLine();
        }
    }
      
      public void dataOnly(){
        display.clrscr();
        display.header();
        System.out.println("\t\t    ---------------------------------------------------------------------");
        System.out.println("\t\t    |     Tujuan       |    Harga    | Jam Berangkat | Makan | Kode Bus |");
        System.out.println("\t\t    |------------------|-------------|---------------|-------|----------|");
        List<Map<String, Object>> data = db.select(table2, "t_perjalanan.kode_bus=t_bus.kode_bus");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                harga = format.formatHarga(data.get(i).get("harga").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                makan = format.formatMakan(data.get(i).get("makan").toString());
                kode = data.get(i).get("kode_bus").toString();
                System.out.println("\t\t    | "+tujuan +" | "+ harga +" |     "+jam+"     |  "+makan+"  |  "+ kode +"   |");
            }
        } else {
            System.out.println("\t\t    |-----------------------| TIDAK ADA DATA |--------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
      
      public void getTrip(){
        System.out.println("\t\t  ---------------------------------------------------------------------");
        System.out.println("\t\t  |     Tujuan       |    Harga    | Jam Berangkat | Makan | Kode Bus |");
        System.out.println("\t\t  |------------------|-------------|---------------|-------|----------|");
        List<Map<String, Object>> data = db.select(table2, "t_perjalanan.kode_bus=t_bus.kode_bus");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                harga = format.formatHarga(data.get(i).get("harga").toString());
                jam = format.formatJam(data.get(i).get("jam_berangkat").toString());
                makan = format.formatMakan(data.get(i).get("makan").toString());
                kode = data.get(i).get("kode_bus").toString();
                System.out.println("\t\t  | "+tujuan +" | "+ harga +" |     "+jam+"     |  "+makan+"  |  "+ kode +"   |");
            }
        } else {
            System.out.println("\t\t  |-----------------------| TIDAK ADA DATA |--------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
      
}
