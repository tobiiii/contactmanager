CREATE TABLE company
(
    id                            BIGINT auto_increment NOT NULL,
    created timestamp(6)          without time zone,
    updated timestamp(6)          without time zone,
    version bigint,
    code    VARCHAR(255)          NOT NULL,
    address VARCHAR(255)          NOT NULL,
    tva     VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_company PRIMARY KEY (id)
);


CREATE TABLE contact
(
    id                              BIGINT auto_increment NOT NULL,
    created    timestamp(6)            without time zone,
    updated    timestamp(6)            without time zone,
    version    bigint,
    code       VARCHAR(255)         NOT NULL,
    first_name VARCHAR(255)         NOT NULL,
    last_name  VARCHAR(255)         NOT NULL,
    address    VARCHAR(255)         NOT NULL,
    tva        VARCHAR(255),
    type       VARCHAR(255),
    CONSTRAINT pk_contact PRIMARY KEY (id)
);

CREATE TABLE company_contacts
(
    company_id  BIGINT NOT NULL,
    contacts_id BIGINT NOT NULL,
    CONSTRAINT pk_company_contacts PRIMARY KEY (company_id, contacts_id),
    CONSTRAINT fk_comcon_on_company FOREIGN KEY (company_id)
        REFERENCES company (id),
    CONSTRAINT fk_comcon_on_contact FOREIGN KEY (contacts_id)
        REFERENCES contact (id)
);


