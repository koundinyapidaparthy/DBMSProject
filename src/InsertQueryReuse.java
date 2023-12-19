import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertQueryReuse {
    public static void ReuseInsertQuery(String prod_id, String dept_id, Integer quantity) {
        System.out.println(prod_id);
        try (Connection connection = DBConnection.getConnection()) {
        String checkProductQuery = "SELECT 1 FROM product WHERE prod_id = ?";
                    try (PreparedStatement checkProductStatement = connection.prepareStatement(checkProductQuery)) {

                        checkProductStatement.setString(1, prod_id);
                        ResultSet checkProductStatementSet = checkProductStatement.executeQuery();

                        if (checkProductStatementSet.next()) {

                            // ? Check if the depot exists
                             String checkDepotQuery = "SELECT 1 FROM depot WHERE dept_id = ?";
                            try (PreparedStatement checkDepotStatement = connection.prepareStatement(checkDepotQuery)) {

                                checkDepotStatement.setString(1, dept_id);
                                ResultSet checkDepotStatementSet = checkDepotStatement.executeQuery();

                                if(checkDepotStatementSet.next()){
                                    // Check and insert into the stock table
                                    String stockQuery = "INSERT INTO stock (prod_id, dept_id, quantity) SELECT ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM stock WHERE prod_id = ? AND dept_id = ?)";
                                    try (PreparedStatement updateStatement1 = connection.prepareStatement(stockQuery)) {
                                        updateStatement1.setString(1, prod_id);
                                        updateStatement1.setString(2, dept_id);
                                        updateStatement1.setInt(3, quantity);
                                        updateStatement1.setString(4, prod_id);
                                        updateStatement1.setString(5, dept_id);
                                        int resultSet =updateStatement1.executeUpdate();
                                    System.out.println("Table Insertion is done. Rows affected: " + Integer.toString(resultSet));
                                    }
                                }
                            }
                        }     
                    }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
