-- Create WorkOrderCost table
CREATE TABLE work_order_cost (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT NOT NULL UNIQUE,
    labor_cost DECIMAL(19,2) DEFAULT 0,
    spare_parts_cost DECIMAL(19,2) DEFAULT 0,
    other_costs DECIMAL(19,2) DEFAULT 0,
    total_cost DECIMAL(19,2) DEFAULT 0,
    CONSTRAINT fk_cost_work_order FOREIGN KEY (work_order_id) REFERENCES work_order(id)
);

CREATE INDEX idx_work_order_cost_work_order ON work_order_cost(work_order_id);
