-- Rename the column
ALTER TABLE user_account
RENAME COLUMN account_non_expired TO account_non_locked;

-- Update existing NULL values to true
UPDATE user_account
SET account_non_locked = true
WHERE account_non_locked IS NULL;

-- Modify the column to be NOT NULL and set default value to true
ALTER TABLE user_account
ALTER COLUMN account_non_locked SET NOT NULL,
ALTER COLUMN account_non_locked SET DEFAULT true;