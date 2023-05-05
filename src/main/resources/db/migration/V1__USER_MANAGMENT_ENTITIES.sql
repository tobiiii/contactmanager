CREATE TABLE privilege
(
    id          bigint                                              NOT NULL,
    created     timestamp(6) without time zone,
    updated     timestamp(6) without time zone,
    version     bigint,
    code        character varying(50) NOT NULL,
    description character varying(255)   NOT NULL,
    CONSTRAINT privilege_pkey PRIMARY KEY (id),
    CONSTRAINT uk_privilege_code UNIQUE (code)
);

CREATE TABLE profile
(
    id      bigint                                              NOT NULL,
    created timestamp(6) without time zone,
    updated timestamp(6) without time zone,
    version bigint,
    code    character varying(255)   NOT NULL,
    name    character varying(255)   NOT NULL,
    CONSTRAINT profile_pkey PRIMARY KEY (id),
    CONSTRAINT uk_profile_code UNIQUE (code),
    CONSTRAINT uk_profile_name UNIQUE (name)
);

CREATE TABLE profiles_privileges
(
    profile_id   bigint NOT NULL,
    privilege_id bigint NOT NULL,
    CONSTRAINT fk_privilege_profile FOREIGN KEY (privilege_id)
        REFERENCES privilege (id),
    CONSTRAINT fk_profile_privilege FOREIGN KEY (profile_id)
        REFERENCES profile (id)
);


CREATE TABLE "user"
(
    id            bigint                                              NOT NULL,
    created       timestamp(6) without time zone,
    updated       timestamp(6) without time zone,
    version       bigint,
    email_address character varying(255)   NOT NULL,
    first_name    character varying(255)   NOT NULL,
    last_name     character varying(255)   NOT NULL,
    profile_id    bigint                                              NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_user_email_address UNIQUE (email_address),
    CONSTRAINT fk_user_profile FOREIGN KEY (profile_id)
        REFERENCES profile (id) 
);

CREATE TABLE security_customization
(
    dtype        character varying(31)   NOT NULL,
    id           bigint                                             NOT NULL,
    created      timestamp(6) without time zone,
    updated      timestamp(6) without time zone,
    version      bigint,
    credential   character varying(255)  ,
    is_temporary boolean,
    user_id      bigint                                             NOT NULL,
    CONSTRAINT security_customization_pkey PRIMARY KEY (id),
    CONSTRAINT uk_security_customization_user_id UNIQUE (user_id),
    CONSTRAINT fk_security_customization_user_id FOREIGN KEY (user_id)
        REFERENCES "user" (id)
);


CREATE TABLE usersession
(
    id          bigint  NOT NULL,
    created     timestamp(6) without time zone,
    updated     timestamp(6) without time zone,
    version     bigint,
    email       character varying(255)  ,
    full_name   character varying(255)  ,
    ip_address  character varying(255)  ,
    is_active   boolean NOT NULL,
    is_blocked  boolean NOT NULL,
    language    character varying(255)  ,
    logon_time  timestamp(6) without time zone,
    logout_time timestamp(6) without time zone,
    token       text  ,
    user_id     bigint  NOT NULL,
    CONSTRAINT usersession_pkey PRIMARY KEY (id),
    CONSTRAINT fk_usersession_user FOREIGN KEY (user_id)
        REFERENCES "user" (id)
);
