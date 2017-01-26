package bdor5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;

/**
 *
 * @author oracle
 */
public class Bdor5 {

    public static Connection conexion = null;
    
    public static Connection getConexion() throws SQLException {
        String usuario = "hr";
        String password = "hr";
        String host = "localhost"; // tambien puede ser una ip como "192.168.1.14"
        String puerto = "1521";
        String sid = "orcl";
        String ulrjdbc = "jdbc:oracle:thin:" + usuario + "/" + password + "@" + host + ":" + puerto + ":" + sid;
        conexion = DriverManager.getConnection(ulrjdbc);
        return conexion;
    }

    public static void closeConexion() throws SQLException {
        conexion.close();
    }

    public void insireLinea(int ordnumx,int linumx, int cantidadx, int descuentox,int itemx) throws SQLException{
        try{
            String inserir = "INSERT INTO THE (SELECT P.pedido FROM pedido_tab P WHERE P.ordnum =?) SELECT ?, REF(S), ?, ? FROM item_tab S WHERE S.itemnum = ?";
            PreparedStatement ps = conexion.prepareStatement(inserir);
            ps.setInt(1,ordnumx);
            ps.setInt(2,linumx);
            ps.setInt(3,cantidadx);
            ps.setInt(4,descuentox);
            ps.setInt(5,itemx);
            ps.executeUpdate();
            ps.close();
            System.out.println("Inserido correctamente");
        }catch(Exception e){
            System.out.println("Non se pode inserir o pedido");
        }
    }
    
    public void modificaLinea(String nome,int clinum){
        try{
            String modifica = "UPDATE cliente_tab c SET c.clinomb=? WHERE c.clinum=?";
            PreparedStatement ps = conexion.prepareStatement(modifica);
            ps.setString(1,nome);
            ps.setInt(2, clinum);
            ps.executeUpdate();
            System.out.println("Modificado correctamente");
        }catch(Exception e){
            System.out.println("Non se pode modificar o cliente");
        }
    }
    public void borraLinea(int ordnum, int linum){
        try{
            String elimina = "DELETE FROM THE(SELECT p.pedido FROM pedido_tab p WHERE p.ordnum=?)WHERE linum=?";
            PreparedStatement ps = conexion.prepareStatement(elimina);
            ps.setInt(1, ordnum);
            ps.setInt(2, linum);
            ps.executeUpdate();
            System.out.println("Eliminado correctamente");
        }catch(Exception e){
            System.out.println("Non se pode eliminar a linea de pedido");
        }
    }

    public static void main(String[] args) throws SQLException {
        Bdor5 b = new Bdor5();
        b.getConexion();
        //b.insireLinea(4001,48,20,10,2004);
        b.modificaLinea("Alvaro Luna",2);
        //b.borraLinea(4001,48);
        b.closeConexion();
    }

}
