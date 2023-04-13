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
        String sql = "Select * from product where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, String.valueOf(id));
            ResultSet resultSet = ps.executeQuery();
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
            String sql = "Select * from product";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = ps.executeQuery();
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
        String sql = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, String.valueOf(productId));
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public void update(Product product) {
//        String sql = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ? WHERE id = ?";
//        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
//            ps.setString(1,product.getName());
//            ps.setString(2,product.getDescription());
//            ps.setString(3, String.valueOf(product.getPrice()));
//            ps.setString(4, String.valueOf(product.getQuantity()));
//            ps.setString(5, String.valueOf(product.getId()));
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void updateName(Product product) {
        String sql = "UPDATE product SET name = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,product.getName());
            ps.setString(2, String.valueOf(product.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDescription(Product product) {
        String sql = "UPDATE product SET description = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,product.getDescription());
            ps.setString(2, String.valueOf(product.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePrice(Product product) {
        String sql = "UPDATE product SET price = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, String.valueOf(product.getPrice()));
            ps.setString(2, String.valueOf(product.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateQuantity(Product product) {
        String sql = "UPDATE product SET quantity = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, String.valueOf(product.getQuantity()));
            ps.setString(2, String.valueOf(product.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCategory(Product product) {
        String sql = "UPDATE product SET category_id = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, String.valueOf(product.getCategory().getId()));
            ps.setString(2, String.valueOf(product.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer sumOfProducts() {
        String sql = "SELECT SUM(quantity) as total FROM product";
        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public Double maxOfPriceProduct() {
        String sql = "SELECT Max(price) as max FROM product";
        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("max");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Double minOfPriceProduct() {
        String sql = "SELECT MIN(price) as min FROM product";
        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("min");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Double avgOfPriceProduct() {
        String sql = "SELECT AVG(price) as avg FROM product";
        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("avg");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}