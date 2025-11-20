-- Create WorkOrder table
-- This table stores both corrective and preventive work orders

CREATE TABLE work_order (
    id BIGSERIAL PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(500) NOT NULL,
    failure_description VARCHAR(1000),
    priority VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    assigned_to VARCHAR(100),
    maintenance_plan_id BIGINT,
    CONSTRAINT fk_equipment FOREIGN KEY (equipment_id) REFERENCES equipment(id)
);

-- Create index on equipment_id for faster lookups
CREATE INDEX idx_work_order_equipment ON work_order(equipment_id);

-- Create index on status for filtering
CREATE INDEX idx_work_order_status ON work_order(status);

-- Create index on assigned_to for filtering
CREATE INDEX idx_work_order_assigned_to ON work_order(assigned_to);

-- Create index on type for filtering
CREATE INDEX idx_work_order_type ON work_order(type);
