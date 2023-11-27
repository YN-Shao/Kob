## First Step
Before running this application, ensure that your system had installed java, gradle, yarn and mySQL.


## Second Step
The step before running the application is to ensure that a database is created for the project. You will need to create a user in mysql that has the username root and password admin123 as specified in our application.properties file. Next you will need to create a kob database. Inside the kob database, you will create the following table. 

```
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
```

## Third Step
After creating the database, you can run the application by first gradle build all the package inside backend cloud, so that is the backend package, the botrunningsystem package and machingsystem package. The frontend runs on localhost:8080, while the backend runs on localhost:3000, 3001, and 3002 ports. Please ensure these port numbers are not occupied. After you had build those package, you can run them by running their jar file directly using the following command:

```
"your java path" '-Dcom.sun.management.jmxremote' '-Dcom.sun.management.jmxremote.port=50362' '-Dcom.sun.management.jmxremote.authenticate=false' '-Dcom.sun.management.jmxremote.ssl=false' '-Dspring.jmx.enabled=true' '-Djava.rmi.server.hostname=localhost' '-Dspring.application.admin.enabled=true' '-Dspring.boot.project.name=backend' '-cp' "your jar" 'com.kob.backend.BackendApplication' 

"your java path" '-Dcom.sun.management.jmxremote' '-Dcom.sun.management.jmxremote.port=50480' '-Dcom.sun.management.jmxremote.authenticate=false' '-Dcom.sun.management.jmxremote.ssl=false' '-Dspring.jmx.enabled=true' '-Djava.rmi.server.hostname=localhost' '-Dspring.application.admin.enabled=true' '-Dspring.boot.project.name=botrunningsystem' '-cp' "your jar" 'com.kob.botrunningsystem.BotRunningSystemApplication' 

"your java path" '-Dcom.sun.management.jmxremote' '-Dcom.sun.management.jmxremote.port=50535' '-Dcom.sun.management.jmxremote.authenticate=false' '-Dcom.sun.management.jmxremote.ssl=false' '-Dspring.jmx.enabled=true' '-Djava.rmi.server.hostname=localhost' '-Dspring.application.admin.enabled=true' '-Dspring.boot.project.name=matchingsystem' '-cp' "your jar" 'com.kob.matchingsystem.MatchingSystemApplication' 
```

With the items within double quotation mark to be replaced by your actual java path and jar path. 

If you have trouble running this way, you can also run your prefered ide such as intellij with springboot or VSCode with Springboot Dashboard plugin to run all the different backend package.


## Forth Step
You can then go to the frontend package, and use _yarn install_ to install all the essential frontend package. You can then run _yarn serve_ to run the frontend in develoopment or you can use _yarn build_ and create minifiles for production and deploy it through instruction provided in this web page https://cli.vuejs.org/guide/deployment.html#vercel

## Conclusion
By running all the step, you should be able to run the frontend in your browser and using it to iteracte with the entire system.
