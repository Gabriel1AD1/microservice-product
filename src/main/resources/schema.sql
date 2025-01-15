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
