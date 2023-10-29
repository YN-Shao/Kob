# elec5619
Before running this application, ensure that your system had installed java, gradle, yarn and mySQL.

The step before running the application is to ensure that a database is created for the project. You will need to create a user in mysql that has the username root and password admin123 as specified in our application.properties file. Next you will need to create a kob database. Inside the kob database, you will create the following table. 

create table bot
(
    id          int auto_increment
        primary key,
    user_id     int            not null,
    title       varchar(50)    not null,
    description varchar(300)   null,
    content     varchar(10000) null,
    create_time datetime       null,
    modify_time datetime       null
);

create table game
(
    id    int auto_increment
        primary key,
    title varchar(1000) null
);

create table gomoku_record
(
    id          int auto_increment
        primary key,
    a_id        int           null,
    b_id        int           null,
    steps       varchar(5000) null,
    loser       varchar(5)    null,
    create_time timestamp     null
);

create table snake_record
(
    id          int auto_increment
        primary key,
    a_id        int           null comment 'a_id',
    a_sx        int           null comment 'a_sx',
    a_sy        int           null,
    b_id        int           null,
    b_sx        int           null,
    b_sy        int           null,
    a_steps     varchar(1000) null,
    b_steps     varchar(1000) null,
    map         varchar(1000) null,
    loser       varchar(10)   null,
    create_time datetime      null
);

create table user
(
    id            int auto_increment
        primary key,
    username      varchar(50)      null,
    password      varchar(100)     null,
    photo         varchar(2000)    null,
    rating        int default 1500 null,
    gomoku_rating int default 1500 null,
    constraint id
        unique (id)
);

create table post
(
    post_id      int auto_increment
        primary key,
    user_id      int           null,
    post_content varchar(2000) not null,
    constraint post_user_id_fk
        foreign key (user_id) references user (id)
);

After creating the database, you can run the application by first gradle build all the package inside backend cloud, so that is the backend package, the botrunningsystem package and machingsystem package. After you had build those package, you can run them by running their jar file directly using the following command:



You can then go to the frontend package, and use yarn serve to run the frontend in developement
