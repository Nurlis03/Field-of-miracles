-- Rename app_user to user_account
ALTER TABLE app_user RENAME TO user_account;

-- Add new columns to user_account
ALTER TABLE user_account
  ADD first_name VARCHAR(255),
  ADD last_name VARCHAR(255),
  ADD middle_name VARCHAR(255),
  ADD email VARCHAR(255),
  ADD birth_date DATE,
  ALTER COLUMN password TYPE VARCHAR(60);

-- Add Unique Constraint on email in user_account
ALTER TABLE user_account ADD CONSTRAINT uc_user_account_email UNIQUE (email);

-- Drop the role and username columns from user_account
ALTER TABLE user_account
  DROP COLUMN role,
  DROP COLUMN username;

-- Create Role Table
CREATE TABLE IF NOT EXISTS role (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name VARCHAR(255)
);

-- Create Join Table for Many-to-Many relationship
CREATE TABLE IF NOT EXISTS users_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES user_account(id),
  CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES role(id),
  PRIMARY KEY (user_id, role_id)
);
