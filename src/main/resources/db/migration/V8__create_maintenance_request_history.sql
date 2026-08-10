CREATE TABLE maintenance_requests_history
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    maintenance_request_id UUID NOT NULL
        REFERENCES maintenance_requests(id)
            ON DELETE RESTRICT,

    previous_status VARCHAR(20) NOT NULL,
    new_status VARCHAR(20) NOT NULL,

    observation TEXT,

    responsible_user_id UUID NOT NULL
        REFERENCES users(id)
            ON DELETE RESTRICT,

    changed_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);