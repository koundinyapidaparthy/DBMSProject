
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertQueryClass {

    public static void main(String[] args) {
             try (Connection connection = DBConnection.getConnection()) {
                // ! 5. We add a product (p100, cd, 5) in Product and (p100, d2, 50) in Stock.

                // ? query to insert
                
                    // Check and insert into the product table
                    String productQuery = "INSERT INTO product (prod_id, pname, price) SELECT ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM product WHERE prod_id = ?)";
                    try (PreparedStatement productStatement = connection.prepareStatement(productQuery)) {
                        productStatement.setString(1, "p100");
                        productStatement.setString(2, "cd");
                        productStatement.setInt(3, 5);
                        productStatement.setString(4, "p100");
                        productStatement.executeUpdate();
                    }

                    // ? Insert into stock

                    InsertQueryReuse.ReuseInsertQuery("p100", "d2", 50);
                    
                // ! 6. We add a depot (d100, Chicago, 100) in Depot and (p1, d100, 100) in Stock.

                // ? query to insert
                
                String depotQuery = "INSERT INTO depot (dept_id, addr, volume) SELECT ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM depot WHERE dept_id = ?)";
                try (PreparedStatement depotStatement = connection.prepareStatement(depotQuery)) {
                    depotStatement.setString(1, "d100");
                    depotStatement.setString(2, "Chicago");
                    depotStatement.setInt(3, 100);
                    depotStatement.setString(4, "d100");
                    depotStatement.executeUpdate();
                }

                InsertQueryReuse.ReuseInsertQuery("p1", "d100", 100);

                GetTableDetails.main(args);
            }
        
            catch (SQLException e) {
            e.printStackTrace();
        }
    }
}