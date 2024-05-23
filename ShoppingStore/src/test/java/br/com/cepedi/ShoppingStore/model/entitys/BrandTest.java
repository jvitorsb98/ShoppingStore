package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.brand.input.DataRegisterBrand;
import br.com.cepedi.ShoppingStore.model.records.brand.input.DataUpdateBrand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BrandTest {

    @Test
    public void shouldCreateBrandWithDataRegisterBrand() {
        // Arrange
        DataRegisterBrand dataRegisterBrand = new DataRegisterBrand("Nike");

        // Act
        Brand brand = new Brand(dataRegisterBrand);

        // Assert
        Assertions.assertEquals("Nike", brand.getName());
        Assertions.assertFalse(brand.getDisabled());
    }

    @Test
    public void shouldUpdateBrandWithDataUpdateBrand() {
        // Arrange
        Brand brand = new Brand(new DataRegisterBrand("Nike"));
        DataUpdateBrand dataUpdateBrand = new DataUpdateBrand(brand.getId(), "Adidas");

        // Act
        brand.updateDataBrand(dataUpdateBrand);

        // Assert
        Assertions.assertEquals("Adidas", brand.getName());
    }

    @Test
    public void shouldDisableBrand() {
        // Arrange
        Brand brand = new Brand(new DataRegisterBrand("Nike"));

        // Act
        brand.disable();

        // Assert
        Assertions.assertTrue(brand.getDisabled());
    }

    @Test
    public void shouldEnableBrand() {
        // Arrange
        Brand brand = new Brand(new DataRegisterBrand("Nike"));
        brand.disable();

        // Act
        brand.enable();

        // Assert
        Assertions.assertFalse(brand.getDisabled());
    }

    @Test
    void shouldSetId() {
        // Arrange
        Brand brand = new Brand();

        // Act
        brand.setId(1L);

        // Assert
        Assertions.assertEquals(1L, brand.getId());
    }

    @Test
    void shouldSetName() {
        // Arrange
        Brand brand = new Brand();

        // Act
        brand.setName("Nike");

        // Assert
        Assertions.assertEquals("Nike", brand.getName());
    }

    @Test
    void shouldSetDisabled() {
        // Arrange
        Brand brand = new Brand();

        // Act
        brand.setDisabled(true);

        // Assert
        Assertions.assertTrue(brand.getDisabled());
    }


}