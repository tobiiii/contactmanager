INSERT INTO PRIVILEGE ( id, created, updated, version, code, description)
VALUES (1, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'profile.add','Créer')
     , (2, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'profile.delete','Supprimer')
     , (3, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'profile.detail','Détail')
     , (4, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'profile.update','Modifier')
     , (6, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'profile.list','Lister')
     , (5, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'user.add','Créer')
     , (7, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'user.delete','Supprimer')
     , (8, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'user.detail','Détail')
     , (9, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'user.update','Modifier')
     , (10, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'user.list','Lister');



INSERT INTO PROFILE (
    id, created, updated, version, code, name)
VALUES (1, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'SUPERADMIN', 'Super Administrateur');


INSERT INTO PROFILES_PRIVILEGES (
    profile_id, privilege_id)
VALUES (1, 1), (1, 2),(1, 3),(1, 4),(1, 5),(1, 6),(1, 7), (1, 8), (1, 9), (1, 10);


INSERT INTO "USER" (
    id, created, updated, version, email_address, first_name, last_name, profile_id)
VALUES (1, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, 'superadmin@test.com', 'SUPER', 'ADMIN', 1);

INSERT INTO SECURITY_CUSTOMIZATION (
    dtype, id, created, updated, version, credential, is_temporary, user_id)
VALUES ('Password', 1, '2021-01-19 14:42:44.351', '2021-01-19 14:42:44.351', 1, '$2a$10$9dkewUPLqIhlr9OWgW9Lae7sFy8jf4oOT3d4R2c2wV3qT/MNvqxFq', true, 1);