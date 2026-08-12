CREATE TABLE work_orders
(
    id                     UUID PRIMARY KEY        DEFAULT gen_random_uuid(),

    order_number           VARCHAR(255)   NOT NULL UNIQUE,
    status                 VARCHAR(30)    NOT NULL DEFAULT 'PENDING_ASSIGNMENT',

    maintenance_request_id UUID           NOT NULL UNIQUE
        REFERENCES maintenance_requests (id),

    technician_id          UUID
        REFERENCES users (id),

    diagnosis              TEXT,
    execution_description  TEXT,

    assigned_at            TIMESTAMPTZ,
    started_at             TIMESTAMPTZ,
    finished_at            TIMESTAMPTZ,

    labor_cost             NUMERIC(12, 2) NOT NULL DEFAULT 0,
    material_cost          NUMERIC(12, 2) NOT NULL DEFAULT 0,

    condominium_id         BIGINT         NOT NULL
        REFERENCES condominiums (id),

    created_at             TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMPTZ    NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_work_orders_status
        CHECK (
            status IN (
                       'PENDING_ASSIGNMENT',
                       'ASSIGNED',
                       'IN_PROGRESS',
                       'WAITING_CONFIRMATION',
                       'COMPLETED',
                       'CANCELED'
                )
            ),

    CONSTRAINT chk_work_orders_labor_cost
        CHECK (labor_cost >= 0),

    CONSTRAINT chk_work_orders_material_cost
        CHECK (material_cost >= 0)
);