-- Create MaintenancePlan table
CREATE TABLE maintenance_plan (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL,
    equipment_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    frequency_type VARCHAR(50) NOT NULL,
    frequency_value INTEGER NOT NULL,
    tolerance_days INTEGER,
    next_maintenance_date DATE NOT NULL,
    estimated_duration_hours INTEGER,
    responsible_role VARCHAR(100),
    checklist TEXT,
    active BOOLEAN DEFAULT true,
    CONSTRAINT fk_plan_equipment FOREIGN KEY (equipment_id) REFERENCES equipment(id)
);

CREATE INDEX idx_maintenance_plan_code ON maintenance_plan(code);
CREATE INDEX idx_maintenance_plan_equipment ON maintenance_plan(equipment_id);
CREATE INDEX idx_maintenance_plan_next_date ON maintenance_plan(next_maintenance_date);
CREATE INDEX idx_maintenance_plan_active ON maintenance_plan(active);
