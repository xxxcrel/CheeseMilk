delete from cheese_house.user where id = 1;
delete from cheese_house.user where id = 2;

insert into cheese_house.role(id, description, role_name) values(1, "超级管理员,可对用户进行管理,授予角色", "SUPER_ADMIN");

-- 超级管理员
insert into cheese_house.user(id, username, password, email, nickname, status) values(1, "xxxcrel", "xc514xxx", "crelxc@gamil.com", "xuecheng", 1);
insert into cheese_house.user(id, username, password, email, nickname, status) values(2, "eric", "hellowq", "hellowq@gamil.com", "wanqiang", 1);

insert into cheese_house.user_role(user_id, role_id) values(1, 1);
insert into cheese_house.user_role(user_id, role_id) values(2, 1);

-- 插入官方板块
insert into cheese_house.category(id, category_name, description) values(1, "Moments", "记录生活,忘记你");
insert into cheese_house.category(id, category_name, description) values(2, "WhiteWall", "南理表白墙，专注大学生的幸福");
insert into cheese_house.category(id, category_name, description) values(3, "IdleFish", "南理闲鱼，淘尽二手");
insert into cheese_house.category(id, category_name, description) values(4, "RentHouse", "租房，出租，你想要的我都有");
insert into cheese_house.category(id, category_name, description) values(5, "IKnowNothing", "你不知道的，学长学姐也许会知道");
insert into cheese_house.category(id, category_name, description) values(6, "Bullshit", "离我远点，我只想吐槽(Leave me along, I was just bullshitting");
insert into cheese_house.category(id, category_name, description) values(7, "FreshmanSummer", "让我想哭又想校的夏天");

insert into cheese_house.manager_group()

