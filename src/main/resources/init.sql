create table user(
    username varchar(30) NOT NULL UNIQUE,
    password varchar(30) NOT NULL,
    name varchar(30) NOT NULL,
    phoneNumber varchar(30) NOT NULL,
    PRIMARY KEY(username)
);

create table car(
    carId bigint AUTO_INCREMENT,
    price double not null,
    structure varchar(50) not null,
    powerType varchar(50) not null,
    brand varchar(50) not null,
    series varchar(50) not null,
    type varchar(50) not null,
    PRIMARY KEY(carId)
);

create table `order` (
    username varchar(30),
    carId BIGINT,
    time datetime NOT NULL,
    PurchaseMethod varchar(50) NOT NULL,
    remark varchar(50) NOT NULL,
    CONSTRAINT pk_user
    FOREIGN KEY(username) REFERENCES user(username),
    CONSTRAINT pk_car
    FOREIGN KEY(carId) REFERENCES car(carId)
);