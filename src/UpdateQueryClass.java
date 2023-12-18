
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateQueryClass {

    public static void main(String[] args) {
             try (Connection connection = DBConnection.getConnection()) {
                // ! 3. The product p1 changes its name to pp1 in Product and Stock.
                
                // ? Alter table
                
                String alterQuery ="ALTER TABLE stock DROP CONSTRAINT fk_stock_product; ALTER TABLE stock ADD CONSTRAINT fk_stock_product FOREIGN KEY (prod_id) REFERENCES product(prod_id) ON UPDATE CASCADE;";
                
                try(PreparedStatement alterStatement= connection.prepareStatement(alterQuery)) {
                    int resultSet = alterStatement.executeUpdate();
                    System.out.println("Table Altering is done. Rows affected: " + Integer.toString(resultSet));
                }
                
                // ? query to update
                
                String updateQuery = "UPDATE product SET prod_id = ? WHERE prod_id = ?; " +
                "UPDATE stock SET prod_id = ? WHERE prod_id = ?";
                try(PreparedStatement updateStatement1 = connection.prepareStatement(updateQuery);
                ){
                    updateStatement1.clearParameters();
                    updateStatement1.setObject(1, "p1"); 
                    updateStatement1.setObject(2, "pp1"); 
                    updateStatement1.setObject(3, "p1"); 
                    updateStatement1.setObject(4, "pp1"); 
                    int resultSet =updateStatement1.executeUpdate();
                    System.out.println("Table Altering is done. Rows affected: " + Integer.toString(resultSet));
                }

                // ! 4. The depot d1 changes its name to dd1 in Depot and Stock.
                // ? Alter table
                
                String alterQuery1 ="ALTER TABLE stock DROP CONSTRAINT fk_stock_depot; ALTER TABLE stock ADD CONSTRAINT fk_stock_depot FOREIGN KEY (dept_id) REFERENCES depot(dept_id) ON UPDATE CASCADE;";
                
                try(PreparedStatement alterStatement= connection.prepareStatement(alterQuery1)) {
                    int resultSet = alterStatement.executeUpdate();
                    System.out.println("Table Updating is done. Rows affected: " + Integer.toString(resultSet));
                }
                
                // ? query to update
                
                String updateQuery1 = "UPDATE depot SET dept_id = ? WHERE dept_id = ?; " +
                "UPDATE stock SET dept_id = ? WHERE dept_id = ?";
                try(PreparedStatement updateStatement1 = connection.prepareStatement(updateQuery1);
                ){
                    updateStatement1.clearParameters();
                    updateStatement1.setObject(1, "d1"); 
                    updateStatement1.setObject(2, "dd1"); 
                    updateStatement1.setObject(3, "d1"); 
                    updateStatement1.setObject(4, "dd1"); 
                    int resultSet =updateStatement1.executeUpdate();
                    System.out.println("Table Updating is done. Rows affected: " + Integer.toString(resultSet));
                }

                System.out.println("");
                GetTableDetails.main(args);
            }
        
            catch (SQLException e) {
            e.printStackTrace();
        }
    }
}