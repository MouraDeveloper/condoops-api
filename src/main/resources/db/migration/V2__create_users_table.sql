create table users
(
    id             uuid PRIMARY KEY      DEFAULT gen_random_uuid(),
    name           varchar(120) not null,
    email          varchar(160) not null unique,
    role           varchar(30)  not null,
    active         boolean      not null default true,
    condominium_id bigint       not null references condominiums (id) on delete restrict,
    created_at     TIMESTAMPTZ  not null default current_timestamp,
    updated_at     TIMESTAMPTZ  not null default current_timestamp,

    CONSTRAINT ck_users_role
        CHECK (role IN ('ADMIN', 'MANAGER', 'RESIDENT', 'TECHNICIAN'))
);
