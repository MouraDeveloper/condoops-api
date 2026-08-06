CREATE TABLE assets
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code           VARCHAR(50) NOT NULL,
    name           VARCHAR(120) NOT NULL,
    description    VARCHAR(500),
    location       VARCHAR(160) NOT NULL,
    manufacturer   VARCHAR(120),
    model          VARCHAR(100),
    serial_number  VARCHAR(100),
    status         VARCHAR(30) NOT NULL DEFAULT 'OPERATIONAL',
    active         BOOLEAN NOT NULL DEFAULT TRUE,

    condominium_id BIGINT NOT NULL
        REFERENCES condominiums(id)
            ON DELETE RESTRICT,

    created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_assets_condominium_code
        UNIQUE (condominium_id, code),

    CONSTRAINT ck_assets_status
        CHECK (
            status IN (
                       'OPERATIONAL',
                       'UNDER_MAINTENANCE',
                       'OUT_OF_SERVICE'
                )
            )
);

CREATE INDEX idx_assets_condominium_id
    ON assets(condominium_id);

CREATE INDEX idx_assets_condominium_status
    ON assets(condominium_id, status);