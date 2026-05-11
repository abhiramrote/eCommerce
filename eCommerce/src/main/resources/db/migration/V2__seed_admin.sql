-- src/main/resources/db/migration/V2__seed_admin.sql

-- password = "admin123" bcrypt-hashed
INSERT INTO users (email, password, role)
VALUES ('admin@smartshop.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        'ADMIN');