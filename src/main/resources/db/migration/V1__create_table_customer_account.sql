 CREATE TABLE customer_account (
    customer_id SERIAL PRIMARY KEY,
    account_name VARCHAR(255),
    address TEXT,
    date TIMESTAMP,
    account_number VARCHAR(255),
    account_description TEXT,
    branch VARCHAR(255),
    cif_no VARCHAR(255),
    ifs_code VARCHAR(255),
    micr_code VARCHAR(255),
    balance DOUBLE PRECISION,
    email VARCHAR(255),
    gender VARCHAR(50),
    contact_no VARCHAR(50),
    country VARCHAR(100),
    dob VARCHAR(50)
);

CREATE TABLE customer_logging (
    logging_id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    target_loc VARCHAR(255),
    generated_at TIMESTAMP,
    success BOOLEAN
);
