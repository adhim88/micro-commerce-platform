MERGE INTO roles (id, role_name)
KEY(role_name)
VALUES
    (NEXT VALUE FOR roles_seq, 'ROLE_ADMIN'),
    (NEXT VALUE FOR roles_seq, 'ROLE_EMPLOYEE'),
    (NEXT VALUE FOR roles_seq, 'ROLE_CUSTOMER');