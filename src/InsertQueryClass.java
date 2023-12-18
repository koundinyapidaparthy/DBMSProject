
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

                    // Check and insert into the stock table
                    String stockQuery = "INSERT INTO stock (prod_id, dept_id, quantity) SELECT ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM stock WHERE prod_id = ? AND dept_id = ?)";
                    try (PreparedStatement updateStatement1 = connection.prepareStatement(stockQuery)) {
                        updateStatement1.setString(1, "p100");
                        updateStatement1.setString(2, "d2");
                        updateStatement1.setInt(3, 50);
                        updateStatement1.setString(4, "p100");
                        updateStatement1.setString(5, "d2");
                        int resultSet =updateStatement1.executeUpdate();
                    System.out.println("Table Insertion is done. Rows affected: " + Integer.toString(resultSet));
                    }

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

                // Check and insert into the stock table
                String stockQuery1 = "INSERT INTO stock (prod_id, dept_id, quantity) SELECT ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM stock WHERE prod_id = ? AND dept_id = ?)";
                try (PreparedStatement updateStatement1 = connection.prepareStatement(stockQuery1)) {
                    updateStatement1.setString(1, "p1");
                    updateStatement1.setString(2, "d100");
                    updateStatement1.setInt(3, 100);
                    updateStatement1.setString(4, "p1");
                    updateStatement1.setString(5, "d100");
                     int resultSet =updateStatement1.executeUpdate();
                    System.out.println("Table Insertion is done. Rows affected: " + Integer.toString(resultSet));
                    
                }

                GetTableDetails.main(args);
            }
        
            catch (SQLException e) {
            e.printStackTrace();
        }
    }
}