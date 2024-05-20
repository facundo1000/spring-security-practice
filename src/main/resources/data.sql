INSERT INTO ACCOUNT (username,password,email,enabled) VALUES ('user-one','$2a$12$TqrEF3PI8C1RyCka5byK9OAc6WAneORpXMyQBg2X6oLnqoRaVxtDa','userOne@gmail.com',true);
INSERT INTO ACCOUNT (username,password,email,enabled) VALUES ('user-two','$2a$12$YYJAQBXo/EnNtz7Nk3eVB.goasc1embDMaBplRztGRPfbpQnyoz3.','userTwo@gmail.com',true);
INSERT INTO ACCOUNT (username,password,email,enabled) VALUES ('user-three','$2a$12$Pi9vYAhACU/PSGgst4PlS.mM053wt8WaWSQ8BWvbDt4pzJCmk0ucm','dev@gmail.com',true);


INSERT INTO ROLE (name) VALUES ('ADMIN'); -- password: 1234
INSERT INTO ROLE (name) VALUES ('USER'); -- password: abcd
INSERT INTO ROLE (name) VALUES ('DEVELOPER'); -- password: dev

INSERT INTO TBL_ACCOUNT_ROLE (account_id,role_id) VALUES (1,1);
INSERT INTO TBL_ACCOUNT_ROLE (account_id,role_id) VALUES (1,2);
INSERT INTO TBL_ACCOUNT_ROLE (account_id,role_id) VALUES (2,2);
INSERT INTO TBL_ACCOUNT_ROLE (account_id,role_id) VALUES (3,1);
INSERT INTO TBL_ACCOUNT_ROLE (account_id,role_id) VALUES (3,2);
INSERT INTO TBL_ACCOUNT_ROLE (account_id,role_id) VALUES (3,3);
