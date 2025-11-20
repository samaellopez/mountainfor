-- Create SparePart table
CREATE TABLE spare_part (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL,
    unit_of_measure VARCHAR(50),
    current_stock INTEGER DEFAULT 0,
    minimum_stock INTEGER,
    maximum_stock INTEGER,
    location VARCHAR(200),
    unit_price DECIMAL(19,2)
);

CREATE INDEX idx_spare_part_code ON spare_part(code);
CREATE INDEX idx_spare_part_stock_level ON spare_part(current_stock, minimum_stock);
