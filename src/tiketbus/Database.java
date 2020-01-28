/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiketbus;
import java.sql.*; 
import java.util.*;

/**
 *
 * @author whiz
 */
public class Database {
    StringBuffer sb = new StringBuffer();
    Scanner sc = new Scanner(System.in);
    String sql = "";
    boolean hasil;
    
    public boolean insert(String table, String[] data){
        sql = sb.append("INSERT INTO `"+table+"` VALUES(").toString();
        for(int i = 0; i < data.length ; i++){
            sql = sb.append("?").toString();
            if(i+1 != data.length){
                sql = sb.append(",").toString();
            } else {
                sql = sb.append(",?").toString();
            }
        }
        sql = sb.append(");").toString();
        sb.setLength(0);
        try
        {
            String urlValue = getUrlValue();
            
            Connection conn = DriverManager.getConnection(urlValue);
            PreparedStatement pStatement = null;
            
            pStatement = conn.prepareStatement(sql);
            
            for(int i = 0; i < data.length ; i++){
                pStatement.setString(i+1,data[i]);
                if(i+1 == data.length){
                    pStatement.setString(i+2,"1");
                }
            }
            
            int intBaris = pStatement.executeUpdate();
            if(intBaris > 0){
               hasil = true;
            } else {
               hasil = false;
            }
            
            pStatement.close();
            conn.close();
        } catch(SQLException e)
        {
            if(table == "t_bus"){
                System.out.println(" Kode Bus sudah ada!");
            }
            if(table == "t_perjalanan"){
                System.out.println(" Kode Perjalanan sudah ada!");
            }
            hasil = false;
        } 
        
        return hasil;
    }
    
    public List<Map<String, Object>> select(String table,String where){
        
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> row = null;
        
        if(where == ""){
            sql="SELECT * FROM `"+table+"` WHERE `aktif`=1;";        
        } else if(table=="t_perjalanan`,`t_bus"){
            sql="SELECT DISTINCT * FROM `"+table+"` WHERE t_perjalanan.`aktif`=1 AND "+where+" ORDER BY `tujuan`;";    
        } 
        else {
            sql="SELECT * FROM `"+table+"` WHERE `aktif`=1 AND "+where+";";
        }
        
        if(table == "counttiket"){
            sql = "SELECT COUNT(*) AS tiket FROM `t_tiket`  WHERE `aktif`=1 AND "+ where;
        }
        
        try
        {
            String urlValue = getUrlValue();
            
            Connection conn = DriverManager.getConnection(urlValue);
            PreparedStatement pStatement = null;
            
            pStatement = conn.prepareStatement(sql);
            
            ResultSet rs = pStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            Integer columnCount = metaData.getColumnCount();

            while (rs.next()) {
                row = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
            }
            resultList.add(row);
        }
            pStatement.close();
            conn.close();
        } catch(SQLException e)
        {
            System.out.println("Gagal Mengambil Data = "+e);
            sc.nextLine();
        } 
        
        return resultList;
    }
    
    public boolean update(String table,String[] col, String[] data,String where){
        sql = sb.append("UPDATE `"+table+"` SET ").toString();
        for(int i = 1; i < col.length ; i++){
            sql = sb.append(col[i-1]+"=?").toString();
            if(i+1 != col.length){
                sql = sb.append(",").toString();
            } else {
                sql = sb.append(","+col[i]+"=?").toString();
            }
        }
        
        sql = sb.append(where+";").toString();
        sb.setLength(0);
        
        try
        {
            String urlValue = getUrlValue();
            
            Connection conn = DriverManager.getConnection(urlValue);
            PreparedStatement pStatement = null;
            
            pStatement = conn.prepareStatement(sql);
            
            for(int i = 0; i < data.length ; i++){
                pStatement.setString(i+1,data[i]);
            }
            if(pStatement.executeUpdate()>0){
                hasil = true;
            } else {
                hasil = false;
            }
            
            pStatement.close();
            conn.close();
        } catch(SQLException e) {
            if(table == "t_bus"){
                System.out.println(" Kode Bus sudah ada!");
            }
            if(table == "t_perjalanan"){
                System.out.println(" Kode Perjalanan sudah ada!");
            }
            hasil = false;
        }     
        return hasil;
    }
    
    public boolean delete(String table,String where, String key, String val){
        sql = "UPDATE `"+table+"` SET `aktif`=0 , `"+key+"`='"+val+"' WHERE aktif=1 AND "+where;
        try
        {
            String urlValue = getUrlValue();
            
            Connection conn = DriverManager.getConnection(urlValue);
            PreparedStatement pStatement = null;
            
            pStatement = conn.prepareStatement(sql);
            
            if(pStatement.executeUpdate()>0){
                hasil = true;
            } else {
                hasil = false;
            }
            
            pStatement.close();
            conn.close();
        } catch(SQLException e)
        {         
            hasil = false;
        }   
        return hasil;
    }
    
    public String getUrlValue(){
        String user="root";
        String pwd="";
        String host="localhost";
        String db="tiketBus";
        
        return "jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
    }
    
    public boolean testKoneksi(){
        try
        {
            String urlValue = getUrlValue();
            
            Connection conn = DriverManager.getConnection(urlValue);
            PreparedStatement pStatement = null;
            
            pStatement = conn.prepareStatement(sql);
            return true;
        } catch(SQLException e)
            {
                return false;
            }    
        }
    
        public String jamBerangkat(String kodebus){
            
             List<Map<String, Object>> data = select("t_bus", "kode_bus='"+kodebus+"'");
            return data.get(0).get("jam_berangkat").toString();
        }
        
        public String getTiket(){
            
            Random rnd = new Random();
            int num;  
            boolean b = true;
            String tiket = "";
            
            while(b){
                num = rnd.nextInt(9999999);
                tiket = "B-"+String.format("%07d", num);; 
                if(select("t_tiket", "id_tiket='"+tiket+"'").size() == 0 ){
                    b = false;
                }
            }
            
            return tiket;
        };
        
    public boolean fullBus(String kodebus, String tujuan, String tgl){
        int bus=0,tiket=0;
        List<Map<String, Object>> dataBus = select("t_bus", "`kode_bus`='"+kodebus+"'");
        List<Map<String, Object>> dataTiket = select("counttiket", "`tujuan`='"+tujuan+"' AND `tanggal_berangkat`='"+tgl+"' AND `kode_bus`='"+kodebus+"'");
        
        String busSeat = dataBus.get(0).get("jmlh_seat").toString();
        String tiketCount = dataTiket.get(0).get("tiket").toString();
        
        try{
         bus = Integer.parseInt(busSeat);
         tiket = Integer.parseInt(tiketCount);   
        }catch (NumberFormatException e){}
        
        if(bus > tiket){
            return true;
        } else {
            System.out.println(" Maaf, Bus Dengan Kode Bus '"+kodebus+"' Pada Tanggal "+tgl+" Sudah Penuh!");
            return false;
        }
    }
    
}
