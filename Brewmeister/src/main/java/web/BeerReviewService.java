package web;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Pdante on 2016-04-01.
 */
public class BeerReviewService {

    public BeerReview convertResultSetToBeerReview(ResultSet rs){

        VendorService vs = new VendorService();

        try{
            String bname = rs.getString("BName");
            int cid = rs.getInt("cid");
            int bRate = rs.getInt("brate");
            String review = rs.getString("review");
            boolean newReview = rs.getBoolean("newreview");

            ArrayList<Vendor> vendors = vs.getVendorsThatSellABeer(bname);


            BeerReview newBR = new BeerReview(bname, review, bRate, cid, newReview);


            return newBR;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}