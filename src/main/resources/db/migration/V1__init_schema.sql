-- Table: public.roles
CREATE TABLE IF NOT EXISTS public.roles (
                                            id integer NOT NULL DEFAULT nextval('roles_id_seq'::regclass),
    role_name character varying(50) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
    );

-- Table: public.users
CREATE TABLE IF NOT EXISTS public.users (
                                            id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    username character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
    );

-- Table: public.user_roles
CREATE TABLE IF NOT EXISTS public.user_roles (
                                                 user_id bigint NOT NULL,
                                                 role_id bigint NOT NULL,
                                                 CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id)
    REFERENCES public.roles (id)
    ON DELETE CASCADE,
    CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id)
    REFERENCES public.users (id)
    ON DELETE CASCADE
    );
