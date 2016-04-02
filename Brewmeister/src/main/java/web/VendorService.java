package web;

import java.sql.*;
import java.util.ArrayList;

public class VendorService {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public ArrayList<Vendor> getVendorsThatSellABeer(String bname) throws Exception{
        String searchString = "SELECT bv.* FROM BeerVendor bv, beerinstock bis " +
                "WHERE bv.StoreID = bis.StoreID AND bis.bname LIKE '%" + bname + "%'";

        System.out.println(searchString);

        try {

            Class.forName("com.mysql.jdbc.Driver");

            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/beerinfo?"
                            + "user=sqluser&password=sqluserpw");
            preparedStatement = connect
                    .prepareStatement(searchString);
            resultSet = preparedStatement.executeQuery();
            //JSONArray beers = new JSONArray();
            ArrayList<Vendor> vendors = new ArrayList<Vendor>();
            while(resultSet.next()){
                vendors.add(convertResultSetToVendor(resultSet));
            }
            //return vendors;
            return vendors;

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public String getBeersByVendor(String storeName) throws SQLException, ClassNotFoundException {
        String searchString = "SELECT bi.*" +
                " FROM BeerInfo bi, BeerVendor bv, BeerInStock bis " +
                "WHERE bi.BName = bis.BName AND bv.storeID = bis.storeID and bv.storeName like '%" + storeName + "%'";

        System.out.println(searchString);
        return searchString;
    }

    public String getBeersByVendorStocked(String storeName) throws SQLException, ClassNotFoundException {
        String searchString = "SELECT *, CASE WHEN (SELECT BName FROM BeerVendor bv, BeerInStock bis WHERE " +
                "beerinfo.BName = bis.BName AND bv.storeID = bis.storeID AND " +
                "bv.storeName like '%"+storeName+"%') is null then 0 else 1 end as stocked from beerinfo";

        System.out.println(searchString);
        return searchString;
    }

    public Vendor convertResultSetToVendor(ResultSet rs) throws Exception{
        int storeID = rs.getInt("storeID");
        String storeName = rs.getString("storeName");
//        String address = rs.getString("address");
        Vendor newVendor = new Vendor(storeID, storeName); //, address);
        return newVendor;
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
