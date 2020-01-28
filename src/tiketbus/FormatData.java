package tiketbus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author whiz
 */
public class FormatData {
    static DateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
    static DateFormat nowformat = new SimpleDateFormat("dd/MM/yyyy");
    static DateFormat del = new SimpleDateFormat("'-deleted at' yyyy-MM-dd HH:mm:ss:SS");
    
    Database db = new Database();
    
    StringBuffer sb = new StringBuffer();
    public boolean kodeBus(String kode){
        if(kode.length() == 5){
            String[] cek = kode.split("-");
            try {
                try{
                    Integer.parseInt(cek[0]);
                    return false;
                } catch (NumberFormatException e){}
                Integer.parseInt(cek[1]);
                return true;
             }
             catch (NumberFormatException e)
             {
                return false;
             }
        }
        return false;
    }
    
    public boolean kodePerjalanan(String kode){
        if(kode.length() == 5){
            String[] cek = kode.split("-");
            try {
                if(!cek[0].equals("T")){
                    return false;
                }
                Integer.parseInt(cek[1]);
                return true;
             }
             catch (NumberFormatException e)
             {
                return false;
             }
        }
        return false;
    }
    
    public boolean tujuan(String tujuan){
        return (tujuan.length()> 2 && tujuan.length() < 16);
    }
    
    public boolean makan(String makan){
        try{
          int m = Integer.parseInt(makan);
          if(m > 99 || m < 0){
              return false;
          } else{
              return true;
          }
        } catch(NumberFormatException e){
            return false;
        }
    }
    
    public boolean seatBus(String seat){
        try {
            int jmlh = Integer.parseInt(seat);
            return !(jmlh < 1 || jmlh > 999);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    
    public boolean jam(String jam){
     if(jam.length() == 5){
            String[] cek = jam.split(":");
            try {
                int jm = Integer.parseInt(cek[0]);
                int menit = Integer.parseInt(cek[1]);
                
                return !( jm < 0 || jm > 23 || menit < 0 || menit > 59);
             }
             catch (NumberFormatException e)
             {
                return false;
             }
        }
        return false;
    }
    
    public String formatJmlhSeat(String seat){
        int l = seat.length();
        if(l == 1){
            return " "+seat+" ";
        } else if(l == 2){
            return " "+seat;
        } else {
            return seat;
        }
    }
    
    public String formatJam(String jam){
        String [] j = jam.split(":");
        return j[0]+":"+j[1];
    }
    
    public boolean harga(String harga){
            if(harga.length()<3 || harga.length()>7){
                return false;
            }
            try {
                int h = Integer.parseInt(harga);
                return !(h<0);
             }
             catch (NumberFormatException e)
             {
                return false;
             }
    }
    
    public String formatHarga(String harga){
        String t;
        t = sb.append("Rp.").toString();
        for(int i = harga.length(); i<8;i++){
            t=sb.append(" ").toString();
        }
        t = sb.append(harga).toString();
        sb.setLength(0);
    return t;
    }
    
    public String formatMakan(String makan){
        String m;
        m = sb.append(makan).toString();
        for(int i = makan.length(); i<2;i++){
            m=sb.append(" ").toString();
        }
        m = sb.append("X").toString();
        sb.setLength(0);
    return m;
    }
    
    public String formatTujuan(String tujuan){
        String t="";
        t = sb.append(tujuan).toString();
        for(int i = tujuan.length(); i<16; i++){
            t=sb.append(" ").toString();
        }
        sb.setLength(0);
        return t;
    }
    
    public String getDate(boolean w){
        Date now = new Date();
        
        if(w){
            return dateformatter.format(now);
        } else {
            return nowformat.format(now);
        }
        
    }
    
    public boolean tujuandb(String tujuan){
        List<Map<String, Object>> data = db.select("t_perjalanan", "`tujuan`='"+tujuan+"'");
        return(data.size() > 0);
    }
    
    public String formatTgl(String tgl){ 
        String[] t = tgl.split("/");
        int[] c = {31,12,2100};
        String rt;
        int s,p;
        for(int i = 0 ; i < 3 ; i++){
            try{
                
              s = Integer.parseInt(t[i]);
              
              if(s > c[i]){
                  return "-1";
              }
              
            } catch (NumberFormatException e){
                return "-1";
            } 
        }
        
        return t[2]+"-"+t[1]+"-"+t[0];
    }
    
     public String reFormatTgl(String tgl){
         String[] t = tgl.split("-");
         return t[2]+"/"+t[1]+"/"+t[0];
     }
     
     public boolean nama_pemesan(String nama){
        return (nama.length() > 2 && nama.length() < 21);
     }
     
     public boolean ktp(String ktp){
         try{
             Long.parseLong(ktp);
          } catch (NumberFormatException e){
                return false;
          } 
        boolean b = ktp.length() == 16;
        if(b){
            return b;
        } else {
            System.out.println(" No KTP tidak Sesuai!");
            return b;
        }
     }
    
     public boolean aftertgl(String tgl) {
         try{
            Date now = new Date();
            String fn = dateformatter.format(now);
            Date comp = dateformatter.parse(fn);
            Date newtgl = dateformatter.parse(tgl);
                      
            if(newtgl.after(now) || newtgl.equals(comp)){
                return true;
            } else {
                return false;
            }
                 
         }
         catch(ParseException e) {
            return  false;
         }
     }
     
     public String formatNama(String nama){
        String t;
        t = sb.append(nama).toString();
        for(int i = nama.length(); i<20 ;i++){
            t=sb.append(" ").toString();
        }
        sb.setLength(0);
    return t;
    }
    
     
     public String rawDate(){
         Date now = new Date();
         return del.format(now);
     }
}
