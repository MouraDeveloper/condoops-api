alter table categories
    add column default_response_hours integer not null default 24
        check (default_response_hours > 0);