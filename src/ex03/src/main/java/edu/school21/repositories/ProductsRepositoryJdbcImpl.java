package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class ProductsRepositoryJdbcImpl implements ProductsRepository{

    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public List<Product> findAll() {
        ArrayList<Product> productArrayList = new ArrayList<>();

        String allQuery = "SELECT * FROM tests.product;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(allQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getInt(3));
                productArrayList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productArrayList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        if (id <= 0) throw new RuntimeException();
        Optional<Product> optionalProduct;
        String selectQuery = "SELECT * FROM tests.product WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product(resultSet.getInt(1),
                        resultSet.getString("name"), resultSet.getInt("cost"));
                optionalProduct = Optional.of(product);
            } else {
                optionalProduct = Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionalProduct;
    }

    @Override
    public void update(Product product) {
        if (product == null || product.getId() <= 0) throw new RuntimeException();
        String updateQuery = "UPDATE tests.product SET name = ?, cost = ? WHERE id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getCost());
            statement.setLong(3, product.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Product product) {
        if (product == null || product.getId() <= 0) throw new RuntimeException();
        String insertQuery = "INSERT INTO tests.product (id, name, cost) VALUES ( ?, ?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setLong(1, product.getId());
            statement.setString(2, product.getName());
            statement.setInt(3, product.getCost());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        if (id <= 0) throw new RuntimeException();
        String deleteQuery = "DELETE FROM tests.product WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
