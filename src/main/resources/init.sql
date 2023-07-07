create table user(
    userId int AUTO_INCREMENT,
    password varchar(30) NOT NULL,
    name varchar(30) NOT NULL,
    phoneNumber varchar(30) NOT NULL,
    type varchar(30) Not Null,
    PRIMARY KEY(userId)
);

create table car(
    carId int AUTO_INCREMENT,
    price double not null,
    powerType varchar(50) not null,
    brand varchar(50) not null,
    series varchar(50) not null,
    type varchar(50) not null,
    number int not null ,
    PRIMARY KEY(carId)
);

create table `order` (
    orderId int AUTO_INCREMENT,
    carId int,
    userId int,
    cusID varchar(30) NOT NULL ,
    orderTime date NOT NULL ,
    cusName varchar(30) NOT NULL ,
    cusPhone varchar(30) NOT NULL ,
    hasLicenseServer varchar(10) not null ,
    payMethod varchar(30) NOT NULL ,
    pmtDiscount int not null ,
    deposit int not null ,
    deliveryTime date NOT NULL ,
    purchaseTax int not null ,
    PRIMARY KEY(orderId),
    CONSTRAINT pk_user
    FOREIGN KEY(userId) REFERENCES user(userId),
    CONSTRAINT pk_car
    FOREIGN KEY(carId) REFERENCES car(carId)
);

create table Insurances (
    insName varchar(30) primary key ,
    price double NOT NULL
);

create table purIns (
    insName varchar(30),
    orderId int,
    CONSTRAINT pk_order
    FOREIGN KEY(orderId) REFERENCES `order`(orderId),
    CONSTRAINT pk_ins
    FOREIGN KEY(insName) REFERENCES Insurances(insName)
);