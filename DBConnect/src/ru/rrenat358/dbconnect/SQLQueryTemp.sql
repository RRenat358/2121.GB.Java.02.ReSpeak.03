

INSERT INTO User (login, password, name, age)
VALUES ('aa', 'bb','cc','23')
;

INSERT INTO User (login, password)
VALUES ('aaa', 'bbb')
;

select login, password
from User
where login = 'Den' and password = 'ddd'
;

select login, password, name
from User
where login = 'Den' and password = 'ddd'
;



select * from User
;


