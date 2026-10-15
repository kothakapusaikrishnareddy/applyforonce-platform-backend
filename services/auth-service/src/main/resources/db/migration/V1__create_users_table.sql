
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,

                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,

                       role VARCHAR(30) NOT NULL DEFAULT 'CANDIDATE',
                       status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',

                       created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT chk_users_role
                           CHECK (role IN ('CANDIDATE', 'ADMIN')),

                       CONSTRAINT chk_users_status
                           CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED'))
);

CREATE INDEX idx_users_status ON users(status);
