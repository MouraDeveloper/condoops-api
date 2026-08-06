ALTER TABLE categories
DROP CONSTRAINT uk_categories_condominium_name;

CREATE UNIQUE INDEX uk_categories_condominium_name_ci
    ON categories (condominium_id, LOWER(name));