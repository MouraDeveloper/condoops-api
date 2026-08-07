CREATE TABLE maintenance_requests
(
    id               UUID PRIMARY KEY       DEFAULT gen_random_uuid(),

    title            VARCHAR(120)  NOT NULL,
    description      VARCHAR(1000) NOT NULL,

    priority         VARCHAR(20)   NOT NULL,
    status           VARCHAR(20)   NOT NULL DEFAULT 'OPEN',

    opened_at        TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    deadline         TIMESTAMPTZ   NOT NULL,

    asset_id         UUID          NOT NULL
        REFERENCES assets (id)
            ON DELETE RESTRICT,

    category_id      UUID          NOT NULL
        REFERENCES categories (id)
            ON DELETE RESTRICT,

    requester_id     UUID          NOT NULL
        REFERENCES users (id)
            ON DELETE RESTRICT,

    condominium_id   BIGINT        NOT NULL
        REFERENCES condominiums (id)
            ON DELETE RESTRICT,

    rejection_reason VARCHAR(500),
    completed_at     TIMESTAMPTZ,

    created_at       TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ   NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_maintenance_requests_priority
        CHECK (priority IN (
                            'LOW',
                            'MEDIUM',
                            'HIGH',
                            'CRITICAL'
            )),

    CONSTRAINT chk_maintenance_requests_status
        CHECK (status IN (
                          'OPEN',
                          'UNDER_REVIEW',
                          'APPROVED',
                          'REJECTED',
                          'CANCELED',
                          'COMPLETED'
            ))
);