-- Create Equipment table
-- This table stores equipment/assets information

CREATE TABLE equipment (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL,
    type VARCHAR(50) NOT NULL,
    location VARCHAR(200),
    cost_center VARCHAR(50),
    status VARCHAR(50) NOT NULL,
    installation_date DATE,
    estimated_life_years INTEGER,
    parent_equipment_id BIGINT,
    CONSTRAINT fk_parent_equipment FOREIGN KEY (parent_equipment_id) REFERENCES equipment(id)
);

-- Create index on code for faster lookups
CREATE INDEX idx_equipment_code ON equipment(code);

-- Create index on location for filtering
CREATE INDEX idx_equipment_location ON equipment(location);

-- Create index on status for filtering
CREATE INDEX idx_equipment_status ON equipment(status);
