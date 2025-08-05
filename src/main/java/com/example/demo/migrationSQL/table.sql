-- 1. Donor
CREATE TABLE donor (
                       email VARCHAR(100) PRIMARY KEY,
                       full_name VARCHAR(100) NOT NULL
);

-- 2. Beneficiary
CREATE TABLE beneficiary (
                             email VARCHAR(100) PRIMARY KEY,
                             full_name VARCHAR(100) NOT NULL
);

-- 3. Payment
CREATE TABLE payment (
                         id VARCHAR(100) PRIMARY KEY,
                         amount DOUBLE PRECISION NOT NULL,
                         payment_method VARCHAR(50),
                         payment_date TIMESTAMP,
                         status VARCHAR(20)
);

-- 4. Donation
CREATE TABLE donation (
                          id VARCHAR(50) PRIMARY KEY,
                          donor_email VARCHAR(100),
                          payment_id VARCHAR(100),
                          FOREIGN KEY (donor_email) REFERENCES donor(email),
                          FOREIGN KEY (payment_id) REFERENCES payment(id)
);

-- 5. Help
CREATE TABLE help (
                      id VARCHAR(50) PRIMARY KEY,
                      beneficiary_email VARCHAR(100),
                      payment_id VARCHAR(100),
                      description_accident VARCHAR(255),
                      FOREIGN KEY (beneficiary_email) REFERENCES beneficiary(email),
                      FOREIGN KEY (payment_id) REFERENCES payment(id)
);
