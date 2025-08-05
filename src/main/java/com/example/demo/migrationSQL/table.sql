CREATE TABLE donation (
                          id VARCHAR(50) PRIMARY KEY,
                          donor_email VARCHAR(100),
                          donor_nom VARCHAR(100),
                          montant INT,
                          moyen_paiement VARCHAR(50),
                          date_paiement TIMESTAMP,
                          status VARCHAR(20),
                          psp_payment_id VARCHAR(100)
);

CREATE TABLE help (
                      id VARCHAR(50) PRIMARY KEY,
                      beneficiary_email VARCHAR(100),
                      beneficiary_nom VARCHAR(100),
                      montant INT,
                      moyen_paiement VARCHAR(50),
                      date_paiement TIMESTAMP,
                      description_accident VARCHAR(255)
);

INSERT INTO help VALUES ('HELP1', 'lao.andria@hei.school', 'Lao Andria', 50000, 'Orange Money', CURRENT_TIMESTAMP, 'Accident moto');
