CREATE TABLE project (
    id SERIAL PRIMARY KEY,
    name VARCHAR(75) NOT NULL,
    description TEXT NOT NULL,
    owner_id BIGINT NOT NULL,
    project_key VARCHAR UNIQUE NOT NULL,
    create_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE project_member (
    id SERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    user_nick VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE
);