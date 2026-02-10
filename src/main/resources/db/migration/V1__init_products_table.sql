CREATE TABLE products (
  id BIGSERIAL PRIMARY KEY,
  serial_number VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  category VARCHAR(255),
  price DOUBLE PRECISION NOT NULL,
  currency VARCHAR(10) NOT NULL,

  search_vector tsvector
      GENERATED ALWAYS AS (
          to_tsvector(
                  'simple',
                  coalesce(serial_number, '') || ' ' ||
                  coalesce(name, '') || ' ' ||
                  coalesce(description, '') || ' ' ||
                  coalesce(category, '') || ' ' ||
                  coalesce(currency, '')
          )
      ) STORED
);

CREATE INDEX idx_products_search_vector
    ON products
    USING GIN (search_vector);

CREATE UNIQUE INDEX ux_products_serial_number
    ON products (serial_number);
