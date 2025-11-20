-- Create PurchaseRequest table
CREATE TABLE purchase_request (
    id BIGSERIAL PRIMARY KEY,
    request_number VARCHAR(50) NOT NULL UNIQUE,
    spare_part_id BIGINT NOT NULL,
    requested_quantity INTEGER NOT NULL,
    justification VARCHAR(1000),
    status VARCHAR(50) NOT NULL,
    requested_by VARCHAR(100) NOT NULL,
    request_date TIMESTAMP NOT NULL,
    approved_by VARCHAR(100),
    approved_date TIMESTAMP,
    purchase_order_number VARCHAR(50),
    ordered_date TIMESTAMP,
    received_quantity INTEGER,
    received_date TIMESTAMP,
    unit_price DECIMAL(19,2),
    total_cost DECIMAL(19,2),
    supplier VARCHAR(200),
    rejection_reason VARCHAR(500),
    CONSTRAINT fk_request_spare_part FOREIGN KEY (spare_part_id) REFERENCES spare_part(id)
);

CREATE INDEX idx_purchase_request_number ON purchase_request(request_number);
CREATE INDEX idx_purchase_request_spare_part ON purchase_request(spare_part_id);
CREATE INDEX idx_purchase_request_status ON purchase_request(status);
CREATE INDEX idx_purchase_request_date ON purchase_request(request_date);
