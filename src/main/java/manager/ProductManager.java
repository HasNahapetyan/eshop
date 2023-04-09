package manager;

import db.DBConnectionProvider;
import manager.CategoryManager;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private CategoryManager categoryManager = new CategoryManager();

    public void save(Product product) {
        String sql = "INSERT INTO product(name,description,price,quantity,category_id) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, String.valueOf(product.getPrice()));
            ps.setString(4, String.valueOf(product.getQuantity()));
            ps.setString(5, String.valueOf(product.getCategory().getId()));
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
            System.out.println("product inserted into DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getById(int id) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("Select * from product where id = " + id);
            if (resultSet.next()) {
                return getProductFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from product");
            while (resultSet.next()) {
                productList.add(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getInt("price"));
        product.setQuantity(resultSet.getInt("quantity"));
        int categoryId = resultSet.getInt("category_id");
        product.setCategory(categoryManager.getById(categoryId));
        return product;
    }


    public void removeById(int productId) {
        String sql = "DELETE FROM product WHERE id = " + productId;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}