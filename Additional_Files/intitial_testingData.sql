use blog;

insert into role values (1, 'admin');
insert into role values (2, 'user');

insert into user values (1, 'testUser1', 'testPassword1');
insert into user values (2, 'testUser2', 'testPassword2');

insert into user_role values (1, 1, 1);
insert into user_role values (2, 1, 2);
insert into user_role values (3, 2, 2);

insert into post_status values (1, 'pending');
insert into post_status values (2, 'approved');

insert into post values(1, 1, 'test title 1', 'test post', '2022-12-02 14:15:00', null, 2);
insert into post values(2, 1, 'test title 2', 'test post 2', '2022-12-02 14:16:00', null, 2);
insert into post values(3, 2, 'test title 3', 'test post 3', '2022-12-02 15:35:00', null, 1);

insert into tag values(1,'test');
insert into tag values(2,'bleh');

insert into post_tag values(1,1,2);
insert into post_tag values(2,2,1);
