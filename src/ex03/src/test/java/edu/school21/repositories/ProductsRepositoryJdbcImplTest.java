package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {

    final List<Product> initData = new ArrayList<>
            (Arrays.asList(new Product(1, "A", 10),
                    new Product(2, "B", 15),
                    new Product(3, "C", 25),
                    new Product(4, "D", 8)));

    Connection connection;
    DataSource dataSource;
    private ProductsRepositoryJdbcImpl productsRepositoryJdbc;

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @AfterEach
    void close() throws SQLException {
        if (connection != null) connection.close();
    }

    @Test
    void checkFindAll() {
        Product[] expectedArray = initData.toArray(new Product[0]);
        Product[] actualArray = productsRepositoryJdbc.findAll().toArray(new Product[0]);
        Assertions.assertArrayEquals(expectedArray, actualArray);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void checkFindById(int Id) {
        Assertions.assertEquals(Optional.of(initData.get(Id - 1)),
                productsRepositoryJdbc.findById((long) Id));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5})
    void checkFindByIdException(int Id) {
        Assertions.assertThrows(RuntimeException.class, () -> productsRepositoryJdbc.findById((long) Id));
    }

    @Test
    void checkUpdate() {
        Product productToUpdate = new Product(1, "NewName", 50);
        productsRepositoryJdbc.update(productToUpdate);
        Assertions.assertEquals(Optional.of(productToUpdate), productsRepositoryJdbc.findById(1L));
    }

    @Test
    void checkUpdateException() {
        Assertions.assertThrows(RuntimeException.class, () -> productsRepositoryJdbc.update(null));
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -20, -30})
    void checkUpdateExceptionParam(int Id) {
        Assertions.assertThrows(RuntimeException.class,
                () -> productsRepositoryJdbc.update(new Product(Id, "A", 200)));
    }

    @Test
    void checkSave() {
        Product newProduct = new Product(5, "E", 20);
        productsRepositoryJdbc.save(newProduct);
        Assertions.assertEquals(Optional.of(newProduct), productsRepositoryJdbc.findById(5L));
    }

    @Test
    void checkSaveExceptionNull() {
        Assertions.assertThrows(RuntimeException.class, () -> productsRepositoryJdbc.update(null));
    }

    @Test
    void checkSaveException() {
        Product newProduct = new Product(1, "E", 20);
        Assertions.assertThrows(RuntimeException.class, () -> productsRepositoryJdbc.save(newProduct));
    }

    @Test
    void checkDelete() {
        productsRepositoryJdbc.delete(1L);
        Assertions.assertEquals(Optional.empty(), productsRepositoryJdbc.findById(1L));
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -20, -30})
    void checkException(int Id) {
        Assertions.assertThrows(RuntimeException.class, () -> productsRepositoryJdbc.delete((long) Id));
    }
}
