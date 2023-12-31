CREATE TABLE IF NOT EXISTS PROPERTY
(
    ID         UUID PRIMARY KEY,
    ALIAS      VARCHAR,
    CREATED_AT TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS PROPERTY_UNIQUE_ALIAS_IDX ON PROPERTY (ALIAS);

CREATE TABLE IF NOT EXISTS BOOKING
(
    ID                UUID PRIMARY KEY,
    START_DATE        TIMESTAMP NOT NULL,
    END_DATE          TIMESTAMP NOT NULL,
    STATUS            VARCHAR   NOT NULL,
    PROPERTY_ID       UUID      NOT NULL,
    CANCELLATION_DATE TIMESTAMP,
    GUEST_FIRST_NAME  VARCHAR   NOT NULL,
    GUEST_LAST_NAME   VARCHAR   NOT NULL,
    GUEST_DOB         TIMESTAMP NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED_AT   TIMESTAMP
);

CREATE INDEX IF NOT EXISTS BOOKING_PROPERTY ON BOOKING (PROPERTY_ID);
CREATE INDEX IF NOT EXISTS BOOKING_DATES_IDX ON BOOKING (START_DATE, END_DATE);
CREATE INDEX IF NOT EXISTS BOOKING_STATUS ON BOOKING (STATUS);

CREATE TABLE IF NOT EXISTS BLOCK
(
    ID                UUID PRIMARY KEY,
    START_DATE        TIMESTAMP NOT NULL,
    END_DATE          TIMESTAMP NOT NULL,
    PROPERTY_ID       UUID      NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS BLOCK_PROPERTY ON BLOCK (PROPERTY_ID);