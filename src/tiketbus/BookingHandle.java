/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiketbus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author whiz
 */
public class BookingHandle {
    Scanner sc = new Scanner(System.in);
    Views display = new Views();
    Database db = new Database();
    FormatData format = new FormatData();
    BusHandle bus = new BusHandle();
    TripHandle trip = new TripHandle();
    
    private String pil,tujuan,id_tiket,ktp,nama_pemesan,tgl_psn,tgl_prgi,kodebus,jam;
    private String table = "t_tiket";
    
    public void add() {
        boolean done = false;
        boolean next = false;
        while(!done){
            display.clrscr();
            display.header();
            System.out.println("---------------------------------------------| TAMBAH DATA TIKET |---------------------------------------------");
            trip.dataOnly();
            System.out.print(" Masukan Tujuan : ");
            tujuan = WordUtils.capitalizeFully(sc.nextLine(),' ');
            System.out.println("");
            System.out.println(" Tanggal Tidak Boleh Sebelum Hari ini ");
            System.out.println(" Tanggal [ dd/mm/yyyy ] Contoh = [ 21/02/2040 ]");
            System.out.print(" Masukan Tanggal Berangkat : ");
            tgl_prgi = format.formatTgl(sc.nextLine());
            System.out.println("");
            System.out.println(" Masukan Kode Bus Untuk Menentukan Jam Berangkat ");
            System.out.print(" Kode Bus Berangkat : ");
            kodebus = sc.nextLine();
            kodebus = kodebus.toUpperCase();
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            if(format.tujuandb(tujuan) && format.aftertgl(tgl_prgi) && format.kodeBus(kodebus) && db.fullBus(kodebus, tujuan, tgl_prgi)){
                sc.nextLine();
                while(!next){
                    display.clrscr();
                    display.header();
                    System.out.println("---------------------------------------------| TAMBAH DATA TIKET |---------------------------------------------");
                    System.out.println(" Tujuan \t\t: "+tujuan);
                    System.out.println(" Tanggal Berangkat\t: "+format.reFormatTgl(tgl_prgi));
                    System.out.println(" Jam Berangkat \t\t: "+format.formatJam(db.jamBerangkat(kodebus)));
                    System.out.println(" Kode Bus \t\t: "+kodebus);
                    System.out.println("---------------------------------------------------------------------------------------------------------------");
                    System.out.println("-----------------------------------------------| DATA PEMBELI |------------------------------------------------");
                    System.out.println(" Jumlah Karakter Harus 16");
                    System.out.print(" Masukan no. KTP : ");
                    ktp = sc.nextLine();
                    System.out.println("");
                    System.out.println(" Nama Pembeli Min 3, Max 20 karakter");
                    System.out.print(" Masukan Nama Pembeli : ");
                    nama_pemesan = sc.nextLine();
                    if(format.ktp(ktp) && format.nama_pemesan(nama_pemesan)){
                        id_tiket = db.getTiket();
                        tgl_psn = format.getDate(true);
                        String [] data = {id_tiket,ktp,nama_pemesan,tujuan,tgl_psn,tgl_prgi,kodebus};
                        System.out.println(Arrays.toString(data));
                        if(db.insert(table, data)){
                            System.out.println("---------------------------------------------------------------------------------------------------------------");
                            System.out.println(" Pembelian Tiket Berhasil!");
                            System.out.println("---------------------------------------------------------------------------------------------------------------");
                            next = true;
                        } else {
                            System.out.println(" Terjadi Kesalahan Saat Memasukan Data");
                            System.out.println("---------------------------------------------------------------------------------------------------------------");
                            next = true;
                        }
                    }else {
                        System.out.println(" Data yang dimasukan tidak Sesuai!");
                        System.out.println(" Ulangi ? [y] atau karakter lain untuk kembali");
                        System.out.print(" Pilihan anda : "); 
                        pil=sc.nextLine();
                        if(!pil.equals("y") && !pil.equals("Y")){
                            next = true;
                        }
                    }
                }
            } else {
                System.out.println(" Data yang dimasukan tidak sesuai! ");
                System.out.println("---------------------------------------------------------------------------------------------------------------");
            }
            System.out.println(" Apakah anda ingin memasukan data lagi ?");
            System.out.println(" Pilih [y] untuk memasukan data lain, atau karakter lain untuk keluar");
            System.out.print(" Pilihan anda : ");
            pil = sc.nextLine();
            if(!pil.equals("y") && !pil.equals("Y")){
                done = true;
            } else {
                done = false;
                next = false;
            }
        }
    }
    
