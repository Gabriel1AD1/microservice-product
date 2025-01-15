-- Crear la tabla tbl_product sin los CHECKs
CREATE TABLE IF NOT EXISTS tbl_product (
                                           id SERIAL PRIMARY KEY,  -- SERIAL genera un valor único automáticamente para el id
                                           name VARCHAR(100) NOT NULL,  -- 3-100 caracteres para el nombre del producto
                                           description VARCHAR(500),  -- Descripción del producto, máximo 500 caracteres
                                           price DECIMAL(10, 2) NOT NULL,  -- Precio con dos decimales y mayor que 0
                                           quantity INT NOT NULL,  -- Cantidad, debe ser mayor que 0
                                           category VARCHAR(255),  -- Categoría del producto
                                           image_url VARCHAR(255),  -- URL de la imagen
                                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Fecha de creación, por defecto la hora actual
                                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Fecha de actualización, por defecto la hora actual
                                           brand VARCHAR(255),  -- Marca del producto
                                           status VARCHAR(50)  -- Estado del producto, ahora solo un VARCHAR sin el CHECK
);

-- Índices adicionales para mejorar el rendimiento de las consultas (opcional)
CREATE INDEX IF NOT EXISTS idx_product_name ON tbl_product(name);
CREATE INDEX IF NOT EXISTS idx_product_category ON tbl_product(category);
CREATE INDEX IF NOT EXISTS idx_product_status ON tbl_product(status);
INSERT INTO tbl_product (name, description, price, quantity, category, image_url, brand, status) VALUES
                                                                                                     ('Wireless Mouse', 'Ergonomic wireless mouse with adjustable DPI.', 19.99, 50, 'Electronics', 'https://example.com/images/mouse.jpg', 'Logitech', 'Available'),
                                                                                                     ('Gaming Keyboard', 'Mechanical keyboard with RGB lighting.', 89.99, 30, 'Electronics', 'https://example.com/images/keyboard.jpg', 'Corsair', 'Available'),
                                                                                                     ('Office Chair', 'Ergonomic office chair with lumbar support.', 129.99, 15, 'Furniture', 'https://example.com/images/chair.jpg', 'IKEA', 'Out of Stock'),
                                                                                                     ('Water Bottle', 'Stainless steel water bottle, 1L capacity.', 15.49, 100, 'Sports', 'https://example.com/images/bottle.jpg', 'Thermos', 'Available'),
                                                                                                     ('Yoga Mat', 'Non-slip yoga mat, 6mm thick.', 24.99, 80, 'Sports', 'https://example.com/images/yogamat.jpg', 'Gaiam', 'Available'),
                                                                                                     ('Noise Cancelling Headphones', 'Over-ear headphones with active noise cancelling.', 199.99, 20, 'Electronics', 'https://example.com/images/headphones.jpg', 'Sony', 'Available'),
                                                                                                     ('Smartphone Case', 'Durable and slim smartphone case.', 9.99, 200, 'Accessories', 'https://example.com/images/case.jpg', 'Spigen', 'Available'),
                                                                                                     ('Running Shoes', 'Lightweight running shoes with cushioned soles.', 74.99, 60, 'Apparel', 'https://example.com/images/shoes.jpg', 'Nike', 'Available'),
                                                                                                     ('Smartwatch', 'Water-resistant smartwatch with fitness tracking.', 249.99, 25, 'Electronics', 'https://example.com/images/smartwatch.jpg', 'Apple', 'Available'),
                                                                                                     ('Desk Lamp', 'LED desk lamp with adjustable brightness.', 29.99, 40, 'Furniture', 'https://example.com/images/lamp.jpg', 'Philips', 'Available');
