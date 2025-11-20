-- Create InventoryMovement table
CREATE TABLE inventory_movement (
    id BIGSERIAL PRIMARY KEY,
    spare_part_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    quantity INTEGER NOT NULL,
    reason VARCHAR(500),
    work_order_id BIGINT,
    movement_date TIMESTAMP NOT NULL,
    performed_by VARCHAR(100),
    CONSTRAINT fk_movement_spare_part FOREIGN KEY (spare_part_id) REFERENCES spare_part(id),
    CONSTRAINT fk_movement_work_order FOREIGN KEY (work_order_id) REFERENCES work_order(id)
);

CREATE INDEX idx_inventory_movement_spare_part ON inventory_movement(spare_part_id);
CREATE INDEX idx_inventory_movement_work_order ON inventory_movement(work_order_id);
CREATE INDEX idx_inventory_movement_date ON inventory_movement(movement_date);
