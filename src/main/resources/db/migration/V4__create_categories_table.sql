CREATE TABLE categories
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name           VARCHAR(80) NOT NULL,
    description    VARCHAR(255),
    active         BOOLEAN NOT NULL DEFAULT TRUE,

    condominium_id BIGINT NOT NULL
        REFERENCES condominiums(id)
            ON DELETE RESTRICT,

    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT uk_categories_condominium_name
        UNIQUE (condominium_id, name)
);
