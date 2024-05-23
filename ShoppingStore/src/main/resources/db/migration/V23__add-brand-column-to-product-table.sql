ALTER TABLE product
ADD COLUMN brand_id BIGINT,
ADD CONSTRAINT fk_product_brand
    FOREIGN KEY (brand_id) REFERENCES brands(id);