    public void view(){
        display.clrscr();
        display.header();
        System.out.println("-------------------------------------------------| DATA TIKET |------------------------------------------------");
        System.out.println("\t     -----------------------------------------------------------------------------------");
        System.out.println("\t     | Kode Tiket |     Nama Pemesan     |      Tujuan      | Tgl Berangkat | Kode Bus |");
        System.out.println("\t     |------------|----------------------|------------------|---------------|----------|");
        List<Map<String, Object>> data = db.select(table, "");
        if(data.size()!=0){
            for(int i = 0; i < data.size(); i++){
                id_tiket = data.get(i).get("id_tiket").toString();
                nama_pemesan = format.formatNama(data.get(i).get("nama_pemesan").toString());
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                tgl_prgi = format.reFormatTgl(data.get(i).get("tanggal_berangkat").toString());
                kodebus = data.get(i).get("kode_bus").toString();
                System.out.println("\t\t     | "+ id_tiket +"  | "+nama_pemesan +" | "+ tujuan +" |  "+ tgl_prgi +"   |  "+ kodebus +"   |");
            }
        } else {
        System.out.println("\t     |------------------------------| TIDAK ADA DATA |---------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
    
    public void update(){
        boolean next = false;
        String where;
        display.clrscr();
        display.header();
        System.out.println("----------------------------------------------| UBAH DATA TIKET |----------------------------------------------");
        System.out.print(" Masukan Kode Tiket : B-");
        where="B-"+sc.nextLine();
        List<Map<String, Object>> data = db.select(table, "`id_tiket`='"+where+"'");
        if(data.size()!=0){
            id_tiket = data.get(0).get("id_tiket").toString();
            nama_pemesan = format.formatNama(data.get(0).get("nama_pemesan").toString());
            tujuan = format.formatTujuan(data.get(0).get("tujuan").toString());
            tgl_prgi = format.reFormatTgl(data.get(0).get("tanggal_berangkat").toString());
            tgl_psn = format.reFormatTgl(data.get(0).get("tanggal_pemesanan").toString());
            kodebus = data.get(0).get("kode_bus").toString();
            ktp = data.get(0).get("ktp").toString();
            jam = db.jamBerangkat(kodebus);
            while(!next){
                display.clrscr();
                display.header();
                System.out.println("----------------------------------------------| UBAH DATA TIKET |----------------------------------------------");
                System.out.println(" Kode Tiket \t\t: "+id_tiket);
                System.out.println(" KTP Pemesan \t\t: "+ktp); 
                System.out.println(" Nama Pemesan \t\t: "+nama_pemesan);
                System.out.println("---------------------------------------------------------------------------------------------------------------");
                System.out.println(" Kosongkan Kolom Jika Ingin Tidak Mengubah Data Sebelumnya");
                trip.getTrip();
                System.out.println(" Kota Tujuan Sebelumnya \t: "+tujuan);
                System.out.print(" Kota Tujuan Baru \t\t: ");
                tujuan = WordUtils.capitalizeFully(sc.nextLine(),' ');
                System.out.println("");
                System.out.println(" Tanggal Tidak Boleh Sebelum Hari ini ");
                System.out.println(" Tanggal Berangkat Sebelumnya \t: "+tgl_prgi);
                System.out.print(" Tanggal Berangkat Baru \t: ");
                tgl_prgi = format.formatTgl(sc.nextLine());
                System.out.println("");
                System.out.println(" Jam Berangkat Sebelumnya \t: "+format.formatJam(jam));
                System.out.println(" Masukan Kode Bus Untuk Menentukan Jam Berangkat ");
                System.out.println(" Kode Bus Sebelumnya \t\t: "+kodebus);
                System.out.print(" Kode Bus Baru \t\t: ");
                kodebus = sc.nextLine();
                
                if(tujuan.equals("")){
                    tujuan = data.get(0).get("tujuan").toString();
                }
                if(tgl_prgi.equals("")){
                    tgl_prgi = data.get(0).get("tanggal_berangkat").toString();
                }
                if(kodebus.equals("")){
                    kodebus = data.get(0).get("kode_bus").toString();;
                }
                
                if(format.tujuandb(tujuan) && format.aftertgl(tgl_prgi) && format.kodeBus(kodebus) && db.fullBus(kodebus, tujuan, tgl_prgi)){
                    tgl_psn = format.getDate(true);
                    
                    where = " WHERE `id_tiket`='"+id_tiket+"' AND `aktif`='1'" ;
                    
                    String [] col = {"tujuan","tanggal_pemesanan","tanggal_berangkat","kode_bus"};
                    String [] uData = {tujuan,tgl_psn,tgl_prgi,kodebus};
                    if(db.update(table, col, uData, where)){
                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                        System.out.println(" Data Tiket Berhasil Diubah! ");
                        next=true;
                    } else {
                        System.out.println(" Terjadi Kesalahan Saat Pengubahan Data");
                        System.out.println(" Pastikan data yang diinput sesuai dan terkoneksi ke database");
                        next=true;
                        sc.nextLine();
                    }
                } else {
                    System.out.println(" Data Yang Dimasukan Tidak Sesuai ! ");
                    System.out.println(" Ulangi? Tekan [y] untuk mengulangi atau karakter lain untuk keluar");
                    System.out.print(" Ulangi? : ");
                    pil=sc.nextLine();
                    if(!pil.equals("y") && !pil.equals("Y")){
                        next = true;
                    }
                } 
            }
        } else {
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            System.out.println("|------------------------------------------| DATA TIDAK DITEMUKAN |-------------------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
        
    }
    
    public void search(){
        do{ 
            display.clrscr();
            display.header();
            display.searchBooking();
            display.pilihan();

            pil = sc.nextLine();

            switch (pil) {
                case "1":
                    searchKode();              
                    break;
                case "2":
                    searchNama();
                    break;
                case "3":
                    searchTujuan();
                    break;
                case "4":
                    searchTglPsn();
                    break; 
                case "5":
                    searchTglPrgi();
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
    
    private void searchKode(){
        String where;
        display.clrscr();
        display.header();
        System.out.println("--------------------------------------------| PENCARIAN KODE TIKET |-------------------------------------------");
        System.out.print(" Masukan Kode Tiket : ");
        where=sc.nextLine();
        System.out.println("");
        System.out.println("-------------------------------------------------| DATA TIKET |------------------------------------------------");
        List<Map<String, Object>> data = db.select(table, "`id_tiket`='"+where+"'");
        if(data.size()!=0){
            id_tiket = data.get(0).get("id_tiket").toString();
            nama_pemesan = format.formatNama(data.get(0).get("nama_pemesan").toString());
            tujuan = format.formatTujuan(data.get(0).get("tujuan").toString());
            tgl_prgi = format.reFormatTgl(data.get(0).get("tanggal_berangkat").toString());
            tgl_psn = format.reFormatTgl(data.get(0).get("tanggal_pemesanan").toString());
            kodebus = data.get(0).get("kode_bus").toString();
            ktp = data.get(0).get("ktp").toString();
            jam = db.jamBerangkat(kodebus);
            
            System.out.println(" Kode Tiket \t\t: "+id_tiket);
            System.out.println(" KTP Pemesan \t\t: "+ktp);
            
            
            System.out.println(" Nama Pemesan \t\t: "+nama_pemesan);
            System.out.println(" Kota Tujuan \t\t: "+tujuan);
            System.out.println(" Kota Tujuan \t\t: "+tujuan);
            System.out.println(" Tanggal Berangkat \t: "+tgl_prgi);
            System.out.println(" Tanggal Pemesanan \t: "+tgl_psn);
            System.out.println(" Jam Berangkat \t\t: "+format.formatJam(jam));
            System.out.println(" Kode Bus \t\t: "+kodebus);

            
        } else {
            System.out.println("|-----------------------------------------| DATA TIDAK DITEMUKAN |--------------------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println("Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
    
    private void searchNama(){
        String where;
        display.clrscr();
        display.header();
        System.out.println("------------------------------------------| PENCARIAN NAMA PEMESAN |-------------------------------------------");
        System.out.print(" Masukan Nama Pembeli : ");
        where=sc.nextLine();
        System.out.println("\t      -----------------------------------| DATA TIKET |----------------------------------");
        System.out.println("\t      -----------------------------------------------------------------------------------");
        System.out.println("\t      | Kode Tiket |     Nama Pemesan     |      Tujuan      | Tgl Berangkat | Kode Bus |");
        System.out.println("\t      |------------|----------------------|------------------|---------------|----------|");
        List<Map<String, Object>> data = db.select(table, "`nama_pemesan` LIKE '%"+where+"%'");
        if(data.size()!=0){
            id_tiket = data.get(0).get("id_tiket").toString();
            nama_pemesan = format.formatNama(data.get(0).get("nama_pemesan").toString());
            tujuan = format.formatTujuan(data.get(0).get("tujuan").toString());
            tgl_prgi = format.reFormatTgl(data.get(0).get("tanggal_berangkat").toString());
            kodebus = data.get(0).get("kode_bus").toString();
            
            for(int i = 0; i < data.size(); i++){
                id_tiket = data.get(i).get("id_tiket").toString();
                nama_pemesan = format.formatNama(data.get(i).get("nama_pemesan").toString());
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                tgl_prgi = format.reFormatTgl(data.get(i).get("tanggal_berangkat").toString());
                kodebus = data.get(i).get("kode_bus").toString();
                System.out.println("\t      | "+ id_tiket +"  | "+nama_pemesan +" | "+ tujuan +" |  "+ tgl_prgi +"   |  "+ kodebus +"   |");
            }
            
        } else {
        System.out.println("\t      |--------------------------| DATA TIDAK DITEMUKAN |-------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
    
    private void searchTujuan(){
        String where;
        display.clrscr();
        display.header();
        System.out.println("------------------------------------------| PENCARIAN TUJUAN TIKET |-------------------------------------------");
        System.out.print(" Masukan Tujuan Tiket : ");
        where=sc.nextLine();
        System.out.println("\t      -----------------------------------| DATA TIKET |----------------------------------");
        System.out.println("\t      -----------------------------------------------------------------------------------");
        System.out.println("\t      | Kode Tiket |     Nama Pemesan     |      Tujuan      | Tgl Berangkat | Kode Bus |");
        System.out.println("\t      |------------|----------------------|------------------|---------------|----------|");
        List<Map<String, Object>> data = db.select(table, "`tujuan` LIKE '%"+where+"%'");
        if(data.size()!=0){
            id_tiket = data.get(0).get("id_tiket").toString();
            nama_pemesan = format.formatNama(data.get(0).get("nama_pemesan").toString());
            tujuan = format.formatTujuan(data.get(0).get("tujuan").toString());
            tgl_prgi = format.reFormatTgl(data.get(0).get("tanggal_berangkat").toString());
            kodebus = data.get(0).get("kode_bus").toString();
            
            for(int i = 0; i < data.size(); i++){
                id_tiket = data.get(i).get("id_tiket").toString();
                nama_pemesan = format.formatNama(data.get(i).get("nama_pemesan").toString());
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                tgl_prgi = format.reFormatTgl(data.get(i).get("tanggal_berangkat").toString());
                kodebus = data.get(i).get("kode_bus").toString();
                System.out.println("\t      | "+ id_tiket +"  | "+nama_pemesan +" | "+ tujuan +" |  "+ tgl_prgi +"   |  "+ kodebus +"   |");
            }
            
        } else {
        System.out.println("\t      |--------------------------| DATA TIDAK DITEMUKAN |-------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
    
    private void searchTglPsn(){
        String where;
        display.clrscr();
        display.header();
        System.out.println("------------------------------------------| PENCARIAN TANGGAL PESAN |------------------------------------------");
        System.out.print(" Masukan Tanggal Berangkat [ dd/mm/yyyy ]  : ");
        where=format.formatTgl(sc.nextLine());
        System.out.println("");
        System.out.println("\t------------------------------------------| DATA TIKET |-----------------------------------------");
        System.out.println("\t-------------------------------------------------------------------------------------------------");
        System.out.println("\t| Kode Tiket |     Nama Pemesan     |      Tujuan      | Tgl Berangkat |  Tgl Pesan  | Kode Bus |");
        System.out.println("\t|------------|----------------------|------------------|---------------|-------------|----------|");
        List<Map<String, Object>> data = db.select(table, "`tanggal_pemesanan` = '"+where+"'");
        if(data.size()!=0){
            id_tiket = data.get(0).get("id_tiket").toString();
            nama_pemesan = format.formatNama(data.get(0).get("nama_pemesan").toString());
            tujuan = format.formatTujuan(data.get(0).get("tujuan").toString());
            tgl_prgi = format.reFormatTgl(data.get(0).get("tanggal_berangkat").toString());
            kodebus = data.get(0).get("kode_bus").toString();
            
            for(int i = 0; i < data.size(); i++){
                id_tiket = data.get(i).get("id_tiket").toString();
                nama_pemesan = format.formatNama(data.get(i).get("nama_pemesan").toString());
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                tgl_prgi = format.reFormatTgl(data.get(i).get("tanggal_berangkat").toString());
                tgl_psn = format.reFormatTgl(data.get(i).get("tanggal_pemesanan").toString());
                kodebus = data.get(i).get("kode_bus").toString();
                System.out.println("\t| "+ id_tiket +"  | "+nama_pemesan +" | "+ tujuan +" |  "+ tgl_prgi +"   | "+ tgl_psn+ "  |  "+ kodebus +"   |");
            }
            
        } else {
            System.out.println("\t|--------------------------------| DATA TIDAK DITEMUKAN |---------------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
    
    private void searchTglPrgi(){
        String where;
        display.clrscr();
        display.header();
        System.out.println("---------------------------------------| PENCARIAN TANGGAL BERANGKAT |-----------------------------------------");
        System.out.print(" Masukan Tanggal Berangkat [ dd/mm/yyyy ]  : ");
        where=format.formatTgl(sc.nextLine());
        System.out.println("");
        System.out.println("\t      -----------------------------------| DATA TIKET |----------------------------------");
        System.out.println("\t      -----------------------------------------------------------------------------------");
        System.out.println("\t      | Kode Tiket |     Nama Pemesan     |      Tujuan      | Tgl Berangkat | Kode Bus |");
        System.out.println("\t      |------------|----------------------|------------------|---------------|----------|");
        List<Map<String, Object>> data = db.select(table, "`tanggal_berangkat` = '"+where+"'");
        if(data.size()!=0){
            id_tiket = data.get(0).get("id_tiket").toString();
            nama_pemesan = format.formatNama(data.get(0).get("nama_pemesan").toString());
            tujuan = format.formatTujuan(data.get(0).get("tujuan").toString());
            tgl_prgi = format.reFormatTgl(data.get(0).get("tanggal_berangkat").toString());
            kodebus = data.get(0).get("kode_bus").toString();
            
            for(int i = 0; i < data.size(); i++){
                id_tiket = data.get(i).get("id_tiket").toString();
                nama_pemesan = format.formatNama(data.get(i).get("nama_pemesan").toString());
                tujuan = format.formatTujuan(data.get(i).get("tujuan").toString());
                tgl_prgi = format.reFormatTgl(data.get(i).get("tanggal_berangkat").toString());
                kodebus = data.get(i).get("kode_bus").toString();
                System.out.println("\t      | "+ id_tiket +"  | "+nama_pemesan +" | "+ tujuan +" |  "+ tgl_prgi +"   |  "+ kodebus +"   |");
            }
            
        } else {
            System.out.println("\t      |---------------------------| DATA TIDAK DITEMUKAN |------------------------------|");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(" Tekan [Enter] Untuk Kembali...");
        sc.nextLine();
    }
    
    public void delete(){
        boolean done = false;
        while(!done){
            String where;
            display.clrscr();
            display.header();
            System.out.println("---------------------------------------------| HAPUS DATA TIKET |----------------------------------------------");
            System.out.println(" Masukan 0 untuk kembali");
            System.out.print(" Masukan Kode Tiket : ");
            pil=sc.nextLine();
            System.out.println("");
            if(pil.equals("0")){
                done = true;
            } else {
                System.out.println("-------------------------------------------------| DATA TIKET |------------------------------------------------");
                List<Map<String, Object>> data = db.select(table, "`id_tiket`='"+pil+"'");
                if(data.size()!=0){
                    id_tiket = data.get(0).get("id_tiket").toString();
                    nama_pemesan = format.formatNama(data.get(0).get("nama_pemesan").toString());
                    tujuan = format.formatTujuan(data.get(0).get("tujuan").toString());
                    tgl_prgi = format.reFormatTgl(data.get(0).get("tanggal_berangkat").toString());
                    tgl_psn = format.reFormatTgl(data.get(0).get("tanggal_pemesanan").toString());
                    kodebus = data.get(0).get("kode_bus").toString();
                    ktp = data.get(0).get("ktp").toString();
                    jam = db.jamBerangkat(kodebus);

                    System.out.println(" Kode Tiket \t\t: "+id_tiket);
                    System.out.println(" KTP Pemesan \t\t: "+ktp);
                    System.out.println(" Nama Pemesan \t\t: "+nama_pemesan);
                    System.out.println(" Kota Tujuan \t\t: "+tujuan);
                    System.out.println(" Kota Tujuan \t\t: "+tujuan);
                    System.out.println(" Tanggal Berangkat \t: "+tgl_prgi);
                    System.out.println(" Tanggal Pemesanan \t: "+tgl_psn);
                    System.out.println(" Jam Berangkat \t\t: "+format.formatJam(jam));
                    System.out.println(" Kode Bus \t\t: "+kodebus);
                    System.out.println("---------------------------------------------------------------------------------------------------------------");
                    
                    System.out.println(" Yakin? Data Diatas Akan Dihapus. Tekan [y] untuk Menghapus atau karakter lain untuk Batal");
                    System.out.print(" Pilihan  : ");
                    pil=sc.nextLine();
                    if(!pil.equals("y") && !pil.equals("Y")){
                        done = true;
                    }else {
                        where = "`id_tiket`='"+id_tiket+"'" ;
                        String key = "id_tiket";
                        String val = format.rawDate();
                        
                        if(db.delete( where, val, 0)){
                            System.out.println("---------------------------------------------------------------------------------------------------------------");
                            System.out.println(" Data Perjalanan Berhasil Dihapus");
                            done = true;
                        } else {
                            System.out.println(" Terjadi Kesalahan Saat Penghapusan Data ");
                            done = true;
                        }
                        System.out.println(" Tekan [Enter] Untuk Kembali...");
                        sc.nextLine();
                    }
                } else {
                    System.out.println("|----------------------------------------| DATA TIDAK DITEMUKAN |---------------------------------------------|");
                    System.out.println("---------------------------------------------------------------------------------------------------------------");
                    System.out.println(" Ulangi? Tekan [y] untuk mengulangi atau karakter lain untuk keluar");
                    System.out.print(" Ulangi? : ");
                    pil=sc.nextLine();
                    if(!pil.equals("y") && !pil.equals("Y")){
                        done = true;
                    }
                }
            }
            
        }
    }
    
}
